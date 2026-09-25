# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

A minimal Spring Boot 4.0.5 REST API (`com.embarkx.blogapi`), from an EmbarkX course starter project. `README.md` is course marketing only and says nothing about the code.

## Commands

Use the Maven wrapper (Maven 3.9.14). The project targets Java 17, which is the machine's default JDK, so no `JAVA_HOME` override is needed.

```bash
./mvnw spring-boot:run                     # run on http://localhost:8080 (devtools enabled)
./mvnw clean test                          # build + run all tests
./mvnw test -Dtest=BlogApiApplicationTests # single test class
./mvnw test -Dtest=BlogApiApplicationTests#contextLoads   # single test method
./mvnw clean package                       # build jar into target/
```

On Windows cmd/PowerShell use `mvnw.cmd` instead of `./mvnw`. `.gitattributes` forces LF for `mvnw` and CRLF for `*.cmd`. Keep it that way or the scripts break.

There is no linter or formatter configured. The only test, `BlogApiApplicationTests.contextLoads`, just checks that the Spring context starts; no tests cover the endpoints. `application.properties` sets `spring.application.name` and `blog.post.max-content-length`. Everything else, including port 8080, is a Spring Boot default. Override a property at run time with `./mvnw spring-boot:run -Dspring-boot.run.arguments=--blog.post.max-content-length=50`.

## Architecture

- There's only one controller, `BlogController`, mapped to `/api/posts`. It has no service or repository layer and no database.
- DTOs (Java records) live in `com.embarkx.blogapi.dto`:
  - `CreatePostRequest`: the JSON body for creating a post. It holds the Bean Validation rules: `@NotBlank` on both fields, `@Size(max = MAX_TITLE_LENGTH)` (100, a constant) on the title, and the custom `@MaxContentLength` on the content.
  - `PostResponse`: `{id, title, content}`, returned by the create/get endpoints and also used as the stored object.
- `com.embarkx.blogapi.validation`: `@MaxContentLength` and its `MaxContentLengthValidator` read the content limit from the `blog.post.max-content-length` property (1000) via `@Value`. `@Size` can't be used there, because annotation values must be compile-time constants. `BlogController` injects the same property for `/validate`.
- Posts live in a static `ConcurrentSkipListMap<Integer, PostResponse>`, keyed by a stable ID from an `AtomicInteger`. Deleting a post doesn't change other IDs. Data is in-memory only and lost on restart.
- Endpoints:
  - `POST /api/posts`: JSON body `{"title","content"}`, validated with `@Valid`. Returns 201 with the created post, or 400 with field errors.
  - `GET /api/posts`: list of posts.
  - `GET /api/posts/{id}`: one post, or 404.
  - `DELETE /api/posts/{id}`: returns `"Deleted"`, or 404.
  - `POST /api/posts/validate?content=`: still takes a query param and returns 200 with `"OK"`/`"Too long"`.
  - `GET /api/posts/total`: returns `"Total words: 600"`.
- Validation needs `spring-boot-starter-validation` (in `pom.xml`). Validation failures are handled by Spring's default error response. There's no `@RestControllerAdvice`.
- `BlogAPI.postman_collection.json` exercises these endpoints, including edge cases: nonexistent ID `999`, empty title, title over 100 characters, over-long content. Use it as a reference for expected API behavior.
- Lombok is declared as an optional dependency and configured as an annotation processor in `pom.xml`. Current code doesn't use it.
- `spring-boot-devtools` is on the runtime classpath, so error responses include full stack traces when run via `spring-boot:run`.

## Original starter bugs (intentional exercise material)

Several Postman request names still carry a `(Bug: ...)` suffix from the original starter. Status:
- **No validation:** fixed via DTO Bean Validation.
- **Crashes:** fixed. A missing ID returns 404.
- **Hardcoded 5000:** partly addressed. The limit now comes from the `blog.post.max-content-length` property (1000). `/validate` still always returns 200.
- **String concat:** fixed. `/total` sums a hardcoded `List<Integer>`, not the words in actual posts.

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

There is no linter or formatter configured. The only test, `BlogApiApplicationTests.contextLoads`, just checks that the Spring context starts; no tests cover the endpoints. `application.properties` sets only `spring.application.name`, so everything else, including port 8080, is a Spring Boot default.

## Architecture

- There's only one controller, `BlogController`, mapped to `/api/posts`. It has no service, repository or model layer, and no database.
- Posts live in a `static List<String>` in the controller, stored as `"title:content"` strings. A post's ID is its list index, so data is lost on restart and deleting a post shifts later IDs.
- Endpoints: `POST /api/posts?title=&content=`, `GET /api/posts`, `GET /api/posts/{id}`, `DELETE /api/posts/{id}`, `POST /api/posts/validate?content=` (max 1000 chars), `GET /api/posts/total`.
- `BlogAPI.postman_collection.json` exercises these endpoints, including edge cases (nonexistent ID `999`, empty title, over-long content). Use it as a reference for expected API behavior.
- Lombok is declared as an optional dependency and configured as an annotation processor in `pom.xml`. Current code doesn't use it.
- `spring-boot-devtools` is on the runtime classpath, so error responses include full stack traces when run via `spring-boot:run`.

## Known bugs (intentional exercise material)

The Postman collection names these requests with a `(Bug: ...)` suffix:
- **No validation:** fixed. `POST /api/posts` now returns 400 for a blank title or content, or for content longer than `MAX_CONTENT_LENGTH` (1000).
- **Crashes:** fixed. `GET`/`DELETE /{id}` return 404 for an out-of-range ID via `checkPostExists`. IDs are still list indexes, so deleting a post shifts later IDs.
- **Hardcoded 5000:** partly addressed. The limit (originally 5000, now 1000) is the `MAX_CONTENT_LENGTH` constant, shared by create and `/validate`. `/validate` still always returns 200, with body `"OK"` or `"Too long"`.
- **String concat:** fixed. `/total` now sums the counts numerically (`"Total words: 600"`). It still sums a hardcoded list, not the words in actual posts.

Also not flagged by Postman: the shared `ArrayList` isn't thread-safe.

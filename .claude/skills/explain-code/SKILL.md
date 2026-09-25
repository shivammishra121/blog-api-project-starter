---
name: explain-code
description: Explain Java/Spring Boot code to a learner - the big picture, the actual program flow, key concepts, good practices, and meaningful improvements with the reasoning behind them. Use when asked to explain code, a class, a method, or a change.
---

# Code Explanation Skill

## Goal

Explain code in a way that helps me understand both:
1. What the code does.
2. Why it is written that way.

I am learning Java, Spring Boot, backend engineering, and software architecture.
Do not simply give me a rewritten version of the code. Teach me the reasoning behind it.

---

## 1. Start With the Big Picture

Before explaining individual lines:

- Explain the purpose of the class/file.
- Explain what problem it solves.
- Explain where it fits in the application.
- Explain its relationship with other important classes.

Show the overall flow when applicable:

Request
→ Controller
→ Service
→ Repository
→ Database
→ Response

---

## 2. Explain the Code

Explain important code sections clearly.

For each important method:

- What is its purpose?
- What are its inputs?
- What does it return?
- What happens internally?
- Which other classes/methods does it call?
- Why is it being called?

Do not explain every trivial syntax element unless it is important for understanding.

---

## 3. Trace the Actual Program Flow

When possible, follow the code through the application.

For example:

POST /api/products
→ ProductController.createProduct()
→ ProductService.createProduct()
→ ProductRepository.save()
→ Hibernate
→ Database

Explain what happens at each step.

Do not invent a flow.
Inspect the actual codebase and explain the flow that actually exists.

---

## 4. Explain Important Java/Spring Concepts

When the code uses an important concept, explain it.

Examples:

- Dependency Injection
- Constructor Injection
- @RestController
- @Service
- @Repository
- @Entity
- DTO
- @Transactional
- JPA/Hibernate
- Exception Handling
- Validation
- Interfaces
- Generics
- Streams
- Optional
- Design Patterns

Explain why the concept is being used in this particular code.

---

## 5. Analyze Coding Practices

After explaining the code, analyze whether it follows good software engineering practices.

Check things such as:

- SOLID principles
- Separation of concerns
- Clean architecture
- Layered architecture
- Dependency injection
- Naming
- Error handling
- Validation
- Security
- Maintainability
- Readability
- Testability
- Performance
- Database practices
- API design
- Appropriate use of design patterns

Do not call something "bad practice" without explaining why.

---

## 6. If Something Is Not a Good Practice

For every significant issue, explain:

### Current approach

What the code currently does.

### Why it may be a problem

Explain the practical consequences.

### Better approach

Show what approach could be used instead.

### Why the alternative is better

Explain the improvement in terms of:

- Maintainability
- Scalability
- Readability
- Testability
- Performance
- Security
- Separation of concerns

Do not recommend changes just because another style is different.
Only recommend changes when there is a meaningful engineering reason.

---

## 7. Show Alternatives

When there are multiple reasonable approaches:

- Explain the current approach.
- Explain the alternative.
- Compare their trade-offs.
- Explain when each approach makes sense.

Do not automatically declare one approach universally "best."

---

## 8. Don't Over-Engineer

Do not recommend complex architecture just because it is possible.

Consider:

- Project size
- Current requirements
- Complexity
- Team size
- Maintainability

Explain when a simple solution is sufficient and when a more advanced solution becomes useful.

---

## 9. Security Review

Look for obvious security problems such as:

- Hardcoded secrets
- Exposed credentials
- Missing authorization
- Unsafe input handling
- SQL injection risks
- Sensitive information in responses
- Improper authentication
- Insecure file uploads

If you identify a security issue, explain the risk and safer approach.

Never expose or reproduce real secrets.

---

## 10. Performance Review

Identify meaningful performance concerns such as:

- N+1 queries
- Unnecessary database calls
- Loading excessive data
- Inefficient loops
- Unnecessary network calls
- Poor caching decisions

Explain whether the optimization is actually necessary for the current project size.

Do not optimize prematurely.

---

## 11. Testing

Explain:

- What should be unit tested.
- What should be integration tested.
- Important edge cases.
- What could currently make the code difficult to test.

If tests already exist, explain what they are testing.

---

## 12. Teaching Mode

I am learning, so whenever you identify an important design decision, explain the reasoning.

Prefer:

"Why?"

→ Explanation

→ Example

→ Consequence

rather than simply saying:

"Use this instead."

---

## 13. Final Summary

Finish with:

### What this code does
Short summary.

### Program flow
Show the actual flow.

### Important concepts
List the concepts I should understand.

### Good practices already present
Mention what the code does well.

### Potential improvements
List meaningful improvements.

For each improvement:

Problem
→ Why it matters
→ Better approach
→ Why it is better

### Learning points
Tell me what software engineering concepts I should learn from this code.

---

## Important Rules

- Understand the existing code before recommending changes.
- Do not invent classes, methods, or architecture that don't exist.
- Distinguish between an actual problem and a stylistic preference.
- Don't rewrite the entire project unnecessarily.
- Don't modify code unless I explicitly ask you to.
- When suggesting a change, explain the reason first.
- Prefer simple, maintainable solutions.
- Teach me the reasoning behind the code rather than just giving me the answer.
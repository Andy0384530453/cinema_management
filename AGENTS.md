# AGENTS.md

Guide for AI agents and contributors working in this repository.

## Project

Cinema management API. Two branches: `preprod` (current work) and `origin/main`
(holds an authoritative squashed version with `SPEC.md` + `doc/api.yaml`).
Stack: Java 21, Spring Boot 3.2, Spring Data JPA, PostgreSQL, Gradle, generated
by POJA (poja.io). OpenAPI codegen and AWS/SES infrastructure are present but
not used by the new features.

## Commands

- Run the whole suite + JaCoCo gate: `.\gradlew.bat test` (Windows).
  Requires Docker Desktop for testcontainers.
- Verify test coverage only: `.\gradlew.bat jacocoTestCoverageVerification`
- Format: `format.sh` (Unix) / `format.bat` (Windows) — google-java-format.

## Conventions

- Style: google-java-format (CI enforces it: `./format.sh && git diff --exit-code`).
- Layering: `entity/` (JPA, Lombok) → `repository/` →
  `service/` (business logic, `@Transactional`) →
  `endpoint/rest/controller/` (REST).
- New code is hand-written and must NOT be annotated `@PojaGenerated`
  (that marker is reserved for generated boilerplate).
- Lombok: `lombok.addLombokGeneratedAnnotation=true` — generated accessors are
  excluded from JaCoCo. Write explicit methods when they need coverage.
- Auth contract: `Authorization: Bearer <user-uuid>` header; the caller is
  resolved from `UserRepository`; missing/unknown identity → `401`,
  unauthorized role → `403`, missing resource → `404`.
- Roles: `CLIENT`, `EMPLOYEE`, `MANAGER` (see `entity/enums/UserRole.java`).
- No unnecessary comments; keep responses and commits in Conventional Commits:
  `feat:`, `fix:`, `test:`, `docs:`, `chore:`, `refactor:`.
  One logical change per commit. Do NOT rewrite the pushed "poja: deployment ID"
  bot commits.

## Testing

- Integration tests extend `src/test/.../conf/FacadeIT.java` (testcontainers
  Postgres, random port).
- Local run (network): use `IConstraint`: line coverage must stay ≥ 80 %
  (enforced by `gradlew test` via `jacocoTestCoverageVerification`).
- Entity accessors are excluded from coverage; business code must therefore be
  heavily tested to keep the 80 % threshold.

## Docs

- `TODO.md` — current work plan and checked items.
- `README.md` and `docs/` — project, domain, API, testing, Git conventions.
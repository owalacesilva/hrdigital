# HR Digital

A Spring Boot back-end for an HR management platform. The system models employees, payroll runs, benefit reimbursements and spending caps, and is organized around a clean / layered architecture (Application → Domain → Infrastructure).

> Status: early stage / work in progress. Only a few endpoints are wired up so far (`/health`, `POST /employees`) while the domain and persistence layers are being fleshed out.

---

## Tech stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.2.0 (Spring Web, Spring Data JPA)
- **Persistence:** Hibernate / JPA on PostgreSQL 13
- **Build tool:** Maven (Spring Boot Maven Plugin)
- **Local infrastructure:** Docker Compose (PostgreSQL, MailHog, MinIO, phpMyAdmin)
- **Testing:** `spring-boot-starter-test` (JUnit 5) — no tests checked in yet

---

## Project structure

The codebase follows a layered / DDD-flavored organization under `src/main/java/com/hrdigital`:

```
com.hrdigital
├── MainApplication.java                # Spring Boot entry point
├── Application                         # Inbound layer (HTTP + DTOs)
│   ├── Controllers
│   │   ├── HealthCheckController.java  # GET /health
│   │   ├── ListUserController.java
│   │   └── Employee
│   │       └── CreateEmployeeController.java  # POST /employees
│   └── Dto
│       └── CreateEmployeeDto.java
├── Domain                              # Business model, framework-agnostic
│   ├── Entities                        # Domain entities (Domain, EmployeeEntity,
│   │                                   # User, Payroll, Reimbursement)
│   ├── Enums                           # BenefitTypeEnum, SpendingTypeEnum
│   ├── Repositories                    # Repository interfaces (IEmployeeRepository,
│   │                                   # IUserRepository)
│   └── ValueObjects                    # ReferencePeriod, TimestampProperties
└── Infrastrucure                       # Outbound layer (note the spelling)
    └── Hibernate
        ├── Models                      # JPA @Entity classes (EmployeeModel, UserModel)
        └── Repositories                # JPA implementations of Domain repository
                                        # interfaces (EmployeeRepository, UserRepository)
```

Supporting resources live under `src/main/resources`:

- `application.properties` — Spring datasource / JPA configuration
- `beans.xml` — declares `<jpa:repositories>` for the Hibernate package
- `erd` — JSON export of the entity-relationship diagram (tables: `employees`, `payroll`, `reimbursements`, `benefit_types`, `spending_cap`)

### Architectural conventions

- **Domain layer** depends on nothing framework-specific. Entities extend the abstract `Domain` base class (which carries the `id`), and persistence is abstracted behind `I*Repository` interfaces.
- **Infrastructure layer** holds JPA `*Model` classes and concrete `Repository` implementations that translate between `Model` and `Entity` via `toDomain()` / `toModel()` mappers.
- **Application layer** (controllers + DTOs) depends on the Domain repository interfaces and is the only layer exposed over HTTP.

---

## Getting started

### Prerequisites

- JDK 17+
- Maven 3.8+
- Docker + Docker Compose (for the local PostgreSQL and auxiliary services)

### 1. Start the infrastructure

```bash
docker compose up -d
```

This starts:

| Service       | Container                | Port(s)         | Notes                                     |
| ------------- | ------------------------ | --------------- | ----------------------------------------- |
| PostgreSQL 13 | `hrdigital-postgres`     | `5432`          | DB: `hrdigital`, user: `hrdigital`        |
| MailHog       | `hrdigital-mailhog`      | `8025` / `1025` | Web UI / SMTP for email testing           |
| MinIO         | `hrdigital-minio`        | `9000` / `9001` | S3-compatible object storage / Console UI |
| phpMyAdmin    | `hrdigital-phpmyadmin`   | `8081`          | DB browser (configured against Postgres)  |

Default credentials:

- PostgreSQL: `hrdigital` / `password` (database `hrdigital`)
- MinIO: `minioadmin` / `minioadmin`

### 2. Run the application

```bash
mvn spring-boot:run
```

Or build a runnable jar:

```bash
mvn clean package
java -jar target/hrdigital-1.0.0.jar
```

The app starts on `http://localhost:8080` and Hibernate will create / update the schema automatically (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Smoke test

```bash
curl http://localhost:8080/health
# -> OK
```

---

## Configuration

Datasource and JPA settings live in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/hrdigital
spring.datasource.username=hrdigital
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

Override per environment with `application-{profile}.properties` (already covered by `.gitignore`) and run with `--spring.profiles.active=<profile>`.

---

## API

| Method | Path         | Description                                    |
| ------ | ------------ | ---------------------------------------------- |
| `GET`  | `/health`    | Liveness probe — returns `OK`.                 |
| `POST` | `/employees` | Creates an employee from a `CreateEmployeeDto` |

Example:

```bash
curl -X POST http://localhost:8080/employees \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Ada",
    "lastName": "Lovelace",
    "email": "ada@example.com"
  }'
```

---

## Domain model (planned)

The ERD under `src/main/resources/erd` already sketches the broader domain:

- **employees** — workforce members
- **payroll** — payroll runs identified by a `reference_period` (`YYYY-MM`)
- **reimbursements** — benefit reimbursement requests linked to an employee, an applicant, a payroll run and a benefit type
- **benefit_types** — catalog of benefits (e.g. *Auxílio Educação*, *Auxílio Saúde*)
- **spending_cap** — per-period spending limits with a typed validity window

Corresponding `Domain.Entities` (`Payroll`, `Reimbursement`) and value objects (`ReferencePeriod`) are scaffolded but not yet fully wired into the persistence layer.

---

## Development notes

- The codebase uses `PascalCase` package segments (e.g. `Application.Controllers`, `Domain.Entities`). This is intentional and matches the layered/DDD style used throughout the project, but it deviates from the conventional all-lowercase Java package naming.
- The infrastructure package is currently spelled `Infrastrucure` (missing an `r`). Imports must use that spelling; rename carefully if you ever fix it.
- `MainApplication` enables an explicit `@ComponentScan` over `com.hrdigital` and `com.hrdigital.Application.Controllers.*` — add new top-level packages here if Spring stops picking them up.
- `.editorconfig` enforces UTF-8, LF line endings, 4-space indentation and a 120-column max line length.

---

## Useful commands

```bash
# Run the app
mvn spring-boot:run

# Build a jar (skipping tests)
mvn clean package -DskipTests

# Run the (currently empty) test suite
mvn test

# Bring up / tear down local services
docker compose up -d
docker compose down

# Tail Postgres logs
docker logs -f hrdigital-postgres
```

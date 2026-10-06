# Online Recipe Sharing Platform

Java web project for the Java Programming project-based evaluation.

## Stack

- Java 17+
- Jakarta Servlets / JSP
- JDBC
- MySQL 8+
- Apache Tomcat 10.1+
- Maven
- HTML5 / CSS3 / JavaScript

## Roles

- **Admin**: manages users, approves/rejects recipes, moderates content, manages settings.
- **Recipe Contributor**: publishes recipes, edits own recipes, checks stats, and messages users.
- **Recipe Explorer**: browses/searches recipes, rates/reviews them, keeps collections, and views history.

## Project structure

```text
src/main/java/com/recipehub
├── controller     # HTTP request handling (Servlets)
├── dao            # JDBC database operations
├── model          # Java model classes / enums
├── service        # Business logic
├── util           # Connection and helper classes
├── filter         # Authentication / authorization
└── exception      # Application-specific exceptions

src/main/webapp
├── WEB-INF/views  # JSP pages
├── css            # Styles
└── js              # Small UI interactions

database
├── schema.sql     # Database + table definitions
└── seed.sql       # Demo data
```

## Database setup

1. Install MySQL Server.
2. Open MySQL Workbench or the MySQL shell.
3. Run `database/schema.sql`.
4. Run `database/seed.sql`.
5. Create a database user or use your local MySQL account.
6. Update the constants in `DBConnection.java` with your local username/password.

## Demo accounts

| Role | Email | Password |
|---|---|---|
| Admin | admin@recipehub.local | admin123 |
| Contributor | meera@recipehub.local | cook123 |
| Explorer | rohan@recipehub.local | explorer123 |

## Running locally

1. Install JDK 17+, Maven and Apache Tomcat 10.1+.
2. Open this project in IntelliJ IDEA or Eclipse.
3. Run the Maven build:

```bash
mvn clean package
```

4. Deploy `target/recipehub.war` to Tomcat's `webapps` directory.
5. Start Tomcat and open:

```text
http://localhost:8080/recipehub/
```

## Review 1 scope

This version establishes the application architecture, normalized database design, JDBC connection layer, role-based navigation plan, and responsive UI structure. Remaining business operations can be completed incrementally for the final review without changing the main database design.

The Review 1 build keeps sample recipe artwork inside `src/main/webapp/images`, so the basic feed does not depend on an external image CDN. Replace the demo artwork later with your own recipe photos if needed.

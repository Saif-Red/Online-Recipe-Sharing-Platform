# Online Recipe Sharing Platform

A Java-based web application for sharing, discovering, reviewing, and organizing recipes.

This project was developed as part of the **Java Programming project-based evaluation**. It demonstrates Object-Oriented Programming, Collections and Generics, JDBC database integration, Servlets, JSP, role-based access control, transaction handling, and responsive web UI design.

---

## 📌 Project Overview

The **Online Recipe Sharing Platform** provides a centralized platform where users can discover recipes, share their own recipes, rate and review recipes, and organize recipes into personal collections.

The system supports three user roles:

* **Admin** — manages users, moderates recipes, approves/rejects submissions, and monitors platform activity.
* **Recipe Contributor** — creates and manages recipes, views recipe status and statistics, and contributes new content.
* **Recipe Explorer** — browses and searches recipes, filters by cuisine, rates and reviews recipes, and saves recipes to collections.

The application uses a layered architecture to separate presentation, request handling, business logic, database operations, and data models.

---

## 🎯 Problem Statement

Finding, organizing, and sharing recipes can become difficult when information is scattered across different sources.

The Online Recipe Sharing Platform addresses this problem by providing:

* Recipe discovery through search and cuisine filters
* Structured recipe information
* Recipe submission and moderation
* Ratings and reviews
* Personal recipe collections
* Role-based functionality
* Database-backed persistent storage

---

## ✨ Implemented Features

### Authentication & Authorization

* Login using email and password
* Session-based authentication
* Role-based access control
* Separate dashboards for Admin, Contributor, and Explorer
* Protected role-specific URLs
* Logout functionality
* Invalid-login error handling

### Recipe Discovery

* Browse approved recipes
* Search recipes using the global search bar
* Filter recipes by cuisine
* Combine search and cuisine filtering
* Responsive recipe cards
* Recipe detail pages

### Recipe Details

Each recipe can contain:

* Recipe title
* Description
* Preparation time
* Cooking time
* Servings
* Difficulty
* Cuisine
* Author
* Ingredients
* Step-by-step instructions
* Average rating
* View count
* User reviews

### Reviews & Ratings

* Submit ratings from 1–5 stars
* Add written reviews
* Update an existing review
* Display average recipe ratings
* Display individual reviews

### Personal Collections

* Create collections
* View personal collections
* Open collection details
* Save recipes to collections
* Remove recipes from collections
* Display saved state for recipes

### Contributor Features

* Contributor dashboard
* View personal recipes
* View recipe approval status
* View recipe ratings and views
* Create recipes with multiple ingredients
* Add multiple preparation instructions
* Submit recipes for admin approval
* Transaction-based recipe creation

### Admin Features

* Admin dashboard
* View total users
* View recipes awaiting moderation
* View published recipe count
* View review count
* Review submitted recipes
* Approve recipes
* Reject recipes
* View users

### Responsive UI

The interface is designed to work across:

* Desktop screens
* Tablet-sized screens
* Mobile screens

The mobile layout includes responsive navigation, horizontally scrollable filters, and adapted recipe/dashboard layouts.

---

## 👥 User Roles

| Role                   | Main Capabilities                                                                     |
| ---------------------- | ------------------------------------------------------------------------------------- |
| **Admin**              | Manage users, moderate recipes, approve/reject submissions, monitor platform activity |
| **Recipe Contributor** | Create recipes, view recipe status, view recipe statistics                            |
| **Recipe Explorer**    | Browse/search recipes, filter recipes, rate/review, manage collections                |

---

## 🛠️ Technology Stack

| Technology              | Purpose                             |
| ----------------------- | ----------------------------------- |
| **Java**                | Core application logic and OOP      |
| **Jakarta Servlets**    | HTTP request handling               |
| **JSP**                 | Dynamic web pages                   |
| **JDBC**                | Database connectivity               |
| **MySQL**               | Persistent relational database      |
| **Apache Tomcat 10.1+** | Web application server              |
| **Maven**               | Dependency and build management     |
| **HTML5**               | Page structure                      |
| **CSS3**                | Responsive UI and styling           |
| **JavaScript**          | Client-side interactions            |
| **IntelliJ IDEA**       | Development environment             |
| **Git / GitHub**        | Version control and project hosting |

---

## 🏗️ Application Architecture

The project follows a layered architecture:

```text
                    ┌──────────────────────┐
                    │       Browser        │
                    │  HTML / CSS / JS     │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │      JSP Views       │
                    │    Presentation      │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │      Servlets        │
                    │ Request / Response   │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │       Services       │
                    │   Business Logic     │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │         DAO          │
                    │    JDBC Operations   │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │        MySQL         │
                    │      recipehub       │
                    └──────────────────────┘
```

### Architecture Responsibilities

**Controller**

Handles HTTP requests, validates request parameters, invokes services, and selects the appropriate view.

**Service**

Contains business rules, validation, transaction coordination, and interaction between multiple DAOs.

**DAO**

Contains JDBC-based database operations and SQL queries.

**Model**

Represents application data using Java classes and enums.

**Filter**

Handles authentication and authorization for protected application areas.

**View**

JSP pages provide the web interface presented to users.

---

## 📂 Project Structure

```text
Online-Recipe-Sharing-Platform/
│
├── database/
│   ├── schema.sql
│   └── seed.sql
│
├── docs/
│   ├── FEATURE_MAP.md
│   ├── GITHUB_SETUP.md
│   ├── REVIEW1_CHECKLIST.md
│   └── VIVA_NOTES.md
│
├── mockups/
│   ├── admin.html
│   ├── feed.html
│   └── login.html
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/recipehub/
│       │       ├── controller/
│       │       ├── dao/
│       │       ├── exception/
│       │       ├── filter/
│       │       ├── model/
│       │       ├── service/
│       │       └── util/
│       │
│       └── webapp/
│           ├── WEB-INF/
│           │   └── views/
│           ├── css/
│           ├── js/
│           └── images/
│
├── pom.xml
├── README.md
└── .gitignore
```

---

## 🗄️ Database

The application uses a MySQL database named:

```text
recipehub
```

### Main Tables

```text
users
recipes
ingredients
instructions
reviews
collections
collection_items
browsing_history
messages
system_settings
```

### Important Relationships

```text
users
  │
  ├───────────────┐
  │               │
  ▼               ▼
recipes         reviews
  │
  ├── ingredients
  │
  └── instructions

users
  │
  ▼
collections
  │
  ▼
collection_items
  │
  ▼
recipes
```

Foreign keys and cascading relationships are used where appropriate to maintain referential integrity.

---

## 🔐 Database Configuration

Database credentials are intentionally **not stored in the Git repository**.

The application uses a local configuration file:

```text
src/main/java/com/recipehub/util/DBConfig.java
```

This file is excluded through `.gitignore`.

A safe configuration reference is provided as:

```text
src/main/java/com/recipehub/util/DBConfig.example.txt
```

### Local Configuration

Create or update `DBConfig.java` with your own local MySQL credentials:

```java
package com.recipehub.util;

public final class DBConfig {

    public static final String USER = "root";
    public static final String PASSWORD = "YOUR_MYSQL_PASSWORD";

    private DBConfig() {
    }
}
```

Do **not** commit your actual database password.

---

## ⚙️ Database Setup

### 1. Install MySQL

Install MySQL Server and MySQL Workbench.

### 2. Create the database

Open MySQL Workbench and execute:

```text
database/schema.sql
```

This creates the `recipehub` database and its tables.

### 3. Insert demo data

Execute:

```text
database/seed.sql
```

This inserts the demo users, recipes, reviews, collections, and related sample data.

### 4. Configure local credentials

Update your local:

```text
src/main/java/com/recipehub/util/DBConfig.java
```

with your MySQL username and password.

---

## 🔑 Demo Accounts

The seed database provides the following accounts:

| Role        | Email                   | Password      |
| ----------- | ----------------------- | ------------- |
| Admin       | `admin@recipehub.local` | `admin123`    |
| Contributor | `meera@recipehub.local` | `cook123`     |
| Explorer    | `rohan@recipehub.local` | `explorer123` |

These credentials are **demo application accounts**, not database credentials.

---

## 🚀 Running the Project

### Requirements

Install:

* JDK 17 or later
* Maven 3.9+
* MySQL 8+
* Apache Tomcat 10.1+
* IntelliJ IDEA or another Java IDE

### 1. Clone the repository

```bash
git clone https://github.com/Saif-Red/Online-Recipe-Sharing-Platform.git
cd Online-Recipe-Sharing-Platform
```

### 2. Configure MySQL

Run:

```text
database/schema.sql
database/seed.sql
```

Then configure your local `DBConfig.java`.

### 3. Build the project

Run:

```bash
mvn clean package
```

The generated WAR file will be:

```text
target/recipehub.war
```

### 4. Deploy to Tomcat

Copy:

```text
target/recipehub.war
```

into the Tomcat:

```text
webapps/
```

directory.

### 5. Start Tomcat

Start the Tomcat server.

### 6. Open the application

Visit:

```text
http://localhost:8080/recipehub/
```

---

## 🧪 Database Connectivity Test

A small utility class is included to verify JDBC connectivity before deployment:

```text
src/main/java/com/recipehub/util/ConnectionTest.java
```

Successful execution should produce output similar to:

```text
JDBC connection successful: MySQL
```

---

## 📊 Java Concepts Demonstrated

This project demonstrates several concepts relevant to the Java Programming evaluation.

### Object-Oriented Programming

* Classes and objects
* Encapsulation
* Interfaces
* Inheritance through servlet architecture
* Enums
* Constructors
* Method overriding
* Separation of responsibilities

### Collections & Generics

Examples include:

* `List<T>`
* `Set<T>`
* `HashSet<T>`
* Generic DAO interfaces
* Collections used for recipe ingredients, instructions, reviews, and user collections

### Exception Handling

The application uses custom exceptions including:

```text
DataAccessException
ValidationException
```

Database and validation failures are handled separately from normal application flow.

### JDBC

The application demonstrates:

* JDBC connection management
* `PreparedStatement`
* `ResultSet`
* SQL queries
* CRUD operations
* Transactions
* Commit and rollback
* Foreign-key based relational operations

### Transactions

Recipe creation with ingredients and instructions uses a database transaction so that related records are inserted consistently.

If an operation fails, the transaction is rolled back.

---

## 🔒 Security & Access Control

The project includes:

* Session-based authentication
* Role-based authorization
* Protected servlet paths
* Prepared statements for database queries
* Local-only database credentials
* `.gitignore` protection for credential files
* Input validation for recipe creation and other operations

The project is intended as an academic/demo application rather than a production-grade authentication system.

---

## 🖥️ Review 1 Deliverables

The repository also contains supporting Review 1 material:

```text
Online_Recipe_Sharing_Platform_Review1.pdf
Online_Recipe_Sharing_Platform_Review1.pptx
```

Additional documentation:

```text
docs/FEATURE_MAP.md
docs/GITHUB_SETUP.md
docs/REVIEW1_CHECKLIST.md
docs/VIVA_NOTES.md
```

These materials document the project structure, implemented functionality, GitHub setup, Review 1 preparation, and viva-related concepts.

---

## 📸 Screenshots & UI

The application includes responsive interfaces for:

* Login
* Recipe feed
* Search and cuisine filters
* Recipe details
* Ratings and reviews
* Collections
* Contributor dashboard
* Recipe creation
* Admin dashboard
* Recipe moderation

Screenshots can be added to this README as the project progresses.

---

## 🔮 Future Enhancements

Potential future improvements include:

* User profile editing
* Complete contributor recipe editing
* Browsing history interface
* Messaging between users
* Advanced recipe filtering
* Recipe image upload
* Improved analytics
* Pagination
* Password hashing and stronger authentication
* Email verification
* Production deployment
* REST API integration
* Additional administrative settings

---

## 📈 Project Status

**Review 1 implementation: Completed**

Current implementation includes:

* Project architecture
* MySQL database
* JDBC integration
* Authentication
* Role-based authorization
* Recipe discovery
* Search and filtering
* Recipe details
* Ingredients and instructions
* Ratings and reviews
* Personal collections
* Contributor recipe creation
* Admin moderation
* Responsive UI
* GitHub repository
* Review 1 documentation

Further features can be added during subsequent development phases without replacing the core architecture.

---

## 👨‍💻 Author

**Mohd Saif Ansari**

B.Tech CSE (AI-ML)

This project was developed as part of the Java Programming course project evaluation.

---

## 📜 License

This project is intended for educational and academic purposes.

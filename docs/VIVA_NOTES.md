# Quick viva notes

## Why did you choose a web-based project?

The product idea behaves like a social recipe community, so a web interface is more natural for feeds, recipe cards, dashboards and responsive screens. The university's Web-based rubric also directly evaluates solution design, core Java, JDBC and Servlets/Web Integration.

## Why JSP + Servlets?

Servlets handle HTTP requests and responses. JSP is used for the view layer. This keeps the project close to the Java concepts taught in the course instead of hiding the work behind a large framework.

## Why JDBC?

JDBC gives direct Java-to-MySQL access and lets the project demonstrate SQL, PreparedStatement, ResultSet and CRUD operations clearly.

## What is the role of DAO classes?

DAO classes isolate database code from the rest of the application. For example, UserDAO handles user queries and RecipeDAO handles recipe queries.

## Why use PreparedStatement?

It keeps SQL parameters separate from the query string, improves readability and avoids building SQL by concatenating user input.

## Where is OOP used?

Model classes such as User and Recipe encapsulate data. BaseServlet is inherited by concrete servlets. CrudDAO<T> is a generic interface shared by different repositories. Enums model fixed values such as roles and recipe status.

## Where are collections and generics used?

DAOs return List<User> and List<Recipe>. CrudDAO<T> defines a reusable generic contract.

## Where are threads used?

StatsService uses ExecutorService with a small fixed thread pool to process view-count updates asynchronously. The intent is to keep page rendering independent from non-critical statistics work.

## How are sessions used?

After successful login, the User object is stored in the HTTP session as `loggedInUser`. Role-specific routes are protected by AuthFilter.

## How do the three roles differ?

Admin manages users, recipe moderation and system settings. Contributor manages personal recipes and contributor analytics. Explorer discovers recipes, rates/reviews them and manages collections/history.

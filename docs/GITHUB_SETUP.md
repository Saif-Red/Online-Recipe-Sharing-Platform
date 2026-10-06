# GitHub setup for Review 1

Create a public repository named:

`Online-Recipe-Sharing-Platform`

From the project folder in PowerShell:

```powershell
git init
git branch -M main
git add .
git commit -m "Review 1: project foundation, database design and web UI"
git remote add origin https://github.com/<your-username>/Online-Recipe-Sharing-Platform.git
git push -u origin main
```

Before `git add .`, make sure no personal credentials are stored in the repository. The supplied `DBConnection.java` contains only a demo local password value and must not be replaced with your real password before pushing.

For the repository description, use:

> Java Servlet, JSP, JDBC and MySQL based online recipe sharing platform with Admin, Recipe Contributor and Recipe Explorer roles.

Suggested repository topics:

`java` `servlets` `jsp` `jdbc` `mysql` `maven` `web-application` `recipe-platform`

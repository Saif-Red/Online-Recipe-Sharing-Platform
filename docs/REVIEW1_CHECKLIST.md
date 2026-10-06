# Review 1 submission checklist

## Before pushing to GitHub

- [ ] Run `database/schema.sql` in MySQL.
- [ ] Run `database/seed.sql` in MySQL.
- [ ] Update the local MySQL password in `DBConnection.java`.
- [ ] Open the project in IntelliJ IDEA / Eclipse.
- [ ] Run `mvn clean package`.
- [ ] Deploy `target/recipehub.war` to Tomcat.
- [ ] Verify login for Admin, Contributor and Explorer accounts.
- [ ] Verify the feed loads recipe data from MySQL.
- [ ] Verify a recipe detail page opens from the database.
- [ ] Verify role-specific dashboard links.
- [ ] Remove any local credentials, screenshots with private data, and IDE files.
- [ ] Commit and push the final Review 1 version.

## Presentation

Use the prepared presentation and add your team members / roll numbers on the title slide if required by your portal.

Recommended evidence to show during submission or viva:

1. Login screen
2. Recipe feed
3. Admin dashboard
4. Architecture diagram
5. ER/database design
6. GitHub repository tree + README

## Important wording

Call this the **Review 1 foundation / prototype**, not the final finished application. The database and architecture are intentionally prepared so the remaining features can be added for Review 2.

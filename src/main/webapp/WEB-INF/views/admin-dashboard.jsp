<%@ page import="java.util.List" %>
<%@ page import="com.recipehub.model.User" %>
<%@ page import="com.recipehub.model.Recipe" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    List<User> users = (List<User>) request.getAttribute("users");
    List<Recipe> recipes = (List<Recipe>) request.getAttribute("recipes");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - RecipeHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="partials/header.jsp" %>
<main class="page-shell">
    <section class="hero-row">
        <div><p class="eyebrow">ADMIN CONTROL CENTER</p><h1>Keep the community healthy.</h1><p class="muted">Manage accounts, review recipes and control system settings.</p></div>
        <span class="pill dark">Administrator</span>
    </section>
    <div class="stats-grid">

        <div class="stat-card">
            <span>Total users</span>
            <strong>
                <%= users == null ? 0 : users.size() %>
            </strong>
            <small>Across all roles</small>
        </div>

        <div class="stat-card">
            <span>Recipes in queue</span>
            <strong>
                <%= request.getAttribute("pendingCount") == null
                        ? 0
                        : request.getAttribute("pendingCount") %>
            </strong>
            <small>Waiting for approval</small>
        </div>

        <div class="stat-card">
            <span>Published</span>
            <strong>
                <%= request.getAttribute("publishedCount") == null
                        ? 0
                        : request.getAttribute("publishedCount") %>
            </strong>
            <small>Approved recipes</small>
        </div>

        <div class="stat-card">
            <span>Reviews</span>
            <strong>
                <%= request.getAttribute("reviewCount") == null
                        ? 0
                        : request.getAttribute("reviewCount") %>
            </strong>
            <small>Community feedback</small>
        </div>

    </div>

    <section class="table-section">
        <div class="section-head"><div><h2>Recipe moderation</h2><p class="muted small">Review submitted recipes before publishing.</p></div><button class="btn secondary">System settings</button></div>
        <div class="table-wrap">
            <table>
                <thead><tr><th>Recipe</th><th>Contributor</th><th>Status</th><th>Views</th><th>Action</th></tr></thead>
                <tbody>
                <% if (recipes != null) for (Recipe r : recipes) { %>
                    <tr>
                        <td>
                            <strong><%= r.getTitle() %></strong>
                            <small><%= r.getCuisine() %></small>
                        </td>

                        <td>
                            <%= r.getAuthorName() %>
                        </td>

                        <td>
                            <span class="status <%= r.getStatus().name().toLowerCase() %>">
                                <%= r.getStatus().name() %>
                            </span>
                        </td>

                        <td>
                            <%= r.getViews() %>
                        </td>

                        <td>
                            <% if ("PENDING".equals(r.getStatus().name())) { %>

                                <form
                                        method="post"
                                        action="${pageContext.request.contextPath}/admin/moderate-recipe"
                                        style="display:inline;"
                                >
                                    <input
                                            type="hidden"
                                            name="recipeId"
                                            value="<%= r.getId() %>"
                                    >

                                    <input
                                            type="hidden"
                                            name="status"
                                            value="APPROVED"
                                    >

                                    <button
                                            type="submit"
                                            class="moderation-btn approve-btn"
                                    >
                                        Approve
                                    </button>
                                </form>

                                <form
                                        method="post"
                                        action="${pageContext.request.contextPath}/admin/moderate-recipe"
                                        style="display:inline;"
                                >
                                    <input
                                            type="hidden"
                                            name="recipeId"
                                            value="<%= r.getId() %>"
                                    >

                                    <input
                                            type="hidden"
                                            name="status"
                                            value="REJECTED"
                                    >

                                    <button
                                            type="submit"
                                            class="moderation-btn reject-btn"
                                    >
                                        Reject
                                    </button>
                                </form>

                            <% } else { %>

                                <span class="muted small">
                                    Reviewed
                                </span>

                            <% } %>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </section>

    <section class="table-section">
        <div class="section-head"><div><h2>User management</h2><p class="muted small">Roles can be changed by an administrator.</p></div><button class="btn primary">Add user</button></div>
        <div class="table-wrap">
            <table>
                <thead><tr><th>Name</th><th>Email</th><th>Role</th><th>Account</th><th>Action</th></tr></thead>
                <tbody>
                <% if (users != null) for (User u : users) { %>
                    <tr><td><strong><%= u.getName() %></strong></td><td><%= u.getEmail() %></td><td><span class="role-badge"><%= u.getRole().name() %></span></td><td><%= u.isActive() ? "Active" : "Disabled" %></td><td><button class="text-btn">Edit</button></td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </section>
</main>
<%@ include file="partials/footer.jsp" %>
</body>
</html>

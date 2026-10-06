<%@ page import="java.util.List" %>
<%@ page import="com.recipehub.model.Recipe" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    String cuisine = request.getParameter("cuisine");
    String search = request.getParameter("q");
    String searchParam = (search == null || search.isBlank())
            ? ""
            : "&q=" + java.net.URLEncoder.encode(search, "UTF-8");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Explore - RecipeHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="partials/header.jsp" %>
<main class="page-shell">
    <section class="hero-row">
        <div>
            <p class="eyebrow">EXPLORE RECIPES</p>
            <h1>What's cooking today?</h1>
            <p class="muted">Find something quick for tonight or save an idea for later.</p>
        </div>
        <div class="cuisine-filters">

            <a
                    class="chip <%= (cuisine == null || cuisine.isBlank()) ? "active" : "" %>"
                    href="${pageContext.request.contextPath}/feed?q=<%= search == null ? "" : java.net.URLEncoder.encode(search, "UTF-8") %>"
            >
                All
            </a>

            <a
                    class="chip <%= "Indian".equalsIgnoreCase(cuisine) ? "active" : "" %>"
                    href="${pageContext.request.contextPath}/feed?cuisine=Indian<%= searchParam %>"
            >
                Indian
            </a>

            <a
                    class="chip <%= "Italian".equalsIgnoreCase(cuisine) ? "active" : "" %>"
                    href="${pageContext.request.contextPath}/feed?cuisine=Italian<%= searchParam %>"
            >
                Italian
            </a>

            <a
                    class="chip <%= "Dessert".equalsIgnoreCase(cuisine) ? "active" : "" %>"
                    href="${pageContext.request.contextPath}/feed?cuisine=Dessert<%= searchParam %>"
            >
                Dessert
            </a>

        </div>
    </section>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert error"><%= request.getAttribute("error") %></div>
    <% } %>

    <div class="content-grid">
        <section>
            <div class="section-head">
                <div><h2>Fresh from the community</h2><p class="muted small">Recently approved recipes</p></div>
                <span class="pill">Community feed</span>
            </div>
            <div class="recipe-grid">
                <%
                    List<Recipe> recipes = (List<Recipe>) request.getAttribute("recipes");
                    if (recipes != null && !recipes.isEmpty()) {
                        for (Recipe recipe : recipes) {
                %>
                <article class="recipe-card">
                    <a href="${pageContext.request.contextPath}/recipe?id=<%= recipe.getId() %>">
                        <img class="recipe-image" src="<%= recipe.getImageUrl() %>" alt="<%= recipe.getTitle() %>">
                    </a>
                    <div class="recipe-card-body">
                        <div class="meta-row"><span><%= recipe.getCuisine() %></span><span><%= recipe.getDifficulty().name().toLowerCase() %></span></div>
                        <h3><a href="${pageContext.request.contextPath}/recipe?id=<%= recipe.getId() %>"><%= recipe.getTitle() %></a></h3>
                        <p class="muted two-line"><%= recipe.getDescription() %></p>
                        <div class="card-footer">
                            <span>★ <%= String.format("%.1f", recipe.getAverageRating()) %></span>
                            <span><%= recipe.getViews() %> views</span>
                        </div>
                    </div>
                </article>
                <%      }
                    } else { %>
                <div class="empty-state">No recipes matched your search.</div>
                <% } %>
            </div>
        </section>

        <aside class="sidebar-stack">
            <div class="side-card">
                <p class="eyebrow">PLATFORM IDEA</p>
                <h3>Three roles, one community.</h3>
                <div class="role-line"><span class="dot admin"></span><div><strong>Admin</strong><small>Moderation & settings</small></div></div>
                <div class="role-line"><span class="dot contributor"></span><div><strong>Contributor</strong><small>Share & manage recipes</small></div></div>
                <div class="role-line"><span class="dot explorer"></span><div><strong>Explorer</strong><small>Discover & collect</small></div></div>
            </div>
            <div class="side-card muted-box">
                <p class="eyebrow">COMING TO FINAL BUILD</p>
                <p>Ratings, reviews, collections, messages, approval workflow and contributor analytics.</p>
            </div>
        </aside>
    </div>
</main>
<%@ include file="partials/footer.jsp" %>
</body>
</html>

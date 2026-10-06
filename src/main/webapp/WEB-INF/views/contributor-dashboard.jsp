<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>My Kitchen - RecipeHub</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"></head>
<body>
<%@ include file="partials/header.jsp" %>
<main class="page-shell">
    <section class="hero-row"><div><p class="eyebrow">CONTRIBUTOR DASHBOARD</p><h1>Welcome to your kitchen.</h1><p class="muted">Publish recipes, watch how people respond and keep your profile up to date.</p></div><a
                                                                                                                                                                                                                              href="${pageContext.request.contextPath}/contributor/create-recipe"
                                                                                                                                                                                                                              class="btn primary"
                                                                                                                                                                                                                      >
                                                                                                                                                                                                                          + Create recipe
                                                                                                                                                                                                                      </a></section>
    <div class="stats-grid"><div class="stat-card"><span>Recipe views</span><strong>2,481</strong><small>+12% this month</small></div><div class="stat-card"><span>Average rating</span><strong>4.7</strong><small>Across 8 published recipes</small></div><div class="stat-card"><span>Reviews</span><strong>53</strong><small>Reader feedback</small></div><div class="stat-card"><span>Followers</span><strong>126</strong><small>Community members</small></div></div>
    <section class="dashboard-two-col">

        <div class="table-section">

            <div class="section-head">
                <div>
                    <h2>My recipes</h2>
                    <p class="muted small">
                        Manage your submitted recipes.
                    </p>
                </div>
            </div>

            <div class="simple-list">

                <%
                    java.util.List<com.recipehub.model.Recipe> myRecipes =
                            (java.util.List<com.recipehub.model.Recipe>)
                                    request.getAttribute("myRecipes");
                %>

                <% if (myRecipes == null || myRecipes.isEmpty()) { %>

                    <div>
                        <strong>No recipes yet</strong>
                        <span>
                            Create your first recipe to start sharing with the community.
                        </span>
                    </div>

                <% } else { %>

                    <% for (com.recipehub.model.Recipe recipe : myRecipes) { %>

                        <div>
                            <strong>
                                <%= recipe.getTitle() %>
                            </strong>

                            <span class="recipe-meta">

                                <span class="status <%= recipe.getStatus().name().toLowerCase() %>">
                                    <%= recipe.getStatus().name() %>
                                </span>

                                <span>
                                    <%= String.format("%.1f", recipe.getAverageRating()) %>★
                                </span>

                                <span>
                                    <%= recipe.getViews() %> views
                                </span>

                            </span>

                            <a
                                    class="text-btn"
                                    href="${pageContext.request.contextPath}/recipe?id=<%= recipe.getId() %>"
                            >
                                View
                            </a>
                        </div>

                    <% } %>

                <% } %>

            </div>
        </div>

        <div class="side-card">

            <p class="eyebrow">CONTRIBUTOR INSIGHTS</p>

            <h3>Your recipe overview</h3>

            <%
                int totalRecipes =
                        myRecipes == null ? 0 : myRecipes.size();

                long approvedRecipes =
                        myRecipes == null
                                ? 0
                                : myRecipes.stream()
                                        .filter(recipe ->
                                                "APPROVED".equals(
                                                        recipe.getStatus().name()
                                                )
                                        )
                                        .count();

                long pendingRecipes =
                        myRecipes == null
                                ? 0
                                : myRecipes.stream()
                                        .filter(recipe ->
                                                "PENDING".equals(
                                                        recipe.getStatus().name()
                                                )
                                        )
                                        .count();

                int totalViews = 0;

                if (myRecipes != null) {
                    for (com.recipehub.model.Recipe recipe : myRecipes) {
                        totalViews += recipe.getViews();
                    }
                }
            %>

            <div class="insight-row">
                <span>Total recipes</span>
                <strong><%= totalRecipes %></strong>
            </div>

            <div class="insight-row">
                <span>Published recipes</span>
                <strong><%= approvedRecipes %></strong>
            </div>

            <div class="insight-row">
                <span>Awaiting approval</span>
                <strong><%= pendingRecipes %></strong>
            </div>

            <div class="insight-row">
                <span>Total views</span>
                <strong><%= totalViews %></strong>
            </div>

        </div>

    </section>
</main>
<%@ include file="partials/footer.jsp" %>
</body>
</html>

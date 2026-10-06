<%@ page import="com.recipehub.model.Collection" %>
<%@ page import="com.recipehub.model.Recipe" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
Collection collection =
        (Collection) request.getAttribute("collection");

List<Recipe> recipes =
        (List<Recipe>) request.getAttribute("recipes");

if (recipes == null) {
    recipes = List.of();
}

String flashMessage =
        (String) session.getAttribute("flashMessage");

session.removeAttribute("flashMessage");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0"
    >

    <title>
        <%= collection.getName() %> - RecipeHub
    </title>

    <link
            rel="stylesheet"
            href="${pageContext.request.contextPath}/css/style.css"
    >

</head>

<body>

<%@ include file="partials/header.jsp" %>

<main class="page-shell narrow">

    <a
            href="${pageContext.request.contextPath}/collections"
            class="back-link"
    >
        ← Back to my collections
    </a>


    <% if (flashMessage != null) { %>

        <div class="success-message">
            <%= flashMessage %>
        </div>

    <% } %>


    <section class="collection-detail-header">

        <div class="collection-detail-icon">
            ♡
        </div>

        <div>

            <p class="eyebrow">
                Your collection
            </p>

            <h1>
                <%= collection.getName() %>
            </h1>

            <% if (collection.getDescription() != null
                    && !collection.getDescription().isBlank()) { %>

                <p class="muted">
                    <%= collection.getDescription() %>
                </p>

            <% } %>

            <span class="collection-meta">
                <%= recipes.size() %>
                saved recipe<%= recipes.size() == 1 ? "" : "s" %>
            </span>

        </div>

    </section>


    <% if (recipes.isEmpty()) { %>

        <section class="detail-panel empty-collection-detail">

            <div class="empty-collection-large-icon">
                ♡
            </div>

            <h2>
                No saved recipes yet
            </h2>

            <p class="muted">
                Recipes you save to this collection will appear here.
            </p>

            <a
                    href="${pageContext.request.contextPath}/feed"
                    class="btn primary"
            >
                Explore recipes
            </a>

        </section>

    <% } else { %>

        <section class="saved-recipes-grid">

            <% for (Recipe recipe : recipes) { %>

                <article class="saved-recipe-card">

                    <img
                            src="<%= recipe.getImageUrl() %>"
                            alt="<%= recipe.getTitle() %>"
                            class="saved-recipe-image"
                    >

                    <div class="saved-recipe-content">

                        <p class="eyebrow">
                            <%= recipe.getCuisine() %>
                            ·
                            <%= recipe.getDifficulty()
                                    .name()
                                    .toLowerCase() %>
                        </p>

                        <h2>
                            <%= recipe.getTitle() %>
                        </h2>

                        <p class="saved-recipe-description">
                            <%= recipe.getDescription() %>
                        </p>

                        <div class="saved-recipe-meta">

                            <span>
                                ★
                                <%= String.format(
                                        "%.1f",
                                        recipe.getAverageRating()
                                ) %>
                            </span>

                            <span>
                                <%= recipe.getPrepMinutes()
                                        + recipe.getCookMinutes() %>
                                min
                            </span>

                            <span>
                                <%= recipe.getServings() %>
                                servings
                            </span>

                        </div>

                        <div class="saved-recipe-actions">

                            <a
                                    href="${pageContext.request.contextPath}/recipe?id=<%= recipe.getId() %>"
                                    class="btn secondary"
                            >
                                View recipe
                            </a>

                            <form
                                    method="post"
                                    action="${pageContext.request.contextPath}/collection"
                                    onsubmit="return confirm('Remove this recipe from the collection?');"
                            >
                                <input
                                        type="hidden"
                                        name="action"
                                        value="remove"
                                >

                                <input
                                        type="hidden"
                                        name="collectionId"
                                        value="<%= collection.getId() %>"
                                >

                                <input
                                        type="hidden"
                                        name="recipeId"
                                        value="<%= recipe.getId() %>"
                                >

                                <button
                                        type="submit"
                                        class="btn remove-recipe-btn"
                                >
                                    Remove
                                </button>
                            </form>

                        </div>

                    </div>

                </article>

            <% } %>

        </section>

    <% } %>

</main>

<%@ include file="partials/footer.jsp" %>

<script
        src="${pageContext.request.contextPath}/js/app.js">
</script>

</body>

</html>
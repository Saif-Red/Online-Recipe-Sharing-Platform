<%@ page import="com.recipehub.model.Collection" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
List<Collection> collections =
        (List<Collection>) request.getAttribute("collections");

if (collections == null) {
    collections = List.of();
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

    <title>My Collections - RecipeHub</title>

    <link
            rel="stylesheet"
            href="${pageContext.request.contextPath}/css/style.css"
    >

</head>

<body>

<%@ include file="partials/header.jsp" %>

<main class="page-shell narrow">

    <a
            href="${pageContext.request.contextPath}/feed"
            class="back-link"
    >
        ← Back to explore
    </a>


    <% if (flashMessage != null) { %>

        <div class="success-message">
            <%= flashMessage %>
        </div>

    <% } %>


    <section class="collections-page-header">

        <div>

            <p class="eyebrow">
                Your library
            </p>

            <h1>
                My Collections
            </h1>

            <p class="muted">
                Organize your favorite recipes into personal collections.
            </p>

        </div>

        <span class="collection-count">
            <%= collections.size() %>
            collection<%= collections.size() == 1 ? "" : "s" %>
        </span>

    </section>


    <% if (collections.isEmpty()) { %>

        <section class="detail-panel empty-collections-page">

            <h2>
                Your library is empty
            </h2>

            <p class="muted">
                Create a collection from a recipe page to start saving recipes.
            </p>

            <a
                    href="${pageContext.request.contextPath}/feed"
                    class="btn primary"
            >
                Explore recipes
            </a>

        </section>

    <% } else { %>

        <section class="collections-page-grid">

            <% for (Collection collection : collections) { %>

                <article class="collection-page-card">

                    <div class="collection-page-icon">
                        ♡
                    </div>

                    <div class="collection-page-content">

                        <h2>
                            <a
                                    href="${pageContext.request.contextPath}/collection-details?id=<%= collection.getId() %>"
                                    class="collection-title-link"
                            >
                                <%= collection.getName() %>
                            </a>
                        </h2>

                        <% if (collection.getDescription() != null
                                && !collection.getDescription().isBlank()) { %>

                            <p class="muted">
                                <%= collection.getDescription() %>
                            </p>

                        <% } else { %>

                            <p class="muted">
                                No description added.
                            </p>

                        <% } %>

                        <span class="collection-meta">
                            Created
                            <%= collection.getCreatedAt() != null
                                    ? collection.getCreatedAt().toLocalDate()
                                    : "" %>
                        </span>

                        <form
                                method="post"
                                action="${pageContext.request.contextPath}/collection"
                                class="delete-collection-form"
                                onsubmit="return confirm('Delete this collection? Saved recipes will be removed from this collection.');"
                        >

                            <input
                                    type="hidden"
                                    name="action"
                                    value="deleteCollection"
                            >

                            <input
                                    type="hidden"
                                    name="collectionId"
                                    value="<%= collection.getId() %>"
                            >

                            <button
                                    type="submit"
                                    class="delete-collection-btn"
                            >
                                Delete collection
                            </button>

                        </form>

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
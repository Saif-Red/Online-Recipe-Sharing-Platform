<%@ page import="com.recipehub.model.Collection" %>
<%@ page import="com.recipehub.model.Recipe" %>
<%@ page import="com.recipehub.model.Ingredient" %>
<%@ page import="com.recipehub.model.Review" %>
<%@ page import="com.recipehub.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
Recipe recipe = (Recipe) request.getAttribute("recipe");
List<Review> reviews = (List<Review>) request.getAttribute("reviews");

List<Ingredient> ingredients =
        (List<Ingredient>) request.getAttribute("ingredients");

List<String> instructions =
        (List<String>) request.getAttribute("instructions");

List<Collection> collections =
        (List<Collection>) request.getAttribute("collections");

Set<Integer> savedCollectionIds =
        (Set<Integer>) request.getAttribute("savedCollectionIds");

if (savedCollectionIds == null) {
    savedCollectionIds = Set.of();
}

if (collections == null) {
    collections = List.of();
}

if (reviews == null) {
    reviews = List.of();
}

if (ingredients == null) {
    ingredients = List.of();
}

if (instructions == null) {
    instructions = List.of();
}

Review userReview = null;

User recipeUser = (User) session.getAttribute("loggedInUser");

if (recipeUser != null) {
    for (Review review : reviews) {
        if (review.getUserId() == recipeUser.getId()) {
            userReview = review;
            break;
        }
    }
}

String flashMessage = (String) session.getAttribute("flashMessage");
session.removeAttribute("flashMessage");


%>

<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= recipe.getTitle() %> - RecipeHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="partials/header.jsp" %>

<main class="page-shell narrow">


<a href="${pageContext.request.contextPath}/feed" class="back-link">
    ← Back to explore
</a>

<% if (flashMessage != null) { %>
    <div class="success-message">
        <%= flashMessage %>
    </div>
<% } %>

<section class="recipe-detail">

    <img
            class="detail-image"
            src="<%= recipe.getImageUrl() %>"
            alt="<%= recipe.getTitle() %>"
    >

    <div class="detail-copy">

        <p class="eyebrow">
            <%= recipe.getCuisine() %>
            ·
            <%= recipe.getDifficulty().name().toLowerCase() %>
        </p>

        <h1><%= recipe.getTitle() %></h1>

        <p class="muted">
            By <strong><%= recipe.getAuthorName() %></strong>
        </p>

        <p class="detail-desc">
            <%= recipe.getDescription() %>
        </p>

        <div class="quick-stats">

            <div>
                <strong>
                    <%= recipe.getPrepMinutes() %>
                    +
                    <%= recipe.getCookMinutes() %>
                    min
                </strong>
                <span>Total prep + cook</span>
            </div>

            <div>
                <strong><%= recipe.getServings() %></strong>
                <span>Servings</span>
            </div>

            <div>
                <strong>
                    ★ <%= String.format("%.1f", recipe.getAverageRating()) %>
                </strong>
                <span>Rating</span>
            </div>

        </div>

        <div class="detail-actions">

            <% if (recipeUser != null) { %>

                <button
                        class="btn primary"
                        type="button"
                        onclick="document.getElementById('collection-section').scrollIntoView({behavior:'smooth'})"
                >
                    Save to collection
                </button>

            <% } else { %>

                <a
                        class="btn primary"
                        href="${pageContext.request.contextPath}/login.jsp"
                >
                    Log in to save
                </a>

            <% } %>

            <button class="btn secondary" type="button"
                    onclick="document.getElementById('review-section').scrollIntoView({behavior:'smooth'})">
                Rate recipe
            </button>

        </div>

    </div>
</section>

<section class="detail-columns">

    <div class="detail-panel">

        <h2>Ingredients</h2>

        <% if (ingredients.isEmpty()) { %>

            <p class="muted">
                No ingredients have been added yet.
            </p>

        <% } else { %>

            <ul class="ingredient-list">

                <% for (Ingredient ingredient : ingredients) { %>

                    <li>
                        <span><%= ingredient.getQuantity() %></span>
                        <strong><%= ingredient.getItemName() %></strong>
                    </li>

                <% } %>

            </ul>

        <% } %>

    </div>

    <div class="detail-panel">

        <h2>Method</h2>

        <% if (instructions.isEmpty()) { %>

            <p class="muted">
                No cooking instructions have been added yet.
            </p>

        <% } else { %>

            <ol class="step-list">

                <% for (String instruction : instructions) { %>

                    <li><%= instruction %></li>

                <% } %>

            </ol>

        <% } %>

    </div>

</section>

<section class="detail-panel review-section" id="review-section">

<% if (recipeUser != null) { %>

<section class="detail-panel collection-section" id="collection-section">

    <div class="section-heading">

        <div>
            <p class="eyebrow">Your library</p>
            <h2>Save this recipe</h2>
        </div>

        <span class="muted">
            <%= collections.size() %> collection<%= collections.size() == 1 ? "" : "s" %>
        </span>

    </div>


    <% if (collections.isEmpty()) { %>

        <div class="empty-collection-state">

            <p>
                You don't have any collections yet.
            </p>

            <p class="muted">
                Create your first collection to start saving recipes.
            </p>

            <form
                    method="post"
                    action="${pageContext.request.contextPath}/collection"
                    class="create-collection-form"
            >

                <input
                        type="hidden"
                        name="action"
                        value="create"
                >

                <input
                        type="text"
                        name="name"
                        placeholder="Collection name"
                        maxlength="100"
                        required
                >

                <input
                        type="text"
                        name="description"
                        placeholder="Description (optional)"
                        maxlength="300"
                >

                <button class="btn primary" type="submit">
                    Create collection
                </button>

            </form>

        </div>


    <% } else { %>

        <div class="collection-save-grid">

            <% for (Collection collection : collections) { %>

                <div class="collection-card">

                    <div class="collection-card-copy">

                        <h3>
                            <%= collection.getName() %>
                        </h3>

                        <% if (collection.getDescription() != null
                                && !collection.getDescription().isBlank()) { %>

                            <p class="muted">
                                <%= collection.getDescription() %>
                            </p>

                        <% } %>

                    </div>

                    <form
                            method="post"
                            action="${pageContext.request.contextPath}/collection"
                    >

                        <input
                                type="hidden"
                                name="action"
                                value="save"
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

                        <%
                        boolean alreadySaved =
                                savedCollectionIds.contains(collection.getId());
                        %>

                        <% if (alreadySaved) { %>

                            <button
                                    class="btn saved-recipe-btn"
                                    type="button"
                                    disabled
                            >
                                ✓ Saved
                            </button>

                        <% } else { %>

                            <button
                                    class="btn secondary"
                                    type="submit"
                            >
                                Save here
                            </button>

                        <% } %>

                    </form>

                </div>

            <% } %>

        </div>


        <div class="create-collection-inline">

            <h3>Create another collection</h3>

            <form
                    method="post"
                    action="${pageContext.request.contextPath}/collection"
                    class="create-collection-form"
            >

                <input
                        type="hidden"
                        name="action"
                        value="create"
                >

                <input
                        type="text"
                        name="name"
                        placeholder="e.g. Weekend Favorites"
                        maxlength="100"
                        required
                >

                <input
                        type="text"
                        name="description"
                        placeholder="What is this collection for?"
                        maxlength="300"
                >

                <button class="btn secondary" type="submit">
                    + Create
                </button>

            </form>

        </div>

    <% } %>

</section>

<% } %>

    <div class="section-heading">
        <div>
            <p class="eyebrow">Community feedback</p>
            <h2>Ratings & Reviews</h2>
        </div>

        <strong>
            ★ <%= String.format("%.1f", recipe.getAverageRating()) %>
            / 5
        </strong>
    </div>

    <% if (recipeUser != null) { %>

        <div class="review-form-card">

            <h3>
                <%= userReview == null
                        ? "Share your experience"
                        : "Update your review" %>
            </h3>

            <form method="post"
                  action="${pageContext.request.contextPath}/review"
                  class="modern-review-form">

                <input
                        type="hidden"
                        name="recipeId"
                        value="<%= recipe.getId() %>"
                >

                <div class="rating-input">

                    <div class="input-label">
                        <span>How would you rate this recipe?</span>
                        <span class="rating-value" id="ratingValue">
                            <%= userReview != null
                                    ? userReview.getRating() + " / 5"
                                    : "Select a rating" %>
                        </span>
                    </div>

                    <div class="star-rating" role="radiogroup" aria-label="Recipe rating">

                        <% for (int rating = 1; rating <= 5; rating++) { %>

                            <input
                                    type="radio"
                                    id="star<%= rating %>"
                                    name="rating"
                                    value="<%= rating %>"
                                    <%= userReview != null
                                            && userReview.getRating() == rating
                                            ? "checked"
                                            : "" %>
                                    required
                            >

                            <label
                                    for="star<%= rating %>"
                                    title="<%= rating %> star<%= rating == 1 ? "" : "s" %>"
                            >
                                ★
                            </label>

                        <% } %>

                    </div>

                </div>

                <div class="review-text-input">

                    <div class="input-label">
                        <label for="reviewText">Your review</label>
                        <span id="characterCount">0 / 1000</span>
                    </div>

                    <textarea
                            id="reviewText"
                            name="reviewText"
                            rows="5"
                            maxlength="1000"
                            placeholder="Tell the community what you thought about this recipe..."
                    ><%= userReview != null
                            && userReview.getReviewText() != null
                            ? userReview.getReviewText()
                            : "" %></textarea>

                </div>

                <div class="review-form-footer">

                    <p class="review-hint">
                        Your rating helps other food lovers discover great recipes.
                    </p>

                    <button class="btn primary review-submit" type="submit">
                        <%= userReview == null
                                ? "Submit Review"
                                : "Update Review" %>
                    </button>

                </div>

            </form>

        </div>

    <% } else { %>

        <p class="muted">
            Please log in to rate this recipe and write a review.
        </p>

    <% } %>

    <div class="reviews-list">

        <% if (reviews.isEmpty()) { %>

            <p class="muted">
                No reviews yet. Be the first to share your experience!
            </p>

        <% } else { %>

            <% for (Review review : reviews) { %>

                <article class="review-card">

                    <div class="review-header">

                        <div>
                            <strong><%= review.getUserName() %></strong>

                            <div class="review-rating">
                                <%
                                    for (int star = 1; star <= 5; star++) {
                                %>
                                    <%= star <= review.getRating() ? "★" : "☆" %>
                                <%
                                    }
                                %>
                            </div>
                        </div>

                        <% if (review.getCreatedAt() != null) { %>
                            <span class="muted">
                                <%= review.getCreatedAt()
                                        .toLocalDate()
                                        .toString() %>
                            </span>
                        <% } %>

                    </div>

                    <% if (review.getReviewText() != null
                            && !review.getReviewText().isBlank()) { %>

                        <p class="review-text">
                            <%= review.getReviewText() %>
                        </p>

                    <% } %>

                </article>

            <% } %>

        <% } %>

    </div>

</section>

</main>

<%@ include file="partials/footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/app.js"></script>

</body>
</html>

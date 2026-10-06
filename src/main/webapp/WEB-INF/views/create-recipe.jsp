<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Create Recipe - RecipeHub</title>

    <link
            rel="stylesheet"
            href="${pageContext.request.contextPath}/css/style.css"
    >
</head>

<body>

<%@ include file="partials/header.jsp" %>

<main class="page-shell narrow">

    <a
            href="${pageContext.request.contextPath}/contributor/dashboard"
            class="back-link"
    >
        ← Back to dashboard
    </a>

    <section class="form-page-header">

        <p class="eyebrow">CONTRIBUTOR</p>

        <h1>Create a new recipe</h1>

        <p class="muted">
            Share your recipe with the RecipeHub community.
            New recipes will be submitted for admin review.
        </p>

    </section>


    <section class="detail-panel recipe-form-panel">

        <form
                method="post"
                action="${pageContext.request.contextPath}/contributor/create-recipe"
                class="recipe-form"
        >

            <div class="form-section">

                <div class="form-section-heading">
                    <h2>Recipe information</h2>
                    <p class="muted small">
                        Start with the basic details of your recipe.
                    </p>
                </div>


                <div class="form-group">

                    <label for="title">
                        Recipe title
                    </label>

                    <input
                            type="text"
                            id="title"
                            name="title"
                            maxlength="180"
                            placeholder="e.g. Creamy Garlic Pasta"
                            required
                    >

                </div>


                <div class="form-group">

                    <label for="description">
                        Description
                    </label>

                    <textarea
                            id="description"
                            name="description"
                            rows="5"
                            maxlength="1000"
                            placeholder="Tell people what makes this recipe special..."
                            required
                    ></textarea>

                </div>


                <div class="form-row">

                    <div class="form-group">

                        <label for="prepMinutes">
                            Preparation time
                        </label>

                        <div class="input-with-suffix">

                            <input
                                    type="number"
                                    id="prepMinutes"
                                    name="prepMinutes"
                                    min="0"
                                    max="1440"
                                    placeholder="15"
                                    required
                            >

                            <span>min</span>

                        </div>

                    </div>


                    <div class="form-group">

                        <label for="cookMinutes">
                            Cooking time
                        </label>

                        <div class="input-with-suffix">

                            <input
                                    type="number"
                                    id="cookMinutes"
                                    name="cookMinutes"
                                    min="0"
                                    max="1440"
                                    placeholder="20"
                                    required
                            >

                            <span>min</span>

                        </div>

                    </div>


                    <div class="form-group">

                        <label for="servings">
                            Servings
                        </label>

                        <input
                                type="number"
                                id="servings"
                                name="servings"
                                min="1"
                                max="100"
                                placeholder="2"
                                required
                        >

                    </div>

                </div>


                <div class="form-row">

                    <div class="form-group">

                        <label for="difficulty">
                            Difficulty
                        </label>

                        <select
                                id="difficulty"
                                name="difficulty"
                                required
                        >

                            <option value="">
                                Select difficulty
                            </option>

                            <option value="EASY">
                                Easy
                            </option>

                            <option value="MEDIUM">
                                Medium
                            </option>

                            <option value="HARD">
                                Hard
                            </option>

                        </select>

                    </div>


                    <div class="form-group">

                        <label for="cuisine">
                            Cuisine
                        </label>

                        <input
                                type="text"
                                id="cuisine"
                                name="cuisine"
                                maxlength="80"
                                placeholder="e.g. Italian"
                                required
                        >

                    </div>

                </div>


                <div class="form-group">

                    <label for="imageUrl">
                        Recipe image URL
                    </label>

                    <input
                            type="url"
                            id="imageUrl"
                            name="imageUrl"
                            maxlength="500"
                            placeholder="https://example.com/recipe-image.jpg"
                    >

                    <p class="field-help">
                        Optional. Use a publicly accessible image URL.
                    </p>

                </div>

            </div>


            <div class="form-section">

                <div class="form-section-heading">

                    <h2>Ingredients</h2>

                    <p class="muted small">
                        List the ingredients and their quantities.
                    </p>

                </div>


                <div id="ingredients-container">

                    <div class="dynamic-form-row ingredient-row">

                        <input
                                type="text"
                                name="ingredientName[]"
                                placeholder="Ingredient"
                                maxlength="150"
                                required
                        >

                        <input
                                type="text"
                                name="ingredientQuantity[]"
                                placeholder="Quantity"
                                maxlength="100"
                                required
                        >

                    </div>

                </div>


                <button
                        type="button"
                        class="btn secondary add-row-btn"
                        id="addIngredient"
                >
                    + Add ingredient
                </button>

            </div>


            <div class="form-section">

                <div class="form-section-heading">

                    <h2>Instructions</h2>

                    <p class="muted small">
                        Add the cooking steps in the order they should be followed.
                    </p>

                </div>


                <div id="instructions-container">

                    <div class="dynamic-form-row instruction-row">

                        <span class="step-number">1</span>

                        <textarea
                                name="instructionText[]"
                                rows="3"
                                maxlength="1000"
                                placeholder="Describe the first step..."
                                required
                        ></textarea>

                    </div>

                </div>


                <button
                        type="button"
                        class="btn secondary add-row-btn"
                        id="addInstruction"
                >
                    + Add step
                </button>

            </div>


            <div class="form-submit-section">

                <div>

                    <strong>Ready to share?</strong>

                    <p class="muted small">
                        Your recipe will be submitted as
                        <strong>Pending</strong> and reviewed by an administrator.
                    </p>

                </div>


                <button
                        type="submit"
                        class="btn primary"
                >
                    Submit recipe
                </button>

            </div>

        </form>

    </section>

</main>

<%@ include file="partials/footer.jsp" %>


<script>

    const ingredientsContainer =
        document.getElementById("ingredients-container");

    const instructionsContainer =
        document.getElementById("instructions-container");


    document.getElementById("addIngredient")
        .addEventListener("click", function () {

            const row =
                document.createElement("div");

            row.className =
                "dynamic-form-row ingredient-row";

            row.innerHTML = `
                <input
                    type="text"
                    name="ingredientName[]"
                    placeholder="Ingredient"
                    maxlength="150"
                    required
                >

                <input
                    type="text"
                    name="ingredientQuantity[]"
                    placeholder="Quantity"
                    maxlength="100"
                    required
                >

                <button
                    type="button"
                    class="remove-row-btn"
                >
                    ×
                </button>
            `;

            ingredientsContainer.appendChild(row);

            addRemoveHandler(row);

        });


    document.getElementById("addInstruction")
        .addEventListener("click", function () {

            const stepNumber =
                instructionsContainer.children.length + 1;

            const row =
                document.createElement("div");

            row.className =
                "dynamic-form-row instruction-row";

            row.innerHTML = `
                <span class="step-number">
                    ${stepNumber}
                </span>

                <textarea
                    name="instructionText[]"
                    rows="3"
                    maxlength="1000"
                    placeholder="Describe this step..."
                    required
                ></textarea>

                <button
                    type="button"
                    class="remove-row-btn"
                >
                    ×
                </button>
            `;

            instructionsContainer.appendChild(row);

            addRemoveHandler(row);

        });


    function addRemoveHandler(row) {

        const button =
            row.querySelector(".remove-row-btn");

        if (!button) {
            return;
        }

        button.addEventListener("click", function () {

            row.remove();

            updateInstructionNumbers();

        });

    }


    function updateInstructionNumbers() {

        const rows =
            instructionsContainer.querySelectorAll(
                ".instruction-row"
            );

        rows.forEach(function (row, index) {

            const number =
                row.querySelector(".step-number");

            if (number) {
                number.textContent = index + 1;
            }

        });

    }

</script>

</body>
</html>
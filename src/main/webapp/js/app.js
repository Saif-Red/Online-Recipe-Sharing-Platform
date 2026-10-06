document.querySelectorAll('.chip').forEach((chip) => {
    chip.addEventListener('click', () => {
        document.querySelectorAll('.chip').forEach((item) => item.classList.remove('active'));
        chip.classList.add('active');
    });
});

// ======================================
// Modern Review Form
// ======================================

document.addEventListener("DOMContentLoaded", function () {

    const ratingInputs = document.querySelectorAll(
        '.star-rating input[name="rating"]'
    );

    const ratingLabels = document.querySelectorAll(
        '.star-rating label'
    );

    const ratingValue = document.getElementById("ratingValue");

    const reviewText = document.getElementById("reviewText");

    const characterCount = document.getElementById("characterCount");


    // --------------------------------------
    // Rating display
    // --------------------------------------

    function updateRatingDisplay(value) {

        if (!ratingValue) {
            return;
        }

        if (value) {
            ratingValue.textContent = value + " / 5";
        } else {
            ratingValue.textContent = "Select a rating";
        }
    }


    function getSelectedRating() {

        const selected = document.querySelector(
            '.star-rating input[name="rating"]:checked'
        );

        return selected ? selected.value : null;
    }


    // Clicking a star

    ratingInputs.forEach(function (input) {

        input.addEventListener("change", function () {
            updateRatingDisplay(this.value);
        });

    });


    // Hover preview

    ratingLabels.forEach(function (label) {

        label.addEventListener("mouseenter", function () {

            const inputId = this.getAttribute("for");
            const input = document.getElementById(inputId);

            if (input) {
                updateRatingDisplay(input.value);
            }

        });


        label.addEventListener("mouseleave", function () {

            updateRatingDisplay(getSelectedRating());

        });

    });


    // Restore existing rating when editing

    updateRatingDisplay(getSelectedRating());


    // --------------------------------------
    // Character counter
    // --------------------------------------

    function updateCharacterCount() {

        if (!reviewText || !characterCount) {
            return;
        }

        characterCount.textContent =
            reviewText.value.length + " / 1000";
    }


    if (reviewText) {

        reviewText.addEventListener(
            "input",
            updateCharacterCount
        );

        updateCharacterCount();
    }

});
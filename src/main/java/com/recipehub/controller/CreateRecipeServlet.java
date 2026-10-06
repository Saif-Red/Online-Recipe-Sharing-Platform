package com.recipehub.controller;

import com.recipehub.dao.RecipeDAO;
import com.recipehub.exception.DataAccessException;
import com.recipehub.exception.ValidationException;
import com.recipehub.model.Difficulty;
import com.recipehub.model.Ingredient;
import com.recipehub.model.Recipe;
import com.recipehub.model.RecipeStatus;
import com.recipehub.model.User;
import com.recipehub.service.RecipeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/contributor/create-recipe")
public class CreateRecipeServlet extends BaseServlet {

    private final RecipeService recipeService =
            new RecipeService(new RecipeDAO());

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        User user = currentUser(request);

        if (user == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }

        request.getRequestDispatcher(
                "/WEB-INF/views/create-recipe.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        User user = currentUser(request);

        if (user == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }

        try {
            Recipe recipe = new Recipe();

            recipe.setAuthorId(user.getId());
            recipe.setTitle(request.getParameter("title"));
            recipe.setDescription(request.getParameter("description"));

            recipe.setPrepMinutes(
                    Integer.parseInt(request.getParameter("prepMinutes"))
            );

            recipe.setCookMinutes(
                    Integer.parseInt(request.getParameter("cookMinutes"))
            );

            recipe.setServings(
                    Integer.parseInt(request.getParameter("servings"))
            );

            recipe.setDifficulty(
                    Difficulty.valueOf(
                            request.getParameter("difficulty")
                    )
            );

            recipe.setCuisine(request.getParameter("cuisine"));
            recipe.setImageUrl(request.getParameter("imageUrl"));

            // New recipes always require admin approval.
            recipe.setStatus(RecipeStatus.PENDING);

            /*
             * Build ingredient objects from the dynamic form fields.
             */
            String[] ingredientNames =
                    request.getParameterValues("ingredientName[]");

            String[] ingredientQuantities =
                    request.getParameterValues("ingredientQuantity[]");

            if (ingredientNames == null
                    || ingredientQuantities == null) {
                throw new ValidationException(
                        "Please add at least one ingredient."
                );
            }

            if (ingredientNames.length
                    != ingredientQuantities.length) {
                throw new ValidationException(
                        "Ingredient information is incomplete."
                );
            }

            List<Ingredient> ingredients =
                    new ArrayList<>();

            for (int i = 0; i < ingredientNames.length; i++) {

                String name = ingredientNames[i] == null
                        ? ""
                        : ingredientNames[i].trim();

                String quantity = ingredientQuantities[i] == null
                        ? ""
                        : ingredientQuantities[i].trim();

                if (name.isBlank() || quantity.isBlank()) {
                    throw new ValidationException(
                            "Ingredient name and quantity are required."
                    );
                }

                Ingredient ingredient = new Ingredient();

                ingredient.setItemName(name);
                ingredient.setQuantity(quantity);
                ingredient.setSortOrder(i + 1);

                ingredients.add(ingredient);
            }

            /*
             * Build the instruction list from the dynamic form fields.
             */
            String[] instructionValues =
                    request.getParameterValues("instructionText[]");

            if (instructionValues == null) {
                throw new ValidationException(
                        "Please add at least one instruction."
                );
            }

            List<String> instructions =
                    new ArrayList<>();

            for (String instructionValue : instructionValues) {

                String instruction = instructionValue == null
                        ? ""
                        : instructionValue.trim();

                if (instruction.isBlank()) {
                    throw new ValidationException(
                            "Instruction steps cannot be empty."
                    );
                }

                instructions.add(instruction);
            }

            /*
             * Save recipe, ingredients and instructions
             * inside one database transaction.
             */
            recipeService.createRecipeWithDetails(
                    recipe,
                    ingredients,
                    instructions
            );

            setFlash(
                    request,
                    "Recipe submitted successfully! It is now waiting for admin approval."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/contributor/dashboard"
            );

        } catch (NumberFormatException e) {

            setFlash(
                    request,
                    "Please enter valid numbers for preparation time, cooking time and servings."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/contributor/create-recipe"
            );

        } catch (IllegalArgumentException e) {

            setFlash(
                    request,
                    "Please select a valid recipe difficulty."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/contributor/create-recipe"
            );

        } catch (ValidationException e) {

            setFlash(
                    request,
                    e.getMessage()
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/contributor/create-recipe"
            );

        } catch (DataAccessException e) {

            e.printStackTrace();

            setFlash(
                    request,
                    "The recipe could not be saved because of a database error."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/contributor/create-recipe"
            );
        }
    }
}
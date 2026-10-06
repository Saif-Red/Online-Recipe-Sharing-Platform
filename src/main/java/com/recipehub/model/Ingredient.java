package com.recipehub.model;

public class Ingredient {
    private int id;
    private int recipeId;
    private String itemName;
    private String quantity;
    private int sortOrder;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getRecipeId() { return recipeId; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
}

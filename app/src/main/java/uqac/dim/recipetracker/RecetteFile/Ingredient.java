package uqac.dim.recipetracker.RecetteFile;

public class Ingredient {
    int idIngredient;

    String nomIngredient;

    int quantite;

    boolean favorite;

    public Ingredient() {}

    public Ingredient(int idIngredient, String nomIngredient, int quantite, boolean favorite) {
        this.idIngredient = idIngredient;
        this.nomIngredient = nomIngredient;
        this.quantite = quantite;
        this.favorite = favorite;
    }

    public void setIdIngredient(int idIngredient) { this.idIngredient = idIngredient; }
    public void setNomIngredient(String nomIngredient) { this.nomIngredient = nomIngredient; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    public int getIdIngredient() { return idIngredient; }
    public String getNomIngredient() { return nomIngredient; }
    public int getQuantite() { return quantite; }
    public boolean getFavorite() { return favorite; }
}
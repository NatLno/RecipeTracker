package uqac.dim.recipetracker.RecetteFile;

public class RecetteIngredient {
    int idIngredient;

    int idRecette;

    int quantiteNec;

    public RecetteIngredient(int idIngredient, int idRecette, int quantiteNec) {
        this.idIngredient = idIngredient;
        this.idRecette = idRecette;
        this.quantiteNec = quantiteNec;
    }
}

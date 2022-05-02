package uqac.dim.recipetracker.RecetteFile;

public class Recette {
    int id;

    String nom;

    String description;

    int tempsPreparation;

    boolean favorite;

    int image;

    String typeRecette;


    public Recette() {}

    public Recette(int id, String nom, String description, int tempsPreparation, boolean favorite, int image, String typeRecette) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.tempsPreparation = tempsPreparation;
        this.favorite = favorite;
        this.image = image;
        this.typeRecette = typeRecette;
    }

    public void setId(int id){this.id = id;}
    public void setNom(String nom){this.nom = nom;}
    public void setDescription(String description){this.description = description;}
    public void setTempsPreparation(int tempsPreparation){this.tempsPreparation = tempsPreparation;}
    public void setFavorite(boolean favorite){this.favorite = favorite;}
    public void setImage(int image) { this.image = image; }
    public void setTypeRecette(String typeRecette) { this.typeRecette = typeRecette; }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public int getTempsPreparation() { return tempsPreparation; }
    public boolean getFavorite() { return favorite; }
    public int getImage() { return image; }
    public String getTypeRecette() { return typeRecette; }
}
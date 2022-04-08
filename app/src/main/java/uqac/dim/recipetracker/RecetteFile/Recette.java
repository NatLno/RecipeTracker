package uqac.dim.recipetracker.RecetteFile;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Recette{

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "image")
    private int image;

    @ColumnInfo(name = "isFavorite")
    private boolean isFavorite;

    @ColumnInfo(name = "typeRecette")
    private String typeRecette;

    @ColumnInfo(name = "isTendance")
    private boolean isTendance;

    public Recette(int id, String nom, String description, int image, boolean isFavorite, String typeRecette, boolean isTendance){
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.isFavorite=isFavorite;
        this.typeRecette = typeRecette;
        this.isTendance = isTendance;
    }

    @Override
    public String toString(){
        return nom+ " : " + description + ", Favoris : " + isFavorite + ", Type de recette : " + typeRecette;
    }


    public int getId() { return id; }

    public final String getNom(){
        return nom;
    }

    public final String getDescription(){
        return description;
    }

    public final int getImage(){
        return image;
    }

    public final boolean getIsFavorite(){ return isFavorite; }

    public String getTypeRecette() { return typeRecette; }

    public boolean getIsTendance() { return isTendance; }

    public final void setIsFavorite(Boolean value){
        isFavorite = value;
    }

    public void setTendance(boolean tendance) { isTendance = tendance; }
}

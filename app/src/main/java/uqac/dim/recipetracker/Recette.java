package uqac.dim.recipetracker;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class Recette implements Parcelable {

    private String nom;
    private String description;
    private String[] ingredients;
    private int image;
    private boolean isFavorite;

    public  Recette(String nom, String description, String[] ingredients, int image){
        this.nom = nom;
        this.description = description;
        this.ingredients = ingredients;
        this.image = image;
        this.isFavorite=false;
    }

    public Recette(String nom, String description, String[] ingredients, int image, boolean isFavorite){
        this.nom = nom;
        this.description = description;
        this.ingredients = ingredients;
        this.image = image;
        this.isFavorite=isFavorite;
    }


    @SuppressLint("NewApi")
    protected Recette(Parcel in) {
        nom = in.readString();
        description = in.readString();
        ingredients = in.createStringArray();
        image = in.readInt();
        isFavorite = in.readBoolean();
    }

    public static final Creator<Recette> CREATOR = new Creator<Recette>() {
        @Override
        public Recette createFromParcel(Parcel in) {
            return new Recette(in);
        }

        @Override
        public Recette[] newArray(int size) {
            return new Recette[size];
        }
    };

    public final String getNom(){
        return nom;
    }

    public final String getDescription(){
        return description;
    }

    public final String[] getIngredients(){
        return ingredients;
    }

    public final int getImage(){
        return image;
    }

    public final boolean getIsFavorite(){
        return isFavorite;
    }

    public final void setIsFavorite(Boolean value){
        isFavorite = value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressLint("NewApi")
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nom);
        parcel.writeString(description);
        parcel.writeStringArray(ingredients);
        parcel.writeInt(image);
        parcel.writeBoolean(isFavorite);
    }
}

package uqac.dim.recipetracker;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Recette implements Parcelable {

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


    public Recette(int id, String nom, String description, int image, boolean isFavorite){
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.isFavorite=isFavorite;
    }

    @Override
    public String toString(){
        return nom+ " : " + description + " Favoris : " + isFavorite;
    }


    @SuppressLint("NewApi")
    protected Recette(Parcel in) {
        id = in.readInt();
        nom = in.readString();
        description = in.readString();
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

    public final boolean getIsFavorite(){
        return isFavorite;
    }

    public void setId(int id) { this.id = id; }

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
        parcel.writeInt(id);
        parcel.writeString(nom);
        parcel.writeString(description);
        parcel.writeInt(image);
        parcel.writeBoolean(isFavorite);
    }
}

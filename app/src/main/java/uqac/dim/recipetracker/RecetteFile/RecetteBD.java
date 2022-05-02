package uqac.dim.recipetracker.RecetteFile;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uqac.dim.recipetracker.R;
import uqac.dim.recipetracker.RecetteFile.Recette;
import uqac.dim.recipetracker.RecetteFile.Ingredient;
import uqac.dim.recipetracker.RecetteFile.RecetteIngredient;

public class RecetteBD extends SQLiteOpenHelper {

    private static final String LOG = "DIM";
    private static final int DATABASE_VERSION = 13;
    private static final String DATABASE_NAME = "RecipeTracker";

    //Noms Tables
    private static final String TABLE_RECETTE = "Recette";
    private static final String TABLE_RECETTEINGREDIENTS = "RecetteIngredients";
    private static final String TABLE_INGREDIENT = "Ingredient";
    private static final String TABLE_INSTRUCTION = "Instruction";

    //Colonnes Recette
    private static final String RECETTE_IDRECETTE = "idRecette";
    private static final String RECETTE_NOMRECETTE = "nomRecette";
    private static final String RECETTE_DESCRIPTION = "description";
    private static final String RECETTE_TEMPSPREPARATION = "tempsPreparation";
    private static final String RECETTE_FAVORITE = "favorite";
    private static final String RECETTE_IMAGE = "image";
    private static final String RECETTE_TYPERECETTE = "typeRecette";

    //Colonnes Ingredient
    private static final String INGREDIENT_IDINGREDIENT = "idIngredient";
    private static final String INGREDIENT_NOMINGREDIENT = "nomIngredient";
    private static final String INGREDIENT_QUANTITE = "quantite";
    private static final String INGREDIENT_FAVORITE = "favorite";

    //Colonnes RecetteIngredients
    private static final String RECETTEINGREDIENT_IDINGREDIENT = "idIngredient";
    private static final String RECETTEINGREDIENT_IDRECETTE = "idRecette";
    private static final String RECETTEINGREDIENT_QUANTITENEC = "quantiteNec";

    //Colonnes Instruction
    private static final String INSTRUCTION_IDRECETTE = "idRecette";
    private static final String INSTRUCTION_IDINSTRUCTION = "idInstruction";
    private static final String INSTRUCTION_INSTRUCTION = "instruction";

    private static final String CREATE_TABLE_RECETTE = "CREATE TABLE Recette (" +
            RECETTE_IDRECETTE + " INT IDENTITY(1,1) PRIMARY KEY, " +
            RECETTE_NOMRECETTE + " VARCHAR(50), " +
            RECETTE_DESCRIPTION + " VARCHAR(200), " +
            RECETTE_TEMPSPREPARATION + " INT, " +
            RECETTE_FAVORITE + " INT, " +
            RECETTE_IMAGE + " INT, " +
            RECETTE_TYPERECETTE +" VARCHAR(50))";

    private static final String CREATE_TABLE_INGREDIENT = "CREATE TABLE Ingredient (" +
            INGREDIENT_IDINGREDIENT + " INT IDENTITY(1,1) PRIMARY KEY, " +
            INGREDIENT_NOMINGREDIENT + " VARCHAR(50), " +
            INGREDIENT_QUANTITE + " INT, " +
            INGREDIENT_FAVORITE + " INT)";

    private static final String CREATE_TABLE_RECETTEINGREDIENTS = "CREATE TABLE RecetteIngredients (" +
            RECETTEINGREDIENT_IDRECETTE + " INT, " +
            RECETTEINGREDIENT_IDINGREDIENT + " INT, " +
            RECETTEINGREDIENT_QUANTITENEC + " INT, " +
            "CONSTRAINT PK_RecetteIngredients PRIMARY KEY(" + RECETTEINGREDIENT_IDRECETTE + ", " + RECETTEINGREDIENT_IDINGREDIENT + "))";

    private static final String CREATE_TABLE_INSTRUCTION = "CREATE TABLE Instruction (" +
            INSTRUCTION_IDRECETTE + " INT , " +
            INSTRUCTION_IDINSTRUCTION + " INT, " +
            INSTRUCTION_INSTRUCTION + " varchar(250), " +
            "CONSTRAINT PK_Instruction PRIMARY KEY(" + INSTRUCTION_IDRECETTE + ", " + INSTRUCTION_IDINSTRUCTION + "))";

    public RecetteBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.i(LOG, "CREATE_TABLE_RECETTE     : " + CREATE_TABLE_RECETTE);
        Log.i(LOG, "CREATE_TABLE_INGREDIENT      : " + CREATE_TABLE_INGREDIENT);
        Log.i(LOG, "CREATE_TABLE_RECETTEINGREDIENTS : " + CREATE_TABLE_RECETTEINGREDIENTS);
        Log.i(LOG, "CREATE_TABLE_INSTRUCTION : " + CREATE_TABLE_INSTRUCTION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i(LOG, "*** APPEL DE ONCREATE DE LA BASE DE DONNEES");

        db.execSQL(CREATE_TABLE_RECETTE);
        db.execSQL(CREATE_TABLE_INGREDIENT);
        db.execSQL(CREATE_TABLE_RECETTEINGREDIENTS);
        db.execSQL(CREATE_TABLE_INSTRUCTION);

        Log.i(LOG, "*** BDD CREEE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(LOG, "*** APPEL DE ONUPGRADE DE LA BASE DE DONNEES");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECETTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECETTEINGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTION);
        onCreate(db);
    }

    //***************************************************************
    //Fonctions pour Recette
    //***************************************************************

    public long addRecette(Recette recette)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECETTE_IDRECETTE, recette.getId());
        values.put(RECETTE_NOMRECETTE, recette.getNom());
        values.put(RECETTE_DESCRIPTION, recette.getDescription());
        values.put(RECETTE_TEMPSPREPARATION, recette.getTempsPreparation());
        if(recette.getFavorite()) values.put(RECETTE_FAVORITE, 1);
        else values.put(RECETTE_FAVORITE, 0);

        if(recette.getImage()== R.drawable.poutine)
            Log.i(LOG, "IMAGE : poutine");
        else if(recette.getImage()==R.drawable.tarte_aux_bleuets)
            Log.i(LOG, "IMAGE : tarte_aux_bleuets");
        else if(recette.getImage()==R.drawable.pates_carbonara)
            Log.i(LOG, "IMAGE : pates_carbonara");
        else
            Log.i(LOG, "AUCUNE IMAGE");

        values.put(RECETTE_IMAGE, recette.getImage());
        values.put(RECETTE_TYPERECETTE, recette.getTypeRecette());

        // insert row
        return db.insert(TABLE_RECETTE, null, values);
    }

    public int updateRecette(Recette recette){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RECETTE_IDRECETTE, recette.getId());
        values.put(RECETTE_NOMRECETTE, recette.getNom());
        values.put(RECETTE_DESCRIPTION, recette.getDescription());
        values.put(RECETTE_TEMPSPREPARATION, recette.getTempsPreparation());
        if(recette.getFavorite()) values.put(RECETTE_FAVORITE, 1);
        else values.put(RECETTE_FAVORITE, 0);
        values.put(RECETTE_IMAGE, recette.getImage());
        values.put(RECETTE_TYPERECETTE, recette.getTypeRecette());

        return db.update(TABLE_RECETTE, values, "idRecette" + " = ?",
                new String[] { String.valueOf(recette.getId()) });

    }

    @SuppressLint("Range")
    public List<Recette> getAllRecettes()
    {
        List<Recette> recettes = new ArrayList<Recette>();
        String selectQuery = "SELECT  * FROM " + TABLE_RECETTE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Recette recette = new Recette();
                recette.setId(c.getInt((c.getColumnIndex(RECETTE_IDRECETTE))));
                recette.setNom(c.getString(c.getColumnIndex(RECETTE_NOMRECETTE)));
                recette.setDescription((c.getString(c.getColumnIndex(RECETTE_DESCRIPTION))));
                recette.setTempsPreparation(c.getInt((c.getColumnIndex(RECETTE_TEMPSPREPARATION))));
                recette.setFavorite((c.getInt(c.getColumnIndex(RECETTE_FAVORITE))) == 1);
                recette.setImage((c.getInt(c.getColumnIndex(RECETTE_IMAGE))));
                recette.setTypeRecette((c.getString(c.getColumnIndex(RECETTE_TYPERECETTE))));

                recettes.add(recette);
            } while (c.moveToNext());
        }

        c.close();
        return recettes;
    }

    @SuppressLint("Range")
    public List<Recette> LoadAllByIds(int[] ids)
    {
        List<Recette> recettes = new ArrayList<Recette>();
        String selectQuery = "SELECT  * FROM " + TABLE_RECETTE + " WHERE " + RECETTE_IDRECETTE + " = " + ids[0];
        for(int i = 1; i<ids.length;i++){ selectQuery += " OR " + RECETTE_IDRECETTE + " = " + ids[i]; }
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Recette recette = new Recette();
                recette.setId(c.getInt((c.getColumnIndex(RECETTE_IDRECETTE))));
                recette.setNom(c.getString(c.getColumnIndex(RECETTE_NOMRECETTE)));
                recette.setDescription((c.getString(c.getColumnIndex(RECETTE_DESCRIPTION))));
                recette.setTempsPreparation(c.getInt((c.getColumnIndex(RECETTE_TEMPSPREPARATION))));
                recette.setFavorite((c.getInt(c.getColumnIndex(RECETTE_FAVORITE))) == 1);
                recette.setImage((c.getInt(c.getColumnIndex(RECETTE_IMAGE))));
                recette.setTypeRecette((c.getString(c.getColumnIndex(RECETTE_TYPERECETTE))));

                // adding to todo list
                recettes.add(recette);
            } while (c.moveToNext());
        }
        c.close();
        return recettes;
    }

    @SuppressLint("Range")
    public Recette findByNom(String nom)
    {
        String selectQuery = "SELECT * FROM " + TABLE_RECETTE + " WHERE " + RECETTE_NOMRECETTE + " = " + nom;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            Recette recette = new Recette();
            recette.setId(c.getInt((c.getColumnIndex(RECETTE_IDRECETTE))));
            recette.setNom(c.getString(c.getColumnIndex(RECETTE_NOMRECETTE)));
            recette.setDescription((c.getString(c.getColumnIndex(RECETTE_DESCRIPTION))));
            recette.setTempsPreparation(c.getInt((c.getColumnIndex(RECETTE_TEMPSPREPARATION))));
            recette.setFavorite((c.getInt(c.getColumnIndex(RECETTE_FAVORITE))) == 1);
            recette.setImage((c.getInt(c.getColumnIndex(RECETTE_IMAGE))));
            recette.setTypeRecette((c.getString(c.getColumnIndex(RECETTE_TYPERECETTE))));
            return recette;
        }
        else return null;
    }

    @SuppressLint("Range")
    public Recette findById(int id)
    {
        String selectQuery = "SELECT * FROM " + TABLE_RECETTE + " WHERE " + RECETTE_IDRECETTE + " = " + id;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            Recette recette = new Recette();
            recette.setId(c.getInt((c.getColumnIndex(RECETTE_IDRECETTE))));
            recette.setNom(c.getString(c.getColumnIndex(RECETTE_NOMRECETTE)));
            recette.setDescription((c.getString(c.getColumnIndex(RECETTE_DESCRIPTION))));
            recette.setTempsPreparation(c.getInt((c.getColumnIndex(RECETTE_TEMPSPREPARATION))));
            recette.setFavorite((c.getInt(c.getColumnIndex(RECETTE_FAVORITE))) == 1);
            recette.setImage((c.getInt(c.getColumnIndex(RECETTE_IMAGE))));
            recette.setTypeRecette((c.getString(c.getColumnIndex(RECETTE_TYPERECETTE))));
            return recette;
        }
        else return null;
    }

    public void deleteRecette(int idRecette){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECETTE, RECETTE_IDRECETTE + " = ?",
                new String[] { String.valueOf(idRecette) });
    }

    public void deleteRecettes() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.rawQuery("DELETE FROM " + TABLE_RECETTE, null);
    }

    //***************************************************************
    //Fonctions pour Ingredient
    //***************************************************************

    public long addIngredient(Ingredient ingredient)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INGREDIENT_IDINGREDIENT, ingredient.getIdIngredient());
        values.put(INGREDIENT_NOMINGREDIENT, ingredient.getNomIngredient());
        values.put(INGREDIENT_QUANTITE, ingredient.getQuantite());
        if(ingredient.getFavorite()) values.put(INGREDIENT_FAVORITE, 1);
        else values.put(INGREDIENT_FAVORITE, 0);
        // insert row
        long ingredient_id = db.insert(TABLE_INGREDIENT, null, values);

        return ingredient_id;
    }

    public int updateIngredient(Ingredient ingredient){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INGREDIENT_IDINGREDIENT, ingredient.getIdIngredient());
        values.put(INGREDIENT_NOMINGREDIENT, ingredient.getNomIngredient());
        values.put(INGREDIENT_QUANTITE, ingredient.getQuantite());
        if(ingredient.getFavorite()) values.put(INGREDIENT_FAVORITE, 1);
        else values.put(INGREDIENT_FAVORITE, 0);

        return db.update(TABLE_INGREDIENT, values, INGREDIENT_IDINGREDIENT + " = ?",
                new String[] { String.valueOf(ingredient.getIdIngredient()) });

    }

    @SuppressLint("Range")
    public List<Ingredient> getAllIngredient()
    {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        String selectQuery = "SELECT  * FROM " + TABLE_INGREDIENT;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setIdIngredient(c.getInt((c.getColumnIndex(INGREDIENT_IDINGREDIENT))));
                ingredient.setNomIngredient(c.getString(c.getColumnIndex(INGREDIENT_NOMINGREDIENT)));
                ingredient.setQuantite(c.getInt(c.getColumnIndex(INGREDIENT_QUANTITE)));
                ingredient.setFavorite((c.getInt(c.getColumnIndex(INGREDIENT_FAVORITE))) == 1);
                ingredients.add(ingredient);
            } while (c.moveToNext());
        }

        c.close();
        return ingredients;
    }

    @SuppressLint("Range")
    public List<Ingredient> getIngredientForRecette(int idRecette)
    {
        String selectQuery = "SELECT * FROM " + TABLE_INGREDIENT +
                " I INNER JOIN " + TABLE_RECETTEINGREDIENTS +
                " RI ON I." + INGREDIENT_IDINGREDIENT + " = RI." + RECETTEINGREDIENT_IDINGREDIENT +
                " INNER JOIN " + TABLE_RECETTE + " R ON RI." + RECETTEINGREDIENT_IDRECETTE + " = R." + RECETTE_IDRECETTE +
                " WHERE R." + RECETTE_IDRECETTE + " = " + idRecette;
        Log.i(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        List<Ingredient> ingredientList = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setIdIngredient(c.getInt((c.getColumnIndex(INGREDIENT_IDINGREDIENT))));
                ingredient.setNomIngredient(c.getString(c.getColumnIndex(INGREDIENT_NOMINGREDIENT)));
                ingredient.setQuantite(c.getInt(c.getColumnIndex(INGREDIENT_QUANTITE)));
                ingredient.setFavorite((c.getInt(c.getColumnIndex(INGREDIENT_FAVORITE))) == 1);
                ingredientList.add(ingredient);
            } while (c.moveToNext());
        }
        return ingredientList;
    }

    public void deleteIngredient(int idIngredient){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INGREDIENT, INGREDIENT_IDINGREDIENT + " = ?",
                new String[] { String.valueOf(idIngredient) });
    }

    public void deleteIngredients()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.rawQuery("DELETE FROM " + TABLE_INGREDIENT, null);
    }

    //***************************************************************
    //Fonctions pour Instruction
    //***************************************************************

    public long addInstruction(Instruction instruction)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INSTRUCTION_IDRECETTE, instruction.getIdRecette());
        values.put(INSTRUCTION_IDINSTRUCTION, instruction.getIdInstruction());
        values.put(INSTRUCTION_INSTRUCTION, instruction.getInstruction());

        // insert row
        return db.insert(TABLE_INSTRUCTION, null, values);
    }

    @SuppressLint("Range")
    public List<Instruction> getAllInstruction()
    {
        List<Instruction> instructions = new ArrayList<Instruction>();
        String selectQuery = "SELECT  * FROM " + TABLE_INSTRUCTION;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Instruction instruction = new Instruction();
                instruction.setIdRecette(c.getInt(c.getColumnIndex(INSTRUCTION_IDRECETTE)));
                instruction.setIdInstruction(c.getInt(c.getColumnIndex(INSTRUCTION_IDINSTRUCTION)));
                instruction.setInstruction(c.getString(c.getColumnIndex(INSTRUCTION_INSTRUCTION)));
                instructions.add(instruction);
            } while (c.moveToNext());
        }

        c.close();
        return instructions;
    }

    @SuppressLint("Range")
    public List<Instruction> getInstructionForRecipe(int idRecipe)
    {
        List<Instruction> instructions = new ArrayList<Instruction>();
        String selectQuery = "SELECT  * FROM " + TABLE_INSTRUCTION + " WHERE " + INSTRUCTION_IDRECETTE + " = " + idRecipe;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Instruction instruction = new Instruction();
                instruction.setIdRecette(c.getInt(c.getColumnIndex(INSTRUCTION_IDRECETTE)));
                instruction.setIdInstruction(c.getInt(c.getColumnIndex(INSTRUCTION_IDINSTRUCTION)));
                instruction.setInstruction(c.getString(c.getColumnIndex(INSTRUCTION_INSTRUCTION)));
                instructions.add(instruction);
            } while (c.moveToNext());
        }

        c.close();
        return instructions;
    }

    public void deleteInstructions()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.rawQuery("DELETE FROM " + TABLE_INSTRUCTION, null);
    }
}

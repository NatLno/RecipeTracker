package uqac.dim.recipetracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import uqac.dim.recipetracker.Fragment.FavoritesFragment;
import uqac.dim.recipetracker.Fragment.HomeFragment;
import uqac.dim.recipetracker.Fragment.MesIngredientsFragment;
import uqac.dim.recipetracker.Fragment.SearchFragment;
import uqac.dim.recipetracker.RecetteFile.Ingredient;
import uqac.dim.recipetracker.RecetteFile.Instruction;
import uqac.dim.recipetracker.RecetteFile.Recette;
import uqac.dim.recipetracker.RecetteFile.RecetteActivity;
import uqac.dim.recipetracker.RecetteFile.RecetteBD;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_RECETTE = "uqac.dim.recipetracker.MESSAGE1";
    static final int LAUNCH_RECETTE = 1;

    private static final String LOG = "DIM";

    String fragmentName = "Home";
    public static RecetteBD rdb;

    int recette_favorite = 0;
    int recette_image = 1;
    int recette_text = 2;
    int recette_description = 3;

    public static List<Recette> recetteList = new ArrayList<>();
    public static List<Ingredient> ingredientList = new ArrayList<>();
    public static List<Instruction> instructionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("DIM","Create");

        rdb = new RecetteBD(getApplicationContext());

        try {
            initDataBase(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        recetteList = rdb.getAllRecettes();
        ingredientList = rdb.getAllIngredient();
        for(Ingredient ingredient : ingredientList)
            Log.i(LOG, "Nouvel ingrédient : " + ingredient.getNomIngredient() + " avec ID : " + ingredient.getIdIngredient());
        instructionList = rdb.getInstructionForRecipe(1);
        for(Instruction instruction : instructionList)
            Log.i(LOG, "Nouvelle instruction : " + instruction.getInstruction() + " avec ID : " + instruction.getIdInstruction());

        //crée la bar de menu en bas de l'écran
        BottomNavigationView bottomNav = findViewById(R.id.bottom_toolbar);
        bottomNav.setOnItemSelectedListener(navListener);

        //ouverture de l'appli avec l'écran home
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

    }

    private BottomNavigationView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item){

                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.home:
                            selectedFragment = new HomeFragment();
                            fragmentName = "Home";
                            break;
                        case R.id.favorites:
                            selectedFragment = new FavoritesFragment();
                            fragmentName = "Favorites";
                            break;
                        case R.id.search:
                            selectedFragment = new SearchFragment();
                            fragmentName = "Search";
                            break;
                        case R.id.mes_ingredients:
                            selectedFragment = new MesIngredientsFragment();
                            fragmentName = "Mes Ingrédients";
                    }

                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };

    void initDataBase(Context context) throws IOException {

        rdb.deleteRecettes();
        rdb.deleteIngredients();
        rdb.deleteInstructions();

        InputStream is = context.getResources().openRawResource(R.raw.recettes);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        br.readLine();
        String line = null;
        int id = 1;
        try {
            while ((line = br.readLine())!= null){
                String[] arrLine = line.split(";");
                String nom = arrLine[1];
                String description = arrLine[2];
                int tempsPreparation = Integer.parseInt(arrLine[3]);
                int isFavorite = Integer.parseInt(arrLine[4]);
                String typeRecette = arrLine[5];
                String imageNom = arrLine[6];
                int imageRecette = getResources().getIdentifier(imageNom,"drawable",getPackageName());

                rdb.addRecette(new Recette(id,nom,description,tempsPreparation,isFavorite==1,imageRecette,typeRecette));
                id++;
            }

            for (Recette recette : rdb.getAllRecettes())
                Toast.makeText(this,"INSERTION : " + recette.toString(),Toast.LENGTH_SHORT).show();

        } finally {
            is.close();
        }

        is = context.getResources().openRawResource(R.raw.ingredients);
        br = new BufferedReader(new InputStreamReader(is));
        line = null;
        id = 1;
        try {
            while ((line = br.readLine())!= null){
                String[] arrLine = line.split(";");
                String nom = arrLine[0];

                rdb.addIngredient(new Ingredient(id,nom,0,false));
                id++;
            }
        } finally {
            is.close();
        }

        is = context.getResources().openRawResource(R.raw.instructions);
        br = new BufferedReader(new InputStreamReader(is));
        line = null;
        try {
            while ((line = br.readLine())!= null){
                String[] arrLine = line.split(";");
                int idRecette = Integer.parseInt(arrLine[0]);
                int idInstruction = Integer.parseInt(arrLine[1]);
                String instruction = arrLine[2];

                rdb.addInstruction(new Instruction(idRecette,idInstruction,instruction));
            }
        } finally {
            is.close();
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void ouvrirRecette(View v){

        int id = v.getId();
        Log.i("DIM","IIDD :" + id);

        Log.i("DIM","Id: " + id);

        Intent intent = new Intent(MainActivity.this, RecetteActivity.class);
        intent.putExtra(EXTRA_RECETTE,id);
        startActivityForResult(intent,LAUNCH_RECETTE);
    }

    public void addRemoveIngredient(View v){
        ImageButton imageButton = (ImageButton) v;
        int id = v.getId();
        Log.i("DOM", "idIngre: "+id);
        Log.i("DOM", "nom : "+ingredientList.get(id).getNomIngredient());

        if(ingredientList.get(id).getFavorite()){
            ((ImageButton) v).setImageResource(R.drawable.add_ingredient_image);
        }
        else{
            ((ImageButton) v).setImageResource(R.drawable.remove_ingredient_image);
        }
        ingredientList.get(id).setFavorite(!ingredientList.get(id).getFavorite());
        rdb.updateIngredient(ingredientList.get(id));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_RECETTE) {
            if(resultCode == Activity.RESULT_OK){
                Log.i("DIM","retour");
                Fragment fragment = null;

                switch (fragmentName){
                    case "Home":
                        fragment = new HomeFragment();
                        break;
                    case "Favorites":
                        fragment = new FavoritesFragment();
                        break;
                    case "Search":
                        fragment = new SearchFragment();
                }

                recetteList = rdb.getAllRecettes();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        }
    }
}
package uqac.dim.recipetracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_RECETTE = "uqac.dim.recipetracker.MESSAGE1";
    static final int LAUNCH_RECETTE = 1;

    public static RecetteBD rdb;

    int recette_favorite = 0;
    int recette_image = 1;
    int recette_text = 2;
    int recette_description = 3;

    public static List<Recette> recetteList = new ArrayList<>();
    public static List<Integer> recetteImage = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("DIM","Create");

        try {
            initDataBase(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        recetteList = rdb.recetteDao().getAllRecettes();

        //crée la bar de menu en bas de l'écran
        BottomNavigationView bottomNav = findViewById(R.id.bottom_toolbar);
        bottomNav.setOnItemSelectedListener(navListener);

        //ouverture de l'appli avec l'écran home
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        /*Recette poutine = new Recette(0, getString(R.string.poutine),getString(R.string.poutine_description),R.drawable.poutine,true);
        recetteList.add(poutine);
        recetteImage.add(R.drawable.poutine);

        Recette tarteBleuets = new Recette(1, getString(R.string.tarte_bleuets),getString(R.string.tarte_bleuets_description),R.drawable.tarte_aux_bleuets,false);
        recetteList.add(tarteBleuets);
        recetteImage.add(R.drawable.tarte_aux_bleuets);*/


    }

    private BottomNavigationView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item){
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.favorites:
                            selectedFragment = new FavoritesFragment();
                            break;
                        case R.id.search:
                            selectedFragment = new SearchFragment();
                    }

                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };


    void initDataBase(Context context) throws IOException {

        rdb = RecetteBD.getDatabase(getApplicationContext());
        rdb.recetteDao().deleteRecettes();

        InputStream is = context.getResources().openRawResource(R.raw.recettes);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;
        int id = 0;
        try {
            while ((line = br.readLine())!= null){
                String[] arrLine = line.split(";");
                String nom = arrLine[0];
                String description = arrLine[1];
                int isFavorite = Integer.parseInt(arrLine[2]);
                rdb.recetteDao().addRecette(new Recette(id,nom,description,R.drawable.poutine,isFavorite==1));
                recetteImage.add(R.drawable.poutine);
                id++;
            }

            for (Recette recette : rdb.recetteDao().getAllRecettes())
                Toast.makeText(this,"INSERTION : " + recette.toString(),Toast.LENGTH_SHORT).show();

        } finally {
            is.close();
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void ouvrirRecette(View v){

        int id = v.getId();
        Log.i("DIM","Id: " + id);

        Recette recette = rdb.recetteDao().findById(id);
        Intent intent = new Intent(MainActivity.this,RecetteActivity.class);
        intent.putExtra(EXTRA_RECETTE,recette);
        startActivityForResult(intent,LAUNCH_RECETTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_RECETTE) {
            if(resultCode == Activity.RESULT_OK){
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                recetteList = rdb.recetteDao().getAllRecettes();
                //updateRecette(recette);
            }
        }
    }


    public void updateRecette(Recette recette){
        int id = recette.getId();
        Log.i("DIM","id : "+id);
        RelativeLayout relativeLayout = findViewById(id);
        ImageView favorisRecette = (ImageView) relativeLayout.getChildAt(0);

        if(recette.getIsFavorite()){
            favorisRecette.setImageResource(R.drawable.etoile);
            favorisRecette.setContentDescription(getString(R.string.favoris));
        }
        else{
            favorisRecette.setImageResource(R.drawable.ic_baseline_star_24);
            favorisRecette.setContentDescription(getString(R.string.notfavoris));
        }
        recetteList.set(id,recette);
    }
}
package uqac.dim.recipetracker.RecetteFile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import uqac.dim.recipetracker.Fragment.FavoritesFragment;
import uqac.dim.recipetracker.Fragment.IngredientsFragment;
import uqac.dim.recipetracker.Fragment.PreparationFragment;
import uqac.dim.recipetracker.MainActivity;
import uqac.dim.recipetracker.R;

public class RecetteActivity extends AppCompatActivity {

    Recette recette;


    @SuppressLint("UseCompatLoadingForDrawables")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recette_layout);


        Intent intent = getIntent();
        if(intent!=null){
            int id = intent.getIntExtra(MainActivity.EXTRA_RECETTE,0);

            recette = MainActivity.rdb.recetteDao().findById(id);

            if(recette!=null){

                if(recette.getIsFavorite()){
                    ((ImageView)findViewById(R.id.favoris)).setImageResource(R.drawable.favoris);            }
                else{
                    ((ImageView)findViewById(R.id.favoris)).setImageResource(R.drawable.non_favoris);            }

                setTitle(recette.getNom());
                ((ImageView)findViewById(R.id.image_recette)).setImageResource(recette.getImage());
            }
        }

        BottomNavigationView bottomNav = findViewById(R.id.ingre_prepa_menu);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.ingre_prepa_fragment_container,new IngredientsFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        MainActivity.rdb.recetteDao().updateRecette(recette);
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MainActivity.rdb.recetteDao().updateRecette(recette);

                Intent intent = new Intent();
                setResult(Activity.RESULT_OK,intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener(){
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item){
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.ingredients:
                            selectedFragment = new IngredientsFragment();
                            break;
                        case R.id.preparation:
                            selectedFragment = new PreparationFragment();
                            break;
                    }

                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.ingre_prepa_fragment_container,selectedFragment).commit();
                    return true;
                }
            };

    public void setFavorite(View v){

        ImageView etoile = (ImageView) v;
        if(recette.getIsFavorite()){
            etoile.setImageResource(R.drawable.non_favoris);
            etoile.setContentDescription(getString(R.string.notfavoris));
        }
        else{
            etoile.setImageResource(R.drawable.favoris);
            etoile.setContentDescription(getString(R.string.favoris));
        }
        recette.setIsFavorite(!recette.getIsFavorite());
    }
}

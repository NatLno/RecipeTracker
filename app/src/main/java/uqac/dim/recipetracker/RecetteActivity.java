package uqac.dim.recipetracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RecetteActivity extends AppCompatActivity {

    Recette recette;


    @SuppressLint("UseCompatLoadingForDrawables")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recette_layout);

        Intent intent = getIntent();
        if(intent!=null){
            recette = intent.getParcelableExtra(MainActivity.EXTRA_RECETTE);

            if(recette!=null){

                if(recette.getIsFavorite()){
                    ((ImageView)findViewById(R.id.favoris)).setImageResource(R.drawable.etoile);            }
                else{
                    ((ImageView)findViewById(R.id.favoris)).setImageResource(R.drawable.ic_baseline_star_24);            }

                setTitle(recette.getNom());
                ((ImageView)findViewById(R.id.image_recette)).setImageResource(recette.getImage());
            }
        }

        BottomNavigationView bottomNav = findViewById(R.id.ingre_prepa_menu);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.ingre_prepa_fragment_container,new FavoritesFragment()).commit();

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
                            selectedFragment = new FavoritesFragment();
                            break;
                        case R.id.preparation:
                            selectedFragment = new FavoritesFragment();
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
            etoile.setImageResource(R.drawable.ic_baseline_star_24);
            etoile.setContentDescription(getString(R.string.notfavoris));
        }
        else{
            etoile.setImageResource(R.drawable.etoile);
            etoile.setContentDescription(getString(R.string.favoris));
        }
        recette.setIsFavorite(!recette.getIsFavorite());
    }
}

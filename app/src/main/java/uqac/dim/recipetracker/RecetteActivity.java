package uqac.dim.recipetracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
                ((TextView)findViewById(R.id.name_recette)).setText(recette.getNom());
                switch (recette.getNom()){
                    case "Poutine":
                        ((ImageView)findViewById(R.id.image_recette)).setImageResource(R.drawable.poutine);
                        break;
                    case "Tarte aux bleuets":
                        ((ImageView)findViewById(R.id.image_recette)).setImageResource(R.drawable.tarte_aux_bleuets);
                        break;
                }
            }
        }

        BottomNavigationView bottomNav = findViewById(R.id.ingre_prepa_menu);
        bottomNav.setOnItemSelectedListener(navListener);

        //ouverture de l'appli avec l'écran home
        getSupportFragmentManager().beginTransaction().replace(R.id.ingre_prepa_fragment_container,new FavoritesFragment()).commit();

    }

    private BottomNavigationView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener(){
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
        }
        else{
            etoile.setImageResource(R.drawable.etoile);
        }

        recette.setIsFavorite(!recette.getIsFavorite());
    }

}

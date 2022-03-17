package uqac.dim.recipetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_RECETTE = "uqac.dim.recipetracker.MESSAGE1";

    int recette_image = 1;
    int recette_text = 2;
    int recette_description = 3;

    public static Recette poutine;
    public static Recette tarteBleuets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //crée la bar de menu en bas de l'écran
        BottomNavigationView bottomNav = findViewById(R.id.bottom_toolbar);
        bottomNav.setOnItemSelectedListener(navListener);

        //ouverture de l'appli avec l'écran home
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        String[] ingredientsPoutine = {"lait"};
        poutine = new Recette(getString(R.string.poutine),getString(R.string.poutine_description),ingredientsPoutine,R.drawable.poutine);

        tarteBleuets = new Recette(getString(R.string.tarte_bleuets),getString(R.string.poutine_description),ingredientsPoutine,R.drawable.tarte_aux_bleuets);
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


    @SuppressLint("UseCompatLoadingForDrawables")
    public void ouvrirRecette(View v){

        RelativeLayout relativeLayout = (RelativeLayout) v;
        TextView textRecette = (TextView) relativeLayout.getChildAt(recette_text);
        TextView descriptionRecette = (TextView) relativeLayout.getChildAt(recette_description);
        Drawable imageRecette = ((ImageView) relativeLayout.getChildAt(recette_image)).getDrawable();
        int id = imageRecette.getAlpha();
        String[] ingredientsRecette= {"lait"};

        Recette recette = new Recette(textRecette.getText().toString(),descriptionRecette.getText().toString(),ingredientsRecette,0);
        Intent intent = new Intent(MainActivity.this,RecetteActivity.class);
        intent.putExtra(EXTRA_RECETTE,recette);
        startActivity(intent);
    }
}
package uqac.dim.recipetracker.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import uqac.dim.recipetracker.MainActivity;
import uqac.dim.recipetracker.R;
import uqac.dim.recipetracker.RecetteFile.Recette;

public class SearchFragment extends Fragment {

    int recette_favorite = 0;
    int recette_image = 1;
    int recette_text = 2;
    int recette_description = 3;

    EditText searchEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        searchEditText = rootView.findViewById(R.id.search_editText);

        ImageButton button = (ImageButton) rootView.findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                View linearLayout =  getView().findViewById(R.id.search_linearLayout);
                ((LinearLayout) linearLayout).removeAllViews();

                String searchText = searchEditText.getText().toString().toLowerCase(Locale.ROOT);

                for (Recette recette: MainActivity.recetteList) {
                    if(recette.getNom().toLowerCase(Locale.ROOT).contains(searchText) || recette.getDescription().toLowerCase(Locale.ROOT).contains(searchText))
                        initRecette(recette, R.id.search_linearLayout);
                }
            }
        });

        return rootView;
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public void initRecette(Recette recette, int linearLayoutId){
        LinearLayout container_recette = (LinearLayout) getLayoutInflater().inflate(R.layout.container_recette,null,false);

        container_recette.setGravity(Gravity.CENTER_HORIZONTAL);
        RelativeLayout relativeLayout = (RelativeLayout) container_recette.getChildAt(0);
        //Poutine init
        ImageView favorisRecette = (ImageView) relativeLayout.getChildAt(recette_favorite);
        TextView textRecette = (TextView) relativeLayout.getChildAt(recette_text);
        TextView descriptionRecette = (TextView) relativeLayout.getChildAt(recette_description);
        ImageView imageRecette = (ImageView) relativeLayout.getChildAt(recette_image);

        relativeLayout.setContentDescription(recette.getNom());

        relativeLayout.setId(recette.getId());
        Log.i("DIM","Id :"+relativeLayout.getId() + " "+relativeLayout.getContentDescription());
        textRecette.setText(recette.getNom());
        descriptionRecette.setText(recette.getDescription());
        imageRecette.setImageResource(recette.getImage());
        imageRecette.setClipToOutline(true);


        if(recette.getIsFavorite()){
            favorisRecette.setImageResource(R.drawable.favoris);
            favorisRecette.setContentDescription(getString(R.string.favoris));
        }
        else{
            favorisRecette.setImageResource(R.drawable.non_favoris);
            favorisRecette.setContentDescription(getString(R.string.notfavoris));
        }


        View linearLayout =  getView().findViewById(linearLayoutId);

        ((LinearLayout) linearLayout).addView(container_recette);
    }

}

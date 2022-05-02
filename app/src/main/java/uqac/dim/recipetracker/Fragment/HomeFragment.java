package uqac.dim.recipetracker.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import uqac.dim.recipetracker.MainActivity;
import uqac.dim.recipetracker.R;
import uqac.dim.recipetracker.RecetteFile.Recette;


public class HomeFragment extends Fragment {

    int recette_favorite = 0;
    int recette_image = 1;
    int recette_text = 2;
    int recette_description = 3;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        super.onCreate(savedInstanceState);

        for (Recette recette: MainActivity.recetteList) {

            int layoutId = R.id.plats_linearLayout;

            if (recette.getTypeRecette().equals("Entree")) {
                layoutId =  R.id.entrees_linearLayout;
            }

            if (recette.getTypeRecette().equals("Plat")) {
                layoutId =  R.id.plats_linearLayout;
            }
            if (recette.getTypeRecette().equals("Dessert")) {
                layoutId =  R.id.desserts_linearLayout;
            }

            initRecette(recette, layoutId);
            Log.i("DIM",recette.toString());
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void initRecette(Recette recette,int linearLayoutId){
        LinearLayout container_recette = (LinearLayout) getLayoutInflater().inflate(R.layout.container_recette,null,false);

        RelativeLayout relativeLayout = (RelativeLayout) container_recette.getChildAt(0);
        //Recette init
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


        if(recette.getFavorite()){
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

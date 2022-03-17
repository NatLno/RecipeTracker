package uqac.dim.recipetracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {

    int recette_image = 1;
    int recette_text = 2;
    int recette_description = 3;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        super.onCreate(savedInstanceState);

        initRecette(MainActivity.poutine);
        initRecette(MainActivity.tarteBleuets);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void initRecette(Recette recette){
        LinearLayout container_recette = (LinearLayout) getLayoutInflater().inflate(R.layout.container_recette,null,false);

        RelativeLayout relativeLayout = (RelativeLayout) container_recette.getChildAt(0);

        //Poutine init
        TextView textRecette = (TextView) relativeLayout.getChildAt(recette_text);
        TextView descriptionRecette = (TextView) relativeLayout.getChildAt(recette_description);
        ImageView imageRecette = (ImageView) relativeLayout.getChildAt(recette_image);

        textRecette.setText(recette.getNom());
        descriptionRecette.setText(recette.getDescription());
        imageRecette.setImageResource(recette.getImage());


        View linearLayout =  getView().findViewById(R.id.tendances_linearLayout);

        ((LinearLayout) linearLayout).addView(container_recette);
    }

}

package uqac.dim.recipetracker.Fragment;

import android.icu.text.Transliterator;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import uqac.dim.recipetracker.MainActivity;
import uqac.dim.recipetracker.R;
import uqac.dim.recipetracker.RecetteFile.Ingredient;

public class MesIngredientsFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);

        Transliterator accentsconverter = Transliterator.getInstance("NFD; [:M:] Remove; NFC; ");

        int i = 0;
        for (Ingredient ingredient : MainActivity.ingredientList){
            if(ingredient.getFavorite()){
                String nomIngredient = ingredient.getNomIngredient();
                Log.i("DOM", "nomIngre"+nomIngredient);
                nomIngredient = nomIngredient.toLowerCase(Locale.ROOT);
                nomIngredient = accentsconverter.transliterate(nomIngredient);
                initIngredient(nomIngredient,i);
            }
            i++;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_mes_ingredients, container, false);
        return rootView;
    }


    public void initIngredient(String ingredient,int i){
        LinearLayout container_ingredient = (LinearLayout) getLayoutInflater().inflate(R.layout.container_ingredients,null,false);

        container_ingredient.setGravity(Gravity.CENTER_HORIZONTAL);
        RelativeLayout relativeLayout = (RelativeLayout) container_ingredient.getChildAt(0);

        relativeLayout.setId(i);

        int imageIngredient = getResources().getIdentifier(ingredient,"drawable", getContext().getPackageName());

        ((ImageView) relativeLayout.getChildAt(0)).setImageResource(imageIngredient);
        TextView nomIngredient = (TextView) relativeLayout.getChildAt(1);
        ImageButton imageButton = (ImageButton) relativeLayout.getChildAt(2);
        imageButton.setId(i);
        nomIngredient.setText(ingredient);

        if(MainActivity.ingredientList.get(i).getFavorite()){
            imageButton.setImageResource(R.drawable.remove_ingredient_image);
        }
        else{
            imageButton.setImageResource(R.drawable.add_ingredient_image);

        }

        View linearLayout =  getView().findViewById(R.id.mesingredients_linearLayout);

        ((LinearLayout) linearLayout).addView(container_ingredient);
    }
}

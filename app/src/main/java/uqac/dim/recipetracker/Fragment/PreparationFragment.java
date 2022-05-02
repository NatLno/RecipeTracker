package uqac.dim.recipetracker.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import uqac.dim.recipetracker.MainActivity;
import uqac.dim.recipetracker.R;
import uqac.dim.recipetracker.RecetteFile.Instruction;
import uqac.dim.recipetracker.RecetteFile.Recette;

public class PreparationFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        if(intent!=null){
            int id = intent.getIntExtra(MainActivity.EXTRA_RECETTE,0);
            List<Instruction> instructions = MainActivity.rdb.getInstructionForRecipe(id);

            TextView textView = getView().findViewById(R.id.text_prepa);
            StringBuilder str = new StringBuilder();


            int etapeId = 1;
            for(Instruction instruction : instructions){
                str.append("Etape ").append(etapeId).append("\n\n");

                String instru = instruction.getInstruction();
                str.append("\t").append(instru).append("\n\n");
                etapeId++;
            }

            textView.setText(str.toString());

            Instruction instruction = instructions.get(0);
            Log.i("DOM",instruction.getInstruction());


        }




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preparation, container, false);
    }


}

package uqac.dim.recipetracker.RecetteFile;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Recette.class}, version = 3)
public abstract class RecetteBD extends RoomDatabase{

    private static RecetteBD INSTANCE;
    public abstract RecetteDao recetteDao();

    public static RecetteBD getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,RecetteBD.class,"recettedatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

}

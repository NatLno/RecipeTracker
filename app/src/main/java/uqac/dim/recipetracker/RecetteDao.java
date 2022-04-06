package uqac.dim.recipetracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecetteDao {

    @Query("SELECT * FROM recette")
    List<Recette> getAllRecettes();

    @Query("SELECT * FROM recette WHERE id IN (:recetteIds)")
    List<Recette> loadAllByIds(int[] recetteIds);

    @Query("SELECT * FROM recette WHERE nom LIKE :nom LIMIT 1")
    Recette findByNom(String nom);

    @Query("SELECT * FROM recette WHERE id LIKE :id LIMIT 1")
    Recette findById(int id);

    @Query("delete from recette")
    void deleteRecettes();

    @Insert
    void insertAll(Recette recettes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addRecette(Recette recette);

    @Delete
    void delete(Recette recette);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecette(Recette recette);
}

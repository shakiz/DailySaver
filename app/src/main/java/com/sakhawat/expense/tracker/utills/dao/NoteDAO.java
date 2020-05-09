package com.sakhawat.expense.tracker.utills.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.sakhawat.expense.tracker.models.note.Note;
import java.util.List;

@Dao
public interface NoteDAO {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("select * from Note order by Id desc")
    LiveData<List<Note>> getAllNotes();
}

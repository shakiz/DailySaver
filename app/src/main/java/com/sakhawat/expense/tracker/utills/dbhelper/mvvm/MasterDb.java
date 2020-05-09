package com.sakhawat.expense.tracker.utills.dbhelper.mvvm;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.sakhawat.expense.tracker.models.note.Note;
import com.sakhawat.expense.tracker.utills.dao.NoteDAO;

@Database(entities = {Note.class},version = 1)
public abstract class MasterDb extends RoomDatabase {
    private static MasterDb instance;

    public abstract NoteDAO noteDAO();

    public static synchronized MasterDb getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),MasterDb.class,"expense_tracker")
            .fallbackToDestructiveMigration()
            .build();
        }
        return instance;
    }
}

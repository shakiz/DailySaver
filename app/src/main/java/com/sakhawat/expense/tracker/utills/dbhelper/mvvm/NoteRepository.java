package com.sakhawat.expense.tracker.utills.dbhelper.mvvm;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.sakhawat.expense.tracker.models.note.Note;
import com.sakhawat.expense.tracker.utills.dao.NoteDAO;
import java.util.List;

public class NoteRepository {
    private NoteDAO noteDAO;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        MasterDb masterDb = MasterDb.getInstance(application);
        noteDAO = masterDb.noteDAO();
        allNotes = noteDAO.getAllNotes();
    }

    public void insert(Note note){ new InsertAsyncTask(noteDAO).execute(note); };
    public void update(Note note){ new UpdateAsyncTask(noteDAO).execute(note); };
    public void delete(Note note){ new DeleteAsyncTask(noteDAO).execute(note); };
    public LiveData<List<Note>> getAllNotes(){return allNotes;};

    private static class InsertAsyncTask extends AsyncTask<Note,Void,Void>{
        NoteDAO noteDAO;

        public InsertAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Note,Void,Void>{
        NoteDAO noteDAO;

        public UpdateAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.update(notes[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Note,Void,Void>{
        NoteDAO noteDAO;

        public DeleteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.delete(notes[0]);
            return null;
        }
    }
}

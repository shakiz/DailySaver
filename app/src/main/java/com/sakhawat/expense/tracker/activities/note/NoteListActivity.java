package com.sakhawat.expense.tracker.activities.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.widget.LinearLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sakhawat.expense.tracker.R;
import com.sakhawat.expense.tracker.adapters.note.NoteRecyclerAdapter;
import com.sakhawat.expense.tracker.models.note.Note;
import com.sakhawat.expense.tracker.utills.dbhelper.mvvm.NoteViewModel;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private LinearLayout LayoutNoNotes;
    private FloatingActionButton add;
    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private NoteRecyclerAdapter noteRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        init();
        bindUiWithComponents();
    }

    private void init() {
        recyclerView = findViewById(R.id.mRecyclerViewNote);
        LayoutNoNotes = findViewById(R.id.LayoutNoNotes);
        add = findViewById(R.id.add);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
    }

    private void bindUiWithComponents() {
        setAdapter();
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteRecyclerAdapter.setAllNotes(notes);
            }
        });
    }

    private void setAdapter() {
        noteRecyclerAdapter = new NoteRecyclerAdapter(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(noteRecyclerAdapter);
        noteRecyclerAdapter.notifyDataSetChanged();
    }
}

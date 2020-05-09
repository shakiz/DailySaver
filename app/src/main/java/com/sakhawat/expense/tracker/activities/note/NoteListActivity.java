package com.sakhawat.expense.tracker.activities.note;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sakhawat.expense.tracker.R;
import com.sakhawat.expense.tracker.activities.dashboard.DashboardActivity;
import com.sakhawat.expense.tracker.adapters.note.NoteRecyclerAdapter;
import com.sakhawat.expense.tracker.models.note.Note;
import com.sakhawat.expense.tracker.utills.dbhelper.mvvm.NoteViewModel;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
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
                if (notes.size() <= 0){
                    LayoutNoNotes.setVisibility(View.VISIBLE);
                }
                else {
                    noteRecyclerAdapter.setAllNotes(notes);
                    LayoutNoNotes.setVisibility(View.GONE);
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(NoteListActivity.this,NewNoteActivity.class),ADD_NOTE_REQUEST);
            }
        });
    }

    private void setAdapter() {
        noteRecyclerAdapter = new NoteRecyclerAdapter(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(noteRecyclerAdapter);
        noteRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(NoteListActivity.this, DashboardActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            String date = data.getStringExtra("date");

            noteViewModel.insert(new Note(title,description,date));
            Toast.makeText(getApplicationContext(),"Note Saved",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Note Not Saved",Toast.LENGTH_SHORT).show();
        }
    }
}

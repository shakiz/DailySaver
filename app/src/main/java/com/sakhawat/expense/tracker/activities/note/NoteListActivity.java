package com.sakhawat.expense.tracker.activities.note;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sakhawat.expense.tracker.R;

public class NoteListActivity extends AppCompatActivity {
    private LinearLayout LayoutNoNotes;
    private FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        init();
        bindUiWithComponents();
    }

    private void bindUiWithComponents() {

    }

    private void init() {
        LayoutNoNotes = findViewById(R.id.LayoutNoNotes);
        add = findViewById(R.id.add);
    }
}

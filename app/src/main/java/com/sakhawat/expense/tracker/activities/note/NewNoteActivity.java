package com.sakhawat.expense.tracker.activities.note;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sakhawat.expense.tracker.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {
    private EditText Title,Description;
    private FloatingActionButton save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        init();
        bindUiWithComponents();
    }

    private void init() {
        Title = findViewById(R.id.Title);
        Description = findViewById(R.id.Description);
        save = findViewById(R.id.save);
    }

    private void bindUiWithComponents() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String title = Title.getText().toString();
        String description = Description.getText().toString();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            Toast.makeText(getApplicationContext(),"Provide correct data",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra("title",title);
        data.putExtra("description",description);
        data.putExtra("date",getCurrentDate());
        setResult(RESULT_OK,data);
        finish();
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(NewNoteActivity.this,NoteListActivity.class));
    }
}

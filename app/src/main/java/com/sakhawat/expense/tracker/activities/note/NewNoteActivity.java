package com.sakhawat.expense.tracker.activities.note;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sakhawat.expense.tracker.R;
import com.sakhawat.expense.tracker.models.note.Note;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewNoteActivity extends AppCompatActivity {
    private EditText Title,Description;
    private FloatingActionButton save;
    private ImageView listenIcon;
    private TextView listenHint;
    private TextToSpeech textToSpeech;
    private boolean isTextToSpeechOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        init();
        bindUiWithComponents();

        if (getIntent().getSerializableExtra("note") != null){
            loadData();
        }
    }

    private void init() {
        Title = findViewById(R.id.Title);
        Description = findViewById(R.id.Description);
        save = findViewById(R.id.save);
        listenIcon = findViewById(R.id.listenIcon);
        listenHint = findViewById(R.id.listenHint);
    }

    private void bindUiWithComponents() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.US);
                textToSpeech.setSpeechRate(0.7f);
                textToSpeech.setPitch(1);
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

    private void loadData() {
        Note note = (Note) getIntent().getSerializableExtra("note");
        Title.setText(note.getTitle());
        Description.setText(note.getDescription());
        listenHint.setVisibility(View.VISIBLE);
        listenIcon.setVisibility(View.VISIBLE);

        listenIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenNote();
            }
        });
    }

    private void listenNote() {
        if (isTextToSpeechOn) {
            textToSpeech.stop();
            listenIcon.setImageDrawable(getDrawable(R.drawable.ic_record_voice_over));
            isTextToSpeechOn = false;
            Toast.makeText(getApplicationContext(), "Text to Speech Stopped", Toast.LENGTH_SHORT).show();
        }
        else {
            textToSpeech.speak("Dear user your note title is"+Title.getText().toString() + "Now you will hear your note description"+Description.getText().toString(),
                    TextToSpeech.QUEUE_ADD, null, "onNote");
            listenIcon.setImageDrawable(getDrawable(R.drawable.ic_stop));
            isTextToSpeechOn = true;
            Toast.makeText(getApplicationContext(), "Text to Speech Started", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(NewNoteActivity.this,NoteListActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textToSpeech.shutdown();
    }

    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.stop();
    }
}

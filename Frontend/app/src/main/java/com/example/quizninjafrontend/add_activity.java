package com.example.quizninjafrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import repository.MainRepository;

public class add_activity extends AppCompatActivity {

    // inputs from layout file
    private Spinner course;
    private EditText question;
    private EditText answer;
    private EditText source;
    private Button add;
    private ExecutorService executorService;
    private Handler uiHandler;
    private MainRepository mainRepo;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.courses_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        course = findViewById(R.id.spinner);
        question = findViewById(R.id.question_url_input);
        answer  = findViewById(R.id.solution_url_input);
        source = findViewById(R.id.source_input);
        add = findViewById(R.id.add_layout_button);
        executorService = Executors.newSingleThreadExecutor();
        uiHandler = new Handler(getMainLooper(), new add_activity.UiHandlerCallback());
        mainRepo = new MainRepository();

        add.setOnClickListener(v -> {
            String input_course = course.getSelectedItem().toString().trim();
            String input_question = question.getText().toString().trim();
            String input_answer = answer.getText().toString().trim();
            String input_source = source.getText().toString().trim();

            if (input_course.isEmpty() || input_question.isEmpty() || input_answer.isEmpty() || input_source.isEmpty()) {
                Toast.makeText(add_activity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                add(input_course, input_question, input_answer, input_source);
            }
        });

        back = findViewById(R.id.back_button);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(add_activity.this, home_activity.class);
            startActivity(intent);
        });
    }

    private void add(String course, String question, String answer, String source) {
       mainRepo.addQuestion(executorService, uiHandler, question, answer, course, source);
    }

    private class UiHandlerCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(android.os.Message msg) {
            String msg_string = msg.obj.toString();
            if (msg_string.equals("added")) {
                // Add successful
                Toast.makeText(add_activity.this, "Add successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(add_activity.this, home_activity.class);
                startActivity(intent);

            } else {
                // Add failed, show an error message
                Toast.makeText(add_activity.this, "Add failed", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }
}
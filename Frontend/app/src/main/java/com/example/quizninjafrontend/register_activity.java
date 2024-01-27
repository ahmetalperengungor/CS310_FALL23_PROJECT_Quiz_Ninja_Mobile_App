package com.example.quizninjafrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import repository.MainRepository;

public class register_activity extends AppCompatActivity {
    private EditText username;
    private EditText password;

    private Button registerButton;

    private ExecutorService executorService;
    private Handler uiHandler;
    private MainRepository mainRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username = findViewById(R.id.username_input);
        password = findViewById(R.id.password_input);
        registerButton = findViewById(R.id.register_register_button);

        executorService = Executors.newSingleThreadExecutor();
        uiHandler = new Handler(getMainLooper(), new UiHandlerCallback());
        mainRepo = new MainRepository();

        registerButton.setOnClickListener(v -> {
            String input_username = username.getText().toString().trim();
            String input_password = password.getText().toString().trim();

            if (input_username.isEmpty() || input_password.isEmpty()) {
                Toast.makeText(register_activity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            } else {
                register(input_username, input_password);
                Intent intent = new Intent(register_activity.this, login_activity.class);
                startActivity(intent);
            }
        });

    }

    private void register(String username, String password) {
        mainRepo.register(executorService, uiHandler, username, password);
    }
    private class UiHandlerCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            String msg_string = msg.obj.toString();
            if (msg_string.equals("registered")) {
                // Login successful
                Toast.makeText(register_activity.this, "Register successful", Toast.LENGTH_SHORT).show();

                // Create an Intent to open RegisterActivity
                //Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                //startActivity(intent);
                //finish(); // Finish the LoginActivity to prevent going back to it with the back button
            } else {
                // Login failed, show an error message
                Toast.makeText(register_activity.this, "Register failed", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }
}
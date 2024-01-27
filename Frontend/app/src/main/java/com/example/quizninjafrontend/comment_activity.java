package com.example.quizninjafrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class comment_activity extends AppCompatActivity {

    private EditText comment_text;
    private EditText comment_username;
    private Button comment_submit;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ArrayList<comment> comments=(ArrayList<comment>)getIntent().getExtras().getSerializable("comments");
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("comments",comments);
        FragmentListComment fragmentListComment = new FragmentListComment();
        fragmentListComment.setArguments(bundle1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView3,fragmentListComment).commit();
        comment_text = findViewById(R.id.texttext);
        comment_username = findViewById(R.id.username_input_comment);
        comment_submit = findViewById(R.id.comment_send_button);

        comment_submit.setOnClickListener(v -> {
            String username = comment_username.getText().toString();
            String text = comment_text.getText().toString();
            comment comment = new comment(username,text);
            comments.add(comment);
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("comments",comments);
            FragmentListComment fragmentListComment1 = new FragmentListComment();
            fragmentListComment1.setArguments(bundle2);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView3,fragmentListComment1).commit();
        });

        back = findViewById(R.id.back_button);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(this, question_detail_activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        });

    }
}
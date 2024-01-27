package com.example.quizninjafrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;

import repository.MainRepository;

public class question_detail_activity extends AppCompatActivity {
    private ImageView question_image;
    private ImageView solution_image;
    private TextView course;
    private TextView source;
    private Button see_solution;
    private ImageButton comment_button;
    boolean question_downloaded = false;
    boolean solution_downloaded = false;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        question_image = findViewById(R.id.question_detail_image);
        solution_image = findViewById(R.id.solution_detail_image);
        course = findViewById(R.id.course_detail_text);
        source = findViewById(R.id.source_detail_text);
        see_solution = findViewById(R.id.solution_display_button);
        comment_button = findViewById(R.id.see_comment_button);

        question question = (question) getIntent().getSerializableExtra("question");
        course.setText("Course: " + question.getCourse());
        source.setText("Source: " + question.getSource());
        Handler imageHandlerQuestion = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                question_image.setImageBitmap((Bitmap) msg.obj);
                question_downloaded = true;

                return true;
            }
        });
        Handler imageHandlerSolution = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                solution_image.setImageBitmap((Bitmap) msg.obj);
                solution_downloaded = true;

                return true;
            }
        });


        ExecutorService srv = ((QuizNinjaApplication) getApplication()).srv;
        MainRepository repo = new MainRepository();
        repo.downloadImage(srv, question.getQuestion_url(), imageHandlerQuestion);
        repo.downloadImage(srv, question.getSolution_url(), imageHandlerSolution);

        solution_image.setVisibility(ImageView.INVISIBLE);
        see_solution.setOnClickListener(v -> {
            solution_image.setVisibility(ImageView.VISIBLE);

        });

        comment_button.setOnClickListener(v -> {
            //NavController navController = Navigation.findNavController((Activity)ctx, R.id.nav_main);
            Intent intent = new Intent(this, comment_activity.class);
            Bundle bundle = new Bundle();

            bundle.putSerializable("comments", question.getComments());
            intent.putExtras(bundle);
            startActivity(intent);

            //navController.navigate(R.id.action_fragmentListQuestion_to_fragmentQuestionDetails, bundle);


        });
        back = findViewById(R.id.back_button);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(this, home_activity.class);
            startActivity(intent);
        });
    }

    public void downloadImage (ExecutorService srv, String url, Handler handler){
        MainRepository repo = new MainRepository();
        repo.downloadImage(srv, url, handler);
    }
}
package com.example.quizninjafrontend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TextView;

import repository.MainRepository;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>{
    Context ctx;
    List<question> data;

    public QuestionAdapter(Context ctx, List<question> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.question_row_layout, parent, false);

        QuestionViewHolder holder = new QuestionViewHolder(view);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        holder.source_name_txt.setText(data.get(position).getCourse());
        holder.comment_count_txt.setText(data.get(position).getComments().size() + " comments");
        ExecutorService srv = ((QuizNinjaApplication)((Activity)ctx).getApplication()).srv;
        holder.downloadImage(srv, data.get(position).getQuestion_url());
        holder.row.setOnClickListener(v->{

            //NavController navController = Navigation.findNavController((Activity)ctx, R.id.nav_main);
            Intent intent = new Intent(ctx, question_detail_activity.class);
            Bundle bundle = new Bundle();
            intent.putExtra("question", data.get(position));
            ctx.startActivity(intent);
            //navController.navigate(R.id.action_fragmentListQuestion_to_fragmentQuestionDetails, bundle);


        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout row;
        TextView source_name_txt, comment_count_txt;
        ImageView question_img;
        boolean image_downloaded;



        Handler imageHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Bitmap bmp = (Bitmap) msg.obj;
                question_img.setImageBitmap(bmp);
                image_downloaded = true;
                return true;
            }
        });

        public QuestionViewHolder(@NonNull View itemView ){
            super(itemView);
            row=itemView.findViewById(R.id.row_list);
            source_name_txt=itemView.findViewById(R.id.source_name_txt);
            comment_count_txt=itemView.findViewById(R.id.comment_count_txt);
            question_img=itemView.findViewById(R.id.question_image);
        }
        public void downloadImage (ExecutorService srv, String url){
            srv.submit(()->{
                if(image_downloaded) return;
                MainRepository repo = new MainRepository();
                repo.downloadImage(srv,url,imageHandler);
            });
        }

    }
}

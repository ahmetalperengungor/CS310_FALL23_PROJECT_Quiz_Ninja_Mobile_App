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
import java.util.logging.Logger;

import androidx.constraintlayout.widget.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TextView;

import repository.MainRepository;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    Context ctx;
    List<comment> data;

    public CommentAdapter(Context ctx, List<comment> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.comment_row_layout, parent, false);

        CommentViewHolder holder = new CommentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.comment_username_txt.setText(data.get(position).getUsername());
        holder.comment_txt.setText(data.get(position).getText());
        //log to console
        Logger.getAnonymousLogger().info("comment username: "+data.get(position).getUsername());
        Logger.getAnonymousLogger().info("comment text: "+data.get(position).getText());




    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout row;
        TextView comment_username_txt,comment_txt;





        public CommentViewHolder(@NonNull View itemView ){
            super(itemView);
            row=itemView.findViewById(R.id.comment_list);
            comment_username_txt=itemView.findViewById(R.id.comment_username_txt);
            comment_txt=itemView.findViewById(R.id.comment_txt);
        }


    }
}

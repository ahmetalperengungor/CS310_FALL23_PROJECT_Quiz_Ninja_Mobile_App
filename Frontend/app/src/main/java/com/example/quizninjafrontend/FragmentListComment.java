package com.example.quizninjafrontend;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.quizninjafrontend.databinding.FragmentListCommentBinding;
import com.example.quizninjafrontend.databinding.FragmentListQuestionBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import repository.MainRepository;

public class FragmentListComment extends Fragment {


    FragmentListCommentBinding binding;



    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            ArrayList<comment> data = (ArrayList<comment>)msg.obj;

            CommentAdapter adp = new CommentAdapter(getActivity(),data);
            binding.recView.setAdapter(adp);

            return true;
        }
    });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListCommentBinding.inflate(getLayoutInflater());

        binding.recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MainRepository repo = new MainRepository();

        ExecutorService srv=((QuizNinjaApplication)getActivity().getApplication()).srv;

        Bundle bundle = getArguments();

        ArrayList<comment> comments = (ArrayList<comment>) bundle.getSerializable("comments");
        Message msg = new Message();



        msg.obj=comments;
        dataHandler.sendMessage(msg);

        return binding.getRoot();
    }
}
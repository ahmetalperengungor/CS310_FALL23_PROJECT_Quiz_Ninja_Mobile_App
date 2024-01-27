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



import com.example.quizninjafrontend.databinding.FragmentListQuestionBinding;

import java.util.List;
import java.util.concurrent.ExecutorService;

import repository.MainRepository;

public class FragmentListQuestion extends Fragment {


    FragmentListQuestionBinding binding;



    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            List<question> data = (List<question>)msg.obj;

            QuestionAdapter adp = new QuestionAdapter(getActivity(),data);
            binding.recView.setAdapter(adp);

            return true;
        }
    });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListQuestionBinding.inflate(getLayoutInflater());

        binding.recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MainRepository repo = new MainRepository();

        ExecutorService srv=((QuizNinjaApplication)getActivity().getApplication()).srv;

        repo.getAll(srv,dataHandler);


        return binding.getRoot();
    }
}
package com.example.quizninjafrontend;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuizNinjaApplication extends Application {
    ExecutorService srv = Executors.newCachedThreadPool();
}

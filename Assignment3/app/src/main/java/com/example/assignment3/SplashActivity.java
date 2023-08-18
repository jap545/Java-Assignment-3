package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        Runnable tasks = () -> {

            startActivity(new Intent(this, MainActivity.class));
        };

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(tasks, 3, TimeUnit.SECONDS);
    }

    @Override
    protected void onResume() {
        super.onResume();

        startActivity(new Intent(this, MainActivity.class));
    }
}
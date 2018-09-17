package com.example.dogegg.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String LogFileName = "logfile";

    Button flush_textview_btn;
    TextView show_file_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        flush_textview_btn = findViewById(R.id.id_mainactivity_btn);
        show_file_context = findViewById(R.id.id_mainactivity_textview);

    }


}

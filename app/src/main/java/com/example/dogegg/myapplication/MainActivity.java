package com.example.dogegg.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String LogFileName = "logfile";
    public static Context main_context = null;

    Button flush_textview_btn,read_btn;
    TextView show_file_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        main_context = getApplicationContext();

        flush_textview_btn = (Button)findViewById(R.id.id_mainactivity_write_btn);
        show_file_context = (TextView)findViewById(R.id.id_mainactivity_textview);
        read_btn = (Button)findViewById(R.id.id_mainactivity_read_btn);


//        PwdManager pwd_manager = PwdManager.getInstance();
//        List<PwdManager.PwdInfo> list =  pwd_manager.getList();
//        System.out.println("pwd info:"+list.size());
//        PwdManager.PwdInfo info =  pwd_manager.new PwdInfo();
//        info.key = "603";
//        info.pwd = "1111";
//        info.active_session_start = new Date(System.currentTimeMillis());
//        info.active_session_stop  = new Date(System.currentTimeMillis()+10000);
//        pwd_manager.Add(info);
//
//        list = pwd_manager.getList();
//        System.out.println("2:"+list.size());
//        System.out.println(list.get(0).active_session_start.toString());
//        pwd_manager.Add(info);

        flush_textview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        read_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_file_context.setText(read());
            }
        });

    }

    public void save(){
        String data = "Data to save";
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = openFileOutput("data", Context.MODE_APPEND); // 还有一个是 private 的模式,就是重新写一遍.
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if( writer!=null)
                    writer.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public String read(){
        String re = "";
        FileInputStream input = null;
        BufferedReader reader = null;
        try{
            input = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(input));
            String i = null;
            while( (i = reader.readLine())!=null){
                re += i;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if( reader!=null )
                    reader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return re;
    }


}

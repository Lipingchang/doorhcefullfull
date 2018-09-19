package com.example.dogegg.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

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

    Button flush_textview_btn,read_btn,update_btn,del_btn;
    TextView show_file_context;
    EditText keyinput,pwdinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        main_context = getApplicationContext();

        flush_textview_btn = (Button)findViewById(R.id.id_mainactivity_write_btn);
        show_file_context = (TextView)findViewById(R.id.id_mainactivity_textview);
        read_btn = (Button)findViewById(R.id.id_mainactivity_read_btn);
        update_btn = (Button)findViewById(R.id.id_button_update_pwd);
        del_btn = (Button)findViewById(R.id.id_button_del_pwdinfo);

        keyinput = (EditText)findViewById(R.id.id_edittext_keyinput);
        pwdinput = (EditText)findViewById(R.id.id_edittext_pwdinput);

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
              flush_textview_btn.setText("get");
              TextViewRefesh refesh = new TextViewRefesh();
              refesh.execute("hh");
            }
        });

        read_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                read_btn.setText("add");
                new AddAKey().execute();
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PwdManager manager = PwdManager.getInstance();
                PwdManager.PwdInfo info = manager.new PwdInfo();
                info.key = keyinput.getText().toString();
                info.pwd = pwdinput.getText().toString();
                manager.updatePwd(info);
                new Update().execute(info);
            }
        });
        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Del d = new Del();
                d.execute(keyinput.getText().toString());
            }
        });
        pwdinput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    new Login().execute(keyinput.getText().toString(),pwdinput.getText().toString());
                }
            }
        });

    }
    private int startcolor = 0xff00ff00;
    class TextViewRefesh extends AsyncTask<String,Integer,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            show_file_context.setBackgroundColor(startcolor);

        }
        @Override
        protected String doInBackground(String... strings) {
            System.out.println("doin backgroud: "+strings);
            PwdManager manager = PwdManager.getInstance();
            Gson gson = new Gson();
            String k = gson.toJson(manager.getList());

            return k;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            show_file_context.setBackgroundColor(0xffffffff);
            show_file_context.setText(s);
        }
    }
    class AddAKey extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            String key = keyinput.getText().toString();
            String pwd = pwdinput.getText().toString();
            PwdManager manager = PwdManager.getInstance();
            PwdManager.PwdInfo info = manager.new PwdInfo();
            info.pwd = pwd; info.key = key;
            info.active_session_stop = new Date(System.currentTimeMillis());
            info.active_session_start = new Date(System.currentTimeMillis()+10000);
            manager.Add(info);

            return null;
        }
    }
    class Del extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... key) {
            PwdManager manager = PwdManager.getInstance();
            manager.del(key[0]);
            return null;
        }
    }
    class Update extends AsyncTask<PwdManager.PwdInfo,Integer,Void>{
        @Override
        protected Void doInBackground(PwdManager.PwdInfo... pwdInfos) {
            PwdManager manager = PwdManager.getInstance();
            manager.updatePwd(pwdInfos[0]);
            return null;
        }
    }
    class Login extends AsyncTask<String,Integer,Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {
            PwdManager manager = PwdManager.getInstance();
            PwdManager.PwdInfo i =  manager.new PwdInfo();
            i.key = strings[0];
            i.pwd = strings[1];
            return new Boolean(manager.GetAccess(i));
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if( aBoolean ){
                del_btn.setBackgroundColor(0xff00ff00);
            }else{
                del_btn.setBackgroundColor(0xffff0000);
            }
        }
    }


}

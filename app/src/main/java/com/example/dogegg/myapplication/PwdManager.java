package com.example.dogegg.myapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * 管理密码的，把密码和对应的名字保存到文件中，
 * 在要用的时候，可以取出文件中的密码的列表
 *
 * 提供 密码匹配 增加用户 删除用户 修改密码 的方法
 */
public class PwdManager{
    private static String TAG = "PWDMANAGER";
    private static String PWD_FILE_NAME = "PWDFILE";
    private static PwdManager pwd_manager = null;
    private static List<PwdInfo> pwd_list = null;


    public static PwdManager getInstance(){
        if( pwd_manager == null )
            pwd_manager = new  PwdManager();
        return pwd_manager;
    }
    public List<PwdInfo> getList(){
        return pwd_list;
    }
    public boolean Add(PwdInfo info)  {
        Iterator<PwdInfo> it = pwd_list.iterator();
        while (it.hasNext()){
            if(it.next().key.equals(info.key))
                return false;
        }
        pwd_list.add(info);
        saveListToFile();
        return true;
    }
    public boolean GetAccess(PwdInfo info){
        Iterator<PwdInfo> it = pwd_list.iterator();
        while( it.hasNext() ){
            PwdInfo right = it.next();
            if( info.key.equals(right.key) && info.pwd.equals(right.pwd))
                return true;
        }
        return false;
    }
    public boolean del(String key){
        int delpos = -1,count = 0;
        Iterator<PwdInfo> it = pwd_list.iterator();
        while(it.hasNext()){
            PwdInfo info = it.next();
            if( info.key == key ){
                delpos = count;
                break;
            }
            count++;
        }
        if(delpos == -1 )
            return false;

        pwd_list.remove(delpos);
        saveListToFile();
        return true;

    }
    public boolean updatePwd(PwdInfo info){
        int updatePost = -1;
        for( int i = 0; i < pwd_list.size(); i++ ){
            if( pwd_list.get(i).key == info.key ) {
                updatePost = -1;
                break;
            }
        }
        if( updatePost == -1 )
            return false;
        pwd_list.remove(updatePost);
        pwd_list.add(info);
        saveListToFile();
        return true;
    }

    private PwdManager(){
        this.pwd_list = getListFromFile();
    }
    private synchronized List<PwdInfo> getListFromFile(){
        // 先读出 字符串
        String re = "";
        FileInputStream input = null;
        BufferedReader reader = null;
        try{
            input = MainActivity.main_context.openFileInput(PWD_FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(input));
            String i = null;
            while( (i = reader.readLine())!=null){
                re += i;
            }
        }catch (Exception e){
            Log.d(TAG,"File not exist!");
        }finally {
            try{
                if( reader!=null )
                    reader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        pwd_list = new ArrayList<>();
        Gson parser = new Gson();
        PwdInfo[] k = parser.fromJson(re,PwdInfo[].class);
        if( k != null ){
            for( int i = 0; i<k.length; i++ ){
                pwd_list.add(k[i]);
            }
        }

        return pwd_list;
    }
    private synchronized void saveListToFile(){
        Gson parser = new Gson();
        String data = parser.toJson(pwd_list);
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = MainActivity.main_context.openFileOutput(PWD_FILE_NAME, Context.MODE_PRIVATE); // 还有一个是 private 的模式,就是重新写一遍.
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

    public class PwdInfo{
        public String key;
        public String pwd;
        // 有效时间
        // 俩个时间相同的话，说明永久有效
        public Date active_session_start;
        public Date active_session_stop;
    }


}

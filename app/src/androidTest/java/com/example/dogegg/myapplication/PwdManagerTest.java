package com.example.dogegg.myapplication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PwdManagerTest {
    PwdManager manager = null;
    @Before
    public void setUp(){
        manager = PwdManager.getInstance();
    }


    @Test
    public void getList() {
        System.out.println(manager.getList().size());
    }
}
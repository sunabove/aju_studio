package com.hello.service;

import java.io.*;
import java.text.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import android.graphics.*;
import android.graphics.Path.*;

import android.database.*;
import android.database.sqlite.*;


public class HelloServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_service);
    }

    // Method to start the service
    public void startService(View view) {
        startService(new Intent(getBaseContext(), HelloService.class));
    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), HelloService.class));
    }

}

package com.example.pockettutorms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Console;

public class StudentActivity2 extends AppCompatActivity {
    String name=null;
    ImageView imageView;
    Uri imguri=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
try{
        TextView s2Name=(TextView)this.findViewById(R.id.s2name);
        imageView=(ImageView)this.findViewById(R.id.s2imageView);
        Intent intrnt=getIntent();
        name=intrnt.getStringExtra("name");
        s2Name.setText(name);
        if(!intrnt.getStringExtra("imageUri").equals("")){
        imguri=Uri.parse(intrnt.getStringExtra("imageUri"));
        imageView.setImageURI(imguri);}
    }
    catch (Exception e)
    {
        System.out.println(e.getMessage());
    }
    }

}

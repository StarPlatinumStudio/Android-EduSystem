package com.example.pockettutorms;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import databaseHelper.MyHelper;

public class teacherActivity extends AppCompatActivity {
    private TextView titleTex;
    private String nullStr="";
    private Uri imageuri=Uri.parse(nullStr);
    private MyHelper myHelper = null;
    private SQLiteDatabase database = null;
    private String name="";
    private void changeUri(String uri){
        if(myHelper == null)
        {
            myHelper = new MyHelper(this);
        }
        database = myHelper.getWritableDatabase();
        database.execSQL("update baseInfo set uri ='"+uri+"' where name = '"+name+"'");
        Toast.makeText(teacherActivity.this,"头像修改成功",Toast.LENGTH_SHORT).show();
    }

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        titleTex=(TextView)findViewById(R.id.t_title);
        titleTex.setText(name);
        imageView=(ImageView)this.findViewById(R.id.t1imageView);
        Button tinfobtn=(Button)this.findViewById(R.id.t_info_btn);
        Button tquerybtn=(Button)this.findViewById(R.id.t_query_btn);
        ImageButton imageButton=(ImageButton)this.findViewById(R.id.imageChoose2);


        //imageView.setImageResource();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, 0x1);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, 0x1);
            }
        });
        tinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lintent =new Intent(teacherActivity.this,selfinfoUpdate.class);
                lintent.putExtra("name",name);
                lintent.setClass(teacherActivity.this, selfinfoUpdate.class);
                startActivity(lintent);
            }
        });
        tquerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lintent =new Intent(teacherActivity.this,teacherActivity2.class);
                lintent.putExtra("name",name);
                lintent.putExtra("imageUri",imageuri.toString());
                lintent.setClass(teacherActivity.this, teacherActivity2.class);
                startActivity(lintent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        // TODO Auto-generated method stub
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                imageuri=data.getData();
                imageView.setImageURI(imageuri);
                //changeUri(imageuri.toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

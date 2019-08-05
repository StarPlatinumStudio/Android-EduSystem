package com.example.pockettutorms;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import databaseHelper.MyHelper;


public class StudentMainActivity extends AppCompatActivity {
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
        Toast.makeText(StudentMainActivity.this,"头像修改成功",Toast.LENGTH_SHORT).show();
    }

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent smIntrnt=getIntent();
        name=smIntrnt.getStringExtra("name");
        titleTex=(TextView)findViewById(R.id.s_title);
        titleTex.setText("欢迎:"+name);
        imageView=(ImageView)this.findViewById(R.id.s1imageView);
        Button sinfobtn=(Button)this.findViewById(R.id.s_info_btn);
        Button squerybtn=(Button)this.findViewById(R.id.s_query_btn);
        ImageButton imageButton=(ImageButton)this.findViewById(R.id.imageChoose);


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
        sinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lintent =new Intent(StudentMainActivity.this,selfinfoUpdate.class);
                lintent.putExtra("name",name);
                lintent.setClass(StudentMainActivity.this, selfinfoUpdate.class);
                startActivity(lintent);
            }
        });
      squerybtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent lintent =new Intent(StudentMainActivity.this,StudentActivity2.class);
              lintent.putExtra("name",name);
              lintent.putExtra("imageUri",imageuri.toString());
              lintent.setClass(StudentMainActivity.this, StudentActivity2.class);
              startActivity(lintent);
          }
      });

//        if(myHelper == null)
//        {
//            myHelper = new MyHelper(this);
//        }
//        database = myHelper.getWritableDatabase();
//        Cursor cursor = database.rawQuery("select * from baseInfo where name='"+name+"'", null);
//
//        cursor.moveToFirst();
//        String uri= cursor.getString(cursor.getColumnIndex("uri"));
//        imageuri=Uri.parse(uri);
//        imageView.setImageURI(imageuri);
//        cursor.close();
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        // TODO Auto-generated method stub
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                imageuri=data.getData();
                imageView.setImageURI(imageuri);
                changeUri(imageuri.toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

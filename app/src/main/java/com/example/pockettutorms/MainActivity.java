package com.example.pockettutorms;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import databaseHelper.MyHelper;
import service.MyService;

public class MainActivity extends PermissionActivity
       {// implements NavigationView.OnNavigationItemSelectedListener
private EditText account;
private EditText password;
           private MyHelper myHelper = null;
           private SQLiteDatabase database = null;
           TextView textView = null;
           private void searchDatabase(String name) // 查询数据库中的数据
           {
               if(myHelper == null)
               {
                   myHelper = new MyHelper(this);
               }
               database = myHelper.getWritableDatabase();
               Cursor cursor = database.rawQuery("select * from baseInfo where name='"+name+"'", null);

               try{
               cursor.moveToFirst();
               String showstr= cursor.getString(cursor.getColumnIndex("password"));
                   String typestr= cursor.getString(cursor.getColumnIndex("type"));
               if(password.getText().toString().equals(showstr)){

//                   cursor = database.rawQuery("select type from baseInfo where name='"+name+"'", null);
//                   String typestr= cursor.getString(0);
                   Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                   if(typestr.equals("Student")){
                   Intent lintent =new Intent(MainActivity.this,StudentMainActivity.class);
                  lintent.putExtra("name",name);
                   lintent.setClass(MainActivity.this, StudentMainActivity.class);
                   startActivity(lintent);}
               }
               else {
                   Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
               }
               return;}
               catch (Exception ex)
               {
                   Toast.makeText(MainActivity.this, "没有找到该账号",Toast.LENGTH_SHORT).show();
               }
           }
           // Method to stop the service
           public void stopService(View view) {
               stopService(new Intent(getBaseContext(), MyService.class));
           }
           // Method to start the service
           public void startService(View view) {
               startService(new Intent(getBaseContext(), MyService.class));
           }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = new Intent(MainActivity.this, MyService.class);
        i.putExtra("name", "SurvivingwithAndroid");
        MainActivity.this.startService(i);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = new TextView(this);
        Button regiestButton=(Button)this.findViewById(R.id.regiest);
        Button teacherBtn=(Button)this.findViewById(R.id.teacherBtn);
        Button adminBtn=(Button)this.findViewById(R.id.adminBtn);
        Button loginIn=(Button)this.findViewById(R.id.loginIn);
        account=(EditText)findViewById(R.id.account);
        password=(EditText)findViewById(R.id.password);

        boolean grant = PermissionHelper.isPermissionGranted(this, Manifest.permission.READ_SMS);
        Toast.makeText(this, "权限" + (grant ? "已允许" : "未允许"), Toast.LENGTH_SHORT).show();
        PermissionHelper.runOnPermissionGranted(this, () -> {
            // 权限通过
            Toast.makeText(MainActivity.this, "已通过", Toast.LENGTH_SHORT).show();
        }, () -> {
            // 权限不通过
            Toast.makeText(MainActivity.this, "未通过", Toast.LENGTH_SHORT).show();
        }, Manifest.permission.READ_SMS);
        PermissionHelper.runOnPermissionGranted(this, () -> {
            // 权限通过
            Toast.makeText(MainActivity.this, "已通过", Toast.LENGTH_SHORT).show();
        }, () -> {
            // 权限不通过
            Toast.makeText(MainActivity.this, "未通过", Toast.LENGTH_SHORT).show();
        }, Manifest.permission.RECEIVE_SMS);
        PermissionHelper.runOnPermissionGranted(this, () -> {
            // 权限通过
            Toast.makeText(MainActivity.this, "已通过", Toast.LENGTH_SHORT).show();
        }, () -> {
            // 权限不通过
            Toast.makeText(MainActivity.this, "未通过", Toast.LENGTH_SHORT).show();
        }, Manifest.permission.INTERNET);
        PermissionHelper.runOnPermissionGranted(this, () -> {
            // 权限通过
            Toast.makeText(MainActivity.this, "已通过", Toast.LENGTH_SHORT).show();
        }, () -> {
            // 权限不通过
            Toast.makeText(MainActivity.this, "未通过", Toast.LENGTH_SHORT).show();
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        regiestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,regiest.class);
                intent.setClass(MainActivity.this, regiest.class);
                startActivity(intent);
            }
        });
        teacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,teacherActivity.class);
                intent.putExtra("name","测试教师");
                intent.setClass(MainActivity.this, teacherActivity.class);
                startActivity(intent);
            }
        });
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,adminActivity.class);
                intent.setClass(MainActivity.this, adminActivity.class);
                startActivity(intent);
            }
        });
        loginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountTxt=account.getText().toString();
                String passwordTxt=password.getText().toString();
              if(accountTxt.equals("")){
                  Toast.makeText(MainActivity.this,"账户名不可为空"+accountTxt,Toast.LENGTH_SHORT).show();
                  return;
              }
                if(passwordTxt.equals("")){
                    Toast.makeText(MainActivity.this,"密碼不可为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                searchDatabase(accountTxt);
            }
        });
    }
}

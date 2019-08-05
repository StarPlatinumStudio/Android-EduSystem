package com.example.pockettutorms;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import databaseHelper.MyHelper;

public class selfinfoUpdate extends AppCompatActivity {
    TextView siname = null;
    private EditText oldpassword;
    private EditText password;
    private EditText password2;
    private MyHelper myHelper = null;
    private SQLiteDatabase database = null;
    private void changePassword(String newpassword,String name){
        if(myHelper == null)
        {
            myHelper = new MyHelper(this);
        }
        database = myHelper.getWritableDatabase();
        database.execSQL("update baseInfo set password ="+newpassword+" where name = '"+name+"'");
        Toast.makeText(selfinfoUpdate.this,"密码修改成功",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfinfo_update);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent smIntrnt=getIntent();
        final String name=smIntrnt.getStringExtra("name");
        siname=(TextView)findViewById(R.id.siNameText);
        siname.setText(name);

        oldpassword=(EditText)this.findViewById(R.id.siOldpassword);
        password=(EditText)this.findViewById(R.id.newpassword);
        password2=(EditText)this.findViewById(R.id.newpassword2);
        Button save=(Button)this.findViewById(R.id.siSaveBtn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpasswordstr=oldpassword.getText().toString();
                String newpasswordstr=password.getText().toString();
                String newpasswordstr2=password2.getText().toString();
                if(oldpasswordstr.equals("")){
                    Toast.makeText(selfinfoUpdate.this,"旧密码不可为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newpasswordstr.equals("")||newpasswordstr2.equals("")){
                    Toast.makeText(selfinfoUpdate.this,"新密码不可为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!newpasswordstr.equals(newpasswordstr2)){
                    Toast.makeText(selfinfoUpdate.this,"旧密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newpasswordstr.equals(oldpasswordstr)){
                    Toast.makeText(selfinfoUpdate.this,"新旧密码一致,请修改",Toast.LENGTH_SHORT).show();
                    return;
                }
                changePassword(newpasswordstr,name);
            }
        });
    }

}

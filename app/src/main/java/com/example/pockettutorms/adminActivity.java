package com.example.pockettutorms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class adminActivity extends AppCompatActivity {
private Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button asinfo=(Button)this.findViewById(R.id.asinfobtn);
        asinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(adminActivity.this,ASinfoAtivity.class);
                intent.setClass(adminActivity.this, ASinfoAtivity.class);
                startActivity(intent);
            }
        });
        add=(Button)findViewById(R.id.APBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(adminActivity.this,ProviderActivity.class);
                intent.setClass(adminActivity.this, ProviderActivity.class);
                startActivity(intent);
            }
        });
    }

}

package com.example.pockettutorms;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import databaseHelper.DatabaseStatic;
import databaseHelper.MyHelper;

public class regiest extends AppCompatActivity {
    private Button backBtn,SMSbtn;
    private Button regiestBtn;
    private EditText name,tel,codeinput;
    private EditText password;
    private EditText password2;
    private RadioGroup radioGroup;
    private RadioButton male;
    private RadioButton famale;
    private MyHelper myHelper = null;
    private SQLiteDatabase database = null;
    private ArrayList<String> prvcList, cityList, areaList;
    private ArrayAdapter<String> prvcAdapter, cityAdapter, areaAdapter;
    private HashMap<String, ArrayList<String>> mapProvince, mapCity;
    private Spinner spProvince, spCity, spArea;
    private String code="";
    private String nullStr="";
    private Uri imageuri=Uri.parse(nullStr);
    private ImageView imageView;
    HttpUnits httpUnits=new HttpUnits();
    private String Code;

    private void changeUri(String uri){
        Toast.makeText(regiest.this,"头像更换成功",Toast.LENGTH_SHORT).show();
    }
    private void createDatabase() // 创建或者打开数据库
    {
        myHelper = new MyHelper(this);
        myHelper.getWritableDatabase();
    }
    private  void insertDatabase(String cname,String cpassword,String cSex){
        if(myHelper == null)
        {
            myHelper = new MyHelper(this);
        }
        database = myHelper.getWritableDatabase();
        ContentValues cV = new ContentValues();
        cV.put(DatabaseStatic.NAME,cname);
        cV.put(DatabaseStatic.PASSWORD,cpassword);
        cV.put(DatabaseStatic.SEX,cSex);
        cV.put(DatabaseStatic.TYPE,"Student");
        database.insert(DatabaseStatic.BASEINFO_TABLE_NAME, null, cV);
        Toast.makeText(regiest.this, "插入数据成功", Toast.LENGTH_SHORT).show();
    }

    private void initXmlData() {
        XmlResourceParser parser = this.getResources().getXml(R.xml.city);
        try {
            int eventType = parser.getEventType();
            prvcList = new ArrayList<>();
            cityList = new ArrayList<>();
            areaList = new ArrayList<>();
            mapProvince = new HashMap<>();
            mapCity = new HashMap<>();
            String provinceName = null;
            String cityName = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();
                        if (tagName.equals("province")) {
                            provinceName = parser.getAttributeValue(0);
                            prvcList.add(provinceName);
                        } else if (tagName.equals("city")) {
                            cityName = parser.getAttributeValue(0);
                            cityList.add(cityName);
                        } else if (tagName.equals("area")) {
                            areaList.add(parser.getAttributeValue(0));
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if ("city".equals(parser.getName())) {
                            mapCity.put(cityName, areaList);
                            cityName = null;
                            areaList = new ArrayList<>();
                        } else if ("province".equals(parser.getName())) {
                            mapProvince.put(provinceName, cityList);
                            provinceName = null;
                            cityList = new ArrayList<>();
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        prvcAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, prvcList);
        prvcAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spProvince.setAdapter(prvcAdapter);
    }
    //XML解析：设置监听
    private void setItemChangedListener() {
        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> cityList = mapProvince.get(parent.getItemAtPosition(position).toString());
                cityAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, cityList);
                cityAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spCity.setAdapter(cityAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> areaList = mapCity.get(parent.getItemAtPosition(position).toString());
                areaAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, areaList);
                areaAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spArea.setAdapter(areaAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                changeUri(imageuri.toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiest);

        backBtn=(Button)findViewById(R.id.back);
        regiestBtn=(Button) findViewById(R.id.regiestBtn);
        name=(EditText) findViewById(R.id.name);
        password=(EditText)findViewById(R.id.password);
        password2=(EditText) findViewById(R.id.password2);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        male=(RadioButton)findViewById(R.id.male);
        famale=(RadioButton)findViewById(R.id.famale);
        imageView=(ImageView)this.findViewById(R.id.regiestImg);
        spProvince = findViewById(R.id.spProvince);
        spCity = findViewById(R.id.spCity);
        spArea = findViewById(R.id.spArea);
        SMSbtn=findViewById(R.id.sendSMS);
        tel=findViewById(R.id.tel);
        codeinput=findViewById(R.id.codeinput);
        SMSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telContent=tel.getText().toString();
                if (telContent.equals(""))
                {
                    Toast.makeText(regiest.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                }
                else {

                  code= httpUnits.sendGet(telContent);
                    Toast.makeText(regiest.this,"短信已发送\n按钮已失效",Toast.LENGTH_SHORT).show();
                    SMSbtn.setEnabled(false);
                }
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
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent=new Intent(regiest.this,MainActivity.class);
                mainIntent.setClass(regiest.this,MainActivity.class);
                startActivity(mainIntent);
            }
        });
        regiestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cname=name.getText().toString();
                String cpassword=password.getText().toString();
                String cpassword2=password2.getText().toString();
                String cSex="0";
                String inputcode=codeinput.getText().toString();
                if(!inputcode.equals(code))
                {
                    Toast.makeText(regiest.this,"验证码错误",Toast.LENGTH_SHORT).show();
                    return;
                }
            if(cname.equals("")){
                Toast.makeText(regiest.this,"账户名不可为空",Toast.LENGTH_SHORT).show();
                return;
            }
                if(cpassword.equals("")){
                    Toast.makeText(regiest.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(cpassword2.equals("")){
                    Toast.makeText(regiest.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }

             if(male.isChecked()==false&&famale.isChecked()==false){
                   Toast.makeText(regiest.this,"请选择性别"+male.isChecked(),Toast.LENGTH_SHORT).show();
                   return;
               }
               if(male.isChecked())
                   cSex="男";
               else{cSex="女";}
                if(!cpassword.equals(cpassword2)){
                    Toast.makeText(regiest.this,"密码输入不一致",Toast.LENGTH_SHORT).show();
                    return;
                }
                createDatabase();
                insertDatabase(cname,cpassword,cSex);
            }
        });
                Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initXmlData();
        setItemChangedListener();
    }

}

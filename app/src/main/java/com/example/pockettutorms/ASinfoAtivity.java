package com.example.pockettutorms;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import databaseHelper.Entity;
import databaseHelper.MyHelper;

public class ASinfoAtivity extends AppCompatActivity {
private ListView lv;
    private TextView tv1;//item.xml里的TextView：Textviewname
    private TextView tv2;//item.xml里的TextView：Textviewage
    private ImageView im;
private BaseAdapter adapter;
private List<Entity> entityList =new ArrayList<Entity>();
    private MyHelper myHelper = null;
    private SQLiteDatabase database = null;
    private Button add;
    List<Entity> accountEntities=new ArrayList<Entity>();


private List<Entity> getEntityList()
{
    if(myHelper == null)
    {
        myHelper = new MyHelper(this);
    }
    database = myHelper.getWritableDatabase();
    Cursor cursor = database.rawQuery("select * from baseInfo", null);
    cursor.moveToFirst();// 显示数据库的内容
    for(; !cursor.isAfterLast(); cursor.moveToNext()) // 获取查询游标中的数据
    {
        Entity entity =new Entity("","","","","");
        entity.name= cursor.getString(cursor.getColumnIndex("name"));
        entity.password=cursor.getString(cursor.getColumnIndex("password"));
        entity.sex=cursor.getString(cursor.getColumnIndex("sex"));
        entity.type=cursor.getString(cursor.getColumnIndex("type"));
        accountEntities.add(entity);
    }
    return  accountEntities;
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asinfo_ativity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       lv=(ListView)findViewById(R.id.sinfo_lv);
       entityList = getEntityList();
        add=(Button)findViewById(R.id.SaddBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ASinfoAtivity.this,ProviderActivity.class);
                intent.setClass(ASinfoAtivity.this, ProviderActivity.class);
                startActivity(intent);
            }
        });
       adapter=new BaseAdapter() {
           @Override
           public int getCount() {
               return entityList.size();
           }

           @Override
           public Object getItem(int position) {
               return entityList.get(position);
           }

           @Override
           public long getItemId(int position) {
               return position;
           }

           @Override
           public View getView(int position, View convertView, ViewGroup parent) {
               LayoutInflater inflater = ASinfoAtivity .this.getLayoutInflater();
               View view;

               if (convertView==null) {
                   //因为getView()返回的对象，adapter会自动赋给ListView
                   view = inflater.inflate(R.layout.item, null);
               }else{
                   view=convertView;
                   Log.i("info","有缓存，不需要重新生成"+position);
               }
               im=(ImageView)view.findViewById(R.id.imageItem);

               tv1 = (TextView) view.findViewById(R.id.Textviewname);//找到Textviewname
               tv1.setText(entityList.get(position).getName());//设置参数

               tv2 = (TextView) view.findViewById(R.id.Textvisex);//找到Textviewage
               tv2.setText(entityList.get(position).getSex());//设置参数
               return view;
           }
       };
       lv.setAdapter(adapter);
    }

}

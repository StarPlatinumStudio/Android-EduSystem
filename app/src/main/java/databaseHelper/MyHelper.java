package databaseHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MyHelper extends SQLiteOpenHelper {
    public static String CREATE_TABLE = "create table "+ DatabaseStatic.BASEINFO_TABLE_NAME+"(" +
            DatabaseStatic.NAME+ " varchar(30) primary key, " +
            DatabaseStatic.TYPE + " varchar(30) not null, " +
            DatabaseStatic.SEX + " varchar(5) not null, " +
            DatabaseStatic.URI+" varchar(128),"+
            DatabaseStatic.PASSWORD + " varchar(30) not null)"; // 用于创建表的SQL语句
    private Context myContext=null;
    private static String CREATE_ATTENDANCE = "create table attendanceInfo(ano varchar(30) primary key,name varchar(30) not null,lesson varchar(30) not null,lessonno int not null)";
    public MyHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DatabaseStatic.DATABASE_NAME, null, DatabaseStatic.DATABASE_VERSION);
    }
    public MyHelper(Context context)
    {
        super(context, DatabaseStatic.DATABASE_NAME, null, DatabaseStatic.DATABASE_VERSION);
        myContext = context;
    }
    public List<Entity> queryAll(){
        SQLiteDatabase database = null;
        List<Entity> accountEntities=null;
        Entity entity =null;
        database = getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from baseInfo", null);
        if(cursor.moveToFirst()) // 显示数据库的内容
        {
            for(; !cursor.isAfterLast(); cursor.moveToNext()) // 获取查询游标中的数据
            {
                entity.name=cursor.getString(cursor.getColumnIndex("name"));
                entity.password=cursor.getString(cursor.getColumnIndex("password"));
                entity.sex=cursor.getString(cursor.getColumnIndex("sex"));
                entity.type=cursor.getString(cursor.getColumnIndex("type"));
                accountEntities.add(entity);
            }
        }
        return  accountEntities;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("UseDatabase", "创建数据库");
        Toast.makeText(myContext, "创建数据库", Toast.LENGTH_SHORT).show();
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}

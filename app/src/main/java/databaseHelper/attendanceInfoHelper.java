package databaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class attendanceInfoHelper extends SQLiteOpenHelper {

    private Context myContext=null;
    private static String CREATE_ATTENDANCE = "create table attendanceInfo(ano varchar(30) primary key,name varchar(30) not null,lesson varchar(30) not null,lessonno int not null)";
    public attendanceInfoHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DatabaseStatic.DATABASE_NAME, null, DatabaseStatic.DATABASE_VERSION);
    }
    public attendanceInfoHelper(Context context)
    {
        super(context, DatabaseStatic.DATABASE_NAME, null, DatabaseStatic.DATABASE_VERSION);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("UseDatabase", "创建数据库");
        Toast.makeText(myContext, "创建数据库", Toast.LENGTH_SHORT).show();
        db.execSQL(CREATE_ATTENDANCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}

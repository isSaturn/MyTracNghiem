package com.example.mytracnghiem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

class quanlycauhoi extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tablecauhoi";
    private static final String KEY_ID = "_id";
    private static final String KEY_CAUHOI = "cauhoi";
    private static final String KEY_A = "cau_a";
    private static final String KEY_B = "cau_b";
    private static final String KEY_C = "cau_c";
    private static final String KEY_D = "cau_d";
    private static final String KEY_DA = "dapan";
    private static String DB_PATH = "/data/data/com.example.mytracnghiem/databases/";
    private static String DB_NAME = "databasecauhoi";
    private final Context myContext;
    private SQLiteDatabase myDataBase;


    public quanlycauhoi(@Nullable Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,CAUHOI TEXT,CAUA TEXT,CAUB TEXT,CAUC TEXT,CAUD TEXT,DAPAN TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.d("error1", "chua co database");
        }

        if (checkDB != null)
            checkDB.close();

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {

        //mo db trong thu muc assets nhu mot input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        //duong dan den db se tao
        String outFileName = DB_PATH + DB_NAME;

        //mo db giong nhu mot output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //truyen du lieu tu inputfile sang outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Dong
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase(); //kiem tra db

        if (dbExist) {
            //khong lam gi ca, database da co roi
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase(); //chep du lieu
            } catch (IOException e) {
                Log.d("error2", "Error copying databases");
            }
        }
    }

    public Cursor laytatcacauhoi() {
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "select * from tablecauhoi";
        Cursor cursor = database.rawQuery(sql, null);
        return cursor;
    }
    public List<cauhoi> layNcaungaunhien(int socau)
    {
        List<cauhoi> ds_cauhoi=new ArrayList<cauhoi>();
        String limit="0,"+socau;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor contro=db.query(TABLE_NAME, //ten bang
                null , //danh sach cot can lay
                null, //dieu kien where
                null, //doi so dieu kien where
                null, //bieu thuc groupby
                null, //bieu thuc having
                "random()", //bieu thuc orderby
                limit //"0,3" //bieuthuc limit
        );

        contro.moveToFirst();
        do{
            cauhoi x=new cauhoi();
            x._id=Integer.parseInt(contro.getString(0));
            x.cauhoi=contro.getString(1);
            x.cau_a=contro.getString(2);
            x.cau_b=contro.getString(3);
            x.cau_c=contro.getString(4);
            x.cau_d=contro.getString(5);
            x.dapan=contro.getString(6);
            ds_cauhoi.add(x);
        }while(contro.moveToNext());

        return ds_cauhoi;
    }
    public boolean insertData(String cauhoi, String caua, String caub, String cauc, String caud, String dapan) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CAUHOI,cauhoi);
        contentValues.put(KEY_A,caua);
        contentValues.put(KEY_B,caub);
        contentValues.put(KEY_C,cauc);
        contentValues.put(KEY_D,caud);
        contentValues.put(KEY_DA,dapan);
        long result = database.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
}

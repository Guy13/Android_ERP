package com.example.ambuj.erp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by Ambuj on 12-03-2015.
 */
public class Database_Helper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.example.ERP/databases/";
    private static String DB_NAME = "database";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public Database_Helper(Context context) {
        super(context,DB_NAME,null,1);
        this.myContext = context;

    }

    public void createDataBase() throws IOException{
        boolean DBExist = checkDataBase();

        if (DBExist) {
        }
        else{
            this.getReadableDatabase();

            try {
                copyDataBase();
            }catch (IOException e){
                throw new Error("Error copying DataBase");
            }
        }
    }


    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        }catch (SQLiteException e){

        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB !=null ? true : false;


    }


    private void copyDataBase() throws IOException{

        InputStream myInput = myContext.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException{
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close(){

        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db){

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public boolean Login(String username, String password) throws SQLException
    {
        Cursor mCursor = myDataBase.rawQuery("SELECT * FROM userinfo WHERE username=? AND password=?", new String[]{username,password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

}

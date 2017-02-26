package uk.ac.acnh585city.searchmoviesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteMoviesdb extends SQLiteOpenHelper {
    public static final String DB_name="results.db";
    public static final int DB_version=1;
    public static final String TABLE_NAME="results";
    public static final String KEYWORD="keyword";
    public static final String JSON="json";

    public SQLiteMoviesdb(Context context) {
        super(context, DB_name, null, DB_version);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String TABLE_CREATE="CREATE TABLE "+TABLE_NAME+" ("+KEYWORD+" TEXT, "+JSON+" TEXT)";
        db.execSQL(TABLE_CREATE);
    }
     public void insertData(String keyword, String json){
         SQLiteDatabase db=this.getReadableDatabase();
         ContentValues values=new ContentValues();
         values.put(KEYWORD, keyword);
         values.put(JSON, json);
         db.insert(TABLE_NAME, null, values);
    }

    public String getResults(String keyword){
        SQLiteDatabase db=this.getReadableDatabase();
        final String sql="SELECT json FROM "+TABLE_NAME+" WHERE keyword='"+keyword+"' ";
        Cursor cursor=db.rawQuery(sql,null);
        String result="";
        while(cursor.moveToNext()){
            result=result+cursor.getString(0)+"\n";
        }
        cursor.close();
        db.close();
        return result;
    }

}

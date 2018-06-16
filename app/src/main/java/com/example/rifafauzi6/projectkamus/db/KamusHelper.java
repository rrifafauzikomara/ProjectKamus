package com.example.rifafauzi6.projectkamus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rifafauzi6.projectkamus.model.KamusEngModel;
import com.example.rifafauzi6.projectkamus.model.KamusIndModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.rifafauzi6.projectkamus.db.DatabaseHelper.DatabaseContract.KamusColumns.DESCRIPTION;
import static com.example.rifafauzi6.projectkamus.db.DatabaseHelper.DatabaseContract.KamusColumns.WORD;
import static com.example.rifafauzi6.projectkamus.db.DatabaseHelper.DatabaseContract.TABLE_ENG;
import static com.example.rifafauzi6.projectkamus.db.DatabaseHelper.DatabaseContract.TABLE_IND;

public class KamusHelper {

    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<KamusEngModel> getDataByNameENG (String name){
        Cursor cursor = database.query(TABLE_ENG,null,WORD+" LIKE ?",new String[]{name +"%"},null,null,_ID+" ASC",null);
        cursor.moveToFirst();
        ArrayList<KamusEngModel> kamusEngModels = new ArrayList<>();
        KamusEngModel kamusEngModel;
        if (cursor.getCount()>0){
            do {
                kamusEngModel = new KamusEngModel();
                kamusEngModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusEngModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                kamusEngModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                kamusEngModels.add(kamusEngModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return kamusEngModels;
    }

    public ArrayList<KamusIndModel> getDataByNameIND (String name){
        Cursor cursor = database.query(TABLE_IND,null,WORD+" LIKE ?",new String[]{name +"%"},null,null,_ID+" ASC",null);
        cursor.moveToFirst();
        ArrayList<KamusIndModel> kamusIndModels = new ArrayList<>();
        KamusIndModel kamusIndModel;
        if (cursor.getCount()>0){
            do {
                kamusIndModel = new KamusIndModel();
                kamusIndModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusIndModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                kamusIndModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                kamusIndModels.add(kamusIndModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return kamusIndModels;
    }

    public ArrayList<KamusEngModel> getAllDataENG(){
        Cursor cursor = database.query(TABLE_ENG,null,null,null,null,null,_ID+" ASC",null);
        cursor.moveToFirst();
        ArrayList<KamusEngModel> kamusEngModels = new ArrayList<>();
        KamusEngModel kamusEngModel;
        if (cursor.getCount()>0){
            do {
                kamusEngModel = new KamusEngModel();
                kamusEngModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusEngModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                kamusEngModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                kamusEngModels.add(kamusEngModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return kamusEngModels;
    }

    public ArrayList<KamusIndModel> getAllDataIND(){
        Cursor cursor = database.query(TABLE_IND,null,null,null,null,null,_ID+" ASC",null);
        cursor.moveToFirst();
        ArrayList<KamusIndModel> kamusIndModels = new ArrayList<>();
        KamusIndModel kamusIndModel;
        if (cursor.getCount()>0){
            do {
                kamusIndModel = new KamusIndModel();
                kamusIndModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusIndModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                kamusIndModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                kamusIndModels.add(kamusIndModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return kamusIndModels;
    }

    public void insertEng(KamusEngModel kamusEngModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORD, kamusEngModel.getWord());
        contentValues.put(DESCRIPTION, kamusEngModel.getDescription());
        database.insert(TABLE_ENG, null, contentValues);
    }

    public void insertInd(KamusIndModel kamusIndModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORD, kamusIndModel.getWord());
        contentValues.put(DESCRIPTION, kamusIndModel.getDescription());
        database.insert(TABLE_IND, null, contentValues);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }
}

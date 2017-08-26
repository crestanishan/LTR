package com.nishan.tasker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NaRan on 8/18/17 at 20:14.
 */

public class DatabaseHandler extends SQLiteOpenHelper{

    Task task;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "taskDetails";
    private static final String TASK_TABLE = "TaskTable";
    public static final String COL_ID = "Id";
    public static final String COL_LAT = "Latitude";
    public static final String COL_LOG = "Longitude";
    public static final String COL_DATE = "Date";
    public static final String COL_TIME = "Time";
    public static final String COL_TASK = "Task_details";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String TABLE = "CREATE TABLE " + TASK_TABLE + "("
                + COL_ID + " INTEGER PRIMARY KEY,"
                + COL_LAT + " TEXT,"
                + COL_LOG + " TEXT,"
                + COL_DATE + " TEXT,"
                + COL_TIME + " TEXT,"
                + COL_TASK + " TEXT" + ")";
        db.execSQL(TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
        // Create tables again
        onCreate(db);

    }

    public void insertDatabase(Task task){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_LAT, task.getLat());
        values.put(COL_LOG, task.getLog());
        values.put(COL_DATE, task.getDate());
        values.put(COL_TIME, task.getTime());
        values.put(COL_TASK, task.getTaskDetails() );

        // Inserting Row
        db.insert(TASK_TABLE, null, values);
        db.close();

    }

    public List<Task> getAllInquiries() {
        List<Task> taskList = new ArrayList<Task>();

        String selectQuery = "SELECT  * FROM " + TASK_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setLat(cursor.getString(1));
                task.setLog(cursor.getString(2));
                task.setDate(cursor.getString(3));
                task.setTime(cursor.getString(4));
                task.setTaskDetails(cursor.getString(5));

                taskList.add(task);
            } while (cursor.moveToNext());
        }

        return taskList;
    }

    public void deleteInquiry(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TASK_TABLE, COL_ID + " = ?",new String[]{ String.valueOf(id) });
        db.close();
    }


}



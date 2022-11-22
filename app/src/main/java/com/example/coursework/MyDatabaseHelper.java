package com.example.coursework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    public static final String DATABASE_NAME = "MyCoursework.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "trip";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "trip_name";
    public static final String COLUMN_DESTINATION = "trip_destination";
    public static final String COLUMN_DATE = "trip_date";
    public static final String COLUMN_ASSESSMENT = "trip_assessment";
    public static final String COLUMN_DESC = "trip_description";

    public static final String EXPENSE_TABLE_NAME = "expense";
    public static final String EXPENSE_ID = "expense_id";
    public static final String EXPENSE_TYPE ="type";
    public static final String EXPENSE_AMOUNT = "amount";
    public static final String EXPENSE_DATE = "expense_date";
    public static final String EXPENSE_TRIP_ID = "trip_id";


    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESTINATION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_ASSESSMENT + " TEXT, " +
                COLUMN_DESC + " TEXT);";

        String query1 =

        "CREATE TABLE " + EXPENSE_TABLE_NAME +
                " (" + EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EXPENSE_TYPE + " TEXT, " +
                EXPENSE_AMOUNT + " TEXT, " +
                EXPENSE_DATE + " TEXT, " +
                EXPENSE_TRIP_ID+ " INTEGER, " +
                "FOREIGN KEY ("+ EXPENSE_TRIP_ID + ") REFERENCES "+TABLE_NAME+"("+COLUMN_ID+"));";
        db.execSQL(query);
        db.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + EXPENSE_TABLE_NAME);
        onCreate(db);
    }

    void addTrip(String name, String destination, String date, String assessment, String desc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESTINATION, destination);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_ASSESSMENT, assessment);
        cv.put(COLUMN_DESC, desc);


        long results = db.insert(TABLE_NAME, null, cv);
        if(results == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;

    }

    Cursor readAllExpense(String trip_id){
        String query = "SELECT expense_id, type, amount, expense_date FROM " + EXPENSE_TABLE_NAME +" WHERE trip_id = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, new String[]{trip_id});
        }
        return cursor;
    }

    void addExpense(String type, String amount, String date, String trip_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EXPENSE_TYPE, type);
        cv.put(EXPENSE_AMOUNT, amount);
        cv.put(EXPENSE_DATE, date);
        cv.put(EXPENSE_TRIP_ID, trip_id);


        long results = db.insert(EXPENSE_TABLE_NAME, null, cv);
        if(results == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }



    void updateData(String row_id, String name, String destination, String date, String assessment, String desc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESTINATION, destination);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_ASSESSMENT, assessment);
        cv.put(COLUMN_DESC, desc);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + EXPENSE_TABLE_NAME);
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.execSQL("DELETE FROM " + EXPENSE_TABLE_NAME);
    }

    ArrayList<TripInfo> findAll(){
        ArrayList<TripInfo> tripInfos = null;
        try{
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
            if (cursor.moveToFirst()){
                tripInfos = new ArrayList<TripInfo>();
                do{

                    TripInfo tripInfo = new TripInfo();
                    tripInfo.setId(cursor.getInt(0));
                    tripInfo.setName(cursor.getString(1));
                    tripInfo.setDestination(cursor.getString(2));
                    tripInfo.setDate(cursor.getString(3));
                    tripInfo.setAssessment(cursor.getString(4));
                    tripInfos.add(tripInfo);
                } while (cursor.moveToNext());
            }

        } catch (Exception e){
            tripInfos = null;
        }
        return tripInfos;
    }
}

package com.arny.lubereckiy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import static com.arny.lubereckiy.db.DBProvider.*;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper db = null;
    // Database Info
    private static final String DATABASE_NAME = "Lubereckiy";
    private static final int DATABASE_VERSION = 1;

    private static DBHelper sInstance;

    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            createTables(db);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.beginTransaction();
            try {
                dropTables(db);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            }
            db.endTransaction();
            onCreate(db);
        }
    }

    public static long insertDB(Context context, String table, ContentValues contentValues) {
        long rowID = connectDB(context).insert(table, null, contentValues);
        disconnectDB();
        return rowID;
    }

    public static Cursor selectDB(Context context, String table, String[] columns, String where, String orderBy) {
        return connectDB(context).query(table, columns, where, null, null, null, orderBy);
    }


    public static Cursor selectDB(Context context, String table, String[] columns, String where, String[] whereArgs, String orderBy) {
        return connectDB(context).query(table, columns, where, whereArgs, null, null, orderBy);
    }

    public static Cursor queryDB(Context context, String sqlQuery, String[] selectionArgs) {
        return connectDB(context).rawQuery(sqlQuery, selectionArgs);
    }

    public static int deleteDB(Context context, String table, String where) {
        int rowCount = connectDB(context).delete(table, where, null);
        disconnectDB();
        return rowCount;
    }

    public static int deleteDB(Context context, String table, String where, String[] whereArgs) {
        int rowCount = connectDB(context).delete(table, where, whereArgs);
        disconnectDB();
        return rowCount;
    }

    public static int updateDB(Context context, String table, ContentValues contentValues, String where) {
        int rowCount = connectDB(context).update(table, contentValues, where, null);
        disconnectDB();
        return rowCount;
    }

    public static int updateDB(Context context, String table, ContentValues contentValues, String where, String[] whereArgs) {
        int rowCount = connectDB(context).update(table, contentValues, where, whereArgs);
        disconnectDB();
        return rowCount;
    }

    public static void isDB(Context context) {
        connectDB(context).getVersion();
        disconnectDB();
    }

    private static void disconnectDB() {
//        db.close();
    }

    private static SQLiteDatabase connectDB(Context context) {
//        if (db != null) {
//            db.close();
//        }
        db = DBHelper.getInstance(context);
        return db.getWritableDatabase();
    }

    static int getInt(Cursor cursor, String columnindex) {
        return Integer.parseInt(cursor.getString(cursor.getColumnIndex(columnindex)));
    }

    static String getString(Cursor cursor, String columnindex) {
        return cursor.getString(cursor.getColumnIndex(columnindex));
    }

    static boolean getBoolean(Cursor cursor, String columnindex) {
        return Boolean.parseBoolean(getString(cursor, columnindex));
    }

    static double getDouble(Cursor cursor, String columnindex) {
        return Double.parseDouble(getString(cursor, columnindex));
    }

}


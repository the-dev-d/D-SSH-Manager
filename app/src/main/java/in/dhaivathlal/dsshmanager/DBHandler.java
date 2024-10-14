package in.dhaivathlal.dsshmanager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import in.dhaivathlal.dsshmanager.models.Group;
import in.dhaivathlal.dsshmanager.models.Machine;

public class DBHandler extends SQLiteOpenHelper {


    private static final int VERSION = 1;
    private static final String NAME = "machines.db";
    public static final String MACHINE_ID = "MACHINE_ID";
    public static final String MACHINE_NAME = "MACHINE_NAME";
    public static final String MACHINE_USERNAME = "MACHINE_USERNAME";
    public static final String MACHINE_IP_ADDRESS = "MACHINE_IP_ADDRESS";
    public static final String MACHINE_PASSWORD = "MACHINE_PASSWORD";
    public static final String MACHINE_GROUP_ID = "MACHINE_GROUP_ID";
    public static final String GROUPS_TABLE = "GROUPS";
    public static final String MACHINES_TABLE = "MACHINES";

    public static String GROUP_ID = "GROUP_ID";
    public static String GROUP_NAME = "GROUP_NAME";


    public DBHandler(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String groupTableCreate = "CREATE TABLE " + GROUPS_TABLE + " ( " + GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ GROUP_NAME +" VARCHAR(30) )";
        final String machinesTableCreate = "CREATE TABLE " + MACHINES_TABLE + " ( " +
                MACHINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MACHINE_NAME + " VARCHAR(30)," +
                MACHINE_USERNAME + " VARCHAR(30)," +
                MACHINE_IP_ADDRESS + " VARCHAR(30)," +
                MACHINE_PASSWORD + " VARCHAR(30)," +
                MACHINE_GROUP_ID + " INTEGER" +
                ")";
        db.execSQL(groupTableCreate);
        db.execSQL(machinesTableCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addGroup(String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GROUP_NAME, name);
        db.insert(GROUPS_TABLE, null, cv);
        db.close();
    }

    public List<Group> getAllGroup() {

        List<Group> groups = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + GROUP_ID + ", "
                + GROUP_NAME + ", "
                + "COUNT(" + MACHINE_ID + ") AS MACHINE_COUNT "
                + "FROM " + GROUPS_TABLE + " g "
                + "LEFT JOIN " + MACHINES_TABLE + " m ON g." + GROUP_ID + " = m." + MACHINE_GROUP_ID + " "
                + "GROUP BY g." + GROUP_ID + ", g." + GROUP_NAME, null);


        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                Integer count = cursor.getInt(2);
                groups.add(
                        new Group(id, name, count)
                );
            }while (cursor.moveToNext());
        }

        cursor.close();
        return groups;
    }

    public boolean groupExists(String name) {

        final String sql = "SELECT " + GROUP_ID + " FROM " + GROUPS_TABLE + " WHERE " + GROUP_NAME + " = ?";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{name});

        return cursor.moveToFirst();
    }

    public void addMachine(Machine machine) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MACHINE_NAME, machine.getName());
        cv.put(MACHINE_USERNAME, machine.getUsername());
        cv.put(MACHINE_PASSWORD, machine.getPassword());
        cv.put(MACHINE_IP_ADDRESS, machine.getIp());
        cv.put(MACHINE_GROUP_ID, machine.getGroupId());

        db.insert(MACHINES_TABLE, null, cv);
        db.close();
    }

    @SuppressLint("Range")
    public List<Machine> getAllMachines(Integer groupId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ MACHINE_ID + ", " +
                MACHINE_NAME + ", " +
                MACHINE_IP_ADDRESS + ", " +
                MACHINE_USERNAME + ", " +
                MACHINE_PASSWORD + " FROM MACHINES WHERE MACHINE_GROUP_ID = ?", new String[]{groupId.toString()});

        List<Machine> machines = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {

                machines.add(
                        new Machine(
                                cursor.getString(cursor.getColumnIndex(MACHINE_NAME)),
                                cursor.getString(cursor.getColumnIndex(MACHINE_IP_ADDRESS)),
                                cursor.getString(cursor.getColumnIndex(MACHINE_USERNAME)),
                                cursor.getString(cursor.getColumnIndex(MACHINE_PASSWORD)),
                                groupId
                        )
                );
                Log.d("HJ", cursor.getString(cursor.getColumnIndex(MACHINE_NAME)));
            }while (cursor.moveToNext());
        }
        return machines;
    }


}

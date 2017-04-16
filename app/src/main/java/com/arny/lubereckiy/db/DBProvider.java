package com.arny.lubereckiy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arny.lubereckiy.models.Flat;
import com.arny.lubereckiy.models.Floor;
import com.arny.lubereckiy.models.Korpus;
import com.arny.lubereckiy.models.Section;

import static com.arny.lubereckiy.db.DBHelper.*;

import java.util.ArrayList;

public class DBProvider {


    private static final String TABLE_SECTION = "section";
    private static final String TABLE_KORPUS = "korpus";
    private static final String TABLE_FLOOR = "floor";
    private static final String TABLE_FLAT = "flat";

    private static final String COLUMN_ID = "_id";
    // Table Columns
    private static final String SECTION_NAME = "section_name";
    private static final String SECTION_QUANTITY = "section_quantity";
    private static final String FLOOR_NUMBER = "floor_number";
    private static final String SECTION_FIRST_FLOOR_NUMBER = "section_first_floor_number";
    private static final String SECTION_MAX_FLOOR_FLATS = "section_max_floor_flats";
    private static final String SECTION_FLATS_DIRECTION = "flats_direction";
    private static final String SECTION_READINESS = "readiness";
    private static final String KORPUS_ID = "korpusID";
    private static final String KORPUS_TITLE = "title";
    private static final String STATUS = "status";
    private static final String KORPUS_FINISHING = "finishing";
    private static final String KORPUS_FREE = "free";
    private static final String KORPUS_BUSY = "busy";
    private static final String KORPUS_MINPRICE_1 = "minprice_1";
    private static final String KORPUS_MINPRICE_2 = "minprice_2";
    private static final String KORPUS_SOLD = "sold";
    private static final String FLOOR_PLAN = "floor_plan";
    private static final String FLAT_ID = "flatID";
    private static final String ROOM_QUANTITY = "roomQuantity";
    private static final String WHOLE_AREA_BTI = "wholeAreaBti";
    private static final String WHOLE_PRICE = "wholePrice";
    private static final String OFFICE_PRICE = "officePrice";
    private static final String DISCOUNT = "discount";
    private static final String DATE_OF_MOVING_IN = "dateOfMovingIn";

    static void createTables(SQLiteDatabase db) {
        db.execSQL(createSectionTable());
        db.execSQL(createKorpusTable());
        db.execSQL(createFloorTable());
        db.execSQL(createFlatTable());
    }

    static void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KORPUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLOOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLAT);
    }

    private static String createSectionTable() {
        return "CREATE TABLE `" + TABLE_SECTION + "` ( " +
                "`" + COLUMN_ID + "`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,	" +
                "`" + SECTION_NAME + "`	TEXT NOT NULL,	" +
                "`" + KORPUS_ID + "` TEXT, " +
                "`" + SECTION_QUANTITY + "`	TEXT,	" +
                "`" + SECTION_FIRST_FLOOR_NUMBER + "` INTEGER, " +
                "`" + SECTION_MAX_FLOOR_FLATS + "` INTEGER, " +
                "`" + SECTION_FLATS_DIRECTION + "` INTEGER, " +
                "`" + SECTION_READINESS + "` INTEGER" +
                ")";
    }

    private static String createKorpusTable() {
        return "CREATE TABLE `" + TABLE_KORPUS + "` ( " +
                "`" + COLUMN_ID + "`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT" +
                ",	`" + KORPUS_ID + "`	TEXT NOT NULL" +
                ",	`" + KORPUS_TITLE + "`	TEXT" +
                ",	`" + STATUS + "`	TEXT" +
                ",	`" + KORPUS_FINISHING + "`	TEXT" +
                ",	`" + KORPUS_FREE + "`	INTEGER" +
                ",	`" + KORPUS_BUSY + "`	INTEGER" +
                ",	`" + KORPUS_MINPRICE_1 + "`	INTEGER" +
                ",	`" + KORPUS_MINPRICE_2 + "`	INTEGER" +
                ",	`" + KORPUS_SOLD + "`	INTEGER" +
                "`" + DATE_OF_MOVING_IN + "`	TEXT" +
                ")";
    }

    private static String createFloorTable() {
        return "CREATE TABLE `" + TABLE_FLOOR + "` ( " +
                "`" + COLUMN_ID + "`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT" +
                ",	`" + KORPUS_ID + "`	TEXT" +
                ",	`" + SECTION_NAME + "`	TEXT" +
                ",	`" + FLOOR_NUMBER + "`	INTEGER" +
                ",	`" + FLOOR_PLAN + "`	TEXT" +
                ")";
    }

    private static String createFlatTable() {
        return "CREATE TABLE `" + TABLE_FLAT + "` ( " +
                "`" + COLUMN_ID + "`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT" +
                ",	`" + KORPUS_ID + "`	TEXT" +
                ",	`" + SECTION_NAME + "`	TEXT" +
                ",	`" + FLOOR_NUMBER + "`	INTEGER" +
                ",	`" + FLAT_ID + "`	TEXT" +
                ",	`" + ROOM_QUANTITY + "`	INTEGER" +
                ",	`" + WHOLE_AREA_BTI + "`	REAL" +
                ",	`" + WHOLE_PRICE + "`	INTEGER" +
                ",	`" + OFFICE_PRICE + "`	INTEGER" +
                ",	`" + DISCOUNT + "`	TEXT" +
                ",	`" + STATUS + "`	TEXT" +
                ")";
    }

    /*korpus*/
    @NonNull
    private static ContentValues getKorpusValues(Korpus korpus) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, korpus.getID());
        values.put(KORPUS_ID, korpus.getKorpusID());
        values.put(KORPUS_TITLE, korpus.getTitle());
        values.put(STATUS, korpus.getStatus());
        values.put(KORPUS_FINISHING, korpus.isFinishing());
        values.put(KORPUS_FREE, korpus.getFree());
        values.put(KORPUS_BUSY, korpus.getBusy());
        values.put(KORPUS_MINPRICE_1, korpus.getMinprice_1());
        values.put(KORPUS_MINPRICE_2, korpus.getMinprice_2());
        values.put(KORPUS_SOLD, korpus.getSold());
        values.put(DATE_OF_MOVING_IN, korpus.getDateOfMovingIn());
        return values;
    }

    public static ArrayList<Korpus> getKorpuses(Context context) {
        Cursor cursor = selectDB(context, TABLE_KORPUS, null, null, null);
        ArrayList<Korpus> korpuses = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Korpus korpus = getCursorKorpus(cursor);
                korpuses.add(korpus);
            } while (cursor.moveToNext());
        }
        return korpuses;
    }

    public static Korpus getKorpus(Context context, int id) {
        Cursor cursor = selectDB(context, TABLE_KORPUS, null, COLUMN_ID + "=" + id, null);
        if (cursor.moveToFirst()) {
            return getCursorKorpus(cursor);
        }
        return null;
    }

    public static boolean updateKorpus(Context context, Korpus korpus) {
        ContentValues values = DBProvider.getKorpusValues(korpus);
        String where = String.format("%s=%s", COLUMN_ID, korpus.getID());
        return updateDB(context, TABLE_KORPUS, values, where) > 0;
    }

    public static boolean removeKorpus(Context context, int id) {
        String where = COLUMN_ID + "=" + id;
        return deleteDB(context, TABLE_KORPUS, where) > 0;
    }

    public static long addKorpus(Context context, Korpus korpus) {
        return insertDB(context, TABLE_KORPUS, getKorpusValues(korpus));
    }

    @NonNull
    private static Korpus getCursorKorpus(Cursor cursor) {
        Korpus korpus = new Korpus();
        korpus.setID(getInt(cursor, COLUMN_ID));
        korpus.setKorpusID(getString(cursor, KORPUS_ID));
        korpus.setTitle(getString(cursor, KORPUS_TITLE));
        korpus.setStatus(getString(cursor, STATUS));
        korpus.setFinishing(getBoolean(cursor, KORPUS_FINISHING));
        korpus.setFree(getInt(cursor, COLUMN_ID));
        korpus.setDateOfMovingIn(getString(cursor, DATE_OF_MOVING_IN));
        return korpus;
    }

    /*Section*/
    @NonNull
    private static ContentValues getSectionValues(Section section) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, section.getID());
        values.put(SECTION_NAME, section.getName());
        values.put(KORPUS_ID, section.getKorpusID());
        values.put(SECTION_QUANTITY, section.getQuantity());
        values.put(SECTION_FIRST_FLOOR_NUMBER, section.getFirstFloorNumber());
        values.put(SECTION_MAX_FLOOR_FLATS, section.getMaxFlatsOnFloor());
        values.put(SECTION_FLATS_DIRECTION, section.getFlatsDirection());
        values.put(SECTION_READINESS, section.getReadiness());
        return values;
    }

    public static ArrayList<Section> getSections(Context context) {
        Cursor cursor = selectDB(context, TABLE_SECTION, null, null, null);
        ArrayList<Section> sections = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Section section = getCursorSection(cursor);
                sections.add(section);
            } while (cursor.moveToNext());
        }
        return sections;
    }

    @NonNull
    private static Section getCursorSection(Cursor cursor) {
        Section section = new Section();
        section.setID(getInt(cursor, COLUMN_ID));
        section.setName(getString(cursor, SECTION_NAME));
        section.setKorpusID(getString(cursor, KORPUS_ID));
        section.setFirstFloorNumber(getInt(cursor, SECTION_FIRST_FLOOR_NUMBER));
        section.setMaxFlatsOnFloor(getInt(cursor, SECTION_MAX_FLOOR_FLATS));
        section.setQuantity(getInt(cursor, SECTION_QUANTITY));
        section.setFlatsDirection(getInt(cursor, SECTION_FLATS_DIRECTION));
        section.setReadiness(getInt(cursor, SECTION_READINESS));
        return section;
    }

    public static Section getSection(Context context, int id) {
        Cursor cursor = selectDB(context, TABLE_SECTION, null, COLUMN_ID + "=" + id, null);
        if (cursor.moveToFirst()) {
            return getCursorSection(cursor);
        }
        return null;
    }

    public static boolean updateSection(Context context, Section section) {
        ContentValues values = DBProvider.getSectionValues(section);
        String where = String.format("%s=%s", COLUMN_ID, section.getID());
        return updateDB(context, TABLE_SECTION, values, where) > 0;
    }

    public static boolean removeSection(Context context, int id) {
        String where = COLUMN_ID + "=" + id;
        return deleteDB(context, TABLE_SECTION, where) > 0;
    }

    public static long addSection(Context context, Section section) {
        return insertDB(context, TABLE_SECTION, getSectionValues(section));
    }

    /*Floor*/
    @NonNull
    private static ContentValues getFloorValues(Floor floor) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, floor.getID());
        values.put(KORPUS_ID, floor.getKorpusID());
        values.put(SECTION_NAME, floor.getSectionName());
        values.put(FLOOR_NUMBER, floor.getFloorNumber());
        values.put(FLOOR_PLAN, floor.getFloorPlan());
        return values;
    }

    public static ArrayList<Floor> getFloors(Context context) {
        Cursor cursor = selectDB(context, TABLE_FLOOR, null, null, null);
        ArrayList<Floor> floors = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Floor floor = getCursorFloors(cursor);
                floors.add(floor);
            } while (cursor.moveToNext());
        }
        return floors;
    }

    @NonNull
    private static Floor getCursorFloors(Cursor cursor) {
        Floor floor = new Floor();
        floor.setID(getInt(cursor, COLUMN_ID));
        floor.setKorpusID(getString(cursor, KORPUS_ID));
        floor.setSectionName(getString(cursor, SECTION_NAME));
        floor.setFloorNumber(getInt(cursor, FLOOR_NUMBER));
        floor.setFloorPlan(getString(cursor, FLOOR_PLAN));
        return floor;
    }

    public static Floor getFloor(Context context, int id) {
        Cursor cursor = selectDB(context, TABLE_FLOOR, null, COLUMN_ID + "=" + id, null);
        if (cursor.moveToFirst()) {
            return getCursorFloors(cursor);
        }
        return null;
    }

    public static boolean updateFloor(Context context, Floor floor) {
        ContentValues values = DBProvider.getFloorValues(floor);
        String where = String.format("%s=%s", COLUMN_ID, floor.getID());
        return updateDB(context, TABLE_FLOOR, values, where) > 0;
    }

    public static boolean removeFloor(Context context, int id) {
        String where = COLUMN_ID + "=" + id;
        return deleteDB(context, TABLE_FLOOR, where) > 0;
    }

    public static long addFloor(Context context, Floor floor) {
        return insertDB(context, TABLE_FLOOR, getFloorValues(floor));
    }

    /*Floor*/
    public static ArrayList<Flat> getFlats(Context context) {
        Cursor cursor = selectDB(context, TABLE_FLAT, null, null, null);
        ArrayList<Flat> flats = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Flat flat = getCursorFlats(cursor);
                flats.add(flat);
            } while (cursor.moveToNext());
        }
        return flats;
    }

    @NonNull
    private static ContentValues getFlatValues(Flat flat) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, flat.getID());
        values.put(KORPUS_ID, flat.getKorpusID());
        values.put(SECTION_NAME, flat.getSectionName());
        values.put(FLOOR_NUMBER, flat.getFloorNumber());
        values.put(FLAT_ID, flat.getFlatID());
        values.put(ROOM_QUANTITY, flat.getRoomQuantity());
        values.put(WHOLE_AREA_BTI, flat.getWholeAreaBti());
        values.put(WHOLE_PRICE, flat.getWholePrice());
        values.put(OFFICE_PRICE, flat.getOfficePrice());
        values.put(DISCOUNT, flat.isDiscount());
        values.put(STATUS, flat.getStatus());
        return values;
    }

    @NonNull
    private static Flat getCursorFlats(Cursor cursor) {
        Flat flat = new Flat();
        flat.setID(getInt(cursor, COLUMN_ID));
        flat.setKorpusID(getString(cursor, KORPUS_ID));
        flat.setSectionName(getString(cursor, SECTION_NAME));
        flat.setFloorNumber(getInt(cursor, FLOOR_NUMBER));
        flat.setFlatID(getString(cursor, FLAT_ID));
        flat.setRoomQuantity(getInt(cursor, ROOM_QUANTITY));
        flat.setWholeAreaBti(getDouble(cursor, WHOLE_AREA_BTI));
        flat.setWholePrice(getInt(cursor, WHOLE_PRICE));
        flat.setOfficePrice(getInt(cursor, OFFICE_PRICE));
        flat.setDiscount(getBoolean(cursor, DISCOUNT));
        flat.setStatus(getString(cursor, STATUS));
        return flat;
    }

    public static Flat getFlat(Context context, int id) {
        Cursor cursor = selectDB(context, TABLE_FLAT, null, COLUMN_ID + "=" + id, null);
        if (cursor.moveToFirst()) {
            return getCursorFlats(cursor);
        }
        return null;
    }

    public static boolean updateFlat(Context context, Flat flat) {
        ContentValues values = DBProvider.getFlatValues(flat);
        String where = String.format("%s=%s", COLUMN_ID, flat.getID());
        return updateDB(context, TABLE_FLAT, values, where) > 0;
    }

    public static boolean removeFlat(Context context, int id) {
        String where = COLUMN_ID + "=" + id;
        return deleteDB(context, TABLE_FLAT, where) > 0;
    }

    public static long addFlat(Context context, Flat flat) {
        return insertDB(context, TABLE_FLAT, getFlatValues(flat));
    }

}

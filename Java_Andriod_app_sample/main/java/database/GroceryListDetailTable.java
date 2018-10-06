package edu.gatech.seclass.glm.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by trevorloranger on 10/18/16.
 */

public final class GroceryListDetailTable implements BaseColumns {

    private GroceryListDetailTable() {}

    // Table Information
    public static final String TABLE_NAME = "grocery_list_detail";
    public static final String COLUMN_LIST_ID = "list_name_id";
    public static final String COLUMN_ITEM_ID = "item_id";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_CHECKED_OFF = "checked_off";

    // Database Creation
    private static final String TABLE_CREATE = "CREATE TABLE "
            + TABLE_NAME
            + " ("
            + COLUMN_LIST_ID + " INTEGER, "
            + COLUMN_ITEM_ID + " INTEGER, "
            + COLUMN_QUANTITY + " TEXT NOT NULL, "
            + COLUMN_CHECKED_OFF + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_LIST_ID +") REFERENCES "
            + GroceryListTable.TABLE_NAME + "(" + GroceryListTable._ID + ") "
            + " ON DELETE CASCADE, "
            + "FOREIGN KEY(" + COLUMN_ITEM_ID +") REFERENCES "
            + ItemNameTable.TABLE_NAME + "(" + ItemNameTable._ID + ") "
            + " ON DELETE CASCADE, "
            + "PRIMARY KEY (" + COLUMN_LIST_ID + ", " + COLUMN_ITEM_ID + ") "
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE);

        ContentValues values = new ContentValues();
        values.put(GroceryListDetailTable.COLUMN_CHECKED_OFF, 1);
        values.put(GroceryListDetailTable.COLUMN_LIST_ID, 1);
        values.put(GroceryListDetailTable.COLUMN_ITEM_ID, 1);
        values.put(GroceryListDetailTable.COLUMN_QUANTITY, "2 lb");
        values.put(GroceryListDetailTable.COLUMN_CHECKED_OFF, 1);

        database.insert(GroceryListDetailTable.TABLE_NAME, null, values);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(ItemTypeTable.class.getName(), "Database upgrade from " + oldVersion + " to "
                + newVersion + ", all data will be lost");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}

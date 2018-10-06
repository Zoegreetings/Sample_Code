package edu.gatech.seclass.glm.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by trevorloranger on 10/18/16.
 */

public final class ItemTypeTable implements BaseColumns {

    private ItemTypeTable() {}

    // Table Information
    public static final String TABLE_NAME = "item_type";
    public static final String COLUMN_TYPE = "type";

    public static final String[] itemTypes = {
            "Milk & Dairy",
            "Bread & Baked Goods",
            "Vegetables",
            "Fruits",
            "Seafood",
            "Frozen Goods",
            "Deli & Meat",
            "Paper Goods",
            "Canned Goods",
            "Dry Packaged Goods",
            "Spices",
            "Condiments & Sauce",
            "Drinks",
            "Snacks",
            "Baking Supplies",
            "Cereal",
            "Baby Items",
            "Cleaning Products",
            "Pet Supplies",
            "Health & Beauty",
            "Other"
    };

    // Database creation
    private static final String TABLE_CREATE = "CREATE TABLE "
            + TABLE_NAME
            + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TYPE + " TEXT NOT NULL UNIQUE"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE);

        for (String type : itemTypes) {
            database.execSQL("insert into "
                    + TABLE_NAME
                    + " ("
                    + COLUMN_TYPE
                    + ") "
                    + "VALUES (\""
                    + type
                    + "\");"
            );
        }
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(ItemTypeTable.class.getName(), "Database upgrade from " + oldVersion + " to "
                + newVersion + ", all data will be lost");
        database.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(database);
    }
}

package edu.gatech.seclass.glm.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by trevorloranger on 10/18/16.
 */

public final class GroceryListTable implements BaseColumns {

    private GroceryListTable() {}

    // Table Information
    public static final String TABLE_NAME = "grocery_list";
    public static final String COLUMN_LIST_NAME = "list_name";

    // Database creation
    private static final String TABLE_CREATE = "CREATE TABLE "
            + TABLE_NAME
            + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_LIST_NAME + " TEXT NOT NULL UNIQUE"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE);
        database.execSQL("INSERT INTO " + TABLE_NAME + " (" + COLUMN_LIST_NAME +") VALUES ("
        +"\"Weekly List\");");

    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(ItemTypeTable.class.getName(), "Database upgrade from " + oldVersion + " to "
                + newVersion + ", all data will be lost");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}

package edu.gatech.seclass.glm.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import edu.gatech.seclass.glm.models.GroceryList;

/**
 * Created by trevorloranger on 10/21/16.
 */

public class GroceryListView {

    private GroceryListView() {}

    // Table Information
    public static final String VIEW_NAME = "vw_grocery_list";

    public static final String COLUMN_LIST_NAME = GroceryListTable.COLUMN_LIST_NAME;

    public static final String COLUMN_LIST_ID = GroceryListDetailTable.COLUMN_LIST_ID;
    public static final String COLUMN_QUANTITY = GroceryListDetailTable.COLUMN_QUANTITY;
    public static final String COLUMN_CHECKED_OFF = GroceryListDetailTable.COLUMN_CHECKED_OFF;
    public static final String COLUMN_ITEM_ID = GroceryListDetailTable.COLUMN_ITEM_ID;

    public static final String COLUMN_ITEM_NAME = ItemNameTable.COLUMN_NAME;
    public static final String COLUMN_ITEM_TYPE_ID = ItemNameTable.COLUMN_TYPE_ID;

    public static final String COLUMN_ITEM_TYPE_NAME = ItemTypeTable.COLUMN_TYPE;

    // View Creation
    private static final String VIEW_CREATE = String.format(
            "CREATE VIEW %s AS SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, %s.%s "
            + "FROM %s INNER JOIN %s ON "
            + "%s.%s = %s.%s INNER JOIN %s ON %s.%s = %s.%s INNER JOIN %s ON %s.%s = %s.%s",
            VIEW_NAME,
            GroceryListTable.TABLE_NAME,
            COLUMN_LIST_NAME,
            GroceryListDetailTable.TABLE_NAME,
            COLUMN_LIST_ID,
            GroceryListDetailTable.TABLE_NAME,
            COLUMN_QUANTITY,
            GroceryListDetailTable.TABLE_NAME,
            COLUMN_CHECKED_OFF,
            GroceryListDetailTable.TABLE_NAME,
            COLUMN_ITEM_ID,
            ItemNameTable.TABLE_NAME,
            COLUMN_ITEM_NAME,
            ItemNameTable.TABLE_NAME,
            COLUMN_ITEM_TYPE_ID,
            ItemTypeTable.TABLE_NAME,
            COLUMN_ITEM_TYPE_NAME,
            GroceryListDetailTable.TABLE_NAME,
            GroceryListTable.TABLE_NAME,
            GroceryListDetailTable.TABLE_NAME,
            GroceryListDetailTable.COLUMN_LIST_ID,
            GroceryListTable.TABLE_NAME,
            GroceryListTable._ID,
            ItemNameTable.TABLE_NAME,
            GroceryListDetailTable.TABLE_NAME,
            GroceryListDetailTable.COLUMN_ITEM_ID,
            ItemNameTable.TABLE_NAME,
            ItemNameTable._ID,
            ItemTypeTable.TABLE_NAME,
            ItemNameTable.TABLE_NAME,
            ItemNameTable.COLUMN_TYPE_ID,
            ItemTypeTable.TABLE_NAME,
            ItemTypeTable._ID);

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(VIEW_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(ItemTypeTable.class.getName(), "Database upgrade from " + oldVersion + " to "
                + newVersion + ", all data will be lost");
        database.execSQL("DROP VIEW IF EXISTS " + VIEW_NAME);
        onCreate(database);
    }
}

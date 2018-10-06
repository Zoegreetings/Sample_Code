package edu.gatech.seclass.glm.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trevorloranger on 10/18/16.
 */

public final class ItemNameTable implements BaseColumns {

    private ItemNameTable() {
    }

    // Table Information
    public static final String TABLE_NAME = "item_name";
    public static final String COLUMN_TYPE_ID = "type_id";
    public static final String COLUMN_NAME = "name";

    // Database creation
    private static final String TABLE_CREATE = "CREATE TABLE "
            + TABLE_NAME
            + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TYPE_ID + " INTEGER, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + "FOREIGN KEY(" + COLUMN_TYPE_ID + ") REFERENCES " + ItemTypeTable.TABLE_NAME
            + "(" + ItemTypeTable._ID + "), "
            + "UNIQUE(" + COLUMN_NAME + ", " + COLUMN_TYPE_ID + ")"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_CREATE);

        for (ItemName item : getListItems()) {
            database.execSQL("INSERT INTO "
                    + TABLE_NAME
                    + "(type_id, name) VALUES ("
                    + item.getItemId()
                    + " , '"
                    + item.getItemName()
                    + "' );");
        }

    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(ItemTypeTable.class.getName(), "Database upgrade from " + oldVersion + " to "
                + newVersion + ", all data will be lost");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public static List<ItemName> getListItems() {
        return new ArrayList<ItemName>() {{
            add(new ItemName(4, "Apple"));
            add(new ItemName(4, "Banana"));
            add(new ItemName(4, "Grapes"));
            add(new ItemName(4, "Peach"));
            add(new ItemName(3, "Spinach"));
            add(new ItemName(3, "Broccoli"));
            add(new ItemName(3, "Green Beans"));
            add(new ItemName(3, "Zucchini"));
            add(new ItemName(13, "Pepsi"));
            add(new ItemName(13, "Beer"));
            add(new ItemName(13, "Water"));
            add(new ItemName(7, "Chicken"));
            add(new ItemName(7, "Beef"));
            add(new ItemName(7, "Turkey"));
            add(new ItemName(7, "Pork"));
            add(new ItemName(7, "Lamb"));
        }};
    }

    private static class ItemName {
        int itemId;
        String itemName;

        ItemName(int itemId, String itemName) {
            this.itemId = itemId;
            this.itemName = itemName;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }
    }
}

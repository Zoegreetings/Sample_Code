package edu.gatech.seclass.glm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.glm.models.ListItem;

/**
 * Class implements singleton pattern.
 * <p>
 * Created by trevorloranger on 10/12/16.
 */

public class GroceryListDbHelper extends SQLiteOpenHelper {

    private static GroceryListDbHelper sInstance;

    // If database schema is changed database version must be incremented.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "glm.db";

    // Usage details below:
    // GroceryListDbHelper h = GroceryListDbHelper.getInstance(this);

    public static synchronized GroceryListDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new GroceryListDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private GroceryListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    public void onCreate(SQLiteDatabase db) {
        // Create tables within database for app.
        GroceryListTable.onCreate(db);
        ItemTypeTable.onCreate(db);
        ItemNameTable.onCreate(db);
        GroceryListDetailTable.onCreate(db);
        GroceryListView.onCreate(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Discard data and re-create.
        GroceryListView.onUpgrade(db, oldVersion, newVersion);
        GroceryListDetailTable.onUpgrade(db, oldVersion, newVersion);
        ItemNameTable.onUpgrade(db, oldVersion, newVersion);
        ItemTypeTable.onUpgrade(db, oldVersion, newVersion);
        GroceryListTable.onUpgrade(db, oldVersion, newVersion);

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // Add a grocery list to database.
    public boolean addGroceryList(String name) {
        boolean ret = true;

        ContentValues values = new ContentValues();
        values.put(GroceryListTable.COLUMN_LIST_NAME, name);

        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(GroceryListTable.TABLE_NAME, null, values);

        if (rowId == -1) {
            ret = false;
        }
        return ret;
    }

    // Rename a grocery list object.
    public boolean renameGroceryList(String oldName, String newName) {
        boolean retVal = true;
        ContentValues values = new ContentValues();
        values.put(GroceryListTable.COLUMN_LIST_NAME, newName);

        SQLiteDatabase db = getWritableDatabase();
        int ret = db.update(GroceryListTable.TABLE_NAME, values,
                GroceryListTable.COLUMN_LIST_NAME + " = ?", new String[]{oldName});

        if (ret == 0) {
            retVal = false;
        }

        return retVal;
    }

    // Clear all checked off items on grocery list.
    public boolean clearAllChecked(String groceryList) {
        boolean retVal = false;
        ContentValues values = new ContentValues();
        values.put(GroceryListDetailTable.COLUMN_CHECKED_OFF, 0);

        int listNameRowId = -1;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + GroceryListTable._ID + " FROM "
                + GroceryListTable.TABLE_NAME + " WHERE "
                + GroceryListTable.COLUMN_LIST_NAME + " \""
                + groceryList + "\"", null);

        if (cursor.moveToFirst()) {
            do {
                listNameRowId = cursor.getInt(cursor.getColumnIndex(GroceryListTable._ID));
            } while (cursor.moveToNext());
        }

        // if a rowId is found for the grocery list (if present in groceryListTable)
        if (listNameRowId > -1) {
            db = getWritableDatabase();

            int ret = db.update(GroceryListDetailTable.TABLE_NAME, values,
                    GroceryListDetailTable.COLUMN_LIST_ID + " = ?", new String[]{String.valueOf(listNameRowId)});

            if (ret > 0) {
                retVal = true;
            }
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return retVal;
    }

    private int getItemNameId(String itemName) {
        int returnValue = -1;

        String ITEM_ID_QUERY = String.format("SELECT %s FROM %s WHERE %s = \"%s\"",
                ItemNameTable._ID,
                ItemNameTable.TABLE_NAME,
                ItemNameTable.COLUMN_NAME,
                itemName);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ITEM_ID_QUERY, null);

        if (cursor.moveToFirst()) {
            returnValue = cursor.getInt(cursor.getColumnIndex(ItemNameTable._ID));
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }

        return returnValue;
    }

    private int getGroceryListId(String groceryList) {
        int returnValue = -1;

        String GROCERY_LIST_ID_QUERY = String.format("SELECT %s FROM %s WHERE %s = \"%s\"",
                GroceryListTable._ID,
                GroceryListTable.TABLE_NAME,
                GroceryListTable.COLUMN_LIST_NAME,
                groceryList);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(GROCERY_LIST_ID_QUERY, null);

        if (cursor.moveToFirst()) {
            returnValue = cursor.getInt(cursor.getColumnIndex(ItemNameTable._ID));
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }

        return returnValue;
    }

    // Flip checked off status on item in grocery list.
    public boolean flipCheckedOffStatus(String groceryList, String itemName) {
        boolean retVal = true;

        int checkedOffStatus = -1;
        int groceryListId = -1;
        int itemNameId;

        //See if there is a groceryList and itemName that matches search criteria.

        String SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s = \"%s\" AND %s = \"%s\"",
                GroceryListView.VIEW_NAME,
                GroceryListView.COLUMN_LIST_NAME,
                groceryList,
                GroceryListView.COLUMN_ITEM_NAME,
                itemName);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToFirst()) {
            checkedOffStatus = cursor.getInt(cursor.getColumnIndex(GroceryListView.COLUMN_CHECKED_OFF));
            groceryListId = cursor.getInt(cursor.getColumnIndex(GroceryListView.COLUMN_LIST_ID));
            itemNameId = cursor.getInt(cursor.getColumnIndex(GroceryListView.COLUMN_ITEM_ID));

            int newFlag;
            //Update Flag
            if (checkedOffStatus == 0) {
                newFlag = 1;
            } else {
                newFlag = 0;
            }

            ContentValues values = new ContentValues();
            values.put(GroceryListDetailTable.COLUMN_CHECKED_OFF, newFlag);

            db = getWritableDatabase();

            int ret = db.update(GroceryListDetailTable.TABLE_NAME, values,
                    GroceryListDetailTable.COLUMN_LIST_ID + " = ? AND "
                            + GroceryListDetailTable.COLUMN_ITEM_ID + " = ?",
                    new String[]{String.valueOf(groceryListId), String.valueOf(itemNameId)});

            if (ret <= 0) {
                retVal = false;
            }
        } else {
            retVal = false;
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return retVal;
    }

    public boolean updateCheckOffStatus(String listName,boolean status){
        int groceryListId = getGroceryListId(listName);
        SQLiteDatabase db = getWritableDatabase();
        String updateQuery = "UPDATE grocery_list_detail SET checked_off = 0 WHERE list_name_id=" + groceryListId;
        Cursor cursor1 = db.rawQuery(updateQuery, null);
        cursor1.close();
        return true;
    }

    // Add item to grocery list.
    public boolean addToGroceryList(String groceryList, ListItem item) {
        return false;
    }

    // Modify item quantity in list.
    public boolean modifyListItemQuantity(String groceryList, String itemName, String newQuantity) {
        boolean retVal = true;

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GroceryListDetailTable.COLUMN_QUANTITY, newQuantity);

        int groceryListId = getGroceryListId(groceryList);
        int itemNameId = getItemNameId(itemName);

        int resp = db.update(GroceryListDetailTable.TABLE_NAME, values,
                GroceryListDetailTable.COLUMN_LIST_ID + " = ? AND "
                + GroceryListDetailTable.COLUMN_ITEM_ID + " = ?",
                new String[]{String.valueOf(groceryListId), String.valueOf(itemNameId)});

        if (resp <= 0) {
            retVal = false;
        }

        return retVal;
    }

    // Delete a grocery list.
    public boolean deleteGroceryList(String groceryList) {
        boolean retVal = true;

        SQLiteDatabase db = getWritableDatabase();

        int groceryListId = getGroceryListId(groceryList);

        int resp = db.delete(GroceryListTable.TABLE_NAME,
                GroceryListTable._ID + " = ?",
                new String[]{String.valueOf(groceryListId)});

        if (resp <= 0) {
            retVal = false;
        }

        return retVal;
    }

    // Delete a list item from a grocery list.
    public boolean deleteFromGroceryList(String groceryList, String itemName) {
        boolean retVal = true;

        SQLiteDatabase db = getWritableDatabase();

        int itemNameId = getItemNameId(itemName);
        int groceryListId = getGroceryListId(groceryList);

        int resp = db.delete(GroceryListDetailTable.TABLE_NAME,
                GroceryListDetailTable.COLUMN_LIST_ID + " = ? AND "
                        + GroceryListDetailTable.COLUMN_ITEM_ID + " = ?",
                new String[]{String.valueOf(groceryListId), String.valueOf(itemNameId)});

        if (resp <= 0) {
            retVal = false;
        }

        return retVal;
    }

    // Add item to grocery list.
    public boolean addItemToGroceryList(String groceryList, ListItem item) {
        boolean retVal = true;

        int checkedOff = 0;

        if (item.isCheckedOff()) {
            checkedOff = 1;
        }

        ContentValues values = new ContentValues();
        values.put(GroceryListDetailTable.COLUMN_LIST_ID, getGroceryListId(groceryList));
        values.put(GroceryListDetailTable.COLUMN_ITEM_ID, getItemNameId(item.getItemName()));
        values.put(GroceryListDetailTable.COLUMN_QUANTITY, item.getQuantity());
        values.put(GroceryListDetailTable.COLUMN_CHECKED_OFF, checkedOff);

        SQLiteDatabase db = getWritableDatabase();

        long resp = db.insert(GroceryListDetailTable.TABLE_NAME, null, values);

        if (resp == -1) {
            retVal = false;
        }

        return retVal;
    }

    //db method
    //add new item to database return true if successful.
    public void addNewItem(String itemName, String itemType) {
        List<String> itemNamesByItemType = getItemNamesByItemType(itemType);
        if (itemNamesByItemType.isEmpty()){
            addNewItemType(itemType);
        }

        getReadableDatabase().execSQL("INSERT INTO item_name (type_id, name) VALUES (" + getItemTypeId(itemType) + " , '"+ itemName + "' );");
    }

    private void addNewItemType(String itemType) {
        getReadableDatabase().execSQL("INSERT INTO item_type ( type ) VALUES ('" + itemType + "');") ;
    }

    private Integer getItemTypeId(String itemType){
        Integer itemId = null;
        String query = "SELECT _ID FROM item_type WHERE type ='" + itemType + "'";
        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        if (cursor.moveToFirst()) {
            itemId = cursor.getInt(0);
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return itemId;
    }

    //Return all grocery lists.
    public List<String> getGroceryLists() {
        List<String> list = new ArrayList<>();

        String SELECT_QUERY = String.format("SELECT %s FROM %s",
                GroceryListTable.COLUMN_LIST_NAME,
                GroceryListTable.TABLE_NAME);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                String type = cursor.getString(cursor.getColumnIndex(GroceryListTable.COLUMN_LIST_NAME));
                list.add(type);
            } while (cursor.moveToNext());
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return list;
    }

    //get all item types from database and return them.
    public List<String> getItemTypes() {
        List<String> types = new ArrayList<>();

        String TYPES_SELECT_QUERY =
                String.format("SELECT %s FROM %s",
                        ItemTypeTable.COLUMN_TYPE,
                        ItemTypeTable.TABLE_NAME);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TYPES_SELECT_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                String type = cursor.getString(0);
                types.add(type);
            } while (cursor.moveToNext());
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return types;
    }

    //get all item name from database and return them.
    public List<String> getItemNames() {
        List<String> types = new ArrayList<>();

        String query = String.format("SELECT %s FROM %s",
                ItemNameTable.COLUMN_NAME,
                ItemNameTable.TABLE_NAME);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String type = cursor.getString(0);
                types.add(type);
            } while (cursor.moveToNext());
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return types;
    }

    public ListItem getItemNameWithItemType(String itemName) {
        ListItem listItem = null;
        String query = "SELECT type FROM item_name S INNER JOIN item_type T ON S.type_id = T._id where S.name='" + itemName + "'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            listItem = new ListItem(itemName, cursor.getString(cursor.getColumnIndex("type")));
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return listItem;
    }

    //get all items in database that have this itemType.
    public List<String> getItemNamesByItemType(String itemType) {
        List<String> names = new ArrayList<>();

        //String TYPES_BY_NAME_SELECT_QUERY = "SELECT NAME FROM ITEM_NAME S INNER JOIN
        //ITEM_TYPE T ON S.type_id = T._id WHERE T.type = \"" + itemType +"\"";

        String TYPES_BY_NAME_SELECT_QUERY =
                String.format("SELECT %s FROM %s S INNER JOIN %s T ON S.%s = T.%s WHERE T.%s = \"%s\"",
                        ItemNameTable.COLUMN_NAME,
                        ItemNameTable.TABLE_NAME,
                        ItemTypeTable.TABLE_NAME,
                        ItemNameTable.COLUMN_TYPE_ID,
                        ItemTypeTable._ID,
                        ItemTypeTable.COLUMN_TYPE,
                        itemType);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TYPES_BY_NAME_SELECT_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(cursor.getColumnIndex(ItemNameTable.COLUMN_NAME)));
            } while (cursor.moveToNext());
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return names;
    }

    //Returns list of ListItems for the desired grocery list.
    public List<ListItem> getListItemsForGroceryList(String groceryList) {
        List<ListItem> list = new ArrayList<>();

        String SELECT_QUERY = String.format(
                "SELECT * FROM %s WHERE %s = \"%s\"",
                GroceryListView.VIEW_NAME,
                GroceryListView.COLUMN_LIST_NAME,
                groceryList);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(cursor.getColumnIndex(GroceryListView.COLUMN_ITEM_NAME));
                String itemType = cursor.getString(cursor.getColumnIndex(GroceryListView.COLUMN_ITEM_TYPE_NAME));
                String quantity = cursor.getString(cursor.getColumnIndex(GroceryListView.COLUMN_QUANTITY));
                int checkedOff = cursor.getInt(cursor.getColumnIndex(GroceryListView.COLUMN_CHECKED_OFF));

                boolean checkedOffStatus = true;

                if (checkedOff == 0) {
                    checkedOffStatus = false;
                }

                ListItem toAdd = new ListItem(itemName, itemType, quantity);
                toAdd.setCheckedOff(checkedOffStatus);

                list.add(toAdd);
            } while (cursor.moveToNext());
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return list;
    }

    public List<String> getItemTypeForGroceryList(String groceryList) {
        List<String> types = new ArrayList<>();

        String query = String.format("SELECT DISTINCT %s FROM %s WHERE %s = \"%s\"",
                GroceryListView.COLUMN_ITEM_TYPE_NAME,
                GroceryListView.VIEW_NAME,
                GroceryListView.COLUMN_LIST_NAME,
                groceryList);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String type = cursor.getString(cursor.getColumnIndex(GroceryListView.COLUMN_ITEM_TYPE_NAME));
                types.add(type);
            } while (cursor.moveToNext());
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return types;
    }

    public ArrayList<ListItem> getAllItemsForListAndType(String groceryList, String itemType) {
        ArrayList<ListItem> list = new ArrayList<>();

        String query = String.format("SELECT * FROM %s WHERE %s = \"%s\" AND %s = \"%s\"",
                GroceryListView.VIEW_NAME,
                GroceryListView.COLUMN_LIST_NAME,
                groceryList,
                GroceryListView.COLUMN_ITEM_TYPE_NAME,
                itemType);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(cursor.getColumnIndex(GroceryListView.COLUMN_ITEM_NAME));
                String type = cursor.getString(cursor.getColumnIndex(GroceryListView.COLUMN_ITEM_TYPE_NAME));
                String quantity = cursor.getString(cursor.getColumnIndex(GroceryListView.COLUMN_QUANTITY));
                int checkedOff = cursor.getInt(cursor.getColumnIndex(GroceryListView.COLUMN_CHECKED_OFF));

                boolean checkedOffStatus = true;

                if (checkedOff == 0) {
                    checkedOffStatus = false;
                }

                ListItem toAdd = new ListItem(itemName, type, quantity);
                toAdd.setCheckedOff(checkedOffStatus);

                list.add(toAdd);
            } while (cursor.moveToNext());
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return list;
    }
}
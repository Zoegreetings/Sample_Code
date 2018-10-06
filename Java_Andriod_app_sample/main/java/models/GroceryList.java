package edu.gatech.seclass.glm.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by trevorloranger on 10/10/16.
 */

public class GroceryList {

    private String name;
    private ArrayList<ListItem> listItems;
    private HashMap<String, ArrayList<ListItem>> allListItems;
    private HashMap<String, ListItem> list;

    public GroceryList(String name) {
        this.name = name;
        this.list = new HashMap<>();
        this.listItems = new ArrayList<>();
        this.allListItems = new HashMap<>();
    }

    //returns true if added successfully
    //needs to do check for proper itemType
    public boolean addItem(String itemName, String itemType, String units) {
        //to check if the listItem to be added exists or not
        String oldItemType = "";
        if (list.containsKey(itemName)) {
            ListItem oldListItem = list.get(itemName);
            oldItemType = oldListItem.getItemType();
            if (itemType.equals(oldItemType)) {
                return false;
            }
        }

        if (!list.containsKey(itemName) || !itemType.equals(oldItemType)) {
            ListItem listItem = new ListItem(itemName, itemType, units);
            list.put(itemName, listItem);
            return true;
        }

        return false;
    }

    //returns true if deleted successfully
    public boolean deleteItem(String name) {
        //to do delete item in hashmap corresponding to name remove this key.
        if (list.containsKey(name)) {
            list.remove(name);
            return true;
        } else
            return false;
    }

    public boolean changeQuantity(String name, String quantity) {
        //get key for this name and change quantity field using setter.
        if (list.containsKey(name)) {
            ListItem listItem = list.get(name);
            listItem.setQuantity(quantity);
            return true;
        } else
            return false;
    }

    //return true if change was successfully done
    public boolean checkOff(String name) {
        //find key with name and flip boolean checkoff status.
        if (list.containsKey(name)) {
            ListItem listItem = list.get(name);
            if (listItem.isCheckedOff()) {
                listItem.setCheckedOff(false);
            } else {
                listItem.setCheckedOff(true);
            }
            return true;
        } else
            return false;
    }


    public void clearAllCheckOffs() {
        //set all check off statuses to false for all keys.
        for (String key : list.keySet()) {
            ListItem listItem = list.get(key);
            listItem.setCheckedOff(false);
        }

    }

    public HashMap<String, ListItem> getList() {
        return list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ListItem> getListItems() {
        return listItems;
    }

    public void setListItems(ArrayList<ListItem> listItems) {
        this.listItems = listItems;
    }

    public HashMap<String, ArrayList<ListItem>> getAllListItems() {
        return allListItems;
    }

    public void setAllListItems(HashMap<String, ArrayList<ListItem>> allListItems) {
        this.allListItems = allListItems;
    }
}

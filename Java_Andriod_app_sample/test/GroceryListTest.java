package edu.gatech.seclass.glm;

import org.junit.Test;

import edu.gatech.seclass.glm.models.GroceryList;
import edu.gatech.seclass.glm.models.ListItem;

import static org.junit.Assert.*;

/**
 * GroceryList unit tests, which will execute on the development machine (host).
 */
public class GroceryListTest {

    @Test
    public void GroceryListConstructorTest() {
        GroceryList newList = new GroceryList("Grocery");

       assertEquals(newList.getName(), "Grocery");
    }

    @Test
    public void GroceryListSetAndGetNameTest() {
        GroceryList newList = new GroceryList("Weekly List");

        assertEquals(newList.getName(), "Weekly List");

        newList.setName("Monthly List");

        assertEquals(newList.getName(), "Monthly List");
    }

    @Test
    public void GroceryListAddItemTest() {
        GroceryList newList = new GroceryList("Grocery");

        newList.addItem("Carrots", "Vegetable", "1 unit");

        ListItem newItem = newList.getList().get("Carrots");

        assertEquals(newItem.getItemName(), "Carrots");
        assertEquals(newItem.getItemType(), "Vegetable");
        assertEquals(newItem.getQuantity(), "1 unit");
    }

    @Test
    public void GroceryListDeleteItemTest() {
        GroceryList newList = new GroceryList("Grocery");

        newList.addItem("Apple", "Fruit", "1 unit");
        newList.addItem("Carrots", "Vegetable", "3 units");
        newList.addItem("Beef", "Meat", "2 lbs");

        assertEquals(newList.getList().size(), 3);

        ListItem newItem = newList.getList().get("Apple");

        assertEquals(newItem.getItemName(), "Apple");
        assertEquals(newItem.getItemType(), "Fruit");
        assertEquals(newItem.getQuantity(), "1 unit");

        newItem = newList.getList().get("Carrots");

        assertEquals(newItem.getItemName(), "Carrots");
        assertEquals(newItem.getItemType(), "Vegetable");
        assertEquals(newItem.getQuantity(), "3 units");

        newItem = newList.getList().get("Beef");

        assertEquals(newItem.getItemName(), "Beef");
        assertEquals(newItem.getItemType(), "Meat");
        assertEquals(newItem.getQuantity(), "2 lbs");

        newList.deleteItem("Carrots");

        assertEquals(newList.getList().size(), 2);

        newItem = newList.getList().get("Apple");

        assertEquals(newItem.getItemName(), "Apple");
        assertEquals(newItem.getItemType(), "Fruit");
        assertEquals(newItem.getQuantity(), "1 unit");

        newItem = newList.getList().get("Carrots");

        assertEquals(newItem, null);

        newItem = newList.getList().get("Beef");

        assertEquals(newItem.getItemName(), "Beef");
        assertEquals(newItem.getItemType(), "Meat");
        assertEquals(newItem.getQuantity(), "2 lbs");
    }

    @Test
    public void GroceryListChangeQuantityTest() {
        GroceryList newList = new GroceryList("Grocery");

        newList.addItem("Carrots", "Vegetable", "1 unit");

        assertEquals(newList.getList().get("Carrots").getQuantity(), "1 unit");

        newList.changeQuantity("Carrots", "3 units");

        assertEquals(newList.getList().get("Carrots").getQuantity(), "3 units");
    }

    @Test
    public void GroceryListCheckOffTest() {
        GroceryList newList = new GroceryList("Grocery");

        newList.addItem("Carrots", "Vegetable", "1 unit");

        assertEquals(newList.getList().get("Carrots").isCheckedOff(), false);

        newList.checkOff("Carrots");

        assertEquals(newList.getList().get("Carrots").isCheckedOff(), true);
    }

    @Test
    public void GroceryListClearAllCheckOffsTest() {
        GroceryList newList = new GroceryList("Grocery");

        newList.addItem("Carrots", "Vegetable", "1 unit");
        newList.addItem("Apple", "Fruit", "3 units");
        newList.addItem("Beef", "Meat", "2 lbs");

        newList.checkOff("Carrots");
        newList.checkOff("Apple");

        assertEquals(newList.getList().get("Carrots").isCheckedOff(), true);
        assertEquals(newList.getList().get("Apple").isCheckedOff(), true);
        assertEquals(newList.getList().get("Beef").isCheckedOff(), false);

        newList.clearAllCheckOffs();

        assertEquals(newList.getList().get("Carrots").isCheckedOff(), false);
        assertEquals(newList.getList().get("Apple").isCheckedOff(), false);
        assertEquals(newList.getList().get("Beef").isCheckedOff(), false);
    }
}
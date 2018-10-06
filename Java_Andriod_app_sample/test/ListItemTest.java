package edu.gatech.seclass.glm;

import org.junit.Test;

import edu.gatech.seclass.glm.models.ListItem;

import static org.junit.Assert.*;

/**
 * ListItem unit tests, which will execute on the development machine (host).
 */
public class ListItemTest {

    @Test
    public void ListItemConstructorTest() {
        ListItem newItem = new ListItem("Apple", "Fruit", "1 unit");

        assertEquals(newItem.getItemName(), "Apple");
        assertEquals(newItem.getItemType(), "Fruit");
        assertEquals(newItem.getQuantity(), "1 unit");
    }

    @Test
    public void ListItemSetAndGetItemTypeTest() {
        ListItem newItem = new ListItem("Apple", "Fruit", "1 unit");

        assertEquals(newItem.getItemName(), "Apple");
    }

    @Test
    public void ListItemSetAndGetItemNameTest() {
        ListItem newItem = new ListItem("Apple", "Fruit", "1 unit");

        assertEquals(newItem.getItemType(), "Fruit");
    }

    @Test
    public void ListItemSetAndGetQuantityTest() {
        ListItem newItem = new ListItem("Apple", "Fruit", "1 unit");

        newItem.setQuantity("5 units");

        assertEquals(newItem.getQuantity(), "5 units");
    }

    @Test
    public void ListItemSetAndGetCheckedOffTest() {
        ListItem newItem = new ListItem("Apple", "Fruit", "1 unit");

        newItem.setCheckedOff(true);

        assertEquals(newItem.isCheckedOff(), true);

        newItem.setCheckedOff(false);

        assertEquals(newItem.isCheckedOff(), false);
    }
}
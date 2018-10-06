package edu.gatech.seclass.glm.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class representing an item within a list.
 * <p>
 * Created by trevorloranger on 10/10/16.
 */

public class ListItem {

    private String itemType, itemName, quantity;

    private boolean checkedOff;

    public ListItem(String itemName, String itemType) {
        this.itemType = itemType;
        this.itemName = itemName;
    }

    public ListItem(String itemName, String itemType, String quantity) {
        this(itemName, itemType);
        this.quantity = quantity;
        this.checkedOff = false;
    }

    public String getItemType() {
        return itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public boolean isCheckedOff() {
        return checkedOff;
    }

    public void setCheckedOff(boolean checkedOff) {
        this.checkedOff = checkedOff;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

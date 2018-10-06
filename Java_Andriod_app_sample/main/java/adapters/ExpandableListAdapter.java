package edu.gatech.seclass.glm.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.database.GroceryListDbHelper;
import edu.gatech.seclass.glm.dialogs.ModifyItemQuantity;
import edu.gatech.seclass.glm.models.ListItem;

/**
 * Created by bijayrijal on 10/16/16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Activity activity;
    private HashMap<String, ArrayList<ListItem>> allListItems;
    private List<String> itemTypes = new ArrayList<>();
    private String groceryListName;

    public ExpandableListAdapter(Activity activity, List<String> itemTypes, HashMap<String, ArrayList<ListItem>> allListItems) {
        this.activity = activity;
        this.allListItems = allListItems;
        this.itemTypes.addAll(itemTypes);
    }

    public void addNewItem(ListItem listItem){
        ArrayList<ListItem> itemNameList = getItemByType(listItem.getItemType());
        itemNameList.add(listItem);
        allListItems.put(listItem.getItemType(), itemNameList);

    }

    private ArrayList<ListItem> getItemByType(String itemType) {
        ArrayList<ListItem> listItems = allListItems.get(itemType);
        if (listItems == null){
            Log.d("addNewItem" , "list items is null");
            itemTypes.add(itemType);
            return new ArrayList<>();
        }

        return listItems;
    }

    public ListItem getChild(int groupPosition, int childPosition) {
        return allListItems.get(itemTypes.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ListItem listItem = getChild(groupPosition, childPosition);
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.item_name_text_view);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
        checkBox.setChecked(listItem.isCheckedOff());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GroceryListDbHelper.getInstance(buttonView.getContext()).flipCheckedOffStatus(groceryListName,listItem.getItemName());
                listItem.setCheckedOff(isChecked);
            }
        });
        String screenText = String.format("%s, %s", listItem.getItemName(), listItem.getQuantity());
        item.setText(screenText);
        item.setLongClickable(true);

        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ModifyItemQuantity modifyItemQuantity = new ModifyItemQuantity();
                modifyItemQuantity.setSelectedItem(listItem);
                modifyItemQuantity.setAdapter(ExpandableListAdapter.this);
                modifyItemQuantity.show(activity.getFragmentManager(), "Dialog");
                return true;
            }

        };


        item.setOnLongClickListener(onLongClickListener);
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return allListItems.get(itemTypes.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return itemTypes.get(groupPosition);
    }

    public int getGroupCount() {
        return itemTypes.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String itemName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.items_by_type, null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.item_type_text_view);
        item.setTypeface(null, Typeface.BOLD);
        item.setTextSize(20);
        item.setText(itemName);

        convertView.setPadding(0, 50, 0, 0);

        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void removeChecked() {
        for (Iterator<Map.Entry<String, ArrayList<ListItem>>> it = allListItems.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, ArrayList<ListItem>> entry = it.next();
            ArrayList<ListItem> value = entry.getValue();
            Iterator<ListItem> listIt = value.iterator();
            while (listIt.hasNext()) {
                ListItem item = listIt.next();
                if (item.isCheckedOff()) {
                    listIt.remove();
                }
            }
            if (value.isEmpty()) {
                itemTypes.remove(entry.getKey());
                it.remove();
            }
        }
        clearAllChecks();
    }

    public void clearAllChecks() {
        for (int i = 0; i < getGroupCount(); i++) {
            for (int j = 0; j < getChildrenCount(i); j++) {
                getChild(i, j).setCheckedOff(false);
            }
        }
    }

    public List<String> findAllChecks() {
        List<String> itemNames = new ArrayList<>();
        for (int i = 0; i < getGroupCount(); i++) {
            for (int j = 0; j < getChildrenCount(i); j++) {
                ListItem child = getChild(i, j);
                if (child.isCheckedOff()){
                    itemNames.add(child.getItemName());
                }
            }
        }
        return itemNames;
    }

    public String getGroceryListName() {
        return groceryListName;
    }

    public void setGroceryListName(String groceryListName) {
        this.groceryListName = groceryListName;
    }
}

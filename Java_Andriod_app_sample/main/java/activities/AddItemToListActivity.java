package edu.gatech.seclass.glm.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.adapters.DataChangeNotifier;
import edu.gatech.seclass.glm.adapters.ExpandableListAdapter;
import edu.gatech.seclass.glm.database.GroceryListDbHelper;
import edu.gatech.seclass.glm.dialogs.AddItemByNameDialog;
import edu.gatech.seclass.glm.dialogs.AddItemByTypeDialog;
import edu.gatech.seclass.glm.dialogs.DeleteItemDialog;
import edu.gatech.seclass.glm.dialogs.UncheckAllDialog;
import edu.gatech.seclass.glm.models.ListItem;

import static edu.gatech.seclass.glm.dialogs.AddItemByTypeDialog.GROCERY_LIST_NAME;

public class AddItemToListActivity extends AppCompatActivity implements DataChangeNotifier{

    public static final String POSITION_IN_LIST = "position";
    ExpandableListAdapter adapter;
    String groceryListName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_to_list_activity);
        populateScreenWithExistingData();

        Button addByTypeButton = (Button) findViewById(R.id.by_type_button);
        addByTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arg = new Bundle();
                arg.putString(GROCERY_LIST_NAME, groceryListName);
                AddItemByTypeDialog addItemByTypeDialog = new AddItemByTypeDialog();
                addItemByTypeDialog.setArguments(arg);
                addItemByTypeDialog.setNotifier(AddItemToListActivity.this);
                addItemByTypeDialog.show(getFragmentManager(), "dialog");
            }
        });

        Button addByNameButton = (Button) findViewById(R.id.by_name_button);
        addByNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arg = new Bundle();
                arg.putString(GROCERY_LIST_NAME, groceryListName);
                AddItemByNameDialog addItemByNameDialog = new AddItemByNameDialog();
                addItemByNameDialog.setArguments(arg);
                addItemByNameDialog.setNotifier(AddItemToListActivity.this);
                addItemByNameDialog.show(getFragmentManager(), "dialog");
            }
        });

        Button deleteItemButton = (Button) findViewById(R.id.delete_item_button);
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteItemDialog deleteItemDialog = new DeleteItemDialog();
                deleteItemDialog.setAdapter(adapter);
                deleteItemDialog.setGroceryListName(groceryListName);
                deleteItemDialog.show(getFragmentManager(), "dialog");
            }
        });

        Button clearAllChecksButton = (Button) findViewById(R.id.clear_all_button);
        clearAllChecksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clear all checks listener :" +groceryListName);
                GroceryListDbHelper.getInstance(v.getContext()).updateCheckOffStatus(groceryListName,false);
                UncheckAllDialog uncheckAllDialog = new UncheckAllDialog();
                uncheckAllDialog.setAdapter(adapter);
                uncheckAllDialog.show(getFragmentManager(), "dialog");
            }
        });
    }

    private void populateScreenWithExistingData() {
        TextView groceryHeader = (TextView) findViewById(R.id.grocery_name_text_view);
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.item_type_expandable_list_view);

        GroceryListDbHelper h = GroceryListDbHelper.getInstance(this);
        List<String> groceryList = h.getGroceryLists();

        groceryListName = groceryList.get(getIntent().getIntExtra(POSITION_IN_LIST, 0));
        groceryHeader.setText(groceryListName);
        List<String> itemTypeForGroceryList = h.getItemTypeForGroceryList(groceryListName);

        HashMap<String, ArrayList<ListItem>> allItems = new HashMap<>();

        for (String itemType : itemTypeForGroceryList){
            ArrayList<ListItem> allItemsForListAndType = h.getAllItemsForListAndType(groceryListName, itemType);
            allItems.put(itemType, allItemsForListAndType);
        }



        adapter = new ExpandableListAdapter(this, itemTypeForGroceryList, allItems);
        adapter.setGroceryListName(groceryListName);
        expandableListView.setAdapter(adapter);
        expandableListView.setLongClickable(true);

        explodeTheList(expandableListView);

    }

    private void explodeTheList(ExpandableListView expandableListView) {
        for (int i = 0; i < this.adapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
    }

    @Override
    public void update(ListItem listItem) {
        adapter.addNewItem(listItem);
        adapter.notifyDataSetChanged();
        adapter.setGroceryListName(groceryListName);
        explodeTheList((ExpandableListView) findViewById(R.id.item_type_expandable_list_view));
    }
}

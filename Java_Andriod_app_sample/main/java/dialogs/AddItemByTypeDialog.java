package edu.gatech.seclass.glm.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.adapters.DataChangeNotifier;
import edu.gatech.seclass.glm.database.GroceryListDbHelper;
import edu.gatech.seclass.glm.models.ListItem;

import static edu.gatech.seclass.glm.R.string.confirm_button;

/**
 * Created by bijayrijal on 10/12/16.
 */

public class AddItemByTypeDialog extends DialogFragment {

    public static final String GROCERY_LIST_NAME = "GroceryListName";

    private String selectedItemType = "";
    private String selectedItemName;
    private ArrayAdapter<String> itemNamesAdapter;
    private DataChangeNotifier notifier;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View addItemByTypeDialogView = getActivity().getLayoutInflater().inflate(R.layout.add_item_by_type_dialog_layout, null);
        final TextView itemTypeTextView = (TextView) addItemByTypeDialogView.findViewById(R.id.selected_item_text_view);
        final TextView itemNameTextView = (TextView) addItemByTypeDialogView.findViewById(R.id.selected_item_name_text_view);

        final GroceryListDbHelper h = GroceryListDbHelper.getInstance(getActivity().getApplicationContext());
        final List<String> itemTypes = h.getItemTypes();

        itemNamesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, h.getItemNamesByItemType(selectedItemType));
        ListView itemsNamesListView = (ListView) addItemByTypeDialogView.findViewById(R.id.item_name_list_view);
        itemsNamesListView.setAdapter(itemNamesAdapter);
        itemsNamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> itemsNameByType = h.getItemNamesByItemType(selectedItemType);
                selectedItemName = itemsNameByType.get(position);
                itemNameTextView.setText(selectedItemName);
            }
        });

        ListView itemsByTypeListView = (ListView) addItemByTypeDialogView.findViewById(R.id.item_type_list_view);
        itemsByTypeListView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, itemTypes));
        itemsByTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemType = itemTypes.get(position);
                itemTypeTextView.setText(selectedItemType);
                itemNameTextView.setText(R.string.select_item_name);
                itemNamesAdapter.clear();
                itemNamesAdapter.addAll(h.getItemNamesByItemType(selectedItemType));
                itemNamesAdapter.notifyDataSetChanged();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(addItemByTypeDialogView)
                .setPositiveButton(confirm_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String quantity = ((EditText) addItemByTypeDialogView.findViewById(R.id.quantity_edit_view)).getText().toString();
                        String groceryName = ((TextView) getActivity().findViewById(R.id.grocery_name_text_view)).getText().toString();
                        boolean alreadyContained = false;
                        List<ListItem> myList = h.getListItemsForGroceryList(groceryName);
                        for (ListItem item : myList) {
                            if ((item.getItemName()).equals(selectedItemName)) {
                                alreadyContained = true;
                            }
                        }
                        if (selectedItemName == null || selectedItemType == null) {
                            Toast.makeText(getActivity().getApplicationContext(), "No item selected!", Toast.LENGTH_LONG).show();
                        } else if (alreadyContained) {
                            Toast.makeText(getActivity().getApplicationContext(), "Item already in list!", Toast.LENGTH_SHORT).show();
                        } else if ("".equalsIgnoreCase(quantity)) {
                            Toast.makeText(getActivity().getApplicationContext(), "Quantity not entered!", Toast.LENGTH_LONG).show();
                        } else {
                            ListItem listItem = new ListItem(selectedItemName, selectedItemType, quantity);
                            h.addItemToGroceryList(getArguments().getString(GROCERY_LIST_NAME), listItem);
                            notifier.update(listItem);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddItemByTypeDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public DataChangeNotifier getNotifier() {
        return notifier;
    }

    public void setNotifier(DataChangeNotifier notifier) {
        this.notifier = notifier;
    }
}

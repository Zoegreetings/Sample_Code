package edu.gatech.seclass.glm.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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

import static edu.gatech.seclass.glm.dialogs.AddItemByTypeDialog.GROCERY_LIST_NAME;

/**
 * Created by bijayrijal on 10/12/16.
 */

public class AssignTypeToItemDialog extends DialogFragment {

    private String itemName;
    private String itemType;
    private List<String> itemTypes;
    private DataChangeNotifier notifier;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final GroceryListDbHelper h = GroceryListDbHelper.getInstance(getActivity().getApplicationContext());
        itemTypes = h.getItemTypes();

        final View assignTypeToItem = getActivity().getLayoutInflater().inflate(R.layout.assign_type_to_item_layout, null);

        TextView itemNameTextView = (TextView) assignTypeToItem.findViewById(R.id.new_item_name_text_view);
        itemNameTextView.setText(itemName);

        final EditText itemTypeEditText = (EditText) assignTypeToItem.findViewById(R.id.item_name_edit_view);

        ListView itemTypeListView = (ListView) assignTypeToItem.findViewById(R.id.add_by_type_list_view1);
        itemTypeListView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, itemTypes));
        itemTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemType = itemTypes.get(position);
                itemTypeEditText.setText(itemType);
                Log.d(this.getClass().getName(), itemType);
            }
        });



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(assignTypeToItem)
                .setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String quantity = ((EditText) assignTypeToItem.findViewById(R.id.quantity_edit_view1)).getText().toString();
                        String itemType = itemTypeEditText.getText().toString();
                        if ("".equalsIgnoreCase(itemType)) {
                            Toast.makeText(getActivity().getApplicationContext(), "Item type not entered !", Toast.LENGTH_LONG).show();
                        } else if ("".equalsIgnoreCase(quantity)) {
                            Toast.makeText(getActivity().getApplicationContext(), "Quantity not entered !", Toast.LENGTH_LONG).show();
                        } else {
                            h.addNewItem(itemName, itemType);
                            ListItem listItem = new ListItem(itemName, itemType, quantity);
                            listItem.setQuantity(quantity);
                            h.addItemToGroceryList(getArguments().getString(GROCERY_LIST_NAME), listItem);
                            notifier.update(listItem);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AssignTypeToItemDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setNotifier(DataChangeNotifier notifier) {
        this.notifier = notifier;
    }
}

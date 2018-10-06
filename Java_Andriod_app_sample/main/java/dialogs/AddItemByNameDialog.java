package edu.gatech.seclass.glm.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.activities.AddItemToListActivity;
import edu.gatech.seclass.glm.adapters.DataChangeNotifier;
import edu.gatech.seclass.glm.database.GroceryListDbHelper;
import edu.gatech.seclass.glm.models.GroceryList;
import edu.gatech.seclass.glm.models.ListItem;

import static edu.gatech.seclass.glm.dialogs.AddItemByTypeDialog.GROCERY_LIST_NAME;

/**
 * Created by bijayrijal on 10/12/16.
 */
public class AddItemByNameDialog extends DialogFragment {

    private ArrayAdapter<String> adapter;
    private String searchedText;
    private DataChangeNotifier notifier;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final GroceryListDbHelper h = GroceryListDbHelper.getInstance(getActivity().getApplicationContext());
        final List<String> itemNames = h.getItemNames();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, itemNames);
        final View addItemByNameDialogView = getActivity().getLayoutInflater().inflate(R.layout.add_item_by_name_dialog_layout, null);

        final EditText searchEditView = (EditText) addItemByNameDialogView.findViewById(R.id.search_item_edit_view);
        searchEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchedText = s.toString();
            }
        });

        ListView itemsByNameListView = (ListView) addItemByNameDialogView.findViewById(R.id.search_item_name_list_view);
        itemsByNameListView.setAdapter(adapter);
        itemsByNameListView.setTextFilterEnabled(true);
        itemsByNameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchEditView.setText((String) parent.getItemAtPosition(position));
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(addItemByNameDialogView)
                .setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        boolean alreadyContained = false;
                        ListItem listItem = h.getItemNameWithItemType(searchEditView.getText().toString());
                        String quantity = ((EditText) addItemByNameDialogView.findViewById(R.id.quantity_edit_view)).getText().toString();
                        String groceryName = ((TextView) getActivity().findViewById(R.id.grocery_name_text_view)).getText().toString();
                        List<ListItem> myList = h.getListItemsForGroceryList(groceryName);
                        for (ListItem item : myList) {
                            if ((item.getItemName()).equals(searchedText)) {
                                alreadyContained = true;
                            }
                        }
                        if (searchedText == null) {
                            Toast.makeText(getActivity().getApplicationContext(), "No item selected!", Toast.LENGTH_LONG).show();
                        } else if (alreadyContained) {
                            Toast.makeText(getActivity().getApplicationContext(), "Item already in list!", Toast.LENGTH_SHORT).show();
                        } else if (listItem == null) {
                            AssignTypeToItemDialog assignTypeToItemDialog = new AssignTypeToItemDialog();
                            assignTypeToItemDialog.setArguments(getArguments());
                            assignTypeToItemDialog.setItemName(searchedText);
                            assignTypeToItemDialog.setNotifier(notifier);
                            assignTypeToItemDialog.show(getFragmentManager(), "dialog");
                        } else if ("".equalsIgnoreCase(quantity)) {
                            Toast.makeText(getActivity().getApplicationContext(), "Quantity not entered!", Toast.LENGTH_LONG).show();
                        } else {
                            listItem.setQuantity(quantity);
                            h.addItemToGroceryList(getArguments().getString(GROCERY_LIST_NAME), listItem);
                            notifier.update(listItem);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddItemByNameDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void setNotifier(AddItemToListActivity notifier) {
        this.notifier = notifier;
    }
}

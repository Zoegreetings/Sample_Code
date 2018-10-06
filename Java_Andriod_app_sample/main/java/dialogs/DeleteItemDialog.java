package edu.gatech.seclass.glm.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.adapters.ExpandableListAdapter;
import edu.gatech.seclass.glm.database.GroceryListDbHelper;


/**
 * Created by oharlowe on 10/19/16.
 */
public class DeleteItemDialog extends DialogFragment {

    private ExpandableListAdapter adapter;
    private String groceryListName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.delete_grocery_list_layout, null);

        builder.setView(view)
                .setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        GroceryListDbHelper h = GroceryListDbHelper.getInstance(getActivity().getApplicationContext());
                        //TODO : fix the delete from database Trevor
                        for (String itemName : adapter.findAllChecks()) {
                            h.deleteFromGroceryList(groceryListName, itemName);
                        }
                        adapter.removeChecked();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteItemDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void setAdapter(ExpandableListAdapter adapter) {
        this.adapter = adapter;
    }

    public void setGroceryListName(String groceryListName) {
        this.groceryListName = groceryListName;
    }
}

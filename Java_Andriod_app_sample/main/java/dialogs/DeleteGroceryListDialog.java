package edu.gatech.seclass.glm.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.adapters.GroceryListAdapter;
import edu.gatech.seclass.glm.database.GroceryListDbHelper;

/**
 * Created by bijayrijal on 10/12/16.
 */

public class DeleteGroceryListDialog extends DialogFragment {

    GroceryListAdapter groceryListAdapter;

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
                        List<String> groceryListArray = h.getGroceryLists();
                        h.deleteGroceryList(groceryListArray.get(groceryListAdapter.getSelectedPosition()));
                        groceryListAdapter.remove(groceryListArray.get(groceryListAdapter.getSelectedPosition()));
                        groceryListAdapter.notifyDataSetChanged();
                        groceryListAdapter.setSelectedPosition(-1);
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteGroceryListDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void setGroceryListAdapter(GroceryListAdapter groceryListAdapter) {
        this.groceryListAdapter = groceryListAdapter;
    }
}

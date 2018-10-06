package edu.gatech.seclass.glm.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.adapters.GroceryListAdapter;
import edu.gatech.seclass.glm.database.GroceryListDbHelper;

/**
 * Created by bijayrijal on 10/12/16.
 */

public class RenameGroceryListDialog extends DialogFragment {

    GroceryListAdapter groceryListAdapter;

    List<String> groceryListArray;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final GroceryListDbHelper h = GroceryListDbHelper.getInstance(getActivity().getApplicationContext());
        groceryListArray = groceryListAdapter.getGroceryList();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.rename_grocery_list_layout, null);
        final EditText newGroceryName = (EditText) view.findViewById(R.id.newListEditText);
        final TextView selectTextView = (TextView) view.findViewById(R.id.selectedListTextView);
        selectTextView.setText(groceryListArray.get(groceryListAdapter.getSelectedPosition()));

        builder.setView(view)
                .setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (newGroceryName.getText().length() > 0) {
                            groceryListArray.set(groceryListAdapter.getSelectedPosition(), newGroceryName.getText().toString());
                            groceryListAdapter.notifyDataSetChanged();
                            h.renameGroceryList(selectTextView.getText().toString(),
                                    newGroceryName.getText().toString());
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "No name entered!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RenameGroceryListDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void setGroceryListAdapter(GroceryListAdapter groceryListAdapter) {
        this.groceryListAdapter = groceryListAdapter;
    }
}

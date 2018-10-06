package edu.gatech.seclass.glm.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.adapters.GroceryListAdapter;
import edu.gatech.seclass.glm.database.GroceryListDbHelper;

public class NewGroceryListDialog extends DialogFragment {

    GroceryListAdapter groceryListAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View newGroceryListView = getActivity().getLayoutInflater().inflate(R.layout.new_grocery_list_layout, null);
        final EditText listInputEditText = (EditText) newGroceryListView.findViewById(R.id.inputListName);

        builder.setView(newGroceryListView)
                .setPositiveButton(R.string.add_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        GroceryListDbHelper h = GroceryListDbHelper.getInstance(getActivity().getApplicationContext());
                        List<String> groceryListArray = groceryListAdapter.getGroceryList();
                        String newGroceryListName = listInputEditText.getText().toString();
                        Log.d("groceryListArray", groceryListArray.toString());
                        if (!"".equalsIgnoreCase(newGroceryListName)) {
                            if (!isNameAlreadyPresent(groceryListArray, newGroceryListName)) {
                                groceryListArray.add(newGroceryListName);
                                groceryListAdapter.notifyDataSetChanged();
                                h.addGroceryList(newGroceryListName);
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "Name already exists!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "No name entered!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NewGroceryListDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private boolean isNameAlreadyPresent(List<String> groceryListArray, String newGroceryListName) {
        for (String existingName : groceryListArray){
            if(existingName.equalsIgnoreCase(newGroceryListName)){
                return true;
            }
        }
        return false;
    }

    public void setGroceryListAdapter(GroceryListAdapter groceryListAdapter) {
        this.groceryListAdapter = groceryListAdapter;
    }

}

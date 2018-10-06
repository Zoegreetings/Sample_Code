package edu.gatech.seclass.glm.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.adapters.ExpandableListAdapter;
import edu.gatech.seclass.glm.database.GroceryListDbHelper;
import edu.gatech.seclass.glm.models.ListItem;

/**
 * Created by bijayrijal on 10/19/16.
 */

public class ModifyItemQuantity extends DialogFragment {

    private ListItem selectedItem;
    private ExpandableListAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View modifyQuantityDialog = getActivity().getLayoutInflater().inflate(R.layout.modify_quantity_dialog_layout, null);

        TextView changeQuantityText = (TextView) modifyQuantityDialog.findViewById(R.id.change_quantity_text);
        changeQuantityText.setText("' " + selectedItem.getItemName() + " '" );

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(modifyQuantityDialog)
                .setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String quantity = ((EditText) modifyQuantityDialog.findViewById(R.id.new_quantity_edit_text)).getText().toString();
                        selectedItem.setQuantity(quantity);
                        adapter.notifyDataSetChanged();
                        GroceryListDbHelper h = GroceryListDbHelper.getInstance(getActivity().getApplicationContext());
                        h.modifyListItemQuantity(adapter.getGroceryListName(), selectedItem.getItemName() , quantity);
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

    public void setSelectedItem(ListItem selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void setAdapter(ExpandableListAdapter adapter) {
        this.adapter = adapter;
    }

}

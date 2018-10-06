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

/**
 * Created by oharlowe on 10/20/16.
 */

public class UncheckAllDialog extends DialogFragment{

    private ExpandableListAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.uncheck_all_items_layout, null);

        builder.setView(view)
                .setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.clearAllChecks();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UncheckAllDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void setAdapter(ExpandableListAdapter adapter) {
        this.adapter = adapter;
    }
}

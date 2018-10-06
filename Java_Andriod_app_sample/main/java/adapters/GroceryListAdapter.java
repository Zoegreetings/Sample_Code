package edu.gatech.seclass.glm.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import edu.gatech.seclass.glm.R;

/**
 * Created by bijayrijal on 10/16/16.
 */

public class GroceryListAdapter extends ArrayAdapter<String> {

    private ListView groceryListView;
    private Context context;
    private Integer selectedPosition = -1;

    private List<String> groceryList;

    public GroceryListAdapter(Context context, List<String> groceryLists, ListView groceryListView) {
        super(context, R.layout.row, groceryLists);
        this.context = context;
        this.groceryListView = groceryListView;
        this.groceryList = groceryLists;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row, parent, false);

        TextView name = (TextView) convertView.findViewById(R.id.item_name_text_view);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
        checkBox.setChecked(position == selectedPosition);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (selectedPosition != -1) {
                        View childAt = groceryListView.getChildAt(selectedPosition);
                        CheckBox checkBox = (CheckBox) childAt.findViewById(R.id.checkBox1);
                        checkBox.setChecked(false);
                    }
                    selectedPosition = position;
                } else {
                    selectedPosition = -1;
                }
            }
        });
        name.setText(groceryList.get(position));
        return convertView;
    }

    public Integer getSelectedPosition() {
        return selectedPosition;
    }

    public List<String> getGroceryList() {
        return groceryList;
    }

    public void setSelectedPosition(Integer selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
}

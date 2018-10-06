package edu.gatech.seclass.glm.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import edu.gatech.seclass.glm.R;
import edu.gatech.seclass.glm.adapters.GroceryListAdapter;
import edu.gatech.seclass.glm.database.GroceryListDbHelper;
import edu.gatech.seclass.glm.dialogs.DeleteGroceryListDialog;
import edu.gatech.seclass.glm.dialogs.NewGroceryListDialog;
import edu.gatech.seclass.glm.dialogs.RenameGroceryListDialog;

import static edu.gatech.seclass.glm.activities.AddItemToListActivity.POSITION_IN_LIST;

public class MultiListManagerActivity extends AppCompatActivity {

    GroceryListAdapter adapter;
//    ArrayList<GroceryList> groceryLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GroceryListDbHelper h = GroceryListDbHelper.getInstance(this);
        //Keep here to initialize database when the app is first opened.
        SQLiteDatabase db = h.getReadableDatabase();
        List<String> groceryList = h.getGroceryLists();
        ListView groceryListView = (ListView) findViewById(R.id.grocery_ListView);
        adapter = new GroceryListAdapter(this, groceryList, groceryListView);
        groceryListView.setAdapter(adapter);
        groceryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AddItemToListActivity.class);
                intent.putExtra(POSITION_IN_LIST, position);
                startActivity(intent);
            }
        });

        Button createListButton = (Button) findViewById(R.id.create_list_button);
        createListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewGroceryListDialog newGroceryListDialog = new NewGroceryListDialog();
                newGroceryListDialog.setGroceryListAdapter(adapter);
                newGroceryListDialog.show(getFragmentManager(), "dialog");
            }
        });

        Button renameListButton = (Button) findViewById(R.id.rename_list_button);
        renameListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getSelectedPosition() >= 0) {
                    RenameGroceryListDialog renameGroceryListDialog = new RenameGroceryListDialog();
                    renameGroceryListDialog.setGroceryListAdapter(adapter);
                    renameGroceryListDialog.show(getFragmentManager(), "dialog");
                }else{
                    Toast.makeText(getApplicationContext(), "No list selected for rename!", Toast.LENGTH_LONG).show();
                }

            }
        });

        Button deleteButton = (Button) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getSelectedPosition() >= 0) {
                    DeleteGroceryListDialog deleteGroceryListDialog = new DeleteGroceryListDialog();
                    deleteGroceryListDialog.setGroceryListAdapter(adapter);
                    deleteGroceryListDialog.show(getFragmentManager(), "dialog");
                }else{
                    Toast.makeText(getApplicationContext(), "No list selected for delete!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

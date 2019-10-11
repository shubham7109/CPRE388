package edu.iastate.shoppinglist.Controllers;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.iastate.shoppinglist.Models.ShoppingItemsAdapter;
import edu.iastate.shoppinglist.Models.ShoppingListModel;
import edu.iastate.shoppinglist.Models.ShoppingListsAdapter;
import edu.iastate.shoppinglist.R;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Shopping list activity. This is created when a shopping list is opened.
 * It implements the click listener when a list item is clicked.
 * @author Shubham Sharma
 */
public class ShoppingItemsActivity extends AppCompatActivity implements ShoppingItemsAdapter.ItemClickListener {

    private ShoppingListModel shoppingListModel;
    private Realm realm;
    private TextView title;
    private RecyclerView recyclerView;
    private ArrayList<String> items;
    private ShoppingItemsAdapter adapter;
    private boolean isDialogOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.rv);

        items = new ArrayList<>();

        realm = Realm.getDefaultInstance();
        Long id = getIntent().getLongExtra("id",0);
        shoppingListModel = realm.where(ShoppingListModel.class).equalTo("id", id).findFirst();
        title.setText(shoppingListModel.getTitle());

        if(shoppingListModel == null){
            Toast.makeText(this, "Error finding shopping list", Toast.LENGTH_SHORT).show();
            return;
        }else{
            buildView();
        }


    }

    /**
     * Called when there are items in the list to build.
     * Populates the Recycler view
     */
    private void buildView() {

        items.clear();
        items.addAll(realm.copyFromRealm(shoppingListModel).getItems());

        if(items.size() == 0){
            Toast.makeText(this, "Add items to the Shopping list!", Toast.LENGTH_SHORT).show();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShoppingItemsAdapter(this, items);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Handles when remove item is clicked
     * Updates the item information within realm
     * @param position Position of the item clicked.
     */
    @Override
    public void onRemoveClick(int position) {
        String item = items.get(position);
        items.remove(item);

        realm.beginTransaction();
        shoppingListModel.getItems().clear();
        shoppingListModel.getItems().addAll(items);
        realm.commitTransaction();

        buildView();
    }

    /**
     * Handles when add item is clicked
     * Open a dialog for user to enter item.
     * @param view Button object instance
     */
    public void onAddItemClick(View view) {
        if(!isDialogOpen)
            openDialog();
    }

    /**
     * Open a dialog to add item to the list
     * Will handle adding it to the Realm data.
     */
    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Item");

        final EditText input = new EditText(this);
        input.setHint("  Enter Item...");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(!input.getText().toString().trim().equals("")){
                    addNewItemToRealm(input.getText().toString());
                    dialog.dismiss();
                    isDialogOpen = false;
                }else{
                    Toast.makeText(ShoppingItemsActivity.this, "Cannot add empty item", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    isDialogOpen = false;
                }
            }
        });
        builder.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                isDialogOpen = false;
            }
        });

        //Open dialog
        builder.show();
        isDialogOpen = true;
    }

    /**
     * Updates the realm data to add the item to realm
     * Once completed the rv is then updated
     * @param item String value of the item to be added to the shopping list
     */
    private void addNewItemToRealm(String item) {
        items.add(item);

        realm.beginTransaction();
        shoppingListModel.getItems().clear();
        shoppingListModel.getItems().addAll(items);
        realm.commitTransaction();

        buildView();
    }
}

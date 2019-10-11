package edu.iastate.shoppinglist.Controllers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import edu.iastate.shoppinglist.Models.ShoppingListModel;
import edu.iastate.shoppinglist.Models.ShoppingListsAdapter;
import edu.iastate.shoppinglist.R;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * This is the main activity for the app,
 * It implements the click listener when a shopping list is clicked.
 * @author Shubham Sharma
 */
public class ShoppingListsActivity extends AppCompatActivity implements ShoppingListsAdapter.ItemClickListener{

    private Realm realm;
    private boolean isDialogOpen = false;
    private RecyclerView recyclerView;
    private ShoppingListsAdapter adapter;
    private ArrayList<ShoppingListModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        list = new ArrayList<>();

        // Initialize Realm
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        //Find all the stored shopping lists
        RealmResults<ShoppingListModel> shoppingListActivities = realm.where(ShoppingListModel.class).findAll();

        if(shoppingListActivities.size() == 0){
            handleNoShoppingLists();
        }else{
            buildShoppingLists();
        }
    }

    /**
     * Rebuilds the shopping list recyclerView
     * every time it needs to be updated
     */
    private void buildShoppingLists() {

        RealmResults<ShoppingListModel> shoppingListModels = realm.where(ShoppingListModel.class).findAll();

        list.clear();
        list.addAll(realm.copyFromRealm(shoppingListModels));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShoppingListsAdapter(this, list);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * This displays the Toast when there is no shopping list
     */
    private void handleNoShoppingLists() {
        Toast.makeText(this, "No Lists Added", Toast.LENGTH_SHORT).show();
    }

    /**
     * Click listener for add new list
     * @param view The button view object
     */
    public void addNew(View view) {
        if(!isDialogOpen)
            openDialog();
    }

    /**
     * Open a dialog to create a new list.
     */
    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Shopping List");

        final EditText input = new EditText(this);
        input.setHint("  Enter Title...");
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
                    Toast.makeText(ShoppingListsActivity.this, "Cannot add empty item", Toast.LENGTH_SHORT).show();
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
     * Creates a new shopping list and
     * adds it to ream, then calls to update the views
     * @param title Title of the shopping list
     */
    private void addNewItemToRealm(String title){
        realm.beginTransaction();
        final ShoppingListModel shoppingListModel = new ShoppingListModel(title);
        realm.insertOrUpdate(shoppingListModel);
        realm.commitTransaction();
        buildShoppingLists();
    }

    /**
     * Handles when open list is clicked
     * Opens a new activity.
     * @param position Position of the item in the list
     */
    @Override
    public void onOpenListClick(int position) {
        ShoppingListModel shoppingListModel = realm.where(ShoppingListModel.class).findAll().get(position);

        Intent intent = new Intent(this, ShoppingItemsActivity.class);
        intent.putExtra("id", shoppingListModel.getId());
        this.startActivity(intent);
    }

    /**
     * Handles when delete list is clicked
     * Deletes the list from Realm data
     * @param position Position of the item in the list
     */
    @Override
    public void onDeleteListClick(int position) {

        RealmResults<ShoppingListModel> shoppingListModels = realm.where(ShoppingListModel.class).findAll();
        realm.beginTransaction();
        shoppingListModels.deleteFromRealm(position);
        realm.commitTransaction();

        buildShoppingLists();


    }

    /**
     * Handles when the duplicate list is clicked
     * Duplicates the list in the Realm data
     * @param position Position of the item in the list
     */
    @Override
    public void onDuplicateListClick(int position) {

        ShoppingListModel shoppingListModel = realm.where(ShoppingListModel.class).findAll().get(position);
        ShoppingListModel shoppingListModel_duplicate = new ShoppingListModel(shoppingListModel.getTitle());
        shoppingListModel_duplicate.setItems(shoppingListModel.getItems());

        realm.beginTransaction();
        realm.insertOrUpdate(shoppingListModel_duplicate);
        realm.commitTransaction();

        buildShoppingLists();

    }

    /**
     * Handles when the rename list is called
     * Open a dialog to rename the list within the Realm data
     * @param position Position of the item in the list
     */
    @Override
    public void onRenameListClick(final int position) {


        final ShoppingListModel shoppingListModel = realm.where(ShoppingListModel.class).findAll().get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Title Name");

        final EditText input = new EditText(this);
        input.setText(shoppingListModel.getTitle());
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if(!input.getText().toString().trim().equals("")){
                    ShoppingListModel shoppingListModel = realm.where(ShoppingListModel.class).findAll().get(position);
                    realm.beginTransaction();
                    shoppingListModel.setTitle(input.getText().toString());
                    realm.commitTransaction();
                    buildShoppingLists();
                    dialog.dismiss();
                    isDialogOpen = false;
                }else{
                    Toast.makeText(ShoppingListsActivity.this, "Cannot add empty item", Toast.LENGTH_SHORT).show();
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
}

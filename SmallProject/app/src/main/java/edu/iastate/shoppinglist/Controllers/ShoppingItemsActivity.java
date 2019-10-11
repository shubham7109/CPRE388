package edu.iastate.shoppinglist.Controllers;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
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

public class ShoppingItemsActivity extends AppCompatActivity implements ShoppingItemsAdapter.ItemClickListener {

    private ShoppingListModel shoppingListModel;
    private Realm realm;
    private TextView title;
    private RecyclerView recyclerView;
    private ArrayList<String> items;
    private ShoppingItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        title = findViewById(R.id.title);
        title.setText(shoppingListModel.getTitle());
        recyclerView = findViewById(R.id.rv);

        realm = Realm.getDefaultInstance();
        Long id = getIntent().getLongExtra("id",0);
        shoppingListModel = realm.where(ShoppingListModel.class).equalTo("id", id).findFirst();

        if(shoppingListModel == null){
            Toast.makeText(this, "Error finding shopping list", Toast.LENGTH_SHORT).show();
            return;
        }else{
            buildView();
        }


    }

    private void buildView() {

        items.clear();
        items.addAll(realm.copyFromRealm(shoppingListModel).getItems());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShoppingItemsAdapter(this, items);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onOpenListClick(int position) {

    }

    @Override
    public void onDeleteListClick(int position) {

    }

    @Override
    public void onDuplicateListClick(int position) {

    }
}

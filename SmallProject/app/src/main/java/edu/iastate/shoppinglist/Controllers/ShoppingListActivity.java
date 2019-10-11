package edu.iastate.shoppinglist.Controllers;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import edu.iastate.shoppinglist.Models.ShoppingListModel;
import edu.iastate.shoppinglist.R;
import io.realm.Realm;

public class ShoppingListActivity extends AppCompatActivity {

    private ShoppingListModel shoppingListModel;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        realm = Realm.getDefaultInstance();
        Long id = getIntent().getLongExtra("id",0);
        shoppingListModel = realm.where(ShoppingListModel.class).equalTo("id", id).findFirst();

        if(shoppingListModel == null){
            Toast.makeText(this, "Error finding shopping list", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Toast.makeText(this, shoppingListModel.getTitle(), Toast.LENGTH_SHORT).show();
        }


    }

}

package edu.iastate.shoppinglist.Models;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Main model class for the Shopping List. It extends RealmObject which helps with the data
 * serialization of the model class. More info:
 * @see <a href="https://github.com/realm/realm-java">Realm Documentation</a>
 * @author Shubham Sharma
 */
public class ShoppingListModel extends RealmObject {

    /**
     * Realm requires a unique ID for each RealmObject
     */
    @PrimaryKey
    private long id;

    /**
     * Title of the shopping list
     */
    private String title;

    /**
     * List of items in the shopping list
     */
    private RealmList<String> items;

    /**
     * Initiates a new object
     * @param title Title of the shopping list
     */
    public ShoppingListModel(String title) {
        id = System.currentTimeMillis();
        this.title = title;
    }

    /**
     * Required empty constructor for RealmObject class
     */
    public ShoppingListModel(){

    }

    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setItems(RealmList<String> items) {
        this.items = items;
    }

    public RealmList<String> getItems() {
        return items;
    }
}

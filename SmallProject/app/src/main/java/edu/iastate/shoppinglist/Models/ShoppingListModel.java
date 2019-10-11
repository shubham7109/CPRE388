package edu.iastate.shoppinglist.Models;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ShoppingListModel extends RealmObject implements Serializable {

    @PrimaryKey
    private long id;

    private String title;

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

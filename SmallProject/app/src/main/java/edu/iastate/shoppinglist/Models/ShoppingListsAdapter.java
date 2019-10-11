package edu.iastate.shoppinglist.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.iastate.shoppinglist.R;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Adapter to populate the shopping list items Recycler view.
 * @author Shubham Sharma
 */
public class ShoppingListsAdapter extends RecyclerView.Adapter<ShoppingListsAdapter.ViewHolder> {

    private ArrayList<ShoppingListModel> shoppingLists;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    /**
     * Constructor to inflate the RV
     * @param context Context of the activity
     * @param data List of ShoppingListModels to be added to the RV
     */
    public ShoppingListsAdapter(Context context, ArrayList<ShoppingListModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.shoppingLists = data;
    }

    /**
     * Creates the view holder
     * @param parent Instance of the parent ViewGroup.
     * @param viewType Instance of the viewType
     * @return Returns the ViewHolder of the adapter
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.shopping_lists_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds the view holder to the RV
     * @param holder Instance of the holder
     * @param position Position of the item being binded
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = shoppingLists.get(position).getTitle();
        holder.titleTv.setText(title);
    }

    /**
     * Gets the item count
     * @return Returns the size of the items passed through the constructor
     */
    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    /**
     * Custom class to instantiate the ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        Button openBtn;
        Button deleteBtn;
        Button duplicateBtn;
        Button renameBtn;
        TextView titleTv;

        /**
         * Constructor to create the itemView
         * @param itemView Returns the binded itemView
         */
        ViewHolder(View itemView) {
            super(itemView);
            openBtn = itemView.findViewById(R.id.view_button);
            deleteBtn = itemView.findViewById(R.id.delete_button);
            duplicateBtn = itemView.findViewById(R.id.duplicate_button);
            renameBtn = itemView.findViewById(R.id.rename_button);
            titleTv = itemView.findViewById(R.id.title);

            renameBtn.setOnClickListener(handleRename());
            openBtn.setOnClickListener(handleOpen());
            deleteBtn.setOnClickListener(handleDelete());
            duplicateBtn.setOnClickListener(handleDuplicate());
        }

        /**
         * Sets the click listener for open list
         * @return Returns the listener for the button
         */
        private View.OnClickListener handleOpen(){
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) mClickListener.onOpenListClick(getAdapterPosition());
                }
            };
        }

        /**
         * Sets the click listener for delete list
         * @return Returns the listener for the button
         */
        private View.OnClickListener handleDelete(){
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) mClickListener.onDeleteListClick(getAdapterPosition());
                }
            };
        }

        /**
         * Sets the click listener for duplicate list
         * @return Returns the listener for the button
         */
        private View.OnClickListener handleDuplicate(){
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) mClickListener.onDuplicateListClick(getAdapterPosition());
                }
            };
        }

        /**
         * Sets the click listener for rename list
         * @return Returns the listener for the button
         */
        private View.OnClickListener handleRename(){
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) mClickListener.onRenameListClick(getAdapterPosition());
                }
            };
        }
    }

    /**
     * Click listener for the callback method
     * @param itemClickListener Instance of the ClickListener
     */
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * Interface for callback to the main activity
     * so that open, delete and duplicate is handled there.
     */
    public interface ItemClickListener {
        void onOpenListClick(int position);
        void onDeleteListClick(int position);
        void onDuplicateListClick(int position);
        void onRenameListClick(int position);
    }
}
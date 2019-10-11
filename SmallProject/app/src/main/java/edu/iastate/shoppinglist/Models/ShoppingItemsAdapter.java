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

/**
 * Adapter to populate the shopping list items Recycler view.
 * @author Shubham Sharma
 */
public class ShoppingItemsAdapter extends RecyclerView.Adapter<ShoppingItemsAdapter.ViewHolder> {

    private ArrayList<String> items;
    private LayoutInflater mInflater;
    private ShoppingItemsAdapter.ItemClickListener mClickListener;

    /**
     * Constructor to inflate the RV
     * @param context Context of the activity.
     * @param data List of strings, which are items of the shopping list
     */
    public ShoppingItemsAdapter(Context context, ArrayList<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.items = data;
    }


    /**
     * Creates the view holder
     * @param parent Instance of the parent ViewGroup.
     * @param viewType Instance of the viewType
     * @return Returns the ViewHolder of the adapter
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.shopping_item_item, parent, false);
        return new ShoppingItemsAdapter.ViewHolder(view);
    }

    /**
     * Binds the view holder to the RV
     * @param holder Instance of the holder
     * @param position Position of the item being binded
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = items.get(position);
        holder.itemTv.setText(title);
    }

    /**
     * Gets the item count
     * @return Returns the size of the items passed through the constructor
     */
    @Override
    public int getItemCount() {
        return items.size();
    }


    /**
     * Custom class to instantiate the ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        Button removeBtn;
        TextView itemTv;

        /**
         * Constructor to create the itemView
         * @param itemView Returns the binded itemView
         */
        ViewHolder(View itemView) {
            super(itemView);
            removeBtn = itemView.findViewById(R.id.remove_item);
            itemTv = itemView.findViewById(R.id.item_tv);
            removeBtn.setOnClickListener(handleRemove());
        }

        /**
         * Sets the click listener for remove item
          * @return Returns the listener for the button
         */
        private View.OnClickListener handleRemove() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null)
                        mClickListener.onRemoveClick(getAdapterPosition());
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
     * so that remove item is handled there.
     */
    public interface ItemClickListener {
        void onRemoveClick(int position);
    }
}

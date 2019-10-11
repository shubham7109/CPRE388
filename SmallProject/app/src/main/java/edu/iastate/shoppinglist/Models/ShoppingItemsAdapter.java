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

public class ShoppingItemsAdapter extends RecyclerView.Adapter<ShoppingItemsAdapter.ViewHolder> {

    private ArrayList<String> items;
    private LayoutInflater mInflater;
    private ShoppingItemsAdapter.ItemClickListener mClickListener;

    public ShoppingItemsAdapter(Context context, ArrayList<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.items = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.shopping_item_item, parent, false);
        return new ShoppingItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = items.get(position);
        holder.itemTv.setText(title);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        Button removeBtn;
        TextView itemTv;

        ViewHolder(View itemView) {
            super(itemView);
            removeBtn = itemView.findViewById(R.id.remove_item);
            itemTv = itemView.findViewById(R.id.item_tv);

            removeBtn.setOnClickListener(handleRemove());
        }

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

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * Interface for callback to the main activity
     * so that open, delete and duplicate is handled there.
     */
    public interface ItemClickListener {
        void onRemoveClick(int position);
    }
}

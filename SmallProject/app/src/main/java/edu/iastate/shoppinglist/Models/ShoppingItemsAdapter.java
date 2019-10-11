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
        View view = mInflater.inflate(R.layout.shopping_lists_item, parent, false);
        return new ShoppingItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = items.get(position);
        holder.titleTv.setText(title);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        Button openBtn;
        Button deleteBtn;
        Button duplicateBtn;
        TextView titleTv;

        ViewHolder(View itemView) {
            super(itemView);
            openBtn = itemView.findViewById(R.id.view_button);
            deleteBtn = itemView.findViewById(R.id.delete_button);
            duplicateBtn = itemView.findViewById(R.id.duplicate_button);
            titleTv = itemView.findViewById(R.id.title);

            openBtn.setOnClickListener(handleOpen());
            deleteBtn.setOnClickListener(handleDelete());
            duplicateBtn.setOnClickListener(handleDuplicate());
        }

        private View.OnClickListener handleOpen() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null)
                        mClickListener.onOpenListClick(getAdapterPosition());
                }
            };
        }

        private View.OnClickListener handleDelete() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null)
                        mClickListener.onDeleteListClick(getAdapterPosition());
                }
            };
        }

        private View.OnClickListener handleDuplicate() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null)
                        mClickListener.onDuplicateListClick(getAdapterPosition());
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
        void onOpenListClick(int position);

        void onDeleteListClick(int position);

        void onDuplicateListClick(int position);
    }
}

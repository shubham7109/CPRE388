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

public class ShoppingListsAdapter extends RecyclerView.Adapter<ShoppingListsAdapter.ViewHolder> {

    private ArrayList<ShoppingListModel> shoppingLists;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public ShoppingListsAdapter(Context context, ArrayList<ShoppingListModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.shoppingLists = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.shopping_lists_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = shoppingLists.get(position).getTitle();
        holder.titleTv.setText(title);
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button openBtn;
        TextView titleTv;

        ViewHolder(View itemView) {
            super(itemView);
            openBtn = itemView.findViewById(R.id.view_button);
            titleTv = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    ShoppingListModel getItem(int id) {
        return shoppingLists.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
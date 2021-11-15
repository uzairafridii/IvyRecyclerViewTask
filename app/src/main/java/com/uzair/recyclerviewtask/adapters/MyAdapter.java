package com.uzair.recyclerviewtask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uzair.recyclerviewtask.R;
import com.uzair.recyclerviewtask.model.Items;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<ChildViewHolder> {
    List<Items> itemsList;
    Context context;
    private String lastProductId;

    public MyAdapter(List<Items> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }


    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout_design, null, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position) {

        childViewHolder.availableStock.setText("Stock Available : " + itemsList.get(position).getStock());
        childViewHolder.itemName.setText(itemsList.get(position).getName());
        childViewHolder.itemSqCode.setText("SKU Code : " + itemsList.get(position).getSku_code());

        Glide.with(context)
                .load(itemsList.get(position).getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(childViewHolder.itemImage);
    }



    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public String getLastProductId() {
        return lastProductId;
    }

    public void setLastProductId(String lastProductId) {
        this.lastProductId = lastProductId;
    }
}

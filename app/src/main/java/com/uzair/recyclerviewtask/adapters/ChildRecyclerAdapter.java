package com.uzair.recyclerviewtask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uzair.recyclerviewtask.R;
import com.uzair.recyclerviewtask.model.Items;

import java.util.List;

public class ChildRecyclerAdapter extends RecyclerView.Adapter<ChildRecyclerAdapter.MYItemViewHolder> {
    private List<Items> itemsList;
    private Context context;

    public ChildRecyclerAdapter(List<Items> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MYItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MYItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout_design, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MYItemViewHolder holder, int position) {
        Items items = itemsList.get(position);

       // holder.totalPcs.setText();
        holder.availableStock.setText("Stock Available : "+items.getStock());
        holder.itemName.setText(items.getName());
        holder.itemSqCode.setText("SKU Code : "+items.getSku_code());

        Glide.with(context)
                .load(items.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.itemImage);

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class MYItemViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName , itemSqCode ,availableStock , totalPcs;
        private ImageView itemImage;
        private EditText edCtn, edQty;

        public MYItemViewHolder(@NonNull View itemView) {
            super(itemView);
            totalPcs = itemView.findViewById(R.id.totalPcs);
            availableStock = itemView.findViewById(R.id.availableStock);
            itemName = itemView.findViewById(R.id.itemName);
            itemSqCode = itemView.findViewById(R.id.itemSqCode);
            edCtn = itemView.findViewById(R.id.edCtn);
            edQty = itemView.findViewById(R.id.edQty);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}

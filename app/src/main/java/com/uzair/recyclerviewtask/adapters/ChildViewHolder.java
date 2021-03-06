package com.uzair.recyclerviewtask.adapters;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uzair.recyclerviewtask.R;

public class ChildViewHolder extends RecyclerView.ViewHolder
{
    public TextView itemName, itemSqCode, availableStock, totalPcs;
    public ImageView itemImage;
    public EditText edCtn, edPcs, edBox;

    public ChildViewHolder(View itemView) {
        super(itemView);

        totalPcs = itemView.findViewById(R.id.totalPcs);
        availableStock = itemView.findViewById(R.id.availableStock);
        itemName = itemView.findViewById(R.id.itemName);
        itemSqCode = itemView.findViewById(R.id.itemSqCode);
        edCtn = itemView.findViewById(R.id.edCtn);
        edPcs = itemView.findViewById(R.id.edPcs);
        edBox = itemView.findViewById(R.id.edBox);
        itemImage = itemView.findViewById(R.id.itemImage);
    }
}

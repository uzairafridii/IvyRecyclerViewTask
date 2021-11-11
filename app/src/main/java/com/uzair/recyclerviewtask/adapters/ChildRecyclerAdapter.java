package com.uzair.recyclerviewtask.adapters;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uzair.recyclerviewtask.R;
import com.uzair.recyclerviewtask.model.Items;
import com.uzair.recyclerviewtask.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ChildRecyclerAdapter extends RecyclerView.Adapter<ChildRecyclerAdapter.MYItemViewHolder>
{
    private List<Items> itemsList;
    private Context context;
    private int ctnSize, total, pieces, totalOfCtn;
    private String pcs;

    public ChildRecyclerAdapter(List<Items> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MYItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MYItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout_design, null));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MYItemViewHolder holder, int position) {
        Items items = itemsList.get(position);

        ctnSize = Integer.parseInt(items.getCtn_size());


        // holder.totalPcs.setText();
        holder.availableStock.setText("Stock Available : " + items.getStock());
        holder.itemName.setText(items.getName());
        holder.itemSqCode.setText("SKU Code : " + items.getSku_code());

        Glide.with(context)
                .load(items.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.itemImage);


        if (items.getCtn_size().equals("0")) {
            holder.edCtn.setEnabled(false);
            holder.edCtn.setBackgroundColor(R.color.purple_700);
        } else if (items.getBox_size().equals("0")) {
            holder.edBox.setEnabled(false);
            holder.edBox.setBackgroundColor(R.color.purple_700);
        }


        /// text change listener on cotton edit text
        holder.edCtn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int ctn = Integer.parseInt(String.valueOf(s.toString()));
                    pcs = holder.edPcs.getText().toString();
                    pieces = Integer.parseInt(pcs);
                    totalOfCtn = (ctn * ctnSize);
                    total = totalOfCtn + pieces;
                    holder.totalPcs.setText("T.Pcs : " + total);
                } catch (Exception e) {

                    holder.totalPcs.setText("T.Pcs : " + pieces);
                    Log.d("childRecyclerError", "afterTextChanged: " + e.getMessage().toString());
                }

            }
        });


        /// text change listener on pcs edit text
        holder.edPcs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int pcs = Integer.parseInt(String.valueOf(s.toString()));
                    total = totalOfCtn + pcs;
                    holder.totalPcs.setText("T.Pcs : " + total);
                } catch (Exception e) {

                    holder.totalPcs.setText("T.Pcs : " + totalOfCtn);
                    Log.d("childRecyclerError", "afterTextChanged: " + e.getMessage().toString());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }




    public class MYItemViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName, itemSqCode, availableStock, totalPcs;
        private ImageView itemImage;
        private EditText edCtn, edPcs, edBox;

        public MYItemViewHolder(@NonNull View itemView) {
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
}

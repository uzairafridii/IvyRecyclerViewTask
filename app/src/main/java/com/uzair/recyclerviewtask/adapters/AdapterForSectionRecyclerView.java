package com.uzair.recyclerviewtask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;
import com.uzair.recyclerviewtask.R;
import com.uzair.recyclerviewtask.model.Items;
import com.uzair.recyclerviewtask.model.Product;
import com.uzair.recyclerviewtask.model.SectionHeader;

import java.util.List;

public class AdapterForSectionRecyclerView extends SectionRecyclerViewAdapter<SectionHeader, Items, SectionViewHolder, ChildViewHolder> {

    private Context context;
    private String lastProductId;

    public AdapterForSectionRecyclerView(Context context, List<SectionHeader> sectionItemList) {
        super(context, sectionItemList);
        this.context = context;
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        return new SectionViewHolder(LayoutInflater.from(context).inflate(R.layout.header_layout, null));
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        return new ChildViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout_design, null));
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int i, SectionHeader sectionHeader) {

        sectionViewHolder.headerTitle.setText(""+sectionHeader.getSectionText());

    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int i, int i1, Items items) {

        childViewHolder.availableStock.setText("Stock Available : " + items.getStock());
        childViewHolder.itemName.setText(items.getName());
        childViewHolder.itemSqCode.setText("SKU Code : " + items.getSku_code());

        Glide.with(context)
                .load(items.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(childViewHolder.itemImage);

    }


    public String getLastProductId() {
        return lastProductId;
    }

    public void setLastProductId(String lastProductId) {
        this.lastProductId = lastProductId;
    }
}

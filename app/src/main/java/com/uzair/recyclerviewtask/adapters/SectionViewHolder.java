package com.uzair.recyclerviewtask.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uzair.recyclerviewtask.R;

public class SectionViewHolder extends RecyclerView.ViewHolder {

    public TextView headerTitle;

    public SectionViewHolder(@NonNull View itemView) {
        super(itemView);
        headerTitle = itemView.findViewById(R.id.headerText);

    }
}

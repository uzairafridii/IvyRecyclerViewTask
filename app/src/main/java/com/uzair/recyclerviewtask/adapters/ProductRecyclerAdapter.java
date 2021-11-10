package com.uzair.recyclerviewtask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uzair.recyclerviewtask.R;
import com.uzair.recyclerviewtask.model.Items;
import com.uzair.recyclerviewtask.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.MyProductViewHolder> {
    private List<Product> productList;
    private List<Items> itemList;
    private Context context;
    private DatabaseReference dbRef;

    public ProductRecyclerAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        itemList = new ArrayList<>();
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public MyProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_rv_item_layout, null);
        return new MyProductViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvProduct.setText(product.getName());

        Query query = dbRef.child("Items")
                .orderByChild("productid")
                .equalTo(product.getUid());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                itemList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Items items = snap.getValue(Items.class);
                    itemList.add(items);
                }

                ChildRecyclerAdapter adapter = new ChildRecyclerAdapter(itemList, context);
                holder.setRecyclerViewAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyProductViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProduct;
        private RecyclerView rvItems;

        public MyProductViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProduct = itemView.findViewById(R.id.sectionNameTextView);
            rvItems = itemView.findViewById(R.id.childRecyclerView);
        }

        private void setRecyclerViewAdapter(ChildRecyclerAdapter adapter) {
            rvItems.setLayoutManager(new LinearLayoutManager(context));
            rvItems.setAdapter(adapter);
        }
    }
}

package com.uzair.recyclerviewtask.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.uzair.recyclerviewtask.MainActivity;
import com.uzair.recyclerviewtask.R;
import com.uzair.recyclerviewtask.model.Items;
import com.uzair.recyclerviewtask.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.MyProductViewHolder>
implements Filterable
{
    private List<Product> productList;
    private List<Items> itemList;
    private Context context;
    private DatabaseReference dbRef;
    private ChildRecyclerAdapter adapter;

    public ProductRecyclerAdapter(Context context) {
       // this.productList = ;productList
        this.context = context;
        itemList = new ArrayList<>();
        this.productList = new ArrayList<>();
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


                adapter = new ChildRecyclerAdapter(itemList, context);
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


    /// add all products
    public void addAll(List<Product> newProduct) {
        int initialSize = productList.size();
        productList.addAll(newProduct);
        notifyItemRangeInserted(initialSize, newProduct.size());

    }

    /// get last product id
    public String getLastItemId() {
        return productList.get(productList.size() - 1).getUid();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }


    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Items> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(itemList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Items item : itemList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList.clear();
            itemList.addAll((List) results.values);
            notifyDataSetChanged();

            for (int i=0; i<itemList.size(); i++)
                Log.d("searchItemList", "publishResults: "+itemList.get(0).getName());
        }
    };


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

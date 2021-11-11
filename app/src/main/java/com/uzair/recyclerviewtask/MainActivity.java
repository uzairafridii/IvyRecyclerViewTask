package com.uzair.recyclerviewtask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uzair.recyclerviewtask.adapters.ProductRecyclerAdapter;
import com.uzair.recyclerviewtask.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvProducts;
    private LinearLayoutManager layoutManager;
    private ProductRecyclerAdapter productAdapter;
    private int mTotalItemCount = 0;
    private int mLastVisibleItemPosition;
    private boolean mIsLoading = false;
    private int mPostsPerPage = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        getProducts(null);


        rvProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mTotalItemCount = layoutManager.getItemCount();
                mLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                if (!mIsLoading && mTotalItemCount <= (mLastVisibleItemPosition + mPostsPerPage)) {
                    getProducts(productAdapter.getLastItemId());
                    mIsLoading = true;

                }
            }
        });

    }


    private void initViews() {
        rvProducts = findViewById(R.id.rvProducts);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvProducts.setLayoutManager(layoutManager);

        // productList = new ArrayList<>();
        productAdapter = new ProductRecyclerAdapter(this);
        rvProducts.setAdapter(productAdapter);
    }

    private void getProducts(String nodeId) {
        Query query;
        List<Product> productList = new ArrayList<>();

        if (nodeId == null) {
            query = FirebaseDatabase.getInstance().getReference()
                    .child("Products")
                    .orderByKey()
                    .limitToFirst(mPostsPerPage);

        } else {
            query = FirebaseDatabase.getInstance().getReference()
                    .child("Products")
                    .orderByKey()
                    .startAfter(nodeId)
                    .limitToFirst(mPostsPerPage);
        }


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Product product = snap.getValue(Product.class);
                    productList.add(product);
                    Log.d("productAdd", "onDataChange: " + product.getName());

                }
                productAdapter.addAll(productList);
                mIsLoading = false;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mIsLoading = false;
            }
        });


    }


}
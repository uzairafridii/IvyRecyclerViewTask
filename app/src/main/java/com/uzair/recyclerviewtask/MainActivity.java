package com.uzair.recyclerviewtask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uzair.recyclerviewtask.adapters.ProductRecyclerAdapter;
import com.uzair.recyclerviewtask.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvProducts;
    private DatabaseReference dbRef;
    private ProductRecyclerAdapter productAdapter;
    private List<Product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        getProducts();


    }


    private void initViews() {
        rvProducts = findViewById(R.id.rvProducts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvProducts.setLayoutManager(layoutManager);
        productList = new ArrayList<>();
        productAdapter = new ProductRecyclerAdapter(productList, this);
        rvProducts.setAdapter(productAdapter);
        // firebase
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    private void getProducts() {
        dbRef.child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Product product = snap.getValue(Product.class);
                            productList.add(product);
                            rvProducts.setAdapter(productAdapter);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

}
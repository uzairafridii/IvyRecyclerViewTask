package com.uzair.recyclerviewtask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uzair.recyclerviewtask.Utils.RecyclerSectionItemDecoration;
import com.uzair.recyclerviewtask.adapters.MyAdapter;
import com.uzair.recyclerviewtask.model.Items;
import com.uzair.recyclerviewtask.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView rvProducts;
    private LinearLayoutManager layoutManager;
    //  private ProductRecyclerAdapter productAdapter;
    public static int mTotalItemCount = 0;
    public static int mLastVisibleItemPosition;
    public static boolean mIsLoading = false;
    public static int mPostsPerPage = 4;

    //// section adpaer with intruder shanky
    //  AdapterForSectionRecyclerView adapterForSectionRecyclerView;
    List<Items> mItemsList;

    //// sticky header adpater
    MyAdapter adapter;
    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        getProducts(null);


        /// check for scrolling of recycler view then load the new items
        rvProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mTotalItemCount = layoutManager.getItemCount();
                mLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                if (!mIsLoading && mTotalItemCount <= (mLastVisibleItemPosition + mPostsPerPage)) {
                    getProducts(adapter.getLastProductId());
                    mIsLoading = true;
                }
            }
        });

    }


    private void initViews() {

        productList = new ArrayList<>();
        rvProducts = findViewById(R.id.rvProducts);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvProducts.setLayoutManager(layoutManager);

        /// adapter for intruder shanky
        mItemsList = new ArrayList<>();
        adapter = new MyAdapter(mItemsList, this);
        //  adapterForSectionRecyclerView = new AdapterForSectionRecyclerView(MainActivity.this, sectionHeaderList);
        rvProducts.setAdapter(adapter);

        RecyclerSectionItemDecoration sectionItemDecoration =
                new RecyclerSectionItemDecoration(getResources().getDimensionPixelSize(R.dimen.header),
                        true,
                        getSectionCallback(mItemsList));
        rvProducts.addItemDecoration(sectionItemDecoration);

        /// our adapter
//        productList = new ArrayList<>();
//        productAdapter = new ProductRecyclerAdapter(this);
//        rvProducts.setAdapter(productAdapter);


//        SearchView searchView = findViewById(R.id.searchView);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
////                productAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
    }

    // get the product using node id
    private void getProducts(String nodeId) {
        Query query;


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


        /// get the data using query
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /// add the products to list
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Product product = snap.getValue(Product.class);
                    adapter.setLastProductId(product.getUid());

                    productList.add(product);

                    Query query = FirebaseDatabase.getInstance().getReference().child("Items")
                            .orderByChild("productid")
                            .equalTo(product.getUid());

                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            List<Items> itemsList = new ArrayList<>();
                            Log.d("productAdd", "onDataChange: " + product.getName());

                            //itemsList.clear();
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                Items items = snap.getValue(Items.class);
                                items.setProductName(product.getName());
                                itemsList.add(items);
                            }

                            mItemsList.addAll(itemsList);
                            adapter.notifyDataSetChanged();
                            mIsLoading = false;

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mIsLoading = false;
            }
        });


    }


    private RecyclerSectionItemDecoration.SectionCallback getSectionCallback(final List<Items> item) {
        return new RecyclerSectionItemDecoration.SectionCallback() {
            @Override
            public boolean isSection(int position) {
                return position == 0
                        || !item.get(position).getProductid()
                        .equals(item.get(position - 1).getProductid());
            }

            @Override
            public String getSectionHeader(int position) {
                return item.get(position).getProductName();

            }
        };
    }

}
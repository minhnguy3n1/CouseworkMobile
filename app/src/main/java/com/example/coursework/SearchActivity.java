package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;
    SearchView searchView;

    MyDatabaseHelper myDB;
    MyCustomAdapter myCustomAdapter;
    ArrayList<TripInfo> tripInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
//        initView();
        loadData();


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.list_trips:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        return true;
                    case R.id.upload:
                        startActivity(new Intent(getApplicationContext(), UploadActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }

                return false;
            }
        });



    }

    private void initView(){
        registerForContextMenu(recyclerView);
    }

    private void loadData(){

        tripInfos = new ArrayList<TripInfo>();
        myDB = new MyDatabaseHelper(SearchActivity.this);
        tripInfos = myDB.findAll();
        myCustomAdapter = new MyCustomAdapter(tripInfos, getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        recyclerView.setAdapter(myCustomAdapter);

    }

//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String keyword) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keyword) {
                filter(keyword);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private void filter (String text){
        ArrayList<TripInfo> filteredList = new ArrayList<TripInfo>();
        for(TripInfo item : tripInfos){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if(filteredList.isEmpty()){
            Toast.makeText(this,"No Data", Toast.LENGTH_SHORT).show();
        } else {
            myCustomAdapter.filterList(filteredList);
        }
    }




//    private void searchTrip(String keyword){
//        myDB = new MyDatabaseHelper(getApplicationContext());
//        ArrayList<TripInfo> tripInfos = myDB.search(keyword);
//        if(tripInfos != null){
//            recyclerView.setAdapter(new MyCustomAdapter(tripInfos, SearchActivity.this));
//            recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
//        }
//    }

}
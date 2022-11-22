package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExpenseDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;
    ExpenseAdapter expenseAdapter;


    MyDatabaseHelper myDB;
    ArrayList<String> expense_id, expense_type, expense_amount, expense_date;
    String trip_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        recyclerView = findViewById(R.id.recyclerView_expense);
        add_button = findViewById(R.id.add_expense_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseDetailActivity.this, AddExpenseActivity.class);
                intent.putExtra("trip_id", trip_id);
                startActivity(intent);
            }
        });


        myDB = new MyDatabaseHelper(ExpenseDetailActivity.this);
        expense_id = new ArrayList<>();
        expense_type = new ArrayList<>();
        expense_amount = new ArrayList<>();
        expense_date = new ArrayList<>();
        trip_id = getIntent().getStringExtra("trip_id");

        storeDataInArrays();

        expenseAdapter = new ExpenseAdapter(ExpenseDetailActivity.this,this, expense_id, expense_type, expense_amount
                , expense_date);
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExpenseDetailActivity.this));
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllExpense(trip_id);
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);

        } else {
            while(cursor.moveToNext()){
                expense_id.add(cursor.getString(0));
                expense_type.add(cursor.getString(1));
                expense_amount.add(cursor.getString(2));
                expense_date.add(cursor.getString(3));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }
}
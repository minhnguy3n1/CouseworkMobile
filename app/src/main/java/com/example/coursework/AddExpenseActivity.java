package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;

public class AddExpenseActivity extends AppCompatActivity {

    Spinner spinner;
    EditText expense_amount, expense_date;
    Button add_button;


    private String[] typeExpesesArray = {
            "Food",
            "Travel",
            "Other"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        spinner = findViewById(R.id.expense_type_spinner);
        expense_amount = findViewById(R.id.expense_amount_input);
        expense_date = findViewById(R.id.expense_date_input);
        add_button = findViewById(R.id.expense_add_button);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeExpesesArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((dataAdapter));

        expense_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFragment = new ExpenseDatePickerFragment();
                newFragment.show(getSupportFragmentManager(),"datePicker");
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddExpenseActivity.this);
                String value_type = spinner.getSelectedItem().toString(),
                        value_amount = expense_amount.getText().toString(),
                        value_date = expense_date.getText().toString(),
                        trip_id = getIntent().getStringExtra("trip_id");

                if(value_type.isEmpty()){
                    Toast.makeText(AddExpenseActivity.this,"You need to fill all required fields", Toast.LENGTH_SHORT).show();
                } else if(value_amount.isEmpty()){
                    Toast.makeText(AddExpenseActivity.this,"You need to fill all required fields", Toast.LENGTH_SHORT).show();
                } else if(value_date.isEmpty()){
                    Toast.makeText(AddExpenseActivity.this,"You need to fill all required fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    myDB.addExpense(value_type, value_amount, value_date, trip_id);

                    Intent intent = new Intent(AddExpenseActivity.this, ExpenseDetailActivity.class);
                    intent.putExtra("trip_id", trip_id);
                    startActivity(intent);
                }
            }
        });
    }
    public void updateDate(LocalDate date){
        expense_date.setText(date.toString());
    }




}


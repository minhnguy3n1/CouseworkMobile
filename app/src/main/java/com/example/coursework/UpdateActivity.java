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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.time.LocalDate;

public class UpdateActivity extends AppCompatActivity {
    RadioButton radioButton, yes_button, no_button;
    RadioGroup assessment_input;
    EditText name_input, destination_input, date_input,desc_input;
    Button update_button, see_expense_button;

    String id, name, destination, date, assessment, desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        name_input = findViewById(R.id.name_update_input);
        destination_input = findViewById(R.id.destination_update_input);
        date_input = findViewById(R.id.date_update_input);
        assessment_input = findViewById(R.id.radioGroup_update_assessment);
        desc_input = findViewById(R.id.description_update_input);
        see_expense_button = findViewById(R.id.see_expense_button);
        yes_button = findViewById(R.id.radioButton_yes);
        no_button = findViewById(R.id.radioButton_no);


        getAndSetIntentData();



        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate localDate = LocalDate.now();
                int year = localDate.getYear();
                int month = localDate.getMonthValue();
                int day = localDate.getDayOfMonth();

                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date_input.setText(day + "-" + (month++) + "-" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });



        assessment_input.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {

                radioButton = (RadioButton) findViewById(checkedId);

            }
        });

        update_button = findViewById(R.id.update_button);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedId = assessment_input.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(checkedId);

                if(assessment_input.getCheckedRadioButtonId() == -1){
                    Toast.makeText(UpdateActivity.this,"You need to fill Require Risks Assessments",Toast.LENGTH_SHORT).show();
                } else {
                    //And only then we call this
                    MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                    name = name_input.getText().toString().trim();
                    destination = destination_input.getText().toString().trim();
                    date = date_input.getText().toString().trim();
                    assessment = radioButton.getText().toString().trim();
                    desc = desc_input.getText().toString().trim();

                    myDB.updateData(id, name, destination, date, assessment, desc);
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);

                }

            }
        });

        see_expense_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, ExpenseDetailActivity.class);
                intent.putExtra("trip_id",id);
                startActivity(intent);

            }
        });


    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("destination") && getIntent().hasExtra("date")
                && getIntent().hasExtra("assessment")&& getIntent().hasExtra("desc")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            destination = getIntent().getStringExtra("destination");
            date = getIntent().getStringExtra("date");
            assessment = getIntent().getStringExtra("assessment");
            desc = getIntent().getStringExtra("desc");

            //Setting Intent Data
            name_input.setText(name);
            destination_input.setText(destination);
            date_input.setText(date);
            desc_input.setText(desc);
            if(assessment.equals("Yes")){
                yes_button.setChecked(true);
                no_button.setChecked(false);
            } else {
                yes_button.setChecked(false);
                no_button.setChecked(true);

            }


            Log.d("stev", name+" "+destination+" "+date+" "+desc);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDate(LocalDate date){
        date_input.setText(date.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                myDB.deleteOneRow(id);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}


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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.time.LocalDate;

public class AddActivity extends AppCompatActivity {
    RadioButton radioButton;
    EditText name_input, destination_input, date_input, desc_input;
    RadioGroup assessment_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name_input = findViewById(R.id.name_input);
        destination_input = findViewById(R.id.destination_input);
        date_input = findViewById(R.id.date_input);
        desc_input = findViewById(R.id.description_input);
        assessment_input = findViewById(R.id.radioGroup_assessment);

        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate localDate = LocalDate.now();
                int year = localDate.getYear();
                int month = localDate.getMonthValue();
                int day = localDate.getDayOfMonth();

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this,
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

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                String value_name = name_input.getText().toString().trim(),
                        value_destination=destination_input.getText().toString().trim(),
                        value_date = date_input.getText().toString().trim(),
                        value_desc = desc_input.getText().toString().trim();
                if(value_name.isEmpty()){
                    isNullAlert();
                } else if(value_destination.isEmpty()){
                    isNullAlert();
                } else if(value_date.isEmpty()){
                    isNullAlert();
                } else if(assessment_input.getCheckedRadioButtonId() == -1){
                    isNullAlert();
                }
                else {
                    myDB.addTrip(value_name,
                            value_destination,
                            value_date,
                            radioButton.getText().toString().trim(),
                            value_desc);
                    displayNextAlert(value_name,
                            value_destination,
                            value_date,
                            radioButton.getText().toString().trim(),
                            value_desc);

                }

            }
        });

    }


    public void updateDate(LocalDate date){
        date_input.setText(date.toString());
    }

    private void displayNextAlert(String strName, String strDestination, String strDate,
                                  String strAssesment, String strDesc){

            new AlertDialog.Builder(this).setTitle("Details entered").setMessage("Details entered: " +
                    "Name of the Trip: " + strName + "\n" +
                    "Destination: " + strDestination +"\n" +
                    "Date of the Trip: " + strDate +"\n" +
                    "Require Risk Assessment: " + strAssesment +"\n" +
                    "Description: "+strDesc
            ).setNeutralButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).show();



    }

    private void isNullAlert(){
        new AlertDialog.Builder(this).setMessage(
                "You need to fill all required fields"
        ).setNeutralButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }


}


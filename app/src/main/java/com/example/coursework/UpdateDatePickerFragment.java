package com.example.coursework;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

public class UpdateDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    static UpdateDatePickerFragment newInstance(int activity) {
        UpdateDatePickerFragment datePickerFragment = new UpdateDatePickerFragment();
        Bundle args = new Bundle();
        args.putInt("activity", activity);
        datePickerFragment.setArguments(args);
        return datePickerFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();


        return new DatePickerDialog(getActivity(), this, year, --month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        LocalDate date = LocalDate.of(year, ++month, day);
        ((UpdateActivity) getActivity()).updateDate(date);

    }
}
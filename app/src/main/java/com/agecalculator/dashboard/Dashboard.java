package com.agecalculator.dashboard;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.agecalculator.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class Dashboard extends AppCompatActivity {

    private MaterialTextView mAgeStringTv;
    private DashboardViewModel mDashboardViewModel;
    private Observer<String> mAgeStringObserver;
    private MaterialButton mPickDate;
    private TextInputEditText mDateOfBirth;
    private TextInputLayout mDateOfBirthTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mAgeStringTv = findViewById(R.id.date);
        mDateOfBirthTextInputLayout = findViewById(R.id.dateOfBirthTextInputLayout);
        mDateOfBirth = findViewById(R.id.dateOfBirth);
        mPickDate = findViewById(R.id.pickDate);
        mPickDate.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    (view, year, monthOfYear, dayOfMonth) -> {
                        Calendar startDate=Calendar.getInstance();

                        startDate.set(year, monthOfYear, dayOfMonth);
                        Calendar endDate=Calendar.getInstance();
                        mDateOfBirth.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        mDashboardViewModel.calculateAge(startDate,endDate);
                    },
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Initial day selection
            );
            dpd.setMaxDate(Calendar.getInstance());
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        });
        mDateOfBirthTextInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateOfBirth.setText("");
                mAgeStringTv.setVisibility(View.INVISIBLE);
                mDashboardViewModel.clearAgeInString();
            }
        });
        mDashboardViewModel = new DashboardViewModel();
        mAgeStringObserver = s -> {
            mAgeStringTv.setVisibility(View.VISIBLE);
            mAgeStringTv.setText(s);
        };
        mDashboardViewModel.getAgeInString().observe(this, mAgeStringObserver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDashboardViewModel.getAgeInString().removeObserver(mAgeStringObserver);
    }
}
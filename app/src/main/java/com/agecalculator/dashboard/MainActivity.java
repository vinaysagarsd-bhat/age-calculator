package com.agecalculator.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.agecalculator.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView ageString;
    private DashboardViewModel dashboardViewModel;
    private Observer<String> ageStringObserver;
    private MaterialButton pickDate;
    private TextInputEditText dateOfBirth;
    private TextInputLayout dateOfBirthTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        ageString = findViewById(R.id.date);
        dateOfBirthTextInputLayout = findViewById(R.id.dateOfBirthTextInputLayout);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        pickDate = findViewById(R.id.pickDate);
        pickDate.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    (view, year, monthOfYear, dayOfMonth) -> {
                        Calendar startDate=Calendar.getInstance();

                        startDate.set(year, monthOfYear, dayOfMonth);
                        Calendar endDate=Calendar.getInstance();
                        dateOfBirth.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        dashboardViewModel.calculateAge(startDate,endDate);
                    },
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Initial day selection
            );
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        });
        dateOfBirthTextInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateOfBirth.setText("");
                ageString.setVisibility(View.INVISIBLE);
                ageString.setText("");
            }
        });
        dashboardViewModel = new DashboardViewModel();
        ageStringObserver = s -> {
            ageString.setVisibility(View.VISIBLE);
            ageString.setText(s);
        };
        dashboardViewModel.getAgeInString().observe(this, ageStringObserver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dashboardViewModel.getAgeInString().removeObserver(ageStringObserver);
    }
}
package com.vinay.agecalculator.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vinay.agecalculator.lib.AgeCalculator;

import java.util.Calendar;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> ageInString = new MutableLiveData<>();

    public MutableLiveData<String> getAgeInString(){
        return ageInString;
    }

    public void calculateAge(Calendar startDate, Calendar endDate) {
        AgeCalculator ageCalculator = AgeCalculator.calculateAge(startDate,endDate);
        ageInString.setValue("Age: " + ageCalculator.getYear() + " Years " + ageCalculator.getMonth() + " Months " + ageCalculator.getDay()+ " Days");
    }

    public void clearAgeInString(){
        ageInString.setValue("");
    }
}

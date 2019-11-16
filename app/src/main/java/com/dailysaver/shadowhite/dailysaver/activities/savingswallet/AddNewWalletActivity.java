package com.dailysaver.shadowhite.dailysaver.activities.savingswallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.onboard.HomeActivity;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;

import java.util.Calendar;
import java.util.Locale;

public class AddNewWalletActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private RelativeLayout mainLayout;
    private Spinner currencySpinner;
    private ArrayAdapter<String> spinnerAdapter;
    private EditText expiryDate;
    private CheckBox expense,income;
    private SimpleDateFormat dateFormatter;
    private UX ux;
    private Tools tools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_savings_wallet);

        init();

        ux.setToolbar(toolbar,this,HomeActivity.class);
        tools.setAnimation(mainLayout);
        bindUiWithComponents();
    }

    private void bindUiWithComponents() {
        setSpinnerAdapter();
        expiryDate.setOnClickListener(this);

        expense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                setVisibility(income,isChecked);
            }
        });
        income.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                setVisibility(expense,isChecked);
            }
        });
    }

    private void setVisibility(CheckBox checkBox, boolean isChecked) {
        if (isChecked) checkBox.setEnabled(false);
        else checkBox.setEnabled(true);
    }

    private void init() {
        ux = new UX(this);
        tools = new Tools(this);
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.home_layout);
        currencySpinner = findViewById(R.id.Currency);
        expiryDate = findViewById(R.id.ExpiresOn);
        expense = findViewById(R.id.Expense);
        income = findViewById(R.id.Savings);
    }

    private void setSpinnerAdapter() {
        spinnerAdapter = new ArrayAdapter<String>(this,R.layout.spinner_drop,new String[]{"BDT Tk.","US Dollar"});
        currencySpinner.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.notifyDataSetChanged();
    }

    private void getAndSetDate(final int resId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        }
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                EditText dateView = findViewById(resId);
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    dateView.setText("Expiry Date : "+dateFormatter.format(newDate.getTime()));
                }
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ExpiresOn:
                getAndSetDate(R.id.ExpiresOn);
                break;
        }
    }

    private void customDatePicker(){
        // Set a Start date (Default, 1 Jan 1970)
//        datePickerTimeline.setInitialDate(2019, 3, 21);
//        // Set a date Selected Listener
//        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
//                // Do Something
//            }
//
//            @Override
//            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
//                // Do Something
//            }
//        });
    }
}

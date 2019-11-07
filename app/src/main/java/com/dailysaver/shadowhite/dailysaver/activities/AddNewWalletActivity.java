package com.dailysaver.shadowhite.dailysaver.activities;

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
import java.util.Calendar;
import java.util.Locale;

public class AddNewWalletActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private RelativeLayout mainLayout;
    private Spinner currencySpinner;
    private ArrayAdapter<String> spinnerAdapter;
    private EditText expiryDate;
    private CheckBox expense,income;
    //private DatePickerTimeline datePickerTimeline;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_wallet);

        init();
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddNewWalletActivity.this,HomeActivity.class));
            }
        });
        Animation a = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mainLayout.startAnimation(a);
        bindUiWithComponents();
    }

    private void bindUiWithComponents() {
        setSpinnerAdapter();
        expiryDate.setOnClickListener(this);
        //datePickerTimeline.setOnClickListener(this);

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
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.home_layout);
        currencySpinner = findViewById(R.id.CurrencySpinner);
        expiryDate = findViewById(R.id.ExpiryDate);
        expense = findViewById(R.id.expense);
        income = findViewById(R.id.income);
        //datePickerTimeline = findViewById(R.id.ExpiryDate);
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
            case R.id.ExpiryDate:
                getAndSetDate(R.id.ExpiryDate);
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

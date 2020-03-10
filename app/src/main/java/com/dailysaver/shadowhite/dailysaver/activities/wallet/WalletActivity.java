package com.dailysaver.shadowhite.dailysaver.activities.wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.onboard.HomeActivity;
import com.dailysaver.shadowhite.dailysaver.models.wallet.Wallet;
import com.dailysaver.shadowhite.dailysaver.utills.SpinnerData;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Calendar;
import java.util.Locale;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private RelativeLayout mainLayout;
    private int currencyValue;
    private String budgetTypeStr;
    private EditText Amount,WalletName,Note,ExpiresOn;
    private FloatingActionButton add;
    private Spinner currencySpinner;
    private EditText expiryDate;
    private CheckBox expense,savings;
    private SimpleDateFormat dateFormatter;
    private UX ux;
    private Tools tools;
    private SpinnerData spinnerData;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_wallet);

        init();

        ux.setToolbar(toolbar,this,HomeActivity.class);
        tools.setAnimation(mainLayout);
        bindUiWithComponents();
    }

    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.home_layout);
        Amount = findViewById(R.id.Amount);
        WalletName = findViewById(R.id.WalletName);
        Note = findViewById(R.id.Note);
        ExpiresOn = findViewById(R.id.ExpiresOn);
        currencySpinner = findViewById(R.id.Currency);
        expiryDate = findViewById(R.id.ExpiresOn);
        expense = findViewById(R.id.Expense);
        savings = findViewById(R.id.Savings);
        add = findViewById(R.id.add);
        ux = new UX(this);
        tools = new Tools(this);
        databaseHelper = new DatabaseHelper(this);
        spinnerData = new SpinnerData(this);
    }

    private void bindUiWithComponents() {
        ux.setSpinnerAdapter(spinnerData.currencyData(),currencySpinner);

        expiryDate.setOnClickListener(this);

        ux.onSpinnerChange(currencySpinner, new UX.onSpinnerChangeListener() {
            @Override
            public void onChange(AdapterView<?> parent, View view, int position, long id) {
                currencyValue = position;
            }
        });

        ux.onChange(expense, new UX.onChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                if (isChecked){
                    savings.setEnabled(false);
                    budgetTypeStr = "Expense";
                }
                else{
                    savings.setEnabled(true);
                    budgetTypeStr = "";
                }
            }
        });

        ux.onChange(savings, new UX.onChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                if (isChecked){
                    expense.setEnabled(false);
                    budgetTypeStr = "Savings";
                }
                else{
                    expense.setEnabled(false);
                    budgetTypeStr = "";
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWallet();
            }
        });
    }

    private void saveWallet() {
        if (ux.validation(new int[]{R.id.Amount,R.id.Note,R.id.ExpiresOn},mainLayout)){
            databaseHelper.addNewWallet(new Wallet(WalletName.getText().toString(),Integer.parseInt(Amount.getText().toString()),
                    currencyValue,ExpiresOn.getText().toString(),budgetTypeStr,Note.getText().toString()));
            Toast.makeText(this,getResources().getString(R.string.data_saved_successfully),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WalletActivity.this,HomeActivity.class));
        }
    }

    private void setVisibility(CheckBox checkBox, boolean isChecked) {
        if (isChecked) checkBox.setEnabled(false);
        else checkBox.setEnabled(true);
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
                    dateView.setText(""+dateFormatter.format(newDate.getTime()));
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

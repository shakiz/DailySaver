package com.dailysaver.shadowhite.dailysaver.activities.wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.onboard.HomeActivity;
import com.dailysaver.shadowhite.dailysaver.models.wallet.Wallet;
import com.dailysaver.shadowhite.dailysaver.utills.DataManager;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private RelativeLayout mainLayout;
    private int currencyValue;
    private String budgetTypeStr;
    private EditText Amount,WalletName,Note;
    private TextView ExpiresOn;
    private FloatingActionButton add;
    private Spinner currencySpinner;
    private CheckBox expense,savings;
    private UX ux;
    private Tools tools;
    private DataManager spinnerData;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_wallet);

        init();

        ux.setToolbar(toolbar,this,HomeActivity.class);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);
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
        ExpiresOn = findViewById(R.id.ExpiresOn);
        expense = findViewById(R.id.Expense);
        savings = findViewById(R.id.Savings);
        add = findViewById(R.id.add);
        ux = new UX(this);
        tools = new Tools(this);
        databaseHelper = new DatabaseHelper(this);
        spinnerData = new DataManager(this);
    }

    private void bindUiWithComponents() {
        ux.setSpinnerAdapter(spinnerData.currencyData(),currencySpinner);

        ExpiresOn.setOnClickListener(this);

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
        if (ux.validation(new int[]{R.id.Amount,R.id.Note,R.id.ExpiresOn, R.id.Currency, R.id.WalletName, },mainLayout)){
            databaseHelper.addNewWallet(new Wallet(WalletName.getText().toString(),Integer.parseInt(Amount.getText().toString()),
                    currencyValue,ExpiresOn.getText().toString(),budgetTypeStr,Note.getText().toString()));
            Toast.makeText(this,getResources().getString(R.string.data_saved_successfully),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WalletActivity.this,HomeActivity.class));
        }
    }

    //Update expense
    private void updateWallet() {
        databaseHelper.updateWallet(new Wallet(
                WalletName.getText().toString(),Integer.parseInt(Amount.getText().toString()),
                currencyValue,ExpiresOn.getText().toString(),budgetTypeStr,Note.getText().toString()
        )
        ,0);
        Toast.makeText(this,getResources().getString(R.string.data_updated_successfully),Toast.LENGTH_LONG).show();
        startActivity(new Intent(WalletActivity.this,HomeActivity.class));
    }
    //end

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ExpiresOn:
                ux.getAndSetDate(ExpiresOn);
                break;
        }
    }

}

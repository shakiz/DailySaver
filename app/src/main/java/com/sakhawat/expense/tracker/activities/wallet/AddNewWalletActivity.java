package com.sakhawat.expense.tracker.activities.wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.sakhawat.expense.tracker.R;
import com.sakhawat.expense.tracker.activities.newrecord.AddNewRecordActivity;
import com.sakhawat.expense.tracker.activities.dashboard.DashboardActivity;
import com.sakhawat.expense.tracker.activities.records.RecordsActivity;
import com.sakhawat.expense.tracker.models.wallet.Wallet;
import com.sakhawat.expense.tracker.utills.DataManager;
import com.sakhawat.expense.tracker.utills.Tools;
import com.sakhawat.expense.tracker.utills.UX;
import com.sakhawat.expense.tracker.utills.dbhelper.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class AddNewWalletActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private RelativeLayout mainLayout;
    private int currencyValue;
    private String budgetTypeStr = "";
    private EditText Amount,WalletName,Note;
    private EditText ExpiresOn;
    private Spinner currencySpinner;
    private UX ux;
    private Tools tools;
    private DataManager spinnerData;
    private DatabaseHelper databaseHelper;
    private RadioGroup RecordType;
    private Button add, clear;
    private Wallet wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_wallet);

        init();

        //load animation with activity UI
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);
        }
        tools.setAnimation(mainLayout);

        // all the user interactions
        bindUiWithComponents();

        //check for any intent data
        if (getIntent().getSerializableExtra("wallet") != null){
            loadWalletRecord();
        }

        //check for intent to navigate toolbar onBackEventClick
        if (getIntent().getStringExtra("from").equals("record")) ux.setToolbar(toolbar,this,RecordsActivity.class,"","");
        else if (getIntent().getStringExtra("from").equals("newRecord")) ux.setToolbar(toolbar,this, AddNewRecordActivity.class,"from","wallet");
        else if (getIntent().getStringExtra("from").equals("walletDetails")) ux.setToolbar(toolbar,this, WalletDetailsActivity.class,"wallet",wallet);
        else if (getIntent().getStringExtra("from").equals("main")) ux.setToolbar(toolbar,this, DashboardActivity.class,"","");
    }

    private void loadWalletRecord() {
        wallet = (Wallet) getIntent().getSerializableExtra("wallet");
        if (wallet != null){
            Amount.setText(String.valueOf(wallet.getAmount()));
            WalletName.setText(wallet.getTitle());
            Note.setText(wallet.getNote());
            ExpiresOn.setText(wallet.getExpiresOn());
            currencySpinner.setSelection(wallet.getCurrency(), true);
            loadRadioGroupData();
            add.setText(getText(R.string.update));
        }
    }

    //load the radioGroup of recordType data
    private void loadRadioGroupData(){
        budgetTypeStr = wallet.getWalletType();
        for (int i = 0; i < RecordType.getChildCount() ; i++) {
            RadioButton radioButton = (RadioButton) RecordType.getChildAt(i);
            if (radioButton.getTag() != null) {
                if(radioButton.getTag().equals(wallet.getWalletType())) {
                    radioButton.setChecked(true);
                    return;
                }
            }
        }
    }

    //will init all the components and new instances
    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.home_layout);
        Amount = findViewById(R.id.Amount);
        WalletName = findViewById(R.id.WalletName);
        RecordType = findViewById(R.id.TransactionType);
        Note = findViewById(R.id.Note);
        ExpiresOn = findViewById(R.id.ExpiresOn);
        currencySpinner = findViewById(R.id.Currency);
        add = findViewById(R.id.add);
        clear = findViewById(R.id.clear);
        ux = new UX(this, mainLayout);
        tools = new Tools(this);
        databaseHelper = DatabaseHelper.getHelper(this);
        spinnerData = new DataManager(this);
    }

    private void bindUiWithComponents() {
        //set spinner
        ux.setSpinnerAdapter(spinnerData.currencyData(),currencySpinner);

        //Date selection
        ExpiresOn.setOnClickListener(this);

        //spinner onChange
        ux.onSpinnerChange(currencySpinner, new UX.onSpinnerChangeListener() {
            @Override
            public void onChange(AdapterView<?> parent, View view, int position, long id) {
                currencyValue = position;
            }
        });

        //add new wallet listener
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wallet != null) {
                    updateWallet();
                } else {
                    saveWallet();
                }
            }
        });

        //clear ui details listener
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ux.clearDetailsUI(new int[]{R.id.Amount, R.id.WalletName, R.id.Note, R.id.ExpiresOn, R.id.Currency, R.id.TransactionType});
            }
        });

        //transactionType or recordType change listener
        RecordType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedRadio = group.getCheckedRadioButtonId();
                View viewRadioButton = findViewById(selectedRadio);
                RadioButton radioButton = (RadioButton) viewRadioButton;
                if (radioButton.isChecked()){
                    budgetTypeStr = radioButton.getTag().toString();
                }
            }
        });
    }

    //save wallet
    private void saveWallet() {
        if (!TextUtils.isEmpty(Amount.getText().toString())){
            if (!TextUtils.isEmpty(WalletName.getText().toString())){
                if (!TextUtils.isEmpty(Note.getText().toString())){
                    if (!budgetTypeStr.isEmpty()){
                        Wallet wallet = new Wallet(WalletName.getText().toString(), Integer.parseInt(Amount.getText().toString()), currencyValue, ExpiresOn.getText().toString(), budgetTypeStr,
                                Note.getText().toString());
                        new SaveWallet(wallet).execute();
                    }
                    else {
                        Snackbar.make(mainLayout,getText(R.string.select_wallet_type),Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Note.requestFocus();
                    Note.setError(getText(R.string.input_validation));
                }
            }
            else {
                WalletName.requestFocus();
                WalletName.setError(getText(R.string.input_validation));
            }
        }
        else {
            Amount.requestFocus();
            Amount.setError(getText(R.string.input_validation));
        }
    }

    //Update expense
    private void updateWallet() {
        if (!TextUtils.isEmpty(Amount.getText().toString())){
            if (!TextUtils.isEmpty(WalletName.getText().toString())){
                if (!TextUtils.isEmpty(Note.getText().toString())){
                    if (!budgetTypeStr.isEmpty()){
                        Wallet updatableWallet = new Wallet(WalletName.getText().toString(), Integer.parseInt(Amount.getText().toString()), currencyValue, ExpiresOn.getText().toString(), budgetTypeStr,
                                Note.getText().toString());
                        new UpdateWallet(updatableWallet, wallet.getId()).execute();
                    }
                    else {
                        Snackbar.make(mainLayout,getText(R.string.select_wallet_type),Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Note.requestFocus();
                    Note.setError(getText(R.string.input_validation));
                }
            }
            else {
                WalletName.requestFocus();
                WalletName.setError(getText(R.string.input_validation));
            }
        }
        else {
            Amount.requestFocus();
            Amount.setError(getText(R.string.input_validation));
        }
    }
    //end

    //AsyncTask for saving wallet
    private class SaveWallet extends AsyncTask<Wallet, Void, Void> {
        Wallet wallet;

        public SaveWallet(Wallet wallet) {
            this.wallet = wallet;
        }

        @Override
        protected Void doInBackground(Wallet... wallets) {
            databaseHelper.addNewWallet(wallet);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),getText(R.string.data_saved_successfully),Toast.LENGTH_LONG).show();
            startActivity(new Intent(AddNewWalletActivity.this,DashboardActivity.class));
        }
    }

    //AsyncTask for updating wallet
    private class UpdateWallet extends AsyncTask<Wallet, Void, Void> {
        Wallet wallet;
        int walletId;

        public UpdateWallet(Wallet wallet, int walletId) {
            this.wallet = wallet;
            this.walletId = walletId;
        }

        @Override
        protected Void doInBackground(Wallet... wallets) {
            databaseHelper.updateWallet(wallet, walletId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),getText(R.string.data_updated_successfully),Toast.LENGTH_LONG).show();
            startActivity(new Intent(AddNewWalletActivity.this, WalletDetailsActivity.class).putExtra("wallet",wallet));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ExpiresOn:
                ux.getAndSetDate(ExpiresOn);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getStringExtra("from").equals("record")) startActivity(new Intent(this, RecordsActivity.class));
        else if (getIntent().getStringExtra("from").equals("newRecord")) startActivity(new Intent(this, AddNewRecordActivity.class).putExtra("from","wallet"));
        else if (getIntent().getStringExtra("from").equals("main")) startActivity(new Intent(this, DashboardActivity.class));
        else if (getIntent().getStringExtra("from").equals("walletDetails")) startActivity(new Intent(this, WalletDetailsActivity.class).putExtra("wallet",wallet));
        overridePendingTransition(R.anim.fadein,R.anim.push_up_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}

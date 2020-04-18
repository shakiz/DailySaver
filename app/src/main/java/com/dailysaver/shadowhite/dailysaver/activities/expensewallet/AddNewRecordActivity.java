package com.dailysaver.shadowhite.dailysaver.activities.expensewallet;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dailysaver.shadowhite.dailysaver.activities.dashboard.DashboardActivity;
import com.dailysaver.shadowhite.dailysaver.activities.records.RecordsActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.category.CategoryRecyclerAdapter;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.models.record.Record;
import com.dailysaver.shadowhite.dailysaver.utills.DataManager;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class AddNewRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mainLayout;
    private Toolbar toolbar;
    private FloatingActionButton addOrUpdate;
    private EditText Amount,Note;
    private TextView ExpenseDate, DateView, CategorySelector;
    private Spinner currencySpinner, walletSpinner;
    private Dialog itemDialog;
    private LinearLayout dialogLinearLayout;
    private TextView categorySelection , categoryTitle;
    private ImageView categoryIcon;
    private RecyclerView categoryRecyclerView;
    private RadioGroup RecordType;
    private int currencyValue = 0, walletValue = 0;
    private String walletTitleStr = "" , recordTypeStr = "";
    private UX ux;
    private Tools tools;
    private DatabaseHelper databaseHelper;
    private DataManager dataManager;
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);

        init();

        //set spinner data
        ux.setSpinnerAdapter(dataManager.currencyData(),currencySpinner);
        ux.setSpinnerAdapter(dataManager.getWalletTitle(""),walletSpinner);

        //Check for pref data
        if (getIntent().getSerializableExtra("expense") != null){
            loadRecord();
        }

        //Set toolbar
        if (getIntent().getStringExtra("from").equals("record")) ux.setToolbar(toolbar,this,RecordsActivity.class,"","");
        else ux.setToolbar(toolbar,this, DashboardActivity.class,"","");

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);
        tools.setAnimation(mainLayout);

        // all the user interactions
        bindUIWIthComponents();
    }

    //will init all the components and new instances
    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.parent_container);
        currencySpinner = findViewById(R.id.Currency);
        RecordType = findViewById(R.id.RecordType);
        CategorySelector = findViewById(R.id.CategorySelector);
        walletSpinner = findViewById(R.id.Wallet);
        DateView = findViewById(R.id.ExpenseDate);
        categorySelection = findViewById(R.id.CategorySelector);
        categoryTitle = findViewById(R.id.Title);
        categoryIcon = findViewById(R.id.IconRes);
        Amount = findViewById(R.id.Amount);
        ExpenseDate = findViewById(R.id.ExpenseDate);
        Note = findViewById(R.id.Note);
        addOrUpdate = findViewById(R.id.add);
        ux = new UX(this,mainLayout);
        tools = new Tools(this);
        databaseHelper = new DatabaseHelper(this);
        dataManager = new DataManager(this);
    }

    private void bindUIWIthComponents() {

        //spinner onChange
        ux.onSpinnerChange(currencySpinner, new UX.onSpinnerChangeListener() {
            @Override
            public void onChange(AdapterView<?> parent, View view, int position, long id) {
                currencyValue = position;
            }
        });
        ux.onSpinnerChange(walletSpinner, new UX.onSpinnerChangeListener() {
            @Override
            public void onChange(AdapterView<?> parent, View view, int position, long id) {
                walletValue = position;
                walletTitleStr = parent.getAdapter().getItem(position).toString();
            }
        });

        //DateView click listener
        DateView.setOnClickListener(this);

        //Category on click for selecting on click
        categorySelection.setOnClickListener(this);

        //RecordType click listener
        RecordType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedRadio = group.getCheckedRadioButtonId();
                View viewRadioButton = findViewById(selectedRadio);
                RadioButton radioButton = (RadioButton) viewRadioButton;
                if (radioButton.isChecked()){
                    recordTypeStr = radioButton.getTag().toString();
                    Log.v("recordTypeStr",recordTypeStr);
                    //Change the UI based on transaction type
                    ux.changeUI(new int[]{R.id.Amount, R.id.Currency, R.id.Wallet, R.id.Note, R.id.ExpenseDate, R.id.categoryItemLayout}, recordTypeStr);

                    if (recordTypeStr.equals("Savings")) {
                        ux.setSpinnerAdapter(dataManager.getWalletTitle("Savings"),walletSpinner);
                        CategorySelector.setTextColor(getResources().getColor(R.color.md_green_600));
                    }
                    else if (recordTypeStr.equals("Expense")){
                        ux.setSpinnerAdapter(dataManager.getWalletTitle("Expense"),walletSpinner);
                        CategorySelector.setTextColor(getResources().getColor(R.color.md_red_600));
                    }
                }
            }
        });

        //add and update event listener
        addOrUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getSerializableExtra("expense") != null) {
                    updateExpense();
                }
                else {
                    saveExpense();
                }
            }
        });

    }

    //Save expense into DB with validation
    private void saveExpense(){
        if (!recordTypeStr.isEmpty()){
            if (ux.validation(new int[]{R.id.Amount, R.id.Currency, R.id.Wallet, R.id.Note, R.id.ExpenseDate})){
                if (getRemainingBalance()){
                    databaseHelper.addNewExpense(new Record(
                            Integer.parseInt(Amount.getText().toString()),
                            currencyValue,
                            recordTypeStr,
                            categoryTitle.getText().toString(),
                            walletTitleStr,
                            walletValue,
                            Note.getText().toString(),
                            ExpenseDate.getText().toString()));

                    Toast.makeText(this,getResources().getString(R.string.data_saved_successfully),Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddNewRecordActivity.this,RecordsActivity.class));
                }
                else{
                    Snackbar.make(mainLayout,getResources().getString(R.string.wallet_amount_exceeds), Snackbar.LENGTH_SHORT).show();
                }
            }
        }
        else Snackbar.make(mainLayout,getResources().getString(R.string.select_record_type_error_message),Snackbar.LENGTH_SHORT).show();
    }
    //end

    //Update expense
    private void updateExpense() {

        if (!recordTypeStr.isEmpty()){
            if (ux.validation(new int[]{R.id.Amount, R.id.Currency, R.id.Wallet, R.id.Note, R.id.ExpenseDate})){
                if (getRemainingBalance()){
                    databaseHelper.updateExpense(new Record(
                                    Integer.parseInt(Amount.getText().toString()),
                                    currencyValue,
                                    recordTypeStr,
                                    categoryTitle.getText().toString(),
                                    walletTitleStr,
                                    walletValue,
                                    Note.getText().toString(),
                                    ExpenseDate.getText().toString())
                            , record.getId());

                    Toast.makeText(this,getResources().getString(R.string.data_updated_successfully),Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddNewRecordActivity.this,RecordsActivity.class));
                }
                else{
                    Snackbar.make(mainLayout,getResources().getString(R.string.wallet_amount_exceeds), Snackbar.LENGTH_LONG).show();
                }
            }
        }
        else Snackbar.make(mainLayout,getResources().getString(R.string.select_record_type_error_message),Snackbar.LENGTH_SHORT).show();
    }
    //end

    //region get selected wallet remaining balance
    private boolean getRemainingBalance(){
        if (getIntent().getSerializableExtra("expense") != null) {
            walletValue = record.getWalletId();
        }
        int remaining = databaseHelper.getWalletBalance(walletValue) - databaseHelper.singleWalletTotalCost(walletTitleStr);
        if (!TextUtils.isEmpty(Amount.getText().toString())){
            if (Integer.parseInt(Amount.getText().toString()) < remaining){
                return true;
            }
            else{
                return false;
            }
        }
        else {
            return false;
        }
    }
    //endregion

    //Load pref data
    private void loadRecord() {
        record = (Record) getIntent().getSerializableExtra("expense");
        loadRadioGroupData();
        ux.setSpinnerAdapter(dataManager.getWalletTitle(record.getRecordType()),walletSpinner);
        walletValue = record.getWalletId();
        walletTitleStr = record.getWalletTitle();
        categoryTitle.setText(record.getCategory());
        setTypeIcon(categoryIcon, record.getCategory());
        Amount.setText(""+ record.getAmount());
        Note.setText(record.getNote());
        ExpenseDate.setText(record.getExpenseDate());
        currencySpinner.setSelection(record.getWalletId(), true);
        currencyValue = record.getCurrency();
        walletSpinner.setSelection(record.getWalletId(), true);
        addOrUpdate.setImageResource(R.drawable.ic_action_done);
    }

    //Set category icon
    private void setTypeIcon(ImageView icon, String type) {
        if (type.equals("Food")) icon.setImageResource(R.drawable.ic_food_icon);
        else if (type.equals("Transport")) icon.setImageResource(R.drawable.ic_transport);
        else if (type.equals("Electricity")) icon.setImageResource(R.drawable.ic_electricity);
        else if (type.equals("Education")) icon.setImageResource(R.drawable.ic_education);
        else if (type.equals("Shopping")) icon.setImageResource(R.drawable.ic_cshopping);
        else if (type.equals("Fun")) icon.setImageResource(R.drawable.ic_entertainment);
        else if (type.equals("Family")) icon.setImageResource(R.drawable.ic_family);
        else if (type.equals("Friends")) icon.setImageResource(R.drawable.ic_friends);
        else if (type.equals("Work")) icon.setImageResource(R.drawable.ic_work);
        else if (type.equals("Gift")) icon.setImageResource(R.drawable.ic_gift);
    }

    //Show dialog
    private void showDialog() {
        itemDialog = new Dialog(this);
        itemDialog.setContentView(R.layout.category_list_layout);
        customViewInit(itemDialog);
        setCategoryAdapter();
        itemDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Animation a = AnimationUtils.loadAnimation(itemDialog.getContext(), R.anim.push_up_in);
        dialogLinearLayout.startAnimation(a);
        itemDialog.show();
    }

    //Category adapter
    private void setCategoryAdapter() {
        CategoryRecyclerAdapter categoryRecyclerAdapter = new CategoryRecyclerAdapter(this, dataManager.setCategoryDataData(),itemDialog,categoryTitle,categoryIcon);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        categoryRecyclerView.setAdapter(categoryRecyclerAdapter);
        categoryRecyclerAdapter.notifyDataSetChanged();
    }

    //dialog components init
    private void customViewInit(Dialog itemDialog) {
        dialogLinearLayout = itemDialog.findViewById(R.id.dialogLinearLayout);
        categoryRecyclerView = itemDialog.findViewById(R.id.categoryRecyclerView);
    }

    //load the radioGroup of recordType data
    private void loadRadioGroupData(){
        recordTypeStr = record.getRecordType();
        for (int i = 0; i <RecordType.getChildCount() ; i++) {
            RadioButton radioButton = (RadioButton)RecordType.getChildAt(i);
            if (radioButton.getTag() != null) {
                if(radioButton.getTag().equals(record.getRecordType())) {
                    radioButton.setChecked(true);
                    if (record.getRecordType().equals("Savings")) {
                        //Change the UI based on transaction type
                        ux.changeUI(new int[]{R.id.Amount, R.id.Currency, R.id.Wallet, R.id.Note, R.id.ExpenseDate, R.id.categoryItemLayout}, "Savings");
                        CategorySelector.setTextColor(getResources().getColor(R.color.md_green_600));
                    }
                    else if (record.getRecordType().equals("Expense")){
                        //Change the UI based on transaction type
                        ux.changeUI(new int[]{R.id.Amount, R.id.Currency, R.id.Wallet, R.id.Note, R.id.ExpenseDate, R.id.categoryItemLayout}, "Expense");
                        CategorySelector.setTextColor(getResources().getColor(R.color.md_red_600));
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ExpenseDate:
                ux.getAndSetDate(DateView);
                break;
            case R.id.CategorySelector:
                showDialog();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getStringExtra("from").equals("record")) startActivity(new Intent(AddNewRecordActivity.this, RecordsActivity.class));
        else startActivity(new Intent(AddNewRecordActivity.this, DashboardActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.push_up_out);
    }
}

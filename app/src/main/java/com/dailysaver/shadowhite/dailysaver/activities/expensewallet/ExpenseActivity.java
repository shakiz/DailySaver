package com.dailysaver.shadowhite.dailysaver.activities.expensewallet;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dailysaver.shadowhite.dailysaver.activities.onboard.HomeActivity;
import com.dailysaver.shadowhite.dailysaver.activities.wallet.WalletActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.category.CategoryRecyclerAdapter;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.models.expense.Expense;
import com.dailysaver.shadowhite.dailysaver.utills.DataLoader;
import com.dailysaver.shadowhite.dailysaver.utills.DataManager;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ExpenseActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mainLayout;
    private Toolbar toolbar;
    private FloatingActionButton addOrUpdate;
    private EditText Amount,Note;
    private TextView ExpenseDate, dateView;
    private Spinner currencySpinner, walletSpinner;
    private Dialog itemDialog;
    private LinearLayout dialogLinearLayout;
    private TextView categorySelection , categoryTitle;
    private ImageView categoryIcon;
    private RecyclerView categoryRecyclerView;
    private CategoryRecyclerAdapter categoryRecyclerAdapter;
    private int currencyValue = 0, walletValue = 0;
    private String walletTitleStr;
    private UX ux;
    private Tools tools;
    private DataLoader dataLoader;
    private DatabaseHelper databaseHelper;
    private DataManager dataManager;
    private Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expense);

        init();

        //set spinner data
        ux.setSpinnerAdapter(dataManager.currencyData(),currencySpinner);
        ux.setSpinnerAdapter(dataManager.getWalletTitle(),walletSpinner);

        //Check for pref data
        if (getIntent().getSerializableExtra("expense") != null){
            loadRecord();
        }

        //Set toolbar
        ux.setToolbar(toolbar,this,HomeActivity.class);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);
        tools.setAnimation(mainLayout);

        //check for any wallet exist or not
        if (databaseHelper.getAllWalletItems().size() == 0){
            Toast.makeText(this,getResources().getString(R.string.please_add_wallet),Toast.LENGTH_LONG).show();
            startActivity(new Intent(ExpenseActivity.this, WalletActivity.class));
        }

        bindUIWIthComponents();
    }

    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.parent_container);
        currencySpinner = findViewById(R.id.Currency);
        walletSpinner = findViewById(R.id.Wallet);
        dateView = findViewById(R.id.ExpenseDate);
        categorySelection = findViewById(R.id.CategorySelector);
        categoryTitle = findViewById(R.id.Title);
        categoryIcon = findViewById(R.id.IconRes);
        Amount = findViewById(R.id.Amount);
        ExpenseDate = findViewById(R.id.ExpenseDate);
        Note = findViewById(R.id.Note);
        addOrUpdate = findViewById(R.id.add);
        ux = new UX(this);
        tools = new Tools(this);
        dataLoader = new DataLoader(this);
        databaseHelper = new DatabaseHelper(this);
        dataManager = new DataManager(this);
    }

    private void bindUIWIthComponents() {

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

        dateView.setOnClickListener(this);

        //Category on click for selecting on click
        categorySelection.setOnClickListener(this);

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
        if (ux.validation(new int[]{R.id.Amount, R.id.Currency, R.id.Wallet, R.id.Note, R.id.ExpenseDate},mainLayout)){
            if (getRemainingBalance()){

                databaseHelper.addNewExpense(new Expense(
                                Integer.parseInt(Amount.getText().toString()),
                                currencyValue,
                                categoryTitle.getText().toString(),
                                walletTitleStr,
                                walletValue,
                                Note.getText().toString(),
                                tools.convertDateToLong(tools.convertStrToDate(ExpenseDate.getText().toString()))));

                Toast.makeText(this,getResources().getString(R.string.data_saved_successfully),Toast.LENGTH_LONG).show();
                startActivity(new Intent(ExpenseActivity.this,HomeActivity.class));
            }
            else{
                //LGSnackbarManager.show(LGSnackBarTheme.SnackbarStyle.INFO, getResources().getString(R.string.wallet_amount_exceeds));
                Snackbar.make(mainLayout,getResources().getString(R.string.wallet_amount_exceeds), Snackbar.LENGTH_LONG).show();
            }
        }
    }
    //end

    //region get selected wallet remaining balance
    private boolean getRemainingBalance(){
        if (getIntent().getSerializableExtra("expense") != null) {
            walletValue = expense.getWalletId();
        }
        int remaining = databaseHelper.getWalletBalance(walletValue) - databaseHelper.singleWalletTotalCost(walletValue);
        if (Integer.parseInt(Amount.getText().toString()) < remaining){
            return true;
        }
        else{
            return false;
        }
    }
    //endregion

    //Update expense
    private void updateExpense() {

        if (ux.validation(new int[]{R.id.Amount, R.id.Currency, R.id.Wallet, R.id.Note, R.id.ExpenseDate},mainLayout)){
            if (getRemainingBalance()){
                databaseHelper.updateExpense(new Expense(
                                Integer.parseInt(Amount.getText().toString()),
                                currencyValue,
                                categoryTitle.getText().toString(),
                                walletTitleStr,
                                walletValue,
                                Note.getText().toString(),
                                tools.convertDateToLong(tools.convertStrToDate(ExpenseDate.getText().toString())))
                        , expense.getId());

                Toast.makeText(this,getResources().getString(R.string.data_updated_successfully),Toast.LENGTH_LONG).show();
                startActivity(new Intent(ExpenseActivity.this,HomeActivity.class));
            }
            else{
                //LGSnackbarManager.show(LGSnackBarTheme.SnackbarStyle.INFO, getResources().getString(R.string.wallet_amount_exceeds));
                Snackbar.make(mainLayout,getResources().getString(R.string.wallet_amount_exceeds), Snackbar.LENGTH_LONG).show();
            }
        }
    }
    //end

    //Load pref data
    private void loadRecord() {
        expense = (Expense) getIntent().getSerializableExtra("expense");
        categoryTitle.setText(expense.getCategory());
        setTypeIcon(categoryIcon, expense.getCategory());
        Amount.setText(""+ expense.getAmount());
        Note.setText(expense.getNote());
        ExpenseDate.setText(tools.longToDateString(expense.getExpenseDate()));
        currencySpinner.setSelection(expense.getWalletId(), true);
        walletSpinner.setSelection(expense.getWalletId(), true);
        addOrUpdate.setImageResource(R.drawable.ic_action_done);
    }

    //Set category icon
    private void setTypeIcon(ImageView icon, String type) {
        if (type.equals("Food")) icon.setImageResource(R.drawable.ic_food_icon);
        else if (type.equals("Transport")) icon.setImageResource(R.drawable.ic_transport);
        else if (type.equals("Electricity")) icon.setImageResource(R.drawable.ic_electricity);
        else if (type.equals("Education")) icon.setImageResource(R.drawable.ic_education);
        else if (type.equals("Shopping")) icon.setImageResource(R.drawable.ic_cshopping);
        else if (type.equals("Entertainment")) icon.setImageResource(R.drawable.ic_entertainment);
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
        categoryRecyclerAdapter = new CategoryRecyclerAdapter(this, dataManager.setCategoryDataData(),itemDialog,categoryTitle,categoryIcon);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        categoryRecyclerView.setAdapter(categoryRecyclerAdapter);
        categoryRecyclerAdapter.notifyDataSetChanged();
    }

    private void customViewInit(Dialog itemDialog) {
        dialogLinearLayout = itemDialog.findViewById(R.id.dialogLinearLayout);
        categoryRecyclerView = itemDialog.findViewById(R.id.categoryRecyclerView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ExpenseDate:
                ux.getAndSetDate(dateView);
                break;
            case R.id.CategorySelector:
                showDialog();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ExpenseActivity.this,HomeActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.push_up_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

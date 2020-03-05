package com.dailysaver.shadowhite.dailysaver.activities.expensewallet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dailysaver.shadowhite.dailysaver.activities.onboard.HomeActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.category.CategoryRecyclerAdapter;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.models.category.Category;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.dailysaver.shadowhite.dailysaver.utills.UX;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddNewBudgetActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mainLayout;
    private FloatingActionButton add;
    private TextView Title;
    private EditText Amount,ExpenseDate,Note;
    private Toolbar toolbar;
    private Spinner currencySpinner;
    private ArrayAdapter<String> spinnerAdapter;
    private Dialog itemDialog;
    private LinearLayout dialogLinearLayout;
    private TextView categorySelection , categoryTitle;
    private ImageView categoryIcon;
    private RecyclerView categoryRecyclerView;
    private CategoryRecyclerAdapter categoryRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Category> categoryList;
    private EditText dateView;
    private SimpleDateFormat dateFormatter;
    private UX ux;
    private Tools tools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_budget);

        init();
        ux.setToolbar(toolbar,this,HomeActivity.class);
        tools.setAnimation(mainLayout);
    }


    private void init() {
        ux = new UX(this);
        tools = new Tools(this);
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.parent_container);
        currencySpinner = findViewById(R.id.Currency);
        dateView = findViewById(R.id.ExpenseDate);
        categorySelection = findViewById(R.id.CategorySelector);
        categoryTitle = findViewById(R.id.Title);
        categoryIcon = findViewById(R.id.IconRes);
        Title = findViewById(R.id.Title);
        Amount = findViewById(R.id.Amount);
        ExpenseDate = findViewById(R.id.ExpenseDate);
        Note = findViewById(R.id.Note);
        add = findViewById(R.id.add);
        bindUIWIthComponents();
    }

    private void bindUIWIthComponents() {
        setSpinnerAdapter();
        dateView.setOnClickListener(this);
        categorySelection.setOnClickListener(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveExpense();
            }
        });
    }

    private void saveExpense(){
        startActivity(new Intent(AddNewBudgetActivity.this,HomeActivity.class));
    }

    private void setSpinnerAdapter() {
        spinnerAdapter = new ArrayAdapter<String>(this,R.layout.spinner_drop,new String[]{getResources().getString(R.string.select_currency),"BDT Tk.","US Dollar"});
        currencySpinner.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.notifyDataSetChanged();
    }

    private void getAndSetDate(final EditText editText){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        }
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    editText.setText(dateFormatter.format(newDate.getTime()));
                }
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

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

    private void setCategoryAdapter() {
        categoryRecyclerAdapter = new CategoryRecyclerAdapter(this,setData(),itemDialog,categoryTitle,categoryIcon);
        layoutManager = new GridLayoutManager(this,2);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setAdapter(categoryRecyclerAdapter);
        categoryRecyclerAdapter.notifyDataSetChanged();
    }

    private ArrayList<Category> setData() {
        categoryList.add(new Category("Food",R.drawable.ic_food_icon));
        categoryList.add(new Category("Transport",R.drawable.ic_transport));
        categoryList.add(new Category("Electricity",R.drawable.ic_electricity));
        categoryList.add(new Category("Education",R.drawable.ic_education));
        categoryList.add(new Category("Shopping",R.drawable.ic_cshopping));
        categoryList.add(new Category("Entertainment",R.drawable.ic_entertainment));
        categoryList.add(new Category("Family",R.drawable.ic_family));
        categoryList.add(new Category("Friends",R.drawable.ic_friends));
        categoryList.add(new Category("Work",R.drawable.ic_work));
        categoryList.add(new Category("Gift",R.drawable.ic_gift));
        return categoryList;
    }

    private void customViewInit(Dialog itemDialog) {
        dialogLinearLayout = itemDialog.findViewById(R.id.dialogLinearLayout);
        categoryRecyclerView = itemDialog.findViewById(R.id.categoryRecyclerView);
        categoryList = new ArrayList<>();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ExpenseDate:
                getAndSetDate(dateView);
                break;
            case R.id.CategorySelector:
                showDialog();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddNewBudgetActivity.this,HomeActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.push_up_out);
    }
}

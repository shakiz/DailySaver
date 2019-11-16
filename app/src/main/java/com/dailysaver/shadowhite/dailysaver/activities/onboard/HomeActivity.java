package com.dailysaver.shadowhite.dailysaver.activities.onboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dailysaver.shadowhite.dailysaver.models.expensewallet.ExpenseModel;
import com.dailysaver.shadowhite.dailysaver.MvvmDs.TheViewModel;
import com.dailysaver.shadowhite.dailysaver.activities.expensewallet.AddNewExpenseActivity;
import com.dailysaver.shadowhite.dailysaver.activities.savingswallet.AddNewWalletActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.HomeDashboardSliderAdapter;
import com.dailysaver.shadowhite.dailysaver.adapters.IconMenuAdapter;
import com.dailysaver.shadowhite.dailysaver.adapters.MonthlyExpenseDashboardAdapter;
import com.dailysaver.shadowhite.dailysaver.models.savingswallet.WalletModel;
import com.dailysaver.shadowhite.dailysaver.models.IconPowerMenuItem;
import com.dailysaver.shadowhite.dailysaver.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skydoves.powermenu.CircularEffect;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<WalletModel> cardItemList;
    //private ArrayList<ExpenseModel> monthlyExpenseList;
    private MonthlyExpenseDashboardAdapter monthlyExpenseDashboardAdapter;
    private RecyclerView recyclerView;
    private RelativeLayout mainLayout;
    private Toolbar toolbar;
    private FloatingActionButton addButton;
    private CustomPowerMenu powerMenu;
    private SliderView sliderView;
    private TheViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApp();
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mainLayout.startAnimation(a);
        bindUiWithComponents();
    }

    private void bindUiWithComponents() {
        setCardSlider();
        setAdapter();
        setMenu();
    }

    private void setMenu() {
        powerMenu =new CustomPowerMenu.Builder<>(this, new IconMenuAdapter())
                .addItem(new IconPowerMenuItem(ContextCompat.getDrawable(this, R.drawable.ic_expense), getResources().getString(R.string.add_new_expense)))
                .addItem(new IconPowerMenuItem(ContextCompat.getDrawable(this, R.drawable.ic_wallet), getResources().getString(R.string.add_new_wallet)))
                .setAnimation(MenuAnimation.SHOW_UP_CENTER) // Animation start point (TOP | LEFT).
                .setMenuRadius(15f) // sets the corner radius.
                .setMenuShadow(10f) // sets the shadow.
                .setWidth(800)
                .setCircularEffect(CircularEffect.INNER)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                powerMenu.showAsAnchorCenter(mainLayout);
            }
        });
    }

    private void setAdapter() {
        monthlyExpenseDashboardAdapter = new MonthlyExpenseDashboardAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(TheViewModel.class);
        viewModel.getAllExpenses().observe(this, new Observer<List<ExpenseModel>>() {
            @Override
            public void onChanged(List<ExpenseModel> expenseModels) {
                //Toast.makeText(getApplicationContext(),"Observed",Toast.LENGTH_LONG).show();
                monthlyExpenseDashboardAdapter.setExpenseModelData(expenseModels);
                recyclerView.setAdapter(monthlyExpenseDashboardAdapter);
            }
        });
    }

    private OnMenuItemClickListener<IconPowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<IconPowerMenuItem>() {
        @Override
        public void onItemClick(int position, IconPowerMenuItem item) {
            if (position==0)startActivity(new Intent(HomeActivity.this, AddNewExpenseActivity.class));
            else if (position==1) startActivity(new Intent(HomeActivity.this, AddNewWalletActivity.class));
            powerMenu.dismiss();
        }
    };

    private void setCardSlider() {
        getData();
        sliderView.setSliderAdapter(new HomeDashboardSliderAdapter(cardItemList,this));
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorPadding(8);
    }

    private void getData() {
        cardItemList.add(new WalletModel("Earned Wallet",2500,"Tk.","21-Oct-19","Savings"));
        cardItemList.add(new WalletModel("Expense Wallet",3200,"Tk.","22-Nov-19","Expense"));
        cardItemList.add(new WalletModel("Earned Wallet",5000,"Tk.","21-Oct-19","Savings"));
        cardItemList.add(new WalletModel("Expense Wallet",7500,"Tk.","10-Oct-19","Expense"));
    }

    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        recyclerView = findViewById(R.id.mRecyclerView);
        sliderView = findViewById(R.id.Slider);
        mainLayout = findViewById(R.id.home_layout);
        addButton = findViewById(R.id.add);
        cardItemList = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_item, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //for item menu generate invoice
            case R.id.report:
                Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    public void exitApp(){
        Intent exitIntent = new Intent(Intent.ACTION_MAIN);
        exitIntent.addCategory(Intent.CATEGORY_HOME);
        exitIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(exitIntent);
    }
}


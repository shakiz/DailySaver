package com.dailysaver.shadowhite.dailysaver.activities.onboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.amitshekhar.DebugDB;
import com.dailysaver.shadowhite.dailysaver.activities.WalletDetailsActivity;
import com.dailysaver.shadowhite.dailysaver.activities.expensewallet.BudgetActivity;
import com.dailysaver.shadowhite.dailysaver.activities.wallet.WalletActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.wallet.WalletDetailsSliderAdapter;
import com.dailysaver.shadowhite.dailysaver.adapters.menu.IconMenuAdapter;
import com.dailysaver.shadowhite.dailysaver.adapters.monthlyexpense.MonthlyExpenseAdapter;
import com.dailysaver.shadowhite.dailysaver.models.budget.Budget;
import com.dailysaver.shadowhite.dailysaver.models.wallet.Wallet;
import com.dailysaver.shadowhite.dailysaver.models.menu.IconPowerMenuItem;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.utills.DataLoader;
import com.dailysaver.shadowhite.dailysaver.utills.Tools;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skydoves.powermenu.CircularEffect;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private MonthlyExpenseAdapter monthlyExpenseAdapter;
    private RecyclerView recyclerView;
    private RelativeLayout mainLayout;
    private Toolbar toolbar;
    private FloatingActionButton addButton;
    private CustomPowerMenu powerMenu;
    private SliderView sliderView;
    private DataLoader dataLoader;
    private Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        //db log
        Log.v("db",DebugDB.getAddressLog());

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApp();
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow);

        tools.setAnimation(mainLayout);
        bindUiWithComponents();
    }

    private void bindUiWithComponents() {
        setCardSlider();
        getBudgetData();
        setMenu();
    }

    private void setMenu() {
        powerMenu =new CustomPowerMenu.Builder<>(this, new IconMenuAdapter())
                .addItem(new IconPowerMenuItem(ContextCompat.getDrawable(this, R.drawable.ic_expense), getResources().getString(R.string.add_new_budget)))
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

    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        recyclerView = findViewById(R.id.mRecyclerView);
        sliderView = findViewById(R.id.Slider);
        mainLayout = findViewById(R.id.home_layout);
        addButton = findViewById(R.id.add);
        tools = new Tools(this);
        dataLoader = new DataLoader(this);
    }

    private OnMenuItemClickListener<IconPowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<IconPowerMenuItem>() {
        @Override
        public void onItemClick(int position, IconPowerMenuItem item) {
            if (position==0)startActivity(new Intent(HomeActivity.this, BudgetActivity.class));
            else if (position==1) startActivity(new Intent(HomeActivity.this, WalletActivity.class));
            powerMenu.dismiss();
        }
    };

    private void getBudgetData(){
        dataLoader.setOnBudgetItemsCompleted(new DataLoader.onBudgetItemsCompleted() {
            @Override
            public void onComplete(ArrayList<Budget> budgetList) {
                if (budgetList != null){
                    if (budgetList.size() != 0){
                        setBudgetAdapter(budgetList);
                    }
                }
            }
        });
    }

    private void setCardSlider() {
        dataLoader.setOnWalletItemsCompleted(new DataLoader.onWalletItemsCompleted() {
            @Override
            public void onComplete(ArrayList<Wallet> walletList) {
                if (walletList != null){
                    if (walletList.size() != 0){
                        setWalletAdapter(walletList);
                    }
                }
            }
        });
    }

    private void setWalletAdapter(ArrayList<Wallet> walletList) {
        sliderView.setSliderAdapter(new WalletDetailsSliderAdapter(walletList, this, new WalletDetailsSliderAdapter.onItemClick() {
            @Override
            public void itemClick(Wallet wallet) {
                startActivity(new Intent(HomeActivity.this, WalletDetailsActivity.class));
            }
        }));
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorPadding(8);
    }

    private void setBudgetAdapter(ArrayList<Budget> budgetList) {
        monthlyExpenseAdapter = new MonthlyExpenseAdapter(budgetList, new MonthlyExpenseAdapter.onItemClick() {
            @Override
            public void itemClick(Budget budget) {
                startActivity(new Intent(HomeActivity.this, BudgetActivity.class).putExtra("budget",budget));
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(monthlyExpenseAdapter);
        monthlyExpenseAdapter.notifyDataSetChanged();
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


package com.dailysaver.shadowhite.dailysaver.onboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
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
import com.dailysaver.shadowhite.dailysaver.activities.expensewallet.AddNewExpenseActivity;
import com.dailysaver.shadowhite.dailysaver.activities.savingswallet.AddNewWalletActivity;
import com.dailysaver.shadowhite.dailysaver.adapters.HomeDashboardSliderAdapter;
import com.dailysaver.shadowhite.dailysaver.adapters.IconMenuAdapter;
import com.dailysaver.shadowhite.dailysaver.adapters.MonthlyExpenseDashboardAdapter;
import com.dailysaver.shadowhite.dailysaver.models.savingswallet.WalletModel;
import com.dailysaver.shadowhite.dailysaver.models.IconPowerMenuItem;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.models.expensewallet.ExpenseModel;
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

    private ArrayList<WalletModel> cardItemList;
    private ArrayList<ExpenseModel> monthlyExpenseList;
    private MonthlyExpenseDashboardAdapter monthlyExpenseDashboardAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RelativeLayout mainLayout;
    private Toolbar toolbar;
    private FloatingActionButton addButton;
    private CustomPowerMenu powerMenu;
    private SliderView sliderView;

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
        monthlyExpenseDashboardAdapter = new MonthlyExpenseDashboardAdapter(this,monthlyExpenseList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(monthlyExpenseDashboardAdapter);
        monthlyExpenseDashboardAdapter.notifyDataSetChanged();
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
        cardItemList.add(new WalletModel("Earned Wallet","21-Oct-19","Income",150,2550,2400));
        cardItemList.add(new WalletModel("Expense Wallet","21-Oct-19","Expense",250,2550,2300));
        cardItemList.add(new WalletModel("Earned Wallet2 ","21-Oct-19","Income",350,2550,2200));
        cardItemList.add(new WalletModel("Expense Wallet2","21-Oct-19","Expense",450,2550,2100));

        monthlyExpenseList.add(new ExpenseModel("Office transport expense","Transport",50,"22-Jun-2019"));
        monthlyExpenseList.add(new ExpenseModel("Eat kolkata kacchi with friends","Food",260,"02-Jun-2019"));
        monthlyExpenseList.add(new ExpenseModel("Evening snacks","Food",20,"22-July-2019"));
        monthlyExpenseList.add(new ExpenseModel("Provide electricity bill for home","Electricity",2000,"28-Aug-2019"));
        monthlyExpenseList.add(new ExpenseModel("Bought gift for a friend","Gift",2000,"04-Nov-2019"));
    }

    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        recyclerView = findViewById(R.id.mRecyclerView);
        sliderView = findViewById(R.id.Slider);
        mainLayout = findViewById(R.id.home_layout);
        addButton = findViewById(R.id.add);
        cardItemList = new ArrayList<>();
        monthlyExpenseList = new ArrayList<>();
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


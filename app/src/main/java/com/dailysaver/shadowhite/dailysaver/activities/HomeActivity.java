package com.dailysaver.shadowhite.dailysaver.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dailysaver.shadowhite.dailysaver.adapters.HomeDashboardSliderAdapter;
import com.dailysaver.shadowhite.dailysaver.adapters.MonthlyExpenseDashboardAdapter;
import com.dailysaver.shadowhite.dailysaver.models.dashboard.WalletDashboardItemModel;
import com.dailysaver.shadowhite.dailysaver.models.IconPowerMenuItem;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.models.expense.ExpenseDashboardModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skydoves.powermenu.CircularEffect;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.MenuBaseAdapter;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<WalletDashboardItemModel> cardItemList;
    private ArrayList<ExpenseDashboardModel> monthlyExpenseList;
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
            else if (position==1) startActivity(new Intent(HomeActivity.this,AddNewWalletActivity.class));
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
        cardItemList.add(new WalletDashboardItemModel("Earned Wallet","21-Oct-19","Income",150,2550,2400));
        cardItemList.add(new WalletDashboardItemModel("Expense Wallet","21-Oct-19","Expense",250,2550,2300));
        cardItemList.add(new WalletDashboardItemModel("Earned Wallet2 ","21-Oct-19","Income",350,2550,2200));
        cardItemList.add(new WalletDashboardItemModel("Expense Wallet2","21-Oct-19","Expense",450,2550,2100));

        monthlyExpenseList.add(new ExpenseDashboardModel("Office transport expense","Transport",50,"22-Jun-2019"));
        monthlyExpenseList.add(new ExpenseDashboardModel("Eat kolkata kacchi with friends","Food",260,"02-Jun-2019"));
        monthlyExpenseList.add(new ExpenseDashboardModel("Evening snacks","Food",20,"22-July-2019"));
        monthlyExpenseList.add(new ExpenseDashboardModel("Provide electricity bill for home","Electricity",2000,"28-Aug-2019"));
        monthlyExpenseList.add(new ExpenseDashboardModel("Bought gift for a friend","Gift",2000,"04-Nov-2019"));
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

    public class IconMenuAdapter extends MenuBaseAdapter<IconPowerMenuItem> {

        @Override
        public View getView(int index, View view, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();

            if(view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_icon_menu, viewGroup, false);
            }

            IconPowerMenuItem item = (IconPowerMenuItem) getItem(index);
            final ImageView icon = view.findViewById(R.id.item_icon);
            icon.setImageDrawable(item.getIcon());
            final TextView title = view.findViewById(R.id.item_title);
            title.setText(item.getTitle());
            return super.getView(index, view, viewGroup);
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


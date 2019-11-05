package com.dailysaver.shadowhite.dailysaver;

import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skydoves.powermenu.CircularEffect;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.MenuBaseAdapter;
import com.skydoves.powermenu.OnMenuItemClickListener;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<CardItemModel> cardItemList;
    private CardAdapter cardAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RelativeLayout mainLayout;
    private FloatingActionButton addButton;
    private CustomPowerMenu powerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        Animation a = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mainLayout.startAnimation(a);
        bindUiWithComponents();
    }

    private void bindUiWithComponents() {
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

    private OnMenuItemClickListener<IconPowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<IconPowerMenuItem>() {
        @Override
        public void onItemClick(int position, IconPowerMenuItem item) {
            if (position==0)startActivity(new Intent(HomeActivity.this,MainActivity.class));
            powerMenu.dismiss();
        }
    };

    private void setAdapter() {
        getData();
        cardAdapter = new CardAdapter(cardItemList,this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cardAdapter);
        cardAdapter.notifyDataSetChanged();
    }

    private void getData() {
        cardItemList.add(new CardItemModel("Expense Wallet",150,2550,2400));
        cardItemList.add(new CardItemModel("Expense Wallet",150,2550,2400));
        cardItemList.add(new CardItemModel("Expense Wallet",150,2550,2400));
        cardItemList.add(new CardItemModel("Expense Wallet",150,2550,2400));
    }

    private void init() {
        recyclerView = findViewById(R.id.dashboardRecyclerView);
        mainLayout = findViewById(R.id.home_layout);
        addButton = findViewById(R.id.add);
        cardItemList = new ArrayList<>();
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


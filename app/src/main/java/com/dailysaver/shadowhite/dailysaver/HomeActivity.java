package com.dailysaver.shadowhite.dailysaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<CardItemModel> cardItemList;
    private CardAdapter cardAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        bindUiWithComponents();
    }

    private void bindUiWithComponents() {
        setAdapter();
    }

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
        cardItemList = new ArrayList<>();
    }
}


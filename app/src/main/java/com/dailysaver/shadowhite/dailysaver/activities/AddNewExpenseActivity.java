package com.dailysaver.shadowhite.dailysaver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.dailysaver.shadowhite.dailysaver.adapters.PagerAdapter;
import com.dailysaver.shadowhite.dailysaver.R;
import com.google.android.material.tabs.TabLayout;

public class AddNewExpenseActivity extends AppCompatActivity {

    private RelativeLayout mainLayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expense);

        init();
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddNewExpenseActivity.this,HomeActivity.class));
            }
        });

        Animation a = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mainLayout.startAnimation(a);

        bindUIWithComponents();


    }

    private void bindUIWithComponents() {
        //this is for tablayout to swipe around the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Daily Expenses"));
        tabLayout.addTab(tabLayout.newTab().setText("Dashboard"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(getResources().getColor(R.color.md_white_1000),
                getResources().getColor(R.color.md_white_1000));

        //viewpager to set the tablayout inside it
        final ViewPager viewPager = findViewById(R.id.pager);
        //creating viewpager adapter
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        //setting the viewpager adapter
        viewPager.setAdapter(adapter);
        //adding the onpage change listener with the viewpager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        mainLayout = findViewById(R.id.main_layout);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu_item, menu); //your file name
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            //for item menu generate invoice
//            case R.id.action_generate_invoice:
//                startActivity(new Intent(AddNewExpenseActivity.this,Invoice_generate.class));
//                return true;
//            //for item menu about us
//            case R.id.action_about_us:
//                Toast.makeText(getApplicationContext(),"About US",Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddNewExpenseActivity.this,HomeActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.push_up_out);
    }
}

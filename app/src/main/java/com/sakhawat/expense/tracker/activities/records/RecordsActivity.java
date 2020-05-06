package com.sakhawat.expense.tracker.activities.records;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sakhawat.expense.tracker.R;
import com.sakhawat.expense.tracker.activities.newrecord.AddNewRecordActivity;
import com.sakhawat.expense.tracker.activities.dashboard.DashboardActivity;
import com.sakhawat.expense.tracker.activities.wallet.AddNewWalletActivity;
import com.sakhawat.expense.tracker.adapters.menu.IconMenuAdapter;
import com.sakhawat.expense.tracker.adapters.allrecords.AllRecordsAdapter;
import com.sakhawat.expense.tracker.models.record.Record;
import com.sakhawat.expense.tracker.models.menu.IconPowerMenuItem;
import com.sakhawat.expense.tracker.utills.Tools;
import com.sakhawat.expense.tracker.utills.UX;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sakhawat.expense.tracker.utills.dbhelper.DatabaseHelper;
import com.skydoves.powermenu.CircularEffect;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import java.util.ArrayList;
import pl.droidsonroids.gif.GifImageView;

public class RecordsActivity extends AppCompatActivity {
    private FloatingActionButton addNew;
    private CustomPowerMenu powerMenu;
    private RecyclerView recyclerView;
    private TextView noBudgetData;
    private GifImageView noDataGif;
    private RelativeLayout mainLayout;
    private Toolbar toolbar;
    private DatabaseHelper databaseHelper;
    private Tools tools;
    private UX ux;
    private ArrayList<Record> allRecords;
    private AllRecordsAdapter allRecordsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        init();
        //Set toolbar
        ux.setToolbar(toolbar,RecordsActivity.this, DashboardActivity.class,"","");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_grey);
        }
        tools.setAnimation(mainLayout);

        // all the user interactions
        bindUiWithComponents();
    }

    //will init all the components and new instances
    private void init() {
        toolbar = findViewById(R.id.tool_bar);
        recyclerView = findViewById(R.id.mRecyclerView);
        noBudgetData = findViewById(R.id.NoData);
        noDataGif = findViewById(R.id.NoDataGif);
        addNew = findViewById(R.id.add);
        mainLayout = findViewById(R.id.mainLayout);
        databaseHelper = DatabaseHelper.getHelper(this);
        tools = new Tools(this);
        ux = new UX(this, mainLayout);
        allRecords = new ArrayList<>();
    }

    private void bindUiWithComponents() {
        setMenu();
        databaseHelper.getAllRecords("").observe(this, new Observer<ArrayList<Record>>() {
            @Override
            public void onChanged(ArrayList<Record> records) {
                if (records != null){
                    if (records.size() > 0){
                        allRecords = records;
                        setBudgetAdapter();
                    }
                    else {
                        noBudgetData.setVisibility(View.VISIBLE);
                        noDataGif.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        ItemTouchHelper.SimpleCallback itemCallBack = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                final Record record = allRecords.get(viewHolder.getAdapterPosition());
                ux.showDialog(R.layout.dialog_update_confirmation, "Delete Wallet information", new UX.onDialogOkListener() {
                    @Override
                    public void onClick() {
                        databaseHelper.deleteExpense(record.getId());
                        allRecords.remove(viewHolder.getAdapterPosition());
                        setBudgetAdapter();
                        allRecordsAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        new ItemTouchHelper(itemCallBack).attachToRecyclerView(recyclerView);
    }

    //setting the pop up menu
    private void setMenu() {
        powerMenu =new CustomPowerMenu.Builder<>(this, new IconMenuAdapter())
                .addItem(new IconPowerMenuItem(ContextCompat.getDrawable(this, R.drawable.ic_new_record), getResources().getString(R.string.add_new_record)))
                .addItem(new IconPowerMenuItem(ContextCompat.getDrawable(this, R.drawable.ic_wallet), getResources().getString(R.string.add_new_wallet)))
                .setAnimation(MenuAnimation.SHOW_UP_CENTER)
                .setMenuRadius(16f) // sets the corner radius.
                .setMenuShadow(16f) // sets the shadow.
                .setWidth(448)
                .setCircularEffect(CircularEffect.INNER)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                powerMenu.showAsAnchorCenter(mainLayout);
            }
        });
    }
    //menu set up end

    //popup menu click listener
    private OnMenuItemClickListener<IconPowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<IconPowerMenuItem>() {
        @Override
        public void onItemClick(int position, IconPowerMenuItem item) {
            if (position==0)startActivity(new Intent(RecordsActivity.this, AddNewRecordActivity.class).putExtra("from","record"));
            else if (position==1) startActivity(new Intent(RecordsActivity.this, AddNewWalletActivity.class).putExtra("from","record"));
            powerMenu.dismiss();
        }
    };
    //menu click listener end

    //set the budget list adapter
    private void setBudgetAdapter() {
        allRecordsAdapter = new AllRecordsAdapter(allRecords, this,new AllRecordsAdapter.onItemClick() {
            @Override
            public void itemClick(Record record) {
                startActivity(new Intent(RecordsActivity.this, AddNewRecordActivity.class).putExtra("record", record).putExtra("from","record"));
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(allRecordsAdapter);
        allRecordsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RecordsActivity.this, DashboardActivity.class));
        overridePendingTransition(R.anim.fadein,R.anim.push_up_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}

package com.dailysaver.shadowhite.dailysaver.fragments.addnewexpense;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dailysaver.shadowhite.dailysaver.DbConnector;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.adapters.DailyExpenseDashboardAdapter;
import com.dailysaver.shadowhite.dailysaver.models.expense.ExpenseDashboardModel;

import java.util.ArrayList;


public class Fragment_Expense_Dashboard extends Fragment {

    DbConnector dbConnector;
    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<ExpenseDashboardModel> dashboardDataList;
    private RecyclerView.LayoutManager layoutManager;
    private DailyExpenseDashboardAdapter dashboardAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        //setting the layout or the view
        View view= inflater.inflate(R.layout.dashboard_fragment, container, false);
        //initializing the attributes
//        datalistView=view.findViewById(R.id.listviewLog);
//        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayoutXml);
//        dbConnector=new DbConnector(context);
//        //calling the  method populateListview() in order to populate the listview with user data
//        populateListView();
//
//        //setting whenever a user swips what actions will happen
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeRefreshLayout.setRefreshing(false);
//                        populateListView();
//                        data.notifyDataSetChanged();
//                        datalistView.smoothScrollToPosition(0);
//                        Toast.makeText(context,"New record loaded.",Toast.LENGTH_SHORT).show();
//                    }
//                }, 2000);
//            }
//        });
        init(view);
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.dailyExpenseRecyclerView);
        dashboardDataList = new ArrayList<>();
        bindUIWithComponents();
    }

    private void bindUIWithComponents() {
        setAdapter();
    }

    private void setAdapter() {
        dashboardAdapter = new DailyExpenseDashboardAdapter(context,setData());
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dashboardAdapter);
        dashboardAdapter.notifyDataSetChanged();
    }

    private ArrayList<ExpenseDashboardModel> setData() {
        dashboardDataList.add(new ExpenseDashboardModel("Office transport expense","Transport",50));
        dashboardDataList.add(new ExpenseDashboardModel("Eat kolkata kacchi with friends","Food",260));
        dashboardDataList.add(new ExpenseDashboardModel("Family outing","Family",2000));
        return dashboardDataList;
    }

    //this methos will be used to populate the listview
//    public void populateListView(){
//        final ArrayList<ExpenseModel> expenseModels = new ArrayList<>(dbConnector.getAllContacts());
//        data=new DailyExpenseDashboardAdapter(context, expenseModels);
//        datalistView.setAdapter(data);
//    }


}

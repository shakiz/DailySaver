package com.dailysaver.shadowhite.dailysaver.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dailysaver.shadowhite.dailysaver.DbConnector;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.models.User;
import com.dailysaver.shadowhite.dailysaver.adapters.DataAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class Dashboard_fragment extends Fragment {

    //instance of dbconnector class
    DbConnector dbConnector;
    //for taking the current context
    Context context;
    //listview for list of user data
    ListView datalistView;
    //instance of data adapter class
    DataAdapter data;
    //a swiperefresh layout which will be use whenever an user swipe down tro refreshthe listview
    SwipeRefreshLayout swipeRefreshLayout;

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
        datalistView=view.findViewById(R.id.listviewLog);
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayoutXml);
        dbConnector=new DbConnector(context);
        //calling the  method populateListview() in order to populate the listview with user data
        populateListView();

        //setting whenever a user swips what actions will happen
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        populateListView();
                        data.notifyDataSetChanged();
                        datalistView.smoothScrollToPosition(0);
                        Toast.makeText(context,"New record loaded.",Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        return view;
    }

    //this methos will be used to populate the listview
    public void populateListView(){
        final ArrayList<User> users = new ArrayList<>(dbConnector.getAllContacts());
        data=new DataAdapter(context, users);
        datalistView.setAdapter(data);
    }


}

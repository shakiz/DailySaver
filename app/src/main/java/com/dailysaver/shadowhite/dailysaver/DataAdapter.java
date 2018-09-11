package com.dailysaver.shadowhite.dailysaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class DataAdapter extends ArrayAdapter<User>{

    //for getting the context
    Context context;
    //arraylist of information like savings amount , current amout or interest rate
    ArrayList<User> userData;

    //this is the constructor for passing the current context and the list of use data
    public DataAdapter(Context context, ArrayList<User> user){
        super(context, R.layout.list_item, user);
        this.context=context;
        this.userData = user;
    }
    //creating the class holder to hold the view
    public  class  Holder{
        //taking three textviews  in order to initialize with xml textviews
        TextView savings;
        TextView current;
        TextView interest;
        TextView time;
        TextView date;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        User data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            //initializing the textviews that we declared into the holder class
            viewHolder.savings = convertView.findViewById(R.id.txtView1);
            viewHolder.current = convertView.findViewById(R.id.txtView2);
            viewHolder.interest = convertView.findViewById(R.id.txtView3);
            viewHolder.time=convertView.findViewById(R.id.txtView4);
            viewHolder.date=convertView.findViewById(R.id.txtView5);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }
        //setting the text into those textviews
        viewHolder.savings.setText("Savings amount: "+data.get_savings());
        viewHolder.current.setText("Current amount: "+data.get_current());
        viewHolder.interest.setText("Interest rate: "+data.get_inerestRate());
        viewHolder.time.setText("Time of calculation :"+data.getTime());
        viewHolder.date.setText("Date of calculation :"+data.getDate());

        // Return the completed view to render on screen
        return convertView;
    }


}
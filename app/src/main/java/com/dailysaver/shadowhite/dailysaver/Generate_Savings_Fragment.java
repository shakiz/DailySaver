package com.dailysaver.shadowhite.dailysaver;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Generate_Savings_Fragment extends Fragment {
    //button for calculating the savings amount and current accoutn amount
    Button calculate;
    //editext holds the user input for savings and current accounts amount
    EditText earningAmount,interestRate;
    //for getting the context
    Context context;
    //textviews to set the savings and cuurent account amount after calculation
    TextView current,savings;
    //creating instance of dbconnector class
    DbConnector dbConnector;
    //taking variable for date and time
    String dateOdCalc,timeOfCalc;
    //taking some variables for calculations
    double doublleAmount,doubleInterest,doubleSavingsAmount,doubleCurrentAccountAmount;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.generate_savings_fragment, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        calculate=view.findViewById(R.id.button_generate_savings);
        interestRate=view.findViewById(R.id.amountOfInterestrate);
        earningAmount=view.findViewById(R.id.amountOfEarning);
        current=view.findViewById(R.id.currentTextview);
        savings=view.findViewById(R.id.savingsTextview);
        dbConnector=new DbConnector(context);
        bindUIWIthComponents();
    }

    private void bindUIWIthComponents() {
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (earningAmount.getText().toString().equals("") || interestRate.getText().toString().equals("")){
                    earningAmount.setError("Amount can not be empty.");
                    interestRate.setError("Interest rate can not be empty.");
                }else{

                    //calculating the savings amoutn and current account amount
                    doublleAmount=Double.parseDouble(earningAmount.getText().toString());
                    doubleInterest=Double.parseDouble(interestRate.getText().toString());
                    doubleSavingsAmount=doublleAmount*(doubleInterest/100);
                    doubleCurrentAccountAmount=doublleAmount-doubleSavingsAmount;

                    //getting the value for date and time
                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                    dateOdCalc = sdfDate.format(new Date());
                    SimpleDateFormat sdfTime=new SimpleDateFormat("h:mm a");
                    timeOfCalc = sdfTime.format(Calendar.getInstance().getTime());
                    //showing toast and setting the text on textviews
                    Toast.makeText(context,"Current :"+doubleCurrentAccountAmount+"\nSavings:"+doubleSavingsAmount+
                            "\nTime :"+timeOfCalc+"\nDate:"+dateOdCalc,Toast.LENGTH_SHORT).show();
                    savings.setText("Savings account amount : "+doubleSavingsAmount);
                    current.setText("Current account amount : "+doubleCurrentAccountAmount);
                    //inserting data into database
                    boolean bool=true;
                    try{
                        bool=dbConnector.insertData(doubleSavingsAmount,doubleCurrentAccountAmount,doubleInterest,timeOfCalc,dateOdCalc);
                    }catch(Exception e){
                        Log.d("ERROR IN FRAGMENT","ERROR : "+e.getMessage());
                    }
                    if (bool == true){
                        Toast.makeText(context,"Data inserted",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(context,"Data is not inserted",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


}

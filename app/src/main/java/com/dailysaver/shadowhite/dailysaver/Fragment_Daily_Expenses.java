package com.dailysaver.shadowhite.dailysaver;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Calendar;
import java.util.Locale;


public class Fragment_Daily_Expenses extends Fragment implements View.OnClickListener {
    private Spinner currencySpinner;
    private ArrayAdapter<String> spinnerAdapter;
    private RelativeLayout mainLayout;
    Button calculate;
    EditText earningAmount,interestRate , dateView;
    Context context;
    DbConnector dbConnector;
    String dateOdCalc,timeOfCalc;
    double doublleAmount,doubleInterest,doubleSavingsAmount,doubleCurrentAccountAmount;
    private SimpleDateFormat dateFormatter;

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
        mainLayout = view.findViewById(R.id.parent_container);
        interestRate=view.findViewById(R.id.amountOfInterestrate);
        earningAmount=view.findViewById(R.id.amountOfEarning);
        currencySpinner = view.findViewById(R.id.CurrencySpinner);
        dateView = view.findViewById(R.id.Date);
        dbConnector=new DbConnector(context);
        bindUIWIthComponents();
    }

    private void bindUIWIthComponents() {

        setSpinnerAdapter();
        dateView.setOnClickListener(this);


//        calculate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (earningAmount.getText().toString().equals("") || interestRate.getText().toString().equals("")){
//                    earningAmount.setError("Amount can not be empty.");
//                    interestRate.setError("Interest rate can not be empty.");
//                }else{
//
//                    //calculating the savings amoutn and current account amount
//                    doublleAmount=Double.parseDouble(earningAmount.getText().toString());
//                    doubleInterest=Double.parseDouble(interestRate.getText().toString());
//                    doubleSavingsAmount=doublleAmount*(doubleInterest/100);
//                    doubleCurrentAccountAmount=doublleAmount-doubleSavingsAmount;
//
//                    //getting the value for date and time
//                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
//                    dateOdCalc = sdfDate.format(new Date());
//                    SimpleDateFormat sdfTime=new SimpleDateFormat("h:mm a");
//                    timeOfCalc = sdfTime.format(Calendar.getInstance().getTime());
//                    //showing toast and setting the text on textviews
//                    Toast.makeText(context,"Current :"+doubleCurrentAccountAmount+"\nSavings:"+doubleSavingsAmount+
//                            "\nTime :"+timeOfCalc+"\nDate:"+dateOdCalc,Toast.LENGTH_SHORT).show();
//                    //inserting data into database
//                    boolean bool=true;
//                    try{
//                        bool=dbConnector.insertData(doubleSavingsAmount,doubleCurrentAccountAmount,doubleInterest,timeOfCalc,dateOdCalc);
//                    }catch(Exception e){
//                        Log.d("ERROR IN FRAGMENT","ERROR : "+e.getMessage());
//                    }
//                    if (bool == true){
//                        Toast.makeText(context,"Data inserted",Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Toast.makeText(context,"Data is not inserted",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });

    }

    private void setSpinnerAdapter() {
        spinnerAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_drop,new String[]{"BDT Tk.","US Dollar"});
        currencySpinner.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.notifyDataSetChanged();
    }

    private void getAndSetDate(final EditText editText){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        }
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    editText.setText(dateFormatter.format(newDate.getTime()));
                }
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Date:
                getAndSetDate(dateView);
                break;
        }
    }
}

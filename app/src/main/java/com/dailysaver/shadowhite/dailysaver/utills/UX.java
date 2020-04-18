package com.dailysaver.shadowhite.dailysaver.utills;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.dailysaver.shadowhite.dailysaver.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UX {
    private Context context;
    public Dialog loadingDialog;
    private ArrayAdapter<String> spinnerAdapter;
    private View view;

    public UX(Context context, View view) {
        this.context = context;
        loadingDialog = new Dialog(context);
        this.view = view;
    }

    public UX(Context context) {
        this.context = context;
    }

    /**
     * This method will set the Toolbar
     *
     * @param toolbar,from,to
     */
    public void setToolbar(Toolbar toolbar, final Activity from, final Class to, final String key, final String value){
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(key) && TextUtils.isEmpty(value)) context.startActivity(new Intent(from, to));
                else context.startActivity(new Intent(from, to).putExtra(key,value));
            }
        });
        //((AppCompatActivity) context).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow);
    }

    /**
     * This method will perform Loading view creator and cancel
     */
    public void getLoadingView(){
        loadingDialog.setContentView(R.layout.loading_layout);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    /**
     * This method will remove Loading view
     */
    public void removeLoadingView(){
        if (loadingDialog.isShowing()) loadingDialog.cancel();
    }

    /**
     * This method will perform Clear UI components
     */
    public void clearDetailsUI(int[] resIds){
        for (int resId : resIds){
            View child = view.findViewById(resId);
            if (child instanceof TextView){
                TextView textView = (TextView) child;
                textView.setText("");
            }
            else if(child instanceof EditText){
                EditText editText = (EditText) child;
                editText.setText("");
            }
        }
    }

    /**
     * This method will set spinner adapter
     */
    public void setSpinnerAdapter(ArrayList<String> dataSet, Spinner spinner) {
        spinnerAdapter = new ArrayAdapter<String>(context,R.layout.spinner_drop,dataSet);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.notifyDataSetChanged();
    }

    /**
     * This method will perform Spinner on change
     */
    public void onSpinnerChange(Spinner spinner, onSpinnerChangeListener listener) {
        final onSpinnerChangeListener customListener = listener;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customListener.onChange(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public interface onSpinnerChangeListener {
        void onChange(AdapterView<?> parent, View view, int position, long id);
    }
    //End spinner

    /**
     * This method will perform checkbox on change
     */
    public void onChange(CheckBox checkBox, final onChangeListener listener) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null) {
                    listener.onChange(isChecked);
                }
            }
        });
    }

    public interface onChangeListener {
        void onChange(boolean isChecked);
    }

    /**
     * This method will perform Date on click operation
     *
     * @param dateViewTXT
     */
    public void getAndSetDate(final TextView dateViewTXT){
        DateFormat dateFormatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        }
        Calendar newCalendar = Calendar.getInstance();
        final DateFormat finalDateFormatter = dateFormatter;
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    dateViewTXT.setText(finalDateFormatter.format(newDate.getTime()));
                }
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * This method will perform Validation of all view
     *
     * @params resIds and view
     */
    public boolean validation(int[] resIds){
        boolean valid = false;
        for (int resId : resIds){
            View child = view.findViewById(resId);

            if (child instanceof EditText){
                EditText editText = (EditText) child;
                if (editText.getText().toString().isEmpty()){
                    editText.setError(context.getResources().getString(R.string.invalid_input));
                    editText.requestFocus();
                    valid = false;
                }
                else{
                    valid = true;
                }
            }
            else if (child instanceof TextView){
                TextView textView = (TextView) child;
                if (textView.getText().toString().isEmpty()){
                    textView.setError(context.getResources().getString(R.string.check_your_data));
                    textView.requestFocus();
                    valid = false;
                }
                else{
                    if (textView.getText().toString().equals(context.getResources().getString(R.string.expiry_date))
                     || textView.getText().toString().equals(context.getResources().getString(R.string.date_hint))){
                        textView.setError(context.getResources().getString(R.string.select_valid_date));
                        textView.requestFocus();
                        valid = false;
                    }
                    else{
                        valid = true;
                    }
                }
            }
            else if (child instanceof Spinner){
                Spinner spinner = (Spinner) child;
                if (spinner.getSelectedItemPosition() == 0) {
                    TextView errorText = (TextView)spinner.getSelectedView();
                    errorText.setError(context.getResources().getString(R.string.select_correct_data));
                    errorText.setTextColor(context.getResources().getColor(R.color.md_red_400));
                    valid = false;
                }
                else {
                    valid = true;
                }
            }
            else if (child instanceof CheckBox){
                CheckBox checkBox = (CheckBox) child;
                if (!checkBox.isSelected()) {
                    checkBox.setError(context.getResources().getString(R.string.select_your_option));
                    valid = false;
                }
                else {
                    valid = true;
                }
            }
        }
        return valid;
    }

    /**
     * This method will perform background changing based on transaction type
     *
     * @param childResIds
     */
    public void changeUI(int[] childResIds,String transactionName) {
        for (int resId : childResIds){
            View child = view.findViewById(resId);
            if (transactionName.equals("Savings")){
                child.setBackground(context.getResources().getDrawable(R.drawable.rectangle_background_savings));
            }
            else{
                child.setBackground(context.getResources().getDrawable(R.drawable.rectangle_background_expense));
            }
        }
    }
    //endregion

    /**
     * This method will perform custom dialog functionality
     *
     * @param layout,title,okListener
     */
    public View showDialog(int layout, String title, onDialogOkListener okListener) {
        final onDialogOkListener onDialogOkListener = okListener;
        Button CancelButton , OkButton;
        TextView DialogMessage;
        final View dialogView = View.inflate(context, layout, null);

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(dialogView);
        dialog.setTitle(title);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        DialogMessage = dialogView.findViewById(R.id.DialogMessage);
        DialogMessage.setText(title);
        dialogView.findViewById(R.id.OkButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogOkListener.onClick(view, view.getId());
            }
        });

        dialogView.findViewById(R.id.CancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
            }
        });

        return dialogView;
    }

    public interface onDialogOkListener {
        void onClick(View dialog, int id);
    }
    //region end for custom dialog

}

package com.sakhawat.expense.tracker.utills;

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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.sakhawat.expense.tracker.R;
import com.sakhawat.expense.tracker.models.record.Record;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UX {
    private Context context;
    public Dialog loadingDialog;
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
    }

    /**
     * This method will set the Toolbar
     *
     * @param toolbar,from,to
     */
    public void setToolbar(Toolbar toolbar, final Activity from, final Class to, final String key, final Serializable value){
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(key) && value == null) context.startActivity(new Intent(from, to));
                else context.startActivity(new Intent(from, to).putExtra(key,value));
            }
        });
    }

    /**
     * This method will perform Loading view creator and cancel
     */
    public void getLoadingView(){
        loadingDialog.setContentView(R.layout.loading_layout);
        loadingDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        loadingDialog.getWindow().setDimAmount(0.6f);
        if (loadingDialog != null) {
            loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

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
                if (!TextUtils.isEmpty(editText.getTag().toString())){
                    if (editText.getTag().toString().equals("Date")){
                        editText.setHint(context.getResources().getString(R.string.date_hint));
                    }
                }
                else editText.setText("");
            }
            else if (child instanceof Spinner){
                Spinner spinner = (Spinner) child;
                spinner.setSelection(0);
            }
            else if (child instanceof RadioGroup){
                RadioGroup radioGroup = (RadioGroup) child;
                for (int start = 0; start < radioGroup.getChildCount(); start++) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(start);
                    if (radioButton.isChecked()){
                        radioButton.setChecked(false);
                    }
                }
            }
        }
    }

    /**
     * This method will set spinner adapter
     */
    public void setSpinnerAdapter(ArrayList<String> dataSet, Spinner spinner) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context,R.layout.spinner_drop,dataSet);
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
    public void showDialog(int layout, String title, onDialogOkListener okListener) {
        final onDialogOkListener onDialogOkListener = okListener;
        TextView DialogMessage;
        final View dialogView = View.inflate(context, layout, null);

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(dialogView);

        if (!TextUtils.isEmpty(title)) dialog.setTitle(title);
        if (dialog != null) dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();

        DialogMessage = dialogView.findViewById(R.id.DialogMessage);
        DialogMessage.setText(title);
        dialogView.findViewById(R.id.OkButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogOkListener.onClick();
                dialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.CancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
            }
        });
    }

    /**
     * This method will perform custom dialog functionality
     *
     * @param layout,title,okListener
     */
    public void showDialog(int layout, String title, onDialogCancelListener cancelListener, onDialogOkListener okListener) {
        final onDialogOkListener onDialogOkListener = okListener;
        final onDialogCancelListener onDialogCancelListener = cancelListener;
        TextView DialogMessage;
        final View dialogView = View.inflate(context, layout, null);

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(dialogView);

        if (!TextUtils.isEmpty(title)) dialog.setTitle(title);
        if (dialog != null) dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();

        DialogMessage = dialogView.findViewById(R.id.DialogMessage);
        DialogMessage.setText(title);
        dialogView.findViewById(R.id.OkButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogOkListener.onClick();
                dialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.CancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogCancelListener.onClick();
                dialog.dismiss();
                dialog.cancel();
            }
        });
    }

    public void showRecordDetailsDialog(int layout, Record record, onDialogOkListener okListener) {
        final onDialogOkListener onDialogOkListener = okListener;
        final View dialogView = View.inflate(context, layout, null);
        TextView WalletTitle,Category,RecordType,Amount,Note,ExpenseDate;

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(dialogView);
        WalletTitle = dialog.findViewById(R.id.WalletTitle);
        Category = dialog.findViewById(R.id.Category);
        RecordType = dialog.findViewById(R.id.RecordType);
        Amount = dialog.findViewById(R.id.Amount);
        Note = dialog.findViewById(R.id.Note);
        ExpenseDate = dialog.findViewById(R.id.ExpenseDate);

        if (dialog != null){
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            WalletTitle.setText(record.getWalletTitle());
            Category.setText(record.getCategory());
            RecordType.setText(record.getRecordType());
            Amount.setText(String.valueOf(record.getAmount()));
            Note.setText(record.getNote());
            ExpenseDate.setText(record.getExpenseDate());
        }

        dialog.show();

        dialogView.findViewById(R.id.OkButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogOkListener.onClick();
                dialog.dismiss();
            }
        });
    }

    public interface onDialogOkListener {
        void onClick();
    }

    public interface onDialogCancelListener {
        void onClick();
    }

    //region end for custom dialog

}

package com.dailysaver.shadowhite.dailysaver.utills;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.dailysaver.shadowhite.dailysaver.R;

public class UX {
    private Context context;
    public Dialog loadingDialog;

    public UX(Context context) {
        this.context = context;
        loadingDialog = new Dialog(context);
    }

    public void setToolbar(Toolbar toolbar, final Activity from, final Class to){
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(from, to));
            }
        });
        ((AppCompatActivity) context).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow);
    }

    public void getLoadingView(){
        loadingDialog.setContentView(R.layout.loading_layout);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    public void removeLoadingView(){
        if (loadingDialog.isShowing()) loadingDialog.cancel();
    }

    public boolean validation(int[] resIds,View view){
        boolean valid = false;
        for (int resId : resIds){
            EditText editText = view.findViewById(resId);
            if (editText.getText().toString().isEmpty()){
                editText.setError(context.getResources().getString(R.string.invalid_input));
                editText.requestFocus();
                valid = false;
            }
            else{
                valid = true;
            }
        }
        return valid;
    }

    public void clearDetailsUI(int[] resIds,View view){
        for (int resId : resIds){
            TextView textView = view.findViewById(resId);
            textView.setText("");
        }
    }

}

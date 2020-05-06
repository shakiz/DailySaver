package com.sakhawat.expense.tracker.utills;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.sakhawat.expense.tracker.R;

public class Tools {
    private Context context;

    public Tools(Context context) {
        this.context = context;
    }

    /**
     * This method will provide animation
     *
     * @param view
     */
    public void setAnimation(View view){
        Animation a = AnimationUtils.loadAnimation(context, R.anim.fadein);
        view.startAnimation(a);
    }

    /**
     * This method will exit the app
     */
    public void exitApp(){
        Intent exitIntent = new Intent(Intent.ACTION_MAIN);
        exitIntent.addCategory(Intent.CATEGORY_HOME);
        exitIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(exitIntent);
    }

    /**
     * This method will take to play store to rate
     */
    public void rateApp(){
        Uri uri = Uri.parse("market://details?id="+context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        //this will take back in our application if user press back button from playStore
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try{
            context.startActivity(goToMarket);
        }
        catch (ActivityNotFoundException ex){
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }
}

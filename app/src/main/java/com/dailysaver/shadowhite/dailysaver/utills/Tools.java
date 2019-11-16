package com.dailysaver.shadowhite.dailysaver.utills;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.dailysaver.shadowhite.dailysaver.R;

public class Tools {
    private Context context;

    public Tools(Context context) {
        this.context = context;
    }

    public void setAnimation(View view){
        Animation a = AnimationUtils.loadAnimation(context, R.anim.fadein);
        view.startAnimation(a);
    }
}

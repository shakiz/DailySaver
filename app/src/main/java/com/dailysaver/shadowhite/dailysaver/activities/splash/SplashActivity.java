package com.dailysaver.shadowhite.dailysaver.activities.splash;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import com.dailysaver.shadowhite.dailysaver.R;
import com.dailysaver.shadowhite.dailysaver.activities.dashboard.DashboardActivity;

public class SplashActivity extends AppCompatActivity {
    private RelativeLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();

        //load animation with activity UI
        Animation a = AnimationUtils.loadAnimation(this, R.anim.fadein);
        linearLayout.startAnimation(a);

        // all the user interactions
        bindUIWithComponents();
    }

    //will init all the components and new instances
    private void init() {
        linearLayout = findViewById(R.id.mainLayout);
    }

    private void bindUIWithComponents() {
        //handler to start the dashboard activity after 1.5sec
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
            }
        }, 1500);

    }
}

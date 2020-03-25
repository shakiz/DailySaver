package com.dailysaver.shadowhite.dailysaver.utills;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.dailysaver.shadowhite.dailysaver.R;
import java.util.Date;
import static com.dailysaver.shadowhite.dailysaver.utills.dbhelper.DBColumns.COLUMN_EXPENSE_DATE;

public class Tools {
    private Context context;

    public Tools(Context context) {
        this.context = context;
    }

    public void setAnimation(View view){
        Animation a = AnimationUtils.loadAnimation(context, R.anim.fadein);
        view.startAnimation(a);
    }

    public static Long persistDate(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    public static Date loadDate(Cursor cursor) {
        return new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_EXPENSE_DATE)));
    }
}

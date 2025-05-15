package ph.edu.usc.skillboost.utils;

import android.content.Context;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ph.edu.usc.skillboost.R;

public class Utilities {
    public static void setViewPadding(View curView){
        if (curView != null){
            final int originalLeft = curView.getPaddingLeft();
            final int originalTop = curView.getPaddingTop();
            final int originalRight = curView.getPaddingRight();
            final int originalBottom = curView.getPaddingBottom();

            ViewCompat.setOnApplyWindowInsetsListener(curView, (v, insets) -> {
                Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

                v.setPadding(
                    originalLeft + bars.left,
                    originalTop + bars.top,
                    originalRight + bars.right,
                    originalBottom + bars.bottom
                );

                return insets;
            });
        }
    }

    public static int getDrawableFromRes(Context context, String imageRes){
        if (imageRes == null){
            imageRes = "placeholder";
        }

        int imageResId = context.getResources().getIdentifier(imageRes, "drawable", context.getPackageName());

        if (imageResId == 0) {
            imageResId = R.drawable.placeholder;
        }

        return imageResId;
    }
    public static int dpToPx(android.content.Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

}

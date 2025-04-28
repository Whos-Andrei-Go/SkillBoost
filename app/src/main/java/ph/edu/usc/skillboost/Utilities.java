package ph.edu.usc.skillboost;

import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Utilities {
    public static void setViewPadding(View curView){
        ViewCompat.setOnApplyWindowInsetsListener(curView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            int currentPaddingLeft = v.getPaddingLeft();
            int currentPaddingTop = v.getPaddingTop();
            int currentPaddingRight = v.getPaddingRight();
            int currentPaddingBottom = v.getPaddingBottom();

            int newPaddingLeft = currentPaddingLeft + systemBars.left;
            int newPaddingTop = currentPaddingTop + systemBars.top;
            int newPaddingRight = currentPaddingRight + systemBars.right;
            int newPaddingBottom = currentPaddingBottom + systemBars.bottom;

            v.setPadding(newPaddingLeft, newPaddingTop, newPaddingRight, newPaddingBottom);

            return insets;
        });
    }
}

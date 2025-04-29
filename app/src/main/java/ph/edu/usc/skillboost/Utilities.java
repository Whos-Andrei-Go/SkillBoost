package ph.edu.usc.skillboost;

import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
}

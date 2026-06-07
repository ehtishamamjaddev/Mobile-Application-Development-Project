package com.auiskillbridge.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.auiskillbridge.R;

/**
 * Custom TextView for category list items in Explore tab.
 * Intercepts category emoji strings set from CategoryAdapter (e.g., "💻")
 * and replaces them with a high-quality vector icon drawable in compound drawables,
 * clearing the emoji text so only the clean icon is rendered.
 */
public class CategoryIconView extends AppCompatTextView {

    public CategoryIconView(Context context) {
        super(context);
    }

    public CategoryIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CategoryIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text != null && text.length() > 0) {
            String emoji = text.toString().trim();
            int iconRes = 0;

            switch (emoji) {
                case "💻":
                    iconRes = R.drawable.ic_code;
                    break;
                case "🤖":
                    iconRes = R.drawable.ic_cpu;
                    break;
                case "⚡":
                    iconRes = R.drawable.ic_bolt;
                    break;
                case "📐":
                    iconRes = R.drawable.ic_drafting;
                    break;
                case "📊":
                    iconRes = R.drawable.ic_math;
                    break;
                case "🎨":
                    iconRes = R.drawable.ic_palette;
                    break;
                case "🗄️":
                case "🗄":
                    iconRes = R.drawable.ic_storage;
                    break;
                case "💼":
                    iconRes = R.drawable.ic_work;
                    break;
            }

            if (iconRes != 0) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), iconRes);
                if (drawable != null) {
                    // Fit inside bounds of the icon container
                    setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    // Clear the emoji text so it doesn't overlap
                    super.setText("", type);
                    // Reset paddings and gravity to center the compound drawable
                    setGravity(android.view.Gravity.CENTER);
                    return;
                }
            }
        }
        super.setText(text, type);
    }
}

package com.auiskillbridge.views;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Custom TextView that automatically filters out emojis and replaces them
 * with professional typography symbols (e.g., system star emoji ⭐ -> ★ star glyph).
 */
public class CleanTextView extends AppCompatTextView {

    public CleanTextView(Context context) {
        super(context);
    }

    public CleanTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CleanTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text != null) {
            String filtered = text.toString()
                    .replace("⭐", "★")
                    .replace("👋", "")
                    .replace("🎓", "")
                    .replace("💡", "")
                    .replace("🔍", "")
                    .replace("📚", "")
                    .replace("😄", "")
                    .replace("❤️", "");
            super.setText(filtered, type);
        } else {
            super.setText(text, type);
        }
    }
}

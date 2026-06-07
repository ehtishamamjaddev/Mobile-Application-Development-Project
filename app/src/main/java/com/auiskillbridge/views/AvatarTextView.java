package com.auiskillbridge.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

import com.auiskillbridge.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom TextView for Avatars in AUSkillBridge.
 * Circularly crops and renders profile photos dynamically for all 12 mentors:
 * - "EA" -> Ehtisham Amjad (Team)
 * - "MA" -> M Abdullah (Team)
 * - "AM" -> Aman Mir (Team)
 * - "AS" -> Ayesha Siddiqui
 * - "BC" -> Bilal Chaudhry
 * - "MI" -> Mahnoor Iqbal
 * - "ZA" -> Zain ul Abideen
 * - "SM" -> Sana Mirza
 * - "AR" -> Ahmed Raza
 * - "HB" -> Hira Baig
 * - "TM" -> Talha Mehmood
 * - "RZ" -> Rimsha Zahid
 * Falls back to the standard initials and background circle if no image is mapped.
 */
public class AvatarTextView extends AppCompatTextView {

    private static final Map<String, Bitmap> bitmapCache = new HashMap<>();

    private Paint maskPaint;
    private String currentInitials = "";
    private final RectF mDstRect = new RectF();
    private final Rect mSrcRect = new Rect();

    public AvatarTextView(Context context) {
        super(context);
        init();
    }

    public AvatarTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AvatarTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        currentInitials = text != null ? text.toString().trim().toUpperCase() : "";
    }

    private static synchronized Bitmap getAvatarBitmap(Context context, String initials) {
        if (initials == null || initials.isEmpty()) return null;

        if (bitmapCache.containsKey(initials)) {
            return bitmapCache.get(initials);
        }

        int resId = 0;
        switch (initials) {
            case "EA":
                resId = R.drawable.user_profile_img;
                break;
            case "MA":
                resId = R.drawable.abdullah_profile_img;
                break;
            case "AM":
                resId = R.drawable.aman_profile_img;
                break;
            case "AS":
                resId = R.drawable.img_ayesha;
                break;
            case "BC":
                resId = R.drawable.img_bilal;
                break;
            case "MI":
                resId = R.drawable.img_mahnoor;
                break;
            case "ZA":
                resId = R.drawable.img_zain;
                break;
            case "SM":
                resId = R.drawable.img_sana;
                break;
            case "AR":
                resId = R.drawable.img_ahmed;
                break;
            case "HB":
                resId = R.drawable.img_hira;
                break;
            case "TM":
                resId = R.drawable.img_talha;
                break;
            case "RZ":
                resId = R.drawable.img_rimsha;
                break;
        }

        if (resId == 0) return null;

        try {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), resId);
            if (bmp != null) {
                bitmapCache.put(initials, bmp);
                return bmp;
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap targetBitmap = getAvatarBitmap(getContext(), currentInitials);

        if (targetBitmap != null) {
            int width = getWidth();
            int height = getHeight();
            if (width > 0 && height > 0) {
                // Create canvas layer for mask compositing
                int saveCount = canvas.saveLayer(0, 0, width, height, null);

                // Draw destination: circular mask
                float radius = Math.min(width, height) / 2f;
                canvas.drawCircle(width / 2f, height / 2f, radius, maskPaint);

                // Draw source: user image with center crop scaling
                mSrcRect.set(0, 0, targetBitmap.getWidth(), targetBitmap.getHeight());
                
                // Calculate center-crop coordinates
                float scale;
                float dx = 0, dy = 0;
                if (targetBitmap.getWidth() * height > width * targetBitmap.getHeight()) {
                    scale = (float) height / (float) targetBitmap.getHeight();
                    dx = (width - targetBitmap.getWidth() * scale) * 0.5f;
                } else {
                    scale = (float) width / (float) targetBitmap.getWidth();
                    dy = (height - targetBitmap.getHeight() * scale) * 0.5f;
                }

                mDstRect.set(dx, dy, dx + targetBitmap.getWidth() * scale, dy + targetBitmap.getHeight() * scale);

                Paint imagePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
                imagePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(targetBitmap, mSrcRect, mDstRect, imagePaint);

                canvas.restoreToCount(saveCount);
                return;
            }
        }
        super.onDraw(canvas);
    }
}

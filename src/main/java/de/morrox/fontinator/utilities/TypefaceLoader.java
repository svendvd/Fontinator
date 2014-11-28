package de.morrox.fontinator.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.util.LruCache;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import de.morrox.fontinator.R;


/**
 * Created by sven on 11.08.14.
 */
public class TypefaceLoader {
    public static enum TRANSFORM {
        NONE(0),
        UPPERCASE(1),
        LOWERCASE(2);

        private final int value;
        private static Map<Integer, TRANSFORM> map = new HashMap<Integer, TRANSFORM>();

        TRANSFORM(int value) {
            this.value = value;
        }

        static TRANSFORM findByValue(int value) {
            return map.get(value);
        }

        static {
            for(TRANSFORM state : values()) map.put(state.value, state);
        }
    }

    public static final float NO_LETTER_SPACE = -9999;

    private TextView view;
    private float letterSpace = NO_LETTER_SPACE;
    private TRANSFORM textTransform = null;
    private boolean isHtml = false;

    public TypefaceLoader(TextView view, Context context, AttributeSet attrs) {
        this.view = view;
        setTypeFace(context, attrs);
    }

    private static LruCache<String, Typeface> sTypefaceCache = new LruCache<String, Typeface>(12);

    public static Typeface get(Context context, String typefaceName) {
        try {
            Typeface typeface = sTypefaceCache.get(typefaceName);
            if (typeface == null) {
                typeface = Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s", typefaceName));
                // Cache the Typeface object
                sTypefaceCache.put(typefaceName, typeface);
            }
            return typeface;
        } catch(Exception e) {
            return null;
        }
    }

    private void setTypeFace(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Typefaceable, 0, 0);
        try {
            isHtml        = a.getBoolean(R.styleable.Typefaceable_html, false);
            letterSpace   = a.getFloat(R.styleable.Typefaceable_letterSpace, NO_LETTER_SPACE);
            textTransform = TRANSFORM.findByValue(a.getInt(R.styleable.Typefaceable_textTransform, TRANSFORM.NONE.value));

            String typefaceName = a.getString(R.styleable.Typefaceable_font);
            if (typefaceName != null && !TextUtils.isEmpty(typefaceName)) {
                Typeface typeface = TypefaceLoader.get(context, typefaceName);
                if (!view.isInEditMode()) {
                    view.setTypeface(typeface);
                }
                view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
            }
        } finally {
            a.recycle();
        }

        createLetterSpacing(view.getText());
    }

    public void createLetterSpacing(CharSequence src){

        switch (textTransform){
            case LOWERCASE:
                src = src.toString().toLowerCase();
            case UPPERCASE:
                src = src.toString().toUpperCase();

        }
        if(letterSpace != NO_LETTER_SPACE && src != null) {
            final int srcLength = src.length();
            if (srcLength > 1) {
                final String nonBreakingSpace = "\u00A0";
                final SpannableStringBuilder builder = src instanceof SpannableStringBuilder
                        ? (SpannableStringBuilder) src : new SpannableStringBuilder((isHtml) ? Html.fromHtml(src.toString()) : src);
                for (int i = src.length() - 1; i >= 1; i--) {
                    builder.insert(i, nonBreakingSpace);
                    builder.setSpan(new ScaleXSpan(letterSpace), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                view.setAllCaps(false);
                view.setText(builder, TextView.BufferType.SPANNABLE);
            }
        }else if(src != null){
            if(isHtml){
                view.setText(Html.fromHtml(src.toString()), TextView.BufferType.SPANNABLE);
            }else if(textTransform != null && textTransform != TRANSFORM.NONE) {
                view.setText(src, TextView.BufferType.SPANNABLE);
            }
        }
    }
}

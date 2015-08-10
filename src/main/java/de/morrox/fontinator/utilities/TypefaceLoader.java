package de.morrox.fontinator.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.util.LruCache;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import de.morrox.fontinator.R;


public class TypefaceLoader {
    public enum TRANSFORM {
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

    private final WeakReference<TextView> view;

    private float letterSpace = NO_LETTER_SPACE;
    private TRANSFORM textTransform = null;
    private boolean isHtml = false;

    public TypefaceLoader(TextView view, Context context, AttributeSet attrs) {
        this.view = new WeakReference<TextView>(view);
        takeTypeface(context, attrs);
    }

    private static LruCache<String, Typeface> sTypefaceCache = new LruCache<String, Typeface>(12);

    public static TypefaceLoader get(TextView view, Context context, AttributeSet attrs){
        return new TypefaceLoader(view, context, attrs);
    }


    private static Typeface getTypeface(Context context, String typefaceName) {
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

    public static Pair<CharSequence, TextView.BufferType> inject(TypefaceLoader typefaceLoader, CharSequence src, TextView.BufferType type) {
        if(typefaceLoader == null){
            return new Pair<CharSequence, TextView.BufferType>(src, type);
        }else{
            return typefaceLoader.createLetterSpacing(src, type);
        }
    }

    public void setFont(String font) {
        setTypeface(font);
    }

    private void takeTypeface(Context context, AttributeSet attrs) {

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Typefaceable, 0, 0);
        try {
            isHtml        = a.getBoolean(R.styleable.Typefaceable_html, false);
            letterSpace   = a.getFloat(R.styleable.Typefaceable_letterSpace, NO_LETTER_SPACE);
            textTransform = TRANSFORM.findByValue(a.getInt(R.styleable.Typefaceable_textTransform, TRANSFORM.NONE.value));

            String typefaceName = a.getString(R.styleable.Typefaceable_font);
            setTypeface(typefaceName);
        } finally {
            a.recycle();
        }

        TextView view = this.view.get();
        Pair<CharSequence, TextView.BufferType> pair = createLetterSpacing(view.getText(), TextView.BufferType.NORMAL);

        // Typeface is null!
        // Because its called from Constructor!!
        // So its a super Method call!!!
        view.setText(pair.first, pair.second);
    }

    private void setTypeface(String typefaceName) {
        TextView view = this.view.get();
        if (typefaceName != null && !TextUtils.isEmpty(typefaceName)) {
            Typeface typeface = TypefaceLoader.getTypeface(view.getContext(), typefaceName);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
    }

    public Pair<CharSequence, TextView.BufferType> createLetterSpacing(CharSequence src, TextView.BufferType type){
        TextView view = this.view.get();

        switch (textTransform){
            case LOWERCASE:
                src = src.toString().toLowerCase();
                break;
            case UPPERCASE:
                src = src.toString().toUpperCase();
                break;

        }
        if (letterSpace != NO_LETTER_SPACE && src != null) {
            final int srcLength = src.length();
            if (srcLength > 1) {
                final String nonBreakingSpace = "\u00A0";
                final SpannableStringBuilder builder = src instanceof SpannableStringBuilder
                        ? (SpannableStringBuilder) src : new SpannableStringBuilder((isHtml) ? Html.fromHtml(src.toString()) : src);
                for (int i = src.length() - 1; i >= 1; i--) {
                    builder.insert(i, nonBreakingSpace);
                    builder.setSpan(new ScaleXSpan(letterSpace), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if(Build.VERSION.SDK_INT >= 14) {
                    view.setAllCaps(false);
                }
                view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                return new Pair<CharSequence, TextView.BufferType>(builder, TextView.BufferType.SPANNABLE);
            }
        }else if(src != null){
            if (isHtml) {

                view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                return new Pair<CharSequence, TextView.BufferType>( Html.fromHtml(src.toString()), TextView.BufferType.SPANNABLE);

            }else if(textTransform != null && textTransform != TRANSFORM.NONE) {

                view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                return new Pair<CharSequence, TextView.BufferType>( src, TextView.BufferType.SPANNABLE);

            }
        }
        return new Pair<CharSequence, TextView.BufferType>(src, type);
    }
}

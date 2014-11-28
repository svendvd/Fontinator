package de.morrox.fontinator;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextClock;

import de.morrox.fontinator.utilities.TypefaceLoader;
import de.morrox.fontinator.utilities.Typefaceable;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class FontTextClock extends TextClock implements Typefaceable {
    private TypefaceLoader typefaceLoader;
    public FontTextClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        typefaceLoader = new TypefaceLoader(this, context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if(type != BufferType.SPANNABLE && typefaceLoader != null){
            typefaceLoader.createLetterSpacing(text);
        }
    }
}

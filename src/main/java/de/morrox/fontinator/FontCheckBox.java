package de.morrox.fontinator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

import de.morrox.fontinator.utilities.TypefaceLoader;



public class FontCheckBox extends CheckBox {
    private TypefaceLoader typefaceLoader;
    public FontCheckBox(Context context, AttributeSet attrs) {
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

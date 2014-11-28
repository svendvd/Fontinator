Fontinator
==========

Fontinator is a custom fontable Android-Widgets Libary

### Version
  1.0

### How to Use:

1. Add fonts to the assets/fonts folder

2. Replace Android Widgets in Layout XML with the Fontinator Widgets

  For Example simply replace:
  ```xml
  <TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textAllCaps="true"
    android:textStyle="bold"
    android:text="@string/hello_world"
    android:textColor="@android:color/black"
    ... />
  ```
  whit this XML Code
  ```xml
  <de.morrox.fontinator.FontTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    font="My Custom Font Bold.otf"
    textTransform="uppercase"
    letterSpace="1.4"
    android:text="@string/hello_world"
    android:textColor="@android:color/black"
    ...
    />
  ```
    Used font file from /assets/fonts/ (use splited fonts without android:textStyle=bold!)
  ```xml
    font="My Custom Font Bold.otf"
  ```
    Replace textAllCaps=true with textTransform=uppercase
  ```xml
    textTransform="uppercase"
  ```
    [Optional] Add LatterSpace
  ```xml
    letterSpace="1.4"
  ```

3. Enjoy Fonted Widgets

### Issues
Please Note that die Layout Editor currently can't Preview Custom Fonts

### Make Custom Widgets from TextView
Option 1, simply extend from FontTextView

Option 2, Use TypefaceLoader and Typefaceable Interface
```java
import de.morrox.fontinator.utilities.TypefaceLoader;
import de.morrox.fontinator.utilities.Typefaceable;

public class MyCustomFontButton extends Button implements Typefaceable{
    private TypefaceLoader typefaceLoader;
    public FontButton(Context context, AttributeSet attrs) {
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

    ```



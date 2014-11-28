Fontinator
==========

![alt tag](http://morrox.de/fontinator/mascot_animated.gif)

Fontinator is an Android-Library that make it simply esay to user coustom Fonts.

### Version
  1.0

### How to Use:

1. Add fonts to the /assets/fonts folder of your Android Studio Project

2. Import this Project as Module

3. add compile project(':fontinator') to your Application Gradle file
```
dependencies {
    compile project(':fontinator')
    ...
}
```

4. Replace Android Widgets in Layout XML with the Fontinator Widgets

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


    Set font file from /assets/fonts/ (use splited fonts without android:textStyle=bold!)
  ```xml
    font="My Custom Font Bold.otf"
  ```
    Replace textAllCaps=true with
  ```xml
    textTransform="uppercase"
  ```
    [Optional] Add LetterSpace
  ```xml
    letterSpace="1.4"
  ```

5. Enjoy Fonted Widgets

### How it works

Fontinator Extend TextView based the Android Widgets like the Button and Inject a Fontface loader

### Issues
Please Note that the Android Layout Editor currently can't Preview Custom Fonts

### Make Custom Widgets from a TextView based Widget
Option 1: simply extend

Option 2: Use TypefaceLoader and Typefaceable Interface
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


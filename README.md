Fontinator
==========

![alt tag](http://morrox.de/fontinator/mascot_animated.gif)

Fontinator is an Android-Library that make it simply easy to use custom Fonts.

### Version
  1.1.3

### How to Use:

##### 1. Add fonts to the /assets/fonts folder of your Android Studio Project

##### 2. add compile 'de.morrox.fontinator:Fontinator:1.1.3' to your Application Gradle file
```
dependencies {
    compile 'de.morrox.fontinator:Fontinator:1.1.3'
    ...
}
```

##### 3. add xmlns:app to the Layout XML root

```xml
xmlns:app="http://schemas.android.com/apk/res-auto"
```

##### 4. Replace Android Widgets in Layout XML with the Fontinator Widgets

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
  app:font="My Custom Font Bold.otf"
  app:textTransform="uppercase"
  app:letterSpace="1.4"
  android:text="@string/hello_world"
  android:textColor="@android:color/black"
  ...
  />
```

  To set font file from /assets/fonts/ (use splited fonts without android:textStyle!)
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

##### 5. Enjoy Fonted Widgets

### How it works

Fontinator extend TextView based Android Widgets like the Button to inject a Cached Fontface loader

### Issues
Please Note that the Android Layout Editor currently can't Preview Custom Fonts

### Make Custom Widgets from a TextView based Widget
Option 1: simply extend

Option 2: Use TypefaceLoader and Typefaceable Interface

```java

    import de.morrox.fontinator.utilities.TypefaceLoader;
    import de.morrox.fontinator.utilities.Typefaceable;
    
    public class MyCustomFontView extends Button implements Typefaceable{
        private TypefaceLoader typefaceLoader;
        public MyCustomFontView(Context context, AttributeSet attrs) {
            super(context, attrs);
            typefaceLoader = TypefaceLoader.get(this, context, attrs);
        }
    
        @Override
        public void setText(CharSequence text, BufferType type) {
            Pair<CharSequence, BufferType> pair = TypefaceLoader.inject(typefaceLoader, text, type);
            super.setText(pair.first, pair.second);
        }
        
        public void setFont(String font) {
            typefaceLoader.setFont(font);
        }
    
        public void setFont(@StringRes int font) {
            typefaceLoader.setFont(getResources().getString(font));
        }
    }

```


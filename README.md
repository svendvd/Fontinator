Fontinator
==========

Fontinator is a custom fontable Android-Widgets Libary

How to Use
----------

1. Add fonts to the assets/fonts folder

2. Replace Android Widgets in the XML white die Fontinator Widgets

  For Example simply replace:
  ```
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
  ```
  <de.morrox.fontinator.FontTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    font="My Custom Font Bold.otf"   //Use bold font whitout android:textStyle="bold"
    textTransform="uppercase"        //Replace a textAllCaps=true whit textTransform=uppercase
    letterSpace="1.4"                //[Optional] Add LatterSpace
    android:text="@string/hello_world"
    android:textColor="@android:color/black"
    ...
    />
  ```

  3. Enjoy Fonted Widgets

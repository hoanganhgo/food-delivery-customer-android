package com.hcmus.fit.customer_apps.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.hcmus.fit.customer_apps.R;

public class WidgetUtil {
    public static TextView getOptionTitle(Context context) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        textView.setText("Size");
        textView.setTextSize(18f);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_black);
        textView.setTypeface(typeface);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

    public static RadioGroup getOptionGroup(Context context) {
        RadioGroup radioGroup = new RadioGroup(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        radioGroup.setLayoutParams(params);
        return radioGroup;
    }

    public static RadioButton getRadioButton(Context context) {
        RadioButton radioButton = new RadioButton(context);
        radioButton.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        radioButton.setText("Small");
        radioButton.setTextSize(16f);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_regular);
        radioButton.setTypeface(typeface);
        radioButton.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd(15);
        radioButton.setLayoutParams(params);

        return radioButton;
    }

    public static CheckBox getCheckBox(Context context) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        checkBox.setText("Thach");
        checkBox.setTextSize(16f);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_regular);
        checkBox.setTypeface(typeface);
        checkBox.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd(15);
        checkBox.setLayoutParams(params);
        return checkBox;
    }
}

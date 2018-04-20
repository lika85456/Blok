package com.lika85456.lika85456.blokusdeskgame.Views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lika85456.lika85456.blokusdeskgame.R;

public class CircleNumberView extends RelativeLayout {

    private TextView textView;
    private String text;
    private int color;

    public CircleNumberView(Context context) {
        super(context);


    }

    public CircleNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.CircleNumberView);
        CharSequence textAttr = arr.getString(R.styleable.CircleNumberView_text);
        Integer colorAttr = arr.getInteger(R.styleable.CircleNumberView_backgroundColor, 0xFFFF0000);

        setColor(colorAttr);

        if (textAttr != null) {
            setText(textAttr.toString());
        }
        arr.recycle();  // Do this when done
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.textView.layout(l, t, r, b);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        this.setBackgroundResource(R.drawable.block);
        this.getBackground().setColorFilter(color & 0xCCFFFFFF, PorterDuff.Mode.MULTIPLY);
        this.getBackground().mutate();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (this.textView == null) {
            this.textView = new TextView(this.getContext());
            this.textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
            LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            this.textView.setTextSize(20f);
            this.addView(textView, params);
        }
        this.text = text;
        this.textView.setText(text);
    }

}

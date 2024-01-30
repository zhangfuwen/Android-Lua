package cc.chenhe.lib.androidlua.demo.chatbox;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.chenhe.lib.androidlua.demo.R;

class BasicMessageView extends LinearLayout {
    TextView m_text;

    public void setText(String text) {
        m_text.setText(text);
    }

    public BasicMessageView(Context context) {
        super(context);
        this.setOrientation(LinearLayout.HORIZONTAL);
//        this.setGravity(Gravity.RIGHT);
        m_text = new TextView(this.getContext());
        m_text.setBackgroundColor(Color.RED);
//        m_text.setWidth(200);
        this.addView(m_text);
        this.setPadding(8,8,8,8);

//        this.setBackgroundColor(Color.BLUE);

    }

    public BasicMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setOrientation(LinearLayout.HORIZONTAL);
//        this.setGravity(Gravity.RIGHT);
        m_text = new TextView(this.getContext());
        m_text.setBackgroundColor(Color.RED);
//        m_text.setWidth(200);
        this.addView(m_text);
        this.setPadding(8,8,8,8);
//        this.setBackgroundColor(Color.BLUE);
    }
}
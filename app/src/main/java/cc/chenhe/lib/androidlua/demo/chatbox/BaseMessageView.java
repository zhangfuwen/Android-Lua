package cc.chenhe.lib.androidlua.demo.chatbox;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.chenhe.lib.androidlua.demo.R;

class BasicMessageView extends LinearLayout {
    TextView m_text;
    BasicChatMessage m_msg;

    public void setMessage(BasicChatMessage msg) {
        m_msg = msg;
        m_text.setText(m_msg.text);
    }

    public BasicMessageView(Context context) {
        super(context);
        this.setOrientation(LinearLayout.HORIZONTAL);
        m_text = new TextView(this.getContext());
        m_text.setBackgroundColor(Color.WHITE);
        this.addView(m_text);
        this.setPadding(8,8,8,8);
        m_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        this.setBackgroundColor(Color.LTGRAY);


    }

    public BasicMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setOrientation(LinearLayout.HORIZONTAL);
        m_text = new TextView(this.getContext());
        m_text.setBackgroundColor(Color.WHITE);
        this.addView(m_text);
        this.setPadding(8,8,8,28);
        m_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        this.setBackgroundColor(Color.LTGRAY);
    }
}
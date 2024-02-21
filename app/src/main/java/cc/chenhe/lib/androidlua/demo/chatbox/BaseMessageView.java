package cc.chenhe.lib.androidlua.demo.chatbox;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import cc.chenhe.lib.androidlua.demo.R;
import io.noties.markwon.Markwon;

class BasicMessageView extends LinearLayout {
    TextView m_text;
    BasicChatMessage m_msg;

    View customView = null;

    public void setCustomView(View view) {
        customView = view;
        if (customView != null) {
            this.removeView(m_text);
            addView(view);
        }
    }

    public void setMessage(BasicChatMessage msg) {
        m_msg = msg;
        final Markwon markwon = Markwon.create(this.getContext());

// set markdown
        markwon.setMarkdown(m_text, m_msg.text);

        m_text.setPadding(40,40,40,40);
        if(msg.side == BasicChatMessage.Side.LEFT) {
            m_text.setBackground(getDrawableWithRadius(Color.parseColor("#eeeeee")));
            this.setPadding(8,8,40,40);
        } else {
            m_text.setBackground(getDrawableWithRadius(Color.parseColor("#aaff1f")));
            this.setPadding(40,8,8,40);
        }


//        m_text.setText(m_msg.text);
    }

    private Drawable getDrawableWithRadius(int color) {

        GradientDrawable gradientDrawable   =   new GradientDrawable();
        gradientDrawable.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }

    public BasicMessageView(Context context) {
        super(context);
        this.setOrientation(LinearLayout.HORIZONTAL);
        m_text = new TextView(this.getContext());
        m_text.setBackgroundColor(Color.WHITE);
        this.addView(m_text);
        this.setPadding(8,8,8,8);
        m_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        this.setBackgroundColor(Color.WHITE);


    }

    public BasicMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setOrientation(LinearLayout.HORIZONTAL);
        m_text = new TextView(this.getContext());
        m_text.setBackgroundColor(Color.WHITE);
        this.addView(m_text);
        this.setPadding(8,8,8,28);
        m_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        this.setBackgroundColor(Color.WHITE);
    }
}
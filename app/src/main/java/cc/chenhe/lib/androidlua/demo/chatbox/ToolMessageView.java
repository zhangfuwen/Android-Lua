package cc.chenhe.lib.androidlua.demo.chatbox;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.noties.markwon.Markwon;


public class ToolMessageView extends BasicMessageView {
    private TextView m_code = new TextView(getContext());

    public static class ToolMessage extends BasicChatMessage {
        public String code="";
        public String result=null;
    }
    public static class ToolResultEvent {
        public ToolResultEvent(String res) {
            result = res;
        }
        public String result;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ToolResultEvent event) {
        Log.e("toolrun", "got event:" + event.result);
        ToolMessage msg = (ToolMessage)m_msg;
        msg.result = event.result;
        TextView tv = new TextView(this.getContext());
        tv.setText(msg.result);
        this.addView(tv);
        EventBus.getDefault().unregister(this);
    }

        public Button button = new Button(this.getContext());
    @Override
    public void setMessage(BasicChatMessage msg) {
        super.setMessage(msg);

        ToolMessage toolMessage = (ToolMessage) msg;

        final Markwon markwon = Markwon.create(this.getContext());

// set markdown
        markwon.setMarkdown(m_code, "```lua\n"+toolMessage.code+"\n```\n");

    }

    public ToolMessageView(Context context) {
        super(context);
        EventBus.getDefault().register(this);
//        this.setBackgroundColor(Color.RED);
        this.setOrientation(LinearLayout.VERTICAL);
        this.addView(m_code);
        button.setText("Run");
        this.addView(button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ToolMessage msg = (ToolMessage)m_msg;
                Log.e("toolrun", "tool run " + msg.code);
                EventBus.getDefault().post(msg.code);
            }
        });
    }

    public ToolMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        EventBus.getDefault().register(this);
//        this.setBackgroundColor(Color.RED);
        this.setOrientation(LinearLayout.VERTICAL);
        this.addView(m_code);
        button.setText("Run");
        this.addView(button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ToolMessage msg = (ToolMessage)m_msg;
                Log.e("toolrun", "tool run " + msg.code);
                EventBus.getDefault().post(msg.code);
            }
        });
    }
}

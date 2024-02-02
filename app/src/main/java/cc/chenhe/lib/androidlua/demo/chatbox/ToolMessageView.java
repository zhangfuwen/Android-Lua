package cc.chenhe.lib.androidlua.demo.chatbox;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ToolMessageView extends BasicMessageView {

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
        ToolMessage msg = (ToolMessage)m_msg;
        msg.result = event.result;
        TextView tv = new TextView(this.getContext());
        tv.setText(msg.result);
        this.addView(tv);
    }

        public Button button = new Button(this.getContext());
    @Override
    public void setMessage(BasicChatMessage msg) {
        super.setMessage(msg);

    }

    public ToolMessageView(Context context) {
        super(context);
        button.setText("Run");
        this.addView(button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ToolMessage msg = (ToolMessage)m_msg;
                EventBus.getDefault().post(msg.code);
            }
        });
    }

    public ToolMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        button.setText("Run");
        this.addView(button);
    }
}

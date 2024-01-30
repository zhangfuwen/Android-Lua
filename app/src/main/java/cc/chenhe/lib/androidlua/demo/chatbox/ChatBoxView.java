package cc.chenhe.lib.androidlua.demo.chatbox;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ChatBoxView extends LinearLayout {


    public void setMessages(ArrayList<String> list) {
        for(int i = 0; i < list.size();i++) {
            BasicMessageView horizontal = new BasicMessageView(this.getContext());
            horizontal.setText(list.get(i));
//            horizontal.setOrientation(LinearLayout.HORIZONTAL);
//            Button button = new Button(this);
//            button.setText("test button");
//            button.setWidth(200);
//            horizontal.addView(button);
            if (i%2==1) {
                horizontal.setGravity(Gravity.LEFT);
            } else {
                horizontal.setGravity(Gravity.RIGHT);
            }
            this.addView(horizontal);
        }
    }
    public ChatBoxView(Context context) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
    }
}

package cc.chenhe.lib.androidlua.demo.chatbox;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import fun.xjbcode.llamaqwen.NativeLib;

import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import fun.xjbcode.glm4.ChatAPI;

@SuppressLint("SetTextI18n")
public class ChatBoxActivity extends AppCompatActivity {

    String TAG="ChatBoxActivity";
    LinearLayout m_layout = null;

    private final ChatAPI chatapi = new ChatAPI();

    EditText editText = null;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ToolMessageView.ToolResultEvent event) {
        Log.e("toolrun", "got event:" + event.result);
        if (editText!= null) {
            editText.setText("tool response:\n"+ event.result);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        LinearLayout m_layout = new LinearLayout(this);
        m_layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(m_layout);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        ArrayList<String> list = new ArrayList<>();
        list.add(".MainActivity");
        list.add(".MainActivity");
        list.add("To use the animation system, whenever a ");
        list.add(".MainActivity");
        list.add(".MainActivity");
        list.add(".MainActivity");
        list.add(".MainActivity");
        list.add("To use the animation system, whenever a property changes what affects your view's appearance, don't change the property directly. Instead, use ValueAnimator to make the change. In the following example, modifying the selected child component in the view makes the entire rendered view rotate so that the selection pointer is centered. ValueAnimator changes the rotation over a period of several hundred milliseconds, rather than immediately setting the new rotation value");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add(".MainActivity");
        list.add(".MainActivity");
        list.add(".MainActivity");
        list.add("To use the animation system, whenever a property changes what affects your view's appearance, don't change the property directly. Instead, use ValueAnimator to make the change. In the following example, modifying the selected child component in the view makes the entire rendered view rotate so that the selection pointer is centered. ValueAnimator changes the rotation over a period of several hundred milliseconds, rather than immediately setting the new rotation value");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");
        list.add("To use the animation system, whenever a ");

        ChatBoxView chatBoxView = new ChatBoxView(this);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params1.weight = 1.0f;
        chatBoxView.setLayoutParams(params1);
        m_layout.addView(chatBoxView);
        for(int i=0;i < list.size(); i++) {
            BasicChatMessage msg = new BasicChatMessage();
            msg.text = list.get(i);
            msg.side = i%2==0? BasicChatMessage.Side.LEFT: BasicChatMessage.Side.RIGHT;
            chatBoxView.addMessage(msg);
        }

        // add add text box
        LinearLayout textEditRow = new LinearLayout(this);
        textEditRow.setOrientation(LinearLayout.HORIZONTAL);
        m_layout.addView(textEditRow);
        textEditRow.setGravity(Gravity.BOTTOM);

        editText = new EditText(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1.0f;
        editText.setLayoutParams(params);
        textEditRow.addView(editText);

        Button sendButton = new Button(this);
        sendButton.setText("Send");
        textEditRow.addView(sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                BasicChatMessage msg = new BasicChatMessage();
                msg.text = "user:" + text;
                msg.side= BasicChatMessage.Side.RIGHT;
                chatBoxView.addMessage(msg);
                editText.setText("");

                BasicChatMessage m_msg1 = new BasicChatMessage();
                m_msg1.side= BasicChatMessage.Side.LEFT;
                m_msg1.text = "placeholder";
                chatBoxView.addMessage(m_msg1);
                chatapi.setCallback(new ChatAPI.ChatCallback() {
                    String m_msg="";
                    @Override
                    public void onFailure(String msg) {
                        Log.e("xxx", "failure:"+msg);
                        m_msg1.text=msg;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                chatBoxView.updateLastMessage();;
                            }
                        });

                    }

                    @Override
                    public void onResponse(String msg) {
                        Log.e(TAG, "toolrun response:"+msg);
                        m_msg = msg;
                        m_msg1.side = BasicChatMessage.Side.LEFT;
                        m_msg1.text = msg;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                chatBoxView.updateLastMessage();
                            }
                        });

                    }

                    @Override
                    public void onFinish() {
                        Log.e("xxx", "finish");

                    }

                    @Override
                    public void onLua(String script) {
//                        EventBus.getDefault().post(script);
                        Log.e(TAG, "toolrun onLua(script): " + script);
                        ToolMessageView.ToolMessage msg = new ToolMessageView.ToolMessage();
                        msg.side= BasicChatMessage.Side.LEFT;
                        msg.text = "# 工具调用";
                        msg.code = script;
//                        chatBoxView.removeLastMessage();
                        chatBoxView.addMessage(msg);
//                        chatBoxView.replaceLastMessage(msg);
//                        chatBoxView.updateLastMessage();
                    }
                });
                chatapi.Chat(msg.text);

            }
        });
    }



}

package cc.chenhe.lib.androidlua.demo.chatbox;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

@SuppressLint("SetTextI18n")
public class ChatBoxActivity extends AppCompatActivity {

    LinearLayout m_layout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout m_layout = new LinearLayout(this);
        m_layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(m_layout);

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
        chatBoxView.setMessages(list);
        m_layout.addView(chatBoxView);

    }

}

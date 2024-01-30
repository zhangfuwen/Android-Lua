package cc.chenhe.lib.androidlua.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

@SuppressLint("SetTextI18n")
public class ButtonListActivity extends AppCompatActivity {

    LinearLayout m_layout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout m_layout = new LinearLayout(this);
        m_layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(m_layout);

        ArrayList<String> list = new ArrayList<>();
        list.add(".MainActivity");


        Button button = new Button(this);
        button.setText("test button");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("xxx", "xxxxxx");
                Toast.makeText(ButtonListActivity.this, "button clicked", Toast.LENGTH_LONG).show();
            }
        });
        m_layout.addView(button);

    }

}

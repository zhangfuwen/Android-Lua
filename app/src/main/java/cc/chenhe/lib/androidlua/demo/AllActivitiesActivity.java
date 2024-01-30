package cc.chenhe.lib.androidlua.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@SuppressLint("SetTextI18n")
public class AllActivitiesActivity extends AppCompatActivity {

//    ListView m_listView = null;
    LinearLayout m_layout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_activities);
//        m_listView = findViewById(R.id.activity_list);
        m_layout = findViewById(R.id.linearlayout);

        ArrayList<String> list= new ArrayList<>();
        list.add(".MainActivity");
        list.add(".ButtonListActivity");


        for(String item : list) {
            Button button = new Button(this);
            button.setText(item);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("xxx", "xxxxxx");
                    try {
                        Class<?> c = Class.forName(this.getClass().getPackage().getName() + item);
                        Intent intent = new Intent(AllActivitiesActivity.this, c);
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.e("xxx", e.toString());
                    }
                }
            });
            m_layout.addView(button);
        }

//        ArrayAdapter<String> arr;
//        arr = new ArrayAdapter<String>(this, R.layout.generic_button, R.id.button, list);
//        m_listView.setAdapter(arr);
//        for(int i = 0; i < list.size(); i++) {
//            Button but = (Button) m_listView.getChildAt(i);
//            final int j = i;
//            but.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try {
//                        Class<?> c = Class.forName(list.get(j));
//                        Intent intent = new Intent(AllActivitiesActivity.this, c);
//                        startActivity(intent);
//                    } catch (Exception e) {}
//                }
//            });
//
//        }

//        for(String item : list) {
//            Button button = new Button(this);
//            button.setText(item);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try {
//                        Class<?> c = Class.forName(item);
//                        Intent intent = new Intent(AllActivitiesActivity.this, c);
//                        startActivity(intent);
//                    } catch (Exception e) {}
//                }
//            });
//            m_listView.addView(button);
//        }
    }

}

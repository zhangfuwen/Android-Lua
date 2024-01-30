package cc.chenhe.lib.androidlua.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
public class MainActivity extends AppCompatActivity {

    class Wrapper {
        public void showToast(Context context, String text) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    private LuaState lua = null;
    private TextView textView;
    private TextView textView1;
    private Spinner spinner;
    private ArrayList<String> codeSnippets = new ArrayList<String>();
    private Wrapper w = new Wrapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);
        textView1 = findViewById(R.id.textView);
        spinner = findViewById(R.id.spinner);


        codeSnippets.add("return 'hello'");
        codeSnippets.add("return obj:getCacheDir():toString()");
        codeSnippets.add("return w.showToast(obj, 'hello')");

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, codeSnippets);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = adapterView.findViewById(android.R.id.text1);
                textView1.setText(tv.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lua = LuaStateFactory.newLuaState();
        if (lua == null) {
            textView.setText("newLuaState false");
            return;
        }
        lua.openLibs();
        lua.pushJavaObject(this);
        lua.setGlobal("obj");
        lua.pushJavaObject(w);
        lua.setGlobal("w");
        lua.LdoString(readAssetsTxt(this, "test.lua"));

        StringBuilder s = new StringBuilder();
        findViewById(R.id.btnGetV1).setOnClickListener(view -> {
            lua.getGlobal("v1");
            textView.setText(lua.toString(-1));
            lua.pop(1);
        });
        findViewById(R.id.btnSetV2).setOnClickListener(view -> {
            lua.pushString("value from java");
            lua.setGlobal("v2");
        });
        findViewById(R.id.btnGetV2).setOnClickListener(view -> {
            lua.getGlobal("v2");
            textView.setText(lua.toString(-1));
            lua.pop(1);
        });
        findViewById(R.id.btnInjectJavaFun).setOnClickListener(view -> new MyJavaFunction(lua).register());
        textView1.setText("return getTime(' - passing by lua')");
        findViewById(R.id.btnCallInjectedFun).setOnClickListener(view -> {
            Toast.makeText(this, textView1.getText(), Toast.LENGTH_SHORT).show();
            String code = textView1.getText().toString();
            lua.LdoString(code);
            textView.setText(lua.toString(-1));
            if(!codeSnippets.contains(code)) {
                codeSnippets.add(code);
            }
            if (codeSnippets.size()> 10) {
                // keep last 10
                codeSnippets = (ArrayList<String>) codeSnippets.subList(codeSnippets.size() - 10, codeSnippets.size());
            }
            lua.pop(1);
        });
        findViewById(R.id.btnGetLuaTable).setOnClickListener(view -> {
            lua.getGlobal("table");
            s.delete(0, s.length());
            if (lua.isTable(-1)) {
                lua.pushNil();
                while (lua.next(-2) != 0) {
                    s.append(lua.toString(-2)).append(" = ")
                            .append(lua.toString(-1)).append("\n");
                    lua.pop(1);
                }
                lua.pop(1);
                textView.setText(s.toString());
            }
        });
        findViewById(R.id.btnSetLuaTable).setOnClickListener(view -> {
            lua.newTable();
            lua.pushString("from");
            lua.pushString("java");
            lua.setTable(-3);
            lua.pushString("value");
            lua.pushString("Hello lua");
            lua.setTable(-3);
            lua.setGlobal("table");
        });
        findViewById(R.id.btnCallLua).setOnClickListener(view -> {
            s.delete(0, s.length());
            lua.getGlobal("extreme");
            lua.pushNumber(15.6);
            lua.pushNumber(0.8);
            lua.pushNumber(189);
            lua.pcall(3, 2, 0);
            s.append("max:").append(lua.toString(-2)).append(" min:").append(lua.toString(-1));
            textView.setText(s.toString());
            lua.pop(2);
        });
        findViewById(R.id.btnInjectJavaObj).setOnClickListener(view -> {
            lua.pushJavaObject(textView);
            lua.setGlobal("textView");
            lua.pushJavaObject(this);
            lua.setGlobal("obj");
            lua.pushInteger(Color.RED);
            lua.setGlobal("red");
            lua.LdoString("textView:setTextColor(red)");
        });
        findViewById(R.id.btnLuaCallback).setOnClickListener(view -> {
            textView.setText("Loading...");
            new AsyncJavaFunction(lua).register();
            lua.getGlobal("luaCallback");
            lua.pushJavaObject(textView);
            lua.pcall(1, 0, 0);
        });

    }

    public static String readAssetsTxt(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "err";
    }
}

package cc.chenhe.lib.androidlua.demo;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import cc.chenhe.lib.androidlua.demo.chatbox.ToolMessageView;

public class MyApplication extends Application {

    private LuaState lua = null;
    @Override
    public void onCreate() {
        super.onCreate();
        lua = LuaStateFactory.newLuaState();
        lua.openLibs();
        lua.pushJavaObject(this);

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String script) {
        System.out.println("toolrun lua.LdoString: " + script);
        lua.LdoString(script);
        String out = lua.toString(-1);
        EventBus.getDefault().post(new ToolMessageView.ToolResultEvent(out));
        System.out.println("toolrun lua output :" + out);
    }
}

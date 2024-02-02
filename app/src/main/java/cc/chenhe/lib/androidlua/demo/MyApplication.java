package cc.chenhe.lib.androidlua.demo;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

public class MyApplication extends Application {

    private LuaState lua = null;
    @Override
    public void onCreate() {
        super.onCreate();
        lua = LuaStateFactory.newLuaState();

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String script) {
        lua.LdoString(script);
        String out = lua.toString(-1);
        System.out.println("lua output :" + out);
    }
}

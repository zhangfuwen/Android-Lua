package fun.xjbcode.llamaqwen;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        Log.e("xxx", new NativeLib().stringFromJNI());
        assertEquals(true, new NativeLib().stringFromJNI().startsWith("Hello"));
    }
}
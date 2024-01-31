package fun.xjbcode.llamaqwen;

public class NativeLib {

    // Used to load the 'llamaqwen' library on application startup.
    static {
        System.loadLibrary("llamaqwen");
    }

    /**
     * A native method that is implemented by the 'llamaqwen' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
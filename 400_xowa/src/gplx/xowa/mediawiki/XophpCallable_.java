package gplx.xowa.mediawiki;

public class XophpCallable_ {
    // REF.PHP: https://www.php.net/manual/en/function.call-user-func-array.phphttps://www.php.net/manual/en/function.call-user-func-array.php
    public static Object call_user_func_array(XophpCallable callback, XophpArray param_arr) {
        return callback.Owner().Callback(callback.MethodName(), param_arr);
    }
}

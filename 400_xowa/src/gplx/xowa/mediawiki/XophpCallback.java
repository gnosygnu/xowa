/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.mediawiki;

public class XophpCallback {
    public XophpCallback(XophpCallbackOwner owner, String methodName) {
        this.owner = owner;
        this.methodName = methodName;
    }
    public XophpCallbackOwner Owner() {return owner;} private final XophpCallbackOwner owner;
    public String MethodName() {return methodName;} private final String methodName;
    public Object Call(Object arg) {
        return owner.Call(methodName, arg);
    }
    public Object Call(Object arg0, Object arg1) {
        return owner.Call(methodName, arg0, arg1);
    }

    // REF.PHP: https://www.php.net/manual/en/function.call-user-func-array.php
    public static Object call_user_func_array(XophpCallback callback, XophpArray param_arr) {
        return callback.Owner().Call(callback.MethodName(), param_arr);
    }
}

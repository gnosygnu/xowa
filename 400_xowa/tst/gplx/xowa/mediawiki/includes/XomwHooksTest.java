package gplx.xowa.mediawiki.includes;

import gplx.core.tests.Gftest;
import gplx.xowa.mediawiki.XophpArray;
import gplx.xowa.mediawiki.XophpArray__tst;
import gplx.xowa.mediawiki.XophpCallback;
import gplx.xowa.mediawiki.XophpCallbackOwner;
import org.junit.Before;
import org.junit.Test;

public class XomwHooksTest {
    private XomwHooksTestCallbackOwner callbackOwner;

    @Before
    public void setUp() throws Exception {
        callbackOwner = new XomwHooksTestCallbackOwner();
        XomwHooks.clearAll();
    }

    @Test
    public void isRegistered() {
        Gftest.Eq__bool_n(XomwHooks.isRegistered("test1"));
    }

    @Test
    public void register() {
        Gftest.Eq__bool_n(XomwHooks.isRegistered("test1"));
        XomwHooks.register("test1", callbackOwner.NewCallback("test1"));
        Gftest.Eq__bool_y(XomwHooks.isRegistered("test1"));
    }

    @Test
    public void clear() {
        Gftest.Eq__bool_n(XomwHooks.isRegistered("test1"));
        XomwHooks.register("test1", callbackOwner.NewCallback("test1"));
        Gftest.Eq__bool_y(XomwHooks.isRegistered("test1"));
        XomwHooks.clear("test1");
        Gftest.Eq__bool_y(XomwHooks.isRegistered("test1"));
    }

    @Test
    public void getHandlers() {
        XomwHooks.register("test1", callbackOwner.NewCallback("test1a"));
        XomwHooks.register("test1", callbackOwner.NewCallback("test1b"));
        XomwHooks.register("test2", callbackOwner.NewCallback("test2"));
        XophpArray handlers = XomwHooks.getHandlers("test1");
        Gftest.Eq__ary
        ( new String[] {"test1a", "test1b"}
        , extractKeysFromCallbackAry(handlers)
        );
    }

    @Test
    public void run() {
        XomwHooks.register("test1", callbackOwner.NewCallback("test1a"));
        XomwHooks.register("test1", callbackOwner.NewCallback("test1b"));
        XomwHooks.register("test2", callbackOwner.NewCallback("test2"));

        Gftest.Eq__bool_y(XomwHooks.run("test1", XophpArray.New(1, 2, 3)));

        Gftest.Eq__str("test1a:3;test1b:3", callbackOwner.Result());
    }

    private static String[] extractKeysFromCallbackAry(XophpArray callbacks) {
        int len = callbacks.Len();
        String[] rv = new String[len];
        for (int i = 0; i < len; i++) {
            XophpCallback callback = (XophpCallback)callbacks.Get_at(i);
            rv[i] = callback.MethodName();
        }
        return rv;
    }
}
class XomwHooksTestCallbackOwner implements XophpCallbackOwner {
    public String Result() {return result;} private String result = "";
    @Override
    public Object Call(String method, Object... args) {
        result += method + ":" + (args == null ? -1 : ((XophpArray)args[0]).count()) + ";";
        return null; // NOTE: XomwHooks throws error if non-null
    }
}
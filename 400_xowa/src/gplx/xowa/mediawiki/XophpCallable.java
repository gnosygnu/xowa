package gplx.xowa.mediawiki;

public class XophpCallable {
    public XophpCallable(XophpCallableOwner owner, String methodName) {
        this.owner = owner;
        this.methodName = methodName;
    }
    public XophpCallableOwner Owner() {return owner;} private final XophpCallableOwner owner;
    public String MethodName() {return methodName;} private final String methodName;
}

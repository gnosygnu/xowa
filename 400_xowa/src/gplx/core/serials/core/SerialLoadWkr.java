package gplx.core.serials.core;

public interface SerialLoadWkr {
    SerialLoadWkr Ctor(SerialLoadMgr mgr);
    void Init(byte[] src, int cur);
    int LoadInt();
    byte[] LoadBry();
    String LoadStr();
}

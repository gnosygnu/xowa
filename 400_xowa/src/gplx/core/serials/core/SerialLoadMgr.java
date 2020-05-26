package gplx.core.serials.core;

public interface SerialLoadMgr {
    int CoreVersion();
    int DataVersion();
    SerialLoadWkr NewLoadWkr();
    void ReadHeader(byte[] data);
    byte[] Data();
    int HeaderEnd();
}

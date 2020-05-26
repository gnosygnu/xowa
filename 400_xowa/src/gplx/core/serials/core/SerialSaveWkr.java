package gplx.core.serials.core;

public interface SerialSaveWkr {
    SerialSaveWkr Ctor(SerialSaveMgr mgr);
    void Init();
    void SaveHdr(int dataVersion);
    void SaveInt(int val);
    void SaveStr(String val);
    void SaveBry(byte[] val);
    byte[] ToBry();
}

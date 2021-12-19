package gplx.core.serials.core;
import gplx.core.serials.binarys.BinaryLoadMgr;
import gplx.core.serials.binarys.BinarySaveMgr;
import gplx.core.texts.Base64Converter;
import gplx.types.errs.ErrUtl;
public class SerialCoreFactory {
    public static SerialSaveMgr NewSaveMgr(int coreVersion) {
        if (coreVersion == BinaryLoadMgr.CORE_VERSION) {
            return new BinarySaveMgr();
        }
        throw ErrUtl.NewUnhandled(coreVersion);
    }
    public static SerialLoadMgr NewLoadMgr(byte[] data) {
        int coreVersion = Base64Converter.GetIndexInt((char)data[0]);
        if (coreVersion == BinaryLoadMgr.CORE_VERSION) {
            BinaryLoadMgr loadMgr = new BinaryLoadMgr();
            loadMgr.ReadHeader(data);
            return loadMgr;
        }
        throw ErrUtl.NewUnhandled(coreVersion);
    }
}

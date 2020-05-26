package gplx.core.serials.core;

import gplx.Err_;
import gplx.core.serials.binarys.BinaryLoadMgr;
import gplx.core.serials.binarys.BinarySaveMgr;
import gplx.core.texts.Base64Converter;

public class SerialCoreFactory {
    public static SerialSaveMgr NewSaveMgr(int coreVersion) {
        if (coreVersion == BinaryLoadMgr.CORE_VERSION) {
            return new BinarySaveMgr();
        }
        throw Err_.new_unhandled_default(coreVersion);
    }
    public static SerialLoadMgr NewLoadMgr(byte[] data) {
        int coreVersion = Base64Converter.GetIndexInt((char)data[0]);
        if (coreVersion == BinaryLoadMgr.CORE_VERSION) {
            BinaryLoadMgr loadMgr = new BinaryLoadMgr();
            loadMgr.ReadHeader(data);
            return loadMgr;
        }
        throw Err_.new_unhandled_default(coreVersion);
    }
}

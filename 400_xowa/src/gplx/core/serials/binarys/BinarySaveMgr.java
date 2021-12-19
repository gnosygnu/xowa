package gplx.core.serials.binarys;

import gplx.types.basics.utls.BryUtl;
import gplx.core.serials.core.SerialSaveMgr;
import gplx.core.serials.core.SerialSaveWkr;

public class BinarySaveMgr implements SerialSaveMgr {
    public int CoreVersion() {return BinaryLoadMgr.CORE_VERSION;}
    public byte[] FldDlm() {return fldDlm;} private final byte[] fldDlm = BryUtl.NewA7("|");
    public byte[] RowDlm() {return rowDlm;} private final byte[] rowDlm = BryUtl.NewA7("\n");

    @Override
    public SerialSaveWkr NewSaveWkr() {
        return new BinarySaveWkr().Ctor(this);
    }
}

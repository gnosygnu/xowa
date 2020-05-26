package gplx.core.serials.binarys;

import gplx.Bry_;
import gplx.core.serials.core.SerialSaveMgr;
import gplx.core.serials.core.SerialSaveWkr;

public class BinarySaveMgr implements SerialSaveMgr {
    public int CoreVersion() {return BinaryLoadMgr.CORE_VERSION;}
    public byte[] FldDlm() {return fldDlm;} private final byte[] fldDlm = Bry_.new_a7("|");
    public byte[] RowDlm() {return rowDlm;} private final byte[] rowDlm = Bry_.new_a7("\n");

    @Override
    public SerialSaveWkr NewSaveWkr() {
        return new BinarySaveWkr().Ctor(this);
    }
}

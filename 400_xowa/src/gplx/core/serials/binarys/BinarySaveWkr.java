package gplx.core.serials.binarys;

import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.core.serials.core.SerialSaveMgr;
import gplx.core.serials.core.SerialSaveWkr;
import gplx.core.texts.Base64Converter;

public class BinarySaveWkr implements SerialSaveWkr {
    private byte[] fldDlm;
    private byte[] rowDlm;
    private BinarySaveMgr mgr;
    private final Bry_bfr bfr = Bry_bfr_.New();

    @Override
    public SerialSaveWkr Ctor(SerialSaveMgr mgrObj) {
        this.mgr = (BinarySaveMgr)mgrObj;
        this.fldDlm = mgr.FldDlm();
        this.rowDlm = mgr.RowDlm();
        return this;
    }

    @Override
    public void Init() {
        bfr.Clear();
    }

    @Override
    public void SaveHdr(int dataVersion) {
        // EX: AA -> SerialCoreMgrID+DataVersionID
        bfr.Add_byte((byte)Base64Converter.GetIndexChar(mgr.CoreVersion()));
        bfr.Add_byte((byte)Base64Converter.GetIndexChar(dataVersion)).Add(rowDlm);
    }

    @Override
    public void SaveInt(int val) {
        SaveInt(val, rowDlm);
    }

    @Override
    public void SaveStr(String val) {
        this.SaveBry(Bry_.new_u8(val));
    }

    @Override
    public void SaveBry(byte[] val) {
        SaveInt(val.length, fldDlm);
		bfr.Add(val).Add(rowDlm);
    }

    private void SaveInt(int val, byte[] dlm) {
		bfr.Add_int_variable(val).Add(dlm);
    }

    @Override
	public byte[] ToBry() {
	    return bfr.To_bry();
	}
}

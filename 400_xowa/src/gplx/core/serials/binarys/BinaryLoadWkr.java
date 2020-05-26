package gplx.core.serials.binarys;

import gplx.Bry_;
import gplx.Bry_find_;
import gplx.Err_;
import gplx.String_;
import gplx.core.serials.core.SerialLoadMgr;
import gplx.core.serials.core.SerialLoadWkr;

public class BinaryLoadWkr implements SerialLoadWkr {
    private BinaryLoadMgr mgr;
    private byte[] fldDlm;
    private byte[] rowDlm;
    private byte[] src;
    private int srcLen;
    private int cur;

    @Override
    public SerialLoadWkr Ctor(SerialLoadMgr mgrObj) {
        this.mgr = (BinaryLoadMgr)mgrObj;
        this.fldDlm = mgr.FldDlm();
        this.rowDlm = mgr.RowDlm();
        return this;
    }

    @Override
    public void Init(byte[] src, int cur) {
        this.src = src;
        this.srcLen = src.length;
        this.cur = cur;
    }

    @Override
    public String LoadStr() {
        return String_.new_u8(LoadBry());
    }

    @Override
    public int LoadInt() {
        return LoadInt(rowDlm);
    }

    @Override
    public byte[] LoadBry() {
	    int bryLen = LoadInt(fldDlm);

        // extract bry
	    int bryBgn = cur;
	    int bryEnd = bryBgn + bryLen;
	    if (bryEnd > srcLen) {
	        throw Err_.new_wo_type(String_.Format("End position is out of bounds; src={0} currentPosition={1} byteArrayLength={2} remainder={3}", src, cur, bryLen, Bry_.Mid(src, bryBgn, bryEnd)));
	    }
	    cur = bryEnd + rowDlm.length;

	    return Bry_.Mid(src, bryBgn, bryEnd);
    }

    private int LoadInt(byte[] dlm) {
        int bgn = cur;

        // find position of delimiter
	    int end = Bry_find_.Find_fwd(src, dlm, cur, srcLen);
	    if (end == Bry_find_.Not_found) {
	        throw Err_.new_wo_type(String_.Format("Failed to find delimiter for integer's end position; src={0} currentPosition={1} delimiter={2}", src, cur, fldDlm));
	    }
	    cur = end + dlm.length;

        // parse int
	    int val = Bry_.To_int_or(src, bgn, end, -1);
	    if (val == -1) {
	        throw Err_.new_wo_type(String_.Format("Failed to parse integer; src={0} currentPosition={1} string={2}", src, cur, Bry_.Mid(src, bgn, end)));
	    }
	    return val;
    }
}

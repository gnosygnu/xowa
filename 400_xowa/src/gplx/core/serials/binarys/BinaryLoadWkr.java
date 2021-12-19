package gplx.core.serials.binarys;
import gplx.core.serials.core.SerialLoadMgr;
import gplx.core.serials.core.SerialLoadWkr;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.StringUtl;

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
        return StringUtl.NewU8(LoadBry());
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
	        throw ErrUtl.NewArgs(StringUtl.Format("End position is out of bounds; src={0} currentPosition={1} byteArrayLength={2} remainder={3}", src, cur, bryLen, BryLni.Mid(src, bryBgn, bryEnd)));
	    }
	    cur = bryEnd + rowDlm.length;

	    return BryLni.Mid(src, bryBgn, bryEnd);
    }

    private int LoadInt(byte[] dlm) {
        int bgn = cur;

        // find position of delimiter
	    int end = BryFind.FindFwd(src, dlm, cur, srcLen);
	    if (end == BryFind.NotFound) {
	        throw ErrUtl.NewArgs(StringUtl.Format("Failed to find delimiter for integer's end position; src={0} currentPosition={1} delimiter={2}", src, cur, fldDlm));
	    }
	    cur = end + dlm.length;

        // parse int
	    int val = BryUtl.ToIntOr(src, bgn, end, -1);
	    if (val == -1) {
	        throw ErrUtl.NewArgs(StringUtl.Format("Failed to parse integer; src={0} currentPosition={1} string={2}", src, cur, BryLni.Mid(src, bgn, end)));
	    }
	    return val;
    }
}

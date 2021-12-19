package gplx.types.basics.encoders;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.IntUtl;
import org.junit.Test;
public class XoHexUtlTest {
	private final XoHexUtlTstr tstr = new XoHexUtlTstr();
	@Test public void Write_bfr() {
		tstr.TestWriteBfr(BoolUtl.Y,              0, "0");
		tstr.TestWriteBfr(BoolUtl.Y,             15, "f");
		tstr.TestWriteBfr(BoolUtl.Y,             16, "10");
		tstr.TestWriteBfr(BoolUtl.Y,             32, "20");
		tstr.TestWriteBfr(BoolUtl.Y,            255, "ff");
		tstr.TestWriteBfr(BoolUtl.Y,            256, "100");
		tstr.TestWriteBfr(BoolUtl.Y, IntUtl.MaxValue, "7fffffff");
	}
}
class XoHexUtlTstr {
	public void TestWriteBfr(boolean lcase, int val, String expd) {
		BryWtr bfr = BryWtr.New();
		XoHexUtl.WriteBfr(bfr, lcase, val);
		GfoTstr.Eq(expd, bfr.ToStrAndClear());
	}
}
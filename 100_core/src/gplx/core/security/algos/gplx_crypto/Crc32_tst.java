package gplx.core.security.algos.gplx_crypto; import gplx.Bry_;
import gplx.Int_;
import gplx.Io_mgr;
import gplx.Io_url;
import gplx.String_;
import gplx.Tfds;
import gplx.core.consoles.Console_adp__sys;
import gplx.core.ios.streams.IoStream;
import gplx.core.ios.streams.IoStream_;
import gplx.core.strings.String_bldr;
import gplx.core.strings.String_bldr_;
import org.junit.Test;
public class Crc32_tst {
	Crc32 crc32 = new Crc32();
	@Test public void Basic() {
		tst_Crc32("00000000", "");
		tst_Crc32("E8B7BE43", "a");
		tst_Crc32("4C2750BD", "abcdefghijklmnopqrstuvwxyz");
		tst_Crc32("ABF77822", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	}
	void tst_Crc32(String expd, String dat) {Tfds.Eq(expd, crc32.CalcHash(Bry_.new_u8(dat)), expd);}
}
class Crc32_mgr {
	Crc32 crc32 = new Crc32();
	public void SearchDir(Io_url dir) {
		Io_url[] urlAry = Io_mgr.Instance.QueryDir_args(dir).Recur_().ExecAsUrlAry();
		String_bldr sb = String_bldr_.new_();
		for (Io_url url : urlAry) {
			String expd = Extract(url);
			String actl = expd == String_.Empty ? String_.Empty : CheckFile(url);
			String status = Status(expd, actl);
			sb.Add(status).Add("|");
			sb.Add(url.Xto_api()).Add("|");
			sb.Add(expd).Add("|"); sb.Add(actl).Add_char_nl();
			Console_adp__sys.Instance.Write_fmt_w_nl("{0} {1} {2}", status, actl, url.NameOnly());
		}
		Io_mgr.Instance.SaveFilStr(dir.GenSubFil("results.csv"), sb.To_str());
	}
	String Status(String expd, String actl) {
		if (expd == String_.Empty) return "none";
		return String_.Eq(expd, actl) ? "ok " : "BAD";
	}
	String Extract(Io_url url) {
		String urlStr = url.NameOnly();
		int bgnPos = String_.FindBwd(urlStr, "[");		if (bgnPos == String_.Find_none) return String_.Empty;
		int endPos = String_.FindFwd(urlStr, "]", bgnPos); if (endPos == String_.Find_none) return String_.Empty;
		String crc = String_.Mid(urlStr, bgnPos + 1, endPos);
		return String_.Upper(crc);
	}
	String CheckFile(Io_url url) {
		IoStream stream = IoStream_.Null;
		int pos = 0, readLen = 0;
		crc32.Reset();
		byte[] ary = new byte[256 * 256];
		try {
			stream = Io_mgr.Instance.OpenStreamRead(url);
			while (true) {
				readLen = stream.Read(ary, pos, ary.length);
				crc32.Calc(ary, 0, readLen);
				if (readLen < 1) break;
			}
		}
		finally {stream.Rls();}
		return Int_.To_str_hex(crc32.Crc());
	}
}

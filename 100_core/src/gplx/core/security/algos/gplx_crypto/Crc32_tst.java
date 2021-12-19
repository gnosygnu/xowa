/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.security.algos.gplx_crypto;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.libs.files.Io_mgr;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.core.consoles.Console_adp__sys;
import gplx.core.ios.streams.IoStream;
import gplx.core.ios.streams.IoStream_;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
import org.junit.Test;
public class Crc32_tst {
	Crc32 crc32 = new Crc32();
	@Test public void Basic() {
		tst_Crc32("00000000", "");
		tst_Crc32("E8B7BE43", "a");
		tst_Crc32("4C2750BD", "abcdefghijklmnopqrstuvwxyz");
		tst_Crc32("ABF77822", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	}
	void tst_Crc32(String expd, String dat) {GfoTstr.EqObj(expd, crc32.CalcHash(BryUtl.NewU8(dat)), expd);}
}
class Crc32_mgr {
	Crc32 crc32 = new Crc32();
	public void SearchDir(Io_url dir) {
		Io_url[] urlAry = Io_mgr.Instance.QueryDir_args(dir).Recur_().ExecAsUrlAry();
		String_bldr sb = String_bldr_.new_();
		for (Io_url url : urlAry) {
			String expd = Extract(url);
			String actl = expd == StringUtl.Empty ? StringUtl.Empty : CheckFile(url);
			String status = Status(expd, actl);
			sb.Add(status).Add("|");
			sb.Add(url.Xto_api()).Add("|");
			sb.Add(expd).Add("|"); sb.Add(actl).AddCharNl();
			Console_adp__sys.Instance.Write_fmt_w_nl("{0} {1} {2}", status, actl, url.NameOnly());
		}
		Io_mgr.Instance.SaveFilStr(dir.GenSubFil("results.csv"), sb.ToStr());
	}
	String Status(String expd, String actl) {
		if (expd == StringUtl.Empty) return "none";
		return StringUtl.Eq(expd, actl) ? "ok " : "BAD";
	}
	String Extract(Io_url url) {
		String urlStr = url.NameOnly();
		int bgnPos = StringUtl.FindBwd(urlStr, "[");        if (bgnPos == StringUtl.FindNone) return StringUtl.Empty;
		int endPos = StringUtl.FindFwd(urlStr, "]", bgnPos); if (endPos == StringUtl.FindNone) return StringUtl.Empty;
		String crc = StringUtl.Mid(urlStr, bgnPos + 1, endPos);
		return StringUtl.Upper(crc);
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
		return IntUtl.ToStrHex(crc32.Crc());
	}
}

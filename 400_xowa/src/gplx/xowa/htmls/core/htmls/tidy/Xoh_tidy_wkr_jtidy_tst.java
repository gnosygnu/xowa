/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.core.htmls.tidy; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.core.envs.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.w3c.tidy.Tidy;
import org.junit.*; 
public class Xoh_tidy_wkr_jtidy_tst {
	@Before public void init() {fxt.Clear();} private Jtidy_fxt fxt = new Jtidy_fxt();
	@Test   public void Image_full() {
		String nl = Op_sys.Cur().Tid_is_wnt() ? "\r\n" : "\n";	// NOTE: JTidy uses different line-endings based on OS; DATE:2015-05-11
		fxt.Test_tidy("<a href='http://𐎍𐎁_𐎜'>𐎍𐎁_𐎜</a>", "<a href='http://%F0%90%8E%8D%F0%90%8E%81_%F0%90%8E%9C'>&eth;&#144;&#142;&#141;&eth;&#144;&#142;&#129;_&eth;&#144;&#142;&#156;</a>" + nl);
	}
}
class Jtidy_fxt {
	public void Clear() {		
	}
	public void Test_tidy(String raw, String expd) {
		Tidy tidy = new Tidy();
		tidy.setPrintBodyOnly(true);
		tidy.setWraplen(0);
		tidy.setQuiet(true);
		tidy.setShowWarnings(false);
		tidy.setShowErrors(0);
		ByteArrayInputStream rdr = null;
		try {
			rdr = new ByteArrayInputStream(raw.getBytes("UTF-8"));
		} catch (Exception e) {}
		ByteArrayOutputStream wtr = new ByteArrayOutputStream();
		tidy.parse(rdr, wtr);
		String actl = wtr.toString();
		Test_mgr.Eq_str(expd, actl);
	}
}
class Test_mgr {
	public static void Eq_str(String expd, String actl) {
//		byte[] expd_bry = Bry_.new_u8(expd);
//		byte[] actl_bry = Bry_.new_u8(actl);
//		int expd_len = expd_bry.length;
//		int actl_len = actl_bry.length;
//		if (expd_len != actl_len) throw new RuntimeException(String.format("expd != actl; expd:%s actl:%s", Int_.To_str(expd_len), Int_.To_str(actl_len)));
//		for (int i = 0; i < expd_len; ++i) {
//			byte expd_byte = expd_bry[i];
//			byte actl_byte = actl_bry[i];
//			if (expd_byte != actl_byte) throw new RuntimeException(String.format("expd != actl; %s expd:%s actl:%s", Int_.To_str(i), Byte_.To_str(expd_byte), Byte_.To_str(actl_byte)));
//		}
		if (!expd.equals(actl)) throw new RuntimeException(String.format("expd != actl; expd:%s actl:%s", expd, actl));
	}
}

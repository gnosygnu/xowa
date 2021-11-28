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
package gplx.xowa.langs.lnki_trails; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
import gplx.xowa.langs.names.*;
public class Xol_lnki_trail_mgr_tst {
	private final Xol_lnki_trail_mgr_fxt fxt = new Xol_lnki_trail_mgr_fxt();
	@Test  public void Add_bulk() {
		fxt.Test_add_bulk("äöüß", "ä", "ö", "ü", "ß");
	}
}
class Xol_lnki_trail_mgr_fxt {
	private final Xol_lnki_trail_mgr lnki_trail_mgr = new Xol_lnki_trail_mgr();
	public void Test_add_bulk(String raw, String... expd_ary) {
		lnki_trail_mgr.Add_bulk(Bry_.new_u8(raw));
		int expd_len = expd_ary.length;
		Tfds.Eq(expd_len, lnki_trail_mgr.Count());
		for (int i = 0; i < expd_len; i++) {
			byte[] expd_bry = Bry_.new_u8(expd_ary[i]);
			byte[] actl_bry = (byte[])lnki_trail_mgr.Trie().Match_bgn(expd_bry, 0, expd_bry.length);
			Tfds.Eq_bry(expd_bry, actl_bry);
		}
	}
}

/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.parsers.xndes;

import gplx.Bry_;
import gplx.String_;
import gplx.Tfds;
import gplx.xowa.parsers.htmls.Mwh_atr_itm;
import org.junit.Before;
import org.junit.Test;

public class Xop_xatr_whitelist_mgr_tst {
	private final Xop_xatr_whitelist_fxt fxt = new Xop_xatr_whitelist_fxt();
	@Before public void init() {fxt.Clear();}
	@Test public void Basic() {
		fxt.Whitelist(Xop_xnde_tag_.Tid__div   , "style"          , true);
		fxt.Whitelist(Xop_xnde_tag_.Tid__div   , "xstyle"         , false);
		fxt.Whitelist(Xop_xnde_tag_.Tid__div   , "stylex"         , false);
		fxt.Whitelist(Xop_xnde_tag_.Tid__div   , "styl"           , false);
		fxt.Whitelist(Xop_xnde_tag_.Tid__img   , "alt"            , true);
		fxt.Whitelist(Xop_xnde_tag_.Tid__img   , "span"           , false);
		fxt.Whitelist(Xop_xnde_tag_.Tid__div   , "data-sort-type" , true);
		fxt.Whitelist(Xop_xnde_tag_.Tid__data  , "value"          , true);
		fxt.Whitelist(Xop_xnde_tag_.Tid__data  , "valuex"         , false);
	}
	@Test public void Role() {
		fxt.Whitelist(Xop_xnde_tag_.Tid__div   , "role"           , "presentation", true);
		fxt.Whitelist(Xop_xnde_tag_.Tid__div   , "role"           , "other", true);
	}
	@Test public void Scrub() {
		fxt.Scrub_style_fail("expression");
		fxt.Scrub_style_fail("filter:a");
		fxt.Scrub_style_fail("filter\t \n:a");
		fxt.Scrub_style_fail("accelerator:a");
		fxt.Scrub_style_fail("url()");
		fxt.Scrub_style_fail("urls()");
		fxt.Scrub_style_pass("filterx");
	}
}
class Xop_xatr_whitelist_fxt {
	private Xop_xatr_whitelist_mgr whitelist_mgr;
	public void Clear() {
		if (whitelist_mgr == null) whitelist_mgr = new Xop_xatr_whitelist_mgr().Ini();
	}
	public void Whitelist(int tag_id, String key_str, boolean expd) {
		Mwh_atr_itm atr_itm = New_atr_itm(key_str, null);
		Tfds.Eq(expd, whitelist_mgr.Chk(tag_id, atr_itm), key_str);
	}
	public void Whitelist(int tag_id, String key_str, String val_str, boolean expd) {
		Mwh_atr_itm atr_itm = New_atr_itm(key_str, val_str);
		Tfds.Eq(expd, whitelist_mgr.Chk(tag_id, atr_itm), key_str);
	}
	public void Scrub_style_pass(String style_val_str) {Scrub_style(style_val_str, style_val_str);}
	public void Scrub_style_fail(String val_str) {Scrub_style(val_str, "");}
	public void Scrub_style(String val_str, String expd) {
		byte[] val_bry = Bry_.new_a7(val_str);
		Mwh_atr_itm atr_itm = New_atr_itm(null, val_str);
		whitelist_mgr.Scrub_style(atr_itm, val_bry);
		Tfds.Eq(expd, String_.new_a7(atr_itm.Val_bry()));
	}
	private static Mwh_atr_itm New_atr_itm(String key_str, String val_str) {
		byte[] key_bry = key_str == null ? null : Bry_.new_u8(key_str);
		byte[] val_bry = val_str == null ? null : Bry_.new_u8(val_str);
		Mwh_atr_itm rv = new Mwh_atr_itm(key_bry, false, false, false, -1, -1, -1, -1, key_bry, -1, -1, val_bry, -1, 0);
		return rv;
	}
}

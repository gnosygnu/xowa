/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_xatr_whitelist_mgr_tst {
	Xop_xatr_whitelist_fxt fxt = new Xop_xatr_whitelist_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		fxt.Whitelist(Xop_xnde_tag_.Tid_div		, "style"			, true);
		fxt.Whitelist(Xop_xnde_tag_.Tid_div		, "xstyle"			, false);
		fxt.Whitelist(Xop_xnde_tag_.Tid_div		, "stylex"			, false);
		fxt.Whitelist(Xop_xnde_tag_.Tid_div		, "styl"			, false);
		fxt.Whitelist(Xop_xnde_tag_.Tid_img		, "alt"				, true);
		fxt.Whitelist(Xop_xnde_tag_.Tid_img		, "span"			, false);
		fxt.Whitelist(Xop_xnde_tag_.Tid_div		, "data-sort-type"	, true);
		fxt.Whitelist(Xop_xnde_tag_.Tid_data	, "value"			, true);
		fxt.Whitelist(Xop_xnde_tag_.Tid_data	, "valuex"			, false);
	}
	@Test  public void Role() {
		fxt.Whitelist(Xop_xnde_tag_.Tid_div		, "role"			, "presentation", true);
		fxt.Whitelist(Xop_xnde_tag_.Tid_div		, "role"			, "other", false);
	}
	@Test  public void Scrub() {
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
	public void Clear() {
		if (whitelist_mgr == null) whitelist_mgr = new Xop_xatr_whitelist_mgr().Ini();
	}	private Xop_xatr_whitelist_mgr whitelist_mgr;
	public void Whitelist(int tag_id, String key_str, boolean expd) {
		byte[] key_bry = Bry_.new_a7(key_str);
		atr_itm.Key_rng_(0, key_bry.length);
		Tfds.Eq(expd, whitelist_mgr.Chk(tag_id, key_bry, atr_itm), key_str);
	}	private Xop_xatr_itm atr_itm = new Xop_xatr_itm(0, 0);
	public void Whitelist(int tag_id, String key_str, String val_str, boolean expd) {
		byte[] key_bry = Bry_.new_a7(key_str);
		atr_itm.Key_rng_(0, key_bry.length);
		atr_itm.Val_bry_(Bry_.new_a7(val_str));
		Tfds.Eq(expd, whitelist_mgr.Chk(tag_id, key_bry, atr_itm), key_str);
	}
	public void Scrub_style_pass(String style_val_str) {Scrub_style(style_val_str, style_val_str);}
	public void Scrub_style_fail(String val_str) {Scrub_style(val_str, "");}
	public void Scrub_style(String val_str, String expd) {
		byte[] val_bry = Bry_.new_a7(val_str);
		atr_itm.Val_bry_(val_bry);
		whitelist_mgr.Scrub_style(atr_itm, val_bry);
		Tfds.Eq(expd, String_.new_a7(atr_itm.Val_bry()));
	}
}

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
package gplx.xowa.hdumps.srls; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import org.junit.*; import gplx.xowa.hdumps.core.*;
public class Hpg_srl_itm_tst {
	@Before public void init() {fxt.Clear();} private Hpg_srl_itm_fxt fxt = new Hpg_srl_itm_fxt();
	@Test   public void Body() {
		fxt.Test_page(fxt.Make_page().Body_("A"), fxt.Make_srl_body("A"));
	}
	@Test   public void Html_modules() {
		fxt.Test_page(fxt.Make_page().Body_("A").Html_modules_(Bool_.Y, Bool_.N, Bool_.Y, Bool_.N), fxt.Make_srl_body("A"), fxt.Make_srl_html_modules(Bool_.Y, Bool_.N, Bool_.Y, Bool_.N));
	}
	@Test   public void Body_all() {
		fxt.Test_page(fxt.Make_page().Body_("A").Html_modules_(Bool_.Y, Bool_.N, Bool_.Y, Bool_.N).Display_ttl_("B").Content_sub_("C").Sidebar_div_("D")
		, fxt.Make_srl_body("A")
		, fxt.Make_srl_html_modules(Bool_.Y, Bool_.N, Bool_.Y, Bool_.N)
		, fxt.Make_srl_display_ttl("B")
		, fxt.Make_srl_content_sub("C")
		, fxt.Make_srl_sidebar_div("D")
		);
	}
}
class Hpg_srl_itm_fxt {
	private Bry_bfr bfr = Bry_bfr.reset_(8);
	public void Clear() {bfr.Clear();}
	public Hdump_page_mok Make_page() {return new Hdump_page_mok();}
	public Hpg_srl_itm_mok Make_srl_body(String s) {return Make_srl(Hpg_srl_itm_.Tid_body, s);}
	public Hpg_srl_itm_mok Make_srl_display_ttl(String s) {return Make_srl(Hpg_srl_itm_.Tid_display_ttl, s);}
	public Hpg_srl_itm_mok Make_srl_content_sub(String s) {return Make_srl(Hpg_srl_itm_.Tid_content_sub, s);}
	public Hpg_srl_itm_mok Make_srl_sidebar_div(String s) {return Make_srl(Hpg_srl_itm_.Tid_sidebar_div, s);}
	public Hpg_srl_itm_mok Make_srl(byte tid, String s) {return new Hpg_srl_itm_mok(tid, Bry_.new_utf8_(s));}
	public Hpg_srl_itm_mok Make_srl_html_modules(boolean... v) {
		return new Hpg_srl_itm_mok(Hpg_srl_itm_.Tid_html_module, new byte[] {Hpg_srl_itm__html_module.Calc_flag(v[0], v[1], v[2], v[3])});
	}
	public void Test_page(Hdump_page_mok hpg_mok, Hpg_srl_itm_mok... expd_itms) {
		Hdump_page hpg = hpg_mok.Xto_hdump_page();
		Hpg_srl_mgr._i_.Save(hpg, bfr);
		byte[] actl = bfr.Xto_bry_and_clear();
		Tfds.Eq_ary(Hpg_srl_itm_mok.Xto_bry(bfr, expd_itms), actl);
		Hdump_page actl_hpg = new Hdump_page();
		Hpg_srl_mgr._i_.Load(actl_hpg, actl);
		Hpg_srl_mgr._i_.Save(hpg, bfr);
		Tfds.Eq_ary(actl, bfr.Xto_bry_and_clear());
	}
}
class Hdump_page_mok {
	public byte[] Body() {return body;} public Hdump_page_mok Body_(String v) {body = Bry_.new_utf8_(v); return this;} private byte[] body;
	public boolean[] Html_modules() {return html_modules;} public Hdump_page_mok Html_modules_(boolean... v) {html_modules = v; return this;} private boolean[] html_modules;
	public byte[] Display_ttl() {return display_ttl;} public Hdump_page_mok Display_ttl_(String v) {display_ttl = Bry_.new_utf8_(v); return this;} private byte[] display_ttl;
	public byte[] Content_sub() {return content_sub;} public Hdump_page_mok Content_sub_(String v) {content_sub = Bry_.new_utf8_(v); return this;} private byte[] content_sub;
	public byte[] Sidebar_div() {return sidebar_div;} public Hdump_page_mok Sidebar_div_(String v) {sidebar_div = Bry_.new_utf8_(v); return this;} private byte[] sidebar_div;
	public Hdump_page Xto_hdump_page() {
		Hdump_page rv = new Hdump_page();
		rv.Page_body_(body);
		Hdump_module_mgr mod_mgr = rv.Module_mgr();
		if (html_modules != null) {
			mod_mgr.Math_exists_(html_modules[0]);
			mod_mgr.Imap_exists_(html_modules[1]);
			mod_mgr.Gallery_packed_exists_(html_modules[2]);
			mod_mgr.Hiero_exists_(html_modules[3]);
		}
		rv.Display_ttl_(display_ttl);
		rv.Content_sub_(content_sub);
		rv.Sidebar_div_(sidebar_div);
		return rv;
	}
}
class Hpg_srl_itm_mok {
	public Hpg_srl_itm_mok(byte tid, byte[] data) {this.tid = tid; this.data = data;}
	public byte Tid() {return tid;} private final byte tid;
	public int Len() {return data.length;}
	public byte[] Data() {return data;} private final byte[] data;
	public static byte[] Xto_bry(Bry_bfr bfr, Hpg_srl_itm_mok[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Hpg_srl_itm_mok itm = ary[i];
			if (itm.Tid() != Hpg_srl_itm_.Tid_body)
				bfr.Add_byte(itm.Tid());
			Hpg_srl_itm_.Save_bin_int_abrv(bfr, itm.Len());
			bfr.Add(itm.Data());
		}
		return bfr.Xto_bry_and_clear();
	}
}

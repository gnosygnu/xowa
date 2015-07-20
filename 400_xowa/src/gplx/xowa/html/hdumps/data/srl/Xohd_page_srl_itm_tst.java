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
package gplx.xowa.html.hdumps.data.srl; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*; import gplx.xowa.html.hdumps.data.*;
import org.junit.*; import gplx.xowa.html.hdumps.core.*; import gplx.xowa.html.hzips.*; import gplx.xowa.html.hdumps.pages.*;
import gplx.xowa2.gui.*;
public class Xohd_page_srl_itm_tst {
	@Before public void init() {fxt.Clear();} private Xohd_page_srl_itm_fxt fxt = new Xohd_page_srl_itm_fxt();
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
class Xohd_page_srl_itm_fxt {
	private final Bry_bfr bfr = Bry_bfr.reset_(8);
	public void Clear() {bfr.Clear();}
	public Xog_page_bldr Make_page() {return new Xog_page_bldr();}
	public Xohd_page_srl_itm_mok Make_srl_body(String s) {return Make_srl(Xohd_page_srl_itm_.Tid_body, s);}
	public Xohd_page_srl_itm_mok Make_srl_display_ttl(String s)	{return Make_srl(Xohd_page_srl_itm_.Tid_display_ttl, s);}
	public Xohd_page_srl_itm_mok Make_srl_content_sub(String s)	{return Make_srl(Xohd_page_srl_itm_.Tid_content_sub, s);}
	public Xohd_page_srl_itm_mok Make_srl_sidebar_div(String s)	{return Make_srl(Xohd_page_srl_itm_.Tid_sidebar_div, s);}
	public Xohd_page_srl_itm_mok Make_srl(byte tid, String s)		{return new Xohd_page_srl_itm_mok(tid, Bry_.new_u8(s));}
	public Xohd_page_srl_itm_mok Make_srl_html_modules(boolean... v) {
		return new Xohd_page_srl_itm_mok(Xohd_page_srl_itm_.Tid_html_module, new byte[] {Xohd_page_srl_itm__html_module.Calc_flag(v[0], v[1], v[2], v[3])});
	}
	public void Test_page(Xog_page_bldr hpg_bldr, Xohd_page_srl_itm_mok... expd_itms) {
		Xog_page hpg = hpg_bldr.Bld();
		byte[] actl = Xohd_page_srl_mgr.I.Save(hpg, bfr);
		Tfds.Eq_ary(Xohd_page_srl_itm_mok.Xto_bry(bfr, expd_itms), actl);
		Xog_page actl_hpg = new Xog_page();
		Xohd_page_srl_mgr.I.Load(actl_hpg, actl);
		Tfds.Eq_ary(actl, Xohd_page_srl_mgr.I.Save(hpg, bfr));
	}
}
class Xog_page_bldr {
	public byte[] Body() {return body;} public Xog_page_bldr Body_(String v) {body = Bry_.new_u8(v); return this;} private byte[] body;
	public boolean[] Html_modules() {return html_modules;} public Xog_page_bldr Html_modules_(boolean... v) {html_modules = v; return this;} private boolean[] html_modules;
	public byte[] Display_ttl() {return display_ttl;} public Xog_page_bldr Display_ttl_(String v) {display_ttl = Bry_.new_u8(v); return this;} private byte[] display_ttl;
	public byte[] Content_sub() {return content_sub;} public Xog_page_bldr Content_sub_(String v) {content_sub = Bry_.new_u8(v); return this;} private byte[] content_sub;
	public byte[] Sidebar_div() {return sidebar_div;} public Xog_page_bldr Sidebar_div_(String v) {sidebar_div = Bry_.new_u8(v); return this;} private byte[] sidebar_div;
	public Xog_page Bld() {
		Xog_page rv = new Xog_page();
		rv.Page_body_(body);
		Xopg_module_mgr mod_mgr = rv.Head_mgr();
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
class Xohd_page_srl_itm_mok {
	public Xohd_page_srl_itm_mok(byte tid, byte[] data) {this.tid = tid; this.data = data;}
	public byte Tid() {return tid;} private final byte tid;
	public int Len() {return data.length;}
	public byte[] Data() {return data;} private final byte[] data;
	public static byte[] Xto_bry(Bry_bfr bfr, Xohd_page_srl_itm_mok[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xohd_page_srl_itm_mok itm = ary[i];
			if (itm.Tid() != Xohd_page_srl_itm_.Tid_body)
				bfr.Add_byte(itm.Tid());
			Xow_hzip_int_.Save_bin_int_abrv(bfr, itm.Len());
			bfr.Add(itm.Data());
		}
		return bfr.Xto_bry_and_clear();
	}
}

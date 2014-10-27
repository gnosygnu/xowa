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
package gplx.xowa.hdumps.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import org.junit.*; import gplx.xowa.html.*;
public class Xoa_hzip_itm__lnki_tst {
	@Before public void init() {fxt.Clear();} private Xoa_hzip_itm__lnki_fxt fxt = new Xoa_hzip_itm__lnki_fxt();
	@Test   public void Srl_ttl() {
		byte[][] brys = Bry_.Ary(Xoa_hzip_dict.Bry_lnki_ttl, Bry_.ints_(2), Bry_.new_ascii_("A"), Xoa_hzip_dict.Escape_bry);
		fxt.Test_save(brys, "<a xtid='a_ttl' href=\"/wiki/A\" id='xowa_lnki_0' title='A'>A</a>");
		fxt.Test_load(brys, "<a href='/wiki/A' title='A'>A</a>");
	}
	@Test   public void Srl_ttl_alt_case() {
		byte[][] brys = Bry_.Ary(Xoa_hzip_dict.Bry_lnki_ttl, Bry_.ints_(2), Bry_.new_ascii_("a"), Xoa_hzip_dict.Escape_bry);
		fxt.Test_save(brys, "<a xtid='a_ttl' href=\"/wiki/A\" id='xowa_lnki_0' title='A'>a</a>");
		fxt.Test_load(brys, "<a href='/wiki/A' title='a'>a</a>");
	}
	@Test   public void Srl_ttl_ns() {
		byte[][] brys = Bry_.Ary(Xoa_hzip_dict.Bry_lnki_ttl, Bry_.ints_(12), Bry_.new_ascii_("A"), Xoa_hzip_dict.Escape_bry);
		fxt.Test_save(brys, "<a xtid='a_ttl' href=\"/wiki/Template:A\" id='xowa_lnki_0' title='Template:A'>Template:A</a>");
		fxt.Test_load(brys, "<a href='/wiki/Template:A' title='Template:A'>Template:A</a>");
	}
	@Test   public void Srl_ttl_smoke() {
		byte[][] brys = Bry_.Ary(Bry_.new_ascii_("a_1"), Xoa_hzip_dict.Bry_lnki_ttl, Bry_.ints_(2), Bry_.new_ascii_("A"), Xoa_hzip_dict.Escape_bry, Bry_.new_ascii_("a_2"));
		fxt.Test_save(brys, "a_1<a xtid='a_ttl' href=\"/wiki/A\" id='xowa_lnki_0' title='A'>A</a>a_2");
		fxt.Test_load(brys, "a_1<a href='/wiki/A' title='A'>A</a>a_2");
	}
	@Test   public void Srl_capt() {
		byte[][] brys = Bry_.Ary(Xoa_hzip_dict.Bry_lnki_capt, Bry_.ints_(2), Bry_.new_ascii_("A"), Xoa_hzip_dict.Escape_bry, Bry_.new_ascii_("A1"), Xoa_hzip_dict.Bry_a_end);
		fxt.Test_save(brys, "<a xtid='a_capt' href=\"/wiki/A\" id='xowa_lnki_0' title='A'>A1</a>");
		fxt.Test_load(brys, "<a href='/wiki/A' title='A1'>A1</a>");
	}
	@Test   public void Srl_noop() {
		fxt.Test_save("<a href='/wiki/A'>A</a>", Bry_.new_utf8_("<a href='/wiki/A'>A</a>"));
	}
	@Test   public void Html_ttl() {
		fxt.Test_html("[[A]]", "<a xtid='a_ttl' href=\"/wiki/A\" xowa_redlink='1'>A</a>");
	}
	@Test   public void Html_capt() {
		fxt.Test_html("[[A|a]]", "<a xtid='a_capt' href=\"/wiki/A\" xowa_redlink='1'>a</a>");
	}
}
class Xoa_hzip_itm__lnki_fxt {
	private Bry_bfr bfr = Bry_bfr.reset_(Io_mgr.Len_mb); private Xoa_hzip_mgr hzip_mgr; private Xow_wiki wiki;
	public void Clear() {
		if (hzip_mgr == null) {
			Xoa_app app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
			hzip_mgr = new Xoa_hzip_mgr(Gfo_usr_dlg_._, wiki);
		}
	}
	public void Test_save(byte[][] expd_brys, String html) {Test_save(html, expd_brys);}
	public void Test_save(String html, byte[]... expd_brys) {
		byte[] expd = Bry_.Add(expd_brys);
		hzip_mgr.Save(bfr, Bry_.Empty, Bry_.new_utf8_(html));
		Tfds.Eq_ary(expd, bfr.Xto_bry_and_clear());
	}
	public void Test_load(byte[][] src_brys, String expd) {
		byte[] src = Bry_.Add(src_brys);
		hzip_mgr.Load(bfr, Bry_.Empty, src);
		Tfds.Eq_ary(Bry_.new_utf8_(expd), bfr.Xto_bry_and_clear());
	}
	public void Test_html(String html, String expd) {
		Xop_ctx ctx = wiki.Ctx(); Xop_parser parser = wiki.Parser(); Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		ctx.Para().Enabled_n_();
		ctx.Cur_page().Lnki_redlinks_mgr().Clear();
		byte[] html_bry = Bry_.new_utf8_(html);
		Xop_root_tkn root = ctx.Tkn_mkr().Root(html_bry);
		parser.Parse_page_all_clear(root, ctx, tkn_mkr, html_bry);
		Xoh_wtr_ctx hctx = Xoh_wtr_ctx.Hdump;
		Xoh_html_wtr html_wtr = wiki.Html_mgr().Html_wtr();
		html_wtr.Write_all(bfr, ctx, hctx, html_bry, root);
		Tfds.Eq(expd, bfr.Xto_str_and_clear());
	}
}
class Xoa_hzip_itm__href {
		// <a href="/wiki/File:The_Earth_seen_from_Apollo_17.jpg" class="image" xowa_title="The Earth seen from Apollo 17.jpg">
		// <a href="/site/simple.wikipedia.org/wiki/Template:Solar_System?action=edit"><span title="Edit this template" style="">e</span></a>	// xwiki [[simple:xx
		// <a href="http://planetarynames.wr.usgs.gov/jsp/append5.jsp" class="external text" rel="nofollow">"Descriptor Terms (Feature Types)"</a>
		// 255,5,
	public static final byte
	  Tid_proto_none  = 0
	, Tid_proto_http  = 1
	, Tid_proto_https = 2
	, Tid_proto_other = 3
	;
	public static final byte
	  Tid_domain_com = 0
	, Tid_domain_org = 1
	, Tid_domain_net = 2
	, Tid_domain_gov = 3
	, Tid_domain_other = 4
	;
	public static final byte
	  Tid_ext_none = 0
	, Tid_ext_htm = 1
	, Tid_ext_html = 2
	, Tid_ext_php = 3
	, Tid_ext_jsp = 4
	, Tid_ext_asp = 5
	, Tid_ext_aspx = 6
	, Tid_ext_other = 7
	;
}

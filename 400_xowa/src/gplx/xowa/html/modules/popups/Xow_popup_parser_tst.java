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
package gplx.xowa.html.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.modules.*;
import org.junit.*;
import gplx.xowa.apis.xowa.html.modules.*;
import gplx.xowa.gui.views.*;
public class Xow_popup_parser_tst {
	@Before public void init() {fxt.Clear();} private Xop_popup_parser_fxt fxt = new Xop_popup_parser_fxt();
	@Test   public void Text_chars_one() {
		fxt.Test_parse
		( "a b c d", String_.Concat_lines_nl_skip_last
		( "<p>a b"
		, "</p>"
		));
	}
	@Test   public void Text_chars_many() {	// PURPOSE: text.read_spans_scan
		fxt.Test_parse
		( "abc def ghi", String_.Concat_lines_nl_skip_last
		( "<p>abc def"
		, "</p>"
		));
	}						
	@Test   public void Text_chars_bound() {// PURPOSE: text.word_spans_scan
		fxt.Test_parse
		( "abcde fghij k l", String_.Concat_lines_nl_skip_last
		( "<p>abcde fghij"
		, "</p>"
		));
	}
	@Test   public void Apos() {
		fxt.Test_parse
		( "'''ab''' ''c'' de", String_.Concat_lines_nl_skip_last
		( "<p><b>ab</b> <i>c"
		, "</p>"
		, "</i>"
		));
	}
	@Test   public void Lnki() {
		fxt.Test_parse("a [[b|c d e]] f"
		, String_.Concat_lines_nl_skip_last
		( "<p>a <a href=\"/site/en.wiki/wiki/B\">c d e</a>"
		, "</p>"
		));
	}
	@Test   public void Lnke_brack() {	// PURPOSE: count lnke caption words; DATE:2014-06-20
		fxt.Init_scan_len_(32).Init_word_min_(5).Test_parse
		( "a [http://b.org b c] d e f g", String_.Concat_lines_nl_skip_last
		( "<p>a b c d e"
		, "</p>"
		));
	}
	@Test   public void Lnke_text() {	// PURPOSE: count entire lnke as one word
		fxt.Init_scan_len_(32).Init_word_min_(5).Test_parse
		( "a http://b.org c d e f g", String_.Concat_lines_nl_skip_last
		( "<p>a <a href=\"http://b.org\" class=\"external text\" rel=\"nofollow\">http://b.org</a> c d e"
		, "</p>"
		));
	}
	@Test   public void Lnke_dangling() {	// PURPOSE: handle dangling lnke; DATE:2014-06-20
		fxt.Test_parse
		( "a [http://b.org c d] e f g", String_.Concat_lines_nl_skip_last	// NOTE: scan_len = 4, so 1st pass will be "a [h"
		( "<p>a c d"	// NOTE: (a) lnke correctly parsed, else would see "[" or "http"; (b) "c d" counts as 1 word
		, "</p>"
		));
	}
	@Test   public void Hdr() {
		fxt.Test_parse
		( "a\n===b===\n c", String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		, ""
		, "<h3>b</h3>"
		));
	}
	@Test   public void Hdr_one_word() {	// PURPOSE: hdr.entire_tkn_counts_as_one_word
		fxt.Test_parse
		( "===a b c===\nd", String_.Concat_lines_nl_skip_last
		( "<h3>a b c</h3>"
		, ""
		, "<p>d"
		, "</p>"
		));
	}
	@Test   public void Hdr_para() {	// PURPOSE: hdr.para; handle para mode and hdr (para causes trailing \n to be para, not \n); PAGE:en.w:Flavius_Valerius_Severus DATE:2014-06-17
		fxt.Init_para_enabled_(true).Test_parse(String_.Concat_lines_nl_skip_last
		( ""
		, "a"
		, ""
		, "==b=="
		, "c"
		, ""
		, "d"
		), String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		, ""
		, "<h2>b</h2>"
		, ""
		));
	}
	@Test   public void List() {
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "a"
		, "*b"
		, "c"
		), String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		, ""
		, "<ul>"
		, "  <li>b"
		, "  </li>"
		, "</ul>"
		));
	}
	@Test   public void Xnde_pair() {
		fxt.Test_parse
		( "<span id='a'>b</span>"
		, String_.Concat_lines_nl_skip_last
		( "<p><span id='a'>b</span>"
		, "</p>"
		));
	}
	@Test   public void Xnde_inline() {
		fxt.Test_parse
		( "<span id='a'/>"
		, String_.Concat_lines_nl_skip_last
		( "<p><span id='a'></span>"
		, "</p>"
		));
	}
	@Test   public void Ignore_tblw() {// also checks for tbl spanning multiple blocks; PAGE:en.w:Stratosphere; DATE:2014-06-17
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "a "
		, "{|"
		, "|-"
		, "|b"
		, "|} c"
		), String_.Concat_lines_nl_skip_last
		( "<p>a  c"
		, "</p>"
		));
	}
	@Test   public void Ignore_tblw_nested() {// PAGE:en.w:Cosmoloyg; DATE:2014-06-17
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "<span>a"
		, "{|"
		, "|-"
		, "|b"
		, "|}"
		, "</span>"
		), String_.Concat_lines_nl_skip_last
		( "<p><span>a"
		, "</span>"
		, "</p>"
		));
	}
	@Test   public void Ignore_tblx() {
		fxt.Test_parse
		( "a <table><tr><td>b</td></tr></table> c"
		, String_.Concat_lines_nl_skip_last
		( "<p>a  c"
		, "</p>"
		));
	}
	@Test   public void Ignore_ref() {
		fxt.Test_parse
		( "a <ref>b</ref> c"
		, String_.Concat_lines_nl_skip_last
		( "<p>a  c"
		, "</p>"
		));
	}
	@Test   public void Ignore_div() {
		fxt.Test_parse
		( "a <div>b</div> c"
		, String_.Concat_lines_nl_skip_last
		( "<p>a  c"
		, "</p>"
		));
	}
	@Test   public void Ignore_space_bos() {	// pre. ignore spaces, else pre; PAGE:en.w:Volcano; en.w:War_elephant; DATE:2014-06-17
		fxt.Test_parse
		( "<div>a</div>  b c d" // spaces before "b" are ignored
		, String_.Concat_lines_nl_skip_last
		( "<p>b c"
		, "</p>"
		));
	}
	@Test   public void Ignore_space_nl() {
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "a"
		, "<div>b</div> c"	// space before "c" is ignored
		), String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "c"
		, "</p>"
		));
	}
	@Test   public void Ignore_nl_bos() {
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( ""
		, ""
		, "a"
		), String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		));
	}
	@Test   public void Ignore_nl_multiple() {
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "a"
		, ""
		, ""
		, ""	// ignored
		, "b"
		), String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		, ""
		, "<p>b"
		, "</p>"
		));
	}
	@Test   public void Ignore_nl_hdr() {
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "a"
		, ""
		, ""
		, ""	// ignored
		, "==b=="
		), String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		, ""
		, "<h2>b</h2>"
		));
	}
	@Test   public void Ignore_lnki_file() {
		fxt.Test_parse
		( "a [[File:b.png|thumb]] c"
		, String_.Concat_lines_nl_skip_last
		( "<p>a  c"
		, "</p>"
		));
	}
	@Test   public void Ignore_gallery() {
		fxt.Test_parse
		( "a <gallery>File:B.png|c</gallery> d"
		, String_.Concat_lines_nl_skip_last
		( "<p>a  d"
		, "</p>"
		));
	}
	@Test   public void Ignore_xnde() {
		fxt.Test_parse
		( "a <span id='coordinates'>b</span> c"
		, String_.Concat_lines_nl_skip_last
		( "<p>a  c"
		, "</p>"
		));
	}
	@Test   public void Dangling() {	// make sure dangling nodes don't fail
		fxt.Test_parse
		( "<i>a"
		, String_.Concat_lines_nl_skip_last
		( "<p><i>a</i>"
		, "</p>"
		));
	}
	@Test   public void End_early_dangling() {	// PURPOSE: dangling tkn is too long; end early; PAGE:en.w:List_of_air_forces; DATE:2014-06-18
		fxt.Init_scan_max_(8).Test_parse
		( "a [[File:Test.png]] k"
		, String_.Concat_lines_nl_skip_last
		( "<p>a "
		, "</p>"
		));
	}
	@Test   public void Ellipsis_() {
		fxt.Init_ellipsis_("...").Test_parse
		( "a b c d"
		, String_.Concat_lines_nl_skip_last
		( "<p>a b..."
		, "</p>"
		));
		fxt.Test_parse	// no ellipsis: entire extract
		( "a"
		, String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		));
		fxt.Test_parse	// no ellipsis: entire extract with skip
		( "a <div>b</div>"
		, String_.Concat_lines_nl_skip_last
		( "<p>a "
		, "</p>"
		));
	}
	@Test   public void Stop_at_hdr() {
		fxt.Init_ellipsis_("...").Init_stop_at_header_(true).Test_parse
		( "a\n==b==\nc"
		, String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		));
	}
	@Test   public void Ns_allowed() {
		fxt.Test_ns_allowed("Help"				, Xow_ns_.Id_help);
		fxt.Test_ns_allowed("(Main)"			, Xow_ns_.Id_main);
		fxt.Test_ns_allowed(""					);
		fxt.Test_ns_allowed("(Main)|Help"		, Xow_ns_.Id_main, Xow_ns_.Id_help);
	}
	@Test   public void Read_til_stop_fwd() {
		fxt.Init_word_min_(2).Init_read_til_stop_fwd_(2).Test_parse("a b c\n==d==", String_.Concat_lines_nl_skip_last
		( "<p>a b c"
		, "</p>"
		));
		fxt.Init_word_min_(2).Init_read_til_stop_fwd_(2).Test_parse("a b c d", String_.Concat_lines_nl_skip_last
		( "<p>a b"
		, "</p>"
		));
	}
	@Test   public void Tmpl_tkn_max() {
		fxt.Init_tmpl_tkn_max_(5).Init_page("Template:A", "a");
		fxt.Test_parse
		( "{{A}}"
		, String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		));
		fxt.Test_parse( "{{A|b|c}}", "<p>\n</p>");
	}
}
class Xop_popup_parser_fxt {
	private Xow_popup_parser parser; private Xow_wiki wiki;
	private int word_min = 2;
	public void Clear() {
		Xoa_app app = Xoa_app_fxt.app_();
		this.wiki = Xoa_app_fxt.wiki_(app, "en.wiki");
		parser = wiki.Html_mgr().Module_mgr().Popup_mgr().Parser();
		parser.Init_by_wiki(wiki);
		parser.Scan_len_(4);
		parser.Html_fmtr().Fmt_("~{content}");
		parser.Ellipsis_(Bry_.Empty);
		parser.Notoc_(Bry_.Empty);
		parser.Stop_at_hdr_(false);
		parser.Output_js_clean_(false);
		parser.Output_tidy_(false);
		parser.Show_all_if_less_than_(-1);
		parser.Xnde_ignore_ids_(Xoapi_popups.Dflt_coordinates);
		parser.Read_til_stop_fwd_(-1);
		word_min = 2;
	}
	public Xop_popup_parser_fxt Init_scan_len_(int v) {parser.Scan_len_(v); return this;}
	public Xop_popup_parser_fxt Init_scan_max_(int v) {parser.Scan_max_(v); return this;}
	public Xop_popup_parser_fxt Init_word_min_(int v) {word_min = v; return this;}
	public Xop_popup_parser_fxt Init_para_enabled_(boolean v) {parser.Wtxt_ctx().Para().Enabled_(v); return this;}
	public Xop_popup_parser_fxt Init_stop_at_header_(boolean v) {parser.Stop_at_hdr_(v); return this;}
	public Xop_popup_parser_fxt Init_ellipsis_(String v) {parser.Ellipsis_(Bry_.new_utf8_(v)); return this;}
	public Xop_popup_parser_fxt Init_read_til_stop_fwd_(int v) {parser.Read_til_stop_fwd_(v); return this;}
	public Xop_popup_parser_fxt Init_tmpl_tkn_max_(int v) {parser.Tmpl_tkn_max_(v); return this;}
	public Xop_popup_parser_fxt Init_page(String ttl, String txt) {Xop_fxt.Init_page_create_static(wiki, ttl, txt); return this;}
	public Xop_popup_parser_fxt Test_ns_allowed(String raw, int... expd) {
		Int_obj_ref[] ids = Xow_popup_mgr.Ns_allowed_parse(wiki, Bry_.new_utf8_(raw));
		Tfds.Eq_ary(expd, Int_obj_ref.Ary_xto_int_ary(ids));
		return this;
	}
	public void Test_parse(String raw, String expd)  {
		Xoa_page page = Xoa_page.create_(wiki, Xoa_ttl.parse_(wiki, Bry_.new_ascii_("Test_1")));
		page.Data_raw_(Bry_.new_utf8_(raw));
		Xow_popup_itm itm = new Xow_popup_itm(1, Bry_.new_utf8_(raw), word_min);
		byte[] actl = parser.Parse(itm, page, wiki.Domain_bry(), null);
		Tfds.Eq_str_lines(expd, String_.new_utf8_(actl));
	}
}

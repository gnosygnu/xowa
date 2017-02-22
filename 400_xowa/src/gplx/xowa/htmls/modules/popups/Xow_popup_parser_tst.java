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
package gplx.xowa.htmls.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.modules.*;
import org.junit.*; import gplx.core.primitives.*;
import gplx.xowa.apps.apis.xowa.html.modules.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.guis.views.*;
public class Xow_popup_parser_tst {
	@Before public void init() {fxt.Clear();} private final    Xop_popup_parser_fxt fxt = new Xop_popup_parser_fxt();
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
		fxt.Init_tmpl_read_len_(32).Init_word_needed_(5).Test_parse
		( "a [http://b.org b c] d e f g", String_.Concat_lines_nl_skip_last
		( "<p>a b c d e"
		, "</p>"
		));
	}
	@Test   public void Lnke_text() {	// PURPOSE: count entire lnke as one word
		fxt.Init_tmpl_read_len_(32).Init_word_needed_(5).Test_parse
		( "a http://b.org c d e f g", String_.Concat_lines_nl_skip_last
		( "<p>a <a href=\"http://b.org\" rel=\"nofollow\" class=\"external free\">http://b.org</a> c d e"
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
	@Test   public void Xnde_br() {	// PURPOSE: check that br is added correctly; PAGE:en.q:Earth; DATE:2014-06-30
		fxt.Init_word_needed_(3).Test_parse
		( "a<br>b<br/>"
		, String_.Concat_lines_nl_skip_last
		( "<p>a<br>b<br/>"
		, "</p>"
		));
	}
	@Test   public void Xnde_math() {	// PURPOSE: <math> should be treated as one word; PAGE:en.w:System_of_polynomial_equations; DATE:2014-07-01
		fxt	.Init_word_needed_(5)		// need to read more words to pick up 1st word after header
			.Init_read_til_stop_bwd_(2)	// need to do read_bwd to start counting before ==e== into <math> node
			.Test_parse
		( "a <math>b c d</math> \n==e==\nf g h i"
		, String_.Concat_lines_nl_skip_last
		( "<p>a <span id='xowa_math_txt_0'>b c d</span> (e)"	// used to fail as <p>a &lt;math&gt;b c d (e)
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
		fxt.Init_tmpl_read_max_(8).Test_parse
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
		fxt.Test_parse	// no ellipsis: entire extract multiple reads
		( "a <div>b</div>"
		, String_.Concat_lines_nl_skip_last
		( "<p>a "
		, "</p>"
		));
	}
	@Test   public void Ns_allowed() {
		fxt.Test_ns_allowed("Help"				, Xow_ns_.Tid__help);
		fxt.Test_ns_allowed("(Main)"			, Xow_ns_.Tid__main);
		fxt.Test_ns_allowed(""					);
		fxt.Test_ns_allowed("(Main)|Help"		, Xow_ns_.Tid__main, Xow_ns_.Tid__help);
	}
	@Test   public void Read_til_stop_fwd() {
		fxt.Init_word_needed_(2).Init_read_til_stop_fwd_(2)					// read fwd found hdr
		.Test_parse("a b c\n==d==", String_.Concat_lines_nl_skip_last
		( "<p>a b c (d)"
		, "</p>"
		));
		fxt.Init_word_needed_(2).Init_read_til_stop_fwd_(2)					// read fwd did not find hdr; reset back to min
		.Test_parse("a b c d", String_.Concat_lines_nl_skip_last
		( "<p>a b"
		, "</p>"
		));
	}
	@Test   public void Read_til_stop_bwd() {
		fxt.Init_word_needed_(8).Init_read_til_stop_bwd_(4)					// read bwd found hdr
		.Test_parse("01 02 03 04 05\n==06==\n07 08 09 10 11 12 13 14 15 16", String_.Concat_lines_nl_skip_last
		( "<p>01 02 03 04 05 (06)"
		, "</p>"
		));
		fxt.Init_tmpl_read_len_(40).Init_word_needed_(5).Init_read_til_stop_bwd_(3)		// read bwd at eos should not return "next_sect"; DATE:2014-07-01
		.Test_parse("01 02 03 \n==04==\n", String_.Concat_lines_nl_skip_last
		( "<p>01 02 03 "
		, "</p>"
		, ""
		, "<h2>04</h2>"
		));
	}
	@Test   public void Stop_if_hdr_after() {
		fxt.Init_word_needed_(5).Init_stop_if_hdr_after_(1)
		.Test_parse("a b\n==c==\nd e", String_.Concat_lines_nl_skip_last
		( "<p>a b"
		, "</p>"
		, ""
		, "<h2>c</h2>"
		));
	}
	@Test   public void Anchor() {
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "a b c d"
		, ""
		, "== e =="
		, "f g h i"
		), "#e", String_.Concat_lines_nl_skip_last
		( "<h2> e </h2>"
		, ""
		, "<p>f"
		, "</p>"
		));
	}
	@Test   public void Anchor_underline() {
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "a b c d"
		, ""
		, "== e f =="
		, "g h i"
		), "#e_f", String_.Concat_lines_nl_skip_last
		( "<h2> e f </h2>"
		, ""
		, "<p>g"
		, "</p>"
		));
	}
	@Test   public void Tmpl_tkn_max() {
		fxt.Init_tmpl_tkn_max_(5).Init_page("Template:A", "a");		// eval
		fxt.Test_parse
		( "{{A}}"
		, String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		));
		fxt.Test_parse("{{A|b|c}}"			, "");					// skip; NOTE: output should be blank, not <p>\n</p>; PAGE:en.w:List_of_countries_by_GDP_(PPP); DATE:2014-07-01
	}
	@Test   public void Tmpl_tkn_max__comment_and_tblw() {	// PURPOSE: garbled popup when tmpl_tkn_max is set and comments in front of tblw; PAGE:en.w:Gwynedd; DATE:2014-07-01
		fxt	.Init_tmpl_tkn_max_(5)		// set tmpl_tkn_max
			.Init_tmpl_read_len_(20)	// set read_len to 20 (must read entire "<!---->\n{|" at once
			.Test_parse(String_.Concat_lines_nl_skip_last
		( "{{A|b}}"
		, "{{A|b}}"
		, "{|"
		, "|-"
		, "|a b c d"
		, "|}"
		), "");	// should be blank, not <table>]
	}
	@Test   public void Tmpl_tkn_max__apos() {	// PURPOSE: handle apos around skipped tmpl token; PAGE:en.w:Somalia; DATE:2014-07-02
		fxt.Init_tmpl_tkn_max_(5).Test_parse("a''{{A|b}}''b", String_.Concat_lines_nl_skip_last
		( "<p>a<i> </i>b"
		, "</p>"
		));
	}
	@Test   public void Notoc_and_para_issue() {	// PURPOSE.fix: issue with "\s__NOTOC__" and "a\n"b; PAGE:en.w:Spain; DATE:2014-07-05
		fxt.Init_word_needed_(3).Init_notoc_(" __NOTOC__").Test_parse("a\nb", String_.Concat_lines_nl_skip_last
		( "<p>a"		// was <p>a</p>b
		, "b "
		, "</p>"
		));
	}
	@Test  public void Test_Assert_at_end() {
		fxt.Test_Assert_at_end("a"			, "a\n");			// add one
		fxt.Test_Assert_at_end("a\n"		, "a\n");			// noop
		fxt.Test_Assert_at_end("a\n\n\n"	, "a\n");			// remove til one
		fxt.Test_Assert_at_end(""			, "");				// empty check
	}
	@Test  public void Skip_to_end__tblw() {	// PURPOSE: skip to end of tblw; PAGE:en.w:List_of_countries_and_dependencies_by_area; DATE:2014-07-19
		fxt.Init_tmpl_read_len_(4).Test_parse
		( String_.Concat_lines_nl_skip_last
		( "a"
		, "{|"
		, "|-"
		, "|b"
		, "|c"
		, "|d"
		, "|}"
		)
		, String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		));
		fxt.Expd_tmpl_loop_count(2);
	}
}
class Xop_popup_parser_fxt {
	private Xow_popup_parser parser; private Xowe_wiki wiki;
	private int word_min = 2;
	public void Clear() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app, "en.wiki");
		parser = wiki.Html_mgr().Head_mgr().Popup_mgr().Parser();
		parser.Init_by_wiki(wiki);
		parser.Cfg().Tmpl_read_len_(4);
		parser.Cfg().Tmpl_read_max_(32 * Io_mgr.Len_kb);
		parser.Cfg().Ellipsis_(Bry_.Empty);
		parser.Cfg().Notoc_(Bry_.Empty);
		parser.Cfg().Show_all_if_less_than_(-1);
		parser.Cfg().Read_til_stop_fwd_(-1);
		parser.Cfg().Read_til_stop_bwd_(-1);
		parser.Cfg().Stop_if_hdr_after_(-1);
		parser.Html_mkr().Fmtr_popup().Fmt_("~{content}");
		parser.Html_mkr().Output_js_clean_(false);
		parser.Html_mkr().Output_tidy_(false);
		parser.Html_mkr().Fmtr_next_sect().Fmt_(" (~{next_sect_val})");
		parser.Wrdx_mkr().Xnde_ignore_ids_(Bry_.new_a7("coordinates"));
		word_min = 2;
	}
	public Xop_popup_parser_fxt Init_notoc_(String v) {parser.Cfg().Notoc_(Bry_.new_u8(v)); return this;}
	public Xop_popup_parser_fxt Init_tmpl_read_len_(int v) {parser.Cfg().Tmpl_read_len_(v); return this;}
	public Xop_popup_parser_fxt Init_tmpl_read_max_(int v) {parser.Cfg().Tmpl_read_max_(v); return this;}
	public Xop_popup_parser_fxt Init_word_needed_(int v) {word_min = v; return this;}
	public Xop_popup_parser_fxt Init_para_enabled_(boolean v) {parser.Wtxt_ctx().Para().Enabled_(v); return this;}
	public Xop_popup_parser_fxt Init_ellipsis_(String v) {parser.Cfg().Ellipsis_(Bry_.new_u8(v)); return this;}
	public Xop_popup_parser_fxt Init_read_til_stop_fwd_(int v) {parser.Cfg().Read_til_stop_fwd_(v); return this;}
	public Xop_popup_parser_fxt Init_read_til_stop_bwd_(int v) {parser.Cfg().Read_til_stop_bwd_(v); return this;}
	public Xop_popup_parser_fxt Init_stop_if_hdr_after_(int v) {parser.Cfg().Stop_if_hdr_after_(v); return this;}
	public Xop_popup_parser_fxt Init_tmpl_tkn_max_(int v) {parser.Tmpl_tkn_max_(v); return this;}
	public Xop_popup_parser_fxt Init_fmtr_next_sect_(String v) {parser.Html_mkr().Fmtr_next_sect().Fmt_(v); return this;}
	public Xop_popup_parser_fxt Init_page(String ttl, String txt) {Xop_fxt.Init_page_create_static(wiki, ttl, txt); return this;}
	public Xop_popup_parser_fxt Expd_tmpl_loop_count(int expd) {Tfds.Eq(expd, parser.Data().Tmpl_loop_count()); return this;}
	public Xop_popup_parser_fxt Test_ns_allowed(String raw, int... expd) {
		Int_obj_ref[] ids = Xow_popup_mgr.Ns_allowed_parse(wiki, Bry_.new_u8(raw));
		Tfds.Eq_ary(expd, To_int_ary(ids));
		return this;
	}
	private static int[] To_int_ary(Int_obj_ref[] ary) {
		int len = ary.length;
		int[] rv = new int[len];
		for (int i = 0; i < len; ++i)
			rv[i] = ary[i].Val();
		return rv;
	}
	public void Test_parse(String raw, String expd)				{Test_parse(raw, "Test_1", expd);}
	public void Test_parse(String raw, String ttl, String expd)	{
		Xoae_page page = Xoae_page.New_edit(wiki, Xoa_ttl.Parse(wiki, Bry_.new_a7(ttl)));
		page.Db().Text().Text_bry_(Bry_.new_u8(raw));
		Xow_popup_itm itm = new Xow_popup_itm(1, Bry_.new_u8(raw), Bry_.Empty, word_min);
		itm.Init(wiki.Domain_bry(), page.Ttl());
		byte[] actl = parser.Parse(wiki, page, null, itm);
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
	}
	public void Test_Assert_at_end(String raw, String expd) {
		if (test_bfr == null) test_bfr = Bry_bfr_.New();
		test_bfr.Clear().Add_str_u8(raw);
		Bry_bfr_.Assert_at_end(test_bfr, Byte_ascii.Nl);
		Tfds.Eq(expd, test_bfr.To_str_and_clear());
	}	private Bry_bfr test_bfr;
}

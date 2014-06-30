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
package gplx.xowa; import gplx.*;
import org.junit.*;
import gplx.intl.*; import gplx.xowa.apps.*; import gplx.xowa.langs.numbers.*;
public class Xol_lang_srl_tst {
	private Xol_lang_srl_fxt fxt = new Xol_lang_srl_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Ns_names() {
		String raw = String_.Concat_lines_nl
			(	"ns_names"
			,	"  .load_text("
			,	"<:['"
			,	"6|Filex"
			,	"10|Templatex"
			,	"']:>"
			,	").lang"
			);
		fxt.Invk(raw);
		Xol_ns_grp grp = fxt.Lang().Ns_names();
		fxt.Tst_ns_grp(grp, fxt.ns_(6, "Filex"), fxt.ns_(10, "Templatex"));
		fxt.Run_save_ns_grp(grp, Xol_lang.Invk_ns_names, raw);
	}
	@Test  public void Ns_aliases() {
		String raw = String_.Concat_lines_nl
			(	"ns_aliases"
			,	"  .load_text("
			,	"<:['"
			,	"6|Filex"
			,	"10|Templatex"
			,	"']:>"
			,	").lang"
			);
		fxt.Invk(raw);
		Xol_ns_grp grp = fxt.Lang().Ns_aliases();
		fxt.Tst_ns_grp(grp, fxt.ns_(6, "Filex"), fxt.ns_(10, "Templatex"));
		fxt.Run_save_ns_grp(grp, Xol_lang.Invk_ns_aliases, raw);
	}
	@Test  public void Kwds() {
		String raw = String_.Concat_lines_nl	// NOTE: notoc must go before toc because ID order
			(	"keywords"
			,	"  .load_text("
			,	"<:['"
			,	"notoc|1|no_table_of_contents~toc_n~"
			,	"toc|0|table_of_contents~toc_y~"
			,	"']:>"
			,	").lang"
			);
		fxt.Invk(raw);
		Xol_kwd_mgr kwd_mgr = fxt.Lang().Kwd_mgr();
		fxt.Tst_keywords(kwd_mgr, fxt.kwd_("notoc", true, "no_table_of_contents", "toc_n"), fxt.kwd_("toc", false, "table_of_contents", "toc_y"));
		fxt.Run_save_kwd_mgr(kwd_mgr, Xol_lang.Invk_keywords, raw);
	}
	@Test  public void Specials() {
		String raw = String_.Concat_lines_nl	// NOTE: notoc must go before toc because ID order
			(	"specials"
			,	"  .clear"
			,	"  .load_text("
			,	"<:['"
			,	"Randompage|PageAuHasard~Page_au_hasard"
			,	"Search|Recherche~Rechercher"
			,	"']:>"
			,	").lang"
			);
		fxt.Invk(raw);
		Xol_specials_mgr specials_mgr = fxt.Lang().Specials_mgr();
		fxt.Tst_specials(specials_mgr, fxt.special_("Randompage", "PageAuHasard", "Page_au_hasard"), fxt.special_("Search", "Recherche", "Rechercher"));
		fxt.Run_save_specials_mgr(specials_mgr, Xol_lang.Invk_specials, raw);
	}
	@Test  public void Kwds_blank_line() {	// PURPOSE.fix: extra blank line negates entire entry
		String raw = String_.Concat_lines_nl
			(	"keywords"
			,	"  .load_text("
			,	"<:['"
			,	"toc|0|table_of_contents~toc_y~"
			,	""
			,	"']:>"
			,	").lang"
			);
		fxt.Invk(raw);
		Xol_kwd_mgr kwd_mgr = fxt.Lang().Kwd_mgr();
		fxt.Tst_keywords(kwd_mgr, fxt.kwd_("toc", false, "table_of_contents", "toc_y"));	// make sure 2 items (and not 0)
	}
	@Test  public void Msgs() {
		String raw = String_.Concat_lines_nl
			(	"messages"
			,	"  .load_text("
			,	"<:['"
			,	"sunday|dimanche"
			,	"monday|lundi"
			,	"']:>"
			,	").lang"
			);
		fxt.Invk(raw);
		Xol_msg_mgr msg_mgr = fxt.Lang().Msg_mgr();
		fxt.Tst_messages(msg_mgr, fxt.msg_("sunday", "dimanche"), fxt.msg_("monday", "lundi"));
		fxt.Run_save_msg_mgr(msg_mgr, Xol_lang.Invk_messages, raw);
	}
	@Test  public void Fallback() {
		Io_mgr._.SaveFilStr(Xol_lang_.xo_lang_fil_(fxt.App(), "zh-hans"), String_.Concat_lines_nl
			(	"this"
			,	".keywords"
			,	"  .load_text("
			,	"<:['"
			,	"toc|0|table_of_contents~toc_y~"
			,	"']:>"
			,	").lang"
			,	".ns_names"
			,	"  .load_text("
			,	"<:['"
			,	"6|FileA"
			,	"']:>"
			,	").lang"
			,	".messages"
			,	"  .load_text("
			,	"<:['"
			,	"sunday|sunday1"
			,	"']:>"
			,	").lang"
			,	";"
			));
		String raw = String_.Concat_lines_nl	// NOTE: notoc must go before toc because ID order
			(	"fallback_load('zh-hans')"
			,	".keywords"
			,	"  .load_text("
			,	"<:['"
			,	"notoc|1|no_table_of_contents~toc_n~"
			,	"']:>"
			,	").lang"
			,	".ns_names"
			,	"  .load_text("
			,	"<:['"
			,	"6|FileB"
			,	"']:>"
			,	").lang"
			,	".messages"
			,	"  .load_text("
			,	"<:['"
			,	"monday|monday1"
			,	"']:>"
			,	").lang"
			);
		fxt.Invk(raw);
		Xol_kwd_mgr kwd_mgr = fxt.Lang().Kwd_mgr();
		fxt.Tst_keywords(kwd_mgr, fxt.kwd_("notoc", true, "no_table_of_contents", "toc_n"), fxt.kwd_("toc", false, "table_of_contents", "toc_y"));
		fxt.Tst_ns_grp(fxt.Lang().Ns_names(), fxt.ns_(6, "FileA"), fxt.ns_(6, "FileB"));
		fxt.Tst_messages(fxt.Lang().Msg_mgr(), fxt.msg_("sunday", "sunday1"), fxt.msg_("monday", "monday1"));
	}
	@Test  public void Fallback_circular() {	// PURPOSE: pt and pt-br cite each other as fallback in Messages*.php; DATE:2013-02-18
		Io_mgr._.SaveFilStr(Xol_lang_.xo_lang_fil_(fxt.App(), "pt")		, "fallback_load('pt-br');");
		Io_mgr._.SaveFilStr(Xol_lang_.xo_lang_fil_(fxt.App(), "pt-br")	, "fallback_load('pt');");
		Xol_lang lang = new Xol_lang(fxt.App(), Bry_.new_ascii_("pt"));
		lang.Init_by_load();
	}
	@Test  public void Num_fmt() {
		String raw = String_.Concat_lines_nl
		( "numbers {"
		, "  separators {"
		, "    clear;"
		, "    set(',', '.');"
		, "    set('.', ',');"
		, "  }"
		, "}"
		);
		fxt.Invk_no_semic(raw);
		fxt.Tst_num_fmt("1234,56", "1.234.56"); // NOTE: dot is repeated; confirmed with dewiki and {{formatnum:1234,56}}
		fxt.Run_save_num_mgr(fxt.Lang().Num_mgr(), raw);
	}
	@Test  public void Num_fmt_apos() {	// PURPOSE:de.ch has apos which breaks gfs
		fxt	.Init_clear()
			.Init_separators(",", "'")
			.Init_separators(".", ",")
			;
		fxt.Run_save_num_mgr(fxt.Lang().Num_mgr(), String_.Concat_lines_nl
		( "numbers {"
		, "  separators {"
		, "    clear;"
		, "    set(',', '''');"
		, "    set('.', ',');"
		, "  }"
		, "}"
		));
	}
}
class Xol_lang_srl_fxt {
	public void Clear() {
		app = Xoa_app_fxt.app_();
		lang = new Xol_lang(app, Bry_.new_utf8_("fr"));
		Xoa_gfs_mgr.Msg_parser_init();	// required by fallback_load
	}	GfsCtx ctx = GfsCtx.new_(); Gfs_bldr bldr = new Gfs_bldr(); //Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public Xoa_app App() {return app;} private Xoa_app app;
	public Xol_lang Lang() {return lang;} private Xol_lang lang;
	public Xow_ns ns_(int id, String s) {return new Xow_ns(id, Xow_ns_case_.Id_1st, Bry_.new_utf8_(s), false);}
	public Xol_specials_itm special_(String key, String... words) {return new Xol_specials_itm(Bry_.new_utf8_(key), Bry_.Ary(words));}
	public Xol_kwd_grp kwd_(String key, boolean case_match, String... words) {
		Xol_kwd_grp rv = new Xol_kwd_grp(Bry_.new_utf8_(key));
		rv.Srl_load(case_match, Bry_.Ary(words));
		return rv;
	}
	public Xol_msg_itm msg_(String key, String val) {
		Xol_msg_itm rv = lang.Msg_mgr().Itm_by_key_or_new(Bry_.new_utf8_(key));
		rv.Atrs_set(Bry_.new_utf8_(val), false, false);
		return rv;
	}
	public Xol_lang_srl_fxt Init_clear() {
		lang.Num_mgr().Clear();
		return this;
	}
	public Xol_lang_srl_fxt Init_separators(String k, String v) {
		lang.Num_mgr().Separators_mgr().Set(Bry_.new_utf8_(k), Bry_.new_utf8_(v));
		return this;
	}
	public void Invk(String raw) {
		app.Gfs_mgr().Run_str_for(lang, raw + ";");
	}
	public void Invk_no_semic(String raw) {
		app.Gfs_mgr().Run_str_for(lang, raw);
	}
	public void Tst_ns_grp(Xol_ns_grp grp, Xow_ns... expd_ns) {
		Tfds.Eq_str_lines(Xto_str(expd_ns), Xto_str(Xto_ary(grp)));
	}
	public void Run_save_ns_grp(Xol_ns_grp grp, String invk, String raw) {
		Xol_lang_srl.Save_ns_grps(bldr, grp, invk);
		Tfds.Eq_str_lines("." + raw, bldr.Bfr().XtoStrAndClear());
	}
	public void Run_save_kwd_mgr(Xol_kwd_mgr kwd_mgr, String invk, String raw) {
		Xol_lang_srl.Save_keywords(bldr, kwd_mgr);
		Tfds.Eq_str_lines("." + raw, bldr.Bfr().XtoStrAndClear());
	}
	public void Run_save_msg_mgr(Xol_msg_mgr msg_mgr, String invk, String raw) {
		Xol_lang_srl.Save_messages(bldr, msg_mgr, true);
		Tfds.Eq_str_lines("." + raw, bldr.Bfr().XtoStrAndClear());
	}
	public void Run_save_num_mgr(Xol_num_mgr num_mgr, String raw) {
		Xol_lang_srl.Save_num_mgr(bldr, num_mgr);
		Tfds.Eq_str_lines(raw, bldr.Bfr().XtoStrAndClear());
	}
	public void Run_save_specials_mgr(Xol_specials_mgr specials_mgr, String invk, String raw) {
		Xol_lang_srl.Save_specials(bldr, specials_mgr);
		Tfds.Eq_str_lines("." + raw, bldr.Bfr().XtoStrAndClear());
	}
	public void Tst_num_fmt(String raw, String expd) {Tfds.Eq(expd, String_.new_utf8_(lang.Num_mgr().Format_num(Bry_.new_utf8_(raw))));}
	public void Tst_keywords(Xol_kwd_mgr kwd_mgr, Xol_kwd_grp... ary) {
		Tfds.Eq_str_lines(Xto_str(ary), Xto_str(Xto_ary(kwd_mgr)));
	}
	public void Tst_messages(Xol_msg_mgr msg_mgr, Xol_msg_itm... ary) {
		Tfds.Eq_str_lines(Xto_str(ary), Xto_str(Xto_ary(msg_mgr)));
	}
	public void Tst_specials(Xol_specials_mgr specials_mgr, Xol_specials_itm... expd) {
		Tfds.Eq_str_lines(Xto_str(expd), Xto_str(Xto_ary(specials_mgr)));
	}
	private String Xto_str(Xol_specials_itm[] ary) {
		int len = ary.length;			
		for (int i = 0; i < len; i++) {
			Xol_specials_itm itm = ary[i];
			sb.Add(itm.Special()).Add("|");
			int aliases_len = itm.Aliases().length;
			for (int j = 0; j < aliases_len; j++) {
				if (j != 0) sb.Add("~");
				sb.Add(itm.Aliases()[j]).Add_char_nl();
			}
		}
		return sb.XtoStrAndClear();
	}
	private Xol_specials_itm[] Xto_ary(Xol_specials_mgr specials_mgr) {
		int len = specials_mgr.Count();
		Xol_specials_itm[] rv = new Xol_specials_itm[len];
		for (int i = 0; i < len; i++)
			rv[i] = specials_mgr.Get_at(i);
		return rv;
	}
	String Xto_str(Xow_ns[] ary) {
		int len = ary.length;			
		for (int i = 0; i < len; i++) {
			Xow_ns ns = ary[i];
			sb.Add(ns.Id()).Add("|").Add(ns.Name_str()).Add_char_nl();
		}
		return sb.XtoStrAndClear();
	}
	Xow_ns[] Xto_ary(Xol_ns_grp ary) {
		int len = ary.Len();			
		Xow_ns[] rv = new Xow_ns[len];
		for (int i = 0; i < len; i++)
			rv[i] = ary.Get_at(i);
		return rv;
	}
	Xol_kwd_grp[] Xto_ary(Xol_kwd_mgr kwd_mgr) {
		int len = kwd_mgr.Len();
		ListAdp rv = ListAdp_.new_();
		for (int i = 0; i < len; i++) {
			Xol_kwd_grp kwd_grp = kwd_mgr.Get_at(i);
			if (kwd_grp == null) continue;
			rv.Add(kwd_grp);
		}
		return (Xol_kwd_grp[])rv.XtoAry(Xol_kwd_grp.class);
	}
	String Xto_str(Xol_kwd_grp[] ary) {
		int len = ary.length;			
		for (int i = 0; i < len; i++) {
			Xol_kwd_grp grp = ary[i];
			sb.Add(grp.Key()).Add("|").Add(grp.Case_match() ? "1" : "0").Add("|");
			Xol_kwd_itm[] itms = grp.Itms();
			int itms_len = itms.length;
			for (int j = 0; j < itms_len; j++) {
				sb.Add(itms[i].Val()).Add(";");
			}
			sb.Add_char_nl();
		}
		return sb.XtoStrAndClear();
	}
	Xol_msg_itm[] Xto_ary(Xol_msg_mgr msg_mgr) {
		int len = msg_mgr.Itms_max();
		ListAdp rv = ListAdp_.new_();
		for (int i = 0; i < len; i++) {
			Xol_msg_itm itm = msg_mgr.Itm_by_id_or_null(i);
			if (itm == null || !itm.Dirty()) continue;
			rv.Add(itm);
		}
		return (Xol_msg_itm[])rv.XtoAry(Xol_msg_itm.class);
	}
	String Xto_str(Xol_msg_itm[] ary) {
		int len = ary.length;			
		for (int i = 0; i < len; i++) {
			Xol_msg_itm itm = ary[i];
			sb.Add(itm.Key()).Add("|").Add(itm.Val()).Add_char_nl();
		}
		return sb.XtoStrAndClear();
	}
	private static String_bldr sb = String_bldr_.new_();
}

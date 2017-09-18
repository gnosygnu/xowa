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
package gplx.xowa.langs.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*; import gplx.core.strings.*;
import gplx.core.intls.*;
import gplx.xowa.apps.gfs.*;
import gplx.xowa.langs.numbers.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.langs.kwds.*; import gplx.xowa.langs.bldrs.*; import gplx.xowa.langs.specials.*;
import gplx.xowa.wikis.nss.*;
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
		fxt.Run_save_ns_grp(grp, Xol_lang_itm.Invk_ns_names, raw);
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
		fxt.Run_save_ns_grp(grp, Xol_lang_itm.Invk_ns_aliases, raw);
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
		fxt.Run_save_kwd_mgr(kwd_mgr, Xol_lang_itm.Invk_keywords, raw);
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
		fxt.Run_save_specials_mgr(specials_mgr, Xol_lang_itm.Invk_specials, raw);
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
		fxt.Run_save_msg_mgr(msg_mgr, Xol_lang_itm.Invk_messages, raw);
	}
	@Test  public void Fallback() {
		Io_mgr.Instance.SaveFilStr(Xol_lang_itm_.xo_lang_fil_(fxt.App().Fsys_mgr(), "zh-hans"), String_.Concat_lines_nl
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
		Io_mgr.Instance.SaveFilStr(Xol_lang_itm_.xo_lang_fil_(fxt.App().Fsys_mgr(), "pt")		, "fallback_load('pt-br');");
		Io_mgr.Instance.SaveFilStr(Xol_lang_itm_.xo_lang_fil_(fxt.App().Fsys_mgr(), "pt-br")	, "fallback_load('pt');");
		Xol_lang_itm lang = new Xol_lang_itm(fxt.App().Lang_mgr(), Bry_.new_a7("pt"));
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
		app = Xoa_app_fxt.Make__app__edit();
		lang = new Xol_lang_itm(app.Lang_mgr(), Bry_.new_a7("fr"));
		Xoa_gfs_mgr.Msg_parser_init();	// required by fallback_load
	}	GfsCtx ctx = GfsCtx.new_(); Xoa_gfs_bldr bldr = new Xoa_gfs_bldr(); //Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xol_lang_itm Lang() {return lang;} private Xol_lang_itm lang;
	public Xow_ns ns_(int id, String s) {return new Xow_ns(id, Xow_ns_case_.Tid__1st, Bry_.new_u8(s), false);}
	public Xol_specials_itm special_(String key, String... words) {return new Xol_specials_itm(Bry_.new_u8(key), Bry_.Ary(words));}
	public Xol_kwd_grp kwd_(String key, boolean case_match, String... words) {
		Xol_kwd_grp rv = new Xol_kwd_grp(Bry_.new_u8(key));
		rv.Srl_load(case_match, Bry_.Ary(words));
		return rv;
	}
	public Xol_msg_itm msg_(String key, String val) {
		Xol_msg_itm rv = lang.Msg_mgr().Itm_by_key_or_new(Bry_.new_u8(key));
		rv.Atrs_set(Bry_.new_u8(val), false, false);
		return rv;
	}
	public Xol_lang_srl_fxt Init_clear() {
		lang.Num_mgr().Clear();
		return this;
	}
	public Xol_lang_srl_fxt Init_separators(String k, String v) {
		lang.Num_mgr().Separators_mgr().Set(Bry_.new_u8(k), Bry_.new_u8(v));
		return this;
	}
	public void Invk(String raw) {
		app.Gfs_mgr().Run_str_for(lang, raw + ";");
	}
	public void Invk_no_semic(String raw) {
		app.Gfs_mgr().Run_str_for(lang, raw);
	}
	public void Tst_ns_grp(Xol_ns_grp grp, Xow_ns... expd_ns) {
		Tfds.Eq_str_lines(Xto_str(expd_ns), Xto_str(To_ary(grp)));
	}
	public void Run_save_ns_grp(Xol_ns_grp grp, String invk, String raw) {
		Xol_lang_srl.Save_ns_grps(bldr, grp, invk);
		Tfds.Eq_str_lines("." + raw, bldr.Bfr().To_str_and_clear());
	}
	public void Run_save_kwd_mgr(Xol_kwd_mgr kwd_mgr, String invk, String raw) {
		Xol_lang_srl.Save_keywords(bldr, kwd_mgr);
		Tfds.Eq_str_lines("." + raw, bldr.Bfr().To_str_and_clear());
	}
	public void Run_save_msg_mgr(Xol_msg_mgr msg_mgr, String invk, String raw) {
		Xol_lang_srl.Save_messages(bldr, msg_mgr, true);
		Tfds.Eq_str_lines("." + raw, bldr.Bfr().To_str_and_clear());
	}
	public void Run_save_num_mgr(Xol_num_mgr num_mgr, String raw) {
		Xol_lang_srl.Save_num_mgr(bldr, num_mgr);
		Tfds.Eq_str_lines(raw, bldr.Bfr().To_str_and_clear());
	}
	public void Run_save_specials_mgr(Xol_specials_mgr specials_mgr, String invk, String raw) {
		Xol_lang_srl.Save_specials(bldr, specials_mgr);
		Tfds.Eq_str_lines("." + raw, bldr.Bfr().To_str_and_clear());
	}
	public void Tst_num_fmt(String raw, String expd) {Tfds.Eq(expd, String_.new_u8(lang.Num_mgr().Format_num(Bry_.new_u8(raw))));}
	public void Tst_keywords(Xol_kwd_mgr kwd_mgr, Xol_kwd_grp... ary) {
		Tfds.Eq_str_lines(Xto_str(ary), Xto_str(To_ary(kwd_mgr)));
	}
	public void Tst_messages(Xol_msg_mgr msg_mgr, Xol_msg_itm... ary) {
		Tfds.Eq_str_lines(Xto_str(ary), Xto_str(To_ary(msg_mgr)));
	}
	public void Tst_specials(Xol_specials_mgr specials_mgr, Xol_specials_itm... expd) {
		Tfds.Eq_str_lines(Xto_str(expd), Xto_str(To_ary(specials_mgr)));
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
		return sb.To_str_and_clear();
	}
	private Xol_specials_itm[] To_ary(Xol_specials_mgr specials_mgr) {
		int len = specials_mgr.Len();
		Xol_specials_itm[] rv = new Xol_specials_itm[len];
		for (int i = 0; i < len; i++)
			rv[i] = specials_mgr.Get_at(i);
		return rv;
	}
	String Xto_str(Xow_ns[] ary) {
		int len = ary.length;			
		for (int i = 0; i < len; i++) {
			Xow_ns ns = ary[i];
			sb.Add(ns.Id()).Add("|").Add(ns.Name_db_str()).Add_char_nl();
		}
		return sb.To_str_and_clear();
	}
	Xow_ns[] To_ary(Xol_ns_grp ary) {
		int len = ary.Len();			
		Xow_ns[] rv = new Xow_ns[len];
		for (int i = 0; i < len; i++)
			rv[i] = ary.Get_at(i);
		return rv;
	}
	Xol_kwd_grp[] To_ary(Xol_kwd_mgr kwd_mgr) {
		int len = kwd_mgr.Len();
		List_adp rv = List_adp_.New();
		for (int i = 0; i < len; i++) {
			Xol_kwd_grp kwd_grp = kwd_mgr.Get_at(i);
			if (kwd_grp == null) continue;
			rv.Add(kwd_grp);
		}
		return (Xol_kwd_grp[])rv.To_ary(Xol_kwd_grp.class);
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
		return sb.To_str_and_clear();
	}
	Xol_msg_itm[] To_ary(Xol_msg_mgr msg_mgr) {
		int len = msg_mgr.Itms_max();
		List_adp rv = List_adp_.New();
		for (int i = 0; i < len; i++) {
			Xol_msg_itm itm = msg_mgr.Itm_by_id_or_null(i);
			if (itm == null || !itm.Dirty()) continue;
			rv.Add(itm);
		}
		return (Xol_msg_itm[])rv.To_ary(Xol_msg_itm.class);
	}
	String Xto_str(Xol_msg_itm[] ary) {
		int len = ary.length;			
		for (int i = 0; i < len; i++) {
			Xol_msg_itm itm = ary[i];
			sb.Add(itm.Key()).Add("|").Add(itm.Val()).Add_char_nl();
		}
		return sb.To_str_and_clear();
	}
	private static String_bldr sb = String_bldr_.new_();
}

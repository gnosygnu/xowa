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
package gplx.xowa.langs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
import gplx.core.intls.*; import gplx.core.log_msgs.*; import gplx.xowa.parsers.lnkis.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.langs.specials.*;
public class Xol_mw_lang_parser_tst {		
	@Before public void init() {fxt.Clear();} private Xol_mw_lang_parser_fxt fxt = new Xol_mw_lang_parser_fxt();
	@Test  public void Core_keywords() {
		fxt.Parse_core("$magicWords = array('toc' => array(0, 'a1', 'a2', 'a3'), 'notoc' => array(1, 'b1', 'b2', 'b3'));")
			.Tst_keyword(Xol_kwd_grp_.Id_toc, false, "a1", "a2", "a3")
			.Tst_keyword(Xol_kwd_grp_.Id_notoc, true, "b1", "b2", "b3")
			;
	}
	@Test  public void Core_keywords_img_thumb() {
		fxt.Parse_core("$magicWords = array('img_thumbnail' => array(1, 'thumb', 'thmb'));")
			.Tst_keyword_img("thumb", Xop_lnki_arg_parser.Tid_thumb)
			.Tst_keyword_img("thmb"	, Xop_lnki_arg_parser.Tid_thumb)
			.Tst_keyword_img("thum"	, Xop_lnki_arg_parser.Tid_caption)
			;
	}
	@Test  public void Core_keywords_img_upright() {
		fxt.Parse_core("$magicWords = array('img_upright' => array(1, 'upright', 'upright=$1', 'upright $1'));")
			.Tst_keyword_img("upright"	, Xop_lnki_arg_parser.Tid_upright)
			.Tst_keyword_img("upright "	, Xop_lnki_arg_parser.Tid_upright)
			;
	}
	@Test  public void Core_keywords_func_currentmonth() {
		Datetime_now.Manual_y_();
		fxt.Parse_core("$magicWords = array('currentmonth' => array(0, 'MOISACTUEL'));")
			.Tst_parse("{{MOISACTUEL}}", "01")
			;
		Datetime_now.Manual_n_();
	}
	@Test  public void Core_keywords_func_ns() {
		fxt.Parse_core("$magicWords = array('ns' => array(0, 'ESPACEN'));")
			.Tst_parse("{{ESPACEN:6}}", "File")
			;
	}
	@Test  public void Keywords_img_dim() {
		fxt.Parse_core("$magicWords = array('img_width' => array(1, '$1pxl'));")
			.Tst_keyword_img("50pxl", Xop_lnki_arg_parser.Tid_dim)
			.Tst_keyword_img("50pxlpxl", Xop_lnki_arg_parser.Tid_dim)
			.Tst_keyword_img("50xl", Xop_lnki_arg_parser.Tid_caption)
			;
	}
	@Test  public void Core_namespaces_names() {
		fxt.Parse_core("$namespaceNames = array(NS_FILE => 'Fichier');")
			.Sync_ns()
			.Tst_ns_lkp("Fichier", Xow_ns_.Tid__file)
			.Tst_ns_lkp("File"	, Xow_ns_.Tid__null)
			;
	}
	@Test  public void Core_namespaces_aliases() {
		fxt.Parse_core("$namespaceAliases = array('Discussion_Fichier' => NS_FILE_TALK);")
			.Sync_ns()
			.Tst_ns_lkp("Discussion Fichier", Xow_ns_.Tid__file_talk)
			.Tst_ns_lkp("Discussion Fichierx", Xow_ns_.Tid__null)
			;
	}
	@Test  public void Core_specialPageAliases() {
		fxt.Parse_core("$specialPageAliases = array('Randompage' => array('PageAuHasard', 'Page_au_hasard'));")
			.Test_specialPageAliases("Randompage", "PageAuHasard", "Page_au_hasard")
			;
	}
	@Test  public void Xtn_keywords_fr() {fxt.Parse_xtn("$magicWords['fr'] = array('if' => array(0, 'si' ));").Tst_parse("{{si:|y|n}}", "n");}
	@Test  public void Xtn_keywords_de() {fxt.Parse_xtn("$magicWords['de'] = array('if' => array(0, 'si' ));").Tst_parse("{{si:|y|n}}", "<a href=\"/wiki/Template:Si:\">Template:Si:</a>");}	// should be "Si", not "si"; ALSO, should probably be "{{si:|y|n}}" not "[[:Template:si:]]" DATE:2014-07-04
	@Test  public void Core_messages() {
		fxt.Parse_core("$messages = array('sunday' => 'dimanche');")
			.Tst_message("sunday", 0, "dimanche", false)
			;
	}
	@Test  public void Fallback() {
		fxt.Parse_core("$fallback = 'zh-hans';");
		Tfds.Eq("zh-hans", String_.new_u8(fxt.Lang().Fallback_bry()));
	}
	@Test  public void Separator_transform_table() {
		fxt.Parse_core("$separatorTransformTable = array( ',' => '.', '.' => ',' );");
		fxt.Num_fmt_tst("1234,56", "1.234.56");	// NOTE: dot is repeated; confirmed with dewiki and {{formatnum:1234,56}}
	}
	@Test  public void Separator_transform_table_fr() {
		fxt.Parse_core("$separatorTransformTable = array( ',' => '\\xc2\\xa0', '.' => ',' );");
		fxt.Num_fmt_tst("1234,56", "1 234 56");	// NOTE: nbsp here; also, nbsp is repeated. see dewiki and {{formatnum:1234,56}}
	}
	@Test  public void Digit_transform_table() {
		fxt.Save_file("mem/xowa/bin/any/xowa/cfg/lang/mediawiki/core_php/MessagesFr.php"
		, "$digitTransformTable = array("
		, "  '0' => 'nulla',"
		, "  '1' => 'I',"
		, "  '2' => 'II',"
		, "  '3' => 'III',"
		, "  '4' => 'IV',"
		, "  '5' => 'V',"
		, "  '6' => 'VI',"
		, "  '7' => 'VII',"
		, "  '8' => 'VIII',"
		, "  '9' => 'IX',"
		, ");"
		);
		fxt.Run_bld_all();
		fxt.Tst_file("mem/xowa/bin/any/xowa/cfg/lang/core/fr.gfs", String_.Concat_lines_nl
		( "numbers {"
		, "  digits {"
		, "    clear;"
		, "    set('0', 'nulla');"
		, "    set('1', 'I');"
		, "    set('2', 'II');"
		, "    set('3', 'III');"
		, "    set('4', 'IV');"
		, "    set('5', 'V');"
		, "    set('6', 'VI');"
		, "    set('7', 'VII');"
		, "    set('8', 'VIII');"
		, "    set('9', 'IX');"
		, "  }"
		, "}"
		, "this"
		, ";"
		));
	}
	@Test  public void Digit_grouping_pattern() {
		fxt.Save_file("mem/xowa/bin/any/xowa/cfg/lang/mediawiki/core_php/MessagesFr.php"
		, "$digitGroupingPattern = '##,##,###'"
		, ");"
		);
		fxt.Run_bld_all();
		fxt.Tst_file("mem/xowa/bin/any/xowa/cfg/lang/core/fr.gfs", String_.Concat_lines_nl
		( "numbers {"
		, "  digit_grouping_pattern = '##,##,###';"
		, "}"
		, "this"
		, ";"
		));
	}
	@Test   public void Bld() {
		fxt.Save_file("mem/xowa/bin/any/xowa/cfg/lang/mediawiki/core_php/MessagesFr.php"
		, "$fallback = 'zh-hans';"
		, "$rtl = true;"
		, "$namespaceNames = array(NS_FILE => 'Filex');"
		, "$namespaceAliases = array('File Discussion' => NS_FILE_TALK);"
		, "$magicWords = array('currentmonth' => array(0, 'CUR_MONTH'));"
		, "$messages = array('sunday' => 'sunday');"
		);
		fxt.Save_file("mem/xowa/bin/any/xowa/cfg/lang/mediawiki/xtns_php/Test.il8n.php"
		, "$magicWords['fr'] = array('currentyear' => array(0, 'CUR_YEAR'));"
		, "$messages['fr'] = array('monday' => 'monday');"
		);
		fxt.Save_file("mem/xowa/bin/any/xowa/cfg/lang/mediawiki/core_json/Messages/fr.json"
		, "{"
		, "    \"@metadata\": {"
		, "        \"authors\": []"
		, "    },"
		, "\"key_1\": \"val_1\","
		, "\"key_2\": \"val $1\","
		, "}"
		);
		fxt.Save_file("mem/xowa/bin/any/xowa/cfg/lang/mediawiki/xtns_json/Test2/fr.json"
		, "{"
		, "    \"@metadata\": {"
		, "        \"authors\": []"
		, "    },"
		, "\"key_3\": \"val_3\","
		, "\"key_4\": \"val $1\","
		, "}"
		);
		fxt.Run_bld_all();
		fxt.Tst_file("mem/xowa/bin/any/xowa/cfg/lang/core/fr.gfs", String_.Concat_lines_nl
		( "this"
		, ".fallback_load('zh-hans')"
		, ".dir_rtl_('y')"
		, ".ns_names"
		, "  .load_text("
		, "<:['"
		, "6|Filex"
		, "']:>"
		, ").lang"
		, ".ns_aliases"
		, "  .load_text("
		, "<:['"
		, "7|File Discussion"
		, "']:>"
		, ").lang"
		, ".keywords"
		, "  .load_text("
		, "<:['"
		, "currentmonth|0|CUR_MONTH~"
		, "currentyear|0|CUR_YEAR~"
		, "']:>"
		, ").lang"
		, ".messages"
		, "  .load_text("
		, "<:['"
		, "sunday|sunday"
		, "monday|monday"
		, "key_1|val_1"
		, "key_2|val ~{0}"
		, "key_3|val_3"
		, "key_4|val ~{0}"
		, "']:>"
		, ").lang"
		, ";"
		));
	}
	@Test   public void Dir_ltr() {
		fxt.Parse_core("$rtl = 'true';");
		Tfds.Eq(false, fxt.Lang().Dir_ltr());
	}
	@Test  public void Core_keywords__only_1() {	// PURPOSE: some magic words don't specify case-match; EX: Disambiguator.php; DATE:2013-12-24
		fxt.Parse_core("$magicWords = array('toc' => array('a1'));")
			.Tst_keyword(Xol_kwd_grp_.Id_toc, false, "a1")
			;
	}
	@Test  public void Core_keywords__skip_case_match__1() {	// PURPOSE: some magic words don't specify case-match; EX: Disambiguator.php; DATE:2013-12-24
		fxt.Parse_core("$magicWords = array('toc' => array('a1'));")
			.Tst_keyword(Xol_kwd_grp_.Id_toc, false, "a1")
			;
	}
	@Test  public void Core_keywords__skip_case_match__2() {	// PURPOSE: some magic words don't specify case-match; EX: Disambiguator.php; DATE:2013-12-24
		fxt.Parse_core("$magicWords = array('toc' => array('a1', 'a2'));")
			.Tst_keyword(Xol_kwd_grp_.Id_toc, false, "a1", "a2")
			;
	}
}
class Xol_mw_lang_parser_fxt {
	Xoae_app app; Xowe_wiki wiki; private Xop_fxt fxt;
	Xol_mw_lang_parser parser = new Xol_mw_lang_parser(Gfo_msg_log.Test()); Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	public void Clear() {
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
		}
		app.Lang_mgr().Clear();// NOTE: always clear the lang
		lang = app.Lang_mgr().Get_by_or_new(Bry_.new_a7("fr"));
		wiki = Xoa_app_fxt.Make__wiki__edit(app, "en.wikipedia.org", lang);
		fxt = new Xop_fxt(app, wiki);
		lang.Kwd_mgr().Clear(); lang.Msg_mgr().Clear();	// NOTE: clear kwds and msgs else they will be printed to file; this line must go last b/c various xtns will fill in kwds dynamically
	}
	public Xol_lang_itm Lang() {return lang;} private Xol_lang_itm lang;
	public void Num_fmt_tst(String raw, String expd) {Tfds.Eq(expd, String_.new_u8(lang.Num_mgr().Format_num(Bry_.new_u8(raw))));}
	public void Run_bld_all() {parser.Bld_all(app.Lang_mgr(), app.Fsys_mgr());}
	public void Save_file(String path, String... lines) {
		Io_mgr.Instance.SaveFilStr(Io_url_.mem_fil_(path), String_.Concat_lines_nl(lines));
	}
	public void Tst_file(String path, String expd) {
		Io_url url = Io_url_.mem_fil_(path);
		String actl = Io_mgr.Instance.LoadFilStr(url);
		Tfds.Eq_str_lines(expd, actl);
	}
	public Xol_mw_lang_parser_fxt Parse_core(String raw)				{parser.Parse_core(raw, lang, tmp_bfr, Xol_lang_transform_null.Instance); return this;}
	public Xol_mw_lang_parser_fxt Parse_xtn (String raw)				{parser.Parse_xtn(raw, Io_url_.Empty, app.Lang_mgr(), tmp_bfr, false, Xol_lang_transform_null.Instance); lang.Evt_lang_changed(); return this;}
	public Xol_mw_lang_parser_fxt Tst_keyword(int id, boolean case_sensitive, String... words) {
		Xol_kwd_grp lst = lang.Kwd_mgr().Get_at(id); if (lst == null) throw Err_.new_wo_type("list should not be null");
		Tfds.Eq(case_sensitive, lst.Case_match());
		int actl_len = lst.Itms().length;
		String[] actl = new String[actl_len];
		for (int i = 0; i < actl_len; i++)
			actl[i] = String_.new_u8(lst.Itms()[i].Val());
		Tfds.Eq_ary_str(words, actl);
		return this;
	}
	public Xol_mw_lang_parser_fxt Tst_keyword_img(String key_str, byte tid) {
		byte[] key = Bry_.new_u8(key_str);
		Tfds.Eq(tid, lang.Lnki_arg_parser().Identify_tid(key, 0, key.length));
		return this;
	}
	public Xol_mw_lang_parser_fxt Tst_parse(String raw, String expd) {
		fxt.Reset();
		fxt.Test_parse_page_all_str(raw, expd);
		return this;
	}
	public Xol_mw_lang_parser_fxt Tst_ns_lkp(String key_str, int id) {
		byte[] key = Bry_.new_u8(key_str);
		Xow_ns ns = (Xow_ns)wiki.Ns_mgr().Names_get_or_null(key, 0, key.length);
		int actl = ns == null ? Xow_ns_.Tid__null : ns.Id();
		Tfds.Eq(id, actl);
		return this;
	}
	public Xol_mw_lang_parser_fxt Test_specialPageAliases(String special, String... expd_aliases) {
		Xol_specials_itm actl_aliases = lang.Specials_mgr().Get_by_key(Bry_.new_u8(special));
		Tfds.Eq_ary_str(expd_aliases, To_str_ary(actl_aliases));
		return this;
	}
	private String[] To_str_ary(Xol_specials_itm itm) {
		int len = itm.Aliases().length;
		String[] rv = new String[len];
		for (int i = 0; i < len; i++) {
			rv[i] = String_.new_u8((byte[])itm.Aliases()[i]);
		}
		return rv;
	}
	public Xol_mw_lang_parser_fxt Sync_ns() {
		Xow_ns_mgr_.rebuild_(lang, wiki.Ns_mgr());
		return this;
	}
	public Xol_mw_lang_parser_fxt Tst_message(String key, int id, String val, boolean fmt) {
		Xol_msg_itm itm = lang.Msg_mgr().Itm_by_key_or_new(Bry_.new_a7(key));
		Tfds.Eq(id, itm.Id());
		Tfds.Eq(val, String_.new_u8(itm.Val()));
		Tfds.Eq(fmt, itm.Has_fmt_arg());
		return this;
	}
}

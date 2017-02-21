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
package gplx.xowa.langs.vnts.converts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*; import gplx.xowa.langs.vnts.*;
import org.junit.*;
import gplx.xowa.langs.vnts.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.drds.*;
public class Xol_convert_regy_tst {
	private final    Xol_convert_regy_fxt fxt = new Xol_convert_regy_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		// fxt.Parser_fxt().Init_page_create("Template:Test_x1", "val");
		fxt.Init_page("Template:Test_x1", "val");
		fxt.Parser_fxt().Test_parse_tmpl_str_test("{{Test_x0}}", "{{test}}", "val");
	}
	@Test  public void Upper_1st() {	// PURPOSE: convert should call Xoa_ttl.Parse(), which will upper 1st letter; EX:{{jez-eng|sense}} -> Jez-eng; PAGE:sr.w:ДНК DATE:2014-07-06
		fxt.Init_page("Template:X1", "val");
		fxt.Parser_fxt().Test_parse_tmpl_str_test("{{x0}}", "{{test}}", "val");
	}
	@Test  public void Redlink() {		// PURPOSE: check redlink's Convert_ttl(Xowe_wiki wiki, Xoa_ttl ttl); DATE:2014-07-06
		fxt.Init_page("Template:Test_x1", "val");
		fxt.Test_convert_by_ttl("zh", "Template:Test_x0", Bool_.Y);	// Template:Test_xo should not be parsed to Template:Template:Test_x0; EX:Шаблон:Šablon:Jez-eng; PAGE:sr.w:ДНК DATE:2014-07-06
		fxt.Test_convert_by_ttl("zh", "Template:Test_x1", Bool_.N);	// note that convert of trg should not find title;
		fxt.Test_convert_by_ttl("zh", "Template:Test_x2", Bool_.N);	// test that non-convert characters return false
	}
	@Test  public void Pfunc() {
		fxt.Parser_fxt().Init_defn_clear();
		fxt.Init_page("Test_x1", "");
		fxt.Test_parse("{{#ifexist:Test_x0|y|n}}", "y");
	}
}
class Xol_convert_regy_fxt {
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public Xop_fxt Parser_fxt() {return parser_fxt;} private Xop_fxt parser_fxt;
	public Xowd_data_tstr Data_mgr() {return data_mgr;} private final    Xowd_data_tstr data_mgr = new Xowd_data_tstr();
	public void Clear() {
		app = Xoa_app_fxt.Make__app__edit();
		Xol_lang_itm lang = app.Lang_mgr().Get_by_or_new(Bry_.new_a7("zh"));
		Xol_lang_itm_.Lang_init(lang);
		Init_cnv(app, "zh", "zh-hant", Keyval_.new_("x0", "x1"));
		wiki = Xoa_app_fxt.Make__wiki__edit(app, "zh.wikipedia.org", lang);
		Xoa_test_.Init__db__edit(wiki);
		data_mgr.Wiki_(wiki);
		gplx.xowa.langs.vnts.Xol_vnt_regy_fxt.Init__vnt_mgr(wiki.Lang().Vnt_mgr(), 1, String_.Ary("zh", "zh-hans", "zh-hant"));
		parser_fxt = new Xop_fxt(app, wiki);
	}
	public void Init_page(String ttl, String wtxt) {Xow_data_fxt.Create(wiki, data_mgr, ttl, wtxt);}
	public static void Init_cnv(Xoae_app app, String lang_key, String vnt_key, Keyval... ary) {
		Xol_lang_itm lang = app.Lang_mgr().Get_by_or_new(Bry_.new_a7(lang_key));
		Xol_convert_grp grp = lang.Vnt_mgr().Convert_mgr().Converter_regy().Get_or_make(Bry_.new_a7(vnt_key));
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Keyval itm = ary[i];
			grp.Add(Bry_.new_u8(itm.Key()), Bry_.new_u8(itm.Val_to_str_or_empty()));
		}
		Xol_vnt_itm vnt_itm = lang.Vnt_mgr().Regy__get_or_new(Bry_.new_a7(vnt_key));
		vnt_itm.Convert_ary_(Bry_.Ary(vnt_key));
		vnt_itm.Convert_wkr().Init(lang.Vnt_mgr().Convert_mgr().Converter_regy(), vnt_itm.Convert_ary());
	}
	public void Test_parse(String raw, String expd) {
		parser_fxt.Test_parse_page_all_str(raw, expd);
	}
	public void Test_convert_by_ttl(String lang_key, String raw, boolean expd) {
		Xol_lang_itm lang = app.Lang_mgr().Get_by_or_new(Bry_.new_a7(lang_key));
		Xoa_ttl ttl = wiki.Ttl_parse(Bry_.new_u8(raw));
		Xowd_page_itm page = lang.Vnt_mgr().Convert_mgr().Convert_ttl(wiki, ttl);
		if (expd)
			Tfds.Eq_true(page.Exists());
		else
			Tfds.Eq_null(page);
	}
}
class Xow_data_fxt {
	public static void Create(Xow_wiki wiki, Xowd_data_tstr tstr, String ttl_str, String wtxt) {
		tstr.Page__insert(1, ttl_str, "2015-10-19 00:01:02");
		tstr.Text__insert(1, wtxt);
	}
}

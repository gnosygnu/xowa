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
package gplx.xowa.xtns.pfuncs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*; import gplx.xowa.langs.*;
public class Pfunc_i18n_tst {		
	@Before public void init() {fxt.Clear();} private Pfunc_i18n_fxt fxt = new Pfunc_i18n_fxt();
	@Test  public void Casing()	{fxt.lang_("de").Ini().Reg_func("fullurl", false, "VOLLSTÄNDIGE_URL").Load().Fxt().Test_parse_tmpl_str_test("{{vollstÄndige_url:a}}", "{{test}}"	, "//de.wikipedia.org/wiki/A");}
	@Test  public void Time()	{fxt.lang_("de").Ini().Reg_msg("march", "März").Load().Fxt().Test_parse_tmpl_str_test("{{#time: d F Y|1 Mar 2013}}", "{{test}}"	, "01 März 2013");}
}
class Pfunc_i18n_fxt {
	public void Clear() {}
	public Xop_fxt Fxt() {return fxt;}
	public Pfunc_i18n_fxt lang_(String v) {lang_key = v; return this;} private String lang_key;
	public Pfunc_i18n_fxt Ini() {
		if (app == null) app = Xoa_app_fxt.Make__app__edit();
		app.Lang_mgr().Clear();	// else lang values retained from last run
		app.Free_mem(false); // else tmpl_result_cache will get reused from last run for {{test}}
		lang = app.Lang_mgr().Get_by_or_new(Bry_.new_a7(lang_key));
		wiki = Xoa_app_fxt.Make__wiki__edit(app, lang_key + ".wikipedia.org", lang);
		fxt = new Xop_fxt(app, wiki);
		return this;
	}	private Xoae_app app; private Xop_fxt fxt; Xol_lang_itm lang; Xowe_wiki wiki;
	public Pfunc_i18n_fxt Reg_func(String name, boolean case_match, String word) {
		Io_url url = Io_url_.mem_fil_("mem/xowa/bin/any/xowa/cfg/lang/core/" + lang_key + ".gfs");
		String func = "keywords.load_text('" + name + "|" + (case_match ? "1" : "0") + "|" + name + "~" + word + "~');";
		Io_mgr.Instance.SaveFilStr(url, func);
		return this;
	}
	public Pfunc_i18n_fxt Reg_msg(String key, String val) {
		Io_url url = Io_url_.mem_fil_("mem/xowa/bin/any/xowa/cfg/lang/core/" + lang_key + ".gfs");
		String func = "messages.load_text('" + key + "|" + val + "');";
		Io_mgr.Instance.SaveFilStr(url, func);
		return this;
	}
	public Pfunc_i18n_fxt Load() {
		lang.Init_by_load();
		wiki.Fragment_mgr().Evt_lang_changed(lang);
		return this;
	}
}


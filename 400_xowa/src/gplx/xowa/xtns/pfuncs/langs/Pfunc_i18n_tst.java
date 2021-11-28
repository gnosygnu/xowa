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
	private final    Pfunc_i18n_fxt fxt = new Pfunc_i18n_fxt();
	@Test public void Casing()	{
		fxt.Init__func("de", "fullurl", false, "VOLLSTÄNDIGE_URL");
		fxt.Init__lang("de");
		fxt.Test__parse("{{vollstÄndige_url:a}}", "{{test}}"	, "//de.wikipedia.org/wiki/A");
	}
	@Test public void Time() {
		fxt.Init__msg("de", "march", "März");
		fxt.Init__lang("de");
		fxt.Test__parse("{{#time: d F Y|1 Mar 2013}}", "{{test}}"	, "01 März 2013");
	}
}
class Pfunc_i18n_fxt {
	private Xop_fxt parser_fxt;
	public void Init__func(String lang_key, String name, boolean case_match, String word) {
		Io_url url = Io_url_.mem_fil_("mem/xowa/bin/any/xowa/cfg/lang/core/" + lang_key + ".gfs");
		String func = "keywords.load_text('" + name + "|" + (case_match ? "1" : "0") + "|" + name + "~" + word + "~');";
		Io_mgr.Instance.SaveFilStr(url, func);
	}
	public void Init__msg(String lang_key, String key, String val) {
		Io_url url = Io_url_.mem_fil_("mem/xowa/bin/any/xowa/cfg/lang/core/" + lang_key + ".gfs");
		String func = "messages.load_text('" + key + "|" + val + "');";
		Io_mgr.Instance.SaveFilStr(url, func);
	}
	public void Init__lang(String lang_key) {
		Xoae_app app = Xoa_app_fxt.Make__app__edit(false);
		Xol_lang_itm lang = app.Lang_mgr().Get_by_or_load(Bry_.new_a7(lang_key));
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app, lang_key + ".wikipedia.org", lang);
		this.parser_fxt = new Xop_fxt(app, wiki);
	}
	public void Test__parse(String tmpl_raw, String page_raw, String expd) {
		parser_fxt.Test_parse_tmpl_str_test(tmpl_raw, page_raw, expd);
	}
}

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
package gplx.xowa.xtns.pfuncs.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
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
		if (app == null) app = Xoa_app_fxt.app_();
		app.Lang_mgr().Clear();	// else lang values retained from last run
		app.Free_mem(false); // else tmpl_result_cache will get reused from last run for {{test}}
		lang = app.Lang_mgr().Get_by_key_or_new(Bry_.new_ascii_(lang_key));
		wiki = Xoa_app_fxt.wiki_(app, lang_key + ".wikipedia.org", lang);
		fxt = new Xop_fxt(app, wiki);
		return this;
	}	private Xoae_app app; private Xop_fxt fxt; Xol_lang lang; Xowe_wiki wiki;
	public Pfunc_i18n_fxt Reg_func(String name, boolean case_match, String word) {
		Io_url url = Io_url_.mem_fil_("mem/xowa/bin/any/xowa/cfg/lang/core/" + lang_key + ".gfs");
		String func = "keywords.load_text('" + name + "|" + (case_match ? "1" : "0") + "|" + name + "~" + word + "~');";
		Io_mgr._.SaveFilStr(url, func);
		return this;
	}
	public Pfunc_i18n_fxt Reg_msg(String key, String val) {
		Io_url url = Io_url_.mem_fil_("mem/xowa/bin/any/xowa/cfg/lang/core/" + lang_key + ".gfs");
		String func = "messages.load_text('" + key + "|" + val + "');";
		Io_mgr._.SaveFilStr(url, func);
		return this;
	}
	public Pfunc_i18n_fxt Load() {
		lang.Init_by_load();
		wiki.Fragment_mgr().Evt_lang_changed(lang);
		return this;
	}
}


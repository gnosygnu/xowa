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
package gplx.xowa.langs.cnvs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
import gplx.xowa.langs.vnts.*;
public class Xol_cnv_mgr_tst {
	private Xol_cnv_mgr_fxt fxt = new Xol_cnv_mgr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		fxt.Parser_fxt().Init_page_create("Template:Test_x1", "val");
		fxt.Parser_fxt().Test_parse_tmpl_str_test("{{Test_x0}}", "{{test}}", "val");
	}
	@Test  public void Upper_1st() {	// PURPOSE: convert should call Xoa_ttl.parse_(), which will upper 1st letter; EX:{{jez-eng|sense}} -> Jez-eng; PAGE:sr.w:ДНК DATE:2014-07-06
		fxt.Parser_fxt().Init_page_create("Template:X1", "val");
		fxt.Parser_fxt().Test_parse_tmpl_str_test("{{x0}}", "{{test}}", "val");
	}
	@Test  public void Redlink() {		// PURPOSE: check redlink's Convert_ttl(Xow_wiki wiki, Xoa_ttl ttl); DATE:2014-07-06
		fxt.Parser_fxt().Init_page_create("Template:Test_x1", "val");
		fxt.Test_convert_by_ttl("zh", "Template:Test_x0", Bool_.Y);	// Template:Test_xo should not be parsed to Template:Template:Test_x0; EX:Шаблон:Šablon:Jez-eng; PAGE:sr.w:ДНК DATE:2014-07-06
		fxt.Test_convert_by_ttl("zh", "Template:Test_x1", Bool_.N);	// note that convert of trg should not find title;
		fxt.Test_convert_by_ttl("zh", "Template:Test_x2", Bool_.N);	// test that non-convert characters return false
	}
	@Test  public void Pfunc() {
		fxt.Parser_fxt().Init_defn_clear();
		fxt.Parser_fxt().Init_page_create("Test_x1");
		fxt.Test_parse("{{#ifexist:Test_x0|y|n}}", "y");
	}
}
class Xol_cnv_mgr_fxt {
	public Xoa_app App() {return app;} private Xoa_app app;
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public Xop_fxt Parser_fxt() {return parser_fxt;} private Xop_fxt parser_fxt;
	public void Clear() {
		app = Xoa_app_fxt.app_();
		Xol_lang lang = app.Lang_mgr().Get_by_key_or_new(Bry_.new_utf8_("zh"));
		Xol_lang_.Lang_init(lang);
		Init_cnv(app, "zh", "zh-hant", KeyVal_.new_("x0", "x1"));
		lang.Vnt_mgr().Enabled_(true);
		lang.Vnt_mgr().Convert_ttl_init();
		wiki = Xoa_app_fxt.wiki_(app, "zh.wikipedia.org", lang);
		parser_fxt = new Xop_fxt(app, wiki);
	}
	public static void Init_cnv(Xoa_app app, String lang_key, String vnt_key, KeyVal... ary) {
		Xol_lang lang = app.Lang_mgr().Get_by_key_or_new(Bry_.new_ascii_(lang_key));
		Xol_cnv_grp grp = lang.Cnv_mgr().Get_or_make(Bry_.new_ascii_(vnt_key));
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			KeyVal itm = ary[i];
			grp.Add(Bry_.new_utf8_(itm.Key()), Bry_.new_utf8_(itm.Val_to_str_or_empty()));
		}
		Xol_vnt_itm vnt_itm = lang.Vnt_mgr().Get_or_new(Bry_.new_ascii_(vnt_key));
		vnt_itm.Convert_ary_(Bry_.Ary(vnt_key));
		vnt_itm.Converter().Rebuild();
	}
//		public void Test_convert(String lang, String vnt, String raw, String expd) {
//			Xol_cnv_grp convert_grp = app.Lang_mgr().Get_by_key_or_new(Bry_.new_ascii_(lang)).Cnv_mgr().Get_or_new(Bry_.new_ascii_(vnt));
//			Bry_bfr bfr = Bry_bfr.new_();
//			boolean converted = convert_grp.Convert_to_bfr(bfr, Bry_.new_utf8_(raw));
//			String actl = converted ? bfr.XtoStrAndClear() : raw;
//			Tfds.Eq(expd, actl);
//		}
	public void Test_parse(String raw, String expd) {
		parser_fxt.Test_parse_page_all_str(raw, expd);
	}
	public void Test_convert_by_ttl(String lang_key, String raw, boolean expd) {
		Xol_lang lang = app.Lang_mgr().Get_by_key_or_new(Bry_.new_ascii_(lang_key));
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, Bry_.new_utf8_(raw));
		Xodb_page page = lang.Vnt_mgr().Convert_ttl(wiki, ttl);
		if (expd)
			Tfds.Eq_true(page.Exists());
		else
			Tfds.Eq_null(page);
	}
}

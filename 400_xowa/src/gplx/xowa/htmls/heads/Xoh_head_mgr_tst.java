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
package gplx.xowa.htmls.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*;
import gplx.xowa.guis.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
public class Xoh_head_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xoh_head_mgr_fxt fxt = new Xoh_head_mgr_fxt();
	@Test   public void Css() {
		fxt.Mgr().Itm__css().Enabled_y_();
		fxt.Test_write(String_.Concat_lines_nl_skip_last
		( ""
		, "  <style type=\"text/css\">"
		, "    .xowa-missing-category-entry {color: red;}"
		, "  </style>"
		));
	}
	@Test   public void Toc() {
		fxt.Init_msg(Xoh_head_itm__toc.Key_showtoc, "Sh\"ow");
		fxt.Init_msg(Xoh_head_itm__toc.Key_hidetoc, "Hi'de");
		fxt.Mgr().Itm__toc().Enabled_y_();
		fxt.Test_write(String_.Concat_lines_nl_skip_last
		( ""
		, "  <script type='text/javascript'>"
		, "    var xowa_global_values = {"
		, "      'toc-enabled' : true,"
		, "      'mw_hidetoc' : '0',"
		, "      'showtoc' : 'Sh\"ow',"
		, "      'hidetoc' : 'Hi\\'de',"
		, "    }"
		, "  </script>"
		));
	}
	@Test  public void Globals() {
		fxt.Init_msg(Xol_msg_itm_.Id_dte_month_name_january, "Jan'uary" );	// add apos to simulate apostrophes in Hebrew months; DATE:2014-07-28
		fxt.Mgr().Itm__globals().Enabled_y_();
		fxt.Test_write(String_.Concat_lines_nl_skip_last
		( ""
		, "  <link rel=\"stylesheet\" href=\"file:///mem/xowa/bin/any/xowa/html/res/src/xowa/core/core.css\" type='text/css'>"
		, "  <script type='text/javascript'>"
		, "    var xowa_root_dir = 'file:///mem/xowa/';"
		, "    var xowa_mode_is_server = false;"
		, "    var xowa_global_values = {"
		, "      'mode_is_gui' : false,"
		, "      'mode_is_http' : false,"
		, "      'http-port' : 8080,"
		, "      'sort-ascending' : 'Sort ascending',"
		, "      'sort-descending' : 'Sort descending',"
		, "      'wgContentLanguage' : 'en',"
		, "      'wgSeparatorTransformTable' : ['.\t.', ',\t,'],"
		, "      'wgDigitTransformTable' : ['', ''],"
		, "      'wgDefaultDateFormat' : 'dmy',"
		, "      'wgMonthNames' : ['', 'Jan\\'uary', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],"
		, "      'wgMonthNamesShort' : ['', 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],"
		, "    }"
		, "  </script>"
		, "  <script src=\"file:///mem/xowa/bin/any/xowa/html/res/src/xowa/core/core.js\" type='text/javascript'></script>"
		, "  <script src=\"file:///mem/xowa/bin/any/xowa/html/res/src/xowa/core/exec.js\" type='text/javascript'></script>"
		, "  <script src=\"file:///mem/xowa/bin/any/xowa/html/res/src/xowa/core/DOMContentLoaded.js\" type='text/javascript'></script>"
		, "  <script type='text/javascript'>"
		, "    xowa.app.mode = 'cmd';"
		, "    var x_p = xowa.page;"
		, "    x_p.wiki = 'en.wikipedia.org';"
		, "    x_p.ttl = 'Test_page';"
		, "  </script>"
		));
		fxt.Init_msg(Xol_msg_itm_.Id_dte_month_name_january, "January" );	// set it back
	}
	@Test   public void Globals_la() { // PURPOSE: la.gfs only specifies "," not "."; make sure both "." and "," show up, or else null ref error during import; DATE:2014-05-13
		Xol_lang_itm la_lang = fxt.Wiki().Lang();
		gplx.xowa.langs.numbers.Xol_transform_mgr separators_mgr = la_lang.Num_mgr().Separators_mgr();
		separators_mgr.Clear();
		separators_mgr.Set(gplx.xowa.langs.numbers.Xol_num_mgr.Separators_key__grp, Bry_.new_a7(" "));
		fxt.Mgr().Itm__globals().Enabled_y_();
		fxt.Test_write(String_.Concat_lines_nl_skip_last
		( ""
		, "  <link rel=\"stylesheet\" href=\"file:///mem/xowa/bin/any/xowa/html/res/src/xowa/core/core.css\" type='text/css'>"
		, "  <script type='text/javascript'>"
		, "    var xowa_root_dir = 'file:///mem/xowa/';"
		, "    var xowa_mode_is_server = false;"
		, "    var xowa_global_values = {"
		, "      'mode_is_gui' : false,"
		, "      'mode_is_http' : false,"
		, "      'http-port' : 8080,"
		, "      'sort-ascending' : 'Sort ascending',"
		, "      'sort-descending' : 'Sort descending',"
		, "      'wgContentLanguage' : 'en',"
		, "      'wgSeparatorTransformTable' : ['.\t.', ' \t,'],"	// note that grp spr (",") is ""
		, "      'wgDigitTransformTable' : ['', ''],"
		, "      'wgDefaultDateFormat' : 'dmy',"
		, "      'wgMonthNames' : ['', 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],"
		, "      'wgMonthNamesShort' : ['', 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],"
		, "    }"
		, "  </script>"
		, "  <script src=\"file:///mem/xowa/bin/any/xowa/html/res/src/xowa/core/core.js\" type='text/javascript'></script>"
		, "  <script src=\"file:///mem/xowa/bin/any/xowa/html/res/src/xowa/core/exec.js\" type='text/javascript'></script>"
		, "  <script src=\"file:///mem/xowa/bin/any/xowa/html/res/src/xowa/core/DOMContentLoaded.js\" type='text/javascript'></script>"
		, "  <script type='text/javascript'>"
		, "    xowa.app.mode = 'cmd';"
		, "    var x_p = xowa.page;"
		, "    x_p.wiki = 'en.wikipedia.org';"
		, "    x_p.ttl = 'Test_page';"
		, "  </script>"
		));
	}
}
class Xoh_head_mgr_fxt {
	private final    Xop_fxt fxt = new Xop_fxt();
	private Xoh_head_mgr mgr;
	private Bry_bfr bfr = Bry_bfr_.Reset(255);
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public Xoh_head_mgr Mgr() {return mgr;}
	public Xol_lang_itm Make_lang(String key) {return wiki.Appe().Lang_mgr().Get_by_or_new(Bry_.new_a7(key));}
	public void Clear() {
		fxt.Reset();
		mgr = fxt.Page().Html_data().Head_mgr();
		wiki = fxt.Wiki();
	}
	public void Init_msg(byte[] key, String val) {
		wiki.Msg_mgr().Get_or_make(key).Atrs_set(Bry_.new_a7(val), false, false);
	}
	public void Init_msg(int id, String val) {
		Xol_msg_itm msg_itm = wiki.Lang().Msg_mgr().Itm_by_id_or_null(id);
		msg_itm.Atrs_set(Bry_.new_a7(val), false, false);
	}
	public void Test_write(String expd) {
		mgr.Write(bfr, fxt.App(), wiki, fxt.Page());
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
}

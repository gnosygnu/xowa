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
package gplx.xowa.html.modules; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import org.junit.*;
import gplx.xowa.gui.*;
public class Xoh_module_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xoh_module_mgr_fxt fxt = new Xoh_module_mgr_fxt();
	@Test   public void Css() {
		fxt.Mgr().Itm_css().Enabled_y_();
		fxt.Test_write(String_.Concat_lines_nl_skip_last
		( ""
		, "  <style type=\"text/css\">"
		, "    .xowa-missing-category-entry {color: red;}"
		, "  </style>"
		));
	}
	@Test   public void Toc() {
		fxt.Init_msg(Xoh_module_itm__toc.Key_showtoc, "Sh\"ow");
		fxt.Init_msg(Xoh_module_itm__toc.Key_hidetoc, "Hi'de");
		fxt.Mgr().Itm_toc().Enabled_y_();
		fxt.Test_write(String_.Concat_lines_nl_skip_last
		( ""
		, "  <script type='text/javascript'>"
		, "    var xowa_global_values = {"
		, "      'toc-enabled' : true,"
		, "      'mw_hidetoc' : '0',"
		, "      'showtoc' : 'Sh\"ow',"
		, "      'hidetoc' : 'Hi''de',"
		, "    }"
		, "  </script>"
		));
	}
}
class Xoh_module_mgr_fxt {
	private Xop_fxt fxt = new Xop_fxt();
	private Xoh_module_mgr mgr;
	private Bry_bfr bfr = Bry_bfr.reset_(255);
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public Xoh_module_mgr Mgr() {return mgr;}
	public void Clear() {
		fxt.Reset();
		mgr = fxt.Page().Html_data().Module_mgr();
		wiki = fxt.Wiki();
	}
	public void Init_msg(byte[] key, String val) {
		wiki.Msg_mgr().Get_or_make(key).Atrs_set(Bry_.new_ascii_(val), false, false);
	}
	public void Test_write(String expd) {
		mgr.Write(bfr, fxt.App(), wiki, fxt.Page());
		Tfds.Eq_str_lines(expd, bfr.XtoStrAndClear());
	}
}

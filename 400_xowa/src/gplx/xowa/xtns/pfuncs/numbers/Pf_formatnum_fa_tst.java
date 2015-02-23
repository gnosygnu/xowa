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
package gplx.xowa.xtns.pfuncs.numbers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
import gplx.intl.*; import gplx.xowa.langs.numbers.*;
public class Pf_formatnum_fa_tst {
	private Xop_fxt fxt;
	@Before public void init() {
		Xoae_app app = Xoa_app_fxt.app_();
		Xol_lang lang = new Xol_lang(app.Lang_mgr(), Bry_.new_ascii_("fa")).Init_by_load_assert();
		String gfs = String_.Concat_lines_nl
		( "numbers {"
		, "  digits {"
		, "    clear;"
		, "    set('0', '۰');"
		, "    set('1', '۱');"
		, "    set('2', '۲');"
		, "    set('3', '۳');"
		, "    set('4', '۴');"
		, "    set('5', '۵');"
		, "    set('6', '۶');"
		, "    set('7', '۷');"
		, "    set('8', '۸');"
		, "    set('9', '۹');"
		, "    set('%', '٪');"
		, "    set('.', '٫');"
		, "    set(',', '٬');"
		, "  }"
		, "}"
		);
		app.Gfs_mgr().Run_str_for(lang, gfs);
		Xowe_wiki wiki = Xoa_app_fxt.wiki_(app, "fa.wikipedia.org", lang);
		fxt = new Xop_fxt(app, wiki);
	}
	@Test  public void Basic()	{
		fxt.Test_parse_tmpl_str_test("{{formatnum:۱۵۰|R}}"		, "{{test}}",	"150");
	}
}

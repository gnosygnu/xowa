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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import org.junit.*; import gplx.xowa.langs.*; import gplx.xowa.wikis.*;
public class Xoud_opt_scope_tst {
	private Xoud_opt_scope_fxt fxt = new Xoud_opt_scope_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		fxt.Test_parse("en.w"		, fxt.Make(Xol_lang_itm_.Id_en, Xow_domain_.Tid_int_wikipedia));
		fxt.Test_parse("en.*"		, fxt.Make(Xol_lang_itm_.Id_en, Xoud_opt_scope.Type_id_wildcard));
		fxt.Test_parse("*.w"		, fxt.Make(Xoud_opt_scope.Lang_id_wildcard, Xow_domain_.Tid_int_wikipedia));
		fxt.Test_parse("*.*"		, Xoud_opt_scope.App);
		fxt.Test_parse("en.w,fr.d"	, fxt.Make(Xol_lang_itm_.Id_en, Xow_domain_.Tid_int_wikipedia), fxt.Make(Xol_lang_itm_.Id_fr, Xow_domain_.Tid_int_wiktionary));
	}
}
class Xoud_opt_scope_fxt {
	private final Xoud_opt_scope_parser parser = new Xoud_opt_scope_parser();
	public void Clear() {
		// Gfo_usr_dlg_.I = Xoa_app_.usr_dlg_console_();
	}
	public Xoud_opt_scope Make(int lang_id, int type_id) {return new Xoud_opt_scope(lang_id, type_id);}
	public void Test_parse(String raw, Xoud_opt_scope... expd) {
		Xoud_opt_scope[] actl = parser.Parse(Bry_.new_utf8_(raw));
		Tfds.Eq(To_str(expd), To_str(actl));
	}
	private static String To_str(Xoud_opt_scope[] ary) {
		Bry_bfr bfr = Bry_bfr.new_();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xoud_opt_scope itm = ary[i];
			if (i != 0) bfr.Add_str_ascii(",");
			bfr.Add_str_ascii(itm.To_str());
		}
		return bfr.Xto_str_and_clear();
	}
}

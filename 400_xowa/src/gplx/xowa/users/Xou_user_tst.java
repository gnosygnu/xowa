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
package gplx.xowa.users; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.xowa.wikis.xwikis.*;
public class Xou_user_tst {
	private final    Xou_user_fxt fxt = new Xou_user_fxt();
	@Test   public void Available_from_fsys() {
		Io_mgr.Instance.CreateDir(fxt.App().Fsys_mgr().Wiki_dir().GenSubDir("en.wikipedia.org"));
		fxt.App().Usere().Available_from_fsys();
		fxt.Test_xwikis
		( fxt.Make_xwiki(Bool_.N, "home")
		, fxt.Make_xwiki(Bool_.Y, "en.wikipedia.org")	// available_from_fsys should mark as offline=y
		);
	}
}
class Xou_user_fxt {
	public Xoae_app App() {return app;} private Xoae_app app = Xoa_app_fxt.Make__app__edit();
	public String Make_xwiki(boolean offline, String name) {return String_.Concat_with_str("|", Yn.To_str(offline), name);}
	public void Test_xwikis(String... expd) {
		Xow_xwiki_mgr xwiki_mgr = app.Usere().Wiki().Xwiki_mgr();
		int len = xwiki_mgr.Len();
		String[] actl = new String[len];
		for (int i = 0; i < len; ++i) {
			Xow_xwiki_itm xwiki_itm = xwiki_mgr.Get_at(i);
			actl[i] = Make_xwiki(xwiki_itm.Offline(), String_.new_u8(xwiki_itm.Domain_name()));
		}
            Tfds.Eq_ary_str(expd, actl);
	}
}

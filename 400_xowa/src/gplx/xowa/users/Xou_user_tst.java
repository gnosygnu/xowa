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
	private Xou_user_fxt fxt = new Xou_user_fxt();
	@Test   public void Run() {
		Io_url user_system_cfg_url = Io_url_.mem_fil_("mem/xowa/user/test_user/app/data/cfg/user_system_cfg.gfs");
		fxt.Test_fil(user_system_cfg_url, "");								// check that it is blank
		fxt.App().Init();													// run Init
		fxt.Test_fil(user_system_cfg_url, Xou_user_.User_system_cfg_text);	// check that it is created
		Io_mgr._.SaveFilStr(user_system_cfg_url, "");						// simulate edit by blanking out file
		fxt.App().Init();													// run Init again
		fxt.Test_fil(user_system_cfg_url, "");								// check that it is still blank
	}
	@Test   public void Available_from_fsys() {
		Io_mgr._.CreateDir(fxt.App().Fsys_mgr().Wiki_dir().GenSubDir("en.wikipedia.org"));
		fxt.App().User().Available_from_fsys();
		fxt.Test_xwikis
		( fxt.Make_xwiki(Bool_.N, "home")
		, fxt.Make_xwiki(Bool_.Y, "en.wikipedia.org")	// available_from_fsys should mark as offline=y
		);
	}
}
class Xou_user_fxt {
	public Xoa_app App() {return app;} private Xoa_app app = Xoa_app_fxt.app_();
	public String Make_xwiki(boolean offline, String name) {return String_.Concat_with_str("|", Yn.Xto_str(offline), name);}
	public void Test_fil(Io_url url, String expd) {
		Tfds.Eq_str_lines(expd, Io_mgr._.LoadFilStr(url));
	}
	public void Test_xwikis(String... expd) {
		Xow_xwiki_mgr xwiki_mgr = app.User().Wiki().Xwiki_mgr();
		int len = xwiki_mgr.Len();
		String[] actl = new String[len];
		for (int i = 0; i < len; ++i) {
			Xow_xwiki_itm xwiki_itm = xwiki_mgr.Get_at(i);
			actl[i] = Make_xwiki(xwiki_itm.Offline(), String_.new_utf8_(xwiki_itm.Name()));
		}
            Tfds.Eq_ary_str(expd, actl);
	}
}

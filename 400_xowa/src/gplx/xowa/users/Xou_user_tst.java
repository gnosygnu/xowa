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
import org.junit.*;
public class Xou_user_tst {
	@Test  public void Run() {
		Xoa_app app = Xoa_app_fxt.app_();
		Io_url user_system_cfg_url = Io_url_.mem_fil_("mem/xowa/user/test_user/app/data/cfg/user_system_cfg.gfs");
		Fil_tst(user_system_cfg_url, "");								// check that it is blank
		app.Init();														// run Init
		Fil_tst(user_system_cfg_url, Xou_user_.User_system_cfg_text);	// check that it is created
		Io_mgr._.SaveFilStr(user_system_cfg_url, "");					// simulate edit by blanking out file
		app.Init();														// run Init again
		Fil_tst(user_system_cfg_url, "");								// check that it is still blank
	}
	private void Fil_tst(Io_url url, String expd) {
		Tfds.Eq_str_lines(expd, Io_mgr._.LoadFilStr(url));
	}
}

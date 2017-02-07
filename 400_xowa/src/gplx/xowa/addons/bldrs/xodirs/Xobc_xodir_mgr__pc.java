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
package gplx.xowa.addons.bldrs.xodirs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
public class Xobc_xodir_mgr__pc implements Xobc_xodir_mgr {
	public Xobc_xodir_mgr__pc(Xoa_app app) {
	}
	public Xobc_xodir_dir[] Get_dirs(Xoa_app app) {
		int len = 2;
		String dflt = app.Fsys_mgr().Root_dir().Raw();
		String selected = app.User().User_db_mgr().Cfg().Get_app_str_or(Xobc_xodir_cfg.Key__selected_dir, dflt);
		String custom = app.User().User_db_mgr().Cfg().Get_app_str_or(Xobc_xodir_cfg.Key__custom_dir, "(choose your own folder)");
		Xobc_xodir_dir[] rv = new Xobc_xodir_dir[len];
		rv[0] = new Xobc_xodir_dir(String_.Eq(selected, dflt), Bool_.N, Bry_.new_u8(dflt));
		rv[1] = new Xobc_xodir_dir(String_.Eq(selected, custom), Bool_.Y, Bry_.new_u8(custom));
		return rv;
	}
}

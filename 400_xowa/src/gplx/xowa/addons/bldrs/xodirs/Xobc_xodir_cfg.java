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
public class Xobc_xodir_cfg {
	public static final String 
	  Key__selected_dir = "xowa.xodir.selected_dir"
	, Key__custom_dir = "xowa.xodir.custom_dir"
	;
	public static void Set_app_str__selected(Xoa_app app, byte[] val_bry) {
		// if wnt, replace "\"; note that url-encoding while navigating dirs will always convert "\" to "/"
		if (gplx.core.envs.Op_sys.Cur().Tid_is_wnt()) val_bry = Bry_.Replace(val_bry, Byte_ascii.Slash, Byte_ascii.Backslash);

		app.User().User_db_mgr().Cfg().Set_app_bry(Xobc_xodir_cfg.Key__selected_dir, val_bry);
	}
}

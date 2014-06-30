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
package gplx.xowa.xtns.titleBlacklists; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.xtns.scribunto.*;
public class Blacklist_xtn_mgr extends Xox_mgr_base {
	@Override public byte[] Xtn_key() {return XTN_KEY;} public static final byte[] XTN_KEY = Bry_.new_ascii_("TitleBlacklist");
	@Override public void Xtn_init_by_app(Xoa_app app) {
		Scrib_xtn_mgr scrib_xtn = (Scrib_xtn_mgr)app.Xtn_mgr().Get_or_fail(Scrib_xtn_mgr.XTN_KEY);
		scrib_xtn.Lib_mgr().Add(new Blacklist_scrib_lib());
	}
	@Override public Xox_mgr Clone_new() {return new Blacklist_xtn_mgr();}
}

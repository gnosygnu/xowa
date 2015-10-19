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
package gplx.xowa.apps.site_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public interface Xoa_site_cfg_loader {
	int			Tid();
	void		Load_csv__bgn(Xoa_site_cfg_mgr mgr, Xow_wiki wiki);
	byte[]		Load_csv(Xoa_site_cfg_mgr mgr, Xow_wiki wiki, Xoa_site_cfg_itm__base itm);
}
class Xoa_site_cfg_loader_ {
	public static final int Tid__null = -1, Tid__cfg = 0, Tid__fsys = 1, Tid__inet = 2, Tid__fallback = 3;
	private static final String Key__cfg = "cfg", Key__fsys = "fsys", Key__inet = "inet", Key__fallback = "itm";
	public static String Get_key(int tid) {
		switch (tid) {
			case Tid__cfg:			return Key__cfg;
			case Tid__fsys:			return Key__fsys;
			case Tid__inet:			return Key__inet;
			case Tid__fallback:		return Key__fallback;
			default:				throw Err_.new_unhandled(tid);
		}
	}
}

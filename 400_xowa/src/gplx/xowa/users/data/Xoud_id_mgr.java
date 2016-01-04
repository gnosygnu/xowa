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
public class Xoud_id_mgr {
	private Xoud_cfg_mgr cfg_mgr;
	public Xoud_id_mgr(Xoud_cfg_mgr cfg_mgr) {this.cfg_mgr = cfg_mgr;}
	public int	Get_next(String key)			{return cfg_mgr.Select_int_or(Grp_key, key, 1);}
	public void Set_next(String key, int v)		{cfg_mgr.Upsert_int(Grp_key, key, v);}
	public int	Get_next_and_save(String key)	{
		int rv = Get_next(key);
		Set_next(key, rv + 1);
		return rv;
	}
	private static final String Grp_key = "gplx.next_id";
}

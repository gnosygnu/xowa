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
package gplx.xowa.addons.apps.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*;
import gplx.dbs.*; import gplx.xowa.addons.apps.cfgs.mgrs.*;
public class Xocfg_mgr {
	private final    Xocfg_cache_mgr cache_mgr;
	public Xocfg_mgr(Db_conn conn) {
		this.cache_mgr = new Xocfg_cache_mgr(conn);
	}
	public void Clear() {
		cache_mgr.Clear();
	}
	public void Sub_many(Gfo_evt_itm sub, String ctx, String... keys) {
		cache_mgr.Sub_many(sub, ctx, keys);
	}
	public String Get_str(String ctx, String key)			{return cache_mgr.Get(ctx, key);}
	public void Set_str(String ctx, String key, String val) {cache_mgr.Set(ctx, key, val);}

	public static Xocfg_mgr New(Db_conn conn) {
		return new Xocfg_mgr(conn);
	}
}

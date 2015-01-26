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
package gplx.dbs; import gplx.*;
class Db_sys_regy_mgr {
	private Db_sys_regy_tbl tbl;
	public Db_sys_regy_mgr(Db_url url, String name) {tbl = new Db_sys_regy_tbl(url, name);}
	public void Set(String grp, String key, String val) {
		if (tbl.Select_val_or(grp, key, null) == null)
			tbl.Insert(grp, key, val);
		else
			tbl.Update(grp, key, val);
	}
	public void Del(String grp, String key) {
		tbl.Delete(grp, key);
	}
	public String Get_val_as_str_or(String grp, String key, String or) {
		return tbl.Select_val_or(grp, key, or);
	}
}
class Db_sys_regy_itm {
	public Db_sys_regy_itm(String grp, String key, String val) {this.grp = grp; this.key = key; this.val = val;}
	public String Grp() {return grp;} private final String grp;
	public String Key() {return key;} private final String key;
	public String Val() {return val;} private final String val;
}

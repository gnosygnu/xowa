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
package gplx.dbs.conn_props; import gplx.*; import gplx.dbs.*;
public class Db_conn_props_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New();
	public boolean Has(String key) {return hash.Has(key);}
	public boolean Match(String key, String expd_val) {
		String actl_val = (String)hash.Get_by(key);
		return actl_val == null ? false : String_.Eq(expd_val,actl_val);
	}
	public void Add(String key, String val) {hash.Add(key, val);}
	public void Del(String key)				{hash.Del(key);}
}

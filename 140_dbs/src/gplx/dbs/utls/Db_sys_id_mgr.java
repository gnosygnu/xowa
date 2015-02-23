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
package gplx.dbs.utls; import gplx.*; import gplx.dbs.*;
import gplx.core.primitives.*;
class Db_sys_id_mgr {
	private Db_sys_regy_mgr db_regy;
	public Db_sys_id_mgr(Db_sys_regy_mgr db_regy) {this.db_regy = db_regy;}
	public int Get_next(String key)			{return db_regy.Get_val_as_int_or(Grp_key, key, 1);}
	public void Set_next(String key, int v) {db_regy.Set(Grp_key, key, Int_.Xto_str(v));}
	private static final String Grp_key = "gplx.next_id";
}

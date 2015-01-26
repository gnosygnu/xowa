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
import gplx.dbs.*;
public class Xoud_regy_mgr {
	private Xoud_regy_tbl regy_tbl = new Xoud_regy_tbl();
	public void Init(Db_conn conn) {
		regy_tbl.Conn_(conn);
	}
	public byte[] Select_bry_or(String key, byte[] or) {return Select_bry_or("", key, or);}
	public byte[] Select_bry_or(String grp, String key, byte[] or) {
		String rv = regy_tbl.Select_val(grp, key);
		return rv == null ? or : Bry_.new_utf8_(rv);
	}
	public byte[] Assert_bry_or(String key, byte[] or) {return Assert_bry_or("", key, or);}
	public byte[] Assert_bry_or(String grp, String key, byte[] or) {
		String rv = regy_tbl.Select_val(grp, key);
		if (rv == null) {
			Insert_bry(grp, key, or);
			return or;
		}
		else
			return Bry_.new_utf8_(rv);
	}
	public void Update_bry(String key, byte[] val) {Update_bry("", key, val);}
	public void Update_bry(String grp, String key, byte[] val) {regy_tbl.Update(grp, key, String_.new_utf8_(val));}
	public void Insert_bry(String key, byte[] val) {Insert_bry("", key, val);}
	public void Insert_bry(String grp, String key, byte[] val) {regy_tbl.Insert(grp, key, String_.new_utf8_(val));}
	public int Next_id(String tbl) {
		String grp = "xowa." + tbl, key = "next_id";
		int next_id = Int_.parse_or_(regy_tbl.Select_val(grp, key), 1);	// EX: xowa.cfg_history|next_id|1
		String new_val = Int_.Xto_str(next_id + 1);
		if (next_id == 1)
			regy_tbl.Insert(grp, key, new_val);
		else
			regy_tbl.Update(grp, key, new_val);
		return next_id;
	}
}

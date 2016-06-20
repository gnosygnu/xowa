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
import gplx.dbs.*; import gplx.dbs.cfgs.*;
public class Xoud_cfg_mgr {
	public Db_cfg_tbl Tbl() {return tbl;} private Db_cfg_tbl tbl;
	public static final String Tbl_name = "user_opt";
	public void Conn_(Db_conn new_conn, boolean created) {
		tbl = new Db_cfg_tbl(new_conn, Tbl_name);
		if (created) tbl.Create_tbl();
	}
	public int		Select_int_or(String grp, String key, int or)		{
		String rv = Select_str_or(grp, key, null);
		return rv == null ? or : Int_.parse_or(rv, or);
	}
	public byte[]	Select_bry_or(String key, byte[] or)				{return Select_bry_or(""	, key, or);}
	public byte[]	Select_bry_or(String grp, String key, byte[] or)	{
		String rv = Select_str_or(grp, key, null);
		return rv == null ? or : Bry_.new_u8(rv);
	}
	public String	Select_str_or(String grp, String key, String or) {
		String rv = tbl.Select_str_or(grp, key, null);
		return rv == null ? or : rv;
	}
	public byte[] Assert_bry_or(String key, byte[] or) {return Assert_bry_or("", key, or);}
	public byte[] Assert_bry_or(String grp, String key, byte[] or) {
		String rv = tbl.Select_str_or(grp, key, null);
		if (rv == null) {
			Insert_bry(grp, key, or);
			return or;
		}
		else
			return Bry_.new_u8(rv);
	}
	public void Upsert_int(String grp, String key, int val) {
		int exists = Select_int_or(grp, key, Int_.Min_value);
		if (exists == Int_.Min_value)
			Insert_int(grp, key, val);
		else
			Update_int(grp, key, val);
	}
	public void Upsert_str(String grp, String key, String val) {
		String exists = Select_str_or(grp, key, null);
		if (exists == null)
			Insert_str(grp, key, val);
		else
			Update_str(grp, key, val);
	}
	public void Update_str(String grp, String key, String val)	{Update_bry(grp, key, Bry_.new_u8(val));}
	public void Update_bry(String key, byte[] val)				{Update_bry("", key, val);}
	public void Update_bry(String grp, String key, byte[] val)	{tbl.Update_bry(grp, key, val);}
	public void Update_int(String grp, String key, int val)		{tbl.Update_int(grp, key, val);}
	public void Insert_str(String grp, String key, String val)	{Insert_bry(grp, key, Bry_.new_u8(val));}
	public void Insert_bry(String key, byte[] val)				{Insert_bry("", key, val);}
	public void Insert_bry(String grp, String key, byte[] val)	{tbl.Insert_bry(grp, key, val);}
	public void Insert_int(String grp, String key, int val)		{tbl.Insert_int(grp, key, val);}
	public int Next_id(String tbl_name) {
		String grp = "xowa." + tbl_name, key = "next_id";
		int next_id = tbl.Select_int_or(grp, key, 1);	// EX: xowa.cfg_history|next_id|1
		int new_val = next_id + 1;
		if (next_id == 1)
			tbl.Insert_int(grp, key, new_val);
		else
			tbl.Update_int(grp, key, new_val);
		return next_id;
	}
}

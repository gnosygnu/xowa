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
package gplx.xowa.hdumps; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*;
public class Db_provider_mkr {
	private final HashAdp engine_regy = HashAdp_.new_();
	public void Engines_add(Db_engine engine) {engine_regy.AddReplace(engine.Conn_info_tid(), engine);}
	public Db_provider Get(Db_provider_key provider_key) {
		Db_provider rv = Get_or_new(provider_key);
		GfoInvkAbleCmd init_cmd = provider_key.Init_cmd();
		if (init_cmd != GfoInvkAbleCmd.Null)
			GfoInvkAble_.InvkCmd_val(init_cmd.InvkAble(), init_cmd.Cmd(), rv);
		return rv;
	}
	private Db_provider Get_or_new(Db_provider_key key) {
		Db_conn_info conn_info = key.Conn_info();
		Db_engine prototype = (Db_engine)engine_regy.Fetch(conn_info.Key());
		Db_engine engine = prototype.Make_new(conn_info);
		engine.Conn_open();	// auto-open
		return new Db_provider(engine);
	}
}
class Mem_db_rdr implements Db_rdr {
	private int idx = -1, len;
	private Mem_db_row[] rows = new Mem_db_row[0];
	private Mem_db_rdr(Mem_db_row[] rows) {
		this.rows = rows;
		this.len = rows.length;
	}
	public boolean		Move_next() {return ++idx < len;}
	public byte[]	Read_bry(int i)				{return (byte[])rows[idx].Get_at(i);}
	public byte[]	Read_bry_by_str(int i)		{return Bry_.new_utf8_((String)rows[idx].Get_at(i));}
	public String 	Read_str(int i)				{return (String)rows[idx].Get_at(i);}
	public byte 	Read_byte(int i)			{return Byte_.cast_(rows[idx].Get_at(i));}
	public int 		Read_int(int i)				{return Int_.cast_(rows[idx].Get_at(i));}
	public long 	Read_long(int i)			{return Long_.cast_(rows[idx].Get_at(i));}
	public float	Read_float(int i)			{return Float_.cast_(rows[idx].Get_at(i));}
	public double	Read_double(int i)			{return Double_.cast_(rows[idx].Get_at(i));}
	public void		Close()						{rows = null;}
	public static Mem_db_rdr new_by_rows(Mem_db_row[] rows) {return new Mem_db_rdr(rows);}
	public static Mem_db_rdr new_by_obj_ary(Object[][] obj_arys) {
		int obj_arys_len = obj_arys.length;
		Mem_db_row[] rows = new Mem_db_row[obj_arys_len];
		for (int i = 0; i < obj_arys_len; ++i) {
			Object[] obj_ary = obj_arys[i];
			rows[i] = new Mem_db_row(obj_ary);
		}
		return new Mem_db_rdr(rows);
	}
}
class Mem_db_row {
	private Object[] vals;
	public Mem_db_row(Object[] vals) {this.vals = vals;}
	public Object Get_at(int i) {return vals[i];}
}

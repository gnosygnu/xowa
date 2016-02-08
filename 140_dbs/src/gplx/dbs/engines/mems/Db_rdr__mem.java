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
package gplx.dbs.engines.mems; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
public class Db_rdr__mem implements Db_rdr {
	private final Mem_row[] rows; private int row_idx = -1; private final int rows_len;
	private Mem_row row;
	public Db_rdr__mem(String[] cols, Mem_row[] rows) {
		this.rows = rows; this.rows_len = rows.length;
	}
	public boolean Move_next() {
		boolean rv = ++row_idx < rows_len;
		if (rv)
			row = rows[row_idx];
		return rv;
	}
	public byte[] Read_bry(String k)			{return (byte[])row.Get_by(k);}
	public String Read_str(String k)			{return (String)row.Get_by(k);}
	public byte[] Read_bry_by_str(String k)		{return Bry_.new_u8_safe((String)row.Get_by(k));}		// NOTE: null b/c db can have NULL
	@gplx.Virtual public void Save_bry_in_parts(Io_url url, String tbl, String fld, String crt_key, Object crt_val) {throw Err_.new_unimplemented();}
	public DateAdp Read_date_by_str(String k)	{return DateAdp_.parse_iso8561((String)row.Get_by(k));}
	public byte Read_byte(String k)				{return Byte_.cast(row.Get_by(k));}
	public int Read_int(String k)				{return Int_.cast(row.Get_by(k));}
	public long Read_long(String k)				{return Long_.cast(row.Get_by(k));}
	public float Read_float(String k)			{return Float_.cast(row.Get_by(k));}
	public double Read_double(String k)			{return Double_.cast(row.Get_by(k));}
	public boolean Read_bool_by_byte(String k)		{return Byte_.cast(row.Get_by(k)) == 1;}
	public Object Read_obj(String k)			{return row.Get_by(k);}
	public Object Read_at(int i)				{return row.Get_at(i);}
	public void Rls()							{}
}

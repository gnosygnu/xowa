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
	private final Mem_itm[] rows; private int row_idx = -1; private final int rows_len;
	private final HashAdp ords = HashAdp_.new_();
	private Mem_itm row;
	public Db_rdr__mem(String[] cols, Mem_itm[] rows) {
		this.rows = rows; this.rows_len = rows.length;
		int cols_len = cols.length;
		for (int i = 0; i < cols_len; ++i)
			ords.Add(cols[i], i);
	}
	public boolean Move_next() {
		boolean rv = ++row_idx < rows_len;
		if (rv)
			row = rows[row_idx];
		return rv;
	}
	public byte[] Read_bry(int i)				{return (byte[])row.Get_at(i);}
	public byte[] Read_bry(String k)			{return (byte[])row.Get_at(Ord_by_key(k));}
	public String Read_str(int i)				{return (String)row.Get_at(i);}
	public String Read_str(String k)			{return (String)row.Get_at(Ord_by_key(k));}
	public byte[] Read_bry_by_str(int i)		{return Bry_.new_utf8_((String)row.Get_at(i));}
	public byte[] Read_bry_by_str(String k)		{return Bry_.new_utf8_((String)row.Get_at(Ord_by_key(k)));}
	public DateAdp Read_date_by_str(int i)		{return DateAdp_.parse_iso8561((String)row.Get_at(i));}
	public DateAdp Read_date_by_str(String k)	{return DateAdp_.parse_iso8561((String)row.Get_at(Ord_by_key(k)));}
	public byte Read_byte(int i)				{return Byte_.cast_(row.Get_at(i));}
	public byte Read_byte(String k)				{return Byte_.cast_(row.Get_at(Ord_by_key(k)));}
	public int Read_int(int i)					{return Int_.cast_(row.Get_at(i));}
	public int Read_int(String k)				{return Int_.cast_(row.Get_at(Ord_by_key(k)));}
	public long Read_long(int i)				{return Long_.cast_(row.Get_at(i));}
	public long Read_long(String k)				{return Long_.cast_(row.Get_at(Ord_by_key(k)));}
	public float Read_float(int i)				{return Float_.cast_(row.Get_at(i));}
	public float Read_float(String k)			{return Float_.cast_(row.Get_at(Ord_by_key(k)));}
	public double Read_double(int i)			{return Double_.cast_(row.Get_at(i));}
	public double Read_double(String k)			{return Double_.cast_(row.Get_at(Ord_by_key(k)));}
	public boolean Read_bool_by_byte(int i)		{return Byte_.cast_(row.Get_at(i)) == 1;}
	public boolean Read_bool_by_byte(String k)		{return Byte_.cast_(row.Get_at(Ord_by_key(k))) == 1;}
	public void Rls()							{}
	private int Ord_by_key(String k)			{return Int_.cast_(ords.Fetch(k));}
}

/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.dbs.engines.mems; import gplx.dbs.Db_rdr;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.types.errs.ErrUtl;
public class Mem_rdr implements Db_rdr {
	private final Mem_row[] rows; private int row_idx = -1; private final int rows_len;
	private Mem_row row;
	public Mem_rdr(String[] cols, Mem_row[] rows) {
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
	public byte[] Read_bry_by_str(String k)		{return BryUtl.NewU8Safe((String)row.Get_by(k));}		// NOTE: null b/c db can have NULL
	public void Save_bry_in_parts(Io_url url, String tbl, String fld, String crt_key, Object crt_val) {throw ErrUtl.NewUnimplemented();}
	public GfoDate Read_date_by_str(String k)	{return GfoDateUtl.ParseIso8561((String)row.Get_by(k));}
	public byte Read_byte(String k)				{return ByteUtl.Cast(row.Get_by(k));}
	public int Read_int(String k)				{return IntUtl.Cast(row.Get_by(k));}
	public long Read_long(String k)				{return LongUtl.Cast(row.Get_by(k));}
	public float Read_float(String k)			{return FloatUtl.Cast(row.Get_by(k));}
	public double Read_double(String k)			{return DoubleUtl.Cast(row.Get_by(k));}
	public boolean Read_bool_by_byte(String k)		{return ByteUtl.Cast(row.Get_by(k)) == 1;}
	public int Fld_len()						{return row.Len();}
	public Object Read_obj(String k)			{return row.Get_by(k);}
	public Object Read_at(int i)				{return row.Get_at(i);}
	public void Rls()							{}
}

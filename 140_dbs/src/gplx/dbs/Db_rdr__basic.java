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
package gplx.dbs;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import java.sql.ResultSet;
public class Db_rdr__basic implements Db_rdr {
	protected ResultSet rdr; 
	private Db_stmt stmt;
	public void Ctor(Db_stmt stmt, ResultSet rdr, String sql) {this.stmt = stmt; this.rdr = rdr; this.sql = sql;}  
	public String		Sql()					{return sql;} private String sql;
	public boolean			Move_next()	{
		try	 {return rdr.next();}	
		catch (Exception e) {throw ErrUtl.NewArgs(e, "move_next failed; check column casting error in SQL", "sql", sql);}
	}
	public byte[]		Read_bry(String k)			{try {return (byte[])rdr.getObject(k);} catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "key", k, "type", BryUtl.ClsValName);}}
	public byte[]		Read_bry_by_str(String k)	{try {return BryUtl.NewU8((String)rdr.getObject(k));} catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "key", k, "type", StringUtl.ClsValName);}}
	public void			Save_bry_in_parts(Io_url url, String tbl, String fld, String crt_key, Object crt_val) {throw ErrUtl.NewUnimplemented();}
	public String 		Read_str(String k)			{try {return (String)rdr.getObject(k);} catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "key", k, "type", StringUtl.ClsValName);}}
	public GfoDate Read_date_by_str(String k)	{return GfoDateUtl.ParseIso8561(Read_str(k));}
	public int 			Read_int(String k)			{try {return IntUtl.Cast(rdr.getObject(k));} catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "key", k, "type", IntUtl.ClsValName);}}
	public long 		Read_long(String k)			{try {return LongUtl.Cast(rdr.getObject(k));} catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "key", k, "type", LongUtl.ClsValName);}}
	public float		Read_float(String k)		{try {return FloatUtl.Cast(rdr.getObject(k));} catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "key", k, "type", FloatUtl.ClsValName);}}
	public double		Read_double(String k)		{try {return DoubleUtl.Cast(rdr.getObject(k));} catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "key", k, "type", DoubleUtl.ClsValName);}}
	public byte			Read_byte(String k)			{try {return ByteUtl.Cast(rdr.getObject(k));} catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "key", k, "type", ByteUtl.ClsValName);}}
	public boolean 		Read_bool_by_byte(String k)	{try {return ByteUtl.Cast(rdr.getObject(k)) == 1;} catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "key", k, "type", BoolUtl.ClsValName);}}
	public int			Fld_len()					{try {return rdr.getMetaData().getColumnCount();} catch (Exception e) {throw ErrUtl.NewArgs(e, "field count failed", "sql", sql);}}
	public Object 		Read_obj(String k)			{try {return rdr.getObject(k);} catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "key", k, "type", ObjectUtl.ClsValName);}}
	public Object 		Read_at(int i)				{try {return rdr.getObject(i + 1);} catch (Exception e) {throw ErrUtl.NewArgs(e, "read failed", "idx", i, "type", ObjectUtl.ClsValName);}}
	public void			Rls() {
		try	{rdr.close();} 
		catch (Exception e) {throw ErrUtl.NewArgs(e, "close failed");}
		if (stmt != null) {
			stmt.Rls();
			stmt = null;	// NOTE: must null reference else will throw SQLException during statements DATE:2016-04-10
		}
	}
}

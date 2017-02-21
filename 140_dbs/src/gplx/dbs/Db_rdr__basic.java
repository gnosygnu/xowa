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
package gplx.dbs; import gplx.*;
import java.sql.ResultSet;
public class Db_rdr__basic implements Db_rdr {
	protected ResultSet rdr; 
	private Db_stmt stmt;
	public void Ctor(Db_stmt stmt, ResultSet rdr, String sql) {this.stmt = stmt; this.rdr = rdr; this.sql = sql;}  
	public String		Sql()					{return sql;} private String sql;
	public boolean			Move_next()	{
		try	 {return rdr.next();}	
		catch (Exception e) {throw Err_.new_exc(e, "db", "move_next failed; check column casting error in SQL", "sql", sql);}
	}
	@gplx.Virtual public byte[]		Read_bry(String k)			{try {return (byte[])rdr.getObject(k);} catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "key", k, "type", Bry_.Cls_val_name);}} 
	@gplx.Virtual public byte[]		Read_bry_by_str(String k)	{try {return Bry_.new_u8((String)rdr.getObject(k));} catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "key", k, "type", String_.Cls_val_name);}} 
	@gplx.Virtual public void			Save_bry_in_parts(Io_url url, String tbl, String fld, String crt_key, Object crt_val) {throw Err_.new_unimplemented();}
	@gplx.Virtual public String 		Read_str(String k)			{try {return (String)rdr.getObject(k);} catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "key", k, "type", String_.Cls_val_name);}} 
	@gplx.Virtual public DateAdp		Read_date_by_str(String k)	{return DateAdp_.parse_iso8561(Read_str(k));}
	@gplx.Virtual public int 			Read_int(String k)			{try {return Int_.cast(rdr.getObject(k));} catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "key", k, "type", Int_.Cls_val_name);}} 
	@gplx.Virtual public long 		Read_long(String k)			{try {return Long_.cast(rdr.getObject(k));} catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "key", k, "type", Long_.Cls_val_name);}} 
	@gplx.Virtual public float		Read_float(String k)		{try {return Float_.cast(rdr.getObject(k));} catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "key", k, "type", Float_.Cls_val_name);}} 
	@gplx.Virtual public double		Read_double(String k)		{try {return Double_.cast(rdr.getObject(k));} catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "key", k, "type", Double_.Cls_val_name);}} 
	@gplx.Virtual public byte			Read_byte(String k)			{try {return Byte_.cast(rdr.getObject(k));} catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "key", k, "type", Byte_.Cls_val_name);}} 
	@gplx.Virtual public boolean 		Read_bool_by_byte(String k)	{try {return Byte_.cast(rdr.getObject(k)) == 1;} catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "key", k, "type", Bool_.Cls_val_name);}} 
	@gplx.Virtual public int			Fld_len()					{try {return rdr.getMetaData().getColumnCount();} catch (Exception e) {throw Err_.new_exc(e, "db", "field count failed", "sql", sql);}}	
	@gplx.Virtual public Object 		Read_obj(String k)			{try {return rdr.getObject(k);} catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "key", k, "type", Object_.Cls_val_name);}} 
	@gplx.Virtual public Object 		Read_at(int i)				{try {return rdr.getObject(i + 1);} catch (Exception e) {throw Err_.new_exc(e, "db", "read failed", "idx", i, "type", Object_.Cls_val_name);}} 
	@gplx.Virtual public void			Rls() {
		try	{rdr.close();} 
		catch (Exception e) {throw Err_.new_exc(e, "db", "close failed");}
		if (stmt != null) {
			stmt.Rls();
			stmt = null;	// NOTE: must null reference else will throw SQLException during statements DATE:2016-04-10
		}
	}
}

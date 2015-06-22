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
package gplx.dbs; import gplx.*;
import java.sql.ResultSet;
public class Db_rdr__basic implements Db_rdr {
	protected ResultSet rdr; 
	private Db_stmt stmt;
	public void Ctor(Db_stmt stmt, ResultSet rdr, String sql) {this.stmt = stmt; this.rdr = rdr; this.sql = sql;}  
	public String		Sql()					{return sql;} private String sql;
	public boolean			Move_next()	{
		try	 {return rdr.next();}	
		catch (Exception e) {throw Err_.new_fmt_("move_next failed; check column casting error in SQL: err={0} sql={1}", Err_.Message_lang(e), sql);}
	}
	@gplx.Virtual public byte[]		Read_bry(String k)			{try {return (byte[])rdr.getObject(k);} catch (Exception e) {throw Err_.new_("read failed: k={0} type={1} err={2}", k, Bry_.Cls_val_name, Err_.Message_lang(e));}} 
	@gplx.Virtual public byte[]		Read_bry_by_str(String k)	{try {return Bry_.new_u8((String)rdr.getObject(k));} catch (Exception e) {throw Err_.new_("read failed: k={0} type={1} err={2}", k, String_.Cls_val_name, Err_.Message_lang(e));}} 
	@gplx.Virtual public byte[]		Read_bry_in_parts(String tbl, String fld, String crt_key, Object crt_val) {throw Err_.not_implemented_();}
	@gplx.Virtual public String 		Read_str(String k)			{try {return (String)rdr.getObject(k);} catch (Exception e) {throw Err_.new_("read failed: k={0} type={1} err={2}", k, String_.Cls_val_name, Err_.Message_lang(e));}} 
	@gplx.Virtual public DateAdp		Read_date_by_str(String k)	{return DateAdp_.parse_iso8561(Read_str(k));}
	@gplx.Virtual public int 			Read_int(String k)			{try {return Int_.cast_(rdr.getObject(k));} catch (Exception e) {throw Err_.new_("read failed: k={0} type={1} err={2}", k, Int_.Cls_val_name, Err_.Message_lang(e));}} 
	@gplx.Virtual public long 		Read_long(String k)			{try {return Long_.cast_(rdr.getObject(k));} catch (Exception e) {throw Err_.new_("read failed: k={0} type={1} err={2}", k, Long_.Cls_val_name, Err_.Message_lang(e));}} 
	@gplx.Virtual public float		Read_float(String k)		{try {return Float_.cast_(rdr.getObject(k));} catch (Exception e) {throw Err_.new_("read failed: k={0} type={1} err={2}", k, Float_.Cls_val_name, Err_.Message_lang(e));}} 
	@gplx.Virtual public double		Read_double(String k)		{try {return Double_.cast_(rdr.getObject(k));} catch (Exception e) {throw Err_.new_("read failed: k={0} type={1} err={2}", k, Double_.Cls_val_name, Err_.Message_lang(e));}} 
	@gplx.Virtual public byte			Read_byte(String k)			{try {return Byte_.cast_(rdr.getObject(k));} catch (Exception e) {throw Err_.new_("read failed: k={0} type={1} err={2}", k, Byte_.Cls_val_name, Err_.Message_lang(e));}} 
	@gplx.Virtual public boolean 		Read_bool_by_byte(String k)	{try {return Byte_.cast_(rdr.getObject(k)) == 1;} catch (Exception e) {throw Err_.new_("read failed: k={0} type={1} err={2}", k, Bool_.Cls_val_name, Err_.Message_lang(e));}} 
	@gplx.Virtual public void			Rls() {
		try	{rdr.close();} 
		catch (Exception e) {throw Err_.new_("close failed: err={0}", Err_.Message_lang(e));}
		if (stmt != null) stmt.Rls();
	}
}

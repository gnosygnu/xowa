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
class Db_rdr__basic implements Db_rdr {
	private final ResultSet rdr; 
	public Db_rdr__basic(ResultSet rdr, String sql) {this.rdr = rdr; this.sql = sql;}  
	public String		Sql()					{return sql;} private final String sql;
	public boolean			Move_next()	{
		try	 {return rdr.next();}	
		catch (Exception e) {throw Err_.new_fmt_("move_next failed; check column casting error in SQL: err={0} sql={1}", Err_.Message_lang(e), sql);}
	}
	public byte[]		Read_bry(int i)			{try {return rdr.getBytes(i + 1);} catch (Exception e) {throw Err_.new_("read failed: i={0} type={1} err={2}", i, Bry_.Cls_name, Err_.Message_lang(e));}} 
	public byte[]		Read_bry_by_str(int i)	{try {return Bry_.new_utf8_(rdr.getString(i + 1));} catch (Exception e) {throw Err_.new_("read failed: i={0} type={1} err={2}", i, String_.Cls_name, Err_.Message_lang(e));}} 
	public String 		Read_str(int i)			{try {return rdr.getString(i + 1);} catch (Exception e) {throw Err_.new_("read failed: i={0} type={1} err={2}", i, String_.Cls_name, Err_.Message_lang(e));}} 
	public int 			Read_int(int i)			{try {return rdr.getInt(i + 1);} catch (Exception e) {throw Err_.new_("read failed: i={0} type={1} err={2}", i, Int_.Cls_name, Err_.Message_lang(e));}} 
	public long 		Read_long(int i)		{try {return rdr.getLong(i + 1);} catch (Exception e) {throw Err_.new_("read failed: i={0} type={1} err={2}", i, Long_.Cls_name, Err_.Message_lang(e));}} 
	public float		Read_float(int i)		{try {return rdr.getFloat(i + 1);} catch (Exception e) {throw Err_.new_("read failed: i={0} type={1} err={2}", i, Float_.Cls_name, Err_.Message_lang(e));}} 
	public double		Read_double(int i)		{try {return rdr.getDouble(i + 1);} catch (Exception e) {throw Err_.new_("read failed: i={0} type={1} err={2}", i, Double_.Cls_name, Err_.Message_lang(e));}} 
	public byte 		Read_byte(int i)		{try {return rdr.getByte(i + 1);} catch (Exception e) {throw Err_.new_("read failed: i={0} type={1} err={2}", i, Byte_.Cls_name, Err_.Message_lang(e));}} 
	public void			Close()					{try {rdr.close();} catch (Exception e) {throw Err_.new_("close failed: err={0}", Err_.Message_lang(e));}} 
}

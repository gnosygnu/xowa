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
public class Db_rdr_ {
	public static final Db_rdr Null = new Db_rdr__null();
}
class Db_rdr__null implements Db_rdr {
	public boolean			Move_next()				{return false;}
	public byte[]		Read_bry(int i)			{return Bry_.Empty;}
	public byte[]		Read_bry_by_str(int i)	{return Bry_.Empty;}
	public byte			Read_byte(int i)		{return Byte_.MaxValue_127;}
	public String 		Read_str(int i)			{return String_.Empty;}
	public int 			Read_int(int i)			{return Int_.MinValue;}
	public long 		Read_long(int i)		{return Long_.MinValue;}
	public float		Read_float(int i)		{return Float_.NaN;}
	public double		Read_double(int i)		{return Double_.NaN;}
	public void			Close() {}
}

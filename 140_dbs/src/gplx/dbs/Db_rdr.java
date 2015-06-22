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
public interface Db_rdr {
	boolean		Move_next();
	byte[]		Read_bry(String k);
	byte[]		Read_bry_in_parts(String tbl, String fld, String crt_key, Object crt_val);
	byte[]		Read_bry_by_str(String k);
	String 		Read_str(String k);
	byte 		Read_byte(String k);
	int 		Read_int(String k);
	long 		Read_long(String k);
	float		Read_float(String k);
	double		Read_double(String k);
	DateAdp		Read_date_by_str(String k);
	boolean		Read_bool_by_byte(String k);
	void		Rls();
}

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
	byte[]		Read_bry(int i);
	byte[]		Read_bry_by_str(int i);
	String 		Read_str(int i);
	byte 		Read_byte(int i);
	int 		Read_int(int i);
	long 		Read_long(int i);
	float		Read_float(int i);
	double		Read_double(int i);
	void		Close();
}

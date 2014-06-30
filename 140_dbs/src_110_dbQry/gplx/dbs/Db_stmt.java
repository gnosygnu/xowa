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
public interface Db_stmt extends RlsAble {
	Db_provider Provider();
	Db_stmt Val_bool_(boolean v);
	Db_stmt Val_byte_(byte v);
	Db_stmt Val_byte_by_bool_(boolean v);
	Db_stmt Val_int_(int v);
	Db_stmt Val_long_(long v);
	Db_stmt Val_float_(float v);
	Db_stmt Val_double_(double v);
	Db_stmt Val_decimal_(DecimalAdp v);
	Db_stmt Val_bry_(byte[] v);
	Db_stmt Val_bry_by_str_(String v);
	Db_stmt Val_str_by_bry_(byte[] v);
	Db_stmt Val_str_(String v);
	Db_stmt Val_rdr_(gplx.ios.Io_stream_rdr rdr, long rdr_len);
	boolean Exec_insert();
	int Exec_update();
	int Exec_delete();
	DataRdr Exec_select();
	Object Exec_select_val();
	Db_stmt Clear();
	Db_stmt New();
}

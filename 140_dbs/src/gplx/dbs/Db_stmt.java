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
import gplx.dbs.engines.*;
public interface Db_stmt extends RlsAble {
	Db_stmt Crt_bool_as_byte(String k, boolean v);
	Db_stmt Val_bool_as_byte(String k, boolean v);
	Db_stmt Val_bool_as_byte(boolean v);
	Db_stmt Crt_byte(String k, byte v);
	Db_stmt Val_byte(String k, byte v);
	Db_stmt Val_byte(byte v);
	Db_stmt Crt_int(String k, int v);
	Db_stmt Val_int(String k, int v);
	Db_stmt Val_int(int v);
	Db_stmt Crt_long(String k, long v);
	Db_stmt Val_long(String k, long v);
	Db_stmt Val_long(long v);
	Db_stmt Crt_float(String k, float v);
	Db_stmt Val_float(String k, float v);
	Db_stmt Val_float(float v);
	Db_stmt Crt_double(String k, double v);
	Db_stmt Val_double(String k, double v);
	Db_stmt Val_double(double v);
	Db_stmt Crt_decimal(String k, DecimalAdp v);
	Db_stmt Val_decimal(String k, DecimalAdp v);
	Db_stmt Val_decimal(DecimalAdp v);
	Db_stmt Crt_bry(String k, byte[] v);
	Db_stmt Val_bry(String k, byte[] v);
	Db_stmt Val_bry(byte[] v);
	Db_stmt Crt_str(String k, String v);
	Db_stmt Val_str(String k, String v);
	Db_stmt Val_str(String v);
	Db_stmt Crt_bry_as_str(String k, byte[] v);
	Db_stmt Val_bry_as_str(String k, byte[] v);
	Db_stmt Val_bry_as_str(byte[] v);
	Db_stmt Val_rdr_(gplx.ios.Io_stream_rdr rdr, long rdr_len);
	boolean Exec_insert();
	int Exec_update();
	int Exec_delete();
	DataRdr Exec_select();
	Db_rdr Exec_select_as_rdr();
	Object Exec_select_val();
	void Ctor_stmt(Db_engine engine, Db_qry qry);
	Db_stmt Clear();
	Db_stmt Reset_stmt();
}

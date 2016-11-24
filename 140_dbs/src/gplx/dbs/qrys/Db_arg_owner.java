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
package gplx.dbs.qrys; import gplx.*; import gplx.dbs.*;
public interface Db_arg_owner extends Db_qry {
	Db_arg_owner From_(String tbl);
	Db_arg_owner Crt_int(String k, int v);
	Db_arg_owner Crt_str(String k, String v);
	Db_arg_owner Val_byte(String k, byte v);
	Db_arg_owner Val_int(String k, int v);
	Db_arg_owner Val_long(String k, long v);
	Db_arg_owner Val_decimal(String k, Decimal_adp v);
	Db_arg_owner Val_str(String k, String v);
	Db_arg_owner Val_date(String k, DateAdp v);
	Db_arg_owner Val_blob(String k, byte[] v);
	Db_arg_owner Val_str_by_bry(String k, byte[] v);
	Db_arg_owner Val_obj(String key, Object val);
	Db_arg_owner Val_obj_type(String key, Object val, byte val_tid);
}

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
public interface Db_qry_arg_owner extends Db_qry {
	Db_qry_arg_owner From_(String tbl);
	Db_qry_arg_owner Key_arg_(String k, int v);
	Db_qry_arg_owner Key_arg_(String k, String v);
	Db_qry_arg_owner Arg_(String k, int v);
	Db_qry_arg_owner Arg_(String k, long v);
	Db_qry_arg_owner Arg_(String k, String v);
	Db_qry_arg_owner Arg_(String k, byte[] v);
	Db_qry_arg_owner Arg_(String k, DateAdp v);
	Db_qry_arg_owner Arg_(String k, Decimal_adp v);
	Db_qry_arg_owner Arg_byte_(String k, byte v);
	Db_qry_arg_owner Arg_bry_(String k, byte[] v);
	Db_qry_arg_owner Arg_obj_(String key, Object val);
	Db_qry_arg_owner Arg_obj_type_(String key, Object val, byte val_tid);
}

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
import gplx.core.criterias.*; import gplx.dbs.sqls.*;
public class Db_qry_update implements Db_qry_arg_owner {
	public int			Tid() {return Db_qry_.Tid_update;}
	public boolean			Exec_is_rdr() {return false;}
	public String		Xto_sql() {return Sql_qry_wtr_.I.Xto_str(this, false);}
	public int			Exec_qry(Db_conn conn) {return conn.Exec_qry(this);}
	public String		Base_table() {return base_table;} private String base_table;
	public String[]		Cols_for_update() {return cols_for_update;} private String[] cols_for_update;
	public Criteria		Where() {return where;} public Db_qry_update Where_(Criteria crt) {where = crt; return this;} private Criteria where;
	public Db_qry_arg_owner From_(String tbl) {base_table = tbl; return this;}
	public KeyValHash Args() {return args;} private final KeyValHash args = KeyValHash.new_();
	public Db_qry_arg_owner Arg_(String k, DecimalAdp v)	{return Arg_obj_type_(k, v.Xto_decimal(), Db_val_type.Tid_decimal);}
	public Db_qry_arg_owner Arg_(String k, DateAdp v)		{return Arg_obj_type_(k, v, Db_val_type.Tid_date);}
	public Db_qry_arg_owner Arg_byte_(String k, byte v)		{return Arg_obj_type_(k, v, Db_val_type.Tid_byte);}
	public Db_qry_arg_owner Arg_(String k, int v)			{return Arg_obj_type_(k, v, Db_val_type.Tid_int32);}
	public Db_qry_arg_owner Arg_(String k, long v)			{return Arg_obj_type_(k, v, Db_val_type.Tid_int64);}
	public Db_qry_arg_owner Arg_(String k, String v)		{return Arg_obj_type_(k, v, Db_val_type.Tid_varchar);}
	public Db_qry_arg_owner Arg_bry_(String k, byte[] v)	{return Arg_obj_type_(k, v, Db_val_type.Tid_bry);}
	public Db_qry_arg_owner Arg_(String k, byte[] v)		{return Arg_obj_type_(k, String_.new_utf8_(v), Db_val_type.Tid_varchar);}
	public Db_qry_arg_owner Arg_obj_(String k, Object v)	{return Arg_obj_type_(k, v, Db_val_type.Tid_null);}
	public Db_qry_arg_owner Arg_obj_type_(String key, Object val, byte val_tid) {
		Db_arg arg = new Db_arg(key, val).Val_tid_(val_tid);
		args.Add(arg.Key(), arg);
		return this;
	}
	public Db_qry_arg_owner Key_arg_(String k, int v) {return Key_arg_obj_(k, v);}
	public Db_qry_arg_owner Key_arg_(String k, String v) {return Key_arg_obj_(k, v);}
	private Db_qry_arg_owner Key_arg_obj_(String k, Object v) {
		Criteria crt = Db_crt_.eq_(k, v);
		where = where == null ? crt : Criteria_.And(where, crt);
		return this;
	}
	public static Db_qry_update new_() {return new Db_qry_update();} Db_qry_update() {}
	public static Db_qry_update new_(String tbl, String[] where, String... update) {
		Db_qry_update rv = Db_qry_.update_(tbl, Db_crt_.eq_many_(where));
		rv.cols_for_update = update;
		int len = update.length;
		for (int i = 0; i < len; i++)
			rv.Arg_obj_(update[i], null);
		return rv;
	}
}

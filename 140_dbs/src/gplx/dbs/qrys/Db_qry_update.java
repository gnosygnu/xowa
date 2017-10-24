/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.dbs.qrys; import gplx.*; import gplx.dbs.*;
import gplx.core.criterias.*; import gplx.dbs.sqls.*;
public class Db_qry_update implements Db_arg_owner {
	public int			Tid() {return Db_qry_.Tid_update;}
	public boolean			Exec_is_rdr() {return false;}
	public String		To_sql__exec(Sql_qry_wtr wtr) {return wtr.To_sql_str(this, false);}
	public int			Exec_qry(Db_conn conn) {return conn.Exec_qry(this);}
	public String		Base_table() {return base_table;} private String base_table;
	public String[]		Cols_for_update() {return cols_for_update;} private String[] cols_for_update;
	public Criteria		Where() {return where;} public Db_qry_update Where_(Criteria crt) {where = crt; return this;} private Criteria where;
	public Db_arg_owner From_(String tbl) {base_table = tbl; return this;}
	public Keyval_hash Args() {return args;} private final Keyval_hash args = new Keyval_hash();
	public Db_arg_owner Val_byte(String k, byte v)				{return Val_obj_type(k, v, Db_val_type.Tid_byte);}
	public Db_arg_owner Val_int(String k, int v)				{return Val_obj_type(k, v, Db_val_type.Tid_int32);}
	public Db_arg_owner Val_long(String k, long v)				{return Val_obj_type(k, v, Db_val_type.Tid_int64);}
	public Db_arg_owner Val_decimal(String k, Decimal_adp v)	{return Val_obj_type(k, v.Under(), Db_val_type.Tid_decimal);}
	public Db_arg_owner Val_str(String k, String v)				{return Val_obj_type(k, v, Db_val_type.Tid_varchar);}
	public Db_arg_owner Val_str_by_bry(String k, byte[] v)		{return Val_obj_type(k, String_.new_u8(v), Db_val_type.Tid_varchar);}
	public Db_arg_owner Val_date(String k, DateAdp v)			{return Val_obj_type(k, v, Db_val_type.Tid_date);}
	public Db_arg_owner Val_blob(String k, byte[] v)			{return Val_obj_type(k, v, Db_val_type.Tid_bry);}
	public Db_arg_owner Val_obj(String k, Object v)				{return Val_obj_type(k, v, Db_val_type.Tid_null);}
	public Db_arg_owner Val_obj_type(String key, Object val, byte val_tid) {
		if (key == Dbmeta_fld_itm.Key_null) return this;
		args.Add(key, new Db_arg(key, val, val_tid));
		return this;
	}
	public Db_arg_owner Crt_int(String k, int v)	{return Key_obj_(k, v);}
	public Db_arg_owner Crt_str(String k, String v) {return Key_obj_(k, v);}
	private Db_arg_owner Key_obj_(String k, Object v) {
		Criteria crt = Db_crt_.New_eq(k, v);
		where = where == null ? crt : Criteria_.And(where, crt);
		return this;
	}
	public static Db_qry_update New(String tbl, String[] where, String... update) {
		Db_qry_update rv = Db_qry_.update_(tbl, Db_crt_.eq_many_(where));
		rv.cols_for_update = update;
		int len = update.length;
		for (int i = 0; i < len; i++)
			rv.Val_obj(update[i], null);
		return rv;
	}
}

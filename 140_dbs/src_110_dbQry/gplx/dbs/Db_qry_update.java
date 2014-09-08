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
import gplx.criterias.*;
public class Db_qry_update implements Db_qry_arg_owner {
	public int Tid() {return Db_qry_.Tid_basic;}
	public String KeyOfDb_qry() {return KeyConst;} public static final String KeyConst = "UPDATE";
	public boolean ExecRdrAble() {return false;}
	public int Exec_qry(Db_provider provider) {return provider.Exec_qry(this);}
	public String XtoSql() {return Sql_cmd_wtr_.Ansi.XtoSqlQry(this, false);}		

	public Db_qry_arg_owner From_(String tbl) {baseTable = tbl; return this;}
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
	Db_qry_arg_owner Key_arg_obj_(String k, Object v) {
		Criteria crt = Db_crt_.eq_(k, v);
		where = Sql_where.merge_or_new_(where, crt);
		return this;
	}
	@gplx.Internal protected String BaseTable() {return baseTable;} private String baseTable;
	@gplx.Internal protected KeyValHash Args() {return args;} final KeyValHash args = KeyValHash.new_();
	@gplx.Internal protected Sql_where Where() {return where;} public Db_qry_update Where_(Criteria crt) {where = Sql_where.new_(crt); return this;} Sql_where where;
	
	public static Db_qry_update new_() {return new Db_qry_update();} Db_qry_update() {}
}

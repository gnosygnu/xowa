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
public class Db_qry_insert implements Db_qry_arg_owner {
	public String KeyOfDb_qry() {return KeyConst;} public static final String KeyConst = "INSERT";
	public boolean ExecRdrAble() {return false;}
	public String XtoSql() {return Sql_cmd_wtr_.Ansi.XtoSqlQry(this, false);}		
	public int Exec_qry(Db_provider provider) {return provider.Exec_qry(this);}
	public Db_qry_arg_owner From_(String tbl) {baseTable = tbl; return this;}
	public Db_qry_arg_owner Arg_(String k, DecimalAdp v)	{return Arg_obj_type_(k, v.XtoDecimal(), Db_val_type.Tid_decimal);}
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
	public Db_qry_arg_owner Key_arg_(String k, int v)		{return Arg_obj_type_(k, v, Db_val_type.Tid_int32);}
	public Db_qry_arg_owner Key_arg_(String k, String v)	{return Arg_obj_type_(k, v, Db_val_type.Tid_varchar);}
	public Db_qry_select Select() {return select;} Db_qry_select select;
	public Db_qry_insert Select_(Db_qry_select qry) {this.select = qry; return this;}
	public Db_qry_insert Cols_(String... ary) {
		if (cols == null) cols = Sql_select_fld_list.new_();
		for (String fld : ary)
			cols.Add(Sql_select_fld_fld.new_(Sql_select_fld_base.Tbl_null, fld, fld));
		return this;
	}
	@gplx.Internal protected String BaseTable() {return baseTable;} public Db_qry_insert BaseTable_(String val) {baseTable = val; return this;} private String baseTable;
	@gplx.Internal protected KeyValHash Args() {return args;} KeyValHash args = KeyValHash.new_();
	@gplx.Internal protected Sql_select_fld_list Cols() {return cols;} Sql_select_fld_list cols;

	public static Db_qry_insert new_() {return new Db_qry_insert();} Db_qry_insert() {}
}

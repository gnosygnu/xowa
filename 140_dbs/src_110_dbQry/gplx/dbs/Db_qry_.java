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
public class Db_qry_ {
	public static Db_qry_select select_cols_(String tbl, Criteria crt, String... cols){return select_().From_(tbl).Where_(crt).Cols_(cols);}
	public static Db_qry_select select_val_(String tbl, String col, Criteria crt)			{return select_().From_(tbl).Where_(crt).Cols_(col);}
	public static Db_qry_select select_tbl_(String tbl)										{return select_().From_(tbl);}
	public static Db_qry_select select_()													{return Db_qry_select.new_();}
	public static Db_qry_delete delete_(String tbl, Criteria crt)							{return Db_qry_delete.new_().BaseTable_(tbl).Where_(crt);}
	public static Db_qry_delete delete_tbl_(String tbl)										{return Db_qry_delete.new_().BaseTable_(tbl);}
	public static Db_qry_insert insert_(String tbl)											{return Db_qry_insert.new_().BaseTable_(tbl);}
	public static Db_qry_insert insert_common_(String tbl, KeyVal... pairs) {
		Db_qry_insert cmd = Db_qry_insert.new_().BaseTable_(tbl);
		for (KeyVal pair : pairs)
			cmd.Arg_obj_(pair.Key(), pair.Val());
		return cmd;
	}

	public static Db_qry_update update_(String tbl, Criteria crt) {
		Db_qry_update update = Db_qry_update.new_();
		update.From_(tbl);
		update.Where_(crt);
		return update;
	}
	public static Db_qry_update update_common_(String tbl, Criteria crt, KeyVal... pairs) {
		Db_qry_update cmd = Db_qry_update.new_();
		cmd.From_(tbl); cmd.Where_(crt);
		for (KeyVal pair : pairs)
			cmd.Arg_obj_(pair.Key(), pair.Val());
		return cmd;
	}
	public static final Object WhereAll = null;
	public static Db_qry as_(Object obj) {return obj instanceof Db_qry ? (Db_qry)obj : null;}
	public static final int Tid_basic = 0, Tid_select_in_tbl = 1;
}
interface Db_qryWkr {
	Object Exec(Db_engine engine, Db_qry cmd);
}
class Db_qryWkr_ {
	public static final Db_qryWkr Null = new Db_qryWrk_null();
}
class Db_qryWrk_null implements Db_qryWkr {
	public Object Exec(Db_engine engine, Db_qry cmd) {return null;}
}

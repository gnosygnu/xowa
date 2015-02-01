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
public class Db_qry_delete implements Db_qry {
	Db_qry_delete(String base_table, Criteria where) {this.base_table = base_table; this.where = where;}
	public int			Tid()					{return Db_qry_.Tid_delete;}
	public boolean			Exec_is_rdr()			{return false;}
	public String		Base_table()			{return base_table;} private final String base_table;
	public String		Xto_sql()				{return Sql_qry_wtr_.I.Xto_str(this, false);}
	public Criteria		Where()					{return where;} private final Criteria where;
	public int			Exec_qry(Db_conn conn)	{return conn.Exec_qry(this);}
	public static Db_qry_delete new_all_(String tbl)						{return new Db_qry_delete(tbl, Criteria_.All);}
	public static Db_qry_delete new_(String tbl, String... where)		{return new Db_qry_delete(tbl, Db_crt_.eq_many_(where));}
	public static Db_qry_delete new_(String tbl, Criteria where)			{return new Db_qry_delete(tbl, where);}
}

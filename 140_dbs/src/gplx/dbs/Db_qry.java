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
public interface Db_qry {
	int			Tid();
	boolean		Exec_is_rdr();
	String		Base_table();
	String		To_sql__exec(gplx.dbs.sqls.Sql_qry_wtr wtr);
}
class Db_qry__noop implements Db_qry {
	public int	Tid() {return Db_qry_.Tid_noop;}
	public boolean	Exec_is_rdr() {return false;}
	public String Base_table() {return "";}
	public String To_sql__exec(gplx.dbs.sqls.Sql_qry_wtr wtr) {return "";}
}

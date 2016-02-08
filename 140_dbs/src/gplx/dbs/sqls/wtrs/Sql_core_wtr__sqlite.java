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
package gplx.dbs.sqls.wtrs; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
public class Sql_core_wtr__sqlite extends Sql_core_wtr {	@Override protected Sql_val_wtr			Make__val_wtr()								{return new Sql_val_wtr_sqlite();}
	@Override protected Sql_select_wtr		Make__select_wtr(Sql_core_wtr qry_wtr)	{return new Sql_select_wtr_sqlite(qry_wtr);}
}

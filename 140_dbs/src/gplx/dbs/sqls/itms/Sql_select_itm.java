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
package gplx.dbs.sqls.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
public class Sql_select_itm {	
	public boolean Distinct = false;
	public final Sql_select_fld_list Flds = new Sql_select_fld_list();

	public static final Sql_select_itm All = all_();
	private static Sql_select_itm all_() {Sql_select_itm rv = new Sql_select_itm(); rv.Flds.Add(Sql_select_fld_wild.Instance); return rv;}
}

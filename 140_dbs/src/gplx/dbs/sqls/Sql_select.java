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
package gplx.dbs.sqls; import gplx.*; import gplx.dbs.*;
import gplx.core.strings.*;
import gplx.dbs.engines.tdbs.*;
public class Sql_select {
	public Sql_select_fld_list Flds() {return flds;} Sql_select_fld_list flds = Sql_select_fld_list.new_();
	public boolean Distinct() {return distinct;} public void Distinct_set(boolean v) {distinct = v;} private boolean distinct;
	public void Add(String fldName) {flds.Add(Sql_select_fld_.new_fld(Sql_select_fld_base.Tbl_null, fldName, fldName));}
	public void Add(String fldName, String alias) {flds.Add(Sql_select_fld_.new_fld(Sql_select_fld_base.Tbl_null, fldName, alias));}
	public void Add(Sql_select_fld_base fld) {flds.Add(fld);}

	public static final Sql_select All = all_(); static Sql_select all_() {Sql_select rv = new_(); rv.Add(Sql_select_fld_wild.Instance); return rv;}
	public static Sql_select new_() {return new Sql_select();} Sql_select() {}
}

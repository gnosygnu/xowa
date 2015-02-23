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
public class Sql_select_fld_list {
	public int Count() {return hash.Count();}
	public void Add(Sql_select_fld_base fld) {hash.Add(fld.Alias(), fld);}
	public Sql_select_fld_base FetchAt(int i) {return (Sql_select_fld_base)hash.FetchAt(i);}
	public Sql_select_fld_base FetchOrNull(String k) {return (Sql_select_fld_base)hash.Fetch(k);}
	public GfoFldList XtoGfoFldLst(TdbTable tbl) {
		GfoFldList rv = GfoFldList_.new_();
		for (int i = 0; i < this.Count(); i++) {
			Sql_select_fld_base selectFld = this.FetchAt(i);
			GfoFld fld = tbl.Flds().FetchOrNull(selectFld.Fld());
			if (fld == null) throw Err_.new_("fld not found in tbl").Add("fldName", selectFld.Fld()).Add("tblName", tbl.Name()).Add("tblFlds", tbl.Flds().XtoStr());
			if (rv.Has(selectFld.Alias())) throw Err_.new_("alias is not unique").Add("fldName", selectFld.Fld()).Add("flds", rv.XtoStr());
			selectFld.GroupBy_type(fld);
			rv.Add(selectFld.Alias(), selectFld.ValType());
		}
		return rv;
	}
	public String[] To_str_ary() {
		int len = this.Count();
		String[] rv = new String[len];
		for (int i = 0; i < len; i++) {
			Sql_select_fld_base fld = this.FetchAt(i);
			rv[i] = fld.Fld();
		}
		return rv;
	}
	public String XtoStr() {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < this.Count(); i++) {
			Sql_select_fld_base fld = this.FetchAt(i);
			sb.Add_fmt("{0},{1}|", fld.Fld(), fld.Alias());
		}
		return sb.XtoStr();
	}
	OrderedHash hash = OrderedHash_.new_();
	public static Sql_select_fld_list new_() {return new Sql_select_fld_list();} Sql_select_fld_list() {}
}

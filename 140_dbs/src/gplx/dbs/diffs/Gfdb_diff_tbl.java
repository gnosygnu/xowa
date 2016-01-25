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
package gplx.dbs.diffs; import gplx.*; import gplx.dbs.*;
import gplx.dbs.metas.*;
public class Gfdb_diff_tbl {		
	public Gfdb_diff_tbl(String name, Dbmeta_fld_itm[] flds, Dbmeta_fld_itm[] keys, Dbmeta_fld_itm[] vals) {
		this.Name = name; this.Flds = flds; this.Keys = keys; this.Vals = vals;
	}
	public final String Name;
	public final Dbmeta_fld_itm[] Flds;
	public final Dbmeta_fld_itm[] Keys;
	public final Dbmeta_fld_itm[] Vals;
	public Db_rdr Make_rdr(Db_conn conn) {
		Db_stmt stmt = conn.Stmt_select_order(Name, Dbmeta_fld_itm.To_str_ary(Flds), Dbmeta_fld_itm.Str_ary_empty, Dbmeta_fld_itm.To_str_ary(Keys));
		return stmt.Exec_select__rls_auto();
	}

	public static Gfdb_diff_tbl New(Dbmeta_tbl_itm tbl) {
		Dbmeta_fld_mgr flds = tbl.Flds();
		Dbmeta_fld_mgr keys = Calc_keys(tbl);
		Dbmeta_fld_mgr vals = Calc_vals(tbl.Flds(), keys);
		return new Gfdb_diff_tbl(tbl.Name(), flds.To_ary(), keys.To_ary(), vals.To_ary());
	}
	public static Dbmeta_fld_mgr Calc_keys(Dbmeta_tbl_itm tbl) {
		Dbmeta_fld_mgr rv = new Dbmeta_fld_mgr();
		// try to find primary
		Dbmeta_fld_mgr flds = tbl.Flds();
		int flds_len = flds.Len();
		for (int i = 0; i < flds_len; ++i) {
			Dbmeta_fld_itm fld = flds.Get_at(i);
			if (fld.Primary()) {
				rv.Add(fld);
				return rv;
			}
		}
		// try to find shortest unique index
		Dbmeta_idx_itm unique_idx = null; int unique_idx_len = Int_.Max_value;
		Dbmeta_idx_mgr idxs = tbl.Idxs();
		int idxs_len = idxs.Len();
		for (int i = 0; i < idxs_len; ++i) {
			Dbmeta_idx_itm idx = idxs.Get_at(i);
			if (idx.Unique() && idx.Flds.length < unique_idx_len) {	// get first shortest unique index; note that "<" is "get first" whereas "<=" is "get last"
				unique_idx = idx;
				unique_idx_len = idx.Flds.length;
				break;
			}
		}
		if (unique_idx != null) {
			Dbmeta_idx_fld[] idx_flds = unique_idx.Flds;
			int idx_flds_len = idx_flds.length;
			for (int i = 0; i < idx_flds_len; ++i)
				rv.Add(flds.Get_by(idx_flds[i].Name));
			return rv;
		}
		// just add all
		for (int i = 0; i < flds_len; ++i) {
			Dbmeta_fld_itm fld = flds.Get_at(i);
			rv.Add(fld);					
		}
		return rv;
	}
	private static Dbmeta_fld_mgr Calc_vals(Dbmeta_fld_mgr flds, Dbmeta_fld_mgr keys) {
		Dbmeta_fld_mgr rv = new Dbmeta_fld_mgr();
		int flds_len = flds.Len();
		for (int i = 0; i < flds_len; ++i) {
			Dbmeta_fld_itm fld = flds.Get_at(i);
			if (!keys.Has(fld.Name())) rv.Add(fld);
		}
		return rv;
	}
}

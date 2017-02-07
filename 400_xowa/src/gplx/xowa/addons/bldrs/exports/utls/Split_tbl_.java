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
package gplx.xowa.addons.bldrs.exports.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
import gplx.dbs.*;
public class Split_tbl_ {
	public static final    Split_tbl
	  Page			= new Split_tbl__page()
	, Html			= new Split_tbl__html()
	, Srch_word		= new Split_tbl__srch_word()
	, Srch_link		= new Split_tbl__srch_link()
	, Fsdb_fil		= new Split_tbl__fsdb_fil()
	, Fsdb_thm		= new Split_tbl__fsdb_thm()
	, Fsdb_org		= new Split_tbl__fsdb_reg()
	, Fsdb_bin		= new Split_tbl__fsdb_bin()
	;

	public static boolean Tbl_has_blob(Split_tbl tbl) {return String_.Len_gt_0(tbl.Fld_blob());}
	public static void Flds__add_blob_len(Dbmeta_fld_list flds) {
		flds.Insert(flds.Len() - 1, Dbmeta_fld_itm.new_int("blob_len"));	// add "blob_len" in penultimate pos; note that last fld is "blob_fld"
	}
	public static void Bld_insert_by_select(Bry_bfr bfr, String tbl_name, Dbmeta_fld_list flds) {
		int flds_len = flds.Len();
		for (int i = 0; i < flds_len; ++i) {
			if (i != 0) bfr.Add_str_a7(",");
			Dbmeta_fld_itm fld = flds.Get_at(i);
			bfr.Add_str_a7(fld.Name());
		}
		byte[] flds_bry = bfr.To_bry_and_clear();
		bfr.Add_str_u8_fmt("INSERT INTO {0}\n({1})\n", tbl_name, flds_bry);	// ANSI.Y
		bfr.Add_str_u8_fmt("SELECT {0}\nFROM <src_db>{1}\n", flds_bry, tbl_name);
	}
}

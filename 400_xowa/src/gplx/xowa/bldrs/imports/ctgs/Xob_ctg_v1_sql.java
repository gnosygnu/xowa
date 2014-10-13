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
package gplx.xowa.bldrs.imports.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.imports.*;
import gplx.core.flds.*; import gplx.ios.*; import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.ctgs.*;
public class Xob_ctg_v1_sql extends Xob_ctg_v1_base {
	@Override public String Wkr_key() {return KEY;} public static final String KEY = "import.sql.category_v1";
	@Override public Io_sort_cmd Make_sort_cmd() {return new Xob_ctg_v1_sql_make(wiki);}
}
class Xob_ctg_v1_sql_make implements Io_make_cmd {
	public Xob_ctg_v1_sql_make(Xow_wiki wiki) {this.wiki = wiki; db_mgr = wiki.Db_mgr_as_sql();} private Xow_wiki wiki; Xodb_mgr_sql db_mgr;
	public Io_sort_cmd Make_dir_(Io_url v) {return this;}	// ignore
	public void Sort_bgn() {
		usr_dlg = wiki.App().Usr_dlg();
		Io_url sql_url = wiki.Fsys_mgr().Root_dir().GenSubFil(Url_sql);
		Io_mgr._.DeleteFil_args(sql_url).MissingFails_off().Exec();
		sql_wtr = Xob_tmp_wtr.new_wo_ns_(Io_url_gen_.fil_(sql_url), Io_mgr.Len_mb);
		sql_wtr.Bfr().Add_str(Xob_categorylinks_sql.Tbl_categorylinks).Add(Sql_hdr);
	}	Gfo_fld_rdr fld_rdr = Gfo_fld_rdr.xowa_(); Gfo_fld_wtr fld_wtr = Gfo_fld_wtr.xowa_(); Xob_tmp_wtr sql_wtr; Gfo_usr_dlg usr_dlg; boolean is_first = true;
	public byte Line_dlm() {return line_dlm;} public Xob_ctg_v1_sql_make Line_dlm_(byte v) {line_dlm = v; return this;} private byte line_dlm = Byte_ascii.Nil;
	private byte[] prv_ctg_name = Bry_.Empty; int prv_page_id = 0;
	public void Sort_do(Io_line_rdr rdr) {
		if (line_dlm == Byte_ascii.Nil) line_dlm = rdr.Line_dlm();
		fld_rdr.Ini(rdr.Bfr(), rdr.Itm_pos_bgn());
		byte[] ctg_name = fld_rdr.Read_bry_escape();
		ctg_name = Escape_for_sql(wiki, ctg_name);
		int page_id = fld_rdr.Read_int_base85_len5();
		if (Bry_.Eq(prv_ctg_name, ctg_name) && page_id == prv_page_id) return; 
		if (sql_wtr.FlushNeeded(5 + 2 + ctg_name.length)) sql_wtr.Flush(usr_dlg);	// 5=base85; 2=dlms
		byte row_dlm = is_first ? Byte_ascii.Space : Byte_ascii.Comma;	// handle " (" or ",("
		is_first = false;
		sql_wtr.Bfr().Add_byte(row_dlm); 
		fmtr.Bld_bfr_many(sql_wtr.Bfr(), page_id, ctg_name, Byte_ascii.Ltr_p);	// 'p' b/c page_ttl is not available; limitation of ctg_v1
		prv_page_id = page_id;
		prv_ctg_name = ctg_name;
	}
	public void Sort_end() {
		sql_wtr.Bfr().Add_byte(Byte_ascii.Semic);	// close INSERT with ";"
		sql_wtr.Flush(usr_dlg);
		db_mgr.Category_version_update(true);
	}
	private static final byte[] Sql_hdr = Bry_.new_ascii_("INSERT INTO 'categorylinks' VALUES");	
	Bry_fmtr fmtr = Bry_fmtr.new_("(~{page_id},'~{cat_name}','','','','','~{cat_type}')\n", "page_id", "cat_name", "cat_type");
	public static final String Url_sql = "xowa_categorylinks.sql";
	private static byte[] Escape_for_sql(Xow_wiki wiki, byte[] bry) {
		Bry_bfr bfr = wiki.App().Utl_bry_bfr_mkr().Get_b512();
		int len = bry.length;
		boolean dirty = false;
		for (int i = 0; i < len; i++) {
			byte b = bry[i];
			switch (b) {
				case Byte_ascii.Apos:		if (!dirty) {bfr.Add_mid(bry, 0, i); dirty = true;} bfr.Add_byte(Byte_ascii.Backslash).Add_byte(Byte_ascii.Apos); break;
				case Byte_ascii.Backslash:	if (!dirty) {bfr.Add_mid(bry, 0, i); dirty = true;} bfr.Add_byte(Byte_ascii.Backslash).Add_byte(Byte_ascii.Backslash); break;
				default:
					if (dirty) bfr.Add_byte(b);
					break;
			}
		}
		bfr.Mkr_rls();
		return dirty ? bfr.Xto_bry_and_clear() : bry;
	}
}

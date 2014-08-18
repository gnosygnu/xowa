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
import gplx.ios.*; import gplx.dbs.*; import gplx.xowa.dbs.tbls.*; import gplx.xowa.dbs.*;
public class Xob_category_registry_sql implements Xob_cmd {
	public Xob_category_registry_sql(Xob_bldr bldr, Xow_wiki wiki) {this.wiki = wiki;} private Xow_wiki wiki;
	public String Cmd_key() {return KEY;} public static final String KEY = "import.sql.category_registry";
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {}
	public void Cmd_end() {	// NOTE: placing in end, b/c must run *after* page_sql
		Io_url rslt_dir = Xob_category_registry_sql.Get_dir_output(wiki);
		Io_mgr._.DeleteDirDeep(rslt_dir);
		Xob_tmp_wtr rslt_wtr = Xob_tmp_wtr.new_wo_ns_(Io_url_gen_.dir_(rslt_dir), Io_mgr.Len_mb);
		
		Xodb_mgr_sql db_mgr = Xodb_mgr_sql.Get_or_load(wiki);
		Db_provider provider = db_mgr.Fsys_mgr().Provider_core();
		Db_qry_select qry = Db_qry_select.new_()
			.Cols_(Xodb_page_tbl.Fld_page_title, Xodb_page_tbl.Fld_page_id)
			.From_(Xodb_page_tbl.Tbl_name)
			.Where_(Db_crt_.eq_(Xodb_page_tbl.Fld_page_ns, Xow_ns_.Id_category))
			.OrderBy_asc_(Xodb_page_tbl.Fld_page_title);
		DataRdr rdr = DataRdr_.Null;
		Gfo_usr_dlg usr_dlg = wiki.App().Usr_dlg();
		try {
			rdr = qry.Exec_qry_as_rdr(provider);
			while (rdr.MoveNextPeer()) {
				byte[] page_ttl = rdr.ReadBryByStr(Xodb_page_tbl.Fld_page_title);
				int page_id = rdr.ReadInt(Xodb_page_tbl.Fld_page_id);
				if (rslt_wtr.FlushNeeded(page_ttl.length + 2 + 5)) rslt_wtr.Flush(usr_dlg);
				rslt_wtr.Bfr().Add(page_ttl).Add_byte_pipe().Add_base85_len_5(page_id).Add_byte_nl();
			}
		}	finally {rdr.Rls();}
		rslt_wtr.Flush(usr_dlg);
	}
	public void Cmd_print() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return GfoInvkAble_.Rv_unhandled;
	}
	public static Io_url Get_dir_output(Xow_wiki wiki) {return wiki.Fsys_mgr().Tmp_dir().GenSubDir(KEY);}
}

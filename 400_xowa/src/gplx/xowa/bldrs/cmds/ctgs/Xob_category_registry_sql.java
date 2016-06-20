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
package gplx.xowa.bldrs.cmds.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.ios.*; import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.bldrs.wtrs.*;
public class Xob_category_registry_sql implements Xob_cmd {
	public Xob_category_registry_sql(Xob_bldr bldr, Xowe_wiki wiki) {this.wiki = wiki;} private Xowe_wiki wiki;
	public String Cmd_key() {return Xob_cmd_keys.Key_text_cat_core;}
	public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return null;}
	public void Cmd_end() {	// NOTE: placing in end, b/c must run *after* page_sql
		// setup
		wiki.Html_mgr().Importing_ctgs_(Bool_.Y);
		Io_url rslt_dir = Xob_category_registry_sql.Tmp_dir(wiki);
		Io_mgr.Instance.DeleteDirDeep(rslt_dir);
		Xob_tmp_wtr rslt_wtr = Xob_tmp_wtr.new_wo_ns_(Io_url_gen_.dir_(rslt_dir), Io_mgr.Len_mb);
		// read data
		Gfo_usr_dlg usr_dlg = wiki.Appe().Usr_dlg();
		wiki.Init_db_mgr();
		Xowd_page_tbl page_core_tbl = wiki.Db_mgr_as_sql().Core_data_mgr().Tbl__page();
		Db_rdr rdr = page_core_tbl.Conn().Stmt_select_order(page_core_tbl.Tbl_name(), String_.Ary(page_core_tbl.Fld_page_title(), page_core_tbl.Fld_page_id()), String_.Ary(page_core_tbl.Fld_page_ns()), page_core_tbl.Fld_page_title())
			.Crt_int(page_core_tbl.Fld_page_ns(), Xow_ns_.Tid__category)
			.Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				byte[] page_ttl = rdr.Read_bry_by_str(page_core_tbl.Fld_page_title());
				int page_id = rdr.Read_int(page_core_tbl.Fld_page_id());
				if (rslt_wtr.FlushNeeded(page_ttl.length + 2 + 5)) rslt_wtr.Flush(usr_dlg);
				rslt_wtr.Bfr().Add(page_ttl).Add_byte_pipe().Add_base85_len_5(page_id).Add_byte_nl();
			}
		}	finally {rdr.Rls();}
		// cleanup
		rslt_wtr.Flush(usr_dlg);
		wiki.Html_mgr().Importing_ctgs_(Bool_.N);
	}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run()	{}
	public void Cmd_term()	{}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return Gfo_invk_.Rv_unhandled;}
	public static Io_url Tmp_dir(Xowe_wiki wiki) {return wiki.Fsys_mgr().Tmp_dir().GenSubDir(Xob_cmd_keys.Key_text_cat_core);}
}

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
package gplx.xowa.bldrs.wikis.redirects; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wikis.*;
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*; import gplx.xowa.bldrs.oimgs.*;
public class Xob_wiki_redirect_cmd extends Xob_dump_mgr_base {
	private Db_provider provider; private Db_stmt stmt;
	private Xob_wiki_redirect_tbl tbl_redirect;
	private Xodb_mgr_sql db_mgr; private Xop_redirect_mgr redirect_mgr; private Url_encoder encoder;
	public Xob_wiki_redirect_cmd(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	@Override public String Cmd_key() {return KEY_redirect;} public static final String KEY_redirect = "wiki.redirect";
	@Override public int[] Init_ns_ary() {return Int_.Ary(Xow_ns_.Id_file);}	// restrict to file ns
	@Override public byte Init_redirect() {return Bool_.Y_byte;}				// restrict to redirects
	@Override protected void Init_reset(Db_provider p) {
		p.Exec_sql("DELETE FROM " + Xodb_xowa_cfg_tbl.Tbl_name);
		p.Exec_sql("DELETE FROM " + Xob_wiki_redirect_tbl.Tbl_name);
	}
	@Override protected Db_provider Init_db_file() {
		this.db_mgr = wiki.Db_mgr_as_sql();
		Xoa_app app = bldr.App();
		redirect_mgr = wiki.Redirect_mgr();
		encoder = app.Url_converter_url_ttl();
//			app.Usr_dlg().Prog_none("", "", "dropping index: page__title");
//			db_mgr.Fsys_mgr().Core_provider().Exec_sql("DROP INDEX IF EXISTS page__title");
//			Sqlite_engine_.Idx_create(app.Usr_dlg(), db_mgr.Fsys_mgr().Core_provider(), "page", Idx_page_title);
		Xodb_db_file db_file = Xodb_db_file.init__wiki_redirect(wiki.Fsys_mgr().Root_dir());
		provider = db_file.Provider();
		tbl_redirect = new Xob_wiki_redirect_tbl().Create_table(provider);
		stmt = tbl_redirect.Insert_stmt(provider);
		provider.Txn_mgr().Txn_bgn_if_none();
		return provider;
	}
	@Override protected void Cmd_bgn_end() {}
	@Override public void Exec_pg_itm_hook(Xow_ns ns, Xodb_page page, byte[] page_src) {
		Xoa_ttl redirect_ttl = redirect_mgr.Extract_redirect(page_src, page_src.length);
		byte[] redirect_ttl_bry = Xoa_ttl.Replace_spaces(redirect_ttl.Page_db());	// NOTE: spaces can still exist b/c redirect is scraped from #REDIRECT which sometimes has a mix; EX: "A_b c"
		redirect_ttl_bry = encoder.Decode(redirect_ttl_bry);
		tbl_redirect.Insert(stmt, page.Id(), Xoa_ttl.Replace_spaces(page.Ttl_wo_ns()), -1, redirect_ttl.Ns().Id(), redirect_ttl_bry, redirect_ttl.Anch_txt(), 1);
	}
	@Override public void Exec_commit_hook() {
		provider.Txn_mgr().Txn_end_all_bgn_if_none();
	}
	@Override public void Exec_end_hook() {
		provider.Txn_mgr().Txn_end_all();			
		tbl_redirect.Create_indexes(usr_dlg, provider);
		tbl_redirect.Update_redirects(provider, db_mgr.Fsys_mgr().Get_url(Xodb_file.Tid_core), 4);
	}
//		private static final Db_idx_itm Idx_page_title = Db_idx_itm.sql_("CREATE UNIQUE INDEX IF NOT EXISTS page__title ON page (page_namespace, page_title, page_id, page_len, page_is_redirect, page_file_idx);");	// PERF:page_id for general queries; PERF: page_len for search_suggest
}

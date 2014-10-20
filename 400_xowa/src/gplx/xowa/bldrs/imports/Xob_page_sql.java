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
package gplx.xowa.bldrs.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*; import gplx.ios.*; import gplx.xowa.bldrs.wikis.redirects.*;
public class Xob_page_sql extends Xob_itm_basic_base implements Xobd_wkr, GfoInvkAble {
	private Db_idx_mode idx_mode = Db_idx_mode.Itm_end;
	private Io_stream_zip_mgr zip_mgr; private byte data_storage_format; private boolean redirect_id_enabled;
	private Xodb_mgr_sql db_mgr; private Xodb_fsys_mgr fsys_mgr; private Db_provider page_provider; private Db_stmt page_stmt; private Xob_text_stmts_mgr text_stmts_mgr;
	private int page_count_all, page_count_main = 0; private int txn_commit_interval = 100000;	// 100 k
	private DateAdp modified_latest = DateAdp_.MinValue;
	private Xop_redirect_mgr redirect_mgr; private Xob_redirect_tbl redirect_tbl;
	public Xob_page_sql(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Wkr_key() {return KEY;} public static final String KEY = "import.sql.page";
	public void Wkr_bgn(Xob_bldr bldr) {
		// init local variables
		Xoa_app app = wiki.App();
		app.Bldr().Parser().Trie_tab_del_();	// disable swapping &#09; for \t
		zip_mgr = app.Zip_mgr();
		redirect_mgr = wiki.Redirect_mgr(); 
		data_storage_format = app.Setup_mgr().Dump_mgr().Data_storage_format();

		// init db
		db_mgr = wiki.Db_mgr_as_sql();
		db_mgr.Data_storage_format_(data_storage_format);
		fsys_mgr = db_mgr.Fsys_mgr();
		page_provider = fsys_mgr.Provider_page();
		page_stmt = db_mgr.Tbl_page().Insert_stmt(page_provider);
		page_provider.Txn_mgr().Txn_bgn_if_none();
		text_stmts_mgr = new Xob_text_stmts_mgr(db_mgr, fsys_mgr);
		if (idx_mode.Tid_is_bgn()) Idx_create();

		if (redirect_id_enabled) {
			redirect_tbl = new Xob_redirect_tbl(wiki.Fsys_mgr().Root_dir(), app.Encoder_mgr().Url_ttl()).Create_table();
			redirect_tbl.Provider().Txn_mgr().Txn_bgn_if_none();
		}
	}
	public void Wkr_run(Xodb_page page) {
		int page_id = page.Id();
		DateAdp modified = page.Modified_on();
		if (modified.compareTo(modified_latest) == CompareAble_.More) modified_latest = modified;
		byte[] text = page.Text();
		int text_len = page.Text_len();
		Xoa_ttl redirect_ttl = redirect_mgr.Extract_redirect(text, text_len);
		boolean redirect = redirect_ttl != null;
		page.Type_redirect_(redirect);
		Xow_ns ns = page.Ns();
		int random_int = ns.Count() + 1;
		ns.Count_(random_int);		
		text = zip_mgr.Zip(data_storage_format, text);
		int text_stmt_idx = text_stmts_mgr.Get_by_ns(ns.Bldr_file_idx(), text.length);	// NOTE: was text.length, but want text_len which is original page_len, not compressed; DATE:2014-08-04
		Db_stmt text_stmt = text_stmts_mgr.Get_at(text_stmt_idx);
		try {
			db_mgr.Page_create(page_stmt, text_stmt, page_id, page.Ns_id(), page.Ttl_wo_ns(), redirect, modified, text, random_int, text_stmt_idx);
		}
		catch (Exception e) {
			usr_dlg.Warn_many("", "", "failed to insert page: id=~{0} ns=~{1} title=~{2} error=~{3}", page.Id(), page.Ns_id(), String_.new_utf8_(page.Ttl_wo_ns()), Err_.Message_gplx_brief(e));
			page_stmt.New();	// must new stmt variable, else java.sql.SQLException: "statement is not executing"
			text_stmt.New();	// must new stmt variable, else java.sql.SQLException: "statement is not executing"
		}
		if (redirect && redirect_id_enabled) {
			redirect_tbl.Insert(page_id, page.Ttl_wo_ns(), redirect_ttl);
		}
		++page_count_all;
		if (ns.Id_main() && !page.Type_redirect()) ++page_count_main;
		if (page_count_all % txn_commit_interval == 0) text_stmt.Provider().Txn_mgr().Txn_end_all_bgn_if_none();
	}
	public void Wkr_end() {
		usr_dlg.Log_many("", "", "import.page: insert done; committing pages; pages=~{0}", page_count_all);
		page_provider.Txn_mgr().Txn_end_all();
		page_stmt.Rls();
		text_stmts_mgr.Rls();
		usr_dlg.Log_many("", "", "import.page: updating core stats");
		Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
		db_mgr.Tbl_site_stats().Update(page_count_main, page_count_all, ns_mgr.Ns_file().Count());	// save page stats
		db_mgr.Tbl_xowa_ns().Insert(ns_mgr);														// save ns
		db_mgr.Tbl_xowa_db().Commit_all(page_provider, db_mgr.Fsys_mgr().Files_ary());				// save dbs; note that dbs can be saved again later
		db_mgr.Tbl_xowa_cfg().Insert_str(Xodb_mgr_sql.Grp_wiki_init, "props.modified_latest", modified_latest.XtoStr_fmt(DateAdp_.Fmt_iso8561_date_time));
		if (idx_mode.Tid_is_end()) Idx_create();
		if (redirect_id_enabled) {
			redirect_tbl.Provider().Txn_mgr().Txn_end_all();
			Xodb_file core_file = fsys_mgr.Get_tid_root(Xodb_file_tid.Tid_core);
			redirect_tbl.Update_trg_redirect_id(core_file.Url(), 1);
			redirect_tbl.Update_src_redirect_id(core_file.Url(), page_provider);
		}
	}
	private void Idx_create() {
		fsys_mgr.Index_create(usr_dlg, Byte_.Ary(Xodb_file_tid.Tid_core, Xodb_file_tid.Tid_text), Idx_page_title, Idx_page_random);
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_txn_commit_interval_))		txn_commit_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_idx_mode_))					idx_mode = Db_idx_mode.Xto_itm(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_redirect_id_enabled_))		redirect_id_enabled = m.ReadYn("v");
		else													return super.Invk(ctx, ikey, k, m);
		return this;
	}
	private static final String Invk_txn_commit_interval_ = "txn_commit_interval_", Invk_idx_mode_ = "idx_mode_", Invk_redirect_id_enabled_ = "redirect_id_enabled_";
	public void Wkr_ini(Xob_bldr bldr) {}
	public void Wkr_print() {}
	private static final Db_idx_itm
	  Idx_page_title	= Db_idx_itm.sql_("CREATE UNIQUE INDEX IF NOT EXISTS page__title ON page (page_namespace, page_title, page_id, page_len, page_is_redirect);")	// PERF:page_id for general queries; PERF: page_len for search_suggest; PREF:page_is_redirect for oimg
	, Idx_page_random	= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS page__name_random ON page (page_namespace, page_random_int);")
	;
}
class Xob_text_stmts_mgr {
	public Xob_text_stmts_mgr(Xodb_mgr_sql db_mgr, Xodb_fsys_mgr fsys_mgr) {this.db_mgr = db_mgr; this.fsys_mgr = fsys_mgr;} private Xodb_mgr_sql db_mgr; Xodb_fsys_mgr fsys_mgr;
	public Db_stmt Get_at(int i) {return text_stmts[i];}
	public int Get_by_ns(int ns_file_idx, int text_len) {
		Xodb_file file = File_get(ns_file_idx, text_len);
		int stmt_idx = file.Id();
		Db_stmt stmt = null;
		if (stmt_idx < text_stmts_len) {
			stmt = text_stmts[stmt_idx];
			if (stmt != null) return stmt_idx;
		}
		stmt = db_mgr.Tbl_text().Insert_stmt(file.Provider());
		file.Provider().Txn_mgr().Txn_bgn_if_none();	// automatically start txn
		Add(stmt, stmt_idx);
		return stmt_idx;
	}
	public void Rls() {
		for (int i = 0; i < text_stmts_len; i++) {
			Db_stmt stmt = text_stmts[i];
			if (stmt != null) {
				stmt.Provider().Txn_mgr().Txn_end_all();
				stmt.Rls();
			}
			text_stmts[i] = null;
		}
		text_stmts = null;
	}
	Xodb_file File_get(int file_idx, int text_len) {
		if (file_idx == Xow_ns.Bldr_file_idx_heap) {
			file_idx = fsys_mgr.Tid_text_idx();
			Xodb_file file = fsys_mgr.Get_or_make(Xodb_file_tid.Tid_text, file_idx);
			long file_len = file.File_len();
			long file_max = fsys_mgr.Tid_text_max();
			if (file_max != Xodb_fsys_mgr.Heap_max_infinite && (file_len + text_len > file_max)) {	// file is "full"
				file.Provider().Txn_mgr().Txn_end_all();	// close txn
				file = fsys_mgr.Make(Xodb_file_tid.Tid_text);
				file_idx = file.Id();
				fsys_mgr.Tid_text_idx_(file_idx);
			}
			file.File_len_add(text_len);
			return file;
		}
		else
			return fsys_mgr.Get_or_make(Xodb_file_tid.Tid_text, file_idx);
	}
	private void Add(Db_stmt stmt, int stmt_idx) {
		int new_len = stmt_idx + 1;
		if (new_len > text_stmts_max) {	// ary too small >>> expand
			text_stmts_max = new_len * 2;
			Db_stmt[] text_stmts_subs = new Db_stmt[text_stmts_max];
			Array_.CopyTo(text_stmts, 0, text_stmts_subs, 0, text_stmts_len);
			text_stmts = text_stmts_subs;
		}
		text_stmts[stmt_idx] = stmt;
		text_stmts_len = new_len;
	}	private Db_stmt[] text_stmts = new Db_stmt[0]; int text_stmts_len, text_stmts_max = 0;
}

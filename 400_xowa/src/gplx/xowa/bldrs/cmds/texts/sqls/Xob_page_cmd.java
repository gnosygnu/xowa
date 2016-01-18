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
package gplx.xowa.bldrs.cmds.texts.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.dbs.*; import gplx.core.ios.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.wikis.*;
import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.dbs.*; 
import gplx.xowa.wikis.*; import gplx.xowa.bldrs.filters.dansguardians.*; import gplx.xowa.apps.apis.xowa.bldrs.imports.*;
import gplx.xowa.parsers.utils.*;
public class Xob_page_cmd extends Xob_itm_basic_base implements Xobd_wkr, GfoInvkAble {
	private Xowd_db_mgr db_mgr; private Db_idx_mode idx_mode = Db_idx_mode.Itm_end; private Xowd_page_tbl page_core_tbl; private Io_stream_zip_mgr text_zip_mgr; private byte text_zip_tid;
	private Xop_redirect_mgr redirect_mgr; private Xob_redirect_tbl redirect_tbl; private boolean redirect_id_enabled;
	private DateAdp modified_latest = DateAdp_.MinValue; private int page_count_all, page_count_main = 0; private int commit_interval = 100000;	// 100 k				
	private Dg_match_mgr dg_match_mgr; private Xob_ns_to_db_mgr ns_to_db_mgr; 
	public Xob_page_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Wkr_key() {return Xob_cmd_keys.Key_text_page;}
	public void Wkr_bgn(Xob_bldr bldr) {
		Xoae_app app = wiki.Appe();
		Xoapi_import import_cfg = app.Api_root().Bldr().Wiki().Import();
		this.redirect_mgr = wiki.Redirect_mgr(); 
		this.db_mgr = wiki.Db_mgr_as_sql().Core_data_mgr();
		this.page_core_tbl = db_mgr.Tbl__page();
		this.text_zip_mgr = Xoa_app_.Utl__zip_mgr(); text_zip_tid = import_cfg.Zip_tid_text();
		this.ns_to_db_mgr = new Xob_ns_to_db_mgr(new Xob_ns_to_db_wkr__text(), db_mgr, import_cfg.Text_db_max());
		this.dg_match_mgr = app.Api_root().Bldr().Wiki().Filter().Dansguardian().New_mgr(wiki.Domain_str(), wiki.Fsys_mgr().Root_dir());
		if (dg_match_mgr != null) redirect_id_enabled = true; // always enable redirect_id if dg_match_mgr enabled; DATE:2016-01-04
		if (redirect_id_enabled) {
			this.redirect_tbl = new Xob_redirect_tbl(wiki.Fsys_mgr().Root_dir(), gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url_ttl).Create_table();
			redirect_tbl.Conn().Txn_bgn("bldr__page__redirect");
		}
		app.Bldr().Dump_parser().Trie_tab_del_();	// disable swapping &#09; for \t
		byte[] ns_file_map = import_cfg.New_ns_file_map(wiki.Import_cfg().Src_rdr_len());
		Xob_ns_file_itm.Init_ns_bldr_data(Xowd_db_file_.Tid_text, wiki.Ns_mgr(), ns_file_map);
		if (idx_mode.Tid_is_bgn()) page_core_tbl.Create_index();
		page_core_tbl.Insert_bgn();
		usr_dlg.Prog_many("", "", "import.page.bgn");
	}
	public void Wkr_run(Xowd_page_itm page) {
		int id = page.Id();
		DateAdp modified = page.Modified_on(); if (modified.compareTo(modified_latest) == CompareAble_.More) modified_latest = modified;
		byte[] text_raw = page.Text(); int text_raw_len = page.Text_len();
		Xoa_ttl redirect_ttl = redirect_mgr.Extract_redirect(text_raw, text_raw_len); boolean redirect = redirect_ttl != null;
		page.Redirected_(redirect);
		Xow_ns ns = page.Ns();
		int random_int = ns.Count() + 1; ns.Count_(random_int);
		if (dg_match_mgr != null) {
			if (dg_match_mgr.Match(1, id, ns.Id(), page.Ttl_page_db(), page.Ttl_full_db(), wiki.Lang(), text_raw)) return;
		}
		byte[] text_zip = text_zip_mgr.Zip(text_zip_tid, text_raw);
		Xowd_db_file text_db = ns_to_db_mgr.Get_by_ns(ns.Bldr_data(), text_zip.length);
		try {db_mgr.Create_page(page_core_tbl, text_db.Tbl__text(), id, page.Ns_id(), page.Ttl_page_db(), redirect, modified, text_zip, text_raw_len, random_int, text_db.Id(), -1);}
		catch (Exception e) {
			throw Err_.new_exc(e, "bldr", "create page in db failed; skipping page", "id", id, "ns", page.Ns_id(), "name", page.Ttl_page_db(), "redirect", redirect, "modified", modified, "text_len", text_raw_len, "text_db_id", text_db.Id());
		}
		if (redirect && redirect_id_enabled)
			redirect_tbl.Insert(id, page.Ttl_page_db(), redirect_ttl);
		++page_count_all;
		if (ns.Id_is_main() && !page.Redirected()) ++page_count_main;
		if (page_count_all % commit_interval == 0) {
			page_core_tbl.Conn().Txn_sav(); text_db.Conn().Txn_sav();
			if (redirect_id_enabled) redirect_tbl.Conn().Txn_sav();
			if (dg_match_mgr != null) dg_match_mgr.Commit();
		}
	}
	public void Wkr_end() {
		usr_dlg.Log_many("", "", "import.page: insert done; committing pages; pages=~{0}", page_count_all);
		page_core_tbl.Insert_end(); ns_to_db_mgr.Rls_all();
		if (dg_match_mgr != null) dg_match_mgr.Rls();
		usr_dlg.Log_many("", "", "import.page: updating core stats");
		Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
		Xowd_db_file db_core = db_mgr.Db__core();
		db_core.Tbl__site_stats().Update(page_count_main, page_count_all, ns_mgr.Ns_file().Count());	// save page stats
		db_core.Tbl__ns().Insert(ns_mgr);															// save ns
		db_mgr.Tbl__cfg().Insert_str(Xow_cfg_consts.Grp__wiki_init, "props.modified_latest", modified_latest.XtoStr_fmt(DateAdp_.Fmt_iso8561_date_time));
		if (idx_mode.Tid_is_end()) page_core_tbl.Create_index();
		if (redirect_id_enabled) {
			redirect_tbl.Conn().Txn_end();
			redirect_tbl.Update_trg_redirect_id(db_core.Url(), 1);
			redirect_tbl.Update_src_redirect_id(db_core.Url(), page_core_tbl.Conn());
		}
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_commit_interval_))			commit_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_idx_mode_))					idx_mode = Db_idx_mode.Xto_itm(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_redirect_id_enabled_))		redirect_id_enabled = m.ReadYn("v");
		else													return super.Invk(ctx, ikey, k, m);
		return this;
	}
	private static final String Invk_commit_interval_ = "commit_interval_", Invk_idx_mode_ = "idx_mode_", Invk_redirect_id_enabled_ = "redirect_id_enabled_";
	public void Wkr_ini(Xob_bldr bldr) {}
	public void Wkr_print() {}
}

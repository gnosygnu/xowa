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
package gplx.xowa.dbs; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*;
import gplx.xowa.apps.*; import gplx.xowa.dbs.tbls.*; import gplx.xowa.ctgs.*; import gplx.xowa.hdumps.*;
public class Xodb_mgr_sql implements Xodb_mgr, GfoInvkAble {
	private boolean html_db_enabled;
	public Xodb_mgr_sql(Xow_wiki wiki) {
		this.wiki = wiki;
		fsys_mgr = new Xodb_fsys_mgr(wiki.App().Fsys_mgr().Bin_db_dir(), wiki.Fsys_mgr().Root_dir(), wiki.Domain_str());
		load_mgr = new Xodb_load_mgr_sql(this, fsys_mgr);
		save_mgr = new Xodb_save_mgr_sql(this);
		tbl_text = new Xodb_text_tbl(this);
		tbl_page = new Xodb_page_tbl(wiki);
		hdump_mgr = new Xodb_hdump_mgr(wiki);
	}
	public byte Tid() {return Tid_sql;} public static final byte Tid_sql = 1;
	public String Tid_name() {return "sqlite3";}
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public byte Data_storage_format() {return data_storage_format;} public void Data_storage_format_(byte v) {data_storage_format = v;} private byte data_storage_format = gplx.ios.Io_stream_.Tid_gzip;		
	public byte Category_version() {return category_version;} private byte category_version = Xoa_ctg_mgr.Version_null;
	public byte Search_version() {return load_mgr.Search_version();}
	public void Search_version_refresh() {load_mgr.Search_version_refresh();}
	public void Html_db_enabled_(boolean v) {
		html_db_enabled = v; db_ctx.Html_db_enabled_(v);
		hdump_mgr.Enabled_(v);
		tbl_page.Html_db_enabled_(v);
		if (v) Xodb_hdump_mgr_setup.Hdump_db_file_init(hdump_mgr);
	}
	public Xodb_ctx Db_ctx() {return db_ctx;} private Xodb_ctx db_ctx = new Xodb_ctx();
	public Xodb_fsys_mgr Fsys_mgr() {return fsys_mgr;} private Xodb_fsys_mgr fsys_mgr;
	public Xodb_load_mgr Load_mgr() {return load_mgr;} private Xodb_load_mgr_sql load_mgr;
	public Xodb_save_mgr Save_mgr() {return save_mgr;} private Xodb_save_mgr_sql save_mgr;
	public Xodb_hdump_mgr Hdump_mgr() {return hdump_mgr;} private Xodb_hdump_mgr hdump_mgr;
	public Xodb_xowa_cfg_tbl Tbl_xowa_cfg() {return tbl_cfg;} private Xodb_xowa_cfg_tbl tbl_cfg = new Xodb_xowa_cfg_tbl();
	public Xodb_xowa_ns_tbl Tbl_xowa_ns() {return tbl_ns;} private Xodb_xowa_ns_tbl tbl_ns = new Xodb_xowa_ns_tbl();
	public Xodb_xowa_db_tbl Tbl_xowa_db() {return tbl_db;} private Xodb_xowa_db_tbl tbl_db = new Xodb_xowa_db_tbl();
	public Xodb_page_tbl Tbl_page() {return tbl_page;} private Xodb_page_tbl tbl_page;
	public Xodb_text_tbl Tbl_text() {return tbl_text;} private Xodb_text_tbl tbl_text;
	public Xodb_site_stats_tbl Tbl_site_stats() {return tbl_site_stats;} private Xodb_site_stats_tbl tbl_site_stats = new Xodb_site_stats_tbl();
	public Xodb_wdata_qids_tbl Tbl_wdata_qids() {return tbl_wdata_qids;} private Xodb_wdata_qids_tbl tbl_wdata_qids = new Xodb_wdata_qids_tbl();
	public Xodb_wdata_pids_tbl Tbl_wdata_pids() {return tbl_wdata_pids;} private Xodb_wdata_pids_tbl tbl_wdata_pids = new Xodb_wdata_pids_tbl();
	public Xodb_category_tbl Tbl_category() {return tbl_category;} private Xodb_category_tbl tbl_category = new Xodb_category_tbl();
	public Xodb_categorylinks_tbl Tbl_categorylinks() {return tbl_categorylinks;} private Xodb_categorylinks_tbl tbl_categorylinks = new Xodb_categorylinks_tbl();
	public Xodb_search_title_word_tbl Tbl_search_title_word() {return tbl_search_title_word;} private Xodb_search_title_word_tbl tbl_search_title_word = new Xodb_search_title_word_tbl();
	public Xodb_search_title_page_tbl Tbl_search_title_page() {return tbl_search_title_page;} private Xodb_search_title_page_tbl tbl_search_title_page = new Xodb_search_title_page_tbl();
	public byte State() {return state;} private byte state = State_init; public static final byte State_init = 0, State_make = 1, State_load = 2;
	public DateAdp Dump_date_query() {
		DateAdp rv = wiki.Props().Modified_latest();
		if (rv != null) return rv;
		Io_url url = fsys_mgr.Get_url(Xodb_file_tid.Tid_core);
		return Io_mgr._.QueryFil(url).ModifiedTime();
	}
	public void Init_by_ns_map(String ns_map) {
		Xoi_dump_mgr dump_mgr = wiki.App().Setup_mgr().Dump_mgr();
		data_storage_format = dump_mgr.Data_storage_format();
		fsys_mgr.Init_by_ns_map(wiki.Ns_mgr(), ns_map, dump_mgr.Db_text_max());
		Core_provider_(fsys_mgr.Provider_core());
		state = State_make;
	}
	public void Init_load(Db_conn_info connect) {
		Db_provider provider = Db_provider_pool._.Get_or_new(connect);
		Xodb_file[] files = tbl_db.Select_all(provider);
		fsys_mgr.Init_by_files(provider, files);
		Core_provider_(provider);
		state = State_load;
	}
	private void Core_provider_(Db_provider provider) {
		tbl_cfg.Provider_(provider);
		tbl_ns.Provider_(provider);
		tbl_db.Provider_(provider);
		tbl_page.Provider_(provider);
		tbl_site_stats.Provider_(provider);
	}
	public void Page_create(Db_stmt page_stmt, Db_stmt text_stmt, int page_id, int ns_id, byte[] ttl_wo_ns, boolean redirect, DateAdp modified_on, byte[] text, int random_int, int file_idx) {
		int text_len = text.length;
		int html_db_id = (html_db_enabled) ? -1 : hdump_mgr.Html_db_id_default(text_len);
		tbl_page.Insert(page_stmt, page_id, ns_id, ttl_wo_ns, redirect, modified_on, text_len, random_int, file_idx, html_db_id);
		tbl_text.Insert(text_stmt, page_id, text, data_storage_format);
	}
	public boolean Ctg_select_v1(Xoctg_view_ctg view_ctg, Db_provider ctg_provider, Xodb_category_itm ctg) {
		Db_qry_select qry = Db_qry_.select_().Cols_(Xodb_categorylinks_tbl.Fld_cl_from)
			.From_(Xodb_categorylinks_tbl.Tbl_name)
			.Where_(Db_crt_.eq_(Xodb_categorylinks_tbl.Fld_cl_to_id, ctg.Id()))
		;
		ListAdp rslts = ListAdp_.new_();
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = ctg_provider.Exec_qry_as_rdr(qry);
			while (rdr.MoveNextPeer()) {
				int page_id = rdr.ReadInt(Xodb_categorylinks_tbl.Fld_cl_from);
				Xodb_page itm = new Xodb_page().Id_(page_id);
				rslts.Add(itm);
			}
		}	finally {rdr.Rls();}
		int rslts_len = rslts.Count();
		rslts.SortBy(Xodb_page_sorter.IdAsc);
		tbl_page.Select_by_id_list(Cancelable_.Never, false, rslts, 0, rslts_len);
		rslts.SortBy(Xodb_page_sorter.Ns_id_TtlAsc);
		boolean rv = false;
		for (int i = 0; i < rslts.Count(); i++) {
			Xodb_page page = (Xodb_page)rslts.FetchAt(i);
			if (page.Ns_id() == Int_.MinValue) continue;	// HACK: page not found; ignore
			byte ctg_tid = Xodb_load_mgr_txt.Load_ctg_v1_tid(page.Ns_id());
			Xoctg_view_grp ctg_grp = view_ctg.Grp_by_tid(ctg_tid);
			Xoctg_view_itm ctg_itm = new Xoctg_view_itm();
			ctg_itm.Load_by_ttl_data(ctg_tid, page.Id(), 0, page.Text_len());
			ctg_itm.Ttl_(Xoa_ttl.parse_(wiki, page.Ns_id(), page.Ttl_wo_ns()));
			ctg_itm.Sortkey_(page.Ttl_wo_ns());
			ctg_grp.Itms_add(ctg_itm);
			rv = true;
		}
		for (byte i = 0; i < Xoa_ctg_mgr.Tid__max; i++) {
			Xoctg_view_grp ctg_grp = view_ctg.Grp_by_tid(i);
			ctg_grp.Itms_make();
			ctg_grp.Total_(ctg_grp.Itms().length);
		}
		return rv;
	}
	public void Rls() {
		fsys_mgr.Rls();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_data_storage_format))				return Xoi_dump_mgr.Wtr_tid_to_str(data_storage_format);
		else if	(ctx.Match(k, Invk_data_storage_format_))				data_storage_format = Xoi_dump_mgr.Wtr_tid_parse(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_category_version))					return category_version;
		else if	(ctx.Match(k, Invk_category_version_))					category_version = m.ReadByte("v");
		else if	(ctx.Match(k, Invk_search_version))						return this.Search_version();
		else if	(ctx.Match(k, Invk_tid_name))							return this.Tid_name();
		else if	(ctx.Match(k, Invk_html_mgr))							return hdump_mgr;
		return this;
	}
	public static final String 
	  Invk_data_storage_format = "data_storage_format", Invk_data_storage_format_ = "data_storage_format_"
	, Invk_category_version = "category_version", Invk_category_version_ = "category_version_"
	, Invk_search_version = "search_version"
	, Invk_tid_name = "tid_name", Invk_html_mgr = "hdump_mgr"
	;
	public void Category_version_update(boolean version_is_1) {
		String grp = Xodb_mgr_sql.Grp_wiki_init;
		String key = Xoa_gfs_mgr.Build_code(Xow_wiki.Invk_db_mgr, Xodb_mgr_sql.Invk_category_version);
//			if (category_version != Xoa_ctg_mgr.Version_null)
			tbl_cfg.Delete(grp, key);// always delete ctg version
		category_version = version_is_1 ? Xoa_ctg_mgr.Version_1 : Xoa_ctg_mgr.Version_2;
		tbl_cfg.Insert_str(grp, key, Byte_.Xto_str(category_version));
	}
	public void Delete_by_tid(byte tid) {
		Xodb_file[] ary = fsys_mgr.Files_ary();
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xodb_file file = ary[i] ;
			if (file.Tid() != tid) continue;
			file.Rls();
			gplx.dbs.Db_conn_info__sqlite sqlite = (gplx.dbs.Db_conn_info__sqlite)file.Connect();
			Io_mgr._.DeleteFil_args(sqlite.Url()).MissingFails_off().Exec();
			file.Cmd_mode_(Db_cmd_mode.Delete);
		}
		tbl_db.Commit_all(fsys_mgr.Provider_core(), ary);
		this.Init_load(fsys_mgr.Provider_core().Conn_info());
	}

	public static final String Grp_wiki_init = "wiki.init";
	public static final int Page_id_null = -1;
	public static Io_url Find_core_url(Xow_wiki wiki) {
		Io_url[] ary = Io_mgr._.QueryDir_args(wiki.Fsys_mgr().Root_dir()).FilPath_("*.sqlite3").ExecAsUrlAry();
		int ary_len = ary.length; if (ary_len == 0) return null;
		if (ary_len == 1) return ary[0];							// only 1 file; assume it is core
		String v0_str = wiki.Domain_str() + ".000";
		String v1_str = wiki.Domain_str() + "core.000";
		for (int i = 0; i < ary_len; i++) {
			Io_url itm = ary[i];
			if		(String_.Eq(itm.NameOnly(), v0_str)) return itm;	// EX: "en.wikipedia.org.000"
			else if (String_.Eq(itm.NameOnly(), v1_str)) return itm;	// EX: "en.wikipedia.org.core.000"
		}
		return null;
	}
	public static Xodb_mgr_sql Get_or_load(Xow_wiki wiki) {
		Xodb_mgr db_mgr = wiki.Db_mgr();
		Xodb_mgr_sql rv = db_mgr.Tid() == Xodb_mgr_txt.Tid_txt ? wiki.Db_mgr_create_as_sql() : wiki.Db_mgr_as_sql();
		byte state = rv.State();
		switch (state) {
			case Xodb_mgr_sql.State_init: rv.Init_load(Db_conn_info_.sqlite_(Xodb_mgr_sql.Find_core_url(wiki))); break; // load
			case Xodb_mgr_sql.State_make: break;	// noop; being made; don't load from db;
			case Xodb_mgr_sql.State_load: break;	// noop; already loaded;
			default: throw Err_.unhandled(state);
		}
		return rv;
	}
}

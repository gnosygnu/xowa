/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.addons.wikis.searchs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.wikis.searchs.dbs.*; import gplx.xowa.addons.wikis.searchs.parsers.*;
class Srch_temp_tbl_wkr implements Srch_text_parser_wkr {
	private Xowe_wiki wiki; private Xow_db_mgr core_data_mgr; private Srch_search_addon search_addon; 
	private Srch_text_parser title_parser;
	private Srch_temp_tbl search_temp_tbl; private int word_id, page_id;	// needed for Parse_done
	public Srch_temp_tbl_wkr Init(boolean cmd, Xowe_wiki wiki) {
		// init
		this.wiki = wiki;
		this.core_data_mgr = wiki.Db_mgr_as_sql().Core_data_mgr();
		this.search_addon = Srch_search_addon.Get(wiki);
		this.title_parser = search_addon.Ttl_parser();
		this.word_id = 0;
		// assert search_word / search_link tables
		Srch_db_mgr search_db_mgr = search_addon.Db_mgr();
		if (cmd) {											// run from maint or from import.offline
			if (search_db_mgr.Tbl__word() != null)			// called from maint; note that import.offline will always be null (since tables don't exist); DATE:2016-04-04
				search_db_mgr.Delete_all(core_data_mgr);	// always delete if launched by cmd
		}
		search_db_mgr.Create_all();
		// start processing search_temp
		this.search_temp_tbl = new Srch_temp_tbl(search_db_mgr.Tbl__word().conn);
		search_temp_tbl.Insert_bgn();
		return this;
	}
	public void Term() {
		// end inserts
		search_temp_tbl.Insert_end();

		// init
		Srch_db_mgr search_db_mgr = search_addon.Db_mgr().Init(wiki.Stats().Num_pages());	// NOTE: must call .Init for import-offline else Cfg_tbl will be null; note that .Init will bind to newly created search_word / search_link tbl; DATE:2016-04-04
		Db_conn word_conn = search_temp_tbl.conn;

		// update search_word ids if they exist
		// Srch_db_mgr.Optimize_unsafe_(word_conn, Bool_.Y);	// NOTE: fails in multi-db due to transaction
		Update_word_id(word_conn, wiki);
		Search_word__insert(word_conn);
		// Srch_db_mgr.Optimize_unsafe_(word_conn, Bool_.N);

		// create search_link
		Db_conn page_conn = wiki.Data__core_mgr().Tbl__page().Conn();
		if (search_db_mgr.Tbl__link__len() == 1) {
			// single_db; just run sql;
			Xoa_app_.Usr_dlg().Plog_many("", "", "creating search_link");
			Srch_link_tbl link_tbl = search_db_mgr.Tbl__link__ary()[0];
			new Db_attach_mgr(word_conn, new Db_attach_itm("link_db", link_tbl.conn))
				.Exec_sql(String_.Concat_lines_nl_skip_last
				( "INSERT INTO <link_db>search_link (word_id, page_id)"
				, "SELECT  w.word_id"
				, ",       t.page_id"
				, "FROM    search_temp t"
				, "        JOIN search_word w ON t.word_text = w.word_text"
				));
			link_tbl.Create_idx__page_id();
		} else {
			Search_link__insert(search_db_mgr, word_conn, page_conn);
		}

		// remove search_temp
		word_conn.Meta_tbl_delete(search_temp_tbl.tbl_name);
	}
	private static void Search_link__insert(Srch_db_mgr search_db_mgr, Db_conn word_conn, Db_conn page_conn) {
		// many_db; split into main links and non-main; ASSUME:link_dbs_is_2
		Db_attach_mgr attach_mgr = new Db_attach_mgr();

		// dump everything into a temp table in order to index it
		page_conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl("page", "page_ns__page_id", "page_namespace", "page_id"));
		Srch_db_mgr.Optimize_unsafe_(word_conn, Bool_.Y);
		word_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New("search_link_temp", Dbmeta_fld_itm.new_int("word_id"), Dbmeta_fld_itm.new_int("page_id"), Dbmeta_fld_itm.new_int("page_namespace")));
		attach_mgr.Conn_main_(word_conn).Conn_links_(new Db_attach_itm("page_db", page_conn));
		attach_mgr.Exec_sql_w_msg
		( "filling search_link_temp (please wait)", String_.Concat_lines_nl_skip_last
		( "INSERT INTO search_link_temp (word_id, page_id, page_namespace)"
		, "SELECT  w.word_id"
		, ",       t.page_id"
		, ",       p.page_namespace"
		, "FROM    search_temp t"
		, "        JOIN search_word w ON t.word_text = w.word_text"
		, "        JOIN <page_db>page p ON t.page_id = p.page_id"
		));
		word_conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_name("search_link_temp", "main", "page_namespace", "word_id", "page_id"));
		Srch_db_mgr.Optimize_unsafe_(word_conn, Bool_.N);
		page_conn.Meta_idx_delete("page", "page_ns__page_id");

		int len = search_db_mgr.Tbl__link__len();
		for (int i = 0; i < len; ++i) {
			Xoa_app_.Usr_dlg().Plog_many("", "", "creating search_link_temp: ~{0}", i);
			Srch_link_tbl link_tbl = search_db_mgr.Tbl__link__ary()[i];
			Srch_db_mgr.Optimize_unsafe_(link_tbl.conn, Bool_.Y);
			attach_mgr.Conn_main_(link_tbl.conn).Conn_links_(new Db_attach_itm("word_db", word_conn));
			attach_mgr.Exec_sql_w_msg
			( Bry_fmt.Make_str("filling search_link: ~{idx} of ~{len}", i, len), String_.Concat_lines_nl_skip_last
			( "INSERT INTO search_link (word_id, page_id)"
			, "SELECT  t.word_id"
			, ",       t.page_id"
			, "FROM    <word_db>search_link_temp t"
			, "WHERE   t.page_namespace" + (i == 0 ? " = 0" : " != 0")
			));
			link_tbl.Create_idx__page_id();
			Srch_db_mgr.Optimize_unsafe_(link_tbl.conn, Bool_.N);
		}
		word_conn.Meta_tbl_delete("search_link_temp");
	}
	public void Exec_by_wkr(int page_id, byte[] page_ttl) {
		this.page_id = page_id;
		title_parser.Parse(this, page_ttl);
	}
	public void Exec_by_cmd(Xowe_wiki wiki, int commit_interval, int progress_interval) {
		this.Init(Bool_.Y, wiki);
		Xowd_page_tbl page_tbl = core_data_mgr.Tbl__page(); String fld_page_id = page_tbl.Fld_page_id(); String fld_page_ttl = page_tbl.Fld_page_title();			
		Db_rdr page_rdr = page_tbl.Select_all__id__ttl(); int page_count = 0;
		try {
			while (page_rdr.Move_next()) {
				this.page_id = page_rdr.Read_int(fld_page_id);
				byte[] page_ttl = page_rdr.Read_bry_by_str(fld_page_ttl);
				try {title_parser.Parse(this, page_ttl);}
				catch (Exception e) {Xoa_app_.Usr_dlg().Warn_many("", "", "error while parsing title; id=~{0} title=~{1} err=~{2}", page_id, page_ttl, Err_.Message_gplx_log(e));}
				++page_count;
				if	((page_count % commit_interval) == 0)	search_temp_tbl.conn.Txn_sav();
				if	((page_count % progress_interval) == 0)	Gfo_usr_dlg_.Instance.Prog_many("", "", "parse progress: count=~{0} last=~{1}", page_count, String_.new_u8(page_ttl));
			}
		}	finally {page_rdr.Rls();}
		this.Term();
	}
	public void Parse_done(Srch_word_itm word) {
		search_temp_tbl.Insert_cmd_by_batch(++word_id, page_id, word.Word);
	}
	private void Update_word_id(Db_conn cur_conn, Xow_wiki wiki) {
		// see if prv_url exists
		Io_url cur_url = gplx.dbs.engines.sqlite.Sqlite_conn_info.To_url(cur_conn);					// EX: /xowa/wiki/en.wikipedia.org/en.wikipedia.org-xtn.search.core.xowa
		Io_url prv_url = wiki.Fsys_mgr().Root_dir().GenSubFil_nest("prv", cur_url.NameAndExt());	// EX: /xowa/wiki/en.wikipedia.org/prv/en.wikipedia.org-xtn.search.core.xowa
		if (!Io_mgr.Instance.Exists(prv_url)) return;
		// update ids for old_words
		cur_conn.Exec_sql("UPDATE search_temp SET word_id = -1");
		Db_conn prv_conn = Db_conn_bldr.Instance.Get_or_noop(prv_url);			
		new Db_attach_mgr(cur_conn, new Db_attach_itm("prv_db", prv_conn))
			.Exec_sql_w_msg("updating old word ids", String_.Concat_lines_nl_skip_last
		( "UPDATE  search_temp"
		, "SET     word_id = Coalesce((SELECT prv.word_id FROM <prv_db>search_word prv WHERE prv.word_text = search_temp.word_text), -1)"
		));
		// assign incrementing int to new_words
		int nxt_word_id = cur_conn.Exec_select_as_int("SELECT Max(word_id) AS word_id FROM search_temp", -1); if (nxt_word_id == -1) throw Err_.new_("dbs", "max_id not found");
		int uids_max = 10000;
		int[] uids_ary = new int[uids_max]; int uid_last = -1;
		Db_stmt update_stmt = cur_conn.Stmt_update("search_temp", String_.Ary("word_uid"), "word_id");
		while (true) {
			// read 10,000 into memory
			int uids_len = 0;
			Db_rdr rdr = cur_conn.Exec_rdr("SELECT word_uid FROM search_temp WHERE word_id = -1 AND word_uid > " + Int_.To_str(uid_last));
			try {
				while (rdr.Move_next()) {
					int uid = rdr.Read_int("word_uid");
					uids_ary[uids_len++] = uid;
					if (uids_len == uids_max) {
						uid_last = uid;
						break;
					}
				}
			}
			finally {rdr.Rls();}
			if (uids_len == 0) break;
			// update
			for (int i = 0; i < uids_max; ++i) {
				int uid = uids_ary[i];
				update_stmt.Clear().Val_int("word_id", ++nxt_word_id).Crt_int("word_uid", uid).Exec_update();
			}
		}
	}
	private static void Search_word__insert(Db_conn word_conn) {
		// fill search_word; need to insert into temp first b/c PRIMARY KEY on search_word will dramatically slow down insertion
		word_conn.Meta_tbl_create(Dbmeta_tbl_itm.New("search_word_temp"
		, Dbmeta_fld_itm.new_int("word_id")
		, Dbmeta_fld_itm.new_str("word_text", 255)
		, Dbmeta_fld_itm.new_int("link_count")
		));
		word_conn.Exec_sql_concat_w_msg
		( "filling search_word_temp (please wait)"
		, "INSERT INTO search_word_temp (word_id, word_text, link_count)"
		, "SELECT  Min(word_id)"
		, ",       word_text"
		, ",       Count(word_id)"	// NOTE: no need to be Count(DISTINCT page_id); each search_temp row will only insert one word per page
		, "FROM    search_temp"
		, "GROUP BY "
		, "        word_text"
		, ";"
		);
		word_conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_name("search_word_temp", "main", "word_id"));
		word_conn.Exec_sql_concat_w_msg
		( "filling search_word (please wait)"
		, "INSERT INTO search_word (word_id, word_text, link_count)"
		, "SELECT  word_id"
		, ",       word_text"
		, ",       link_count"
		, "FROM    search_word_temp"
		, "ORDER BY "
		, "        word_id"
		, ";"
		);
		word_conn.Meta_tbl_delete("search_word_temp");
	}
}

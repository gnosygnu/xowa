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
public class Xodb_file {
	public Xodb_file(int id, byte tid) {this.id = id; this.tid = tid;}
	public byte Cmd_mode() {return cmd_mode;} public Xodb_file Cmd_mode_(byte v) {cmd_mode = v; return this;} private byte cmd_mode;
	public int Id() {return id;} private int id;
	public byte Tid() {return tid;} private byte tid;
	public Io_url Url() {return url;} public Xodb_file Url_(Io_url v) {url = v; return this;} private Io_url url;
	public String Url_rel() {return url_rel;} public Xodb_file Url_rel_(String v) {this.url_rel = v; return this;} private String url_rel;
	public Db_connect Connect() {return connect;} public Xodb_file Connect_(Db_connect v) {connect = v; return this;} Db_connect connect;
	public long File_len() {return file_len;} public void File_len_add(int v) {file_len += v;} long file_len;
	public long File_max() {return file_max;} public Xodb_file File_max_(long v) {file_max = v; return this;} long file_max;
	public Db_provider Provider() {
		if (provider == null) provider = Db_provider_pool._.FetchOrNew(connect);
		return provider;
	}	Db_provider provider;
	public void Provider_(Db_provider p) {provider = p;}
	public void Rls() {
		if (provider == null) return;
		try {
			provider.Txn_mgr().Txn_end_all();	// close any open transactions
			provider.Rls();
		}	finally {provider = null;}
	}
	public static final byte Tid_core = 1, Tid_text = 2, Tid_category = 3, Tid_search = 4, Tid_wikidata = 5, Tid_temp = 6;
	public static String Tid_to_name(byte v) {
		switch (v) {
			case Tid_core:		return "core";
			case Tid_text:		return "text";
			case Tid_category:	return "category";
			case Tid_wikidata:	return "wikidata";
			case Tid_temp:		return "temp";
			case Tid_search:	return "search";
			default:			throw Err_.unhandled(v);
		}
	}
	public static Xodb_file load_(int id, byte tid, String url) {return new Xodb_file(id, tid).Url_rel_(url).Cmd_mode_(Db_cmd_mode.Ignore);}
	public static Xodb_file make_(int id, byte tid, String url) {return new Xodb_file(id, tid).Url_rel_(url).Cmd_mode_(Db_cmd_mode.Create);}
	public void Index_create(Gfo_usr_dlg usr_dlg, Db_idx_itm[] idxs) {
		int len = idxs.length;
		provider.Txn_mgr().Txn_end_all();	// commit any pending transactions
		for (int i = 0; i < len; i++) {
			provider.Txn_mgr().Txn_bgn_if_none();
			String index = idxs[i].Xto_sql();
			usr_dlg.Prog_many("", "", "creating index: ~{0} ~{1}", id, index);
			provider.Exec_sql(index);
			provider.Txn_mgr().Txn_end_all();
		}
	}
	public static final Db_idx_itm 
		  Indexes_page_title				= Db_idx_itm.sql_("CREATE UNIQUE INDEX IF NOT EXISTS page__title                   ON page (page_namespace, page_title, page_id, page_len, page_is_redirect);")	// PERF:page_id for general queries; PERF: page_len for search_suggest; PREF:page_is_redirect for oimg
		, Indexes_page_random				= Db_idx_itm.sql_("CREATE UNIQUE INDEX IF NOT EXISTS page__name_random             ON page (page_namespace, page_random_int);")
		, Indexes_categorylinks_main		= Db_idx_itm.sql_("CREATE        INDEX IF NOT EXISTS categorylinks__cl_main        ON categorylinks (cl_to_id, cl_type_id, cl_sortkey, cl_from);")
		, Indexes_categorylinks_from		= Db_idx_itm.sql_("CREATE        INDEX IF NOT EXISTS categorylinks__cl_from        ON categorylinks (cl_from);")
		, Indexes_wikidata_qids				= Db_idx_itm.sql_("CREATE        INDEX IF NOT EXISTS wdata_qids__src               ON wdata_qids (wq_src_wiki, wq_src_ns, wq_src_ttl);")
		, Indexes_wikidata_pids				= Db_idx_itm.sql_("CREATE        INDEX IF NOT EXISTS wdata_pids__src               ON wdata_pids (wp_src_lang, wp_src_ttl);")
		;
}

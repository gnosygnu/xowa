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
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.dbs.tbls.*;
public class Xodb_fsys_mgr {
	private final Io_url src_dir; private final Io_url trg_dir; private final String wiki_name;
	public Xodb_fsys_mgr(Io_url src_dir, Io_url trg_dir, String wiki_name) {this.src_dir = src_dir; this.trg_dir = trg_dir; this.wiki_name = wiki_name;}
	public Xodb_file[] Files_ary() {return files_ary;}
	public Db_conn Conn_core()	{return provider_core;} private Db_conn provider_core;
	public Db_conn Conn_page()	{return provider_page;} private Db_conn provider_page;
	public Db_conn Conn_ctg()	{return provider_ctg;}	public void Conn_ctg_(Xodb_file file) {provider_ctg = file.Conn();} private Db_conn provider_ctg;
	public Db_conn Conn_wdata() {return provider_wdata;}public void Conn_wdata_(Xodb_file file)	{provider_wdata = file.Conn();} private Db_conn provider_wdata;		
	public int Tid_text_idx() {return tid_text_idx;} public Xodb_fsys_mgr Tid_text_idx_(int v) {tid_text_idx = v; return this;} private int tid_text_idx = File_id_core;
	public long Tid_text_max() {return tid_text_max;} private long tid_text_max = Heap_max_infinite;
	public void Init_by_files(Db_conn p, Xodb_file[] v) {
		files_ary = v; files_ary_len = v.length;
		boolean category_provider_core_null = true;
		for (int i = 0; i < files_ary_len; i++) {
			Xodb_file file = files_ary[i];
			Io_url url = trg_dir.GenSubFil(file.Url_rel());	// relative name only
			file.Connect_(Db_url_.sqlite_(url)).Url_(url);
			switch (file.Tid()) {
				case Xodb_file_tid.Tid_core					: file.Conn_(p); Set_file_core(file); break;
				case Xodb_file_tid.Tid_category				: if (category_provider_core_null) {Conn_ctg_(file); category_provider_core_null = false;} break;
				case Xodb_file_tid.Tid_wikidata				: Conn_wdata_(file); break;
				case Xodb_file_tid.Tid_text					: Set_file_text(file); break;
			}
		}
	}
	public void Init_by_ns_map(Xow_ns_mgr ns_mgr, String ns_map_str, long text_max) {
		Set_file_core(Make(Xodb_file_tid.Tid_core));
		Xodb_ns_map_mgr ns_map = Xodb_ns_map_mgr.Parse(Bry_.new_ascii_(ns_map_str));
		Xodb_ns_map_itm[] ns_map_itms = ns_map.Itms();
		int ns_map_itms_len = ns_map_itms.length;
		for (int i = 0; i < ns_map_itms_len; i++) {
			Xodb_ns_map_itm itm = ns_map_itms[i];
			int[] ns_ids = itm.Ns_ids();
			int ns_ids_len = ns_ids.length;
			Xodb_file file = Make(Xodb_file_tid.Tid_text);
			for (int j = 0; j < ns_ids_len; j++) {
				int ns_id = ns_ids[j];
				Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id); if (ns == null) continue; // some dumps may not have ns; for example, pre-2013 dumps won't have Module (828)
				ns.Bldr_file_idx_(file.Id());
			}
		}
		if (text_max > 0)
			Set_file_text(Make(Xodb_file_tid.Tid_text).File_max_(text_max));		
	}
	private void Set_file_core(Xodb_file file)	{provider_core = provider_page = provider_ctg = provider_wdata = file.Conn();}
	private void Set_file_text(Xodb_file file)	{tid_text_idx = file.Id(); tid_text_max = file.File_max();}
	public Io_url Get_url(byte file_tid) {
		Xodb_file file = Get_tid_root(file_tid);
		return trg_dir.GenSubFil(file.Url_rel());
	}
	public Xodb_file Get_by_idx(int idx) {return files_ary[idx];}
	public Xodb_file Get_or_make(byte file_tid, int file_idx) {return file_idx < files_ary_len ? files_ary[file_idx] : Make(file_tid);}
	public Xodb_file Get_tid_root(byte file_tid) {
		for (int i = 0; i < files_ary_len; i++) {
			Xodb_file file = files_ary[i];
			if (file.Tid() == file_tid) return file;	// assume 1st found file is root
		}
		return null;
	}
	public void Index_create(Gfo_usr_dlg usr_dlg, byte[] tids, Db_idx_itm... idxs) {
		for (int i = 0; i < files_ary_len; i++) {
			Xodb_file file = files_ary[i];
			if (Byte_.In(file.Tid(), tids))
				Sqlite_engine_.Idx_create(usr_dlg, file.Conn(), Int_.Xto_str(file.Id()), idxs);
		}
	}
	public Xodb_file Make(byte file_tid) {
		int file_idx = files_ary_len;
		Io_url url = Create_sqlite3(src_dir, trg_dir, wiki_name, file_idx);
		Xodb_file rv = Xodb_file.make_(file_idx, file_tid, url.NameAndExt()).Connect_(Db_url_.sqlite_(url));
		rv.Url_(url);
		Xodb_xowa_cfg_tbl.Insert_str(rv.Conn(), Cfg_grp_db_meta, "type_name", Xodb_file_tid.Xto_key(file_tid));
		files_ary = (Xodb_file[])Array_.Resize(files_ary, files_ary_len + 1);
		files_ary[files_ary_len++] = rv;
		return rv;
	}	private Xodb_file[] files_ary = new Xodb_file[0]; private int files_ary_len = 0;
	public void Rls() {
		for (int i = 0; i < files_ary_len; i++)
			files_ary[i].Rls();
	}
	private static Io_url Create_sqlite3(Io_url src_dir, Io_url trg_dir, String wiki_name, int file_idx) {
		Io_url src_fil = src_dir.GenSubFil("xowa.sqlite3");	 // /bin/any/sql/xowa/xowa.sqlite3
		Io_url trg_fil = trg_dir.GenSubFil_ary(wiki_name, ".", Int_.Xto_str_pad_bgn(file_idx, 3), ".sqlite3"); // /wiki/en.wikipedia.org/en.wikipedia.org.000.sqlite3
		Io_mgr._.CopyFil(src_fil, trg_fil, true);
		return trg_fil;
	}
	private static final int File_id_core = 0;
	public static final int Heap_max_infinite = 0;
	public static final String Cfg_grp_db_meta = "db.meta";
}

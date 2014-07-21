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
public class Xodb_fsys_mgr {
	public Xodb_fsys_mgr Ctor(Io_url src_dir, Io_url trg_dir, String trg_name) {
		this.src_dir = src_dir;
		this.trg_dir = trg_dir;
		this.trg_name = trg_name;
		return this;
	}	private Io_url src_dir; private String trg_name;
	public Db_provider Core_provider() {return core_provider;} private Db_provider core_provider;
	public Db_provider Page_provider() {return page_provider;} private Db_provider page_provider;
	public Db_provider Category_provider() {return category_provider;} private Db_provider category_provider;
	public Db_provider Wdata_provider() {return wikidata_provider;} private Db_provider wikidata_provider;
	public Io_url Trg_dir() {return trg_dir;} private Io_url trg_dir; 
	public int Tid_text_idx() {return tid_text_idx;} public Xodb_fsys_mgr Tid_text_idx_(int v) {tid_text_idx = v; return this;} private int tid_text_idx = File_id_core;
	public long Tid_text_max() {return tid_text_max;} long tid_text_max = Heap_max_infinite;
	static final int File_id_core = 0;
	public Xodb_file[] Ary() {return files_ary;}
	public void Init_by_files(Db_provider p, Xodb_file[] v) {
		files_ary = v; files_ary_len = v.length;
		boolean category_provider_core_null = true;
		for (int i = 0; i < files_ary_len; i++) {
			Xodb_file file = files_ary[i];
			Io_url url = trg_dir.GenSubFil(file.Url_rel());	// relative name only
			file.Connect_(Db_connect_.sqlite_(url)).Url_(url);
			switch (file.Tid()) {
				case Xodb_file_tid_.Tid_core					: file.Provider_(p); Init_by_tid_core(file); break;
				case Xodb_file_tid_.Tid_category				: if (category_provider_core_null) {Init_by_tid_category(file); category_provider_core_null = false;}break;
				case Xodb_file_tid_.Tid_wikidata				: Init_by_tid_wikidata(file); break;
				case Xodb_file_tid_.Tid_text					: Init_by_tid_text(file); break;
			}
		}
	}
	public void Init_make(Xow_ns_mgr ns_mgr, String ns_map_str, long text_max) {
		Init_by_tid_core(Make(Xodb_file_tid_.Tid_core));
		Init_by_ns_map(ns_mgr, Xodb_ns_map_mgr.Parse(Bry_.new_ascii_(ns_map_str)));
		if (text_max > 0)
			Init_by_tid_text(Init_make_file(Xodb_file_tid_.Tid_text, text_max));
	}
	public Xodb_file Init_make_file(byte tid, long max) {
		if (max == Max_core_db) return files_ary[File_id_core];
		return Make(tid).File_max_(max);
	}
	private void Init_by_tid_core(Xodb_file file)				{core_provider = page_provider = category_provider = wikidata_provider = file.Provider();}
	public void Init_by_tid_category(Xodb_file file)	{category_provider = file.Provider();}
	public void Init_by_tid_wikidata(Xodb_file file)	{wikidata_provider = file.Provider();}
	private void Init_by_ns_map(Xow_ns_mgr ns_mgr, Xodb_ns_map_mgr ns_map) {
		Xodb_ns_map_itm[] ns_map_itms = ns_map.Itms();
		int ns_map_itms_len = ns_map_itms.length;
		for (int i = 0; i < ns_map_itms_len; i++) {
			Xodb_ns_map_itm itm = ns_map_itms[i];
			int[] ns_ids = itm.Ns_ids();
			int ns_ids_len = ns_ids.length;
			Xodb_file file = Make(Xodb_file_tid_.Tid_text);
			for (int j = 0; j < ns_ids_len; j++) {
				int ns_id = ns_ids[j];
				Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id); if (ns == null) continue; // some dumps may not have ns; for example, pre-2013 dumps won't have Module (828)
				ns.Bldr_file_idx_(file.Id());
			}
		}
	}
	private void Init_by_tid_text(Xodb_file file) {
		tid_text_idx = file.Id();
		tid_text_max = file.File_max();
	}
	public Xodb_file Get_by_db_idx(int db_idx) {return files_ary[db_idx];}
	public Xodb_file Get_or_make(byte file_tid, int file_idx) {return file_idx < files_ary_len ? files_ary[file_idx] : Make(file_tid);}
	public Io_url Get_url(byte file_tid) {
		Xodb_file file = Get_tid_root(file_tid);
		return trg_dir.GenSubFil(file.Url_rel());
	}
	public Xodb_file Get_tid_root(byte file_tid) {
		for (int i = 0; i < files_ary_len; i++) {
			Xodb_file file = files_ary[i];
			if (file.Tid() == file_tid) return file;	// assume 1st file is root
		}
		return null;
	}
	public void Index_create(Gfo_usr_dlg usr_dlg, byte[] tids, Db_idx_itm... idxs) {
		for (int i = 0; i < files_ary_len; i++) {
			Xodb_file file = files_ary[i];
			if (Byte_.In(file.Tid(), tids))
				Xodb_fsdb_mgr_.Index_create(usr_dlg, file, idxs);
		}
	}
	public void Overwrite(int file_idx) {Create_sqlite3(src_dir, trg_dir, trg_name, file_idx);}
	public Xodb_file Get_or_make(String name) {
		Io_url url = trg_dir.GenSubFil(name + ".sqlite3");
		if (!Io_mgr._.ExistsFil(url))
			Io_mgr._.CopyFil(src_dir.GenSubFil("xowa.sqlite3"), url, true);
		return Xodb_file.make_(-1, Xodb_file_tid_.Tid_temp, url.NameAndExt()).Connect_(Db_connect_.sqlite_(url));
	}
	public Xodb_file Make(byte file_tid) {
		int file_idx = files_ary_len;
		Io_url url = Create_sqlite3(src_dir, trg_dir, trg_name, file_idx);
		Xodb_file rv = Xodb_file.make_(file_idx, file_tid, url.NameAndExt()).Connect_(Db_connect_.sqlite_(url));
		gplx.xowa.dbs.tbls.Xodb_xowa_cfg_tbl.Insert_str(rv.Provider(), "db.meta", "type_name", Xodb_file_tid_.Xto_key(file_tid));
		files_ary = (Xodb_file[])Array_.Resize(files_ary, files_ary_len + 1);
		files_ary[files_ary_len] = rv;
		++files_ary_len;
		return rv;
	}	private Xodb_file[] files_ary = new Xodb_file[0]; int files_ary_len = 0;
	public void Rls() {
		for (int i = 0; i < files_ary_len; i++)
			files_ary[i].Rls();
	}
	private static Io_url Create_sqlite3(Io_url src_dir, Io_url trg_dir, String trg_name, int file_idx) {
		Io_url src_fil = src_dir.GenSubFil("xowa.sqlite3");	 // /bin/any/sql/xowa/xowa.sqlite3
		Io_url trg_fil = trg_dir.GenSubFil_ary(trg_name, ".", Int_.XtoStr_PadBgn(file_idx, 3), ".sqlite3"); // /wiki/en.wikipedia.org/en.wikipedia.org.000.sqlite3
		Io_mgr._.CopyFil(src_fil, trg_fil, true);
		return trg_fil;
	}
	public static final int Heap_max_infinite = 0;
	public static final long Max_core_db = -1;
}
class Xodb_fsdb_mgr_ {
	public static void Index_create(Gfo_usr_dlg usr_dlg, Xodb_file file, Db_idx_itm[] idxs) {
		Db_provider provider = file.Provider();
		provider.Txn_mgr().Txn_end_all();	// commit any pending transactions
		int len = idxs.length;
		for (int i = 0; i < len; i++) {
			provider.Txn_mgr().Txn_bgn_if_none();
			String index = idxs[i].Xto_sql();
			usr_dlg.Prog_many("", "", "creating index: ~{0} ~{1}", file.Id(), index);
			provider.Exec_sql(index);
			provider.Txn_mgr().Txn_end_all();
		}
	}
}

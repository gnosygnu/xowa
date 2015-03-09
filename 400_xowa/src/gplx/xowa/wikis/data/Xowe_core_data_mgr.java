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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.dbs.engines.sqlite.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*;
public class Xowe_core_data_mgr implements Xow_core_data_mgr {
	private Xowd_db_file[] dbs__ary = new Xowd_db_file[0]; private int dbs__ary_len = 0;
	private final Io_url src_dir; private final Io_url trg_dir; private final String domain_str;
	public Xowe_core_data_mgr(Io_url src_dir, Io_url trg_dir, String domain_str) {this.src_dir = src_dir; this.trg_dir = trg_dir; this.domain_str = domain_str;}
	public Xow_core_data_map		Map()		{return map;}		private final Xow_core_data_map	map			= new Xow_core_data_map();
	public boolean						Cfg__schema_is_1()	{return Bool_.Y;}
	public int						Cfg__db_id()		{return 1;}
	public byte						Cfg__hdump_zip_tid() {return Tbl__cfg().Select_as_byte_or("xowa.schema.dbs.html", "zip_tid", gplx.ios.Io_stream_.Tid_bzip2);}
	public Db_cfg_tbl				Tbl__cfg()	{return tbl__cfg;}	private final Db_cfg_tbl			tbl__cfg	= new Db_cfg_tbl();
	public Xodb_xowa_db_tbl			Tbl__db()	{return tbl__db;}	private final Xodb_xowa_db_tbl	tbl__db		= new Xodb_xowa_db_tbl();
	public Xowd_pg_regy_tbl			Tbl__pg()	{return tbl__pg;}	private final Xowd_pg_regy_tbl	tbl__pg		= new Xowd_pg_regy_tbl();
	public int						Dbs__len()	{return dbs__ary.length;}
	public Xowd_db_file				Dbs__get_db_core() {return dbs__ary[0];}
	public Xowd_db_file				Dbs__get_at(int i) {return dbs__ary[i];}
	public Xowd_db_file				Dbs__get_by_tid_1st(byte tid) {
		if (tid == Xowd_db_file_.Tid_core && dbs__ary_len != 0) return dbs__ary[0];
		for (int i = 0; i < dbs__ary_len; i++) {
			Xowd_db_file file = dbs__ary[i];
			if (file.Tid() == tid) return file;
		}
		return Xowd_db_file.Null;
	}
	public Xowd_db_file				Dbs__get_by_tid_nth_or_new(byte tid) {
		Xowd_db_file rv = Xowd_db_file.Null;
		for (int i = 0; i < dbs__ary_len; i++) {
			Xowd_db_file file = dbs__ary[i];
			if (file.Tid() == tid) rv = file;
		}
		if (rv == Xowd_db_file.Null) {
			Xowd_db_init_db_wkr wkr = Xowd_db_init_db_mgr.I.Get(tid);
			rv = wkr.Db_make(this);
		}
		return rv;
	}
	public void						Dbs__save() {tbl__db.Commit_all(this);}
	public Xowd_db_file				Dbs__add_new(byte file_tid) {
		int file_idx = dbs__ary_len;
		Io_url url = Create_sqlite3(src_dir, trg_dir, domain_str, file_idx);
		Xowd_db_file rv = Xowd_db_file.make_(file_idx, file_tid, url.NameAndExt()).Connect_(Db_url_.sqlite_(url));
		rv.Url_(url);
		Db_conn core_conn = rv.Conn(); boolean created = Bool_.N; boolean schema_is_1 = Bool_.Y; int db_id = 1;
		if (file_idx == 0) {
			tbl__db.Conn_(core_conn, created, schema_is_1);
			tbl__pg.Conn_(core_conn, created, schema_is_1, db_id, Bool_.N);
		}
		tbl__cfg.Conn_(core_conn, created, schema_is_1, "xowa_cfg", "wiki_cfg_regy");
		Xodb_xowa_cfg_tbl.Insert_str(rv.Conn(), Cfg_grp_db_meta, "type_name", Xowd_db_file_.Xto_key(file_tid));
		dbs__ary = (Xowd_db_file[])Array_.Resize(dbs__ary, dbs__ary_len + 1);
		dbs__ary[dbs__ary_len++] = rv;
		return rv;
	}
	public void Core_conn_(Db_conn new_conn, boolean created, boolean schema_is_1, int db_id, boolean hdump_enabled) {
		tbl__cfg.Conn_(new_conn, created, schema_is_1, "xowa_cfg", "wiki_cfg_regy");
		tbl__db.Conn_(new_conn, created, schema_is_1);
		tbl__pg.Conn_(new_conn, created, schema_is_1, db_id, hdump_enabled);
	}
	public Db_conn Conn_core()	{return provider_core;} private Db_conn provider_core;
	public Db_conn Conn_page()	{return provider_page;} private Db_conn provider_page;
	public Db_conn Conn_ctg()	{return provider_ctg;}	public void Conn_ctg_(Xowd_db_file file) {provider_ctg = file.Conn();} private Db_conn provider_ctg;
	public Db_conn Conn_wdata() {return provider_wdata;}public void Conn_wdata_(Xowd_db_file file)	{provider_wdata = file.Conn();} private Db_conn provider_wdata;		
	public int Tid_text_idx() {return tid_text_idx;} public Xowe_core_data_mgr Tid_text_idx_(int v) {tid_text_idx = v; return this;} private int tid_text_idx = File_id_core;
	public long Tid_text_max() {return tid_text_max;} private long tid_text_max = Heap_max_infinite;
	public void Init_by_files(Db_conn p, Xowd_db_file[] v) {
		dbs__ary = v; dbs__ary_len = v.length;
		boolean category_provider_core_null = true;
		for (int i = 0; i < dbs__ary_len; i++) {
			Xowd_db_file file = dbs__ary[i];
			Io_url url = trg_dir.GenSubFil(file.Url_rel());	// relative name only
			file.Connect_(Db_url_.sqlite_(url)).Url_(url);
			switch (file.Tid()) {
				case Xowd_db_file_.Tid_core					: file.Conn_(p); Set_file_core(file); break;
				case Xowd_db_file_.Tid_category				: if (category_provider_core_null) {Conn_ctg_(file); category_provider_core_null = false;} break;
				case Xowd_db_file_.Tid_wikidata				: Conn_wdata_(file); break;
				case Xowd_db_file_.Tid_text					: Set_file_text(file); break;
			}
		}
	}
	public void Init_by_ns_map(Xow_ns_mgr ns_mgr, String ns_map_str, long text_max) {
		Set_file_core(Dbs__add_new(Xowd_db_file_.Tid_core));
		Xodb_ns_map_mgr ns_map = Xodb_ns_map_mgr.Parse(Bry_.new_ascii_(ns_map_str));
		Xodb_ns_map_itm[] ns_map_itms = ns_map.Itms();
		int ns_map_itms_len = ns_map_itms.length;
		for (int i = 0; i < ns_map_itms_len; i++) {
			Xodb_ns_map_itm itm = ns_map_itms[i];
			int[] ns_ids = itm.Ns_ids();
			int ns_ids_len = ns_ids.length;
			Xowd_db_file file = Dbs__add_new(Xowd_db_file_.Tid_text);
			for (int j = 0; j < ns_ids_len; j++) {
				int ns_id = ns_ids[j];
				Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id); if (ns == null) continue; // some dumps may not have ns; for example, pre-2013 dumps won't have Module (828)
				ns.Bldr_file_idx_(file.Id());
			}
		}
		if (text_max > 0)
			Set_file_text(Dbs__add_new(Xowd_db_file_.Tid_text).File_max_(text_max));		
	}
	private void Set_file_core(Xowd_db_file file)	{provider_core = provider_page = provider_ctg = provider_wdata = file.Conn();}
	private void Set_file_text(Xowd_db_file file)	{tid_text_idx = file.Id(); tid_text_max = file.File_max();}
	public Io_url Get_url(byte file_tid) {
		Xowd_db_file file = Dbs__get_by_tid_1st(file_tid);
		return trg_dir.GenSubFil(file.Url_rel());
	}
	public void Index_create(Gfo_usr_dlg usr_dlg, byte[] tids, Db_idx_itm... idxs) {
		for (int i = 0; i < dbs__ary_len; i++) {
			Xowd_db_file file = dbs__ary[i];
			if (Byte_.In(file.Tid(), tids))
				Sqlite_engine_.Idx_create(usr_dlg, file.Conn(), Int_.Xto_str(file.Id()), idxs);
		}
	}
	public void Rls() {
		for (int i = 0; i < dbs__ary_len; i++)
			dbs__ary[i].Rls();
	}
	private static Io_url Create_sqlite3(Io_url src_dir, Io_url trg_dir, String domain_str, int file_idx) {
		Io_url src_fil = src_dir.GenSubFil("xowa.sqlite3");	 // /bin/any/sql/xowa/xowa.sqlite3
		Io_url trg_fil = trg_dir.GenSubFil_ary(domain_str, ".", Int_.Xto_str_pad_bgn(file_idx, 3), ".sqlite3"); // /wiki/en.wikipedia.org/en.wikipedia.org.000.sqlite3
		Io_mgr._.CopyFil(src_fil, trg_fil, true);
		return trg_fil;
	}
	private static final int File_id_core = 0;
	public static final int Heap_max_infinite = 0;
	public static final String Cfg_grp_db_meta = "db.meta";
}

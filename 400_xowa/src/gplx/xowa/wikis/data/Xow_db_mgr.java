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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.core.lists.hashs.*;
import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.bldrs.infos.*; import gplx.xowa.wikis.metas.*;	
public class Xow_db_mgr {
	private final    Io_url wiki_root_dir;
	private final    String domain_str; // needed for generating new files; EX: en.wikipedia.org-text.ns.001.xowa
	private final    Ordered_hash hash_by_id = Ordered_hash_.New(); private final    Xow_db_file_hash hash_by_tids = new Xow_db_file_hash();
	private int db_id_next = 0;
	public Xow_db_mgr(Io_url wiki_root_dir, String domain_str) {
		this.wiki_root_dir = wiki_root_dir;
		this.domain_str = domain_str;
	}
	public Xowd_core_db_props		Props()			{return props;} private Xowd_core_db_props props = Xowd_core_db_props.Test;
	public Db_cfg_tbl				Tbl__cfg()		{return db__core.Tbl__cfg();}
	public Xowd_page_tbl			Tbl__page()		{return db__core.Tbl__page();}
	public Xow_db_file				Db__core()		{return db__core;}		private Xow_db_file db__core;
	public Xow_db_file				Db__text()		{return db__text;}		private Xow_db_file db__text;
	public Xow_db_file				Db__html()		{return db__html;}		private Xow_db_file db__html;
	public Xow_db_file				Db__cat_core()	{return db__cat_core;}	private Xow_db_file db__cat_core;
	public Xow_db_file				Db__wbase()		{return db__wbase;}		private Xow_db_file db__wbase;  public void Db__wbase_(Xow_db_file v) {db__wbase = v;}
	public void Init_by_load(Io_url core_url) {
		// clear lists
		hash_by_id.Clear();
		hash_by_tids.Clear();

		// create core_conn / core_db
		Db_conn core_conn = Db_conn_bldr.Instance.Get(core_url);
		props = Xowd_core_db_props.Cfg_load(core_conn);	// load props to get layout_text
		Dbs__set_by_tid(Xow_db_file.Load(props, Xow_db_file_.Uid__core, Xow_db_file__core_.Core_db_tid(props.Layout_text()), core_url, Xob_info_file.Ns_ids_empty, Xob_info_file.Part_id_1st, Guid_adp_.Empty));

		// load dbs from "xowa_db" tbl
		Xow_db_file[] ary = db__core.Tbl__db().Select_all(props, core_url.OwnerDir());
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xow_db_file db = ary[i];
			Dbs__set_by_tid(db);
			Dbs__add(db);
		}
	}
	public void Init_by_make(Xowd_core_db_props props, Xob_info_session info_session) {
		this.props = props;

		// save data
		Xow_db_file core_db = Xow_db_file__core_.Make_core_db(props, info_session, wiki_root_dir, domain_str);
		Dbs__set_by_tid(core_db);
		Dbs__add_and_save(core_db);
		props.Cfg_save(db__core.Tbl__cfg());	// NOTE: must save cfg now, especially zip_tid; latter will be reloaded after import is done;
	}
	public void Rls() {
		int len = hash_by_id.Len();
		for (int i = 0; i < len; i++) {
			Xow_db_file db_file = (Xow_db_file)hash_by_id.Get_at(i);
			db_file.Rls();
		}
	}

	public int						Dbs__len()						{return hash_by_id.Len();}		
	public Xow_db_file				Dbs__get_at(int i)				{return (Xow_db_file)hash_by_id.Get_at(i);}
	public Xow_db_file				Dbs__get_by_id_or_fail(int id)	{return (Xow_db_file)hash_by_id.Get_by_or_fail(id);}
	public Xow_db_file				Dbs__get_by_id_or_null(int id)	{return (Xow_db_file)hash_by_id.Get_by(id);}
	public Xow_db_file				Dbs__get_by_tid_or_core(byte... tids_ary) {Xow_db_file rv = Dbs__get_by_tid_or_null(tids_ary); return rv == null ? db__core : rv;}
	public Xow_db_file				Dbs__get_by_tid_or_null(byte... tids_ary) {
		int tids_len = tids_ary.length;
		for (int i = 0; i < tids_len; ++i) {
			byte tid = tids_ary[i];
			Ordered_hash tid_dbs = hash_by_tids.Get_by_tid_or_null(tid); if (tid_dbs == null) continue;
			int tid_dbs_len = tid_dbs.Len();
			if (tid_dbs_len != 1) {	// NOTE: occurs when multiple search imports fail; DATE:2016-04-04
				Xoa_app_.Usr_dlg().Warn_many("", "", "expecting only 1 db for tid; tid=~{0} len=~{1} db_api=~{2}", tid, tid_dbs.Len(), db__core.Conn().Conn_info().Db_api());
			}
			return (Xow_db_file)tid_dbs.Get_at(tid_dbs_len - 1);	// get last idx;
		}
		return null;
	}
	public Xow_db_file				Dbs__assert_by_tid(byte tid) {
		Xow_db_file rv = Dbs__get_by_tid_or_null(tid);
		if (rv == null) {
			rv = Dbs__make_by_tid(tid);
		}
		return rv;
	}
	public Ordered_hash				Dbs__get_hash_by_tid(int tid) {return hash_by_tids.Get_by_tid_or_null((byte)tid);}
	public Xow_db_file				Dbs__make_by_tid(byte tid) {
		int tid_idx = Get_tid_idx(hash_by_tids, tid);
		return Dbs__make_by_tid(tid, Xob_info_file.Ns_ids_empty, tid_idx, Get_tid_name(tid_idx, tid));
	}
	public Xow_db_file				Dbs__make_by_tid(byte tid, String ns_ids, int part_id, String file_name_suffix) {
		return Dbs__make_by_id(db_id_next++, tid, ns_ids, part_id, file_name_suffix);
	}
	public Xow_db_file Dbs__make_by_id(int id, byte tid, String ns_ids, int part_id, String file_name_suffix) {
		Io_url url = wiki_root_dir.GenSubFil(domain_str + file_name_suffix);
		Xow_db_file rv = Xow_db_file.Make(db__core.Info_session(), props, id, tid, url, ns_ids, part_id, db__core.Url().NameAndExt(), Db_conn_bldr.Instance.New(url));
		Dbs__add_and_save(rv);
		Dbs__set_by_tid(rv);
		return rv;
	}
	public Xow_db_file				Dbs__remake_by_tid(byte tid) {
		Dbs__delete_by_tid(tid);
		return Dbs__make_by_tid(tid);
	}
	public void						Dbs__delete_by_tid(byte... tids) {
		int len = hash_by_id.Len();
		for (int i = 0; i < len; ++i) {
			Xow_db_file db = (Xow_db_file)hash_by_id.Get_at(i);
			if (!Byte_.In(db.Tid(), tids)) continue;
			db.Rls();
			Io_mgr.Instance.DeleteFil_args(db.Url()).MissingFails_off().Exec();
			db.Cmd_mode_(Db_cmd_mode.Tid_delete);
		}
		db__core.Tbl__db().Commit_all(this);

		// call init again to regen list of dbs
		this.Init_by_load(db__core.Url());
	}
	public Xow_db_file Dbs__get_for_create(byte tid, int ns_id) {
		Xow_db_file[] ary = Dbs__get_ary(tid, ns_id);
		if (ary.length == 0) throw Err_.new_wo_type("no dbs exist; wiki=~{0} type=~{1}", domain_str, tid);
		return ary[ary.length - 1];
	}
	public Xow_db_file[] Dbs__get_ary(byte tid, int ns_id) {
		List_adp rv = List_adp_.New();

		// loop all dbs
		int len = this.Dbs__len();
		for (int i = 0; i < len; i++) {
			boolean add = false;
			Xow_db_file db = this.Dbs__get_at(i);
			switch (tid) {
				// cat_link requested
				case Xow_db_file_.Tid__cat_link:
					switch (db.Tid()) {
						case Xow_db_file_.Tid__core:
							add = props.Layout_text().Tid_is_all_or_few(); // cat_link will be in "core" if "all" or "few" (-text, -html, -file)
							break;
						case Xow_db_file_.Tid__cat_link:
							add = true;
							break;
					}
					break;
				// text requested
				case Xow_db_file_.Tid__text:
					switch (db.Tid()) {
						case Xow_db_file_.Tid__core:
							add = props.Layout_text().Tid_is_all(); // text will be in "core" if "all"
							break;
						case Xow_db_file_.Tid__text_solo:  // EX: "en.wikipedia.org-text.xowa"
							add = true;                    // text will be in db if solo; 
							break;
						case Xow_db_file_.Tid__text:       // EX: "en.wikipedia.org-text-ns.000.xowa"
							int[] db_ns_ids = Int_.Ary_parse(db.Ns_ids(), "|"); // need to handle both "0" and "0|4"
							for (int db_ns_id : db_ns_ids) {								
								if (db_ns_id == ns_id) {
									add = true;				// text will be in db if ns matches; EX: en.wikipedia.org-text-ns.014.xowa
									break;
								}
							}
							break;
					}
					break;
			}
			if (add)
				rv.Add(db);
		}

		return (Xow_db_file[])rv.To_ary_and_clear(Xow_db_file.class);
	}
	public void Create_page(Xowd_page_tbl core_tbl, Xowd_text_tbl text_tbl, int page_id, int ns_id, byte[] ttl_wo_ns, boolean redirect, DateAdp modified_on, byte[] text_zip_data, int text_raw_len, int random_int, int text_db_id, int html_db_id) {
		core_tbl.Insert_cmd_by_batch(page_id, ns_id, ttl_wo_ns, redirect, modified_on, text_raw_len, random_int, text_db_id, html_db_id, -1);
		text_tbl.Insert_cmd_by_batch(page_id, text_zip_data);
	}
	private void Dbs__set_by_tid(Xow_db_file db) {
		switch (db.Tid()) {
			case Xow_db_file_.Tid__wiki_solo:
			case Xow_db_file_.Tid__text_solo:
			case Xow_db_file_.Tid__core					: {db__core = db; if (props.Layout_text().Tid_is_all_or_few()) db__cat_core = db__text = db; break;}
			case Xow_db_file_.Tid__text					: {db__text = db; break;}
			case Xow_db_file_.Tid__html_data			: {db__html = db; break;}
			case Xow_db_file_.Tid__wbase				: {if (db__wbase == null)		db__wbase = db; break;}
			case Xow_db_file_.Tid__cat_core				:
			case Xow_db_file_.Tid__cat					: {if (db__cat_core == null)	db__cat_core = db; break;}
		}
	}
	private void Dbs__add(Xow_db_file db_file) {
		int db_id = db_file.Id();
		hash_by_id.Add(db_id, db_file);
		hash_by_tids.Add_or_new(db_file);
		if (db_id >= db_id_next)	// always set db_id_next to largest value in given set of dbs; EX: dbs=[0,1,10]; db_id_next should be 11
			db_id_next = db_id + 1;			
	}
	private void Dbs__add_and_save(Xow_db_file db_file) {
		Dbs__add(db_file);

		db__core.Tbl__db().Commit_all(this);
		db_file.Info_file().Save(db_file.Tbl__cfg());
		db_file.Info_session().Save(db_file.Tbl__cfg());
	}
	private int Get_tid_idx(Xow_db_file_hash hash, byte tid) {return hash.Count_of_tid(tid) + Int_.Base1;}
	private static String Get_tid_name(int tid_idx, byte tid) {
		String tid_name = Xow_db_file_.To_key(tid);
		String tid_idx_str = "";
		switch (tid) {
			case Xow_db_file_.Tid__cat_core		: break;
			case Xow_db_file_.Tid__cat_link		: tid_idx_str = "-db." + Int_.To_str_pad_bgn_zero(tid_idx, 3); break;
			default									: tid_idx_str = tid_idx == 1 ? "" : "-db." + Int_.To_str_pad_bgn_zero(tid_idx, 3); break;
		}
		return String_.Format("-{0}{1}.xowa", tid_name, tid_idx_str);	// EX: en.wikipedia.org-text-001.sqlite3
	}

	// helper method for wikis to (a) init db_mgr; (b) load wiki.props; should probably be moved to more generic "wiki.Init_by_db()"
	public static void Init_by_load(Xow_wiki wiki, Io_url core_url) {
		wiki.Data__core_mgr().Init_by_load(core_url);
		wiki.Props().Init_by_load(wiki.App(), wiki.Data__core_mgr().Tbl__cfg());	// load Main_page
	}
}

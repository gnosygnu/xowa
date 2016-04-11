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
import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.bldrs.infos.*;	
public class Xowd_db_mgr {
	private Xowd_db_file[] dbs__ary = new Xowd_db_file[0]; private int dbs__ary_len = 0; private final    Xowd_db_file_hash db_file_hash = new Xowd_db_file_hash();
	private final    Hash_adp id_hash = Hash_adp_.new_(); private final    gplx.core.primitives.Int_obj_ref id_hash_ref = gplx.core.primitives.Int_obj_ref.neg1_();
	private final    Xow_wiki wiki; private final    Io_url wiki_root_dir; private final    Xow_domain_itm domain_itm;
	public Xowd_db_mgr(Xow_wiki wiki, Io_url wiki_root_dir, Xow_domain_itm domain_itm) {this.wiki = wiki; this.wiki_root_dir = wiki_root_dir; this.domain_itm = domain_itm;}
	public Xowd_core_db_props		Props()			{return props;} private Xowd_core_db_props props = Xowd_core_db_props.Test;
	public Db_cfg_tbl				Tbl__cfg()		{return db__core.Tbl__cfg();}
	public Xowd_page_tbl			Tbl__page()		{return db__core.Tbl__page();}
	public Xowd_db_file				Db__core()		{return db__core;}		private Xowd_db_file db__core;
	public Xowd_db_file				Db__text()		{return db__text;}		private Xowd_db_file db__text;
	public Xowd_db_file				Db__html()		{return db__html;}		private Xowd_db_file db__html;
	public Xowd_db_file				Db__cat_core()	{return db__cat_core;}	private Xowd_db_file db__cat_core;
	public Xowd_db_file				Db__wbase()		{return db__wbase;}		private Xowd_db_file db__wbase;
	public int						Dbs__len()		{return dbs__ary.length;}
	public void						Db__wbase_(Xowd_db_file v) {db__wbase = v;}
	public Ordered_hash				Dbs__get_hash_by_tid(int tid) {return db_file_hash.Get_by_tid_or_null((byte)tid);}
	public Xowd_db_file				Dbs__get_at(int i) {return dbs__ary[i];}
	public Xowd_db_file				Dbs__get_by_id(int id) {return (Xowd_db_file)id_hash.Get_by_or_fail(id_hash_ref.Val_(id));}
	public Xowd_db_file				Dbs__get_by_tid_or_core(byte... tids_ary) {Xowd_db_file rv = Dbs__get_by_tid_or_null(tids_ary); return rv == null ? db__core : rv;}
	public Xowd_db_file				Dbs__get_by_tid_or_null(byte... tids_ary) {
		int tids_len = tids_ary.length;
		for (int i = 0; i < tids_len; ++i) {
			byte tid = tids_ary[i];
			Ordered_hash tid_dbs = db_file_hash.Get_by_tid_or_null(tid); if (tid_dbs == null) continue;
			int tid_dbs_len = tid_dbs.Len();
			if (tid_dbs_len != 1) {	// NOTE: occurs when multiple search imports fail; DATE:2016-04-04
				Xoa_app_.Usr_dlg().Warn_many("", "", "expecting only 1 db for tid; tid=~{0} len=~{1} db_api=~{2}", tid, tid_dbs.Len(), db__core.Conn().Conn_info().Db_api());
			}
			return (Xowd_db_file)tid_dbs.Get_at(tid_dbs_len - 1);	// get last idx;
		}
		return null;
	}
	public Xowd_db_file				Dbs__make_by_tid(byte tid) {
		int tid_idx = Get_tid_idx(db_file_hash, tid);
		return Dbs__make_by_tid(tid, Xob_info_file.Ns_ids_empty, tid_idx, Get_tid_name(db_file_hash, tid_idx, tid));
	}
	public Xowd_db_file				Dbs__make_by_tid(byte tid, String ns_ids, int part_id, String file_name_suffix) {
		Io_url url = wiki_root_dir.GenSubFil(domain_itm.Domain_str() + file_name_suffix);
		int next_id = Dbs__get_at(dbs__ary_len - 1).Id() + 1;
		Xowd_db_file rv = Xowd_db_file.make_(db__core.Info_session(), props, next_id, tid, url, ns_ids, part_id, db__core.Url().NameAndExt(), Db_conn_bldr.Instance.New(url));
		Dbs__add_and_save(rv);
		Dbs__set_by_tid(rv);
		return rv;
	}
	public void						Dbs__delete_by_tid(byte... tids) {
		int len = dbs__ary_len;
		for (int i = 0; i < len; ++i) {
			Xowd_db_file db = dbs__ary[i];
			if (!Byte_.In(db.Tid(), tids)) continue;
			db.Rls();
			Io_mgr.Instance.DeleteFil_args(db.Url()).MissingFails_off().Exec();
			db.Cmd_mode_(Db_cmd_mode.Tid_delete);
		}
		db__core.Tbl__db().Commit_all(this);
		this.Init_by_load(db__core.Url());
	}
	public void Init_by_load(Io_url core_url) {
		db_file_hash.Clear();
		id_hash.Clear();
		Db_conn core_conn = Db_conn_bldr.Instance.Get(core_url);
		props = Xowd_core_db_props.Cfg_load(core_url, core_conn);
		Dbs__set_by_tid(Xowd_db_file.load_(props, Xowd_db_file_.Id_core, Core_db_tid(props.Layout_text()), core_url, Xob_info_file.Ns_ids_empty, Xob_info_file.Part_id_1st, Guid_adp_.Empty));
		dbs__ary = db__core.Tbl__db().Select_all(props, core_url.OwnerDir());
		dbs__ary_len = dbs__ary.length;
		for (int i = 0; i < dbs__ary_len; i++) {
			Xowd_db_file db = dbs__ary[i];
			Dbs__set_by_tid(db);
			db_file_hash.Add_or_new(db);
			id_hash.Add(gplx.core.primitives.Int_obj_ref.new_(db.Id()), db);
		}
		wiki.Props().Init_by_load(wiki.App(), Tbl__cfg());
	}
	public void Init_by_make(Xowd_core_db_props props, Xob_info_session info_session) {
		this.props = props;
		String core_file_name = Core_file_name(props.Layout_text(), domain_itm.Domain_str());
		byte core_db_tid = Core_db_tid(props.Layout_text());
		Io_url core_db_url = wiki_root_dir.GenSubFil(core_file_name);
		Db_conn conn = Db_conn_bldr.Instance.New(core_db_url);
		conn.Txn_bgn("make__core__tbls");
		Dbs__set_by_tid(Xowd_db_file.make_(info_session, props, Xowd_db_file_.Id_core, core_db_tid, core_db_url, Xob_info_file.Ns_ids_empty, Xob_info_file.Part_id_1st, core_file_name, conn));
		db__core.Tbl__db().Create_tbl();
		db__core.Tbl__ns().Create_tbl();
		db__core.Tbl__site_stats().Create_tbl();
		db__core.Tbl__page().Create_tbl();
		if (props.Layout_text().Tid_is_all_or_few()) {	// create in advance else will fail for v2; import wiki -> wiki loads and tries to load categories; v2 category processes and builds tbl; DATE:2015-03-22
			db__core.Tbl__cat_core().Create_tbl();
			db__core.Tbl__cat_link().Create_tbl();
		}
		Dbs__add_and_save(db__core);
		props.Cfg_save(db__core.Tbl__cfg());	// NOTE: must save cfg now, especially zip_tid; latter will be reloaded after import is done;
		conn.Txn_end();
	}
	public void Create_page(Xowd_page_tbl core_tbl, Xowd_text_tbl text_tbl, int page_id, int ns_id, byte[] ttl_wo_ns, boolean redirect, DateAdp modified_on, byte[] text_zip_data, int text_raw_len, int random_int, int text_db_id, int html_db_id) {
		core_tbl.Insert_cmd_by_batch(page_id, ns_id, ttl_wo_ns, redirect, modified_on, text_raw_len, random_int, text_db_id, html_db_id);
		text_tbl.Insert_cmd_by_batch(page_id, text_zip_data);
	}
	private void Dbs__set_by_tid(Xowd_db_file db) {
		switch (db.Tid()) {
			case Xowd_db_file_.Tid_wiki_solo:
			case Xowd_db_file_.Tid_text_solo:
			case Xowd_db_file_.Tid_core					: {db__core = db; if (props.Layout_text().Tid_is_all_or_few()) db__cat_core = db__text = db; break;}
			case Xowd_db_file_.Tid_text					: {db__text = db; break;}
			case Xowd_db_file_.Tid_html_data			: {db__html = db; break;}
			case Xowd_db_file_.Tid_wbase				: {if (db__wbase == null)		db__wbase = db; break;}
			case Xowd_db_file_.Tid_cat_core				:
			case Xowd_db_file_.Tid_cat					: {if (db__cat_core == null)	db__cat_core = db; break;}
		}
	}
	private void Dbs__add_and_save(Xowd_db_file rv) {
		dbs__ary = (Xowd_db_file[])Array_.Resize(dbs__ary, dbs__ary_len + 1);
		dbs__ary[dbs__ary_len++] = rv;
		db__core.Tbl__db().Commit_all(this);
		rv.Info_file().Save(rv.Tbl__cfg());
		rv.Info_session().Save(rv.Tbl__cfg());
		db_file_hash.Add_or_new(rv);
		id_hash.Add(gplx.core.primitives.Int_obj_ref.new_(rv.Id()), rv);
	}
	public void Rls() {
		for (int i = 0; i < dbs__ary_len; i++)
			dbs__ary[i].Rls();
	}
	private int Get_tid_idx(Xowd_db_file_hash hash, byte tid) {return hash.Count_of_tid(tid) + Int_.Base1;}
	private String Get_tid_name(Xowd_db_file_hash hash, int tid_idx, byte tid) {
		String tid_name = Xowd_db_file_.To_key(tid);
		String tid_idx_str = "";
		switch (tid) {
			case Xowd_db_file_.Tid_cat_core		: break;
			case Xowd_db_file_.Tid_cat_link		: tid_idx_str = "-db." + Int_.To_str_pad_bgn_zero(tid_idx, 3); break;
			default								: tid_idx_str = tid_idx == 1 ? "" : "-db." + Int_.To_str_pad_bgn_zero(tid_idx, 3); break;
		}
		return String_.Format("-{0}{1}.xowa", tid_name, tid_idx_str);	// EX: en.wikipedia.org-text-001.sqlite3
	}
	private static String Core_file_name(Xowd_db_layout layout, String domain_name) {
		switch (layout.Tid()) {
			case Xowd_db_layout.Const_all:		return domain_name + ".xowa";		// EX: en.wikipedia.org.xowa
			case Xowd_db_layout.Const_few:		return domain_name + "-text.xowa";	// EX: en.wikipedia.org-text.xowa
			case Xowd_db_layout.Const_lot:		return domain_name + "-core.xowa";	// EX: en.wikipedia.org-core.xowa
			default: 							throw Err_.new_unimplemented();
		}
	}
	public static boolean Maybe_core(String domain_name, String fil_name) {
		Xow_domain_itm domain_itm = Xow_domain_itm_.parse(Bry_.new_u8(domain_name));
		if (domain_itm.Domain_type_id() == Xow_domain_tid_.Int__other) {
			return String_.Has_at_end(fil_name, ".xowa");
		}
		String domain_str = domain_itm.Domain_str();
		return	(	String_.Eq(fil_name, domain_str + "-text.xowa")
				||	String_.Eq(fil_name, domain_str + "-core.xowa")
				||	String_.Eq(fil_name, domain_str + ".xowa")
				);
	}
	private static byte Core_db_tid(Xowd_db_layout layout) {
		switch (layout.Tid()) {
			case Xowd_db_layout.Const_all:		return Xowd_db_file_.Tid_wiki_solo;
			case Xowd_db_layout.Const_few:		return Xowd_db_file_.Tid_text_solo;
			case Xowd_db_layout.Const_lot:		return Xowd_db_file_.Tid_core;
			default:							throw Err_.new_unimplemented();
		}
	}
}

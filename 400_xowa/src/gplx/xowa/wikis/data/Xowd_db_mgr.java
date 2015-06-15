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
import gplx.xowa.dbs.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.*; import gplx.xowa.bldrs.infos.*;
public class Xowd_db_mgr {
	private Xowd_db_file[] dbs__ary = new Xowd_db_file[0]; private int dbs__ary_len = 0; private final Xowd_db_file_hash db_file_hash = new Xowd_db_file_hash();
	private final Io_url wiki_root_dir; private final Xow_domain domain_itm;
	public Xowd_db_mgr(Io_url wiki_root_dir, Xow_domain domain_itm) {this.wiki_root_dir = wiki_root_dir; this.domain_itm = domain_itm;}
	public Xowd_core_db_props		Props()			{return props;} private Xowd_core_db_props props = Xowd_core_db_props.Test;
	public Db_cfg_tbl				Tbl__cfg()		{return db__core.Tbl__cfg();}
	public Xowd_page_tbl			Tbl__page()		{return db__core.Tbl__page();}
	public Xowd_db_file				Db__core()		{return db__core;}		private Xowd_db_file db__core;
	public Xowd_db_file				Db__text()		{return db__text;}		private Xowd_db_file db__text;
	public Xowd_db_file				Db__html()		{return db__html;}		private Xowd_db_file db__html;
	public Xowd_db_file				Db__cat_core()	{return db__cat_core;}	private Xowd_db_file db__cat_core;
	public Xowd_db_file				Db__search()	{return db__search;}	private Xowd_db_file db__search;
	public Xowd_db_file				Db__wbase()		{return db__wbase;}		private Xowd_db_file db__wbase;
	public int						Dbs__len()		{return dbs__ary.length;}
	public void						Db__wbase_(Xowd_db_file v) {db__wbase = v;}
	public Xowd_db_file				Dbs__get_at(int i) {return dbs__ary[i];}
	public Xowd_db_file				Dbs__make_by_tid(byte tid) {
		int tid_idx = Get_tid_idx(db_file_hash, tid);
		return Dbs__make_by_tid(tid, Xob_info_file.Ns_ids_empty, tid_idx, Get_tid_name(db_file_hash, tid_idx, tid));
	}
	public Xowd_db_file				Dbs__make_by_tid(byte tid, String ns_ids, int part_id, String file_name_suffix) {
		Io_url url = wiki_root_dir.GenSubFil(domain_itm.Domain_str() + file_name_suffix);
		Xowd_db_file rv = Xowd_db_file.make_(db__core.Info_session(), props, dbs__ary_len, tid, url, ns_ids, part_id, db__core.Url().NameAndExt(), Db_conn_bldr.I.New(url));
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
			Io_mgr.I.DeleteFil_args(db.Url()).MissingFails_off().Exec();
			db.Cmd_mode_(Db_cmd_mode.Tid_delete);
		}
		db__core.Tbl__db().Commit_all(this);
		this.Init_by_load(db__core.Url());
	}
	public void Init_by_load(Io_url core_url) {
		db_file_hash.Clear();
		Db_conn core_conn = Db_conn_bldr.I.Get(core_url);
		props = Xowd_core_db_props.Cfg_load(core_url, core_conn);
		Dbs__set_by_tid(Xowd_db_file.load_(props, Xowd_db_file_.Id_core, Core_db_tid(props.Layout_text()), core_url, Xob_info_file.Ns_ids_empty, Xob_info_file.Part_id_1st, Guid_adp_.Empty));
		dbs__ary = db__core.Tbl__db().Select_all(props, core_url.OwnerDir());
		dbs__ary_len = dbs__ary.length;
		for (int i = 0; i < dbs__ary_len; i++) {
			Xowd_db_file db = dbs__ary[i];
			Dbs__set_by_tid(db);
			db_file_hash.Add_or_new(db);
		}
	}
	public void Init_by_make(Xowd_core_db_props props, Xob_info_session info_session) {
		this.props = props;
		String core_file_name = Core_file_name(props.Layout_text(), domain_itm.Domain_str());
		byte core_db_tid = Core_db_tid(props.Layout_text());
		Io_url core_db_url = wiki_root_dir.GenSubFil(core_file_name);
		Db_conn conn = Db_conn_bldr.I.New(core_db_url);
		conn.Txn_bgn();
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
		db__core.Tbl__cfg().Insert_yn(Xow_cfg_consts.Grp__wiki_schema, Xowd_db_file_schema_props.Key__col_page_html_text_id, Bool_.Y);
		props.Cfg_save(db__core.Tbl__cfg());	// NOTE: must save cfg now, especially zip_tid; latter will be reloaded after import is done;
		conn.Txn_end();
	}
	private void Dbs__set_by_tid(Xowd_db_file db) {
		switch (db.Tid()) {
			case Xowd_db_file_.Tid_wiki_solo:
			case Xowd_db_file_.Tid_text_solo:
			case Xowd_db_file_.Tid_core					: {db__core = db; if (props.Layout_text().Tid_is_all_or_few()) db__cat_core = db__search = db__text = db; break;}
			case Xowd_db_file_.Tid_text					: {db__text = db; break;}
			case Xowd_db_file_.Tid_html_data			: {db__html = db; break;}
			case Xowd_db_file_.Tid_search_core			: {if (db__search == null)		db__search = db; break;}
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
			case Xowd_db_file_.Tid_cat_link		: tid_idx_str = "-db." + Int_.Xto_str_pad_bgn_zero(tid_idx, 3); break;
			default								: tid_idx_str = tid_idx == 1 ? "" : "-db." + Int_.Xto_str_pad_bgn_zero(tid_idx, 3); break;
		}
		return String_.Format("-{0}{1}.xowa", tid_name, tid_idx_str);	// EX: en.wikipedia.org-text-001.sqlite3
	}
	private static String Core_file_name(Xowd_db_layout layout, String domain_name) {
		switch (layout.Tid()) {
			case Xowd_db_layout.Const_all:		return domain_name + ".xowa";		// EX: en.wikipedia.org.xowa
			case Xowd_db_layout.Const_few:		return domain_name + "-text.xowa";	// EX: en.wikipedia.org-text.xowa
			case Xowd_db_layout.Const_lot:		return domain_name + "-core.xowa";	// EX: en.wikipedia.org-core.xowa
			default: 							throw Err_.not_implemented_();
		}
	}
	private static byte Core_db_tid(Xowd_db_layout layout) {
		switch (layout.Tid()) {
			case Xowd_db_layout.Const_all:		return Xowd_db_file_.Tid_wiki_solo;
			case Xowd_db_layout.Const_few:		return Xowd_db_file_.Tid_text_solo;
			case Xowd_db_layout.Const_lot:		return Xowd_db_file_.Tid_core;
			default:							throw Err_.not_implemented_();
		}
	}
}

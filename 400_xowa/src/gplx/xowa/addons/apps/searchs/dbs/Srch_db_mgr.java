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
package gplx.xowa.addons.apps.searchs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.searchs.*;
import gplx.dbs.cfgs.*;
import gplx.xowa.wikis.data.*;
public class Srch_db_mgr {
	private final    Xow_wiki wiki;
	public final    Srch_db_upgrade Upgrade_mgr;
	public Srch_db_mgr(Xow_wiki wiki) {
		this.wiki = wiki;
		Upgrade_mgr = new Srch_db_upgrade(wiki, this);
	}
	public Srch_db_cfg			Cfg()						{return cfg;} private Srch_db_cfg cfg;
	public Db_cfg_tbl			Tbl__cfg()					{return tbl__cfg;} private Db_cfg_tbl tbl__cfg;
	public Srch_word_tbl		Tbl__word()					{return tbl__word;} private Srch_word_tbl tbl__word;
	public int					Tbl__link__len()			{return tbl__link__ary.length;}
	public Srch_link_tbl		Tbl__link__get_at(int i)	{return tbl__link__ary[i];}
	public Srch_link_tbl[]		Tbl__link__ary()			{return tbl__link__ary;} private Srch_link_tbl[] tbl__link__ary = Srch_link_tbl.Ary_empty;
	public Srch_db_mgr Init() {
		Xowd_db_mgr db_mgr = wiki.Data__core_mgr();
		Xowd_core_db_props db_props = db_mgr.Db__core().Db_props();
		Xowd_db_file word_db = null;
		if (	db_props.Schema() == 1
			||	db_props.Layout_text().Tid_is_all_or_few()) {
			// single_db; core_db has search_word and search_link
			word_db = db_mgr.Db__core();
			tbl__cfg = new Db_cfg_tbl(word_db.Conn(), "xowa_cfg");
			tbl__word = new Srch_word_tbl(word_db.Conn());
			tbl__link__ary = new Srch_link_tbl[1];
			Tbl__link__ary__set(tbl__link__ary, 0, word_db);
		} else {
			// many_db; figure out link_dbs
			word_db = db_mgr.Dbs__get_by_tid_or_null(Srch_db_mgr_.Dbtid__search_core);
			if (word_db == null) return this;	// HACK: called during db build; skip;
			tbl__cfg = new Db_cfg_tbl(word_db.Conn(), "xowa_cfg");
			tbl__word = new Srch_word_tbl(word_db.Conn());
			Ordered_hash hash = db_mgr.Dbs__get_hash_by_tid(Srch_db_mgr_.Dbtid__search_link);
			if (hash == null) {	// v2 file layout where search_word and search_link is in 1 search_db
				tbl__link__ary = new Srch_link_tbl[1];
				Tbl__link__ary__set(tbl__link__ary, 0, word_db);
			} else {			// v3 file layout where search_link is in many db
				int dbs_len = hash.Count();
				tbl__link__ary = new Srch_link_tbl[dbs_len];
				for (int i = 0; i < dbs_len; ++i) {
					Xowd_db_file db_file = (Xowd_db_file)hash.Get_at(i);
					Tbl__link__ary__set(tbl__link__ary, i, db_file);
				}
			}
		}
		cfg = Srch_db_cfg_.New(tbl__cfg, wiki.Stats().Num_pages(), Srch_db_cfg_.Select__version_id(tbl__cfg, tbl__word));
		return this;
	}
	public void Delete_all(Xowd_db_mgr core_data_mgr) {
		tbl__word.conn.Meta_tbl_delete(Srch_link_reg_tbl.Tbl_name);
		Xowd_core_db_props db_props = wiki.Data__core_mgr().Db__core().Db_props();
		if (	db_props.Schema() == 1
			||	db_props.Layout_text().Tid_is_all_or_few()) {
			// single_db; just drop tables
			tbl__word.conn.Meta_tbl_delete(tbl__word.tbl_name);
			Srch_link_tbl link_tbl = tbl__link__ary[0];
			link_tbl.conn.Meta_tbl_delete(link_tbl.tbl_name);
		} else {
			// many_db; drop databases
			core_data_mgr.Dbs__delete_by_tid(Xowd_db_file_.Tid_search_core);
			core_data_mgr.Dbs__delete_by_tid(Xowd_db_file_.Tid_search_link);
		}
	}
	public void Create_all() {
		Xowd_db_mgr db_mgr = wiki.Data__core_mgr();
		Xowd_core_db_props db_props = wiki.Data__core_mgr().Db__core().Db_props();
		if (	db_props.Schema() == 1
			||	db_props.Layout_text().Tid_is_all_or_few()) {
			// single_db; put both in core_db
			Xowd_db_file search_db = db_mgr.Db__core();
			tbl__word = new Srch_word_tbl(search_db.Conn()); tbl__word.Create_tbl();
			Srch_link_tbl tbl__link = new Srch_link_tbl(search_db.Conn()); tbl__link.Create_tbl();
			tbl__link__ary = new Srch_link_tbl[] {tbl__link};
			Srch_link_reg_tbl tbl__lreg = new Srch_link_reg_tbl(search_db.Conn()); tbl__lreg.Create_tbl();
			Tbl__link__ary__new(tbl__lreg, tbl__link__ary, db_mgr, 0, Bool_.N, search_db);
		} else {
			// many_db: put in 3 db;
			Xowd_db_file word_db = db_mgr.Dbs__make_by_tid(Srch_db_mgr_.Dbtid__search_core);				
			tbl__word = new Srch_word_tbl(word_db.Conn()); tbl__word.Create_tbl();
			tbl__link__ary = new Srch_link_tbl[2];
			Srch_link_reg_tbl tbl__lreg = new Srch_link_reg_tbl(word_db.Conn()); tbl__lreg.Create_tbl();
			Tbl__link__ary__new(tbl__lreg, tbl__link__ary, db_mgr, 0, Bool_.Y, null);
			Tbl__link__ary__new(tbl__lreg, tbl__link__ary, db_mgr, 1, Bool_.N, null);
		}
	}
	private static Srch_link_tbl Tbl__link__ary__set(Srch_link_tbl[] ary, int idx, Xowd_db_file db) {
		Srch_link_tbl tbl = new Srch_link_tbl(db.Conn());
		ary[idx] = tbl;
		return tbl;
	}
	private static void Tbl__link__ary__new(Srch_link_reg_tbl lreg_tbl, Srch_link_tbl[] ary, Xowd_db_mgr db_mgr, int idx, boolean ns_ids_is_main, Xowd_db_file db) {
		if (db == null) {
			String ns_ids = (ns_ids_is_main ? "000" : "999");
			String suffix = "-xtn.search.link-title-ns." + ns_ids + "-db.001.xowa"; // -xtn.search.link-title-ns.main-db.001.xowa
			db = db_mgr.Dbs__make_by_tid(Srch_db_mgr_.Dbtid__search_link, ns_ids, idx, suffix);
		}
		Srch_link_tbl tbl = Tbl__link__ary__set(ary, idx, db);
		tbl.Create_tbl();
		lreg_tbl.Insert(idx, db.Id(), Srch_link_reg_tbl.Db_type__title, ns_ids_is_main ? Srch_link_reg_tbl.Ns_type__main : Srch_link_reg_tbl.Ns_type__rest, 0, -1, -1);
	}
}

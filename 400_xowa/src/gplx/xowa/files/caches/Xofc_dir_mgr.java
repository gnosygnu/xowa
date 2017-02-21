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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*;
class Xofc_dir_mgr {
	private final    Xofc_dir_tbl tbl = new Xofc_dir_tbl();
	private final    Ordered_hash hash_by_names = Ordered_hash_.New_bry(); private final    Hash_adp hash_by_ids = Hash_adp_.New();
	private Xof_cache_mgr cache_mgr;
	public Xofc_dir_mgr(Xof_cache_mgr v) {this.cache_mgr = v;}
	public void Conn_(Db_conn v, boolean created, boolean schema_is_1) {tbl.Conn_(v, created, schema_is_1);}
	public Xofc_dir_itm Get_by_id(int id) {return (Xofc_dir_itm)hash_by_ids.Get_by(id);}
	public Xofc_dir_itm Get_by_name_or_make(byte[] name) {
		Xofc_dir_itm itm = Get_by_name_or_null(name);
		if (itm == null) {											// not in memory / db
			int id = cache_mgr.Next_id();							// make it
			itm = new Xofc_dir_itm(id, name, Db_cmd_mode.Tid_create);
			Add(name, itm);
		}
		return itm;
	}
	public Xofc_dir_itm Get_by_name_or_null(byte[] name) {
		Xofc_dir_itm itm = (Xofc_dir_itm)hash_by_names.Get_by(name);
		if (itm == null) {											// not in memory
			itm = tbl.Select_one(name);								// check db
			if (itm == Xofc_dir_itm.Null) return null;				// in db
			Add(name, itm);
		}
		return itm;
	}
	private void Add(byte[] name, Xofc_dir_itm dir) {
		hash_by_names.Add(name, dir);								// put it in memory
		hash_by_ids.Add(dir.Id(), dir);
	}
	public void Save_all() {
		int len = hash_by_names.Count();
		boolean err_seen = false;
		for (int i = 0; i < len; i++) {
			Xofc_dir_itm itm = (Xofc_dir_itm)hash_by_names.Get_at(i);
			if (err_seen)
				itm.Id_(cache_mgr.Next_id());
			if (itm.Cmd_mode() == Db_cmd_mode.Tid_create) {			// create; check if in db;
				Xofc_dir_itm cur = tbl.Select_one(itm.Name());
				if (cur != Xofc_dir_itm.Null)						// cur found
					itm.Cmd_mode_(Db_cmd_mode.Tid_update);			// change itm to update
			}
			String err = tbl.Db_save(itm);
			if (err != null) {
				Db_recalc_next_id(itm, err);
				err_seen = true;
			}
		}
	}
	public void Load_all() {
		List_adp list = List_adp_.New();
		tbl.Select_all(list);
		int len = list.Count();
		hash_by_ids.Clear();
		hash_by_names.Clear();
		for (int i = 0; i < len; ++i) {
			Xofc_dir_itm itm = (Xofc_dir_itm)list.Get_at(i);
			hash_by_names.Add(itm.Name(), itm);
			hash_by_ids.Add(itm.Id(), itm);
		}
	}
	public void Cleanup() {tbl.Cleanup();}
	private void Db_recalc_next_id(Xofc_dir_itm itm, String err) {
		if (String_.Has(err, "PRIMARY KEY must be unique")) { // primary key exception in strange situations (multiple xowas at same time)
			int next_id = tbl.Select_max_uid() + 1;				
			Gfo_usr_dlg_.Instance.Warn_many("", "", "uid out of sync; incrementing; uid=~{0} name=~{1} err=~{2}", itm.Id(), String_.new_u8(itm.Name()), err);
			itm.Id_(next_id);
			cache_mgr.Next_id_(next_id + 1);
			err = tbl.Db_save(itm);
			if (err == null) return;
		}
		Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to save uid; uid=~{0} name=~{1} err=~{2}", itm.Id(), String_.new_u8(itm.Name()), err);
	}
}

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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*;
class Xofc_dir_mgr {
	private final Xofc_dir_tbl tbl = new Xofc_dir_tbl();
	private final OrderedHash hash_by_names = OrderedHash_.new_bry_(); private final HashAdp hash_by_ids = HashAdp_.new_();
	private Xof_cache_mgr cache_mgr;
	public Xofc_dir_mgr(Xof_cache_mgr v) {this.cache_mgr = v;}
	public void Conn_(Db_conn v, boolean created, boolean version_is_1) {tbl.Conn_(v, created, version_is_1);}
	public Xofc_dir_itm Get_by_id(int id) {return (Xofc_dir_itm)hash_by_ids.Fetch(id);}
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
		Xofc_dir_itm itm = (Xofc_dir_itm)hash_by_names.Fetch(name);
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
			Xofc_dir_itm itm = (Xofc_dir_itm)hash_by_names.FetchAt(i);
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
		ListAdp list = ListAdp_.new_();
		tbl.Select_all(list);
		int len = list.Count();
		hash_by_ids.Clear();
		hash_by_names.Clear();
		for (int i = 0; i < len; ++i) {
			Xofc_dir_itm itm = (Xofc_dir_itm)list.FetchAt(i);
			hash_by_names.Add(itm.Name(), itm);
			hash_by_ids.Add(itm.Id(), itm);
		}
	}
	public void Cleanup() {tbl.Cleanup();}
	private void Db_recalc_next_id(Xofc_dir_itm itm, String err) {
		if (String_.Has(err, "PRIMARY KEY must be unique")) { // primary key exception in strange situations (multiple xowas at same time)
			int next_id = tbl.Select_max_uid() + 1;				
			Gfo_usr_dlg_._.Warn_many("", "", "uid out of sync; incrementing; uid=~{0} name=~{1} err=~{2}", itm.Id(), String_.new_utf8_(itm.Name()), err);
			itm.Id_(next_id);
			cache_mgr.Next_id_(next_id + 1);
			err = tbl.Db_save(itm);
			if (err == null) return;
		}
		Gfo_usr_dlg_._.Warn_many("", "", "failed to save uid; uid=~{0} name=~{1} err=~{2}", itm.Id(), String_.new_utf8_(itm.Name()), err);
	}
}

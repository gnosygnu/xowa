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
package gplx.xowa.files.fsdb.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.core.primitives.*;
import gplx.dbs.*;
import gplx.fsdb.*;
class Cache_fil_mgr {
	private Cache_mgr cache_mgr;
	private OrderedHash fil_hash = OrderedHash_.new_bry_();
	private Cache_fil_tbl fil_tbl = new Cache_fil_tbl();
	private Bry_bfr fil_key_bldr = Bry_bfr.reset_(255);
	public Cache_fil_mgr(Cache_mgr v) {this.cache_mgr = v;}
	public void Db_init(Db_conn p) {fil_tbl.Db_init(p);}
	public void Db_when_new(Db_conn p) {fil_tbl.Db_when_new(p);}
	public void Db_save() {
		int len = fil_hash.Count();
		boolean err_seen = false;
		for (int i = 0; i < len; i++) {
			Cache_fil_itm fil_itm = (Cache_fil_itm)fil_hash.FetchAt(i);
			if (err_seen)
				fil_itm.Uid_(cache_mgr.Next_id());
			if (fil_itm.Cmd_mode() == Db_cmd_mode.Create) {		// create; check if in db;
				Cache_fil_itm tbl_itm = fil_tbl.Select(fil_itm.Dir_id(), fil_itm.Fil_name(), fil_itm.Fil_is_orig(), fil_itm.Fil_w(), fil_itm.Fil_h(), fil_itm.Fil_thumbtime());
				if (tbl_itm != Cache_fil_itm.Null)				// tbl_itm found
					fil_itm.Cmd_mode_(Db_cmd_mode.Update);		// change fil_itm to update
			}
			String err_msg = fil_tbl.Db_save(fil_itm);
			if (err_msg != null) {
				Db_recalc_next_id(fil_itm, err_msg);
				err_seen = true;
			}
		}
	}
	public void Db_term() {
		fil_tbl.Db_term();
	}
	private void Db_recalc_next_id(Cache_fil_itm fil_itm, String err_msg) {
		if (String_.Has(err_msg, "PRIMARY KEY must be unique")) { // primary key exception in strange situations (multiple xowas at same time)
			int next_id = fil_tbl.Select_max_uid() + 1;				
			Gfo_usr_dlg_._.Warn_many("", "", "uid out of sync; incrementing; uid=~{0} name=~{1} err=~{2}", fil_itm.Uid(), String_.new_utf8_(fil_itm.Fil_name()), err_msg);
			fil_itm.Uid_(next_id);
			cache_mgr.Next_id_(next_id + 1);
			err_msg = fil_tbl.Db_save(fil_itm);
			if (err_msg == null)
				return;
		}
		Gfo_usr_dlg_._.Warn_many("", "", "failed to save uid; uid=~{0} name=~{1} err=~{2}", fil_itm.Uid(), String_.new_utf8_(fil_itm.Fil_name()), err_msg);
	}
	public Cache_fil_itm Get_or_new(int dir_id, byte[] fil_name, boolean fil_is_orig, int fil_w, int fil_h, double fil_thumbtime, Xof_ext fil_ext, long fil_size, Bool_obj_ref created) {
		byte[] fil_key = Cache_fil_itm.Gen_hash_key(fil_key_bldr, dir_id, fil_name, fil_is_orig, fil_w, fil_h, fil_thumbtime);
		Cache_fil_itm fil_itm = (Cache_fil_itm)fil_hash.Fetch(fil_key);
		if (fil_itm == null)			{								// not in memory
			fil_itm = fil_tbl.Select(dir_id, fil_name, fil_is_orig, fil_w, fil_h, fil_thumbtime);
			if (fil_itm == Cache_fil_itm.Null) {						// not in db
				int fil_id = cache_mgr.Next_id();
				fil_itm = new Cache_fil_itm().Init_by_make(fil_id, dir_id, fil_name, fil_is_orig, fil_w, fil_h, fil_thumbtime, fil_ext, fil_size, Xof_doc_page.Null);
				created.Val_(true);
			}
			fil_hash.Add(fil_key, fil_itm);
		}
		return fil_itm;
	}
	public void Compress(Xoa_app app, Cache_dir_mgr dir_mgr, Cache_cfg_mgr cfg_mgr) {
		try {
			app.Usr_dlg().Note_many("", "", "compressing cache");
			dir_mgr.Db_save();
			dir_mgr.Db_load();
			this.Db_save();		// save everything first
			fil_tbl.Db_load(fil_key_bldr, fil_hash);
			fil_hash.Sort();	// sorts by cache_time desc
			int len = fil_hash.Count();
			long cur_size = 0, actl_size = 0;
			Xof_url_bldr url_bldr = new Xof_url_bldr();
			ListAdp deleted = ListAdp_.new_();
			fil_tbl.Conn().Txn_mgr().Txn_bgn_if_none();
			long compress_to = cfg_mgr.Cache_min();
			for (int i = 0; i < len; i++) {
				Cache_fil_itm itm = (Cache_fil_itm)fil_hash.FetchAt(i);
				long itm_size = itm.Fil_size();
				long new_size = cur_size + itm_size;
				if (new_size > compress_to) {
					itm.Cmd_mode_(gplx.dbs.Db_cmd_mode.Delete);
					Fsys_delete(url_bldr, app.File_mgr().Repo_mgr(), dir_mgr, itm);
					deleted.Add(itm);
				}
				else
					actl_size += itm_size;
				cur_size = new_size;
				String err_msg = fil_tbl.Db_save(itm);	// save to db now, b/c fils will be deleted and want to keep db and fsys in sync
				if (err_msg != null)
					Db_recalc_next_id(itm, err_msg);
			}
			len = deleted.Count();
			for (int i = 0; i < len; i++) {
				Cache_fil_itm itm = (Cache_fil_itm)deleted.FetchAt(i);
				byte[] fil_key = itm.Gen_hash_key(fil_key_bldr);
				fil_hash.Del(fil_key);
			}
			cfg_mgr.Cache_len_(actl_size);
			this.Db_save();		// save everything again
		}
		catch (Exception e) {
			app.Usr_dlg().Warn_many("", "", "failed to compress cache: err=~{0}", Err_.Message_gplx_brief(e));
		}
		finally {fil_tbl.Conn().Txn_mgr().Txn_end_all();}
	}
	private void Fsys_delete(Xof_url_bldr url_bldr, Xoa_repo_mgr repo_mgr, Cache_dir_mgr dir_mgr, Cache_fil_itm itm) {
		byte mode_id = itm.Fil_is_orig() ? Xof_repo_itm.Mode_orig : Xof_repo_itm.Mode_thumb;
		byte[] wiki_domain = dir_mgr.Get_itm_by_id(itm.Dir_id()).Dir_bry();
		Xow_wiki wiki = repo_mgr.App().Wiki_mgr().Get_by_key_or_make(wiki_domain);
		wiki.Init_assert();
		Xof_repo_itm trg_repo = repo_mgr.Get_primary(wiki_domain);
		byte[] ttl = itm.Fil_name();			
		byte[] md5 = Xof_xfer_itm_.Md5_(ttl);
		int itm_ext_id = itm.Fil_ext().Id();
		Io_url fil_url = url_bldr.Init_for_trg_file(mode_id, trg_repo, ttl, md5, itm.Fil_ext(), itm.Fil_w()
			, Xof_doc_thumb.Convert_to_xowa_thumbtime	(itm_ext_id, itm.Fil_thumbtime())
			, Xof_doc_thumb.Convert_to_xowa_page		(itm_ext_id, itm.Fil_thumbtime())
			).Xto_url();
		Io_mgr._.DeleteFil_args(fil_url).MissingFails_off().Exec();
		itm.Cmd_mode_delete_();
	}
}

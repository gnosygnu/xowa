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
import gplx.core.primitives.*;
import gplx.dbs.*; import gplx.fsdb.*; import gplx.xowa.wikis.*;
import gplx.xowa.files.repos.*; import gplx.xowa.files.imgs.*;
class Xofc_fil_mgr {
	private Xof_cache_mgr cache_mgr;		
	private final    Xofc_fil_tbl tbl = new Xofc_fil_tbl(); private final    Ordered_hash hash = Ordered_hash_.New_bry(); private final    Bry_bfr key_bldr = Bry_bfr_.Reset(255);
	public Xofc_fil_mgr(Xof_cache_mgr v) {this.cache_mgr = v;}
	public void Conn_(Db_conn v, boolean created, boolean schema_is_1) {tbl.Conn_(v, created, schema_is_1);}
	public void Save_all() {
		int len = hash.Count();
		boolean err_seen = false;
		for (int i = 0; i < len; i++) {
			Xofc_fil_itm itm = (Xofc_fil_itm)hash.Get_at(i);
			if (err_seen)
				itm.Uid_(cache_mgr.Next_id());
			if (itm.Cmd_mode() == Db_cmd_mode.Tid_create) {		// create; check if in db;
				Xofc_fil_itm cur = tbl.Select_one_v1(itm.Dir_id(), itm.Name(), itm.Is_orig(), itm.W(), itm.H(), itm.Time());
				if (cur != Xofc_fil_itm.Null)					// cur found
					itm.Cmd_mode_(Db_cmd_mode.Tid_update);		// change itm to update
			}
			String err_msg = tbl.Db_save(itm);
			if (err_msg != null) {
				Db_recalc_next_id(itm, err_msg);
				err_seen = true;
			}
		}
	}
	public Xofc_fil_itm Get_or_make(int dir_id, byte[] name, boolean is_orig, int w, int h, double time, Xof_ext ext, long size, Bool_obj_ref created) {
		byte[] key = Xofc_fil_itm.Gen_hash_key_v1(key_bldr, dir_id, name, is_orig, w, h, time);
		Xofc_fil_itm itm = (Xofc_fil_itm)hash.Get_by(key);
		if (itm == Xofc_fil_itm.Null) {								// not in memory
			itm = tbl.Select_one_v1(dir_id, name, is_orig, w, h, time);
			if (itm == Xofc_fil_itm.Null) {							// not in db
				itm = Make_v1(dir_id, name, is_orig, w, h, time, Xof_lnki_page.Null, ext, size);
				created.Val_(true);
			}
			else													// NOTE: itm loaded from tbl; add to hash; do not add if created b/c Make adds to hash;
				hash.Add(key, itm);
		}
		return itm;
	}
	public Xofc_fil_itm Get_or_null(int dir_id, byte[] name, boolean is_orig, int w, double time, int page) {
		byte[] key = Xofc_fil_itm.Gen_hash_key_v2(key_bldr, dir_id, name, is_orig, w, time, page);
		Xofc_fil_itm itm = (Xofc_fil_itm)hash.Get_by(key);
		if (itm == null) {											// not in memory
			itm = tbl.Select_one_v2(dir_id, name, is_orig, w, time, page);
			if (itm == Xofc_fil_itm.Null) return itm;				// not in db
			hash.Add(key, itm);
		}
		return itm;
	}
	private Xofc_fil_itm Make_v1(int dir_id, byte[] name, boolean is_orig, int w, int h, double time, int page, Xof_ext ext, long size) {return Make(Bool_.Y, dir_id, name, is_orig, w, h, time, page, ext, size);}
	public Xofc_fil_itm Make_v2(int dir_id, byte[] name, boolean is_orig, int w, int h, double time, int page, Xof_ext ext, long size) {return Make(Bool_.N, dir_id, name, is_orig, w, h, time, page, ext, size);}
	private Xofc_fil_itm Make(boolean schema_is_1, int dir_id, byte[] name, boolean is_orig, int w, int h, double time, int page, Xof_ext ext, long size) {
		int id = cache_mgr.Next_id();
		Xofc_fil_itm rv = new Xofc_fil_itm(id, dir_id, name, is_orig, w, h, time, page, ext, size, 0, Db_cmd_mode.Tid_create).Cache_time_now_();
		byte[] key = schema_is_1 ? rv.Gen_hash_key_v1(key_bldr) : rv.Gen_hash_key_v2(key_bldr);
		hash.Add(key, rv);
		return rv;
	}
	public void Compress(Gfo_usr_dlg usr_dlg, Xoae_wiki_mgr wiki_mgr, Xoa_repo_mgr repo_mgr, Xofc_dir_mgr dir_mgr, Xofc_cfg_mgr cfg_mgr) {
		try {
			usr_dlg.Note_many("", "", "compressing cache");
			dir_mgr.Save_all(); dir_mgr.Load_all();				// save and load all dirs
			this.Save_all(); tbl.Select_all(key_bldr, hash);	// save and load all fils				
			hash.Sort();	// sorts by cache_time desc
			int len = hash.Count();
			long cur_size = 0, actl_size = 0;
			Xof_url_bldr url_bldr = new Xof_url_bldr();
			List_adp deleted = List_adp_.New();
			tbl.Conn().Txn_bgn("user__file_cache__compress");
			long compress_to = cfg_mgr.Cache_min();
			for (int i = 0; i < len; ++i) {
				Xofc_fil_itm itm = (Xofc_fil_itm)hash.Get_at(i);
				long itm_size = itm.Size();
				long new_size = cur_size + itm_size;
				if (new_size > compress_to) {
					itm.Cmd_mode_(gplx.dbs.Db_cmd_mode.Tid_delete);
					Fsys_delete(url_bldr, wiki_mgr, repo_mgr, dir_mgr, itm);
					deleted.Add(itm);
				}
				else
					actl_size += itm_size;
				cur_size = new_size;
				String err_msg = tbl.Db_save(itm);	// save to db now, b/c fils will be deleted and want to keep db and fsys in sync
				if (err_msg != null)
					Db_recalc_next_id(itm, err_msg);
			}
			len = deleted.Count();
			for (int i = 0; i < len; i++) {
				Xofc_fil_itm itm = (Xofc_fil_itm)deleted.Get_at(i);
				byte[] fil_key = itm.Gen_hash_key_v1(key_bldr);
				hash.Del(fil_key);
			}
			cfg_mgr.Cache_len_(actl_size);
			this.Save_all();		// save everything again
		}
		catch (Exception e) {
			usr_dlg.Warn_many("", "", "failed to compress cache: err=~{0}", Err_.Message_gplx_full(e));
		}
		finally {tbl.Conn().Txn_end();}
	}
	private void Fsys_delete(Xof_url_bldr url_bldr, Xoae_wiki_mgr wiki_mgr, Xoa_repo_mgr repo_mgr, Xofc_dir_mgr dir_mgr, Xofc_fil_itm itm) {
		byte mode_id = itm.Is_orig() ? Xof_img_mode_.Tid__orig : Xof_img_mode_.Tid__thumb;
		byte[] wiki_domain = dir_mgr.Get_by_id(itm.Dir_id()).Name();
		Xowe_wiki wiki = wiki_mgr.Get_by_or_make(wiki_domain);
		wiki.Init_assert();
		Xof_repo_itm trg_repo = repo_mgr.Get_by_primary(wiki_domain);
		byte[] ttl = itm.Name();			
		byte[] md5 = Xof_file_wkr_.Md5(ttl);
		int itm_ext_id = itm.Ext().Id();
		Io_url fil_url = url_bldr.Init_for_trg_file(trg_repo, mode_id, ttl, md5, itm.Ext(), itm.W()
			, Xof_lnki_time.Convert_to_xowa_thumbtime	(itm_ext_id, itm.Time())
			, Xof_lnki_time.Convert_to_xowa_page		(itm_ext_id, itm.Time())
			).Xto_url();
		Io_mgr.Instance.DeleteFil_args(fil_url).MissingFails_off().Exec();
		itm.Cmd_mode_delete_();
	}
	public void Cleanup() {tbl.Rls();}
	private void Db_recalc_next_id(Xofc_fil_itm fil_itm, String err_msg) {
		if (String_.Has(err_msg, "PRIMARY KEY must be unique")) { // primary key exception in strange situations (multiple xowas at same time)
			int next_id = tbl.Select_max_uid() + 1;				
			Gfo_usr_dlg_.Instance.Warn_many("", "", "uid out of sync; incrementing; uid=~{0} name=~{1} err=~{2}", fil_itm.Uid(), String_.new_u8(fil_itm.Name()), err_msg);
			fil_itm.Uid_(next_id);
			cache_mgr.Next_id_(next_id + 1);
			err_msg = tbl.Db_save(fil_itm);
			if (err_msg == null)
				return;
		}
		Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to save uid; uid=~{0} name=~{1} err=~{2}", fil_itm.Uid(), String_.new_u8(fil_itm.Name()), err_msg);
	}
}

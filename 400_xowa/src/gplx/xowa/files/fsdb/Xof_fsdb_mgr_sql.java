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
package gplx.xowa.files.fsdb; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*; import gplx.fsdb.*; import gplx.xowa.files.wiki_orig.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.qrys.*; import gplx.xowa.files.fsdb.caches.*;
import gplx.xowa.files.fsdb.fs_roots.*;
public class Xof_fsdb_mgr_sql implements Xof_fsdb_mgr, GfoInvkAble {
	private Db_provider img_regy_provider = null;		
	private Io_url app_file_dir;
	private Xof_url_bldr url_bldr = Xof_url_bldr.new_v2_();
	public boolean Tid_is_mem() {return false;}
	public Xof_qry_mgr Qry_mgr() {return qry_mgr;} private Xof_qry_mgr qry_mgr = new Xof_qry_mgr();
	public Xof_bin_mgr Bin_mgr() {return bin_mgr;} private Xof_bin_mgr bin_mgr;
	public Xof_bin_wkr Bin_wkr_fsdb() {return bin_wkr_fsdb;} private Xof_bin_wkr_fsdb_sql bin_wkr_fsdb;
	public void Db_bin_max_(long v) {mnt_mgr.Bin_db_max_(v);}
	public Fsdb_mnt_mgr Mnt_mgr() {return mnt_mgr;} private Fsdb_mnt_mgr mnt_mgr = new Fsdb_mnt_mgr();
	public int Patch_upright() {
		if (patch_upright_null) {
			if (mnt_mgr.Abc_mgr_len() > 0) {
				Fsdb_cfg_grp cfg_grp = mnt_mgr.Abc_mgr_at(0).Cfg_mgr().Grps_get_or_load(Xof_fsdb_mgr_cfg.Grp_xowa);
				boolean use_thumb_w	= cfg_grp.Get_yn_or(Xof_fsdb_mgr_cfg.Key_upright_use_thumb_w, Bool_.N);
				boolean fix_default	= cfg_grp.Get_yn_or(Xof_fsdb_mgr_cfg.Key_upright_fix_default, Bool_.N);
				patch_upright_tid = Xof_patch_upright_tid_.Merge(use_thumb_w, fix_default);
			}
			else	// TEST: no cfg dbs
				patch_upright_tid = Xof_patch_upright_tid_.Tid_all;
			patch_upright_null = false;
		}
		return patch_upright_tid;
	}	private boolean patch_upright_null = true; private int patch_upright_tid;
	public Io_url Db_dir() {return db_dir;} public Xof_fsdb_mgr_sql Db_dir_(Io_url v) {db_dir = v; mnt_mgr.Init(db_dir); return this;} private Io_url db_dir;
	public Gfo_usr_dlg Usr_dlg() {return usr_dlg;} Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Null;
	public Cache_mgr Cache_mgr() {return cache_mgr;} private Cache_mgr cache_mgr;
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public Xof_fsdb_mgr_sql(Xow_wiki wiki) {this.wiki = wiki;}
	public boolean Init_by_wiki__add_bin_wkrs(Xow_wiki wiki) { // helper method to init and add bin wkrs
		Xof_fsdb_mgr_sql fsdb_mgr = (Xof_fsdb_mgr_sql)wiki.File_mgr().Fsdb_mgr();
		boolean init = fsdb_mgr.Init_by_wiki(wiki);
		if (init) {
			Xof_bin_mgr bin_mgr = fsdb_mgr.Bin_mgr();
			Xof_bin_wkr_fsdb_sql fsdb_wkr = (Xof_bin_wkr_fsdb_sql)bin_mgr.Get_or_new(Xof_bin_wkr_.Key_fsdb_wiki, "xowa.fsdb.main");
			fsdb_wkr.Fsdb_mgr().Db_dir_(wiki.App().Fsys_mgr().File_dir().GenSubDir(wiki.Domain_str()));
			bin_mgr.Get_or_new(Xof_bin_wkr_.Key_http_wmf, "xowa.http.wmf");
		}
		return init;
	}
	public boolean Init_by_wiki(Xow_wiki wiki) {
		if (init) return false;
		try {
			this.wiki = wiki;
			usr_dlg = wiki.App().Usr_dlg();
			mnt_mgr.Usr_dlg_(usr_dlg);
			init = true;
			Xow_repo_mgr repo_mgr = wiki.File_mgr().Repo_mgr();
			Init_by_wiki(wiki, wiki.App().Fsys_mgr().File_dir().GenSubDir(wiki.Domain_str()), wiki.App().Fsys_mgr().File_dir(), repo_mgr);
			Xof_qry_wkr_xowa_reg qry_xowa = new Xof_qry_wkr_xowa_reg(img_regy_provider);
//			Xof_qry_wkr_xowa qry_xowa = new Xof_qry_wkr_xowa(new Xof_wiki_finder(wiki.App().Wiki_mgr().Get_by_key_or_make(Xow_wiki_.Domain_commons_bry), wiki), new gplx.xowa.files.qrys.Xof_img_meta_wkr_xowa());
			Xof_qry_wkr_wmf_api qry_wmf_api = new Xof_qry_wkr_wmf_api(wiki, new Xof_img_wkr_api_size_base_wmf());
			qry_mgr.Add_many(qry_xowa, qry_wmf_api);
			wiki.Rls_list().Add(this);
			bin_mgr.Resizer_(wiki.App().File_mgr().Img_mgr().Wkr_resize_img());
			return true;
		} catch (Exception exc) {throw Err_.new_fmt_("failed to initialize fsdb_mgr: wiki=~{0) err=~{1}", wiki.Domain_str(), Err_.Message_gplx_brief(exc));}
	}
	public void Init_by_wiki(Xow_wiki wiki, Io_url db_dir, Io_url app_file_dir, Xow_repo_mgr repo_mgr) {
		this.app_file_dir = app_file_dir;
		this.db_dir = db_dir;
		img_regy_provider = Wiki_orig_provider(db_dir);
		mnt_mgr.Init(db_dir);
		bin_mgr = new Xof_bin_mgr(wiki, this, repo_mgr);
		bin_wkr_fsdb = new Xof_bin_wkr_fsdb_sql(this).Bin_bfr_len_(64 * Io_mgr.Len_kb);	// most thumbs are 40 kb
		cache_mgr = wiki.App().File_mgr().Cache_mgr();
	}	private boolean init = false;
	public boolean Reg_select_itm_exists(byte[] ttl) {return Xof_wiki_orig_tbl.Select_itm_exists(img_regy_provider, ttl);}
	public void Reg_select_only(Xoa_page page, byte exec_tid, ListAdp itms, OrderedHash hash) {
		Xof_wiki_orig_tbl.Select_list(wiki.App().Usr_dlg(), img_regy_provider, wiki, exec_tid, itms, hash, url_bldr, bin_mgr.Repo_mgr());
	}
	public void Reg_select(Xoa_page page, byte exec_tid, ListAdp itms) {
		OrderedHash hash = OrderedHash_.new_bry_();
		Reg_select_only(page, exec_tid, itms, hash);
		Xof_fsdb_mgr_._.Fsdb_search(this, app_file_dir, page, exec_tid, itms, bin_mgr.Repo_mgr(), url_bldr);
	}
	public Fsdb_db_bin_fil Bin_db_get(int mnt_id, int bin_db_id) {
		return mnt_mgr.Bin_db_get(mnt_id, bin_db_id);
	}
	public Fsdb_fil_itm Fil_select_bin(byte[] dir, byte[] fil, boolean is_thumb, int width, double thumbtime)	{return mnt_mgr.Fil_select_bin(dir, fil, is_thumb, width, thumbtime);}
	public boolean Thm_select_bin(byte[] dir, byte[] fil, Fsdb_xtn_thm_itm thm)								{return mnt_mgr.Thm_select_bin(dir, fil, thm);}
	public void Reg_insert(Xof_fsdb_itm itm, byte repo_id, byte status) {
		byte[] orig_page = itm.Orig_ttl();
		if (!Xof_wiki_orig_tbl.Select_itm_exists(img_regy_provider, orig_page))
			Xof_wiki_orig_tbl.Insert(img_regy_provider, orig_page, status, repo_id, itm.Orig_redirect(), itm.Lnki_ext().Id(), itm.Orig_w(), itm.Orig_h());
	}
	public void Fil_insert(Fsdb_fil_itm rv    , byte[] dir, byte[] fil, int ext_id, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		mnt_mgr.Fil_insert(rv, dir, fil, ext_id, modified, hash, bin_len, bin_rdr);
	}
	public void Thm_insert(Fsdb_xtn_thm_itm rv, byte[] dir, byte[] fil, int ext_id, int w, int h, double thumbtime, int page, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		mnt_mgr.Thm_insert(rv, dir, fil, ext_id, w, h, thumbtime, page, modified, hash, bin_len, bin_rdr);
	}
	public void Img_insert(Fsdb_xtn_img_itm rv, byte[] dir, byte[] fil, int ext_id, int img_w, int img_h, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		mnt_mgr.Img_insert(rv, dir, fil, ext_id, modified, hash, bin_len, bin_rdr, img_w, img_h);
	}
	public void Txn_open() {
		mnt_mgr.Txn_open();
		img_regy_provider.Txn_mgr().Txn_bgn_if_none();
	}
	public void Txn_save() {
		mnt_mgr.Txn_save();
		img_regy_provider.Txn_mgr().Txn_end_all();
	}
	public void Rls() {
		this.Txn_save();	// NOTE: must call save, else user db will not update next id; DATE:2014-02-11
		mnt_mgr.Rls();
		img_regy_provider.Rls();
	}
	public static Io_url Wiki_orig_url(Io_url root_dir) {return root_dir.GenSubFil("wiki.orig#00.sqlite3");}
	public static Db_provider Wiki_orig_provider(Io_url root_dir) {
		Bool_obj_ref created = Bool_obj_ref.n_();
		Db_provider rv = Sqlite_engine_.Provider_load_or_make_(Wiki_orig_url(root_dir), created);
		if (created.Val())
			Xof_wiki_orig_tbl.Create_table(rv);
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_mnt_mgr))	return mnt_mgr;
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_mnt_mgr = "mnt_mgr";
}

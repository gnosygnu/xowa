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
import gplx.dbs.*; import gplx.xowa.users.dbs.*;
import gplx.xowa2.files.*;
public class Cache_mgr implements Xou_db_wkr, GfoInvkAble {
	private Cache_cfg_mgr cfg_mgr;
	private Cache_dir_mgr dir_mgr;
	private Cache_fil_mgr fil_mgr;
	private Xoa_app app;
	private Bool_obj_ref created = Bool_obj_ref.n_();
	public Cache_mgr(Xoa_app app) {
		this.app = app;
		cfg_mgr = new Cache_cfg_mgr(this);
		dir_mgr = new Cache_dir_mgr(this);
		fil_mgr = new Cache_fil_mgr(this);
	}
	public String Xtn_key() {return "xowa.file.cache_mgr";}
	public String Xtn_version() {return "0.1.0.0";}
	public int Next_id() {return cfg_mgr.Next_id();} public void Next_id_(int v) {cfg_mgr.Next_id_(v);}
	public void Db_init(Xou_db_mgr data_mgr) {
		try {
		Db_conn conn = data_mgr.Conn();
		data_mgr.Wkr_reg(this);
		cfg_mgr.Db_init(conn);
		dir_mgr.Db_init(conn);
		fil_mgr.Db_init(conn);
		} catch (Exception e) {app.Usr_dlg().Warn_many("", "", "cache_mgr.init:fatal error: err=~{0}", Err_.Message_gplx_brief(e));}
	}
	public void Db_when_new(Xou_db_mgr data_mgr) {
		Db_conn conn = data_mgr.Conn();
		cfg_mgr.Db_when_new(conn);
		dir_mgr.Db_when_new(conn);
		fil_mgr.Db_when_new(conn);
		this.Db_save(data_mgr);
	}
	public void Db_save(Xou_db_mgr data_mgr) {
		try {
		dir_mgr.Db_save();
		fil_mgr.Db_save();
		cfg_mgr.Db_save();	// always save cfg_mgr last; fil_mgr / dir_mgr may change next_id during failed saves; DATE:2014-03-07
		} catch (Exception e) {app.Usr_dlg().Warn_many("", "", "cache_mgr.save:fatal error: err=~{0}", Err_.Message_gplx_brief(e));}
	}
	public void Db_term(Xou_db_mgr data_mgr) {
		try {
		cfg_mgr.Db_term();
		dir_mgr.Db_term();
		fil_mgr.Db_term();
		} catch (Exception e) {app.Usr_dlg().Warn_many("", "", "cache_mgr.term:fatal error: err=~{0}", Err_.Message_gplx_brief(e));}
	}
	public Cache_fil_itm Reg(Xow_wiki wiki, Xof_fsdb_itm itm, long bin_len) {return this.Reg(wiki, itm.Orig_wiki(), itm.Lnki_ttl(), itm.File_is_orig(), itm.File_w(), itm.File_w(), itm.Lnki_thumbtime(), itm.Lnki_ext(), bin_len, DateAdp_.MaxValue, "");}
	public Cache_fil_itm Reg(Xow_wiki wiki, byte[] repo, byte[] ttl, boolean fil_is_orig, int fil_w, int fil_h, double fil_thumbtime, Xof_ext ext, long bin_len, DateAdp modified, String hash) {
		int dir_id = dir_mgr.Get_itm_by_name(repo).Uid();
		Cache_fil_itm fil_itm = fil_mgr.Get_or_new(dir_id, ttl, fil_is_orig, fil_w, fil_h, fil_thumbtime, ext, bin_len, created.Val_n_());
		fil_itm.Cache_time_now_();
		if (created.Val())	// increase cache_size if item is new; (don't increase if update); NOTE: not same as Db_cmd_mode.Created, b/c itm could be created, but not saved to db yet; EX: Page_1 has A.png; A.png marked Created; Page_2 has A.png; A.png still Created, but should increase cache_size
			cfg_mgr.Cache_len_add(bin_len);
		return fil_itm;
	}
//		public Xou_cache_fil Get_or_new(byte[] dir, byte[] ttl, boolean is_orig, int w, double time, int page) {
//			int dir_id = dir_mgr.Get_itm_by_name(dir).Uid();
//			Xou_cache_fil fil_itm = fil_mgr.Get_or_new(dir_id, ttl, is_orig, w, -1, time, ext, 0, created.Val_n_());
//			fil_itm.Cache_time_now_();
//			if (created.Val())	// increase cache_size if item is new; (don't increase if update); NOTE: not same as Db_cmd_mode.Created, b/c itm could be created, but not saved to db yet; EX: Page_1 has A.png; A.png marked Created; Page_2 has A.png; A.png still Created, but should increase cache_size
//				cfg_mgr.Cache_len_add(bin_len);
////			return fil_itm;
//			return null;
//		}
	public void Compress_check() {
		if (cfg_mgr.Cache_len() > cfg_mgr.Cache_max())
			fil_mgr.Compress(app, dir_mgr, cfg_mgr);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_cache_min))		return cfg_mgr.Cache_min() / Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_cache_min_))		cfg_mgr.Cache_min_(m.ReadLong("v") * Io_mgr.Len_mb);
		else if	(ctx.Match(k, Invk_cache_max))		return cfg_mgr.Cache_max() / Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_cache_max_))		cfg_mgr.Cache_max_(m.ReadLong("v") * Io_mgr.Len_mb);
		else if	(ctx.Match(k, Invk_cache_compress))	fil_mgr.Compress(app, dir_mgr, cfg_mgr);
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_cache_min = "cache_min", Invk_cache_min_ = "cache_min_", Invk_cache_max = "cache_max", Invk_cache_max_ = "cache_max_", Invk_cache_compress = "cache_compress";
}

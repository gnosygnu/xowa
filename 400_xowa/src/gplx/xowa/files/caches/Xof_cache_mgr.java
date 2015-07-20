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
import gplx.core.primitives.*; import gplx.dbs.*;
import gplx.xowa.files.fsdb.*; import gplx.xowa2.files.*; import gplx.xowa.wikis.*;
public class Xof_cache_mgr implements GfoInvkAble {
	private final Gfo_usr_dlg usr_dlg; private final Xoae_wiki_mgr wiki_mgr; private final Xoa_repo_mgr repo_mgr;
	private final Xofc_cfg_mgr cfg_mgr = new Xofc_cfg_mgr(); private final Xofc_dir_mgr dir_mgr; private final Xofc_fil_mgr fil_mgr;
	private final Bool_obj_ref fil_created = Bool_obj_ref.n_();
	public Xof_cache_mgr(Gfo_usr_dlg usr_dlg, Xoae_wiki_mgr wiki_mgr, Xoa_repo_mgr repo_mgr) {
		this.usr_dlg = usr_dlg; this.wiki_mgr = wiki_mgr; this.repo_mgr = repo_mgr;
		this.dir_mgr = new Xofc_dir_mgr(this);
		this.fil_mgr = new Xofc_fil_mgr(this);
	}
	public int Next_id() {return cfg_mgr.Next_id();} public void Next_id_(int v) {cfg_mgr.Next_id_(v);}
	public void Init_for_db(Db_conn conn, boolean created, boolean schema_is_1) {
		cfg_mgr.Conn_(conn, created, schema_is_1);
		dir_mgr.Conn_(conn, created, schema_is_1);
		fil_mgr.Conn_(conn, created, schema_is_1);
	}
	public void Db_save() {
		try {
			dir_mgr.Save_all();
			fil_mgr.Save_all();
			cfg_mgr.Save_all();	// always save cfg_mgr last; fil_mgr / dir_mgr may change next_id during failed saves; DATE:2014-03-07
		} catch (Exception e) {usr_dlg.Warn_many("", "", "cache_mgr.save:fatal error: err=~{0}", Err_.Message_gplx_full(e));}
	}
	public void Db_term() {
		try {
			cfg_mgr.Cleanup();
			dir_mgr.Cleanup();
			fil_mgr.Cleanup();
		} catch (Exception e) {usr_dlg.Warn_many("", "", "cache_mgr.term:fatal error: err=~{0}", Err_.Message_gplx_full(e));}
	}
	public Xofc_fil_itm Reg(Xof_fsdb_itm itm, long bin_len) {return this.Reg(itm.Orig_repo_name(), itm.Orig_ttl(), itm.File_is_orig(), itm.File_w(), itm.File_w(), itm.Lnki_time(), itm.Orig_ext(), bin_len, DateAdp_.MaxValue, "");}
	private Xofc_fil_itm Reg(byte[] repo, byte[] ttl, boolean fil_is_orig, int fil_w, int fil_h, double fil_thumbtime, Xof_ext ext, long bin_len, DateAdp modified, String hash) {
		int dir_id = dir_mgr.Get_by_name_or_make(repo).Id();
		Xofc_fil_itm fil_itm = fil_mgr.Get_or_make(dir_id, ttl, fil_is_orig, fil_w, fil_h, fil_thumbtime, ext, bin_len, fil_created.Val_n_());
		fil_itm.Cache_time_now_();
		if (fil_created.Val())	// increase cache_size if item is new; (don't increase if update); NOTE: not same as Db_cmd_mode.Created, b/c itm could be created, but not saved to db yet; EX: Page_1 has A.png; A.png marked Created; Page_2 has A.png; A.png still Created, but should increase cache_size
			cfg_mgr.Cache_len_add(bin_len);
		return fil_itm;
	}
	public void Reg_and_check_for_size_0(Xof_fsdb_itm itm) {
		if (Env_.Mode_testing()) return;				// NOTE: needed else test breaks in sqlite mode; DATE:2015-02-21
		Xofc_fil_itm cache_fil_itm = this.Reg(itm, 0);	// get item
		if (cache_fil_itm.Size() == 0) {				// item does not exist; size will be 0, since 0 passed above
			long fil_size = Io_mgr.I.QueryFil(itm.Html_view_url()).Size();
			cache_fil_itm.Size_(fil_size);
		}
	}
	public void Compress_check() {
		if (cfg_mgr.Cache_len() > cfg_mgr.Cache_max())
			fil_mgr.Compress(usr_dlg, wiki_mgr, repo_mgr, dir_mgr, cfg_mgr);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_cache_min))		return cfg_mgr.Cache_min() / Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_cache_min_))		cfg_mgr.Cache_min_(m.ReadLong("v") * Io_mgr.Len_mb);
		else if	(ctx.Match(k, Invk_cache_max))		return cfg_mgr.Cache_max() / Io_mgr.Len_mb;
		else if	(ctx.Match(k, Invk_cache_max_))		cfg_mgr.Cache_max_(m.ReadLong("v") * Io_mgr.Len_mb);
		else if	(ctx.Match(k, Invk_cache_compress))	fil_mgr.Compress(usr_dlg, wiki_mgr, repo_mgr, dir_mgr, cfg_mgr);
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_cache_min = "cache_min", Invk_cache_min_ = "cache_min_", Invk_cache_max = "cache_max", Invk_cache_max_ = "cache_max_", Invk_cache_compress = "cache_compress";
}

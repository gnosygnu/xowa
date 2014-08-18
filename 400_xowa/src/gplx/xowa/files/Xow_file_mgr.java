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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.fsdb.*; import gplx.xowa.files.fsdb.*;
public class Xow_file_mgr implements GfoInvkAble {
	private Xof_wkr_mgr wkr_mgr;
	public Xow_file_mgr(Xow_wiki wiki) {
		this.wiki = wiki;
		repo_mgr = new Xow_repo_mgr(wiki);
		meta_mgr = new Xof_meta_mgr(wiki);
		fsdb_mgr = new Xof_fsdb_mgr_sql(wiki);
		wkr_mgr = new Xof_wkr_mgr(this);
	}
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public byte Version() {
		if (version == Bool_.__byte) {
			Io_url file_dir = wiki.Fsys_mgr().File_dir();
			Io_url[] sqlite_fils = Io_mgr._.QueryDir_args(file_dir).FilPath_("*.sqlite3").ExecAsUrlAry();
			if (sqlite_fils.length == 0)
				version = Version_1;
			else
				version = Version_2;
		}
		return version;
	}	private byte version = Version_null;
	public boolean Version_1_y() {return this.Version() == Version_1;}
	public boolean Version_2_y() {return this.Version() == Version_2;}
	public void Version_1_y_() {version = Version_1;}	// TEST:
	public void Version_2_y_() {version = Version_2;}	// TEST:
	public void Fsdb_mgr_(Xof_fsdb_mgr fsdb_mgr) {
		this.fsdb_mgr = fsdb_mgr;
		version = Version_2;
	}
	public int Patch_upright() {
		return this.Version() == Version_1
			? Xof_patch_upright_tid_.Tid_all
			: fsdb_mgr.Patch_upright()
			;
	}
	public static final byte Version_null = Byte_.MaxValue_127, Version_1 = 1, Version_2 = 2;
	public Xow_repo_mgr Repo_mgr() {return repo_mgr;} private Xow_repo_mgr repo_mgr;
	public Xof_meta_mgr  Meta_mgr() {return meta_mgr;} private Xof_meta_mgr meta_mgr;
	public Xof_cfg_download Cfg_download() {return cfg_download;} private Xof_cfg_download cfg_download = new Xof_cfg_download();
	public void Cfg_set(String grp, String key, String val) {	// TEST: should only be called by tests
		if (test_grps == null) test_grps = HashAdp_.new_();
		Fsdb_cfg_grp grp_itm = (Fsdb_cfg_grp)test_grps.Fetch(grp);
		if (grp_itm == null) {
			grp_itm = new Fsdb_cfg_grp(grp);
			test_grps.Add(grp, grp_itm);
		}
		grp_itm.Upsert(key, val);
	}	private HashAdp test_grps;
	public Fsdb_cfg_grp Cfg_get(String grp) {
		if (test_grps != null) {
			Fsdb_cfg_grp rv = (Fsdb_cfg_grp)test_grps.Fetch(grp);
			return rv == null ? Fsdb_cfg_grp.Null : rv;
		}
		if (this.Version() == Version_1) return Fsdb_cfg_grp.Null;
		fsdb_mgr.Init_by_wiki__add_bin_wkrs(wiki);	// make sure fsdb is init'd
		return fsdb_mgr.Mnt_mgr().Abc_mgr_at(0).Cfg_mgr().Grps_get_or_load(grp);
	}
	public Xof_fsdb_mgr Fsdb_mgr() {return fsdb_mgr;} private Xof_fsdb_mgr fsdb_mgr;
	public boolean Find_meta(Xof_xfer_itm xfer_itm) {
		xfer_itm.Trg_repo_idx_(Xof_meta_itm.Repo_unknown);
		byte[] xfer_itm_ttl = xfer_itm.Lnki_ttl();
		xfer_itm.Set__ttl(xfer_itm_ttl, Bry_.Empty);
		Xof_meta_itm meta_itm = meta_mgr.Get_itm_or_new(xfer_itm_ttl, xfer_itm.Lnki_md5());
		xfer_itm.Set__meta_only(meta_itm);
		if (meta_itm.State_new()) {														// meta_itm is brand new
			xfer_itm.Set__meta(meta_itm, repo_mgr.Repos_get_at(0).Trg(), wiki.Html_mgr().Img_thumb_width());	// default to 1st repo to prevent null_ref in xfer_mgr; questionable, but all wikis must have at least 1 repo
			xfer_itm.Calc_by_meta();
			return false;
		}
		else {																			// meta_itm exists
			Xof_repo_itm cur_repo = null;
			cur_repo = meta_itm.Repo_itm(wiki);
			xfer_itm.Set__meta(meta_itm, cur_repo, wiki.Html_mgr().Img_thumb_width());
			return xfer_itm.Calc_by_meta();
		}
	}
	public boolean Exists(byte[] ttl_bry) {
		if (this.Version_1_y()) {
			Xof_meta_itm meta = meta_mgr.Get_itm_or_new(ttl_bry);
			return meta.Orig_exists() == Bool_.Y_byte || meta.Thumbs().length != 0;
		}
		else
			return fsdb_mgr.Reg_select_itm_exists(ttl_bry);
	}

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_repos))					return repo_mgr;
		else if	(ctx.Match(k, Invk_metas))					return meta_mgr;
		else if	(ctx.Match(k, Invk_cfg_download))			return cfg_download;
		else if	(ctx.Match(k, Invk_fsdb))					return fsdb_mgr;
		else if	(ctx.Match(k, Invk_wkrs))					return wkr_mgr;
		else	return GfoInvkAble_.Rv_unhandled;
//			return this;
	}	private static final String Invk_repos = "repos", Invk_metas = "metas", Invk_cfg_download = "cfg_download", Invk_fsdb = "fsdb", Invk_wkrs = "wkrs";
}

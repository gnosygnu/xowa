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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; 
import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*;
import gplx.fsdb.*; import gplx.fsdb.meta.*; import gplx.xowa.files.fsdb.*;
import gplx.xowa.wikis.tdbs.metas.*;
public class Xow_file_mgr implements Gfo_invk {
	private Xof_wkr_mgr wkr_mgr;
	public Xow_file_mgr(Xowe_wiki wiki) {
		this.wiki = wiki;
		repo_mgr = new Xowe_repo_mgr(wiki);
		meta_mgr = new Xof_meta_mgr(wiki);
		wkr_mgr = new Xof_wkr_mgr(this);
	}
	public Fsdb_db_mgr		Db_core() {return db_core;} private Fsdb_db_mgr db_core;
	public Xof_orig_mgr		Orig_mgr() {return orig_mgr;} private final    Xof_orig_mgr orig_mgr = new Xof_orig_mgr();
	public Xof_fsdb_mode	Fsdb_mode() {
		if (fsdb_mode == null) {
			Version();
		}
		return fsdb_mode;
	} private Xof_fsdb_mode fsdb_mode = null;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public byte Version() {
		if (version == Bool_.__byte) {
			Io_url file_dir = wiki.Fsys_mgr().File_dir();
			if (!Io_mgr.Instance.ExistsFil(file_dir.GenSubFil(Fsdb_db_mgr__v1.Mnt_name))) {
				version = Version_1;
				fsdb_mode = Xof_fsdb_mode.New__v0();
			}
			else {
				version = Version_2;
				fsdb_mode = Xof_fsdb_mode.New__v2__gui();
			}
		}
		return version;
	}	private byte version = Version_null;
	public boolean Version_1_y() {return this.Version() == Version_1;}
	public boolean Version_2_y() {return this.Version() == Version_2;}
	public void Version_1_y_() {version = Version_1;}	// TEST:
	public void Version_2_y_() {version = Version_2; fsdb_mode = Xof_fsdb_mode.New__v2__gui();}	// TEST:
	public void Fsdb_mgr_(Xof_fsdb_mgr fsdb_mgr) {
		this.fsdb_mgr = fsdb_mgr;			
		version = Version_2;
	}
	public int Patch_upright() {
		Fsm_mnt_mgr mnt_mgr = fsdb_mgr.Mnt_mgr();
		return this.Version() == Version_1 || mnt_mgr == null
			? Xof_patch_upright_tid_.Tid_all
			: fsdb_mgr.Mnt_mgr().Patch_upright()
			;
	}
	public static final byte Version_null = Byte_.Max_value_127, Version_1 = 1, Version_2 = 2;
	public Xowe_repo_mgr Repo_mgr() {return repo_mgr;} private Xowe_repo_mgr repo_mgr;
	public Xof_meta_mgr  Dbmeta_mgr() {return meta_mgr;} private Xof_meta_mgr meta_mgr;
	public Xof_cfg_download Cfg_download() {return cfg_download;} private Xof_cfg_download cfg_download = new Xof_cfg_download();
	public void Init_by_wiki(Xow_wiki wiki) {
		cfg_download.Init_by_wiki(wiki);
		
		// if non-wmf, set fsdb_mgr to fs.dir; DATE:2017-02-01
		if (wiki.Domain_tid() == gplx.xowa.wikis.domains.Xow_domain_tid_.Tid__other) {
			String cfg_domain_str = wiki.Data__core_mgr().Db__core().Tbl__cfg().Select_str_or("xowa.bldr.session", "wiki_domain", wiki.Domain_str());		// NOTE: or is "wiki.domain" for user_wikis
			// FOLDER.RENAME: do not change to fs.dir if renamed; DATE:2017-02-06
			if (String_.Eq(cfg_domain_str, wiki.Domain_str())) {
				// wiki has not been renamed; use fs.dir
				gplx.xowa.files.fsdb.fs_roots.Fs_root_core fsdir_core = gplx.xowa.files.fsdb.fs_roots.Fs_root_core.Set_fsdb_mgr(this, this.wiki);
				fsdir_core.Orig_dir_(wiki.Fsys_mgr().Root_dir().GenSubDir_nest("file", "orig"));
			}
			else {
				// wiki has been renamed; apply "imported name" to wikis; note that this won't support renamed wikia wikis; DATE:2017-02-07
				byte[] cfg_domain_bry = Bry_.new_u8(cfg_domain_str);
				Xof_repo_pair[] repo_pairs = wiki.File__repo_mgr().Repos_ary();
				for (int i = 0; i < repo_pairs.length; i++) {
					Xof_repo_pair repo_pair = repo_pairs[i];
					if (Bry_.Eq(wiki.Domain_bry(), repo_pair.Trg().Wiki_domain())) {
						repo_pair.Wiki_domain_(cfg_domain_bry);
						repo_pair.Src().Wiki_domain_(cfg_domain_bry);
						repo_pair.Trg().Wiki_domain_(cfg_domain_bry);
					}
				} 
			}
		}
	}
	public void Cfg_set(String grp, String key, String val) {	// TEST: should only be called by tests
		if (test_grps == null) test_grps = Hash_adp_.New();
		Db_cfg_hash grp_itm = (Db_cfg_hash)test_grps.Get_by(grp);
		if (grp_itm == null) {
			grp_itm = new Db_cfg_hash(grp);
			test_grps.Add(grp, grp_itm);
		}
		grp_itm.Set(key, val);
	}	private Hash_adp test_grps;
	public Db_cfg_hash Cfg_get(String grp) {
		if (test_grps != null) {
			Db_cfg_hash rv = (Db_cfg_hash)test_grps.Get_by(grp);
			return rv == null ? new Db_cfg_hash("") : rv;
		}
		if (this.Version() == Version_1) return new Db_cfg_hash("");
		this.Init_file_mgr_by_load(wiki);	// make sure fsdb is init'd
		Fsm_mnt_itm mnt_itm = fsdb_mgr.Mnt_mgr().Mnts__get_main_or_null(); // NOTE: can be null for embeddable parser; DATE:2017-06-06
		return mnt_itm == null ? new Db_cfg_hash("") : mnt_itm.Cfg_mgr().Grps_get_or_load(grp);
	}
	public Xof_fsdb_mgr Fsdb_mgr() {return fsdb_mgr;} private Xof_fsdb_mgr fsdb_mgr = new Xof_fsdb_mgr__sql();
	public void Clear_for_tests() {	// NOTE: must clear else fsdb_mode will be cached for multiple runs; will generally be v1, but some tests will set to v2; DATE:2015-12-22
		version = Bool_.__byte;
		fsdb_mode = null;
	}
	public boolean Find_meta(Xof_xfer_itm xfer_itm) {
		xfer_itm.Orig_repo_id_(Xof_meta_itm.Repo_unknown);
		byte[] xfer_itm_ttl = xfer_itm.Lnki_ttl();
		xfer_itm.Orig_ttl_and_redirect_(xfer_itm_ttl, Bry_.Empty);
		Xof_meta_itm meta_itm = meta_mgr.Get_itm_or_new(xfer_itm_ttl, xfer_itm.Orig_ttl_md5());
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
			return orig_mgr.Find_by_ttl_or_null(ttl_bry) != Xof_orig_itm.Null;
	}
	public void Init_file_mgr_by_load(Xow_wiki wiki) {
		if (db_core != null) return;	// already init'd
		if (wiki.Data__core_mgr() == null) return; // NOTE: can be null for embeddable parser; DATE:2017-06-06

		this.db_core = Fsdb_db_mgr_.new_detect(wiki, wiki.Fsys_mgr().Root_dir(), wiki.Fsys_mgr().File_dir());
		if (	db_core == null			// "-file-core.xowa" not found
			&&	!wiki.Data__core_mgr().Props().Layout_file().Tid_is_all()	// DATE:2015-08-10
			)
			db_core = Fsdb_db_mgr__v2_bldr.Get_or_make(wiki, false);	// make it
		this.version = Version_2;
		this.fsdb_mode = Xof_fsdb_mode.New__v2__gui();
		orig_mgr.Init_by_wiki(wiki, fsdb_mode, db_core.File__orig_tbl_ary(), Xof_url_bldr.new_v2());
		fsdb_mgr.Init_by_wiki(wiki);
	}
	public void Rls() {
		fsdb_mgr.Rls();
		db_core = null;
	}

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_repos))					return repo_mgr;
		else if	(ctx.Match(k, Invk_metas))					return meta_mgr;
		else if	(ctx.Match(k, Invk_cfg_download))			return cfg_download;	// NOTE: documented for Schnark; https://sourceforge.net/p/xowa/tickets/344/
		else if	(ctx.Match(k, Invk_fsdb))					return fsdb_mgr;
		else if	(ctx.Match(k, Invk_wkrs))					return wkr_mgr;
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_repos = "repos", Invk_metas = "metas", Invk_cfg_download = "cfg_download", Invk_fsdb = "fsdb", Invk_wkrs = "wkrs";
}

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
package gplx.fsdb.meta; import gplx.*; import gplx.fsdb.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
public class Fsm_mnt_mgr implements Gfo_invk {
	private Db_cfg_tbl cfg_tbl; private Fsm_mnt_tbl mnt_tbl;
	private Fsm_mnt_itm[] mnt_ary; private int mnt_ary_len = 0;
	public void Ctor_by_load(Fsdb_db_mgr db_core) {
		Db_conn conn = db_core.File__mnt_file().Conn();
		this.cfg_tbl = new Db_cfg_tbl	(conn, db_core.File__cfg_tbl_name());
		this.mnt_tbl = new Fsm_mnt_tbl	(conn, db_core.File__schema_is_1());
		this.mnt_ary = mnt_tbl.Select_all();
		this.mnt_ary_len = mnt_ary.length;
		for (int i = 0; i < mnt_ary_len; ++i) {
			mnt_ary[i].Ctor_by_load(db_core);
		}
		this.insert_idx = cfg_tbl.Select_int(Cfg_grp_core, Cfg_key_mnt_insert_idx);
		Db_cfg_hash cfg_hash = this.Mnts__get_main().Cfg_mgr().Grps_get_or_load(Xof_fsdb_mgr_cfg.Grp_xowa);
		boolean use_thumb_w	= cfg_hash.Get_by(Xof_fsdb_mgr_cfg.Key_upright_use_thumb_w).To_yn_or_n();
		boolean fix_default	= cfg_hash.Get_by(Xof_fsdb_mgr_cfg.Key_upright_fix_default).To_yn_or_n();
		this.patch_upright_tid = Xof_patch_upright_tid_.Merge(use_thumb_w, fix_default);
	}
	public int			Mnts__len()						{return mnt_ary_len;}
	public Fsm_mnt_itm	Mnts__get_at(int i)				{return mnt_ary[i];}
	public Fsm_mnt_itm	Mnts__get_main_or_null()		{return mnt_ary == null ? null : mnt_ary[Mnt_idx_main];} // NOTE: can be null for embeddable parser; DATE:2017-06-06
	public Fsm_mnt_itm	Mnts__get_main()				{return mnt_ary[Mnt_idx_main];}
	public Fsm_mnt_itm	Mnts__get_insert()				{return mnt_ary[insert_idx];} public void Mnts__get_insert_idx_(int v) {insert_idx = v;} private int insert_idx = Mnt_idx_user;
	public Fsm_bin_fil	Bins__at(int mnt_id, int bin_db_id) {return mnt_ary[mnt_id].Bin_mgr().Dbs__get_by_or_null(bin_db_id);}
	public int			Patch_upright()					{return patch_upright_tid;} private int patch_upright_tid = Xof_patch_upright_tid_.Tid_all;
	public void Rls() {
		for (int i = 0; i < mnt_ary_len; ++i) {
			Fsm_mnt_itm mnt = mnt_ary[i];
			mnt.Rls();
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return Gfo_invk_.Rv_unhandled;}	
	public static final int Mnt_idx_main = 0, Mnt_idx_user = 1, Insert_to_bin_null = -1;
	public static void Patch(Db_cfg_tbl cfg_tbl) {
		cfg_tbl.Upsert_str(Xof_fsdb_mgr_cfg.Grp_xowa, Xof_fsdb_mgr_cfg.Key_gallery_fix_defaults		, "y");
		cfg_tbl.Upsert_str(Xof_fsdb_mgr_cfg.Grp_xowa, Xof_fsdb_mgr_cfg.Key_gallery_packed			, "y");
		cfg_tbl.Upsert_str(Xof_fsdb_mgr_cfg.Grp_xowa, Xof_fsdb_mgr_cfg.Key_upright_use_thumb_w		, "y");
		cfg_tbl.Upsert_str(Xof_fsdb_mgr_cfg.Grp_xowa, Xof_fsdb_mgr_cfg.Key_upright_fix_default		, "y");
	}
	public static void Patch_core(Db_cfg_tbl cfg_tbl) {	// NOTE: thes need to be upserts else upgrading will fail; DATE:2015-05-23
		cfg_tbl.Upsert_int	(Fsm_cfg_mgr.Grp_core, Fsm_cfg_mgr.Key_next_id					, 1);		// start next_id at 1
		cfg_tbl.Upsert_yn	(Fsm_cfg_mgr.Grp_core, Fsm_cfg_mgr.Key_schema_thm_page			, Bool_.Y);	// new dbs automatically have page and time in fsdb_xtn_tm
		cfg_tbl.Upsert_yn	(Fsm_cfg_mgr.Grp_core, Fsm_cfg_mgr.Key_patch__next_id			, Bool_.Y);	// new dbs automatically have correct next_id
	}
	public static final String Cfg_grp_core = "core", Cfg_key_mnt_insert_idx = "mnt.insert_idx";	// SERIALIZED
}

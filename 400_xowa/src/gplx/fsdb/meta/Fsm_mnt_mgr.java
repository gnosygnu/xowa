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
package gplx.fsdb.meta; import gplx.*; import gplx.fsdb.*;
import gplx.core.primitives.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.fsdb.data.*; import gplx.fsdb.meta.*;
public class Fsm_mnt_mgr implements GfoInvkAble {
	private final Db_cfg_tbl cfg_tbl = new Db_cfg_tbl(); private final Fsm_mnt_tbl mnt_tbl = new Fsm_mnt_tbl();
	private Fsm_abc_mgr[] ary; private int ary_len = 0;
	private static final String Db_conn_bldr_type_mnt = "gplx.fsdb.mnt";
	public void Init_by_wiki(Io_url db_dir, boolean version_is_1) {
		Db_conn_bldr_data conn_data = Db_conn_bldr.I.Get_or_new(Db_conn_bldr_type_mnt, db_dir.GenSubFil("wiki.mnt.sqlite3"));
		Fsm_mnt_itm[] mnts = Mnts__load_or_make(conn_data, version_is_1);
		ary_len = mnts.length;
		ary = new Fsm_abc_mgr[ary_len];
		for (int i = 0; i < ary_len; i++) {
			Fsm_mnt_itm itm = mnts[i];
			Fsm_abc_mgr abc_mgr = new Fsm_abc_mgr();
			ary[i] = abc_mgr;
			if (version_is_1) {
				Io_url abc_url = db_dir.GenSubFil_nest(itm.Url(), "fsdb.abc.sqlite3");
				abc_mgr.Init_for_db(version_is_1, abc_url.OwnerDir());
			}
			else
				throw Err_.not_implemented_();
		}
		if (conn_data.Created()) Fsm_mnt_mgr.Patch(this);
		insert_to_mnt = cfg_tbl.Select_as_int_or_fail("core", "mnt.insert_idx");
		if (ary_len > 0) {
			Db_cfg_grp cfg_grp = this.Mnts__at(0).Cfg_mgr().Grps_get_or_load(Xof_fsdb_mgr_cfg.Grp_xowa);
			boolean use_thumb_w	= cfg_grp.Get_yn_or(Xof_fsdb_mgr_cfg.Key_upright_use_thumb_w, Bool_.N);
			boolean fix_default	= cfg_grp.Get_yn_or(Xof_fsdb_mgr_cfg.Key_upright_fix_default, Bool_.N);
			patch_upright_tid = Xof_patch_upright_tid_.Merge(use_thumb_w, fix_default);
		}
		else	// TEST: no cfg dbs
			patch_upright_tid = Xof_patch_upright_tid_.Tid_all;
	}
	public Fsm_abc_mgr	Mnts__at(int i) {return ary[i];}
	public Fsm_bin_fil	Bins__at(int mnt_id, int bin_db_id) {return ary[mnt_id].Bin_mgr().Get_at(bin_db_id);}
	public Fsd_fil_itm	Fil_select_bin(byte[] dir, byte[] fil, boolean is_thumb, int width, double thumbtime) {
		for (int i = 0; i < ary_len; i++) {
			Fsd_fil_itm rv = ary[i].Fil_select_bin(dir, fil, is_thumb, width, thumbtime);
			if (rv != Fsd_fil_itm.Null && rv.Db_bin_id() != Fsd_bin_tbl.Null_db_bin_id) {	// NOTE: mnt_0 can have thumb, but mnt_1 can have itm; check for itm with Db_bin_id; DATE:2013-11-16
				rv.Mnt_id_(i);
				return rv;
			}
		}
		return Fsd_fil_itm.Null;
	}
	public boolean Thm_select_bin(byte[] dir, byte[] fil, Fsd_thm_itm thm) {
		for (int i = 0; i < ary_len; i++) {
			boolean rv = ary[i].Thm_select_bin(dir, fil, thm);
			if (rv) {
				thm.Mnt_id_(i);
				return rv;
			}
		}
		return false;
	}
	public int Insert_to_mnt() {return insert_to_mnt;} public Fsm_mnt_mgr Insert_to_mnt_(int v) {insert_to_mnt = v; return this;} private int insert_to_mnt = Mnt_idx_user;
	public void Fil_insert(Fsd_fil_itm rv    , byte[] dir, byte[] fil, int ext_id, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		ary[insert_to_mnt].Fil_insert(rv, dir, fil, ext_id, modified, hash, bin_len, bin_rdr);
	}
	public void Thm_insert(Fsd_thm_itm rv, byte[] dir, byte[] fil, int ext_id, int w, int h, double thumbtime, int page, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		ary[insert_to_mnt].Thm_insert(rv, dir, fil, ext_id, w, h, thumbtime, page, modified, hash, bin_len, bin_rdr);
	}
	public void Img_insert(Fsd_img_itm rv, byte[] dir, byte[] fil, int ext_id, int img_w, int img_h, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		ary[insert_to_mnt].Img_insert(rv, dir, fil, ext_id, modified, hash, bin_len, bin_rdr, img_w, img_h);
	}
	public void Bin_db_max_(long v) {
		for (int i = 0; i < ary_len; i++)
			ary[i].Bin_mgr().Db_bin_max_(v);
	}
	public void Insert_to_bin_(int v) {
		for (int i = 0; i < ary_len; i++)
			ary[i].Bin_mgr().Insert_to_bin_(v);
	}
	public int Patch_upright() {return patch_upright_tid;} private int patch_upright_tid = Xof_patch_upright_tid_.Tid_all;
	public void Txn_open() {
		for (int i = 0; i < ary_len; i++)
			ary[i].Txn_open();
	}
	public void Txn_save() {
		for (int i = 0; i < ary_len; i++)
			ary[i].Txn_save();
	}
	public void Rls() {
		for (int i = 0; i < ary_len; i++)
			ary[i].Rls();
		cfg_tbl.Rls();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_bin_db_max_in_mb_))	this.Bin_db_max_(m.ReadLong("v") * Io_mgr.Len_mb);
		else if	(ctx.Match(k, Invk_insert_to_mnt_))		insert_to_mnt = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_insert_to_bin_))		this.Insert_to_bin_(m.ReadInt("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_bin_db_max_in_mb_ = "bin_db_max_in_mb_", Invk_insert_to_mnt_ = "insert_to_mnt_", Invk_insert_to_bin_ = "insert_to_bin_";
	public static final int Mnt_idx_main = 0, Mnt_idx_user = 1, Insert_to_bin_null = -1;
	public static void Patch(Fsm_mnt_mgr mnt_mgr) {
		mnt_mgr.Mnts__at(Fsm_mnt_mgr.Mnt_idx_main).Cfg_mgr()
			.Update(Xof_fsdb_mgr_cfg.Grp_xowa, Xof_fsdb_mgr_cfg.Key_gallery_fix_defaults	, "y")
			.Update(Xof_fsdb_mgr_cfg.Grp_xowa, Xof_fsdb_mgr_cfg.Key_gallery_packed			, "y")
			.Update(Xof_fsdb_mgr_cfg.Grp_xowa, Xof_fsdb_mgr_cfg.Key_upright_use_thumb_w		, "y")
			.Update(Xof_fsdb_mgr_cfg.Grp_xowa, Xof_fsdb_mgr_cfg.Key_upright_fix_default		, "y")
			;
	}
	private Fsm_mnt_itm[] Mnts__load_or_make(Db_conn_bldr_data conn_data, boolean version_is_1) {
		Db_conn conn = conn_data.Conn(); boolean created = conn_data.Created();
		cfg_tbl.Conn_(conn, created, version_is_1, Fsm_abc_mgr.Cfg_tbl_v1, Fsm_abc_mgr.Cfg_tbl_v2);
		mnt_tbl.Conn_(conn, created, version_is_1);
		if (created) cfg_tbl.Insert("core", "mnt.insert_idx", Int_.Xto_str(Mnt_idx_user));
		return mnt_tbl.Select_all();
	}
}

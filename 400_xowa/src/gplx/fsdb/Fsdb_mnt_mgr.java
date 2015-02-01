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
package gplx.fsdb; import gplx.*;
import gplx.core.primitives.*;
import gplx.dbs.*; import gplx.xowa.files.fsdb.*;
public class Fsdb_mnt_mgr implements GfoInvkAble {
	private Db_conn conn;
	private Fsdb_cfg_tbl tbl_cfg;
	private Fsdb_db_abc_mgr[] ary; int ary_len = 0;
	public Gfo_usr_dlg Usr_dlg() {return usr_dlg;} public Fsdb_mnt_mgr Usr_dlg_(Gfo_usr_dlg v) {usr_dlg = v; return this;} private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Null;
	public void Init(Io_url cur_dir) {
		Fsdb_mnt_itm[] mnts = Db_load_or_make(cur_dir);
		ary_len = mnts.length;
		ary = new Fsdb_db_abc_mgr[ary_len];
		for (int i = 0; i < ary_len; i++) {
			Fsdb_mnt_itm itm = mnts[i];
			Io_url abc_url = cur_dir.GenSubFil_nest(itm.Url(), "fsdb.abc.sqlite3");
			ary[i] = new Fsdb_db_abc_mgr(this).Init(abc_url.OwnerDir());
		}
		insert_to_mnt = tbl_cfg.Select_as_int_or_fail("core", "mnt.insert_idx");
	}
	public int Insert_to_mnt() {return insert_to_mnt;} public Fsdb_mnt_mgr Insert_to_mnt_(int v) {insert_to_mnt = v; return this;} private int insert_to_mnt = Mnt_idx_user;
	public int Abc_mgr_len() {return ary == null ? 0 : ary.length;}
	public Fsdb_db_abc_mgr Abc_mgr_at(int i) {return ary[i];}
	private Fsdb_mnt_itm[] Db_load_or_make(Io_url cur_dir) {
		Bool_obj_ref created = Bool_obj_ref.n_();
		conn = Sqlite_engine_.Conn_load_or_make_(cur_dir.GenSubFil("wiki.mnt.sqlite3"), created);
		tbl_cfg = new Fsdb_cfg_tbl_sql().Ctor(conn, created.Val());
		if (created.Val()) {
			Fsdb_mnt_tbl.Create_table(conn);
			Fsdb_mnt_tbl.Insert(conn, Mnt_idx_main, "fsdb.main", "fsdb.main");
			Fsdb_mnt_tbl.Insert(conn, Mnt_idx_user, "fsdb.user", "fsdb.user");
			tbl_cfg.Insert("core", "mnt.insert_idx", Int_.Xto_str(Mnt_idx_user));
		}
		return Fsdb_mnt_tbl.Select_all(conn);
	}
	public Fsdb_db_bin_fil Bin_db_get(int mnt_id, int bin_db_id) {
		return ary[mnt_id].Bin_mgr().Get_at(bin_db_id);
	}
	public Fsdb_fil_itm Fil_select_bin(byte[] dir, byte[] fil, boolean is_thumb, int width, double thumbtime) {
		for (int i = 0; i < ary_len; i++) {
			Fsdb_fil_itm rv = ary[i].Fil_select_bin(dir, fil, is_thumb, width, thumbtime);
			if (rv != Fsdb_fil_itm.Null && rv.Db_bin_id() != Fsdb_bin_tbl.Null_db_bin_id) {	// NOTE: mnt_0 can have thumb, but mnt_1 can have itm; check for itm with Db_bin_id; DATE:2013-11-16
				rv.Mnt_id_(i);
				return rv;
			}
		}
		return Fsdb_fil_itm.Null;
	}
	public boolean Thm_select_bin(byte[] dir, byte[] fil, Fsdb_xtn_thm_itm thm) {
		for (int i = 0; i < ary_len; i++) {
			boolean rv = ary[i].Thm_select_bin(dir, fil, thm);
			if (rv) {
				thm.Mnt_id_(i);
				return rv;
			}
		}
		return false;
	}
	public void Fil_insert(Fsdb_fil_itm rv    , byte[] dir, byte[] fil, int ext_id, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		ary[insert_to_mnt].Fil_insert(rv, dir, fil, ext_id, modified, hash, bin_len, bin_rdr);
	}
	public void Thm_insert(Fsdb_xtn_thm_itm rv, byte[] dir, byte[] fil, int ext_id, int w, int h, double thumbtime, int page, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		ary[insert_to_mnt].Thm_insert(rv, dir, fil, ext_id, w, h, thumbtime, page, modified, hash, bin_len, bin_rdr);
	}
	public void Img_insert(Fsdb_xtn_img_itm rv, byte[] dir, byte[] fil, int ext_id, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr, int img_w, int img_h) {
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
		tbl_cfg.Rls();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_bin_db_max_in_mb_))	this.Bin_db_max_(m.ReadLong("v") * Io_mgr.Len_mb);
		else if	(ctx.Match(k, Invk_insert_to_mnt_))		insert_to_mnt = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_insert_to_bin_))		this.Insert_to_bin_(m.ReadInt("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_bin_db_max_in_mb_ = "bin_db_max_in_mb_", Invk_insert_to_mnt_ = "insert_to_mnt_", Invk_insert_to_bin_ = "insert_to_bin_";
	public static final int Mnt_idx_main = 0, Mnt_idx_user = 1, Insert_to_bin_null = -1;
	public static void Patch(Fsdb_mnt_mgr mnt_mgr) {
		mnt_mgr.Abc_mgr_at(Fsdb_mnt_mgr.Mnt_idx_main).Cfg_mgr()
			.Update(Xof_fsdb_mgr_cfg.Grp_xowa, Xof_fsdb_mgr_cfg.Key_gallery_fix_defaults	, "y")
			.Update(Xof_fsdb_mgr_cfg.Grp_xowa, Xof_fsdb_mgr_cfg.Key_gallery_packed			, "y")
			.Update(Xof_fsdb_mgr_cfg.Grp_xowa, Xof_fsdb_mgr_cfg.Key_upright_use_thumb_w		, "y")
			.Update(Xof_fsdb_mgr_cfg.Grp_xowa, Xof_fsdb_mgr_cfg.Key_upright_fix_default		, "y")
			;
	}
}

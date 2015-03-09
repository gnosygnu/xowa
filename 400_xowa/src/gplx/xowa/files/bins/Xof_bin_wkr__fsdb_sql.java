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
package gplx.xowa.files.bins; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.primitives.*; import gplx.dbs.*; import gplx.ios.*; import gplx.cache.*; import gplx.xowa.files.fsdb.*;
import gplx.fsdb.data.*; import gplx.fsdb.meta.*;
public class Xof_bin_wkr__fsdb_sql implements Xof_bin_wkr, GfoInvkAble {
	private final Int_obj_ref tmp_itm_id = Int_obj_ref.neg1_(), tmp_bin_id = Int_obj_ref.neg1_(), tmp_mnt_id = Int_obj_ref.neg1_();
	private final Fsm_mnt_mgr mnt_mgr;
	public Xof_bin_wkr__fsdb_sql(Fsm_mnt_mgr mnt_mgr) {this.mnt_mgr = mnt_mgr;}
	public byte Tid() {return Xof_bin_wkr_.Tid_fsdb_xowa;}
	public boolean Resize_allowed() {return bin_wkr_resize;} public void Resize_allowed_(boolean v) {bin_wkr_resize = v;} private boolean bin_wkr_resize = false;		
	public Io_stream_rdr Get_as_rdr(Xof_fsdb_itm itm, boolean is_thumb, int w) {
		Find_ids(itm, is_thumb, w, tmp_itm_id, tmp_bin_id, tmp_mnt_id);
		int bin_db_id = tmp_bin_id.Val(); if (bin_db_id == Fsd_bin_tbl.Null_db_bin_id) return gplx.ios.Io_stream_rdr_.Null;
		Fsm_bin_fil bin_db = mnt_mgr.Bins__at(tmp_mnt_id.Val(), bin_db_id);
		return bin_db.Get_as_rdr(tmp_itm_id.Val());
	}
	public boolean Get_to_fsys(Xof_fsdb_itm itm, boolean is_thumb, int w, Io_url bin_url) {return Get_to_fsys(itm.Orig_repo_name(), itm.Lnki_ttl(), itm.Lnki_md5(), itm.Lnki_ext(), is_thumb, w, itm.Lnki_time(), itm.Lnki_page(), bin_url);}
	private boolean Get_to_fsys(byte[] orig_repo, byte[] orig_ttl, byte[] orig_md5, Xof_ext orig_ext, boolean lnki_is_thumb, int file_w, double lnki_time, int lnki_page, Io_url file_url) {
		Find_ids(orig_repo, orig_ttl, orig_ext.Id(), lnki_time, lnki_page, lnki_is_thumb, file_w, tmp_itm_id, tmp_bin_id, tmp_mnt_id);
		int bin_db_id = tmp_bin_id.Val(); if (bin_db_id == Fsd_bin_tbl.Null_db_bin_id) return false;
		Fsm_bin_fil bin_db = mnt_mgr.Bins__at(tmp_mnt_id.Val(), bin_db_id);
		return bin_db.Get_to_url(tmp_itm_id.Val(), file_url);
	}
	private void Find_ids(Xof_fsdb_itm itm, boolean is_thumb, int w, Int_obj_ref tmp_itm_id, Int_obj_ref tmp_bin_id, Int_obj_ref tmp_mnt_id) {Find_ids(itm.Orig_repo_name(), itm.Lnki_ttl(), itm.Lnki_ext().Id(), itm.Lnki_time(), itm.Lnki_page(), is_thumb, w, tmp_itm_id, tmp_bin_id, tmp_mnt_id);}
	private void Find_ids(byte[] orig_repo, byte[] orig_ttl, int orig_ext, double lnki_time, int lnki_page, boolean is_thumb, int w, Int_obj_ref tmp_itm_id, Int_obj_ref tmp_bin_id, Int_obj_ref tmp_mnt_id) {
		synchronized (tmp_bin_id) {
			byte[] dir = orig_repo, fil = orig_ttl;
			double time = Xof_lnki_time.Convert_to_fsdb_thumbtime(orig_ext, lnki_time, lnki_page);
			if (is_thumb) {
				Fsd_thm_itm thm_itm = Fsd_thm_itm.new_();
				thm_itm.Init_by_req(w, lnki_time, lnki_page);
				boolean found = mnt_mgr.Thm_select_bin(dir, fil, thm_itm);
				tmp_itm_id.Val_(thm_itm.Id());
				tmp_bin_id.Val_(found ? thm_itm.Db_bin_id() : Fsd_bin_tbl.Null_db_bin_id);
				tmp_mnt_id.Val_(thm_itm.Mnt_id());
			}
			else {
				Fsd_fil_itm fil_itm = mnt_mgr.Fil_select_bin(dir, fil, is_thumb, w, time);
				tmp_itm_id.Val_(fil_itm.Id());
				tmp_bin_id.Val_(fil_itm.Db_bin_id());
				tmp_mnt_id.Val_(fil_itm.Mnt_id());
			}
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.MatchIn(k, Invk_url_))		mnt_mgr.Init_by_wiki(m.ReadIoUrl("v"), Bool_.Y);
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_url_ = "url_";
}


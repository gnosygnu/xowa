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
import gplx.dbs.*; import gplx.ios.*; import gplx.cache.*; import gplx.xowa.files.fsdb.*;
import gplx.fsdb.*; import gplx.fsdb.data.*; import gplx.fsdb.meta.*;
public class Xof_bin_wkr__fsdb_sql implements Xof_bin_wkr {
	private final Xof_bin_wkr_ids tmp_ids = new Xof_bin_wkr_ids();
	Xof_bin_wkr__fsdb_sql(Fsm_mnt_mgr mnt_mgr) {this.mnt_mgr = mnt_mgr;}
	public byte Tid() {return Xof_bin_wkr_.Tid_fsdb_xowa;}
	public String Key() {return Xof_bin_wkr_.Key_fsdb_wiki;}
	public Fsm_mnt_mgr Mnt_mgr() {return mnt_mgr;} private final Fsm_mnt_mgr mnt_mgr;
	public boolean Resize_allowed() {return bin_wkr_resize;} public void Resize_allowed_(boolean v) {bin_wkr_resize = v;} private boolean bin_wkr_resize = false;		
	public Io_stream_rdr Get_as_rdr(Xof_fsdb_itm itm, boolean is_thumb, int w) {
		Find_ids(itm, is_thumb, w);
		int bin_db_id = tmp_ids.Bin_db_id(); if (bin_db_id == Fsd_bin_tbl.Bin_db_id_null) return gplx.ios.Io_stream_rdr_.Null;
		Fsm_bin_fil bin_db = mnt_mgr.Bins__at(tmp_ids.Mnt_id(), bin_db_id);
		return bin_db.Select_as_rdr(tmp_ids.Itm_id());
	}
	public boolean Get_to_fsys(Xof_fsdb_itm itm, boolean is_thumb, int w, Io_url bin_url) {return Get_to_fsys(itm.Orig_repo_name(), itm.Lnki_ttl(), itm.Lnki_md5(), itm.Lnki_ext(), is_thumb, w, itm.Lnki_time(), itm.Lnki_page(), bin_url);}
	private boolean Get_to_fsys(byte[] orig_repo, byte[] orig_ttl, byte[] orig_md5, Xof_ext orig_ext, boolean lnki_is_thumb, int file_w, double lnki_time, int lnki_page, Io_url file_url) {
		Find_ids(orig_repo, orig_ttl, orig_ext.Id(), lnki_time, lnki_page, lnki_is_thumb, file_w);
		int bin_db_id = tmp_ids.Bin_db_id(); if (bin_db_id == Fsd_bin_tbl.Bin_db_id_null) return false;
		Fsm_bin_fil bin_db = mnt_mgr.Bins__at(tmp_ids.Mnt_id(), bin_db_id);
		return bin_db.Select_to_url(tmp_ids.Itm_id(), file_url);
	}
	private void Find_ids(Xof_fsdb_itm itm, boolean is_thumb, int w) {Find_ids(itm.Orig_repo_name(), itm.Lnki_ttl(), itm.Lnki_ext().Id(), itm.Lnki_time(), itm.Lnki_page(), is_thumb, w);}
	private void Find_ids(byte[] orig_repo, byte[] orig_ttl, int orig_ext, double lnki_time, int lnki_page, boolean is_thumb, int w) {
		synchronized (tmp_ids) {
			byte[] dir = orig_repo, fil = orig_ttl;
			double time = Xof_lnki_time.Convert_to_fsdb_thumbtime(orig_ext, lnki_time, lnki_page);
			if (is_thumb) {
				Fsd_thm_itm thm_itm = Fsd_thm_itm.new_();
				thm_itm.Init_by_req(w, lnki_time, lnki_page);
				boolean found = Select_thm_bin(thm_itm, dir, fil);
				tmp_ids.Init_by_thm(found, thm_itm);
			}
			else {
				Fsd_fil_itm fil_itm = Select_fil_bin(dir, fil, is_thumb, w, time);
				tmp_ids.Init_by_fil(fil_itm);
			}
		}
	}
	private Fsd_fil_itm	Select_fil_bin(byte[] dir, byte[] fil, boolean is_thumb, int width, double thumbtime) {
		int len = mnt_mgr.Mnts__len();
		for (int i = 0; i < len; i++) {
			Fsd_fil_itm rv = mnt_mgr.Mnts__get_at(i).Select_fil_or_null(dir, fil, is_thumb, width, thumbtime);
			if (	rv != Fsd_fil_itm.Null 
				&&	rv.Bin_db_id() != Fsd_bin_tbl.Bin_db_id_null) {	// NOTE: mnt_0 can have thumb, but mnt_1 can have itm; check for itm with Db_bin_id; DATE:2013-11-16
				return rv;
			}
		}
		return Fsd_fil_itm.Null;
	}
	private boolean Select_thm_bin(Fsd_thm_itm rv, byte[] dir, byte[] fil) {
		int len = mnt_mgr.Mnts__len();
		for (int i = 0; i < len; i++) {
			boolean exists = mnt_mgr.Mnts__get_at(i).Select_thm(rv, dir, fil);
			if (exists) return true;
		}
		return false;
	}
	public void Txn_bgn() {mnt_mgr.Mnts__get_insert().Txn_bgn();}
	public void Txn_end() {mnt_mgr.Mnts__get_insert().Txn_end();}
        public static Xof_bin_wkr__fsdb_sql new_(Fsm_mnt_mgr mnt_mgr) {return new Xof_bin_wkr__fsdb_sql(mnt_mgr);}
}
class Xof_bin_wkr_ids {
	public Xof_bin_wkr_ids() {this.Clear();}
	public int Mnt_id() {return mnt_id;} private int mnt_id;
	public int Bin_db_id() {return bin_db_id;} private int bin_db_id;
	public int Itm_id() {return itm_id;} private int itm_id;
	public void Init_by_thm(boolean found, Fsd_thm_itm thm) {
		if (found) {
			this.mnt_id = thm.Mnt_id();
			this.bin_db_id = thm.Db_bin_id();
			this.itm_id = thm.Thm_id();
		}
		else
			this.Clear();
	}
	public void Init_by_fil(Fsd_fil_itm fil) {
		if (fil == Fsd_fil_itm.Null)
			this.Clear();
		else {
			this.mnt_id = fil.Mnt_id();
			this.bin_db_id = fil.Bin_db_id();
			this.itm_id = fil.Fil_id();
		}
	}
	private void Clear() {
		this.mnt_id = -1;
		this.bin_db_id = Fsd_bin_tbl.Bin_db_id_null;
		this.itm_id = -1;
	}
}

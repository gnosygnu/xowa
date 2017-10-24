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
package gplx.xowa.files.bins; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*; import gplx.core.ios.*; import gplx.core.ios.streams.*; import gplx.core.caches.*; import gplx.xowa.files.fsdb.*;
import gplx.fsdb.*; import gplx.fsdb.data.*; import gplx.fsdb.meta.*;
public class Xof_bin_wkr__fsdb_sql implements Xof_bin_wkr {
	private final    Xof_bin_wkr_ids tmp_ids = new Xof_bin_wkr_ids();
	private Xof_bin_skip_mgr skip_mgr;
	Xof_bin_wkr__fsdb_sql(Fsm_mnt_mgr mnt_mgr) {this.mnt_mgr = mnt_mgr;}
	public byte Tid() {return Xof_bin_wkr_.Tid_fsdb_xowa;}
	public String Key() {return Xof_bin_wkr_.Key_fsdb_wiki;}
	public Fsm_mnt_mgr Mnt_mgr() {return mnt_mgr;} private final    Fsm_mnt_mgr mnt_mgr;
	public boolean Resize_allowed() {return bin_wkr_resize;} public void Resize_allowed_(boolean v) {bin_wkr_resize = v;} private boolean bin_wkr_resize = false;		
	public Xof_bin_skip_mgr Skip_mgr() {return skip_mgr;}
	public void Skip_mgr_init(Fsm_cfg_mgr cfg_mgr, String[] wkrs) {this.skip_mgr = new Xof_bin_skip_mgr(cfg_mgr, wkrs);}
	public Io_stream_rdr Get_as_rdr(Xof_fsdb_itm fsdb, boolean is_thumb, int w) {
		Find_ids(fsdb, is_thumb, w);
		int bin_db_id = tmp_ids.Bin_db_id(); if (bin_db_id == Fsd_bin_tbl.Bin_db_id_null) return Io_stream_rdr_.Noop;
		Fsm_bin_fil bin_db = mnt_mgr.Bins__at(tmp_ids.Mnt_id(), bin_db_id);
		Io_stream_rdr rdr = bin_db.Select_as_rdr(tmp_ids.Itm_id());
		if (skip_mgr != null && skip_mgr.Skip(fsdb, rdr)) return Io_stream_rdr_.Noop;
		return rdr;
	}
	public boolean Get_to_fsys(Xof_fsdb_itm itm, boolean is_thumb, int w, Io_url bin_url) {return Get_to_fsys(itm.Orig_repo_name(), itm.Orig_ttl(), itm.Orig_ext(), is_thumb, w, itm.Lnki_time(), itm.Lnki_page(), bin_url);}
	private boolean Get_to_fsys(byte[] orig_repo, byte[] orig_ttl, Xof_ext orig_ext, boolean lnki_is_thumb, int file_w, double lnki_time, int lnki_page, Io_url file_url) {
		Find_ids(orig_repo, orig_ttl, orig_ext.Id(), lnki_time, lnki_page, lnki_is_thumb, file_w);
		int bin_db_id = tmp_ids.Bin_db_id(); if (bin_db_id == Fsd_bin_tbl.Bin_db_id_null) return false;
		Fsm_bin_fil bin_db = mnt_mgr.Bins__at(tmp_ids.Mnt_id(), bin_db_id);
		return bin_db.Select_to_url(tmp_ids.Itm_id(), file_url);
	}
	public Io_stream_rdr Get_to_fsys_near(Xof_fsdb_itm rv, byte[] orig_repo, byte[] orig_ttl, Xof_ext orig_ext, double lnki_time, int lnki_page) {
		Fsd_thm_itm thm_itm = Fsd_thm_itm.new_();
		thm_itm.Init_by_req(Int_.Max_value, lnki_time, lnki_page);
		boolean found = Select_thm_bin(Bool_.N, thm_itm, orig_repo, orig_ttl);
		if (found) {
			tmp_ids.Init_by_thm(found, thm_itm);
			rv.Init_by_fsdb_near(Bool_.N, thm_itm.W());
		}
		else {
			Fsd_fil_itm fil_itm = Select_fil_bin(orig_repo, orig_ttl);		// find orig
			if (fil_itm == Fsd_fil_itm.Null) return Io_stream_rdr_.Noop;
			tmp_ids.Init_by_fil(fil_itm);
			rv.Init_by_fsdb_near(Bool_.Y, rv.Orig_w());
		}
		Fsm_bin_fil bin_db = mnt_mgr.Bins__at(tmp_ids.Mnt_id(), tmp_ids.Bin_db_id());
		return bin_db.Select_as_rdr(tmp_ids.Itm_id());
	}
	private void Find_ids(Xof_fsdb_itm itm, boolean is_thumb, int w) {Find_ids(itm.Orig_repo_name(), itm.Orig_ttl(), itm.Orig_ext().Id(), itm.Lnki_time(), itm.Lnki_page(), is_thumb, w);}
	private void Find_ids(byte[] orig_repo, byte[] orig_ttl, int orig_ext, double lnki_time, int lnki_page, boolean is_thumb, int w) {
		synchronized (tmp_ids) {
			byte[] dir = orig_repo, fil = orig_ttl;
			if (is_thumb) {
				Fsd_thm_itm thm_itm = Fsd_thm_itm.new_();
				thm_itm.Init_by_req(w, lnki_time, lnki_page);
				boolean found = Select_thm_bin(Bool_.Y, thm_itm, dir, fil);
				tmp_ids.Init_by_thm(found, thm_itm);
			}
			else {
				Fsd_fil_itm fil_itm = Select_fil_bin(dir, fil);
				tmp_ids.Init_by_fil(fil_itm);
			}
		}
	}
	private Fsd_fil_itm	Select_fil_bin(byte[] dir, byte[] fil) {
		int len = mnt_mgr.Mnts__len();
		for (int i = 0; i < len; i++) {
			Fsd_fil_itm rv = mnt_mgr.Mnts__get_at(i).Select_fil_or_null(dir, fil);
			if (	rv != Fsd_fil_itm.Null 
				&&	rv.Bin_db_id() != Fsd_bin_tbl.Bin_db_id_null) {	// NOTE: mnt_0 can have thumb, but mnt_1 can have itm; check for itm with Db_bin_id; DATE:2013-11-16
				return rv;
			}
		}
		return Fsd_fil_itm.Null;
	}
	private boolean Select_thm_bin(boolean exact, Fsd_thm_itm rv, byte[] dir, byte[] fil) {
		int len = mnt_mgr.Mnts__len();
		for (int i = 0; i < len; i++) {
			boolean exists = mnt_mgr.Mnts__get_at(i).Select_thm(exact, rv, dir, fil);
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
			this.bin_db_id = thm.Bin_db_id();
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

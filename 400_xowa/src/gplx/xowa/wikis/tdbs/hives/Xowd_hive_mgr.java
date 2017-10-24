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
package gplx.xowa.wikis.tdbs.hives; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.tdbs.xdats.*;
public class Xowd_hive_mgr {
	public Xowd_hive_mgr(Xowe_wiki wiki, byte dir_tid) {
		this.wiki = wiki; fsys_mgr = wiki.Tdb_fsys_mgr(); this.dir_tid = dir_tid;
		dir_tid_reg = dir_tid == Xotdb_dir_info_.Tid_page ? Xotdb_dir_info_.Tid_ttl : dir_tid; 
	} Xowe_wiki wiki; Xotdb_fsys_mgr fsys_mgr; Xowd_regy_mgr reg_mgr; byte dir_tid;
	byte dir_tid_reg;
	public void Create(Xow_ns ns, byte[] key, byte[] data, gplx.core.lists.ComparerAble comparer) {
		if (reg_mgr == null) reg_mgr = new Xowd_regy_mgr(fsys_mgr.Url_ns_reg(ns.Num_str(), dir_tid_reg));
		int fil_idx = 0;
		if (reg_mgr.Files_ary().length == 0) {
			reg_mgr.Create(key);
			fil_idx = 0;
		}
		else {
			fil_idx = reg_mgr.Files_find(key);
			reg_mgr.Update_add(fil_idx, key);
		}
		Io_url url = fsys_mgr.Url_ns_fil(dir_tid, ns.Id(), fil_idx);
		byte[] bry = Io_mgr.Instance.LoadFilBry(url);
		Xob_xdat_file xdat = new Xob_xdat_file();
		if (bry != Bry_.Empty)
			xdat.Parse(bry, bry.length, url);
		Bry_bfr tmp = wiki.Utl__bfr_mkr().Get_m001();
		xdat.Insert(tmp, data);
		if (comparer != null)
			xdat.Sort(tmp, comparer);
		tmp.Mkr_rls();
		xdat.Save(url);
		reg_mgr.Save();
	}
	public void Create(byte[] key, byte[] data, gplx.core.lists.ComparerAble comparer) {
		if (reg_mgr == null) reg_mgr = new Xowd_regy_mgr(fsys_mgr.Url_site_reg(dir_tid));
		int fil_idx = 0;
		if (reg_mgr.Files_ary().length == 0) {
			reg_mgr.Create(key);
			fil_idx = 0;
		}
		else {
			fil_idx = reg_mgr.Files_find(key);
			reg_mgr.Update_add(fil_idx, key);
		}
		Io_url url = fsys_mgr.Url_site_fil(dir_tid, fil_idx);
		byte[] bry = Io_mgr.Instance.LoadFilBry(url);
		Xob_xdat_file xdat = new Xob_xdat_file();
		if (bry != Bry_.Empty)
			xdat.Parse(bry, bry.length, url);
		Bry_bfr tmp = wiki.Utl__bfr_mkr().Get_m001();
		xdat.Insert(tmp, data);
		if (comparer != null)
			xdat.Sort(tmp, comparer);
		tmp.Mkr_rls();
		xdat.Save(url);
		reg_mgr.Save();
	}
	public void Update(Xow_ns ns, byte[] old_key, byte[] new_key, byte[] data, int lkp_bgn, byte lkp_dlm, boolean exact, boolean sort) {
		if (reg_mgr == null) reg_mgr = new Xowd_regy_mgr(fsys_mgr.Url_ns_reg(ns.Num_str(), Xotdb_dir_info_.Tid_ttl));
		int fil_idx = reg_mgr.Files_find(old_key);
		boolean reg_save = false;
		if (new_key != null)
			reg_save = reg_mgr.Update_change(fil_idx, old_key, new_key);
		Io_url url = fsys_mgr.Url_ns_fil(dir_tid, ns.Id(), fil_idx);
		byte[] bry = Io_mgr.Instance.LoadFilBry(url);
		Xob_xdat_file xdat = new Xob_xdat_file();
		if (bry != Bry_.Empty)
			xdat.Parse(bry, bry.length, url);
		Bry_bfr tmp = wiki.Utl__bfr_mkr().Get_m001();
		Xob_xdat_itm itm = new Xob_xdat_itm(); 
		xdat.Find(itm, old_key, lkp_bgn, lkp_dlm, exact);
		if (itm.Missing()) return;
		xdat.Update(tmp, itm, data);
		if (sort) xdat.Sort(tmp, new Bry_comparer_bgn_eos(lkp_bgn));
		tmp.Mkr_rls();
		xdat.Save(url);
		if (reg_save) reg_mgr.Save();
	}
}

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
import gplx.core.ios.zips.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.bldrs.sql_dumps.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.specials.*; import gplx.xowa.specials.allPages.*;
import gplx.xowa.wikis.tdbs.xdats.*;
public class Xob_hive_mgr {
	public Xob_hive_mgr(Xowe_wiki wiki) {this.wiki = wiki; this.fsys_mgr = wiki.Tdb_fsys_mgr();} private Xowe_wiki wiki; Xotdb_fsys_mgr fsys_mgr;
	public void Clear() {regy.Clear(); xdat.Clear();}
	public void Special_allpages_query(Xows_page_allpages mgr, Xow_ns ns, byte[] key, int count, boolean include_redirects) {
		byte dir_tid = Xotdb_dir_info_.Tid_ttl;
		int xdat_idx = Regy__find_file_ns(key, dir_tid, ns.Num_str());
		Xob_xdat_file xdat_main = new Xob_xdat_file();
		xdat_main = xdat_load_(xdat_main, dir_tid, ns, xdat_idx);
		xdat_main.Find(xdat_itm, key, Xotdb_page_itm_.Txt_ttl_pos, Byte_ascii.Tab, false);
		int itm_idx = xdat_itm.Itm_idx();
		Special_allpages_query_fwd(mgr, dir_tid, ns, include_redirects, count, xdat_idx, itm_idx    , xdat_main);
		Special_allpages_query_bwd(mgr, dir_tid, ns, include_redirects, count, xdat_idx, itm_idx - 1, xdat_main);
	}
	Xob_xdat_file xdat_load_(Xob_xdat_file xdat, byte dir_tid, Xow_ns ns, int fil_idx) {
		Io_url xdat_url = fsys_mgr.Url_ns_fil(dir_tid, ns.Id(), fil_idx);
		byte[] xdat_bry = Io_mgr.Instance.LoadFilBry(xdat_url);
		xdat.Parse(xdat_bry, xdat_bry.length, xdat_url);
		return xdat;
	}
	private void Special_allpages_query_fwd(Xows_page_allpages mgr, byte dir_tid, Xow_ns ns, boolean include_redirects, int total, int fil_idx, int row_idx, Xob_xdat_file xdat_file) {
		int count = 0; ++total;
		boolean loop = true;
		int regy_len = regy.Files_ary().length;
		int rslt_list_len = mgr.Rslt_list_len();
		Xowd_page_itm[] rslt_list_ttls = mgr.Rslt_list_ttls();
		Xowd_page_itm nxt_itm = null;
		while (loop) {
			if (fil_idx == regy_len) break;
			if (xdat_file == null) {
				xdat_file = xdat_load_(this.xdat, dir_tid, ns, fil_idx);
				row_idx = 0;
			}
			int rows_len = xdat_file.Count();
			for (; row_idx < rows_len; row_idx++) {
				xdat_file.GetAt(xdat_itm, row_idx);
				Xowd_page_itm ttl_itm = Xotdb_page_itm_.Txt_ttl_load(Bry_.Mid(xdat_itm.Src(), xdat_itm.Itm_bgn(), xdat_itm.Itm_end()));
				if (!include_redirects && ttl_itm.Redirected()) continue;
				++count;
				nxt_itm = ttl_itm;
				if (count == total) {
					loop = false;
					break;
				}
				else
					rslt_list_ttls[rslt_list_len++] = ttl_itm;
			}
			xdat_file = null;
			++fil_idx;
		}
		mgr.Rslt_list_len_(rslt_list_len);
		mgr.Rslt_nxt_(nxt_itm);
	}
	private void Special_allpages_query_bwd(Xows_page_allpages mgr, byte dir_tid, Xow_ns ns, boolean include_redirects, int total, int fil_idx, int row_idx, Xob_xdat_file xdat_file) {
		if (row_idx < 0) {
			--fil_idx;
			row_idx = -1;
		}
		int count = 0;
		boolean loop = true;
		Xowd_page_itm prv_itm = null;
		while (loop) {
			if (fil_idx == -1) break;
			if (xdat_file == null) {
				xdat_file = xdat_load_(this.xdat, dir_tid, ns, fil_idx);
				row_idx = -1;
			}
			if (row_idx == -1)
				row_idx = xdat_file.Count() - 1;
			for (; row_idx > -1; row_idx--) {
				xdat_file.GetAt(xdat_itm, row_idx);
				Xowd_page_itm ttl_itm = Xotdb_page_itm_.Txt_ttl_load(Bry_.Mid(xdat_itm.Src(), xdat_itm.Itm_bgn(), xdat_itm.Itm_end()));
				if (!include_redirects && ttl_itm.Redirected()) continue;
//				list.Add(ttl_itm);
				++count;
				prv_itm = ttl_itm;
				if (count == total) {					
					loop = false;
					break;
				}
				else {
//					rslt_list_ttls[rslt_list_len++] = ttl_itm;
				}
			}
			xdat_file = null;
			--fil_idx;
		}
		if (prv_itm == null) prv_itm = mgr.Rslt_list_ttls()[0];
		mgr.Rslt_prv_(prv_itm);			
	}
	public void Find_bgn(List_adp list, Xow_ns ns, byte[] key, int count, boolean include_redirects) {
		byte dir_tid = Xotdb_dir_info_.Tid_ttl;
		int xdat_idx = Regy__find_file_ns(key, dir_tid, ns.Num_str());
		Io_url xdat_url = fsys_mgr.Url_ns_fil(dir_tid, ns.Id(), xdat_idx);
		byte[] xdat_bry = Io_mgr.Instance.LoadFilBry(xdat_url);
		xdat.Parse(xdat_bry, xdat_bry.length, xdat_url);
		xdat.Find(xdat_itm, key, Xotdb_page_itm_.Txt_ttl_pos, Byte_ascii.Tab, false);
		Find_nearby_add_fwd(list, dir_tid, ns, include_redirects, count, xdat_idx, xdat_itm.Itm_idx());
	}	private Xob_xdat_itm xdat_itm = new Xob_xdat_itm(); //Int_2_ref find_nearby_rslt = new Int_2_ref();
//		private void Find_nearby_add_bwd(List_adp list, byte dir_tid, Xow_ns ns, boolean include_redirects, int total, int fil_bgn, int row_bgn) {
//			if (--row_bgn < 0) {
//				--fil_bgn;
//				row_bgn = -1;
//			}
//			int fil_idx = fil_bgn;
//			boolean first = true;
//			int count = 0;
//			boolean loop = true;
//			while (loop) {
//				if (fil_idx == -1) break;
//				Io_url xdat_url = fsys_mgr.Url_ns_fil(dir_tid, ns.Id(), fil_idx);
//				byte[] xdat_bry = Io_mgr.Instance.LoadFilBry(xdat_url);
//				xdat.Parse(xdat_bry, xdat_bry.length, xdat_url);
//				int row_idx = first && row_bgn != -1 ? row_bgn : xdat.Count() - 1;
//				first = false;
//				for (; row_idx > -1; row_idx--) {
//					xdat.GetAt(xdat_itm, row_idx);
//					Xowd_page_itm ttl_itm = Xotdb_page_itm_.Txt_ttl_load(Bry_.Mid(xdat_itm.Src(), xdat_itm.Itm_bgn(), xdat_itm.Itm_end()));
//					if (!include_redirects && ttl_itm.Type_redirect()) continue;
//					list.Add(ttl_itm);
//					if (++count == total) {loop = false; break;}
//				}
//				--fil_idx;
//			}
//		}
	private void Find_nearby_add_fwd(List_adp list, byte dir_tid, Xow_ns ns, boolean include_redirects, int total, int fil_bgn, int row_bgn) {
		int fil_idx = fil_bgn;
		boolean first = true;
		int count = 0;
		boolean loop = true;
		int regy_len = regy.Files_ary().length;
		while (loop) {
			if (fil_idx == regy_len) break;
			Io_url xdat_url = fsys_mgr.Url_ns_fil(dir_tid, ns.Id(), fil_idx);
			byte[] xdat_bry = Io_mgr.Instance.LoadFilBry(xdat_url);
			xdat.Parse(xdat_bry, xdat_bry.length, xdat_url);
			int row_idx = first ? row_bgn : 0;
			int rows_len = xdat.Count();
			first = false;
			for (; row_idx < rows_len; row_idx++) {
				xdat.GetAt(xdat_itm, row_idx);
				Xowd_page_itm ttl_itm = Xotdb_page_itm_.Txt_ttl_load(Bry_.Mid(xdat_itm.Src(), xdat_itm.Itm_bgn(), xdat_itm.Itm_end()));
				if (!include_redirects && ttl_itm.Redirected()) continue;
				list.Add(ttl_itm);
				if (++count == total) {loop = false; break;}
			}
			++fil_idx;
		}
	}
	public void Create(byte dir_tid, byte[] key, byte[] row) {	// Ctg_0; Ctg_0|!!!!"|!!!!#
		int xdat_idx = Regy__find_file(key, dir_tid);
		if (xdat_idx == Xowd_regy_mgr.Regy_null) {	// no entries in regy; create at least one; EX: "" -> "0|A|A|1"
			regy.Create(key);
			xdat_idx = 0;
		}
		else
			regy.Update_add(0, key);
		regy.Save();
		Xdat__create_row(dir_tid, key, row, xdat_idx);
	}
	int Regy__find_file(byte[] key, byte dir_tid)					{return Regy__find_file_by_url(key, fsys_mgr.Url_site_reg(dir_tid));}
	int Regy__find_file_ns(byte[] key, byte dir_tid, String ns_num) {return Regy__find_file_by_url(key, fsys_mgr.Url_ns_reg(ns_num, Xotdb_dir_info_.Tid_ttl));}
	int Regy__find_file_by_url(byte[] key, Io_url regy_url) {regy.Init(regy_url); return regy.Files_find(key);} private Xowd_regy_mgr regy = new Xowd_regy_mgr();
	private void Xdat__create_row(byte dir_tid, byte[] key, byte[] row, int xdat_idx) {
		Io_url xdat_url = fsys_mgr.Url_site_fil(dir_tid, xdat_idx);
		byte[] xdat_bry = Io_mgr.Instance.LoadFilBry(xdat_url);
		Xob_xdat_file xdat_fil = new Xob_xdat_file();
		if (xdat_bry.length > 0)	// if file is not empty, load it and parse it
			xdat_fil.Parse(xdat_bry, xdat_bry.length, xdat_url);
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_m001();
		xdat_fil.Insert(tmp_bfr, row);
		xdat_fil.Save(xdat_url);
		tmp_bfr.Mkr_rls();
	}	private Xob_xdat_file xdat = new Xob_xdat_file(); Io_zip_mgr zip_mgr = Io_zip_mgr_base.Instance;
}

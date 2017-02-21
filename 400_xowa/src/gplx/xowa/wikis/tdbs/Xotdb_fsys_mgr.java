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
package gplx.xowa.wikis.tdbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.bldrs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.tdbs.utils.*;
public class Xotdb_fsys_mgr {
	private final    Io_url root_dir; private final    Xow_ns_mgr ns_mgr;
	public Xotdb_fsys_mgr(Io_url root_dir, Xow_ns_mgr ns_mgr) {
		this.root_dir = root_dir; this.ns_mgr = ns_mgr;
		this.tmp_dir		= root_dir.GenSubDir("tmp");			
		this.ns_dir			= root_dir.GenSubDir(Xotdb_dir_info_.Name_ns);
		this.site_dir		= root_dir.GenSubDir(Xotdb_dir_info_.Name_site);
	}
	public Io_url Tmp_dir()				{return tmp_dir;}	private final    Io_url tmp_dir;		
	public Io_url Ns_dir()				{return ns_dir;}	private final    Io_url ns_dir;
	public Io_url Site_dir()			{return site_dir;}	private final    Io_url site_dir;
	public Io_url Cfg_wiki_core_fil()	{return root_dir.GenSubFil_nest(Const_url_cfg, "wiki_core.gfs");}
	public Io_url Cfg_wiki_stats_fil()	{return root_dir.GenSubFil_nest(Const_url_cfg, "wiki_stats.gfs");}
	public Xotdb_dir_info[] Tdb_dir_regy()	{return dir_regy;} private final    Xotdb_dir_info[] dir_regy = Xotdb_dir_info_.regy_();
	public Io_url Url_ns_dir(String ns_num, byte tid)	{return ns_dir.GenSubDir_nest(ns_num, Xotdb_dir_info_.Tid_name(tid));}
	public Io_url Url_ns_reg(String ns_num, byte tid)	{return ns_dir.GenSubFil_nest(ns_num, Xotdb_dir_info_.Tid_name(tid), Xotdb_dir_info_.Name_reg_fil);}
	public Io_url Url_ns_fil(byte tid, int ns_id, int fil_idx) {
		Xotdb_dir_info dir_info = dir_regy[tid];
		String dir_name = dir_info.Name() + Xotdb_dir_info.Wtr_dir(dir_info.Ext_tid());
		return Xotdb_fsys_mgr.Url_fil(ns_dir.GenSubDir_nest(Int_.To_str_pad_bgn_zero(ns_id, 3), dir_name), fil_idx, dir_regy[tid].Ext_bry());
	}
	public Io_url Url_site_fil(byte tid, int fil_idx)	{return Xotdb_fsys_mgr.Url_fil(Url_site_dir(tid), fil_idx, Xotdb_dir_info_.Bry_xdat);}
	public Io_url Url_site_reg(byte tid)				{return Url_site_dir(tid).GenSubFil(Xotdb_dir_info_.Name_reg_fil);}
	public Io_url Url_site_dir(byte tid) {
		switch (tid) {
			case Xotdb_dir_info_.Tid_category2_link:		return site_dir.GenSubDir_nest(Xotdb_dir_info_.Name_category2, Xotdb_dir_info_.Name_category2_link);
			case Xotdb_dir_info_.Tid_category2_main:		return site_dir.GenSubDir_nest(Xotdb_dir_info_.Name_category2, Xotdb_dir_info_.Name_category2_main);
			default:										return site_dir.GenSubDir_nest(Xotdb_dir_info_.Tid_name(tid));
		}
	}
	public void Scan_dirs() {
		Scan_dirs_zip(this, Xotdb_dir_info_.Tid_page);
		Scan_dirs_ns(ns_dir, ns_mgr);
	}
	private static void Scan_dirs_zip(Xotdb_fsys_mgr fsys_mgr, byte id) {
		Io_url[] dirs = Io_mgr.Instance.QueryDir_args(fsys_mgr.Ns_dir().GenSubDir_nest("000")).FilPath_("*page*").DirOnly_().Recur_(false).ExecAsUrlAry();
		int len = dirs.length;
		byte tid = gplx.core.ios.streams.Io_stream_tid_.Tid__raw;	// needed for Xoa_xowa_exec_tst
		for (int i = 0; i < len; i++) {
			Io_url dir = dirs[i];
			String dir_name = dir.NameOnly();
			if		(String_.Eq(dir_name, "page"))			{tid = gplx.core.ios.streams.Io_stream_tid_.Tid__raw; break;} 
			else if	(String_.Eq(dir_name, "page_zip"))		tid = gplx.core.ios.streams.Io_stream_tid_.Tid__zip;
			else if	(String_.Eq(dir_name, "page_gz"))		tid = gplx.core.ios.streams.Io_stream_tid_.Tid__gzip;
			else if	(String_.Eq(dir_name, "page_bz2"))		tid = gplx.core.ios.streams.Io_stream_tid_.Tid__bzip2;
		}
		fsys_mgr.Tdb_dir_regy()[id].Ext_tid_(tid);
	}
	private static Hash_adp Scan_dirs_ns(Io_url ns_dir, Xow_ns_mgr ns_mgr) {
		Io_url[] ns_dirs = Io_mgr.Instance.QueryDir_args(ns_dir).Recur_(false).DirOnly_().ExecAsUrlAry();
		int len = ns_dirs.length;
		Hash_adp rv = Hash_adp_.New();
		for (int i = 0; i < len; i++) {
			int ns_int = Int_.parse_or(ns_dirs[i].NameOnly(), Int_.Min_value); if (ns_int == Int_.Min_value) continue; 
			Xow_ns ns = ns_mgr.Ids_get_or_null(ns_int); if (ns == null) continue;
			ns.Exists_(true);
		}
		return rv;
	}
	public static Io_url Url_fil(Io_url root_dir, int fil_idx, byte[] ext) {return Xos_url_gen.bld_fil_(root_dir, fil_idx, ext);} private static final String Const_url_cfg = "cfg";

}

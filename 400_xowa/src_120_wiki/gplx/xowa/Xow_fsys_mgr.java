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
package gplx.xowa; import gplx.*;
public class Xow_fsys_mgr {
	public Xow_fsys_mgr(Xow_wiki wiki, Io_url root_dir) {
		this.wiki = wiki; this.root_dir = root_dir;
		ns_dir		= root_dir.GenSubDir(Xow_dir_info_.Name_ns);
		site_dir	= root_dir.GenSubDir(Xow_dir_info_.Name_site);
		tmp_dir		= root_dir.GenSubDir("tmp");
		file_dir	= wiki.App().Fsys_mgr().File_dir().GenSubDir_nest(wiki.Domain_str());
		file_dir_bry_len = file_dir.To_http_file_bry().length;
	}	private Xow_wiki wiki;
	public Io_url Root_dir()		{return root_dir;}	private Io_url root_dir;
	public Io_url Ns_dir()			{return ns_dir;}	private Io_url ns_dir;
	public Io_url Site_dir()		{return site_dir;}	private Io_url site_dir;
	public Io_url File_dir()		{return file_dir;}	private Io_url file_dir;
	public int File_dir_bry_len()	{return file_dir_bry_len;} private int file_dir_bry_len;
	public Io_url Tmp_dir()			{return tmp_dir;}	public void Tmp_dir_(Io_url v) {tmp_dir = v;} Io_url tmp_dir;		
	public Io_url Cfg_wiki_core_fil()	{return root_dir.GenSubFil_nest(Const_url_cfg, "wiki_core.gfs");}
	public Io_url Cfg_wiki_stats_fil()	{return root_dir.GenSubFil_nest(Const_url_cfg, "wiki_stats.gfs");}
	public Xow_dir_info[] Dir_regy() {return dir_regy;} private Xow_dir_info[] dir_regy = Xow_dir_info_.regy_();
	public Io_url Url_ns_dir(String ns_num, byte tid)	{return ns_dir.GenSubDir_nest(ns_num, Xow_dir_info_.Tid_name(tid));}
	public Io_url Url_ns_reg(String ns_num, byte tid)	{return ns_dir.GenSubFil_nest	(ns_num , Xow_dir_info_.Tid_name(tid)	, Xow_dir_info_.Name_reg_fil);}
	public Io_url Url_ns_fil(byte tid, int ns_id, int fil_idx) {
		Xow_dir_info dir_info = dir_regy[tid];
		String dir_name = dir_info.Name() + Xow_fsys_mgr.Wtr_dir(dir_info.Ext_tid());
		return Xow_fsys_mgr.Url_fil(ns_dir.GenSubDir_nest(Int_.Xto_str_pad_bgn(ns_id, 3), dir_name), fil_idx, dir_regy[tid].Ext_bry());
	}
	public Io_url Url_site_fil(byte tid, int fil_idx)	{return Xow_fsys_mgr.Url_fil(Url_site_dir(tid), fil_idx, Xow_dir_info_.Bry_xdat);}
	public Io_url Url_site_reg(byte tid)				{return Url_site_dir(tid).GenSubFil(Xow_dir_info_.Name_reg_fil);}
	public Io_url Url_site_dir(byte tid) {
		switch (tid) {
			case Xow_dir_info_.Tid_category2_link:		return site_dir.GenSubDir_nest(Xow_dir_info_.Name_category2, Xow_dir_info_.Name_category2_link);
			case Xow_dir_info_.Tid_category2_main:		return site_dir.GenSubDir_nest(Xow_dir_info_.Name_category2, Xow_dir_info_.Name_category2_main);
			default:									return site_dir.GenSubDir_nest(Xow_dir_info_.Tid_name(tid));
		}
	}
	public void Scan_dirs() {
		Scan_dirs_zip(this, Xow_dir_info_.Tid_page);
		Scan_dirs_ns(ns_dir, wiki.Ns_mgr());
	}
	private static void Scan_dirs_zip(Xow_fsys_mgr fsys_mgr, byte id) {
		Io_url[] dirs = Io_mgr._.QueryDir_args(fsys_mgr.Ns_dir().GenSubDir_nest("000")).FilPath_("*page*").DirOnly_().Recur_(false).ExecAsUrlAry();
		int len = dirs.length;
		byte tid = gplx.ios.Io_stream_.Tid_file;	// needed for Xoa_xowa_exec_tst
		for (int i = 0; i < len; i++) {
			Io_url dir = dirs[i];
			String dir_name = dir.NameOnly();
			if		(String_.Eq(dir_name, "page"))			{tid = gplx.ios.Io_stream_.Tid_file; break;} 
			else if	(String_.Eq(dir_name, "page_zip"))		tid = gplx.ios.Io_stream_.Tid_zip;
			else if	(String_.Eq(dir_name, "page_gz"))		tid = gplx.ios.Io_stream_.Tid_gzip;
			else if	(String_.Eq(dir_name, "page_bz2"))		tid = gplx.ios.Io_stream_.Tid_bzip2;
//				else											throw Err_.unhandled(dir_name);
		}
		fsys_mgr.Dir_regy()[id].Ext_tid_(tid);
	}
	private static HashAdp Scan_dirs_ns(Io_url ns_dir, Xow_ns_mgr ns_mgr) {
		Io_url[] ns_dirs = Io_mgr._.QueryDir_args(ns_dir).Recur_(false).DirOnly_().ExecAsUrlAry();
		int len = ns_dirs.length;
		HashAdp rv = HashAdp_.new_();
		for (int i = 0; i < len; i++) {
			int ns_int = Int_.parse_or_(ns_dirs[i].NameOnly(), Int_.MinValue); if (ns_int == Int_.MinValue) continue; 
			Xow_ns ns = ns_mgr.Ids_get_or_null(ns_int); if (ns == null) continue;
			ns.Exists_(true);
		}
		return rv;
	}
	public static Io_url Url_fil(Io_url root_dir, int fil_idx, byte[] ext) {return Xos_url_gen.bld_fil_(root_dir, fil_idx, ext);}
	static final String Const_url_cfg = "cfg";
	public static Io_url Find_file_or_fail(Io_url dir, String file_name, String file_ext_0, String file_ext_1) {
		Io_url url = Find_file_or_null(dir, file_name, file_ext_0, file_ext_1);
		if (url == null) throw Err_mgr._.fmt_("", "", "could not find file: dir=~{0} name=~{1} ext_0=~{2} ext_1=~{3}", dir.Raw(), file_name, file_ext_0, file_ext_1);
		return url;
	}
	public static Io_url Find_file_or_null(Io_url dir, String file_name, String file_ext_0, String file_ext_1) {
		Io_url url = Xobd_rdr.Find_fil_by(dir, file_name + file_ext_0);
		if (url == null) {
			url = Xobd_rdr.Find_fil_by(dir, file_name + file_ext_1);
			if (url == null) return null;
		}
		return url;
	}
	public static String Wtr_dir(byte v) {
		switch (v) {
			case gplx.ios.Io_stream_.Tid_file	: return "";
			case gplx.ios.Io_stream_.Tid_zip	: return "_zip";
			case gplx.ios.Io_stream_.Tid_gzip	: return "_gz";
			case gplx.ios.Io_stream_.Tid_bzip2	: return "_bz2";
			default								: throw Err_.unhandled(v);
		}
	}
	public static byte[] Wtr_ext(byte v) {
		switch (v) {
			case gplx.ios.Io_stream_.Tid_file	: return Wtr_xdat_bry;
			case gplx.ios.Io_stream_.Tid_zip	: return Wtr_zip_bry;
			case gplx.ios.Io_stream_.Tid_gzip	: return Wtr_gz_bry;
			case gplx.ios.Io_stream_.Tid_bzip2	: return Wtr_bz2_bry;
			default								: throw Err_.unhandled(v);
		}
	}
	public static final String Wtr_xdat_str = ".xdat", Wtr_zip_str = ".zip", Wtr_gz_str = ".gz", Wtr_bz2_str = ".bz2";
	public static final byte[] Wtr_xdat_bry = Bry_.new_ascii_(Wtr_xdat_str), Wtr_zip_bry = Bry_.new_ascii_(Wtr_zip_str), Wtr_gz_bry = Bry_.new_ascii_(Wtr_gz_str), Wtr_bz2_bry = Bry_.new_ascii_(Wtr_bz2_str);
}

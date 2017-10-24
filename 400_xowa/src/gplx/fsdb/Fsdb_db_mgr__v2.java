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
package gplx.fsdb; import gplx.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.fsdb.meta.*; import gplx.xowa.files.origs.*; import gplx.xowa.wikis.data.*;
public class Fsdb_db_mgr__v2 implements Fsdb_db_mgr {
	private final    Xow_db_layout layout; private final    Io_url wiki_dir;
	private final    Fsdb_db_file file_main_core, file_user_core;
	private final    Xof_orig_tbl[] orig_tbl_ary;
	public Fsdb_db_mgr__v2(Xow_db_layout layout, Io_url wiki_dir, Fsdb_db_file file_main_core, Fsdb_db_file file_user_core) {
		this.layout = layout; this.wiki_dir = wiki_dir;
		this.file_main_core = file_main_core; this.file_user_core = file_user_core;
		this.orig_tbl_ary	= new Xof_orig_tbl[] 
		{ new Xof_orig_tbl(file_main_core.Conn(), this.File__schema_is_1())
		, new Xof_orig_tbl(file_user_core.Conn(), this.File__schema_is_1())
		};
	}		
	public boolean				File__schema_is_1()					{return Bool_.N;}
	public boolean				File__solo_file()					{return layout.Tid_is_all_or_few();}
	public String			File__cfg_tbl_name()				{return gplx.xowa.wikis.data.Xowd_cfg_tbl_.Tbl_name;}
	public Xof_orig_tbl[]	File__orig_tbl_ary()				{return orig_tbl_ary;}
	public Fsdb_db_file		File__mnt_file()					{return file_main_core;}
	public Fsdb_db_file		File__abc_file__at(int mnt_id)		{return mnt_id == Fsm_mnt_mgr.Mnt_idx_main ? file_main_core: file_user_core;}
	public Fsdb_db_file		File__atr_file__at(int mnt_id)		{return mnt_id == Fsm_mnt_mgr.Mnt_idx_main ? file_main_core: file_user_core;}
	public Fsdb_db_file		File__bin_file__at(int mnt_id, int bin_id, String file_name) {
		if (mnt_id == Fsm_mnt_mgr.Mnt_idx_user)		return file_user_core;
		if (layout.Tid_is_all_or_few())				return file_main_core;
		Io_url url = wiki_dir.GenSubFil(file_name);
		Db_conn conn = Db_conn_bldr.Instance.Get(url);
		if (conn == null) {	// bin file deleted or not downloaded; use Noop Db_conn and continue; do not fail; DATE:2015-04-16
			Gfo_usr_dlg_.Instance.Warn_many("", "", "fsdb.bin:file does not exist; url=~{0}", url);
			conn = Db_conn_.Noop; 
		}
		return new Fsdb_db_file(url, conn);
	}
	public Fsdb_db_file		File__bin_file__new(int mnt_id, String file_name) {
		if (mnt_id == Fsm_mnt_mgr.Mnt_idx_user) return Fsdb_db_mgr__v2_bldr.Make_bin_tbl(file_user_core);
		if (layout.Tid_is_all_or_few())			return Fsdb_db_mgr__v2_bldr.Make_bin_tbl(file_main_core);
		Io_url url = wiki_dir.GenSubFil(file_name);
		Db_conn conn = Db_conn_bldr.Instance.New(url);
		gplx.xowa.wikis.data.Xowd_cfg_tbl_.New(conn).Create_tbl();
		return Fsdb_db_mgr__v2_bldr.Make_bin_tbl(new Fsdb_db_file(url, conn));
	}
	public static Xow_db_layout Cfg__layout_file__get(Db_conn main_core_conn) {
		Db_cfg_tbl cfg_tbl = gplx.xowa.wikis.data.Xowd_cfg_tbl_.New(main_core_conn);
		return Xow_db_layout.Get_by_name(cfg_tbl.Select_str_or(gplx.xowa.wikis.data.Xowd_cfg_key_.Grp__bldr_fsdb, Cfg_key__layout_file, Xow_db_layout.Key__few));
	}
	public static void Cfg__layout_file__set(Db_cfg_tbl cfg_tbl, Xow_db_layout v) {
		cfg_tbl.Insert_str(gplx.xowa.wikis.data.Xowd_cfg_key_.Grp__bldr_fsdb, Cfg_key__layout_file, v.Key());
	}
	private static final String Cfg_key__layout_file = "layout_file";
}

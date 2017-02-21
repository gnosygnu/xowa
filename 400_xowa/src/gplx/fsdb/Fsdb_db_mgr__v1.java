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
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.fsdb.meta.*; import gplx.fsdb.data.*; import gplx.xowa.files.origs.*;
public class Fsdb_db_mgr__v1 implements Fsdb_db_mgr {
	private final Io_url file_dir;
	private final Fsdb_db_file orig_file, mnt_file, abc_file__main, abc_file__user, atr_file__main, atr_file__user;
	private final Xof_orig_tbl[] orig_tbl_ary;
	private String bin_prefix__main = "fsdb.bin.", bin_prefix__user = "fsdb.bin.";
	public Fsdb_db_mgr__v1(Io_url file_dir) {
		this.file_dir = file_dir;
		this.orig_file		= get_db(file_dir.GenSubFil(Orig_name));										// EX: /xowa/enwiki/wiki.orig#00.sqlite3
		this.mnt_file		= get_db(file_dir.GenSubFil(Mnt_name));											// EX: /xowa/enwiki/wiki.mnt.sqlite3
		this.abc_file__main	= get_db(file_dir.GenSubFil_nest(Fsm_mnt_tbl.Mnt_name_main, Abc_name));			// EX: /xowa/enwiki/fsdb.main/fsdb.abc.sqlite3
		this.atr_file__main	= get_db(Get_atr_db_url(Bool_.Y, file_dir, Fsm_mnt_tbl.Mnt_name_main));			// EX: /xowa/enwiki/fsdb.main/fsdb.atr.00.sqlite3
		if (Db_conn_bldr.Instance.Get(file_dir.GenSubFil_nest(Fsm_mnt_tbl.Mnt_name_user, Abc_name)) == null)		// user doesn't exist; create; DATE:2015-04-20
			Fsdb_db_mgr__v1_bldr.Instance.Make_core_dir(file_dir, Fsm_mnt_mgr.Mnt_idx_user, Fsm_mnt_tbl.Mnt_name_user);
		this.abc_file__user	= get_db(file_dir.GenSubFil_nest(Fsm_mnt_tbl.Mnt_name_user, Abc_name));			// EX: /xowa/enwiki/fsdb.user/fsdb.abc.sqlite3
		this.atr_file__user	= get_db(Get_atr_db_url(Bool_.N, file_dir, Fsm_mnt_tbl.Mnt_name_user));			// EX: /xowa/enwiki/fsdb.user/fsdb.atr.00.sqlite3
		this.orig_tbl_ary	= new Xof_orig_tbl[] {new Xof_orig_tbl(orig_file.Conn(), this.File__schema_is_1())};
	}
	public boolean				File__schema_is_1()					{return Bool_.Y;}
	public boolean				File__solo_file()					{return Bool_.N;}
	public String			File__cfg_tbl_name()				{return "fsdb_cfg";}
	public Xof_orig_tbl[]	File__orig_tbl_ary()				{return orig_tbl_ary;}
	public Fsdb_db_file		File__mnt_file()					{return mnt_file;}
	public Fsdb_db_file		File__abc_file__at(int mnt_id)		{return mnt_id == Fsm_mnt_mgr.Mnt_idx_main ? abc_file__main : abc_file__user;}
	public Fsdb_db_file		File__atr_file__at(int mnt_id)		{return mnt_id == Fsm_mnt_mgr.Mnt_idx_main ? atr_file__main : atr_file__user;}
	public Fsdb_db_file		File__bin_file__at(int mnt_id, int bin_id, String file_name) {
		boolean mnt_is_main = mnt_id == Fsm_mnt_mgr.Mnt_idx_main;
		String bin_name = (mnt_is_main ? bin_prefix__main : bin_prefix__user) + Int_.To_str_pad_bgn_zero(bin_id, 4) + ".sqlite3";
		String mnt_name = mnt_is_main ? Fsm_mnt_tbl.Mnt_name_main : Fsm_mnt_tbl.Mnt_name_user;
		Io_url url = file_dir.GenSubFil_nest(mnt_name, bin_name);	// EX: /xowa/enwiki/fsdb.main/fsdb.bin.0000.sqlite3
		Db_conn conn = Db_conn_bldr.Instance.Get(url);
		if (conn == null) {	// NOTE: handle wikis with missing bin files; EX:sv.w missing bin.0010; DATE:2015-07-04
			gplx.xowa.Xoa_app_.Usr_dlg().Warn_many("", "", "fsdb.v1: missing db; db=~{0}", url.Raw());
			return Fsdb_db_mgr__v1_bldr.Instance.new_db__bin(url);
		}
		else
			return new Fsdb_db_file(url, conn);
	}
	public Fsdb_db_file		File__bin_file__new(int mnt_id, String file_name) {
		String mnt_name = mnt_id == Fsm_mnt_mgr.Mnt_idx_main ? Fsm_mnt_tbl.Mnt_name_main : Fsm_mnt_tbl.Mnt_name_user;
		Io_url url = file_dir.GenSubFil_nest(mnt_name, file_name);	// EX: /xowa/enwiki/fsdb.main/fsdb.bin.0000.sqlite3
		Db_conn conn = Db_conn_bldr.Instance.New(url);
		Fsd_bin_tbl bin_tbl = new Fsd_bin_tbl(conn, Bool_.Y); bin_tbl.Create_tbl();
		return new Fsdb_db_file(url, conn);
	}
	private Io_url Get_atr_db_url(boolean main, Io_url file_dir, String mnt_name) {
		Io_url rv = null;
		rv = file_dir.GenSubFil_nest(mnt_name, Atr_name_v1a);
		if (Io_mgr.Instance.ExistsFil(rv)) {
			if (main)
				bin_prefix__main = "fsdb.bin#";
			else
				bin_prefix__user = "fsdb.bin#";
			return rv;
		}
		rv = file_dir.GenSubFil_nest(mnt_name, Atr_name_v1b); if (Io_mgr.Instance.ExistsFil(rv)) return rv;
		throw Err_.new_wo_type("could not find atr file", "dir", file_dir.Raw(), "mnt", mnt_name);
	}
	public static final String Orig_name = "wiki.orig#00.sqlite3", Mnt_name = "wiki.mnt.sqlite3", Abc_name	= "fsdb.abc.sqlite3"
	, Atr_name_v1a = "fsdb.atr#00.sqlite3", Atr_name_v1b = "fsdb.atr.00.sqlite3";
	private static Fsdb_db_file get_db(Io_url file) {
		Db_conn conn = Db_conn_bldr.Instance.Get(file);
		if (conn == null) conn = Db_conn_.Noop;
		return new Fsdb_db_file(file, conn);
	}
}
class Fsdb_db_mgr__v1_bldr {
	public void Make_core_dir(Io_url file_dir, int mnt_id, String mnt_name) {
		boolean schema_is_1 = true;
		Io_url mnt_dir = file_dir.GenSubDir(mnt_name);
		// make abc_fil
		Fsdb_db_file db_abc = new_db(mnt_dir.GenSubFil(Fsdb_db_mgr__v1.Abc_name));
		Db_cfg_tbl cfg_tbl = new Db_cfg_tbl(db_abc.Conn(), "fsdb_cfg"); cfg_tbl.Create_tbl();
		Fsm_mnt_mgr.Patch(cfg_tbl);
		Fsm_atr_tbl dba_tbl = new Fsm_atr_tbl(db_abc.Conn(), schema_is_1); dba_tbl.Create_tbl();
		dba_tbl.Insert(mnt_id, mnt_name);
		Fsm_bin_tbl dbb_tbl = new Fsm_bin_tbl(db_abc.Conn(), schema_is_1, mnt_id); dbb_tbl.Create_tbl();
		dbb_tbl.Insert(0, "fsdb.bin.0000.sqlite3");
		// make atr_fil
		Fsdb_db_file db_atr = new_db(mnt_dir.GenSubFil(Fsdb_db_mgr__v1.Atr_name_v1b));	// create atr database in v1b style; "fsdb.atr.00.sqlite3" not "fsdb.atr#00.sqlite3"
		Fsd_dir_tbl dir_tbl = new Fsd_dir_tbl(db_atr.Conn(), schema_is_1); dir_tbl.Create_tbl();
		Fsd_fil_tbl fil_tbl = new Fsd_fil_tbl(db_atr.Conn(), schema_is_1, mnt_id); fil_tbl.Create_tbl();
		Fsd_thm_tbl thm_tbl = new Fsd_thm_tbl(db_atr.Conn(), schema_is_1, mnt_id, Bool_.Y); thm_tbl.Create_tbl();
		// make bin_fil
		new_db__bin(mnt_dir.GenSubFil("fsdb.bin.0000.sqlite3"));
	}
	private Fsdb_db_file new_db(Io_url url) {return new Fsdb_db_file(url, Db_conn_bldr.Instance.New(url));}
	public Fsdb_db_file new_db__bin(Io_url url) {
		Fsdb_db_file rv = new_db(url);
		Fsd_bin_tbl bin_tbl = new Fsd_bin_tbl(rv.Conn(), true);	// NOTE: schema_is_1 is always true b/c it is in Fsdb_db_mgr__v1_bldr
		bin_tbl.Create_tbl();
		return rv;
	}
        public static final Fsdb_db_mgr__v1_bldr Instance = new Fsdb_db_mgr__v1_bldr(); Fsdb_db_mgr__v1_bldr() {}
}

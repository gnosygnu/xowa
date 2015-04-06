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
import gplx.dbs.*; import gplx.fsdb.meta.*; import gplx.xowa.files.origs.*;
public class Fsdb_db_mgr__v1 implements Fsdb_db_mgr {
	private final Io_url file_dir;
	private final Fsdb_db_file orig_file, mnt_file, abc_file__main, abc_file__user, atr_file__main, atr_file__user;
	private final Xof_orig_tbl[] orig_tbl_ary;
	public Fsdb_db_mgr__v1(Io_url file_dir) {
		this.file_dir = file_dir;
		this.orig_file		= new_db(file_dir.GenSubFil(Orig_name));								// EX: /xowa/enwiki/wiki.orig#00.sqlite3
		this.mnt_file		= new_db(file_dir.GenSubFil(Mnt_name));									// EX: /xowa/enwiki/wiki.mnt.sqlite3
		this.abc_file__main	= new_db(file_dir.GenSubFil_nest(Fsm_mnt_tbl.Mnt_name_main, Abc_name));	// EX: /xowa/enwiki/fsdb.main/fsdb.abc.sqlite3
		this.atr_file__main	= new_db(file_dir.GenSubFil_nest(Fsm_mnt_tbl.Mnt_name_main, Atr_name));	// EX: /xowa/enwiki/fsdb.main/fsdb.atr.00.sqlite3
		this.abc_file__user	= new_db(file_dir.GenSubFil_nest(Fsm_mnt_tbl.Mnt_name_user, Abc_name));	// EX: /xowa/enwiki/fsdb.user/fsdb.abc.sqlite3
		this.atr_file__user	= new_db(file_dir.GenSubFil_nest(Fsm_mnt_tbl.Mnt_name_user, Atr_name));	// EX: /xowa/enwiki/fsdb.user/fsdb.atr.00.sqlite3
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
		String bin_name = "fsdb.bin." + Int_.Xto_str_pad_bgn_zero(bin_id, 4) + ".sqlite3";
		String mnt_name = mnt_id == Fsm_mnt_mgr.Mnt_idx_main ? Fsm_mnt_tbl.Mnt_name_main : Fsm_mnt_tbl.Mnt_name_user;
		Io_url url = file_dir.GenSubFil_nest(mnt_name, bin_name);	// EX: /xowa/enwiki/fsdb.main/fsdb.bin.0000.sqlite3
		return new Fsdb_db_file(url, Db_conn_bldr.I.Get(url));
	}
	public Fsdb_db_file		File__bin_file__new(int mnt_id, String file_name) {throw Err_.not_implemented_();}
	public static final String Orig_name = "wiki.orig#00.sqlite3", Mnt_name = "wiki.mnt.sqlite3", Abc_name	= "fsdb.abc.sqlite3", Atr_name= "fsdb.atr.00.sqlite3";
	private static Fsdb_db_file new_db(Io_url file) {return new Fsdb_db_file(file, Db_conn_bldr.I.Get(file));}
}

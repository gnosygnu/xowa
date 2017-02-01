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
package gplx.xowa.files.fsdb.fs_roots; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.core.primitives.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.gfui.*;
import gplx.fsdb.meta.*; import gplx.xowa.files.imgs.*;
class Fs_root_wkr {
	private Xof_img_wkr_query_img_size img_size_wkr;
	private Io_url orig_dir; private boolean recurse = true;
	private Orig_fil_mgr cache = new Orig_fil_mgr(), fs_fil_mgr;
	private Db_conn conn; private Db_cfg_tbl cfg_tbl;
	private Orig_fil_tbl orig_tbl;
	private int fil_id_next = 0;
	public Orig_fil_tbl Orig_tbl() {return orig_tbl;}
	public void Init(Xof_img_wkr_query_img_size img_size_wkr, Io_url orig_dir) {
		this.img_size_wkr = img_size_wkr;
		this.orig_dir = orig_dir;
	}
	public Orig_fil_row Get_by_ttl(byte[] lnki_ttl) {
		Orig_fil_row rv = (Orig_fil_row)cache.Get_by_ttl(lnki_ttl);
		if (rv == null) {
			// not in mem; get from db
			rv = Get_from_db(lnki_ttl);
			// not in db; get from fsys
			if (rv == null) {
				rv = Get_from_fs(lnki_ttl);
				if (rv == null) return Orig_fil_row.Null;
			}
			cache.Add(rv);
		}
		return rv;
	}
	private Orig_fil_row Get_from_db(byte[] lnki_ttl) {
		if (conn == null) conn = Init_orig_db();
		return orig_tbl.Select_itm_or_null(orig_dir, lnki_ttl);
	}
	private Orig_fil_row Get_from_fs(byte[] lnki_ttl) {
		if (fs_fil_mgr == null) fs_fil_mgr = Init_fs_fil_mgr();
		Orig_fil_row rv = fs_fil_mgr.Get_by_ttl(lnki_ttl);
		if (rv == null) return Orig_fil_row.Null;		// not in fs
		SizeAdp img_size = SizeAdp_.Zero;
		if (Xof_ext_.Id_is_image(rv.Ext_id()))
			img_size = img_size_wkr.Exec(rv.Url());
		rv.Init_by_fs(++fil_id_next, img_size.Width(), img_size.Height());
		cfg_tbl.Update_int(Cfg_grp_root_dir, Cfg_key_fil_id_next, fil_id_next);	
		String fil_orig_dir = "~{orig_dir}" + rv.Url().OwnerDir().GenRelUrl_orEmpty(orig_dir);	// converts "/xowa/wiki/custom_wiki/file/orig/7/70/A.png" -> "~{orig_dir}7/70/"
		orig_tbl.Insert(rv, fil_orig_dir);
		return rv;
	}
	private Orig_fil_mgr Init_fs_fil_mgr() {	// NOTE: need to read entire dir, b/c ttl may be "A.png", but won't know which subdir
		Orig_fil_mgr rv = new Orig_fil_mgr();
		Io_url orig_changes_log = orig_dir.GenSubFil("xowa.orig.changes.log");

		// loop over all dirs in orig_dir
		Io_url[] sub_dirs = Io_mgr.Instance.QueryDir_args(orig_dir).DirInclude_(true).ExecAsUrlAry();
		int sub_dirs_len = sub_dirs.length;
		for (int i = 0; i < sub_dirs_len; i++) {
			Io_url sub_dir = sub_dirs[i];
			if (String_.Len(sub_dir.NameOnly()) != 1) continue;	// only look at subdirs with 1 char; EX: "/orig_dir/a/" vs "/orig_dir/math/"

			// loop over all fils in that 1-char dir
			Io_url[] fils = Io_mgr.Instance.QueryDir_args(sub_dir).Recur_(recurse).ExecAsUrlAry();
			int fils_len = fils.length;
			for (int j = 0; j < fils_len; j++) {
				Io_url fil = fils[j];
				byte[] fil_name_bry = Bry_.new_u8(fil.NameAndExt());

				String orig_change_type = null;
				// if url has space, replace it with underscore
				if (Bry_.Has(fil_name_bry, Byte_ascii.Space)) {
					fil_name_bry = Bry_.Replace(fil_name_bry, Byte_ascii.Space, Byte_ascii.Underline);
					orig_change_type = "space_to_underscore";
				}

				// TOMBSTONE: code below had unit_test, but not sure if needed; file's title should be title-cased, but it's possible to be lower-case for "File:" namespaces with case_match; DATE:2017-02-01
				// if url's first char is lowercase, uppercase it;
				// byte b_0 = fil_name_bry[0];
				// if (b_0 >= Byte_ascii.Ltr_a && b_0 <= Byte_ascii.Ltr_z) {
				//	 fil_name_bry = Bry_.Ucase__1st(fil_name_bry);
				//	 orig_change_type = "ucase_1st";
				// }

				// if changed above, rename it and log it
				if (orig_change_type != null) {
					Io_url new_url = fil.GenNewNameAndExt(String_.new_u8(fil_name_bry));
					Io_mgr.Instance.MoveFil_args(fil, new_url, true).Exec();
					Io_mgr.Instance.AppendFilStr(orig_changes_log, orig_change_type + "|" + fil.Raw() + "\n");
					fil = new_url;
				}

				// if file already seen, ignore it
				Orig_fil_row fil_itm = rv.Get_by_ttl(fil_name_bry);
				if (fil_itm != Orig_fil_row.Null) {
					Gfo_usr_dlg_.Instance.Warn_many("", "", "file already exists: cur=~{0} new=~{1}", fil_itm.Url().Raw(), fil.Raw());
					continue;
				}

				// add it to cache
				Xof_ext ext = Xof_ext_.new_by_ttl_(fil_name_bry);
				fil_itm = Orig_fil_row.New_by_fs(fil, fil_name_bry, ext.Id());
				rv.Add(fil_itm);
			}
		}
		return rv;
	}
	private Db_conn Init_orig_db() {
		Io_url orig_db = orig_dir.GenSubFil("^orig_regy.sqlite3");
		boolean created = false; boolean schema_is_1 = Bool_.Y;
		Db_conn conn = Db_conn_bldr.Instance.Get(orig_db);
		if (conn == null) {
			conn = Db_conn_bldr.Instance.New(orig_db);
			created = true;
		}
		cfg_tbl = new Db_cfg_tbl(conn, schema_is_1 ? "fsdb_cfg" : gplx.xowa.wikis.data.Xowd_cfg_tbl_.Tbl_name);
		orig_tbl = new Orig_fil_tbl(conn, schema_is_1);
		if (created) {
			cfg_tbl.Create_tbl();
			cfg_tbl.Insert_int(Cfg_grp_root_dir, Cfg_key_fil_id_next, fil_id_next);
			orig_tbl.Create_tbl();
		}
		else {
			fil_id_next = cfg_tbl.Select_int(Cfg_grp_root_dir, Cfg_key_fil_id_next);
		}
		return conn;
	}
	public void Rls() {
		cfg_tbl.Rls();
		orig_tbl.Rls();
	}
	private static final String Cfg_grp_root_dir = "xowa.root_dir", Cfg_key_fil_id_next = "fil_id_next";
	public static final String Url_orig_dir = "~{orig_dir}";
}

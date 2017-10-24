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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*;
class Xofc_fil_tbl implements Rls_able {
	private String tbl_name = "file_cache_fil"; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private String fld_uid, fld_dir_id, fld_name, fld_is_orig, fld_w, fld_h, fld_time, fld_page, fld_ext, fld_size, fld_cache_time;
	private Db_conn conn; private final    Db_stmt_bldr stmt_bldr = new Db_stmt_bldr(); private Db_stmt select_itm_stmt, select_itm_v2_stmt;
	public Db_conn Conn() {return conn;}
	public void Conn_(Db_conn new_conn, boolean created, boolean schema_is_1) {
		this.conn = new_conn; flds.Clear();
		String fld_prefix = "";
		if (schema_is_1) {
			tbl_name		= "cache_fil";
			fld_prefix		= "fil_";
		}
		fld_uid				= flds.Add_int_pkey("uid");
		fld_dir_id			= flds.Add_int("dir_id");
		fld_name			= flds.Add_str(fld_prefix + "name", 255);
		fld_is_orig			= flds.Add_byte(fld_prefix + "is_orig");
		fld_w				= flds.Add_int(fld_prefix + "w");
		fld_h				= flds.Add_int(fld_prefix + "h");
		fld_time			= flds.Add_int(fld_prefix + "thumbtime");
		if (schema_is_1) {
			fld_page		= Dbmeta_fld_itm.Key_null;
		}
		else {
			fld_page		= flds.Add_int(fld_prefix + "page");
		}
		fld_ext				= flds.Add_int(fld_prefix + "ext");
		fld_size			= flds.Add_long(fld_prefix + "size");
		fld_cache_time		= flds.Add_long("cache_time");
		if (created) {
			Dbmeta_tbl_itm meta = Dbmeta_tbl_itm.New(tbl_name, flds
			, Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "fil", fld_name, fld_is_orig, fld_w, fld_h, fld_time, fld_cache_time, fld_uid)
			);
			conn.Meta_tbl_create(meta);
		}
		select_itm_stmt = select_itm_v2_stmt = null;
		stmt_bldr.Conn_(conn, tbl_name, flds, fld_uid);
		conn.Rls_reg(this);
	}
	public void Rls() {
		select_itm_stmt = Db_stmt_.Rls(select_itm_stmt);
		select_itm_v2_stmt = Db_stmt_.Rls(select_itm_v2_stmt);
		stmt_bldr.Rls();
	}
	public String Db_save(Xofc_fil_itm itm) {
		try {
			Db_stmt stmt = stmt_bldr.Get(itm.Cmd_mode());
			switch (itm.Cmd_mode()) {
				case Db_cmd_mode.Tid_create:	stmt.Clear().Val_int(fld_uid, itm.Uid());	Db_save_modify(stmt, itm); stmt.Exec_insert(); break;
				case Db_cmd_mode.Tid_update:	stmt.Clear();								Db_save_modify(stmt, itm); stmt.Crt_int(fld_uid, itm.Uid()).Exec_update(); break;
				case Db_cmd_mode.Tid_delete:	stmt.Clear().Crt_int(fld_uid, itm.Uid()); stmt.Exec_delete();	break;
				case Db_cmd_mode.Tid_ignore:	break;
				default:						throw Err_.new_unhandled(itm.Cmd_mode());
			}
			itm.Cmd_mode_(Db_cmd_mode.Tid_ignore);
			return null;
		} catch (Exception e) {
			stmt_bldr.Rls();	// null out bldr, else bad stmt will lead to other failures
			return Err_.Message_gplx_full(e);
		}
	}
	private void Db_save_modify(Db_stmt stmt, Xofc_fil_itm itm) {
		stmt.Val_int(fld_dir_id, itm.Dir_id())
			.Val_bry_as_str(fld_name, itm.Name())
			.Val_bool_as_byte(fld_is_orig, itm.Is_orig())
			.Val_int(fld_w, itm.W())
			.Val_int(fld_h, itm.H())
			.Val_int(fld_time, Xof_lnki_time.Db_save_int(itm.Time()))
			.Val_int(fld_page, itm.Page())
			.Val_int(fld_ext, itm.Ext().Id())
			.Val_long(fld_size, itm.Size())
			.Val_long(fld_cache_time, itm.Cache_time())
			;
	}
	public Xofc_fil_itm Select_one_v1(int dir_id, byte[] fil_name, boolean fil_is_orig, int fil_w, int fil_h, double fil_thumbtime) {
		if (select_itm_stmt == null) select_itm_stmt = conn.Stmt_select(tbl_name, flds, String_.Ary(fld_dir_id, fld_name, fld_is_orig, fld_w, fld_h, fld_time));
		Db_rdr rdr = select_itm_stmt.Clear()
			.Crt_int(fld_dir_id, dir_id)
			.Crt_bry_as_str(fld_name, fil_name)
			.Crt_bool_as_byte(fld_is_orig, fil_is_orig)
			.Crt_int(fld_w, fil_w)
			.Crt_int(fld_h, fil_h)
			.Crt_int(fld_time, Xof_lnki_time.Db_save_int(fil_thumbtime))
			.Exec_select__rls_manual();
		try {
			return rdr.Move_next() ? new_itm(rdr) : Xofc_fil_itm.Null;
		}
		finally {rdr.Rls();}
	}
	public Xofc_fil_itm Select_one_v2(int dir_id, byte[] name, boolean is_orig, int w, double time, int page) {
		if (select_itm_v2_stmt == null) select_itm_v2_stmt = conn.Stmt_select(tbl_name, flds, String_.Ary(fld_dir_id, fld_name, fld_is_orig, fld_w, fld_time, fld_page));
		Db_rdr rdr = select_itm_v2_stmt.Clear()
			.Crt_int(fld_dir_id, dir_id)
			.Crt_bry_as_str(fld_name, name)
			.Crt_bool_as_byte(fld_is_orig, is_orig)
			.Crt_int(fld_w, w)
			.Crt_int(fld_time, Xof_lnki_time.Db_save_int(time))
			.Crt_int(fld_page, page)
			.Exec_select__rls_manual();
		try {
			return rdr.Move_next() ? new_itm(rdr) : Xofc_fil_itm.Null;
		}
		finally {rdr.Rls();}
	}
	public void Select_all(Bry_bfr fil_key_bldr, Ordered_hash hash) {
		hash.Clear();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, Dbmeta_fld_itm.Str_ary_empty).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Xofc_fil_itm fil_itm = new_itm(rdr);
				byte[] key = fil_itm.Gen_hash_key_v1(fil_key_bldr);
				if (hash.Has(key))		// NOTE: need to check for uniqueness else dupe file will cause select to fail; shouldn't happen, but somehow did; DATE:2013-12-28
					Gfo_usr_dlg_.Instance.Warn_many("", "", "cache had duplicate itm: key=~{0}", String_.new_u8(key));
				else
					hash.Add(key, fil_itm);
			}
		}
		finally {rdr.Rls();}
	}
	public int Select_max_uid() {return conn.Exec_select_as_int("SELECT Max(uid) AS MaxId FROM cache_fil;", -1);}
	private Xofc_fil_itm new_itm(Db_rdr rdr) {
		return new Xofc_fil_itm
		( rdr.Read_int(fld_uid)
		, rdr.Read_int(fld_dir_id)
		, rdr.Read_bry_by_str(fld_name)
		, rdr.Read_byte(fld_is_orig) != Byte_.Zero
		, rdr.Read_int(fld_w)
		, rdr.Read_int(fld_h)
		, Xof_lnki_time.Db_load_int(rdr, fld_time)
		, Xof_lnki_page.Null
		, Xof_ext_.new_by_id_(rdr.Read_int(fld_ext))
		, rdr.Read_long(fld_size)
		, rdr.Read_long(fld_cache_time)
		, Db_cmd_mode.Tid_ignore
		);
	}
}

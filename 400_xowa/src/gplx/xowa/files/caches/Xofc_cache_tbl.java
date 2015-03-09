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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*;
class Xofc_cache_tbl {
	private String tbl_name = "user_file_cache"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String
	  fld_uid
	, fld_lnki_wiki, fld_lnki_ttl, fld_lnki_type, fld_lnki_upright, fld_lnki_w, fld_lnki_h, fld_lnki_time, fld_lnki_page
	, fld_orig_wiki, fld_orig_ttl, fld_orig_ext, fld_file_w, fld_file_h, fld_file_time, fld_file_page
	, fld_temp_file_size, fld_temp_view_count, fld_temp_view_date, fld_temp_w
	;
	private Db_conn conn; private final Db_stmt_bldr stmt_bldr = new Db_stmt_bldr(); private Db_stmt select_stmt;
	private final Bry_bfr lnki_key_bfr = Bry_bfr.reset_(255);
	public void Conn_(Db_conn new_conn, boolean created) {
		this.conn = new_conn; flds.Clear();
		fld_uid				= flds.Add_int_pkey_autonum("uid");
		fld_lnki_wiki		= flds.Add_int("lnki_wiki");
		fld_lnki_ttl		= flds.Add_str("lnki_ttl", 255);
		fld_lnki_type		= flds.Add_int("lnki_type");
		fld_lnki_upright	= flds.Add_double("lnki_upright");
		fld_lnki_w			= flds.Add_int("lnki_w");
		fld_lnki_h			= flds.Add_int("lnki_h");
		fld_lnki_time		= flds.Add_double("lnki_time");
		fld_lnki_page		= flds.Add_int("lnki_page");
		fld_orig_wiki		= flds.Add_int("orig_wiki");
		fld_orig_ttl		= flds.Add_str("orig_ttl", 255);
		fld_orig_ext		= flds.Add_int("orig_ext");
		fld_file_w			= flds.Add_int("file_w");
		fld_file_h			= flds.Add_int("file_h");
		fld_file_time		= flds.Add_double("file_time");
		fld_file_page		= flds.Add_int("file_page");
		fld_temp_file_size	= flds.Add_long("temp_file_size");
		fld_temp_view_count	= flds.Add_int("temp_view_count");
		fld_temp_view_date	= flds.Add_long("temp_view_date");
		fld_temp_w			= flds.Add_int("temp_w");
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_normal_by_tbl(tbl_name, "main", fld_lnki_wiki, fld_lnki_ttl, fld_lnki_type, fld_lnki_upright, fld_lnki_w, fld_lnki_h, fld_lnki_time, fld_lnki_page)
			);
			conn.Exec_create_tbl_and_idx(meta);
		}
		select_stmt = null;
		stmt_bldr.Conn_(conn, tbl_name, flds, fld_uid);
	}
	public Xofc_cache_itm Select_one(int lnki_wiki, byte[] lnki_ttl, int lnki_type, double lnki_upright, int lnki_w, int lnki_h, double lnki_time, int lnki_page) {
		if (select_stmt == null) select_stmt = conn.Rls_reg(conn.Stmt_select(tbl_name, flds, String_.Ary(fld_lnki_wiki, fld_lnki_ttl, fld_lnki_type, fld_lnki_upright, fld_lnki_w, fld_lnki_h, fld_lnki_time, fld_lnki_page)));
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = select_stmt.Clear()
			.Crt_int		(fld_lnki_wiki		, lnki_wiki)
			.Crt_bry_as_str	(fld_lnki_ttl		, lnki_ttl)
			.Crt_int		(fld_lnki_type		, lnki_type)
			.Crt_double		(fld_lnki_upright	, lnki_upright)
			.Crt_int		(fld_lnki_w			, lnki_w)
			.Crt_int		(fld_lnki_h			, lnki_h)
			.Crt_double		(fld_lnki_time		, lnki_time)
			.Crt_int		(fld_lnki_page		, lnki_page)
			.Exec_select_as_rdr();
			return rdr.Move_next() ? new_itm(rdr) : Xofc_cache_itm.Null;
		}
		finally {rdr.Rls();}
	}
	public void Select_all(Bry_bfr fil_key_bldr, OrderedHash hash) {
		hash.Clear();
		Db_stmt stmt = conn.Stmt_select(tbl_name, flds, Db_meta_fld.Ary_empy);
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = stmt.Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Xofc_cache_itm itm = new_itm(rdr);
				byte[] key = Bry_.Empty;
				hash.Add(key, itm);
			}
		}
		finally {rdr.Rls();}
	}
	public void Db_save(Xofc_cache_itm itm) {
		try {
			Db_stmt stmt = stmt_bldr.Get(itm.Db_state());
			switch (itm.Db_state()) {
				case Db_cmd_mode.Tid_create:	stmt.Clear().Val_int(fld_uid, itm.Uid());	Db_save_modify(stmt, itm); stmt.Exec_insert(); break;
				case Db_cmd_mode.Tid_update:	stmt.Clear();								Db_save_modify(stmt, itm); stmt.Crt_int(fld_uid, itm.Uid()).Exec_update(); break;
				case Db_cmd_mode.Tid_delete:	stmt.Clear().Crt_int(fld_uid, itm.Uid()); stmt.Exec_delete();	break;
				case Db_cmd_mode.Tid_ignore:	break;
				default:						throw Err_.unhandled(itm.Db_state());
			}
			itm.Db_state_(Db_cmd_mode.Tid_ignore);
		} catch (Exception e) {stmt_bldr.Rls(); throw Err_.err_(e, Err_.Message_gplx(e));}
	}
	private void Db_save_modify(Db_stmt stmt, Xofc_cache_itm itm) {
		stmt.Val_int			(itm.Lnki_wiki())
			.Val_bry_as_str		(itm.Lnki_ttl())
			.Val_int			(itm.Lnki_type())
			.Val_double			(itm.Lnki_upright())
			.Val_int			(itm.Lnki_w())
			.Val_int			(itm.Lnki_h())
			.Val_double			(itm.Lnki_time())
			.Val_int			(itm.Lnki_page())
			.Val_int			(itm.Orig_wiki())
			.Val_bry_as_str		(itm.Orig_ttl())
			.Val_int			(itm.Orig_ext())
			.Val_int			(itm.File_w())
			.Val_int			(itm.File_h())
			.Val_double			(itm.File_time())
			.Val_int			(itm.File_page())
			.Val_long			(itm.Temp_file_size())
			.Val_int			(itm.Temp_view_count())
			.Val_long			(itm.Temp_view_date())
			.Val_int			(itm.Temp_w())
			;
	}
	private Xofc_cache_itm new_itm(Db_rdr rdr) {
		return new Xofc_cache_itm
		( lnki_key_bfr
		, rdr.Read_int			(fld_uid)
		, Db_cmd_mode.Tid_ignore
		, rdr.Read_int			(fld_lnki_wiki)
		, rdr.Read_bry_by_str	(fld_lnki_ttl)
		, rdr.Read_int			(fld_lnki_type)
		, rdr.Read_double		(fld_lnki_upright)
		, rdr.Read_int			(fld_lnki_w)
		, rdr.Read_int			(fld_lnki_h)
		, Xof_lnki_time.Db_load_double	(rdr, fld_lnki_time)
		, Xof_lnki_page.Db_load_int		(rdr, fld_lnki_page)
		, rdr.Read_int			(fld_orig_wiki)
		, rdr.Read_bry_by_str	(fld_orig_ttl)
		, rdr.Read_int			(fld_orig_ext)
		, rdr.Read_int			(fld_file_w)
		, rdr.Read_int			(fld_file_h)
		, Xof_lnki_time.Db_load_double	(rdr, fld_file_time)
		, Xof_lnki_page.Db_load_int		(rdr, fld_file_page)
		, rdr.Read_long			(fld_temp_file_size)
		, rdr.Read_int			(fld_temp_view_count)
		, rdr.Read_long			(fld_temp_view_date)
		, rdr.Read_int			(fld_temp_w)
		);
	}
}

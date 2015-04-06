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
public class Xof_cache_tbl implements RlsAble {
	private String tbl_name = "user_file_cache"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String
	  fld_lnki_site, fld_lnki_ttl, fld_lnki_type, fld_lnki_upright, fld_lnki_w, fld_lnki_h, fld_lnki_time, fld_lnki_page
	, fld_orig_wiki, fld_orig_ttl, fld_orig_ext, fld_file_w, fld_file_h, fld_file_time, fld_file_page
	, fld_temp_file_size, fld_temp_view_count, fld_temp_view_date, fld_temp_w
	;
	private final Db_conn conn; private final Db_stmt_bldr stmt_bldr = new Db_stmt_bldr(); private Db_stmt select_stmt;
	private final Bry_bfr lnki_key_bfr = Bry_bfr.reset_(255);
	public Db_conn Conn() {return conn;}
	public Xof_cache_tbl(Db_conn conn) {
		this.conn = conn;
		fld_lnki_site		= flds.Add_int("lnki_site");
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
		stmt_bldr.Conn_(conn, tbl_name, flds, fld_lnki_site, fld_lnki_ttl, fld_lnki_type, fld_lnki_upright, fld_lnki_w, fld_lnki_h, fld_lnki_time, fld_lnki_page);
		conn.Rls_reg(this);
	}
	public void Rls() {
		select_stmt = Db_stmt_.Rls(select_stmt);
	}
	public void Create_tbl() {
		Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
		, Db_meta_idx.new_unique_by_tbl(tbl_name, "main", fld_lnki_site, fld_lnki_ttl, fld_lnki_type, fld_lnki_upright, fld_lnki_w, fld_lnki_h, fld_lnki_time, fld_lnki_page)
		, Db_meta_idx.new_normal_by_tbl(tbl_name, "size", fld_temp_file_size)
		, Db_meta_idx.new_normal_by_tbl(tbl_name, "date", fld_temp_view_date)
		);
		conn.Ddl_create_tbl(meta);
	}
	public Xof_cache_itm Select_one(int lnki_site, byte[] lnki_ttl, int lnki_type, double lnki_upright, int lnki_w, int lnki_h, double lnki_time, int lnki_page) {
		if (select_stmt == null) select_stmt = conn.Stmt_select(tbl_name, flds, String_.Ary(fld_lnki_site, fld_lnki_ttl, fld_lnki_type, fld_lnki_upright, fld_lnki_w, fld_lnki_h, fld_lnki_time, fld_lnki_page));
		Db_rdr rdr = select_stmt.Clear()
			.Crt_int		(fld_lnki_site		, lnki_site)
			.Crt_bry_as_str	(fld_lnki_ttl		, lnki_ttl)
			.Crt_int		(fld_lnki_type		, lnki_type)
			.Crt_double		(fld_lnki_upright	, lnki_upright)
			.Crt_int		(fld_lnki_w			, lnki_w)
			.Crt_int		(fld_lnki_h			, lnki_h)
			.Crt_double		(fld_lnki_time		, lnki_time)
			.Crt_int		(fld_lnki_page		, lnki_page)
			.Exec_select__rls_manual();
		try {return rdr.Move_next() ? new_itm(rdr) : Xof_cache_itm.Null;}
		finally {rdr.Rls();}
	}
	public void Select_all(Bry_bfr fil_key_bldr, OrderedHash hash) {
		hash.Clear();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, Db_meta_fld.Ary_empy).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Xof_cache_itm itm = new_itm(rdr);
				byte[] key = Bry_.Empty;
				hash.Add(key, itm);
			}
		}
		finally {rdr.Rls();}
	}
	public long Select_max_size() {
		Db_rdr rdr = conn.Stmt_new(gplx.dbs.qrys.Db_qry_sql.rdr_(String_.Format("SELECT Max({0}) FROM {1}", fld_temp_file_size, tbl_name))).Exec_select__rls_auto();
		try {
			return rdr.Move_next() ? rdr.Read_long(fld_temp_file_size) : 0;
		}
		finally {rdr.Rls();}
	}
	public void Db_save(Xof_cache_itm itm) {
		try {
			Db_stmt stmt = stmt_bldr.Get(itm.Db_state());
			switch (itm.Db_state()) {
				case Db_cmd_mode.Tid_create:	stmt.Clear(); Db_save_crt(stmt, itm, Bool_.Y);	Db_save_val(stmt, itm); stmt.Exec_insert(); break;
				case Db_cmd_mode.Tid_update:	stmt.Clear();									Db_save_val(stmt, itm); Db_save_crt(stmt, itm, Bool_.N); stmt.Exec_update(); break;
				case Db_cmd_mode.Tid_delete:	stmt.Clear(); Db_save_crt(stmt, itm, Bool_.N);	stmt.Exec_delete();	break;
				case Db_cmd_mode.Tid_ignore:	break;
				default:						throw Err_.unhandled(itm.Db_state());
			}
			itm.Db_state_(Db_cmd_mode.Tid_ignore);
		} catch (Exception e) {stmt_bldr.Rls(); throw Err_.err_(e, Err_.Message_gplx(e));}
	}
	private void Db_save_crt(Db_stmt stmt, Xof_cache_itm itm, boolean insert) {
		if (insert) {
			stmt.Val_int			(itm.Lnki_site())
				.Val_bry_as_str		(itm.Lnki_ttl())
				.Val_int			(itm.Lnki_type())
				.Val_double			(itm.Lnki_upright())
				.Val_int			(itm.Lnki_w())
				.Val_int			(itm.Lnki_h())
				.Val_double			(itm.Lnki_time())
				.Val_int			(itm.Lnki_page())
			;
		}
		else {
			stmt.Crt_int			(fld_lnki_site, itm.Lnki_site())
				.Crt_bry_as_str		(fld_lnki_ttl, itm.Lnki_ttl())
				.Crt_int			(fld_lnki_type, itm.Lnki_type())
				.Crt_double			(fld_lnki_upright, itm.Lnki_upright())
				.Crt_int			(fld_lnki_w, itm.Lnki_w())
				.Crt_int			(fld_lnki_h, itm.Lnki_h())
				.Crt_double			(fld_lnki_time, itm.Lnki_time())
				.Crt_int			(fld_lnki_page, itm.Lnki_page())
			;
		}
	}
	private void Db_save_val(Db_stmt stmt, Xof_cache_itm itm) {
		stmt.Val_int			(itm.Lnki_site())
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
	private Xof_cache_itm new_itm(Db_rdr rdr) {
		return new Xof_cache_itm
		( lnki_key_bfr
		, Db_cmd_mode.Tid_ignore
		, rdr.Read_int			(fld_lnki_site)
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

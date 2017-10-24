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
import gplx.dbs.*;
public class Xou_cache_tbl implements Rls_able {
	private String tbl_name = "file_cache"; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private String
	  fld_lnki_wiki_abrv, fld_lnki_ttl, fld_lnki_type, fld_lnki_upright, fld_lnki_w, fld_lnki_h, fld_lnki_time, fld_lnki_page, fld_user_thumb_w
	, fld_orig_repo, fld_orig_ttl, fld_orig_ext, fld_orig_w, fld_orig_h
	, fld_html_w, fld_html_h, fld_html_time, fld_html_page
	, fld_file_is_orig, fld_file_w, fld_file_time, fld_file_page
	, fld_file_size, fld_view_count, fld_view_date
	;
	public String Tbl_name() {return tbl_name;}
	public String Fld_orig_ttl() {return fld_orig_ttl;}
	private final    Db_conn conn; private final    Db_stmt_bldr stmt_bldr = new Db_stmt_bldr(); private Db_stmt select_stmt;
	private final    Bry_bfr lnki_key_bfr = Bry_bfr_.Reset(255);
	public Db_conn Conn() {return conn;}
	public Xou_cache_tbl(Db_conn conn) {
		this.conn = conn;
		fld_lnki_wiki_abrv	= flds.Add_str("lnki_wiki_abrv", 255);
		fld_lnki_ttl		= flds.Add_str("lnki_ttl", 255);
		fld_lnki_type		= flds.Add_int("lnki_type");
		fld_lnki_upright	= flds.Add_double("lnki_upright");
		fld_lnki_w			= flds.Add_int("lnki_w");
		fld_lnki_h			= flds.Add_int("lnki_h");
		fld_lnki_time		= flds.Add_double("lnki_time");
		fld_lnki_page		= flds.Add_int("lnki_page");
		fld_user_thumb_w	= flds.Add_int("user_thumb_w");
		fld_orig_repo		= flds.Add_int("orig_repo");
		fld_orig_ttl		= flds.Add_str("orig_ttl", 255);
		fld_orig_ext		= flds.Add_int("orig_ext");
		fld_orig_w			= flds.Add_int("orig_w");
		fld_orig_h			= flds.Add_int("orig_h");
		fld_html_w			= flds.Add_int("html_w");
		fld_html_h			= flds.Add_int("html_h");
		fld_html_time		= flds.Add_double("html_time");
		fld_html_page		= flds.Add_int("html_page");
		fld_file_is_orig	= flds.Add_bool("file_is_orig");
		fld_file_w			= flds.Add_int("file_w");
		fld_file_time		= flds.Add_double("file_time");
		fld_file_page		= flds.Add_int("file_page");
		fld_file_size		= flds.Add_long("file_size");
		fld_view_count		= flds.Add_int("view_count");
		fld_view_date		= flds.Add_long("view_date");
		stmt_bldr.Conn_(conn, tbl_name, flds, fld_lnki_wiki_abrv, fld_lnki_ttl, fld_lnki_type, fld_lnki_upright, fld_lnki_w, fld_lnki_h, fld_lnki_time, fld_lnki_page, fld_user_thumb_w);
		conn.Rls_reg(this);
	}
	public void Rls() {
		select_stmt = Db_stmt_.Rls(select_stmt);
	}
	public void Create_tbl() {
		Dbmeta_tbl_itm meta = Dbmeta_tbl_itm.New(tbl_name, flds
		, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "main", fld_lnki_wiki_abrv, fld_lnki_ttl, fld_lnki_type, fld_lnki_upright, fld_lnki_w, fld_lnki_h, fld_lnki_time, fld_lnki_page, fld_user_thumb_w)
		, Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "size", fld_file_size)
		, Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "date", fld_view_date)
		);
		conn.Meta_tbl_create(meta);
	}
	public Xou_cache_itm Select_one(byte[] lnki_wiki_abrv, byte[] lnki_ttl, int lnki_type, double lnki_upright, int lnki_w, int lnki_h, double lnki_time, int lnki_page, int user_thumb_w) {
		if (select_stmt == null) select_stmt = conn.Stmt_select(tbl_name, flds, String_.Ary(fld_lnki_wiki_abrv, fld_lnki_ttl, fld_lnki_type, fld_lnki_upright, fld_lnki_w, fld_lnki_h, fld_lnki_time, fld_lnki_page, fld_user_thumb_w));
		Db_rdr rdr = select_stmt.Clear()
			.Crt_bry_as_str	(fld_lnki_wiki_abrv	, lnki_wiki_abrv)
			.Crt_bry_as_str	(fld_lnki_ttl		, lnki_ttl)
			.Crt_int		(fld_lnki_type		, lnki_type)
			.Crt_double		(fld_lnki_upright	, lnki_upright)
			.Crt_int		(fld_lnki_w			, lnki_w)
			.Crt_int		(fld_lnki_h			, lnki_h)
			.Crt_double		(fld_lnki_time		, lnki_time)
			.Crt_int		(fld_lnki_page		, lnki_page)
			.Crt_int		(fld_user_thumb_w	, user_thumb_w)
			.Exec_select__rls_manual();
		try {return rdr.Move_next() ? new_itm(rdr) : Xou_cache_itm.Null;}
		finally {rdr.Rls();}
	}
	public void Select_all(Bry_bfr fil_key_bldr, Ordered_hash hash) {
		hash.Clear();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, Dbmeta_fld_itm.Str_ary_empty).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Xou_cache_itm itm = new_itm(rdr);
				hash.Add(itm.Lnki_key(), itm);
			}
		}
		finally {rdr.Rls();}
	}
	public void Db_save(Xou_cache_itm itm) {
		try {
			Db_stmt stmt = stmt_bldr.Get(itm.Db_state());
			switch (itm.Db_state()) {
				case Db_cmd_mode.Tid_create:	stmt.Clear(); Db_save_crt(stmt, itm, Bool_.Y);	Db_save_val(stmt, itm); stmt.Exec_insert(); break;
				case Db_cmd_mode.Tid_update:	stmt.Clear();									Db_save_val(stmt, itm); Db_save_crt(stmt, itm, Bool_.N); stmt.Exec_update(); break;
				case Db_cmd_mode.Tid_delete:	stmt.Clear(); Db_save_crt(stmt, itm, Bool_.N);	stmt.Exec_delete();	break;
				case Db_cmd_mode.Tid_ignore:	break;
				default:						throw Err_.new_unhandled(itm.Db_state());
			}
			itm.Db_state_(Db_cmd_mode.Tid_ignore);
		} catch (Exception e) {stmt_bldr.Rls(); throw Err_.new_exc(e, "xo", "db_save failed");}
	}
	@gplx.Internal protected Db_rdr Select_all_for_test() {return conn.Stmt_select(tbl_name, flds, Dbmeta_fld_itm.Str_ary_empty).Exec_select__rls_manual();}
	private void Db_save_crt(Db_stmt stmt, Xou_cache_itm itm, boolean insert) {
		if (insert) {
			stmt.Val_bry_as_str		(fld_lnki_wiki_abrv		, itm.Lnki_wiki_abrv())
				.Val_bry_as_str		(fld_lnki_ttl			, itm.Lnki_ttl())
				.Val_int			(fld_lnki_type			, itm.Lnki_type())
				.Val_double			(fld_lnki_upright		, itm.Lnki_upright())
				.Val_int			(fld_lnki_w				, itm.Lnki_w())
				.Val_int			(fld_lnki_h				, itm.Lnki_h())
				.Val_double			(fld_lnki_time			, itm.Lnki_time())
				.Val_int			(fld_lnki_page			, itm.Lnki_page())
				.Val_int			(fld_user_thumb_w		, itm.User_thumb_w())
			;
		}
		else {
			stmt.Crt_bry_as_str		(fld_lnki_wiki_abrv		, itm.Lnki_wiki_abrv())
				.Crt_bry_as_str		(fld_lnki_ttl			, itm.Lnki_ttl())
				.Crt_int			(fld_lnki_type			, itm.Lnki_type())
				.Crt_double			(fld_lnki_upright		, itm.Lnki_upright())
				.Crt_int			(fld_lnki_w				, itm.Lnki_w())
				.Crt_int			(fld_lnki_h				, itm.Lnki_h())
				.Crt_double			(fld_lnki_time			, itm.Lnki_time())
				.Crt_int			(fld_lnki_page			, itm.Lnki_page())
				.Crt_int			(fld_user_thumb_w		, itm.User_thumb_w())
			;
		}
	}
	private void Db_save_val(Db_stmt stmt, Xou_cache_itm itm) {
		byte[] orig_ttl = itm.Orig_ttl(), lnki_ttl = itm.Lnki_ttl();
		if (Bry_.Eq(orig_ttl, lnki_ttl)) orig_ttl = Bry_.Empty;	// PERF:only store redirects in orig_ttl; DATE:2015-05-14
		stmt.Val_int			(fld_orig_repo			, itm.Orig_repo_id())
			.Val_bry_as_str		(fld_orig_ttl			, orig_ttl)
			.Val_int			(fld_orig_ext			, itm.Orig_ext_id())
			.Val_int			(fld_orig_w				, itm.Orig_w())
			.Val_int			(fld_orig_h				, itm.Orig_h())
			.Val_int			(fld_html_w				, itm.Html_w())
			.Val_int			(fld_html_h				, itm.Html_h())
			.Val_double			(fld_html_time			, itm.Html_time())
			.Val_int			(fld_html_page			, itm.Html_page())
			.Val_bool_as_byte	(fld_file_is_orig		, itm.File_is_orig())
			.Val_int			(fld_file_w				, itm.File_w())
			.Val_double			(fld_file_time			, itm.File_time())
			.Val_int			(fld_file_page			, itm.File_page())
			.Val_long			(fld_file_size			, itm.File_size())
			.Val_int			(fld_view_count			, itm.View_count())
			.Val_long			(fld_view_date			, itm.View_date())
			;
	}
	private Xou_cache_itm new_itm(Db_rdr rdr) {
		byte[] lnki_ttl = rdr.Read_bry_by_str			(fld_lnki_ttl);
		byte[] orig_ttl = rdr.Read_bry_by_str			(fld_orig_ttl);
		if (orig_ttl.length == 0) orig_ttl = lnki_ttl;	// PERF:only store redirects in orig_ttl; DATE:2015-05-14
		return new Xou_cache_itm
		( lnki_key_bfr
		, Db_cmd_mode.Tid_ignore
		, rdr.Read_bry_by_str			(fld_lnki_wiki_abrv)
		, lnki_ttl
		, rdr.Read_int					(fld_lnki_type)
		, rdr.Read_double				(fld_lnki_upright)
		, rdr.Read_int					(fld_lnki_w)
		, rdr.Read_int					(fld_lnki_h)
		, Xof_lnki_time.Db_load_double	(rdr, fld_lnki_time)
		, Xof_lnki_page.Db_load_int		(rdr, fld_lnki_page)
		, rdr.Read_int					(fld_user_thumb_w)
		, rdr.Read_int					(fld_orig_repo)
		, orig_ttl
		, rdr.Read_int					(fld_orig_ext)
		, rdr.Read_int					(fld_orig_w)
		, rdr.Read_int					(fld_orig_h)
		, rdr.Read_int					(fld_html_w)
		, rdr.Read_int					(fld_html_h)
		, Xof_lnki_time.Db_load_double	(rdr, fld_html_time)
		, Xof_lnki_page.Db_load_int		(rdr, fld_html_page)
		, rdr.Read_bool_by_byte			(fld_file_is_orig)
		, rdr.Read_int					(fld_file_w)
		, Xof_lnki_time.Db_load_double	(rdr, fld_file_time)
		, Xof_lnki_page.Db_load_int		(rdr, fld_file_page)
		, rdr.Read_long					(fld_file_size)
		, rdr.Read_int					(fld_view_count)
		, rdr.Read_long					(fld_view_date)
		);
	}
}

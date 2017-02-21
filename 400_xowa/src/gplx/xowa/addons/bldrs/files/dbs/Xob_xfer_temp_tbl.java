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
package gplx.xowa.addons.bldrs.files.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.wikis.dbs.*; import gplx.xowa.files.*;
public class Xob_xfer_temp_tbl {
	public static void Create_table(Db_conn p)		{Sqlite_engine_.Tbl_create_and_delete(p, Tbl_name, Tbl_sql);}
	public static Db_stmt Insert_stmt(Db_conn p) {return Db_stmt_.new_insert_(p, Tbl_name, Fld_lnki_id, Fld_lnki_tier_id, Fld_lnki_page_id, Fld_orig_repo, Fld_orig_page_id, Fld_lnki_ttl, Fld_orig_redirect_src, Fld_lnki_ext, Fld_lnki_type, Fld_orig_media_type, Fld_file_is_orig, Fld_orig_w, Fld_orig_h, Fld_file_w, Fld_file_h, Fld_html_w, Fld_html_h, Fld_lnki_w, Fld_lnki_h, Fld_lnki_upright, Fld_lnki_time, Fld_lnki_page, Fld_lnki_count);}
	public static void Insert(Db_stmt stmt, int lnki_id, int lnki_tier_id, int lnki_page_id, byte repo_id
		, int page_id, String ttl, String redirect_src, int ext_id, byte lnki_type, String orig_media_type
		, boolean file_is_orig, int orig_w, int orig_h, int file_w, int file_h, int html_w, int html_h
		, int lnki_w, int lnki_h, double lnki_upright, double thumbtime, int page, int count) {
		stmt.Clear()
		.Val_int(lnki_id)
		.Val_int(lnki_tier_id)
		.Val_int(lnki_page_id)
		.Val_byte(repo_id)
		.Val_int(page_id)
		.Val_str(ttl)
		.Val_str(redirect_src)
		.Val_int(ext_id)
		.Val_byte(lnki_type)
		.Val_str(orig_media_type)
		.Val_bool_as_byte(file_is_orig)
		.Val_int(orig_w)
		.Val_int(orig_h)
		.Val_int(file_w)
		.Val_int(file_h)
		.Val_int(html_w)
		.Val_int(html_h)
		.Val_int(lnki_w)
		.Val_int(lnki_h)
		.Val_double(lnki_upright)
		.Val_double(Xof_lnki_time.Db_save_double(thumbtime))
		.Val_int(page)
		.Val_int(count)
		.Exec_insert();
	}
	public static final    String Tbl_name = "xfer_temp"		
	, Fld_lnki_id = "lnki_id", Fld_lnki_tier_id = "lnki_tier_id", Fld_lnki_page_id = "lnki_page_id", Fld_lnki_ttl = "lnki_ttl", Fld_lnki_ext = "lnki_ext", Fld_lnki_type = "lnki_type"
	, Fld_lnki_w = "lnki_w", Fld_lnki_h = "lnki_h", Fld_lnki_upright = "lnki_upright", Fld_lnki_time = "lnki_time", Fld_lnki_page = "lnki_page", Fld_lnki_count = "lnki_count"
	, Fld_orig_repo = "orig_repo", Fld_orig_page_id = "orig_page_id", Fld_orig_redirect_src = "orig_redirect_src", Fld_orig_media_type = "orig_media_type"
	, Fld_orig_w = "orig_w", Fld_orig_h = "orig_h"
	, Fld_file_w = "file_w", Fld_file_h = "file_h", Fld_file_is_orig = "file_is_orig"
	, Fld_html_w = "html_w", Fld_html_h = "html_h"
	;
	private static final    String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS xfer_temp"
	,	"( lnki_id             integer             NOT NULL			    PRIMARY KEY"
	,	", lnki_tier_id        integer             NOT NULL"
	,	", lnki_page_id        integer             NOT NULL"
	,	", lnki_ttl            varchar(255)        NOT NULL"
	,	", lnki_ext            integer             NOT NULL"
	,	", lnki_type           integer             NOT NULL"
	,	", lnki_w              integer             NOT NULL"
	,	", lnki_h              integer             NOT NULL"
	,	", lnki_upright        double              NOT NULL"
	,	", lnki_time           double              NOT NULL"
	,	", lnki_page           integer             NOT NULL"
	,	", lnki_count          integer             NOT NULL"
	,	", orig_repo           integer             NOT NULL"
	,	", orig_page_id        integer             NOT NULL"
	,	", orig_redirect_src   varchar(255)        NOT NULL"
	,	", orig_media_type     varchar(64)         NOT NULL"
	,	", orig_w              integer             NOT NULL"
	,	", orig_h              integer             NOT NULL"
	,	", file_is_orig        tinyint             NOT NULL"
	,	", file_w              integer             NOT NULL"
	,	", file_h              integer             NOT NULL"
	,	", html_w              integer             NOT NULL"
	,	", html_h              integer             NOT NULL"
	,	");"
	);
}

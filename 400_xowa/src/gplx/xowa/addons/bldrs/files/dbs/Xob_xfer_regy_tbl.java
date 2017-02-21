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
import gplx.core.stores.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.dbs.engines.sqlite.*;
import gplx.xowa.bldrs.*;
public class Xob_xfer_regy_tbl {
	public static final    String Tbl_name = "xfer_regy"
	, Fld_lnki_id = "lnki_id", Fld_lnki_tier_id = "lnki_tier_id", Fld_lnki_page_id = "lnki_page_id", Fld_lnki_ttl = "lnki_ttl", Fld_lnki_ext = "lnki_ext"
	, Fld_lnki_time = "lnki_time", Fld_lnki_page = "lnki_page", Fld_lnki_count = "lnki_count"
	, Fld_orig_repo = "orig_repo", Fld_orig_page_id = "orig_page_id", Fld_orig_redirect_src = "orig_redirect_src", Fld_orig_media_type = "orig_media_type"
	, Fld_orig_w = "orig_w", Fld_orig_h = "orig_h"
	, Fld_file_w = "file_w", Fld_file_h = "file_h", Fld_file_is_orig = "file_is_orig"		
	, Fld_xfer_status = "xfer_status"
	;
	public static void Create_table(Db_conn p) {Sqlite_engine_.Tbl_create_and_delete(p, Tbl_name, Tbl_sql);}
	public static void Create_data(Gfo_usr_dlg usr_dlg, Db_conn p) {
		p.Exec_sql(Sql_create_data_orig);
		p.Exec_sql(Sql_create_data_thumb);
	}
	public static void Create_index(Gfo_usr_dlg usr_dlg, Db_conn p)	{Sqlite_engine_.Idx_create(usr_dlg, p, Xob_db_file.Name__file_make, Idx_lnki_page_id, Idx_lnki_ttl);}
	public static DataRdr Select(Db_conn conn, byte repo_id, byte[] ttl, int limit) {
		Db_qry qry = Db_qry_.select_().Cols_all_()
			.From_(Tbl_name)
			.Where_(gplx.core.criterias.Criteria_.And_many(Db_crt_.New_mte(Fld_orig_repo, repo_id), Db_crt_.New_mt(Fld_lnki_ttl, String_.new_u8(ttl)), Db_crt_.New_eq(Fld_xfer_status, 0)))
			.Order_asc_many_(Fld_xfer_status, Fld_orig_repo, Fld_lnki_ttl, Fld_file_w)
			.Limit_(limit)
			;
		return conn.Exec_qry_as_old_rdr(qry);
	}
	public static Db_stmt Select_by_page_id_stmt(Db_conn p) {return p.Stmt_sql(Sql_select_clause);}
	public static DataRdr Select_by_page_id(Db_stmt stmt, int page_id, int limit) {return stmt.Val_int(page_id).Val_int(limit).Exec_select();}
	private static final    String
	  Sql_select_clause = String_.Concat_lines_nl
		( "SELECT   *"
		, "FROM     xfer_regy"
		, "WHERE    xfer_status  =  0"
		, "AND      lnki_page_id >= ?"
		, "ORDER BY lnki_tier_id, lnki_page_id, lnki_id"
		, "LIMIT    ?"
		)
	, Sql_select_total_pending = String_.Concat_lines_nl
		( "SELECT   Count(*) AS CountAll"
		, "FROM     xfer_regy"
		, "WHERE    xfer_status  =  0"
		)
	;
	public static DataRdr Select_by_tier_page(Db_conn conn, int tier_id, int page_id, int select_interval) {
		Db_qry qry = Db_qry_.select_().Cols_all_()
			.From_(Tbl_name)
			.Where_(gplx.core.criterias.Criteria_.And_many(Db_crt_.New_eq(Fld_xfer_status, 0), Db_crt_.New_eq(Fld_lnki_tier_id, tier_id), Db_crt_.New_mte(Fld_lnki_page_id, page_id)))
			.Order_asc_many_(Fld_lnki_tier_id, Fld_lnki_page_id, Fld_lnki_id)
			.Limit_(select_interval)
			;
		return conn.Exec_qry_as_old_rdr(qry);
	}
	public static int Select_total_pending(Db_conn p) {
		DataRdr rdr = p.Exec_sql_as_old_rdr(Sql_select_total_pending);
		int rv = 0;
		if (rdr.MoveNextPeer())
			rv = rdr.ReadInt("CountAll");
		rdr.Rls();
		return rv;
	}
	private static final    String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS xfer_regy"
	,	"( lnki_id             integer             NOT NULL			    PRIMARY KEY"
	,	", lnki_tier_id        integer             NOT NULL"
	,	", lnki_page_id        integer             NOT NULL"
	,	", lnki_ttl            varchar(255)        NOT NULL"
	,	", lnki_ext            integer             NOT NULL"
	,	", lnki_time           double              NOT NULL"
	,	", lnki_page           integer             NOT NULL"
	,	", lnki_count          integer             NOT NULL"
	,	", orig_repo           tinyint             NOT NULL"
	,	", orig_page_id        integer             NOT NULL"
	,	", orig_redirect_src   varchar(255)        NOT NULL"
	,	", orig_media_type     varchar(64)         NOT NULL"
	,	", orig_w              integer             NOT NULL"
	,	", orig_h              integer             NOT NULL"
	,	", file_is_orig        tinyint             NOT NULL"
	,	", file_w              integer             NOT NULL"
	,	", file_h              integer             NOT NULL"
	,	", xfer_status         integer             NOT NULL"
	,	");"
	);
	private static final    String Sql_create_data_orig = String_.Concat_lines_nl
	( "INSERT INTO xfer_regy "
	, "( lnki_id, lnki_tier_id, lnki_page_id, orig_page_id, orig_repo, lnki_ttl, orig_redirect_src, lnki_ext, orig_media_type"
	, ", file_is_orig, orig_w, orig_h, file_w, file_h, lnki_time, lnki_page, lnki_count"
	, ", xfer_status"
	, ")"
	, "SELECT "
	, "  Min(lnki_id), Min(lnki_tier_id), Min(lnki_page_id), Min(orig_page_id), orig_repo, lnki_ttl, Max(orig_redirect_src), lnki_ext, orig_media_type"	// NOTE: Max(orig_redirect_src) not Min (else would get '')
	, ", file_is_orig, orig_w, orig_h, -1, -1, lnki_time, lnki_page, Sum(lnki_count)"
	, ", 0"
	, "FROM    xfer_temp x"
	, "WHERE   file_is_orig = 1"
	, "GROUP BY orig_repo, lnki_ttl, lnki_ext, orig_media_type, file_is_orig, orig_w, orig_h, lnki_time, lnki_page"
	);
	private static final    String Sql_create_data_thumb = String_.Concat_lines_nl
	( "INSERT INTO xfer_regy "
	, "( lnki_id, lnki_tier_id, lnki_page_id, orig_page_id, orig_repo, lnki_ttl, orig_redirect_src, lnki_ext, orig_media_type"
	, ", file_is_orig, orig_w, orig_h, file_w, file_h, lnki_time, lnki_page, lnki_count"
	, ", xfer_status"
	, ")"
	, "SELECT "
	, "  Min(lnki_id), Min(lnki_tier_id), Min(lnki_page_id), Min(orig_page_id), orig_repo, lnki_ttl, Max(orig_redirect_src), lnki_ext, orig_media_type"
	, ", file_is_orig, orig_w, orig_h, file_w, file_h, lnki_time, lnki_page, Sum(lnki_count)"
	, ", 0"
	, "FROM    xfer_temp x"
	, "WHERE   file_is_orig = 0"
	, "GROUP BY orig_repo, lnki_ttl, lnki_ext, orig_media_type, file_is_orig, orig_w, orig_h, file_w, file_h, lnki_time, lnki_page"
	);
	private static final    Db_idx_itm
//		  Idx_select		= Db_idx_itm.sql_("CREATE        INDEX IF NOT EXISTS xfer_regy__select        ON xfer_regy (xfer_status, orig_repo, lnki_ttl, file_w);")
	  Idx_lnki_page_id	= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS xfer_regy__lnki_page_id  ON xfer_regy (xfer_status, lnki_tier_id, lnki_page_id, lnki_id, orig_repo, file_is_orig, lnki_ttl, lnki_ext, lnki_time, lnki_page, file_w, file_h);")
	, Idx_lnki_ttl		= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS xfer_regy__lnki_ttl ON xfer_regy (lnki_ttl);")	// needed for troubleshooting
	;
	public static byte Status_todo = 0, Status_pass = 1, Status_fail = 2, Status_ignore_processed = 3;
}

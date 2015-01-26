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
package gplx.xowa.files.wiki_orig; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.files.fsdb.*;
public class Xof_wiki_orig_tbl {
	public static void Create_table(Db_conn p) {
		Sqlite_engine_.Tbl_create(p, Tbl_name, Tbl_sql);
		Sqlite_engine_.Idx_create(p, Idx_key);
	}
	public static void Select_list(Cancelable cancelable, Db_conn p, Xodb_ctx db_ctx, byte exec_tid, ListAdp itms, OrderedHash hash, Xof_url_bldr url_bldr, Xow_repo_mgr repo_mgr) {
		Xof_wiki_orig_tbl_in_wkr in_wkr = new Xof_wiki_orig_tbl_in_wkr();
		in_wkr.Init(itms, hash);
		in_wkr.Select_in(p, cancelable, db_ctx, 0, itms.Count());
		Xof_wiki_orig_tbl_evaluator.Rdr_done(exec_tid, itms, hash, url_bldr, repo_mgr);
	}
	public static Xof_orig_regy_itm Select_itm(Db_conn p, byte[] ttl) {
		Xof_orig_regy_itm rv = Xof_orig_regy_itm.Null;
		DataRdr rdr = Db_qry_.select_().From_(Tbl_name).Cols_all_().Where_(Db_crt_.eq_(String_.new_utf8_(ttl))).Exec_qry_as_rdr(p);
		if (rdr.MoveNextPeer())
			rv = Xof_orig_regy_itm.load_(rdr);
		rdr.Rls();
		return rv;
	}
	public static boolean Select_itm_exists(Db_conn p, byte[] ttl) {
		Object o =  Db_qry_.select_val_(Tbl_name, Fld_uid, Db_crt_.eq_(Fld_orig_ttl, String_.new_utf8_(ttl))).ExecRdr_val(p);
		return o != null;
	}
	public static void Insert(Db_conn p, byte[] ttl, byte status, byte orig_repo, byte[] orig_redirect, int orig_ext, int orig_w, int orig_h) {
		Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = Xof_wiki_orig_tbl.Insert_stmt(p);
			Insert(stmt, ttl, status, orig_repo, orig_redirect, orig_ext, orig_w, orig_h);
		}	finally {stmt.Rls();}
	}
	public static Db_stmt Insert_stmt(Db_conn p) {return Db_stmt_.new_insert_(p, Tbl_name, Fld_orig_ttl, Fld_status, Fld_orig_repo, Fld_orig_ext, Fld_orig_w, Fld_orig_h, Fld_orig_redirect);}
	public static void Insert(Db_stmt stmt, byte[] ttl, byte status, byte orig_repo, byte[] orig_redirect, int orig_ext, int orig_w, int orig_h) {
		stmt.Clear()
		.Val_bry_as_str(ttl)
		.Val_byte(status)
		.Val_byte(orig_repo)
		.Val_int(orig_ext)
		.Val_int(orig_w)
		.Val_int(orig_h)
		.Val_bry_as_str(orig_redirect)
		.Exec_insert();
	}	
	public static final String Tbl_name = "wiki_orig"
	, Fld_uid = "uid", Fld_orig_ttl = "orig_ttl", Fld_status = "status"
	, Fld_orig_repo = "orig_repo", Fld_orig_ext = "orig_ext", Fld_orig_w = "orig_w", Fld_orig_h = "orig_h", Fld_orig_redirect = "orig_redirect"
	;
	private static final Db_idx_itm
	  Idx_key     = Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS wiki_orig__ttl ON wiki_orig (orig_ttl);")
	;
	public static final int Ord_uid = 0, Ord_orig_ttl = 1, Ord_status = 2, Ord_orig_repo = 3, Ord_orig_ext = 4, Ord_orig_w = 5, Ord_orig_h = 6, Ord_orig_redirect = 7; 
	static final String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS wiki_orig"
	,	"( uid               integer             NOT NULL    PRIMARY KEY AUTOINCREMENT"
	,	", orig_ttl          varchar(1024)       NOT NULL"
	,	", status            tinyint             NOT NULL"	// status of qry; 0=fail; 1=pass
	,	", orig_repo         tinyint             NOT NULL"
	,	", orig_ext          integer             NOT NULL"
	,	", orig_w            integer             NOT NULL"
	,	", orig_h            integer             NOT NULL"
	,	", orig_redirect     varchar(1024)       NOT NULL"
	,	");"
	);
}

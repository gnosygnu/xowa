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
package gplx.xowa.bldrs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.dbs.*;
class Xob_lnki_temp_tbl {
	public static void Create_table(Db_conn p) {Sqlite_engine_.Tbl_create(p, Tbl_name, Tbl_sql);}
	public static Db_stmt Insert_stmt(Db_conn p) {return Db_stmt_.new_insert_(p, Tbl_name, Fld_lnki_page_id, Fld_lnki_ttl, Fld_lnki_commons_ttl, Fld_lnki_ext, Fld_lnki_type, Fld_lnki_src_tid, Fld_lnki_w, Fld_lnki_h, Fld_lnki_upright, Fld_lnki_time, Fld_lnki_page);}
	public static void Insert(Db_stmt stmt, int page_id, byte[] ttl, byte[] ttl_commons, byte ext_id, byte img_type, byte lnki_src_tid, int w, int h, double upright, double thumbtime, int page) {
		stmt.Clear()
		.Val_int(page_id)
		.Val_bry_as_str(ttl)
		.Val_bry_as_str(ttl_commons)
		.Val_byte(ext_id)
		.Val_byte(img_type)
		.Val_int(lnki_src_tid)
		.Val_int(w)
		.Val_int(h)
		.Val_double(upright)
		.Val_double(gplx.xowa.files.Xof_doc_thumb.Db_save_double(thumbtime))
		.Val_int(page)
		.Exec_insert();
	}
	public static final String Tbl_name = "lnki_temp"
	, Fld_lnki_id = "lnki_id"
	, Fld_lnki_page_id = "lnki_page_id", Fld_lnki_ttl = "lnki_ttl", Fld_lnki_commons_ttl = "lnki_commons_ttl"
	, Fld_lnki_ext = "lnki_ext", Fld_lnki_type = "lnki_type", Fld_lnki_src_tid = "lnki_src_tid"
	, Fld_lnki_w = "lnki_w", Fld_lnki_h = "lnki_h", Fld_lnki_upright = "lnki_upright"
	, Fld_lnki_time = "lnki_time", Fld_lnki_page = "lnki_page"
	;
	private static final String Tbl_sql = String_.Concat_lines_nl
	( "CREATE TABLE IF NOT EXISTS lnki_temp"	
	, "( lnki_id                integer             NOT NULL		PRIMARY KEY		AUTOINCREMENT" // NOTE: insertion order index
	, ", lnki_page_id           integer             NOT NULL"
	, ", lnki_ttl               varchar(255)        NOT NULL"
	, ", lnki_commons_ttl       varchar(255)        NULL"
	, ", lnki_ext               integer             NOT NULL"
	, ", lnki_type              integer             NOT NULL"
	, ", lnki_src_tid           integer             NOT NULL"
	, ", lnki_w                 integer             NOT NULL"
	, ", lnki_h                 integer             NOT NULL"
	, ", lnki_upright           double              NOT NULL"
	, ", lnki_time              double              NOT NULL"	// thumbtime is float; using double b/c upright does and would like to keep datatypes same; https://bugzilla.wikimedia.org/show_bug.cgi?id=39014
	, ", lnki_page              integer             NOT NULL"
	, ");"
	);
}

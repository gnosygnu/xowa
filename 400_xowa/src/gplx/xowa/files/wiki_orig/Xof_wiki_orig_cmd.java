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
import gplx.dbs.*; import gplx.xowa.bldrs.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.qrys.*; import gplx.xowa.bldrs.oimgs.*;
public class Xof_wiki_orig_cmd extends Xob_itm_basic_base implements Xob_cmd {
	private Db_conn conn;
	public Xof_wiki_orig_cmd(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return KEY_oimg;} public static final String KEY_oimg = "file.wiki_orig";
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		wiki.Init_assert();
		conn = Xof_fsdb_mgr_sql.Wiki_orig_provider(wiki.App().Fsys_mgr().File_dir().GenSubDir(wiki.Domain_str())); 
		Io_url make_db_url = Xodb_db_file.init__file_make(wiki.Fsys_mgr().Root_dir()).Url();
		Sqlite_engine_.Db_attach(conn, "make_db", make_db_url.Raw());
	}
	public void Cmd_run() {Exec();}
	public void Cmd_end() {}
	public void Cmd_print() {}
	private void Exec() {
		usr_dlg.Prog_many("", "", "deleting wiki_orig");		conn.Exec_sql(Sql_delete_wiki_orig);	// always delete wiki_orig, else will not pick up changed sizes / moved repos; DATE:2014-07-21
		usr_dlg.Prog_many("", "", "inserting xfer direct");		conn.Exec_sql(Sql_create_xfer_direct);
		usr_dlg.Prog_many("", "", "inserting xfer redirect");	conn.Exec_sql(Sql_create_xfer_redirect);
		usr_dlg.Prog_many("", "", "inserting orig direct");		conn.Exec_sql(Sql_create_orig_direct);
		usr_dlg.Prog_many("", "", "inserting orig redirect");	conn.Exec_sql(Sql_create_orig_redirect);
	}
	private static final String 
		Sql_delete_wiki_orig = "DELETE FROM wiki_orig;"
	,	Sql_create_xfer_direct = String_.Concat_lines_nl
	(	"INSERT INTO wiki_orig "
	,	"(orig_ttl, status, orig_repo, orig_ext, orig_w, orig_h, orig_redirect)"
	,	"SELECT DISTINCT"
	,	"        xfer.lnki_ttl"
	,	",       1 --pass"
	,	",       xfer.orig_repo"
	,	",       xfer.lnki_ext"
	,	",       xfer.orig_w"
	,	",       xfer.orig_h"
	,	",       ''"
	,	"FROM    make_db.xfer_regy xfer"
	,	"        LEFT JOIN wiki_orig cur ON xfer.lnki_ttl = cur.orig_ttl"
	,	"WHERE   cur.orig_ttl IS NULL"
	)
	,	Sql_create_xfer_redirect = String_.Concat_lines_nl
	(	"INSERT INTO wiki_orig "
	,	"(orig_ttl, status, orig_repo, orig_ext, orig_w, orig_h, orig_redirect)"
	,	"SELECT DISTINCT"
	,	"        xfer.orig_redirect_src"
	,	",       1 --pass"
	,	",       xfer.orig_repo"
	,	",       xfer.lnki_ext"
	,	",       xfer.orig_w"
	,	",       xfer.orig_h"
	,	",       xfer.lnki_ttl"
	,	"FROM    make_db.xfer_regy xfer"
	,	"        LEFT JOIN wiki_orig cur ON xfer.orig_redirect_src = cur.orig_ttl"
	,	"WHERE   cur.orig_ttl IS NULL"
	,	"AND     Coalesce(xfer.orig_redirect_src, '') != ''"
	) 
	,	Sql_create_orig_direct = String_.Concat_lines_nl
	(	"INSERT INTO wiki_orig "
	,	"(orig_ttl, status, orig_repo, orig_ext, orig_w, orig_h, orig_redirect)"
	,	"SELECT DISTINCT"
	,	"        orig.lnki_ttl"
	,	",       0 --unknown"
	,	",       orig.orig_repo"
	,	",       orig.lnki_ext"
	,	",       orig.orig_w"
	,	",       orig.orig_h"
	,	",       ''"
	,	"FROM    make_db.orig_regy orig"
	,	"        LEFT JOIN wiki_orig cur ON orig.lnki_ttl = cur.orig_ttl"
	,	"WHERE   cur.orig_ttl IS NULL"							// not already in wiki_orig
	,	"AND     orig.orig_repo IS NOT NULL"					// not found in oimg_image.sqlite3
	,	"AND     Coalesce(orig.orig_w           , -1) != -1"	// ignore entries that are either ext_id = 0 ("File:1") or don't have any width / height info (makes it useless); need to try to get again from wmf_api
	,	"AND     Coalesce(orig.orig_redirect_ttl, '') == ''"	// direct
	)
	,	Sql_create_orig_redirect = String_.Concat_lines_nl
	(	"INSERT INTO wiki_orig "
	,	"(orig_ttl, status, orig_repo, orig_ext, orig_w, orig_h, orig_redirect)"
	,	"SELECT DISTINCT"
	,	"        orig.orig_redirect_ttl"
	,	",       0 --unknown"
	,	",       orig.orig_repo"
	,	",       orig.lnki_ext"
	,	",       orig.orig_w"
	,	",       orig.orig_h"
	,	",       ''"
	,	"FROM    make_db.orig_regy orig"
	,	"        LEFT JOIN wiki_orig cur ON orig.orig_redirect_ttl = cur.orig_ttl"
	,	"WHERE   cur.orig_ttl IS NULL"							// not already in wiki_orig
	,	"AND     orig.orig_repo IS NOT NULL"					// not found in oimg_image.sqlite3
	,	"AND     Coalesce(orig.orig_w,            -1) != -1"	// ignore entries that are either ext_id = 0 ("File:1") or don't have any width / height info (makes it useless); need to try to get again from wmf_api
	,	"AND     Coalesce(orig.orig_redirect_ttl, '') != ''"	// redirect
	)
	; 
}

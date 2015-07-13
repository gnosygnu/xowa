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
package gplx.xowa.bldrs.cmds.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.dbs.*; import gplx.xowa.files.*; import gplx.xowa.bldrs.cmds.files.*;
public class Xob_xfer_update_cmd extends Xob_itm_basic_base implements Xob_cmd {
	private Io_url prv_url;
	public Xob_xfer_update_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Xob_cmd_keys.Key_file_xfer_update;}
	public void Cmd_run() {
		// init vars
		Xob_db_file cur_file = Xob_db_file.new__file_make(wiki.Fsys_mgr().Root_dir());
		Db_conn conn = cur_file.Conn();
		if (prv_url == null) {
			prv_url = wiki.Appe().Fsys_mgr().File_dir().GenSubFil_nest(wiki.Domain_str(), "bldr", Xob_db_file.Name__file_make);
		}

		// run sql
		Sqlite_engine_.Tbl_rename(conn, "xfer_regy", "xfer_regy_old");
		Xob_xfer_regy_tbl.Create_table(conn);
		Sqlite_engine_.Db_attach(conn, "old_db", prv_url.Raw());
		conn.Exec_sql(Sql_update);
		Sqlite_engine_.Db_detach(conn, "old_db");
		Sqlite_engine_.Tbl_delete(conn, "xfer_regy_old");
		Xob_xfer_regy_tbl.Create_index(usr_dlg, conn);

//			// rotate db
//			DateAdp wiki_date = wiki.Db_mgr().Dump_date_query();
//			Io_url archive_url = prv_url.GenNewNameOnly("oimg_lnki_" + wiki_date.XtoStr_fmt("yyyyMMdd"));
//			Io_mgr.I.CopyFil(cur_file.Url(), archive_url, true);
//			Io_mgr.I.CopyFil(cur_file.Url(), prv_url, true);
	}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	public static final String Sql_update = String_.Concat_lines_nl
	( "INSERT INTO xfer_regy"
	, "SELECT  cur.lnki_id"
	, ",       cur.orig_page_id"
	, ",       cur.orig_repo"
	, ",       cur.lnki_ttl"
	, ",       cur.orig_redirect_src"
	, ",       cur.lnki_ext"
	, ",       cur.orig_media_type"
	, ",       cur.file_is_orig"
	, ",       cur.orig_w"
	, ",       cur.orig_h"
	, ",       cur.file_w"
	, ",       cur.file_h"
	, ",       cur.lnki_time"
	, ",       cur.lnki_count"
	, ",       CASE"
	, "          WHEN old.lnki_ttl IS NULL THEN"	// not in old table; mark todo
	, "            " + Byte_.Xto_str(Xob_xfer_regy_tbl.Status_todo)
	, "          ELSE"									// in old table; mark processed
	, "            " + Byte_.Xto_str(Xob_xfer_regy_tbl.Status_ignore_processed)
	, "        END"
	, ",       cur.xfer_bin_tid"
	, ",       cur.xfer_bin_msg"
	, "FROM    xfer_regy_old cur"
	, "        LEFT JOIN old_db.xfer_regy old ON cur.lnki_ttl = old.lnki_ttl AND cur.file_w = old.file_w"
	);
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_previous_url_))				prv_url = m.ReadIoUrl("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_previous_url_ = "previous_url_";
}

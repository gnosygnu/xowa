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
package gplx.xowa.addons.bldrs.wmdumps.imglinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.wmdumps.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*;
import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*;
public class Xof_orig_wkr__img_links_ {
	public static Xof_orig_wkr__img_links Load_all(Xowe_wiki wiki) {
		Xof_orig_wkr__img_links rv = new Xof_orig_wkr__img_links();

		Db_conn conn = Xob_db_file.New__img_link(wiki).Conn();
		Load_by_wiki(rv, conn, Xof_repo_itm_.Repo_local , wiki);
		Load_by_wiki(rv, conn, Xof_repo_itm_.Repo_remote, wiki.Appe().Wiki_mgr().Wiki_commons());
		
		return rv;
	}
	private static void Load_by_wiki(Xof_orig_wkr__img_links rv, Db_conn conn, byte repo_id, Xowe_wiki wiki) {
		String sql = String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "SELECT  ilr.img_repo, ilr.img_src, i.img_media_type, i.img_minor_mime, i.img_size, i.img_width, i.img_height, i.img_bits, i.img_ext_id, i.img_timestamp, ilr.img_trg AS img_redirect"
		, "FROM    imglnk_reg ilr"
		, "        JOIN <img_db>image i ON ilr.img_trg = i.img_name"
		, "WHERE   ilr.img_repo = " + repo_id
		);

		Xob_db_file img_db = Xob_db_file.New__wiki_image(wiki.Fsys_mgr().Root_dir());
		Db_attach_mgr attach_mgr = new Db_attach_mgr(conn, new Db_attach_itm("img_db", img_db.Conn()));
		sql = attach_mgr.Resolve_sql(sql);
		attach_mgr.Attach();

		int count = 0;
		Db_rdr rdr = conn.Stmt_sql(sql).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				rv.Add_by_db(new Xof_orig_itm
				( rdr.Read_byte("img_repo")
				, rdr.Read_bry_by_str("img_src")
				, rdr.Read_int("img_ext_id")
				, rdr.Read_int("img_width")
				, rdr.Read_int("img_height")
				, rdr.Read_bry_by_str("img_redirect")
				));
				if ((++count % 10000) == 0)
					Gfo_usr_dlg_.Instance.Prog_many("", "", "loading img_links.orig: rows=~{0}", count);
			}
		} finally {rdr.Rls();}
		attach_mgr.Detach();
	}
}

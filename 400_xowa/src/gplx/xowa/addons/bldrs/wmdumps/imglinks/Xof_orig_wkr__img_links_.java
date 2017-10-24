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
package gplx.xowa.addons.bldrs.wmdumps.imglinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.wmdumps.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*;
import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*;
public class Xof_orig_wkr__img_links_ {
	public static void Load_all(Xof_orig_wkr__img_links wkr) {
		Xowe_wiki wiki = wkr.Wiki();
		Db_conn conn = Xob_db_file.New__img_link(wiki).Conn();
		Load_all_by_wiki(wkr, conn, Xof_repo_tid_.Tid__local , wiki);
		Load_all_by_wiki(wkr, conn, Xof_repo_tid_.Tid__remote, wiki.Appe().Wiki_mgr().Wiki_commons());
	}
	public static Xof_orig_itm Load_itm(Xof_orig_wkr__img_links wkr, Db_conn conn, Xowe_wiki wiki, byte[] ttl) {
		Imglnk_reg_tbl imglnk_reg_tbl = wkr.Tbl__imglnk_reg();
		Db_rdr rdr = imglnk_reg_tbl.Select_by_ttl_stmt().Clear().Crt_bry_as_str("img_src", ttl).Exec_select__rls_manual();
		byte img_repo = Byte_.Max_value_127;
		byte[] img_trg = null;
		try {
			if (rdr.Move_next()) {
				img_repo = rdr.Read_byte("img_repo");
				img_trg = rdr.Read_bry_by_str("img_trg");
			}
			else	// ttl missing; EX:</*_File:Chehov_v_serpuhove11.JPG; DATE:2016-08-10
				return Xof_orig_itm.Null;
		} finally {rdr.Rls();}
		Xowe_wiki image_wiki = img_repo == Xof_repo_tid_.Tid__local ? wiki : wiki.Appe().Wiki_mgr().Wiki_commons();
		return Load_itm_by_wiki(wkr, conn, image_wiki, img_repo, ttl, img_trg);
	}
	private static Xof_orig_itm Load_itm_by_wiki(Xof_orig_wkr__img_links wkr, Db_conn conn, Xowe_wiki wiki, byte repo_id, byte[] img_src, byte[] img_trg) {
		Db_stmt stmt = wkr.Stmt__image__select(repo_id, wiki);
		Db_rdr rdr = stmt.Clear().Crt_bry_as_str("img_name", img_trg).Exec_select__rls_manual();
		try {
			return rdr.Move_next()
				? new Xof_orig_itm
				( repo_id
				, img_src	// NOTE: was originally (incorrectly) img_trg; PAGE:en.v:Ani; DATE:2016-10-18
				, rdr.Read_int("img_ext_id")
				, rdr.Read_int("img_width")
				, rdr.Read_int("img_height")
				, img_trg	// NOTE: was originally (incorrectly) img_src; PAGE:en.v:Ani; DATE:2016-10-18
				)
				: Xof_orig_itm.Null;
		} finally {rdr.Rls();}
	}

	private static void Load_all_by_wiki(Xof_orig_wkr__img_links rv, Db_conn conn, byte repo_id, Xowe_wiki wiki) {
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

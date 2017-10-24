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
public class Imglnk_reg_tbl implements Db_tbl {
	private final    String tbl_name = "imglnk_reg"; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__img_src, fld__img_trg, fld__img_repo;
	private final    Db_conn conn;
	public Imglnk_reg_tbl(Db_conn conn) {
		this.conn = conn;
		fld__img_src = flds.Add_str("img_src", 255);
		fld__img_trg = flds.Add_str("img_trg", 255);
		fld__img_repo = flds.Add_byte("img_repo");
		flds.Add_int("img_count");
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;}
	public String Tbl_name() {return tbl_name;}
	public String Fld__img_src() {return fld__img_src;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx__src_ttl() {conn.Meta_idx_create(tbl_name, fld__img_src, fld__img_src, fld__img_repo);}
	public void Create_idx__trg_ttl() {conn.Meta_idx_create(tbl_name, fld__img_trg, fld__img_trg, fld__img_repo);}
	public Db_stmt Select_by_ttl_stmt() {
		if (select_by_ttl_stmt == null)
			select_by_ttl_stmt = conn.Stmt_select(tbl_name, flds, fld__img_src);
		return select_by_ttl_stmt;
	}	private Db_stmt select_by_ttl_stmt;
	public void Insert(Db_conn conn, byte repo_id, Xowe_wiki wiki) {
		String repo_id_str = Byte_.To_str(repo_id);
		Db_attach_mgr attach_mgr = new Db_attach_mgr(conn);
		String sql = "";
		
		Xob_db_file redirect_db = Xob_db_file.New__wiki_redirect(wiki.Fsys_mgr().Root_dir());
		attach_mgr.Conn_links_(new Db_attach_itm("redirect_db", redirect_db.Conn()));
		sql = String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "INSERT INTO imglnk_reg (img_src, img_trg, img_repo, img_count)"
		, "SELECT  ilt.img_name, r.trg_ttl, " + repo_id_str + ", Count(ilt.img_name)"
		, "FROM    imglnk_tmp ilt"
		, "        JOIN <redirect_db>redirect r ON ilt.img_name = r.src_ttl"
		, "        LEFT JOIN imglnk_reg il ON il.img_src = ilt.img_name"
		, "WHERE   il.img_src IS NULL"
		, "GROUP BY ilt.img_name"
		);
		attach_mgr.Exec_sql_w_msg("imglnk_reg.insert.redirect: repo=" + repo_id_str, sql);

		Xob_db_file image_db = Xob_db_file.New__wiki_image(wiki.Fsys_mgr().Root_dir());
		attach_mgr.Conn_links_(new Db_attach_itm("image_db", image_db.Conn()));
		sql = String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "INSERT INTO imglnk_reg (img_src, img_trg, img_repo, img_count)"
		, "SELECT  ilt.img_name, ilt.img_name, " + repo_id_str + ", Count(ilt.img_name)"
		, "FROM    imglnk_tmp ilt"
		, "        JOIN <image_db>image i ON i.img_name = ilt.img_name"
		, "        LEFT JOIN imglnk_reg il ON il.img_src = ilt.img_name"
		, "WHERE   il.img_src IS NULL"
		, "GROUP BY ilt.img_name"
		);
		attach_mgr.Exec_sql_w_msg("imglnk_reg.insert.direct: repo=" + repo_id_str, sql);
	}
	public void Rls() {
		select_by_ttl_stmt = Db_stmt_.Rls(select_by_ttl_stmt);
	}
}

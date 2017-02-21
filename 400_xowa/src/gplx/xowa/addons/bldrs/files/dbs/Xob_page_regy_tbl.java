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
import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.files.repos.*; import gplx.dbs.engines.sqlite.*;
import gplx.xowa.bldrs.*;
public class Xob_page_regy_tbl {
	public static void Reset_table(Db_conn p) {Sqlite_engine_.Tbl_create_and_delete(p, Tbl_name, Tbl_sql);}
	public static void Create_data(Gfo_usr_dlg usr_dlg, Db_conn p, byte repo_tid, Xowe_wiki wiki) {
		Xow_db_file db_core = wiki.Db_mgr_as_sql().Core_data_mgr().Db__core();
		Create_data__insert_page(usr_dlg, p, repo_tid, db_core.Url());
		Create_data__insert_redirect(usr_dlg, p, repo_tid, wiki.Fsys_mgr().Root_dir().GenSubFil(Xob_db_file.Name__wiki_redirect));
	}
	public static void Delete_local(Db_conn p) {
		p.Exec_sql("DELETE FROM page_regy WHERE repo_id = " + Xof_repo_tid_.Tid__local);
	}
	private static void Create_data__insert_page(Gfo_usr_dlg usr_dlg, Db_conn cur, byte repo_tid, Io_url join) {
		usr_dlg.Note_many("", "", "inserting page: ~{0}", join.NameOnly());
		Sqlite_engine_.Db_attach(cur, "page_db", join.Raw());
		cur.Exec_sql(String_.Format(Sql_create_page, repo_tid));
		Sqlite_engine_.Db_detach(cur, "page_db");
	}
	private static void Create_data__insert_redirect(Gfo_usr_dlg usr_dlg, Db_conn cur, byte repo_tid, Io_url join) {
		if (!Io_mgr.Instance.ExistsFil(join)) return;	// redirect_db will not exist when commons.wikimedia.org is set up on new machine
		usr_dlg.Note_many("", "", "inserting redirect: ~{0}", join.OwnerDir().NameOnly());
		Sqlite_engine_.Db_attach(cur, "redirect_db", join.Raw());
		cur.Exec_sql(String_.Format(Sql_create_redirect, repo_tid));
		Sqlite_engine_.Db_detach(cur, "redirect_db");
	}
	public static final    String Tbl_name = "page_regy"
	, Fld_uid = "uid", Fld_repo_id = "repo_id", Fld_itm_tid = "itm_tid"
	, Fld_src_id = "src_id", Fld_src_ttl = "src_ttl"
	, Fld_trg_id = "trg_id", Fld_trg_ttl = "trg_ttl"
	;
	public static final    Db_idx_itm
		Idx_main     		= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS page_regy__main           ON page_regy (repo_id, itm_tid, src_ttl, src_id, trg_id, trg_ttl);")
	;
	private static final    String
		Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS page_regy"
	,	"( uid                     integer             NOT NULL			PRIMARY KEY           AUTOINCREMENT"	// NOTE: must be PRIMARY KEY, else later REPLACE INTO will create dupe rows
	,	", repo_id                 integer             NOT NULL"
	,	", itm_tid                 tinyint             NOT NULL"
	,	", src_id                  integer             NOT NULL"
	,	", src_ttl                 varchar(255)        NOT NULL"
	,	", trg_id                  integer             NOT NULL"
	,	", trg_ttl                 varchar(255)        NOT NULL"
	,	");"
	)
	,	Sql_create_page = String_.Concat_lines_nl
	(	"INSERT INTO page_regy (repo_id, itm_tid, src_id, src_ttl, trg_id, trg_ttl)"
	,	"SELECT  {0}"
	,	",       0"		// 0=page
	,	",       p.page_id"
	,	",       p.page_title"
	,	",       -1"
	,	",       ''"
	,	"FROM    page_db.page p"
	,	";"
	)
	,	Sql_create_redirect = String_.Concat_lines_nl
	(	"INSERT INTO page_regy (repo_id, itm_tid, src_id, src_ttl, trg_id, trg_ttl)"
	,	"SELECT  {0}"
	,	",       1"		// 1=redirect
	,	",       r.src_id"
	,	",       r.src_ttl"
	,	",       r.trg_id"
	,	",       r.trg_ttl"
	,	"FROM    redirect_db.redirect r"
	,	";"
	)
	;
}

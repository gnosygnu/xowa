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
import gplx.dbs.*; import gplx.xowa.files.repos.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.domains.*;
import gplx.dbs.engines.sqlite.*;
import gplx.xowa.bldrs.*;
public class Xob_orig_regy_tbl {
	public static void Create_table(Db_conn p) {Sqlite_engine_.Tbl_create_and_delete(p, Tbl_name, Tbl_sql);}
	public static void Create_data(Gfo_usr_dlg usr_dlg, Db_conn p, Xob_db_file file_registry_db, boolean repo_0_is_remote, Xowe_wiki repo_0_wiki, Xowe_wiki repo_1_wiki, boolean wiki_ns_for_file_is_case_match_all) {
		usr_dlg.Prog_many("", "", "inserting lnki_regy");
		p.Exec_sql(Sql_create_data);
		Sqlite_engine_.Idx_create(usr_dlg, p, "orig_regy", Idx_ttl_local);
		Sqlite_engine_.Db_attach(p, "page_db", file_registry_db.Url().Raw());
		Io_url repo_0_dir = repo_0_wiki.Fsys_mgr().Root_dir(), repo_1_dir = repo_1_wiki.Fsys_mgr().Root_dir();
		byte repo_0_tid = Xof_repo_tid_.Tid__local, repo_1_tid = Xof_repo_tid_.Tid__remote;
		boolean local_is_remote = Bry_.Eq(repo_0_wiki.Domain_bry(), repo_1_wiki.Domain_bry());
		Xowe_wiki local_wiki = repo_0_wiki;
		if (	repo_0_is_remote														// .gfs manually marked specifes repo_0 as remote
			||	(	Bry_.Eq(repo_0_wiki.Domain_bry(), Xow_domain_itm_.Bry__commons)		// repo_0 = commons; force repo_0 to be remote; else all orig_repo will be 1; DATE:2014-02-01
				&&	local_is_remote														// repo_0 = repo_1
				)
			) {
			repo_0_tid = Xof_repo_tid_.Tid__remote;
			repo_1_tid = Xof_repo_tid_.Tid__local;
			local_wiki = repo_1_wiki;
		}
		Create_data_for_repo(usr_dlg, p, local_wiki, Byte_.By_int(repo_0_tid), repo_0_dir.GenSubFil(Xob_db_file.Name__wiki_image));
		if (!local_is_remote) {	// only run for repo_1 if local != remote; only affects commons
			Create_data_for_repo(usr_dlg, p, local_wiki, Byte_.By_int(repo_1_tid), repo_1_dir.GenSubFil(Xob_db_file.Name__wiki_image));
			if (wiki_ns_for_file_is_case_match_all) {
				Io_url repo_remote_dir = repo_0_is_remote ? repo_0_dir : repo_1_dir;
				Create_data_for_cs(usr_dlg, p, local_wiki, repo_remote_dir);
			}
		}
		Sqlite_engine_.Db_detach(p, "page_db");
		Sqlite_engine_.Idx_create(usr_dlg, p, "orig_regy", Idx_xfer_temp);
	}
	private static void Create_data_for_repo(Gfo_usr_dlg usr_dlg, Db_conn conn, Xowe_wiki local_wiki, byte repo_tid, Io_url join) {
		usr_dlg.Note_many("", "", "inserting page for xowa.wiki.image: ~{0}", join.OwnerDir().NameOnly());
		boolean wiki_has_cs_file = repo_tid == Xof_repo_tid_.Tid__remote && local_wiki.Ns_mgr().Ns_file().Case_match() == Xow_ns_case_.Tid__all;
		String lnki_ttl_fld = wiki_has_cs_file ? "Coalesce(o.lnki_commons_ttl, o.lnki_ttl)" : "o.lnki_ttl";	// NOTE: use lnki_commons_ttl if [[File]] is cs PAGE:en.d:water EX:[[image:wikiquote-logo.png|50px|none|alt=]]; DATE:2014-09-05
		if (wiki_has_cs_file)
			Sqlite_engine_.Idx_create(usr_dlg, conn, "orig_regy", Idx_ttl_remote);
		new Db_attach_mgr(conn, new Db_attach_itm("image_db", join))
			.Exec_sql_w_msg("orig_regy:updating page"		, Sql_update_repo_page		, repo_tid, lnki_ttl_fld)
			.Exec_sql_w_msg("orig_regy:updating redirect"	, Sql_update_repo_redirect	, repo_tid, lnki_ttl_fld);
	}
	private static void Create_data_for_cs(Gfo_usr_dlg usr_dlg, Db_conn p, Xowe_wiki local_wiki, Io_url repo_remote_dir) {
		p.Exec_sql(Xob_orig_regy_tbl.Sql_cs_mark_dupes);	// orig_regy: find dupes; see note in SQL
		p.Exec_sql(Xob_orig_regy_tbl.Sql_cs_update_ttls);	// orig_regy: update lnki_ttl with lnki_commons_ttl
		Create_data_for_repo(usr_dlg, p, local_wiki, Xof_repo_tid_.Tid__remote, repo_remote_dir.GenSubFil(Xob_db_file.Name__wiki_image));
		p.Exec_sql(Xob_lnki_regy_tbl.Sql_cs_mark_changed);	// lnki_regy: update lnki_commons_flag
		p.Exec_sql(Xob_lnki_regy_tbl.Sql_cs_update_ttls);	// lnki_regy: update cs
	}
	public static final    String Tbl_name = "orig_regy"
	, Fld_lnki_id = "lnki_id", Fld_lnki_ttl = "lnki_ttl", Fld_lnki_ext = "lnki_ext", Fld_lnki_count = "lnki_count"
	, Fld_orig_repo = "orig_repo", Fld_orig_page_id = "orig_page_id"
	, Fld_orig_redirect_id = "orig_redirect_id", Fld_orig_redirect_ttl = "orig_redirect_ttl", Fld_orig_file_id = "orig_file_id", Fld_orig_file_ttl = "orig_file_ttl"
	, Fld_orig_size = "orig_size", Fld_orig_w = "orig_w", Fld_orig_h = "orig_h", Fld_orig_bits = "orig_bits"
	, Fld_orig_media_type = "orig_media_type", Fld_orig_minor_mime = "orig_minor_mime", Fld_orig_file_ext = "orig_file_ext", Fld_orig_timestamp = "orig_timestamp"
	;
	private static final    Db_idx_itm
	  Idx_ttl_local     	= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS orig_regy__ttl_local  ON orig_regy (lnki_ttl);")
	, Idx_ttl_remote     	= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS orig_regy__ttl_remote ON orig_regy (lnki_commons_ttl, lnki_ttl);")
	, Idx_xfer_temp			= Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS orig_regy__xfer_temp  ON orig_regy (lnki_ttl, orig_file_ttl, orig_repo, orig_timestamp);")
	;
	private static final    String
		Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS orig_regy"
	,	"( lnki_id                     integer             NOT NULL			PRIMARY KEY"	// NOTE: must be PRIMARY KEY, else later REPLACE INTO will create dupe rows
	,	", lnki_ttl                    varchar(256)        NOT NULL"
	,	", lnki_commons_ttl            varchar(256)        NULL"
	,	", orig_commons_flag           integer             NULL"
	,	", lnki_ext                    integer             NOT NULL"
	,	", lnki_count                  integer             NOT NULL"
	,	", orig_repo                   integer             NULL"
	,	", orig_page_id                integer             NULL"
	,	", orig_redirect_id            integer             NULL"
	,	", orig_redirect_ttl           varchar(256)        NULL"
	,	", orig_file_id                integer             NULL"
	,	", orig_file_ttl               varchar(256)        NULL"
	,	", orig_file_ext               integer             NULL"
	,	", orig_size                   integer             NULL"
	,	", orig_w                      integer             NULL"
	,	", orig_h                      integer             NULL"
	,	", orig_bits                   smallint            NULL"
	,	", orig_media_type             varchar(64)         NULL"
	,	", orig_minor_mime             varchar(32)         NULL"
	,	", orig_timestamp              varchar(14)         NULL"
	,	");"
	)
	,	Sql_create_data = String_.Concat_lines_nl
	(	"INSERT INTO orig_regy (lnki_id, lnki_ttl, lnki_commons_ttl, orig_commons_flag, lnki_ext, lnki_count, orig_timestamp)"
	,	"SELECT  Min(lnki_id)"
	,	",       lnki_ttl"
	,	",       lnki_commons_ttl"
	,	",       NULL"
	,	",       lnki_ext"
	,	",       Sum(lnki_count)"
	,	",       ''"
	,	"FROM    lnki_regy"
	,	"GROUP BY lnki_ttl"
	,	",       lnki_ext"
	,	"ORDER BY 1"	// must order by lnki_id since it is PRIMARY KEY, else sqlite will spend hours shuffling rows in table
	,	";"
	)
	,	Sql_update_repo_page = String_.Concat_lines_nl
	(	"REPLACE INTO orig_regy"
	,	"SELECT  o.lnki_id"
	,	",       o.lnki_ttl"
	,	",       o.lnki_commons_ttl"
	,	",       o.orig_commons_flag"
	,	",       o.lnki_ext"
	,	",       o.lnki_count"
	,	",       {0}"
	,	",       m.src_id"
	,	",       NULL"
	,	",       NULL"
	,	",       m.src_id"
	,	",       m.src_ttl"
	,	",       i.img_ext_id"
	,	",       i.img_size"
	,	",       i.img_width"
	,	",       i.img_height"
	,	",       i.img_bits"
	,	",       i.img_media_type"
	,	",       i.img_minor_mime"
	,	",       i.img_timestamp"
	,	"FROM    orig_regy o"
	,	"        JOIN <image_db>image i ON {1} = i.img_name"
	,	"        JOIN page_db.page_regy m ON m.repo_id = {0} AND m.itm_tid = 0 AND {1} = m.src_ttl"
	,	"WHERE   o.orig_file_ttl IS NULL"					// NOTE: only insert if file doesn't exist; changed from timestamp b/c old images may exist in both wikis; EX:ar.n:File:Facebook.png; DATE:2014-08-20
	// ,	"WHERE   i.img_timestamp > o.orig_timestamp"	// NOTE: this handles an image in local and remote by taking later version; DATE:2014-07-22
	,	"ORDER BY 1"	// must order by lnki_id since it is PRIMARY KEY, else sqlite will spend hours shuffling rows in table
	,	";"
	)
	,	Sql_update_repo_redirect = String_.Concat_lines_nl
	(	"REPLACE INTO orig_regy"
	,	"SELECT  o.lnki_id"
	,	",       o.lnki_ttl"
	,	",       o.lnki_commons_ttl"
	,	",       o.orig_commons_flag"
	,	",       o.lnki_ext"
	,	",       o.lnki_count"
	,	",       {0}"
	,	",       m.src_id"
	,	",       m.trg_id"
	,	",       m.trg_ttl"
	,	",       m.trg_id"
	,	",       m.trg_ttl"
	,	",       i.img_ext_id"
	,	",       i.img_size"
	,	",       i.img_width"
	,	",       i.img_height"
	,	",       i.img_bits"
	,	",       i.img_media_type"
	,	",       i.img_minor_mime"
	,	",       i.img_timestamp"
	,	"FROM    orig_regy o"
	,	"        JOIN page_db.page_regy m ON m.repo_id = {0} AND m.itm_tid = 1 AND {1} = m.src_ttl"
	,	"            JOIN <image_db>image i ON m.trg_ttl = i.img_name"
	,	"WHERE   o.orig_file_ttl IS NULL"						// NOTE: only insert if file doesn't exist; changed from timestamp b/c old images may exist in both wikis; EX:ar.n:File:Facebook.png; DATE:2014-08-20
	// ,	"WHERE   i.img_timestamp > o.orig_timestamp"	// NOTE: this handles an image in local and remote by taking later version; DATE:2014-07-22
	,	"ORDER BY 1"	// must order by lnki_id since it is PRIMARY KEY, else sqlite will spend hours shuffling rows in table
	,	";"
	)
	,	Sql_cs_mark_dupes = String_.Concat_lines_nl
	(	"REPLACE INTO orig_regy"
	,	"SELECT  o.lnki_id"
	,	",       o.lnki_ttl"
	,	",       o.lnki_commons_ttl"
	,	",       1"
	,	",       o.lnki_ext"
	,	",       o.lnki_count"
	,	",       o.orig_repo"
	,	",       o.orig_page_id"
	,	",       o.orig_redirect_id"
	,	",       o.orig_redirect_ttl"
	,	",       o.orig_file_id"
	,	",       o.orig_file_ttl"
	,	",       o.orig_file_ext"
	,	",       o.orig_size"
	,	",       o.orig_w"
	,	",       o.orig_h"
	,	",       o.orig_bits"
	,	",       o.orig_media_type"
	,	",       o.orig_minor_mime"
	,	",       o.orig_timestamp"
	,	"FROM    orig_regy o"
	,	"        JOIN orig_regy o2 ON o.lnki_commons_ttl = o2.lnki_ttl"	// EX: 2 rows in table (1) A.jpg; and (2) "a.jpg,A.jpg"; do not insert row (2) b/c row (1) exists;
	,	"WHERE   o.orig_file_ttl IS NULL"	// NOTE: don't use timestamp logic here
	,	"ORDER BY 1"	// must order by lnki_id since it is PRIMARY KEY, else sqlite will spend hours shuffling rows in table
	,	";"
	)
	,	Sql_cs_update_ttls = String_.Concat_lines_nl
	(	"UPDATE  orig_regy"
	,	"SET     lnki_ttl = lnki_commons_ttl"
	,	",       orig_commons_flag = 2"
	,	"WHERE   orig_file_ttl IS NULL"			// orig not found
	,	"AND     lnki_commons_ttl IS NOT NULL"	// orig commons ttl exists
	,	"AND     orig_commons_flag IS NULL"		// orig is not dupe
	,	";"
	)
	;
}

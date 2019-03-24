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
package gplx.xowa.addons.bldrs.wmdumps.pagelinks.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.wmdumps.*; import gplx.xowa.addons.bldrs.wmdumps.pagelinks.*;
import gplx.core.ios.*; import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.wikis.dbs.*; import gplx.dbs.cfgs.*;
import gplx.xowa.bldrs.*;
import gplx.xowa.addons.bldrs.wmdumps.pagelinks.dbs.*;
class Pglnk_tempdb_mgr {
	private final    Gfo_usr_dlg usr_dlg;
	private final    Xow_wiki wiki;
	private final    Db_conn conn;
	private final    int row_max;
	private int rows;

	private final    Dbmeta_fld_list dump_flds;
	private final    String dump_tbl_name = "pagelink_dump", dump_src_id, dump_trg_ns, dump_trg_ttl;
	private Db_stmt dump_insert;

	private final    String temp_tbl_name = "pagelink_temp";

	public Pglnk_tempdb_mgr(Gfo_usr_dlg usr_dlg, Xow_wiki wiki, int row_max) {
		// init members
		this.usr_dlg = usr_dlg;
		this.wiki = wiki;
		this.row_max = row_max;

		// create conn
		conn = Xob_db_file.New__page_link(wiki).Conn();

		// create dump_tbl
		dump_flds = new Dbmeta_fld_list();
		dump_flds.Add_int_pkey_autonum("uid");
		dump_src_id  = dump_flds.Add_int("src_id");
		dump_trg_ns  = dump_flds.Add_int("trg_ns");
		dump_trg_ttl = dump_flds.Add_str("trg_ttl", 255);

		// create temp_tbl
		Dbmeta_fld_list temp_flds = new Dbmeta_fld_list();
		temp_flds.Add_int("src_id");
		temp_flds.Add_int("trg_id");
		temp_flds.Add_int("trg_count");
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(temp_tbl_name, temp_flds));
	}
	private void Dump__insert_bgn() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(dump_tbl_name, dump_flds));
		conn.Txn_bgn("pagelink__dump__insert");
		dump_insert = conn.Stmt_insert(dump_tbl_name, dump_flds);
	}
	public void Dump__insert_row(int src_id, int trg_ns, byte[] trg_ttl) {
		// move rows from dump to temp every n million
		if (rows % row_max == 0) {
			if (rows != 0) Dump__insert_end();
			Dump__insert_bgn();
		}

		// do insert; write prog
		dump_insert.Clear().Val_int(dump_src_id, src_id).Val_int(dump_trg_ns, trg_ns).Val_bry_as_str(dump_trg_ttl, trg_ttl).Exec_insert();
		if (++rows % 100000 == 0) usr_dlg.Prog_many("", "", "reading row ~{0}", Int_.To_str_fmt(rows, "#,##0"));
	}
	private void Dump__insert_end() {
		// clean-up insert_stmt
		conn.Txn_end();
		dump_insert = Db_stmt_.Rls(dump_insert);
		conn.Meta_idx_create(Gfo_usr_dlg_.Instance, Dbmeta_idx_itm.new_normal_by_tbl(dump_tbl_name, "main", dump_src_id, dump_trg_ns, dump_trg_ttl));

		// move rows from dump to temp
		new Db_attach_mgr(conn, new Db_attach_itm("page_db", wiki.Data__core_mgr().Db__core().Conn()))
			.Exec_sql_w_msg
			( String_.Format("transferring from dump to temp: row={0}", Int_.To_str_fmt(rows, "#,##0"))
			, String_.Concat_lines_nl_skip_last
			( "INSERT INTO pagelink_temp (src_id, trg_id, trg_count)"
			, "SELECT  pl.src_id"
			, ",       p.page_id"
			, ",       Count(p.page_id)"
			, "FROM    pagelink_dump pl"
			, "        JOIN <page_db>page p ON pl.trg_ns = p.page_namespace AND pl.trg_ttl = p.page_title"
			, "GROUP BY pl.src_id, p.page_id"
			));

		// drop dump_tbl; vaccuum
		conn.Meta_tbl_delete(dump_tbl_name);
		conn.Env_vacuum();
	}
	public void Live__create() {
		// end current batch
		if (rows > 0) this.Dump__insert_end();

		// index temp tbl
		conn.Meta_idx_create(Gfo_usr_dlg_.Instance, Dbmeta_idx_itm.new_normal_by_tbl(temp_tbl_name, "main", "src_id", "trg_id"));

		// create live_tbl
		Pglnk_page_link_tbl live_tbl = new Pglnk_page_link_tbl(conn);
		live_tbl.Create_tbl();

		// move rows from temp to live; drop temp tbl
		conn.Exec_sql_concat_w_msg
			( String_.Format("creating live tbl: row={0}", Int_.To_str_fmt(rows, "#,##0"))
			, "INSERT INTO page_link (src_id, trg_id, trg_count)"
			, "SELECT  pl.src_id"
			, ",       pl.trg_id"
			, ",       Sum(pl.trg_count)"
			, "FROM    pagelink_temp pl"
			, "GROUP BY pl.src_id, pl.trg_id"
			);
		conn.Meta_tbl_delete(temp_tbl_name);
		conn.Env_vacuum(); // NOTE: do not VACCUUM after indexes

		// create idxs
		live_tbl.Create_idx__src_trg();
		live_tbl.Create_idx__trg_src();
	}
}
/*
"A fatal error has been detected by the Java Runtime Environment" ISSUE#:396 DATE:2019-03-23

== Background ==
* Occurred on Linux openSUSE 13.2 (desb42 reported completion (Windows OS?))
* Size of xowa.wiki.pagelinks.sqlite3 at time of crash was 42 GB
* Number of rows in dump table was 1,215,821,988

== Original attempt ==
* Insert rows into dump table
* Fatal error occurred during CREATE INDEX on dump table
** Possibly b/c of INDEX on 1.2 billion varchar fields?

== Second attempt ==
* Insert rows into dump table N
* After 500 million, INDEX old dump table N and create a new dump table N + 1
** INDEX now on 500 million varchar fields
* After EOS, loop each dump table and transfer into live table
* Fatal error occurred during final VACCUUM (possibly b/c of space from the 1.2 billion varchar fields?)

== Third attempt ==
* Insert rows into dump table
* After 200 million rows, INDEX dump table, transfer into temp table, and recreate dump table
** INDEX now on 200 million varchar fields
* After EOS, drop dump table, INDEX temp table and transfer into live table
** INDEX now on 1.2 billion int,int fields
** No varchar fields during final transfer and VACCUUM; db rougly 55 GB
* NOTE: VACCUM after live index still causes failure but Java one (not core dump)
*/

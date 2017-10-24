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
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.sql_dumps.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.wikis.data.*; import gplx.xowa.addons.bldrs.wmdumps.pagelinks.dbs.*;
public class Pglnk_bldr_cmd extends Xob_sql_dump_base implements Xosql_dump_cbk {
	private Db_conn conn;
	private Pglnk_page_link_temp_tbl temp_tbl;
	private int tmp_src_id, tmp_trg_ns; private byte[] tmp_trg_ttl;
	private int rows = 0;
	public Pglnk_bldr_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki); this.make_fil_len = Io_mgr.Len_mb;}
	@Override public String Sql_file_name() {return Dump_type_key;} public static final String Dump_type_key = "pagelinks";
	@Override protected Xosql_dump_parser New_parser() {return new Xosql_dump_parser(this, "pl_from", "pl_namespace", "pl_title");}
	@Override public void Cmd_bgn_hook(Xob_bldr bldr, Xosql_dump_parser parser) {
		wiki.Init_assert();
		Xob_db_file page_link_db = Xob_db_file.New__page_link(wiki);
		this.conn = page_link_db.Conn();
		this.temp_tbl = new Pglnk_page_link_temp_tbl(conn);
		conn.Meta_tbl_delete(temp_tbl.Tbl_name());
		temp_tbl.Create_tbl();
		temp_tbl.Insert_bgn();
	}
	@Override public void Cmd_end() {
		if (fail) return;
		temp_tbl.Insert_end();
		temp_tbl.Create_idx();
		Pglnk_page_link_tbl actl_tbl = new Pglnk_page_link_tbl(conn);
		conn.Meta_tbl_delete(actl_tbl.Tbl_name());
		actl_tbl.Create_tbl();
		new Db_attach_mgr(conn, new Db_attach_itm("page_db", wiki.Data__core_mgr().Db__core().Conn()))
			.Exec_sql_w_msg("updating page_link", Sql__page_link__make);
		conn.Meta_tbl_delete(temp_tbl.Tbl_name());
		actl_tbl.Create_idx__src_trg();
		actl_tbl.Create_idx__trg_src();
		conn.Env_vacuum();
	}
	public void On_fld_done(int fld_idx, byte[] src, int val_bgn, int val_end) {
		switch (fld_idx) {
			case Fld__pl_from:			this.tmp_src_id = Bry_.To_int_or(src, val_bgn, val_end, -1); break;
			case Fld__pl_namespace:		this.tmp_trg_ns = Bry_.To_int_or(src, val_bgn, val_end, -1); break;
			case Fld__pl_title:			this.tmp_trg_ttl = Bry_.Mid(src, val_bgn, val_end); break;
		}
	}
	public void On_row_done() {
		temp_tbl.Insert(tmp_src_id, tmp_trg_ns, tmp_trg_ttl);
		if (++rows % 100000 == 0) usr_dlg.Prog_many("", "", "reading row ~{0}", Int_.To_str_fmt(rows, "#,##0"));
	}
	private static final byte Fld__pl_from = 0, Fld__pl_namespace = 1, Fld__pl_title = 2;
	private static final    String Sql__page_link__make = String_.Concat_lines_nl_skip_last
	(	"INSERT INTO page_link (src_id, trg_id, trg_count)"
	,	"SELECT  pl.src_id"
	,	",       p.page_id"
	,	",       Count(p.page_id)"
	,	"FROM    page_link_temp pl"
	,	"        JOIN <page_db>page p ON pl.trg_ns = p.page_namespace AND pl.trg_ttl = p.page_title"
	,	"GROUP BY pl.src_id, p.page_id"
	);

	public static final String BLDR_CMD_KEY = "wiki.page_link";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}
	public static final    Xob_cmd Prototype = new Pglnk_bldr_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Pglnk_bldr_cmd(bldr, wiki);}
}

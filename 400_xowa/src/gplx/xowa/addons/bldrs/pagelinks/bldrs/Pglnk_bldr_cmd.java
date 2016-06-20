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
package gplx.xowa.addons.bldrs.pagelinks.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.pagelinks.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.sqls.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.wikis.data.*; import gplx.xowa.addons.bldrs.pagelinks.dbs.*;
public class Pglnk_bldr_cmd extends Xob_sql_dump_base implements Sql_file_parser_cmd {
	private Db_conn conn;
	private Pglnk_page_link_temp_tbl temp_tbl;
	private int tmp_src_id, tmp_trg_ns;
	private int rows = 0;
	public Pglnk_bldr_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki); this.make_fil_len = Io_mgr.Len_mb;}
	@Override public String Sql_file_name() {return Dump_type_key;} public static final String Dump_type_key = "pagelinks";
	@Override public void Cmd_bgn_hook(Xob_bldr bldr, Sql_file_parser parser) {
		wiki.Init_assert();
		parser.Fld_cmd_(this).Flds_req_idx_(4, 0, 1, 2);
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
	public void Exec(byte[] src, byte[] fld_key, int fld_idx, int fld_bgn, int fld_end, Bry_bfr file_bfr, Sql_file_parser_data data) {
		switch (fld_idx) {
			case Fld__pl_from:			this.tmp_src_id = Bry_.To_int_or(src, fld_bgn, fld_end, -1); break;
			case Fld__pl_namespace:		this.tmp_trg_ns = Bry_.To_int_or(src, fld_bgn, fld_end, -1); break;
			case Fld__pl_title:
				byte[] tmp_trg_ttl = Bry_.Mid(src, fld_bgn, fld_end);
				temp_tbl.Insert(tmp_src_id, tmp_trg_ns, tmp_trg_ttl);
				if (++rows % 100000 == 0) usr_dlg.Prog_many("", "", "reading row ~{0}", Int_.To_str_fmt(rows, "#,##0"));
				break;
		}
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

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
package gplx.xowa.bldrs.cmds.texts.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.dbs.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
public class Xob_pagelinks_parser_cmd extends Xob_sql_dump_base implements Sql_file_parser_cmd {
	private Db_conn core_conn;
	private Xowd_pagelinks_temp_tbl temp_tbl;
	public Xob_pagelinks_parser_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki); this.make_fil_len = Io_mgr.Len_mb;}
	@Override public String Sql_file_name() {return "pagelinks";}
	@Override public String Cmd_key() {return Xob_cmd_keys.Key_wiki_pagelink;}
	private static final byte Fld__pl_from = 0, Fld__pl_namespace = 1, Fld__pl_title = 2;
	private int tmp_src_id, tmp_trg_ns;
	private int rows = 0;
	@Override public void Cmd_bgn_hook(Xob_bldr bldr, Sql_file_parser parser) {
		parser.Fld_cmd_(this).Flds_req_idx_(4, 0, 1, 2);
		wiki.Init_assert();
		Xowd_db_file core_db = wiki.Data__core_mgr().Db__core();
		this.core_conn = core_db.Conn();
		this.temp_tbl = new Xowd_pagelinks_temp_tbl(core_conn);
		core_conn.Ddl_delete_tbl(temp_tbl.Tbl_name());
		temp_tbl.Create_tbl();
		temp_tbl.Insert_bgn();
	}
	@Override public void Cmd_end() {
		temp_tbl.Insert_end();
		temp_tbl.Create_idx();
		Xowd_pagelinks_tbl actl_tbl = new Xowd_pagelinks_tbl(core_conn);
		core_conn.Ddl_delete_tbl(actl_tbl.Tbl_name());
		actl_tbl.Create_tbl();
		core_conn.Exec_sql(Sql__pagelinks__make);
		core_conn.Ddl_delete_tbl(temp_tbl.Tbl_name());
		actl_tbl.Create_idx__src_trg();
		actl_tbl.Create_idx__trg_src();
		core_conn.Env_vacuum();
	}
	public void Exec(byte[] src, byte[] fld_key, int fld_idx, int fld_bgn, int fld_end, Bry_bfr file_bfr, Sql_file_parser_data data) {
		switch (fld_idx) {
			case Fld__pl_from:			this.tmp_src_id = Bry_.To_int_or(src, fld_bgn, fld_end, -1); break;
			case Fld__pl_namespace:		this.tmp_trg_ns = Bry_.To_int_or(src, fld_bgn, fld_end, -1); break;
			case Fld__pl_title:
				byte[] tmp_trg_ttl = Bry_.Mid(src, fld_bgn, fld_end);
				temp_tbl.Insert(tmp_src_id, tmp_trg_ns, tmp_trg_ttl);
				if (++rows % 100000 == 0) usr_dlg.Prog_many("", "", "reading row ~{0}", Int_.Xto_str_fmt(rows, "#,##0"));
				break;
		}
	}
	private static final String Sql__pagelinks__make = String_.Concat_lines_nl_skip_last
	(	"INSERT INTO pagelinks (src_id, trg_id, trg_count)"
	,	"SELECT  pl.src_id"
	,	",       p.page_id"
	,	",       Count(p.page_id)"
	,	"FROM    pagelinks_temp pl"
	,	"        JOIN page p ON pl.trg_ns = p.page_namespace AND pl.trg_ttl = p.page_title"
	,	"GROUP BY pl.src_id, p.page_id"
	);
}

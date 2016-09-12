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
package gplx.xowa.addons.wikis.ctgs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.sqls.*;
import gplx.dbs.*; import gplx.xowa.addons.wikis.ctgs.dbs.*;
public class Xob_pageprop_cmd extends Xob_sql_dump_base implements Sql_file_parser_cmd {
	private int tmp_id;
	private boolean tmp_key_is_hiddencat;
	private int rows;
	private Xodb_tmp_cat_hidden_tbl tbl;

	public Xob_pageprop_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	@Override public String Sql_file_name() {return Dump_file_name;} public static final String Dump_file_name = "page_props";

	@Override public void Cmd_bgn_hook(Xob_bldr bldr, Sql_file_parser parser) {
		wiki.Init_assert();
		parser.Save_csv_n_().Fld_cmd_(this).Flds_req_idx_(4, 0, 1, 2);	// NOTE: 4 b/c MW added fld_3:pp_sortkey; DATE:2014-04-28
		Xodb_tmp_cat_db tmp_db = new Xodb_tmp_cat_db(wiki);
		tbl = new Xodb_tmp_cat_hidden_tbl(tmp_db.Conn());
		tbl.Insert_bgn();
	}
	@Override public void Cmd_end() {
		tbl.Insert_end();
		this.Cmd_cleanup_sql();
	}
	public void Exec(byte[] src, byte[] fld_key, int fld_idx, int fld_bgn, int fld_end, Bry_bfr file_bfr, Sql_file_parser_data data) {
		switch (fld_idx) {
			case Fld__id:					this.tmp_id					= Bry_.To_int_or(src, fld_bgn, fld_end, -1); break;
			case Fld__key:					this.tmp_key_is_hiddencat	= Bry_.Eq(src, fld_bgn, fld_end, Key_hiddencat); break;
			case Fld__val:
				if (!tmp_key_is_hiddencat) {data.Cancel_row_y_(); return;}
				tbl.Insert_cmd_by_batch(tmp_id);
				if (++rows % 10000 == 0) usr_dlg.Prog_many("", "", "parsing pageprops sql: row=~{0}", Int_.To_str_fmt(rows, "#,##0"));
				break;
		}
	}
	private static final byte Fld__id = 0, Fld__key = 1, Fld__val = 2;

	public static final String BLDR_CMD_KEY = "wiki.page_props";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}
	public static final    Xob_cmd Prototype = new Xob_pageprop_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xob_pageprop_cmd(bldr, wiki);}

	private static final    byte[] Key_hiddencat = Bry_.new_a7("hiddencat");
}

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
package gplx.xowa.addons.wikis.ctgs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.sql_dumps.*;
import gplx.dbs.*; import gplx.xowa.addons.wikis.ctgs.dbs.*;
public class Xob_pageprop_cmd extends Xob_sql_dump_base implements Xosql_dump_cbk {
	private int tmp_id;
	private boolean tmp_key_is_hiddencat;
	private int rows;
	private Xodb_tmp_cat_hidden_tbl tbl;

	public Xob_pageprop_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	@Override public String Sql_file_name() {return Dump_file_name;} public static final String Dump_file_name = "page_props";
	@Override protected Xosql_dump_parser New_parser() {return new Xosql_dump_parser(this, "pp_page", "pp_propname", "pp_value");}	// NOTE: 4 b/c MW added fld_3:pp_sortkey; DATE:2014-04-28

	@Override public void Cmd_bgn_hook(Xob_bldr bldr, Xosql_dump_parser parser) {
		wiki.Init_assert();
		Xodb_tmp_cat_db tmp_db = new Xodb_tmp_cat_db(wiki);
		tbl = new Xodb_tmp_cat_hidden_tbl(tmp_db.Conn());
		tbl.Insert_bgn();
	}
	@Override public void Cmd_end() {
		if (fail) return;
		tbl.Insert_end();
		this.Cmd_cleanup_sql();
	}
	public void On_fld_done(int fld_idx, byte[] src, int val_bgn, int val_end) {
		switch (fld_idx) {
			case Fld__pp_page:					this.tmp_id					= Bry_.To_int_or(src, val_bgn, val_end, -1); break;
			case Fld__pp_propname:				this.tmp_key_is_hiddencat	= Bry_.Eq(src, val_bgn, val_end, Key_hiddencat); break;
		}
	}
	public void On_row_done() {
		if (tmp_key_is_hiddencat)
			tbl.Insert_cmd_by_batch(tmp_id);
		if (++rows % 10000 == 0) usr_dlg.Prog_many("", "", "parsing pageprops sql: row=~{0}", Int_.To_str_fmt(rows, "#,##0"));
	}
	private static final byte Fld__pp_page = 0, Fld__pp_propname = 1;

	public static final String BLDR_CMD_KEY = "wiki.page_props";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}
	public static final    Xob_cmd Prototype = new Xob_pageprop_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xob_pageprop_cmd(bldr, wiki);}

	private static final    byte[] Key_hiddencat = Bry_.new_a7("hiddencat");
}

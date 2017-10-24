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
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.sql_dumps.*;
public class Imglnk_bldr_cmd extends Xob_sql_dump_base implements Xosql_dump_cbk {
	private Imglnk_bldr_mgr mgr;
	private int tmp_page_id; private byte[] tmp_il_to;
	private int rows = 0;

	public Imglnk_bldr_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki); this.make_fil_len = Io_mgr.Len_mb;}
	@Override public String Sql_file_name() {return "imagelinks";}
	@Override protected Xosql_dump_parser New_parser() {return new Xosql_dump_parser(this, "il_from", "il_to");}

	@Override public void Cmd_bgn_hook(Xob_bldr bldr, Xosql_dump_parser parser) {
		mgr = new Imglnk_bldr_mgr(wiki);
	}
	public void On_fld_done(int fld_idx, byte[] src, int val_bgn, int val_end) {
		switch (fld_idx) {
			case Fld__il_from:			this.tmp_page_id = Bry_.To_int_or(src, val_bgn, val_end, -1); break;
			case Fld__il_to:			this.tmp_il_to = Bry_.Mid(src, val_bgn, val_end); break;
		}
	}	private static final byte Fld__il_from = 0, Fld__il_to = 1;
	public void On_row_done() {
		mgr.Tmp_tbl().Insert_by_batch(tmp_page_id, tmp_il_to);
		if (++rows % 100000 == 0) usr_dlg.Prog_many("", "", "reading row ~{0}", Int_.To_str_fmt(rows, "#,##0"));
	}
	@Override public void Cmd_end() {
		if (fail) return;
		mgr.On_cmd_end();
	}

	public static final String BLDR_CMD_KEY = "wiki.imagelinks";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}
	public static final    Xob_cmd Prototype = new Imglnk_bldr_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Imglnk_bldr_cmd(bldr, wiki);}
}

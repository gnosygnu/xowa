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
package gplx.xowa.addons.bldrs.wmdumps.imglinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.wmdumps.*;
import gplx.dbs.*; 
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.sql_dumps.*;
public class Imglnk_bldr_cmd extends Xob_sql_dump_base implements Xosql_dump_cbk {
	private Imglnk_bldr_mgr mgr;
	private int tmp_page_id;
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
			case Fld__il_to:
				mgr.Tmp_tbl().Insert_by_batch(tmp_page_id, Bry_.Mid(src, val_bgn, val_end));
				if (++rows % 100000 == 0) usr_dlg.Prog_many("", "", "reading row ~{0}", Int_.To_str_fmt(rows, "#,##0"));
				break;
		}
	}	private static final byte Fld__il_from = 0, Fld__il_to = 1;
	@Override public void Cmd_end() {
		if (fail) return;
		mgr.On_cmd_end();
	}

	public static final String BLDR_CMD_KEY = "wiki.imagelinks";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}
	public static final    Xob_cmd Prototype = new Imglnk_bldr_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Imglnk_bldr_cmd(bldr, wiki);}
}

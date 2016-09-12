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
public class Xob_catlink_cmd extends Xob_sql_dump_base implements Sql_file_parser_cmd {
	private final    Xob_catlink_mgr mgr = new Xob_catlink_mgr();
	private int tmp_page_id;
	private byte[] tmp_ctg_ttl, tmp_sortkey, tmp_timestamp, tmp_sortkey_prefix, tmp_collation, tmp_type;

	public Xob_catlink_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	@Override public String Sql_file_name() {return Dump_file_name;} public static final String Dump_file_name = "categorylinks";

	@Override public void Cmd_bgn_hook(Xob_bldr bldr, Sql_file_parser parser) {
		wiki.Init_assert();
		parser.Save_csv_n_().Fld_cmd_(this).Flds_req_idx_(7, 0, 1, 2, 3, 4, 5, 6);
		mgr.On_cmd_bgn(wiki);
	}
	@Override public void Cmd_end() {
		mgr.On_cmd_end();
		this.Cmd_cleanup_sql();
	}
	public void Exec(byte[] src, byte[] fld_key, int fld_idx, int fld_bgn, int fld_end, Bry_bfr file_bfr, Sql_file_parser_data data) {
		switch (fld_idx) {
			case Fld__cl_from:				this.tmp_page_id			= Bry_.To_int_or(src, fld_bgn, fld_end, -1); break;
			case Fld__cl_to:				this.tmp_ctg_ttl			= Bry_.Mid(src, fld_bgn, fld_end); break;
			case Fld__cl_sortkey:			this.tmp_sortkey			= Bry_.Mid(src, fld_bgn, fld_end); break;
			case Fld__cl_timestamp:			this.tmp_timestamp			= Bry_.Mid(src, fld_bgn, fld_end); break;
			case Fld__cl_sortkey_prefix:	this.tmp_sortkey_prefix		= Bry_.Mid(src, fld_bgn, fld_end); break;
			case Fld__cl_collation:			this.tmp_collation			= Bry_.Mid(src, fld_bgn, fld_end); break;
			case Fld__cl_type:				this.tmp_type				= Bry_.Mid(src, fld_bgn, fld_end);
				mgr.On_cmd_row(tmp_page_id, tmp_ctg_ttl, tmp_sortkey, tmp_timestamp, tmp_sortkey_prefix, tmp_collation, tmp_type);
				break;
		}
	}
	private static final byte Fld__cl_from = 0, Fld__cl_to = 1, Fld__cl_sortkey = 2, Fld__cl_timestamp = 3, Fld__cl_sortkey_prefix = 4, Fld__cl_collation = 5, Fld__cl_type = 6;

	public static final String BLDR_CMD_KEY = "wiki.categorylinks";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}
	public static final    Xob_cmd Prototype = new Xob_catlink_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xob_catlink_cmd(bldr, wiki);}
}

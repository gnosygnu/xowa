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
public class Xob_catlink_cmd extends Xob_sql_dump_base implements Xosql_dump_cbk {
	private final    Xob_catlink_mgr mgr = new Xob_catlink_mgr();
	private int tmp_page_id;
	private byte[] tmp_ctg_ttl, tmp_sortkey, tmp_timestamp, tmp_sortkey_prefix, tmp_collation, tmp_type;

	public Xob_catlink_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	@Override public String Sql_file_name() {return Dump_file_name;} public static final String Dump_file_name = "categorylinks";
	@Override protected Xosql_dump_parser New_parser() {return new Xosql_dump_parser(this, "cl_from", "cl_to", "cl_sortkey", "cl_timestamp", "cl_sortkey_prefix", "cl_collation", "cl_type");}

	@Override public void Cmd_bgn_hook(Xob_bldr bldr, Xosql_dump_parser parser) {
		wiki.Init_assert();
		mgr.On_cmd_bgn(wiki);
	}
	@Override public void Cmd_end() {
		if (fail) return;
		mgr.On_cmd_end();
		this.Cmd_cleanup_sql();
	}
	public void On_fld_done(int fld_idx, byte[] src, int val_bgn, int val_end) {
		switch (fld_idx) {
			case Fld__cl_from:				this.tmp_page_id			= Bry_.To_int_or(src, val_bgn, val_end, -1); break;
			case Fld__cl_to:				this.tmp_ctg_ttl			= Bry_.Mid(src, val_bgn, val_end); break;
			case Fld__cl_sortkey:			this.tmp_sortkey			= Bry_.Mid(src, val_bgn, val_end); break;
			case Fld__cl_timestamp:			this.tmp_timestamp			= Bry_.Mid(src, val_bgn, val_end); break;
			case Fld__cl_sortkey_prefix:	this.tmp_sortkey_prefix		= Bry_.Mid(src, val_bgn, val_end); break;
			case Fld__cl_collation:			this.tmp_collation			= Bry_.Mid(src, val_bgn, val_end); break;
			case Fld__cl_type:				this.tmp_type				= Bry_.Mid(src, val_bgn, val_end); break;
		}
	}
	public void On_row_done() {
		mgr.On_cmd_row(tmp_page_id, tmp_ctg_ttl, tmp_sortkey, tmp_timestamp, tmp_sortkey_prefix, tmp_collation, tmp_type);
	}
	private static final byte Fld__cl_from = 0, Fld__cl_to = 1, Fld__cl_sortkey = 2, Fld__cl_timestamp = 3, Fld__cl_sortkey_prefix = 4, Fld__cl_collation = 5, Fld__cl_type = 6;

	public static final String BLDR_CMD_KEY = "wiki.categorylinks";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}
	public static final    Xob_cmd Prototype = new Xob_catlink_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xob_catlink_cmd(bldr, wiki);}
}

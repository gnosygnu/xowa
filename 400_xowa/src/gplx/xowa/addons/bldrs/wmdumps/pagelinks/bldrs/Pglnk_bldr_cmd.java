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
package gplx.xowa.addons.bldrs.wmdumps.pagelinks.bldrs;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.GfsCtx;
import gplx.libs.ios.IoConsts;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.xowa.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.sql_dumps.*;
public class Pglnk_bldr_cmd extends Xob_sql_dump_base implements Xosql_dump_cbk {
	private Pglnk_tempdb_mgr tempdb_mgr;
	private int tmp_src_id, tmp_trg_ns; private byte[] tmp_trg_ttl;
	private int row_max = 200 * 1000 * 1000;
	public Pglnk_bldr_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki); this.make_fil_len = IoConsts.LenMB;}
	@Override public String Sql_file_name() {return Dump_type_key;} public static final String Dump_type_key = "pagelinks";
	@Override protected Xosql_dump_parser New_parser() {return new Xosql_dump_parser(this, "pl_from", "pl_namespace", "pl_title");}
	@Override public void Cmd_bgn_hook(Xob_bldr bldr, Xosql_dump_parser parser) {
		wiki.Init_assert();
		tempdb_mgr = new Pglnk_tempdb_mgr(usr_dlg, wiki, row_max);
	}
	public void On_fld_done(int fld_idx, byte[] src, int val_bgn, int val_end) {
		switch (fld_idx) {
			case Fld__pl_from:			this.tmp_src_id = BryUtl.ToIntOr(src, val_bgn, val_end, -1); break;
			case Fld__pl_namespace:		this.tmp_trg_ns = BryUtl.ToIntOr(src, val_bgn, val_end, -1); break;
			case Fld__pl_title:			this.tmp_trg_ttl = BryLni.Mid(src, val_bgn, val_end); break;
		}
	}
	public void On_row_done() {
		tempdb_mgr.Dump__insert_row(tmp_src_id, tmp_trg_ns, tmp_trg_ttl);
	}
	@Override public void Cmd_end() {
		if (fail) return;
		tempdb_mgr.Live__create();
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_row_max_))			row_max = m.ReadInt("v");
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}

	private static final byte Fld__pl_from = 0, Fld__pl_namespace = 1, Fld__pl_title = 2;
	private static final String Invk_row_max_ = "row_max_";

	public static final String BLDR_CMD_KEY = "wiki.page_link";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}
	public static final Xob_cmd Prototype = new Pglnk_bldr_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Pglnk_bldr_cmd(bldr, wiki);}
}

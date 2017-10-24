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
package gplx.xowa.addons.bldrs.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.core.stores.*; import gplx.dbs.*; import gplx.xowa.addons.bldrs.files.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.files.*; import gplx.xowa.files.exts.*; import gplx.xowa.parsers.lnkis.*;	
public class Xobldr__xfer_temp__insert_orig extends Xob_cmd__base {
	private byte[] ext_rules_key = Bry_.Empty;
	public Xobldr__xfer_temp__insert_orig(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		Db_conn conn = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		Xob_xfer_temp_tbl.Create_table(conn);
		Db_stmt trg_stmt = Xob_xfer_temp_tbl.Insert_stmt(conn);
		conn.Txn_bgn("bldr__xfer_temp");
		DataRdr rdr = conn.Exec_sql_as_old_rdr(Sql_select_clause);
		long[] ext_maxs = Calc_ext_max();
		while (rdr.MoveNextPeer()) {
			int lnki_ext = rdr.ReadByte(Xob_lnki_regy_tbl.Fld_lnki_ext);
			String orig_media_type = rdr.ReadStrOr(Xob_orig_regy_tbl.Fld_orig_media_type, "");	// convert nulls to ""
			lnki_ext = rdr.ReadInt(Xob_orig_regy_tbl.Fld_orig_file_ext);
			int lnki_id = rdr.ReadInt(Xob_lnki_regy_tbl.Fld_lnki_id);
			int lnki_tier_id = rdr.ReadInt(Xob_lnki_regy_tbl.Fld_lnki_tier_id);
			byte orig_repo = rdr.ReadByte(Xob_orig_regy_tbl.Fld_orig_repo);
			int orig_page_id = rdr.ReadIntOr(Xob_orig_regy_tbl.Fld_orig_page_id, -1);
			if (orig_page_id == -1) continue;	// no orig found; ignore
			String join_ttl = rdr.ReadStr(Xob_orig_regy_tbl.Fld_orig_file_ttl);
			String redirect_src = rdr.ReadStr(Xob_orig_regy_tbl.Fld_lnki_ttl);
			if (String_.Eq(join_ttl, redirect_src))	// lnki_ttl is same as redirect_src; not a redirect
				redirect_src = "";
			int orig_w = rdr.ReadIntOr(Xob_orig_regy_tbl.Fld_orig_w, -1);
			int orig_h = rdr.ReadIntOr(Xob_orig_regy_tbl.Fld_orig_h, -1);
			int orig_size = rdr.ReadIntOr(Xob_orig_regy_tbl.Fld_orig_size, -1);
			if (orig_size > ext_maxs[lnki_ext]) continue;
			int lnki_page_id = rdr.ReadInt(Xob_lnki_regy_tbl.Fld_lnki_page_id);
			Xob_xfer_temp_tbl.Insert(trg_stmt, lnki_id, lnki_tier_id, lnki_page_id, orig_repo, orig_page_id, join_ttl, redirect_src, lnki_ext, Xop_lnki_type.Id_none, orig_media_type
			, Bool_.Y									// orig is y
			, orig_w, orig_h
			, orig_w, orig_h							// file_w, file_h is same as orig_w,orig_h; i.e.: make same file_w as orig_w
			, Xof_img_size.Null, Xof_img_size.Null		// html_w, html_h is -1; i.e.: will not be displayed in page at specific size (this matches logic in Xobldr__xfer_temp__insert_thm)
			, rdr.ReadInt(Xob_lnki_regy_tbl.Fld_lnki_w)
			, rdr.ReadInt(Xob_lnki_regy_tbl.Fld_lnki_h)
			, Xop_lnki_tkn.Upright_null
			, Xof_lnki_time.Null
			, Xof_lnki_page.Null
			, 0);
		}
		conn.Txn_end();
	}
	private long[] Calc_ext_max() {
		Xof_rule_grp ext_rules = wiki.Appe().File_mgr().Ext_rules().Get_or_new(ext_rules_key);
		long[] rv = new long[Xof_ext_.Id__max];
		for (int i = 0; i < Xof_ext_.Id__max; i++) {
			byte[] ext = Xof_ext_.Get_ext_by_id_(i);
			Xof_rule_itm ext_rule = ext_rules.Get_or_null(ext);
			long max = ext_rule == null ? 0 : ext_rule.Make_max();
			rv[i] = max;
		}
		return rv;
	}
	private static final    String
		Sql_select_clause = String_.Concat_lines_nl
	(	"SELECT  DISTINCT"
	,   "        l.lnki_id"
//		,	",       lnki_ttl"
	,	",       l.lnki_ext"
	,	",       l.lnki_page_id"
	,	",       o.orig_repo"
	,	",       o.orig_page_id"
//		,	",       orig_file_id"
	,	",       o.orig_file_ttl"
	,	",       o.orig_file_ext"
	,	",       o.lnki_ttl"
	,	",       o.orig_size"
	,	",       o.orig_w"
	,	",       o.orig_h"
	,	",       o.orig_media_type"
//		,	",       orig_bits"
	,	"FROM    lnki_regy l"
	,	"        JOIN orig_regy o ON o.lnki_ttl = l.lnki_ttl"
	,	"WHERE   o.orig_file_ttl IS NOT NULL"
	,	"ORDER BY o.orig_file_ttl DESC"
	);
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_ext_rules_))			ext_rules_key = m.ReadBry("v");
		else	return super.Invk (ctx, ikey, k, m);
		return this;
	}	private static final String Invk_ext_rules_ = "ext_rules_";

	public static final String BLDR_CMD_KEY = "file.xfer_temp.orig";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__xfer_temp__insert_orig(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__xfer_temp__insert_orig(bldr, wiki);}
}

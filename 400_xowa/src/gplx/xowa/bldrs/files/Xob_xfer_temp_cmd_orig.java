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
package gplx.xowa.bldrs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.dbs.*; import gplx.xowa.dbs.*;
import gplx.xowa.files.*; import gplx.xowa.files.exts.*;
import gplx.xowa.bldrs.oimgs.*;
public class Xob_xfer_temp_cmd_orig extends Xob_itm_basic_base implements Xob_cmd {
	private byte[] ext_rules_key = Bry_.Empty;
	public Xob_xfer_temp_cmd_orig(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return KEY_oimg;} public static final String KEY_oimg = "file.xfer_temp.orig";
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		Db_conn conn = Xodb_db_file.init__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		Xob_xfer_temp_tbl.Create_table(conn);
		Db_stmt trg_stmt = Xob_xfer_temp_tbl.Insert_stmt(conn);
		conn.Txn_mgr().Txn_bgn_if_none();
		DataRdr rdr = conn.Exec_sql_as_rdr(Sql_select);
		long[] ext_maxs = Calc_ext_max();
		while (rdr.MoveNextPeer()) {
			int lnki_ext = rdr.ReadByte(Xob_lnki_regy_tbl.Fld_lnki_ext);
			String orig_media_type = rdr.ReadStrOr(Xob_orig_regy_tbl.Fld_orig_media_type, "");	// convert nulls to ""
			lnki_ext = rdr.ReadInt(Xob_orig_regy_tbl.Fld_orig_file_ext);
			int lnki_id = rdr.ReadInt(Xob_lnki_regy_tbl.Fld_lnki_id);
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
			Xob_xfer_temp_tbl.Insert(trg_stmt, lnki_id, lnki_page_id, orig_repo, orig_page_id, join_ttl, redirect_src, lnki_ext, Xop_lnki_type.Id_none, orig_media_type
			, Bool_.Y									// orig is y
			, orig_w, orig_h
			, orig_w, orig_h							// file_w, file_h is same as orig_w,orig_h; i.e.: make same file_w as orig_w
			, Xof_img_size.Null, Xof_img_size.Null		// html_w, html_h is -1; i.e.: will not be displayed in page at specific size (this matches logic in Xob_xfer_temp_cmd_thumb)
			, Xof_lnki_time.Null
			, Xof_lnki_page.Null
			, 0);
		}
		conn.Txn_mgr().Txn_end_all();
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
	public void Cmd_run() {}
	public void Cmd_end() {}
	public void Cmd_print() {}
	private static final String
		Sql_select = String_.Concat_lines_nl
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
}

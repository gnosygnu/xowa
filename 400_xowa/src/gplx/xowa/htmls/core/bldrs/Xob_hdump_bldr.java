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
package gplx.xowa.htmls.core.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.brys.*; import gplx.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.apps.apis.xowa.bldrs.imports.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.dbs.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.data.*;	
public class Xob_hdump_bldr implements Gfo_invk {
	private boolean enabled, hzip_enabled, hzip_diff, hzip_b256; private byte zip_tid = Byte_.Max_value_127;
	private Xowe_wiki wiki; private Xow_hdump_mgr hdump_mgr; private Xob_hdump_tbl_retriever html_tbl_retriever;
	private Xoh_stat_tbl stat_tbl; private Xoh_stat_itm stat_itm;
	private int prv_row_len = 0;
	private final    Xoh_page tmp_hpg = new Xoh_page(); private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private boolean op_sys_is_wnt;
	public boolean Init(Xowe_wiki wiki, Db_conn make_conn, Xob_hdump_tbl_retriever html_tbl_retriever) {
		if (!enabled) return false;
		this.op_sys_is_wnt = gplx.core.envs.Op_sys.Cur().Tid_is_wnt();
		this.wiki = wiki; this.hdump_mgr = wiki.Html__hdump_mgr(); this.html_tbl_retriever = html_tbl_retriever;
		this.stat_tbl = new Xoh_stat_tbl(make_conn); this.stat_itm = hdump_mgr.Hzip_mgr().Hctx().Hzip__stat();
		
		Xoapi_import import_cfg = wiki.Appe().Api_root().Bldr().Wiki().Import();
		if (zip_tid == Byte_.Max_value_127) zip_tid = import_cfg.Zip_tid_html();
		hdump_mgr.Init_by_db(zip_tid, hzip_enabled, hzip_b256);
		return true;
	}
	public void Insert(Xoae_page wpg) {
		// clear
		tmp_hpg.Clear();			// NOTE: must clear tmp_hpg or else will leak memory during mass build; DATE:2016-01-09
		wpg.File_queue().Clear();	// need to reset uid to 0, else xowa_file_# will resume from last

		// write to html
		wiki.Html_mgr().Page_wtr_mgr().Wkr(Xopg_page_.Tid_read).Write_hdump(tmp_bfr, wiki.Parser_mgr().Ctx(), Xoh_wtr_ctx.Hdump, wpg);
		byte[] orig_bry = tmp_bfr.To_bry_and_clear();
		wpg.Db().Html().Html_bry_(orig_bry);

		// save to db
		Xowd_html_tbl html_tbl = html_tbl_retriever.Get_html_tbl(wpg.Ttl().Ns(), prv_row_len);	// get html_tbl
		this.prv_row_len = hdump_mgr.Save_mgr().Save(tmp_hpg.Ctor_by_page(tmp_bfr, wpg), html_tbl, true);	// save to db
		stat_tbl.Insert(tmp_hpg, stat_itm, wpg.Root().Root_src().length, tmp_hpg.Db().Html().Html_bry().length, prv_row_len); // save stats

		// run hzip diff if enabled
		if (hzip_diff) {
			byte[] expd_bry = op_sys_is_wnt ? Bry_.Replace(tmp_bfr, orig_bry, Byte_ascii.Cr_lf_bry, Byte_ascii.Nl_bry) : orig_bry;	// tidy adds crlf if wnt
			byte[] actl_bry = hdump_mgr.Load_mgr().Decode_as_bry(tmp_bfr, tmp_hpg, hdump_mgr.Save_mgr().Src_as_hzip(), Bool_.Y);
			byte[][] diff = Bry_diff_.Diff_1st_line(expd_bry, actl_bry);
			if (diff != null)
				Gfo_usr_dlg_.Instance.Warn_many("", "", String_.Format("hzip diff: page={0} lhs='{1}' rhs='{2}'", tmp_hpg.Url_bry_safe(), diff[0], diff[1]));
		}
	}
	public void Commit() {
		html_tbl_retriever.Commit();
		// wiki_db_mgr.Tbl__cfg().Update_long(Cfg_grp_hdump_make, Cfg_itm_hdump_size, hdump_db_size);	// update cfg; should happen after commit entries
	}
	public void Term() {this.Commit(); html_tbl_retriever.Rls_all();}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled_))					enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_zip_tid_))					zip_tid = m.ReadByte("v");
		else if	(ctx.Match(k, Invk_hzip_enabled_))				hzip_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_hzip_diff_))					hzip_diff = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_hzip_b256_))					hzip_b256 = m.ReadYn("v");
		else													return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_enabled_ = "enabled_", Invk_zip_tid_ = "zip_tid_", Invk_hzip_enabled_ = "hzip_enabled_", Invk_hzip_diff_ = "hzip_diff_", Invk_hzip_b256_ = "hzip_b256_";
}

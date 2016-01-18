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
import gplx.core.brys.*;
import gplx.dbs.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.dbs.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.apps.apis.xowa.bldrs.imports.*;
public class Xob_hdump_bldr implements GfoInvkAble {
	private boolean enabled, hzip_enabled, hzip_diff, hzip_b256; private byte zip_tid = Byte_.Max_value_127;
	private Xowe_wiki wiki; private Xow_hdump_mgr hdump_mgr;
	private Xob_ns_to_db_mgr ns_to_db_mgr; int prv_row_len = 0;
	private Xoh_stat_tbl stats_tbl; private Xoh_stat_itm tmp_stat_itm;
	private final Xoh_page tmp_hpg = new Xoh_page(); private final Bry_bfr tmp_bfr = Bry_bfr.new_();
	private boolean op_sys_is_wnt;
	public boolean Init(Xowe_wiki wiki, Db_conn make_conn) {
		if (!enabled) return false;
		this.op_sys_is_wnt = gplx.core.envs.Op_sys.Cur().Tid_is_wnt();
		this.wiki = wiki; this.hdump_mgr = wiki.Html__hdump_mgr(); this.tmp_stat_itm = hdump_mgr.Hzip_mgr().Hctx().Hzip__stat();
		this.stats_tbl = new Xoh_stat_tbl(make_conn);
		Xoapi_import import_cfg = wiki.Appe().Api_root().Bldr().Wiki().Import();
		if (zip_tid == Byte_.Max_value_127) zip_tid = import_cfg.Zip_tid_html();
		hdump_mgr.Init_by_db(zip_tid, hzip_enabled, hzip_b256);
		Xowd_db_mgr core_data_mgr = wiki.Db_mgr_as_sql().Core_data_mgr();
		this.ns_to_db_mgr = new Xob_ns_to_db_mgr(new Xob_ns_to_db_wkr__html(core_data_mgr.Db__core()), core_data_mgr, import_cfg.Html_db_max());
		Xob_ns_file_itm.Init_ns_bldr_data(Xowd_db_file_.Tid_html_data, wiki.Ns_mgr(), gplx.xowa.apps.apis.xowa.bldrs.imports.Xoapi_import.Ns_file_map__each);
		return true;
	}
	public void Insert(Xoae_page page) {
		tmp_hpg.Clear();	// NOTE: must clear tmp_hpg or else will leak memory during mass build; DATE:2016-01-09
		page.File_queue().Clear();																				// need to reset uid to 0, else xowa_file_# will resume from last
		wiki.Html_mgr().Page_wtr_mgr().Wkr(Xopg_page_.Tid_read).Write_body(tmp_bfr, Xoh_wtr_ctx.Hdump, page);	// write to html in hdump mode
		byte[] orig_bry = tmp_bfr.To_bry_and_clear();
		page.Hdump_data().Body_(orig_bry);																		// write to body bry
		Xowd_db_file html_db = ns_to_db_mgr.Get_by_ns(page.Ttl().Ns().Bldr_data(), prv_row_len);				// get html_db
		this.prv_row_len = hdump_mgr.Save_mgr().Save(tmp_hpg.Ctor_by_page(tmp_bfr, page), html_db, true);		// save to db
		if (hzip_diff) Hzip_exec(orig_bry);
		stats_tbl.Insert(tmp_hpg, tmp_stat_itm, page.Root().Root_src().length, tmp_hpg.Body().length, prv_row_len); // save stats
	}
	public void Bld_term() {this.Commit(); ns_to_db_mgr.Rls_all();}
	public void Commit() {
		ns_to_db_mgr.Commit();
		// wiki_db_mgr.Tbl__cfg().Update_long(Cfg_grp_hdump_make, Cfg_itm_hdump_size, hdump_db_size);	// update cfg; should happen after commit entries
	}
	private void Hzip_exec(byte[] orig_bry) {
		byte[] expd_bry = op_sys_is_wnt ? Bry_.Replace(tmp_bfr, orig_bry, Byte_ascii.Cr_lf_bry, Byte_ascii.Nl_bry) : orig_bry;
		byte[] actl_bry = hdump_mgr.Load_mgr().Decode_as_bry(tmp_bfr, tmp_hpg, hdump_mgr.Save_mgr().Src_as_hzip(), Bool_.Y);
		byte[][] diff = Bry_diff_.Diff_1st_line(expd_bry, actl_bry);
		if (diff != null)
			Gfo_usr_dlg_.Instance.Warn_many("", "", String_.Format("hzip diff: page={0} lhs='{1}' rhs='{2}'", tmp_hpg.Url_bry_safe(), diff[0], diff[1]));
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled_))					enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_zip_tid_))					zip_tid = m.ReadByte("v");
		else if	(ctx.Match(k, Invk_hzip_enabled_))				hzip_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_hzip_diff_))					hzip_diff = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_hzip_b256_))					hzip_b256 = m.ReadYn("v");
		else													return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_enabled_ = "enabled_", Invk_zip_tid_ = "zip_tid_", Invk_hzip_enabled_ = "hzip_enabled_", Invk_hzip_diff_ = "hzip_diff_", Invk_hzip_b256_ = "hzip_b256_";
}

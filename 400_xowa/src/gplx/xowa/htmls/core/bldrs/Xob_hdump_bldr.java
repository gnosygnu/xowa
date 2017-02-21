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
package gplx.xowa.htmls.core.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.brys.*; import gplx.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.apps.apis.xowa.bldrs.imports.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.dbs.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.data.*;
import gplx.xowa.parsers.*;
public class Xob_hdump_bldr implements Gfo_invk {
	private boolean enabled, hzip_enabled, hzip_diff, hzip_b256; private byte zip_tid = Byte_.Max_value_127;
	private Xowe_wiki wiki; private Xob_hdump_tbl_retriever html_tbl_retriever;
	private Xoh_stat_tbl stat_tbl; private Xoh_stat_itm stat_itm;
	private int prv_row_len = 0;
	private final    Xoh_page tmp_hpg = new Xoh_page(); private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private boolean op_sys_is_wnt;
	private byte[] toc_label = Bry_.Empty;
	public Xob_hdump_bldr Enabled_(boolean v) {this.enabled = v; return this;}
	public Xob_hdump_bldr Hzip_enabled_(boolean v) {this.hzip_enabled = v; return this;}
	public Xob_hdump_bldr Hzip_diff_(boolean v) {this.hzip_diff = v; return this;}
	public Xob_hdump_bldr Zip_tid_(byte v) {this.zip_tid = v; return this;}
	public Xow_hdump_mgr Hdump_mgr() {return hdump_mgr;} private Xow_hdump_mgr hdump_mgr; 
	public boolean Init(Xowe_wiki wiki, Db_conn make_conn, Xob_hdump_tbl_retriever html_tbl_retriever) {
		if (!enabled) return false;
		this.op_sys_is_wnt = gplx.core.envs.Op_sys.Cur().Tid_is_wnt();
		this.wiki = wiki; this.hdump_mgr = wiki.Html__hdump_mgr(); this.html_tbl_retriever = html_tbl_retriever;
		this.stat_tbl = new Xoh_stat_tbl(make_conn); this.stat_itm = hdump_mgr.Hzip_mgr().Hctx().Hzip__stat();
		this.toc_label = wiki.Msg_mgr().Val_by_id(gplx.xowa.langs.msgs.Xol_msg_itm_.Id_toc);
		
		if (zip_tid == Byte_.Max_value_127) zip_tid = Xobldr_cfg.Zip_mode__html(wiki.App());
		hdump_mgr.Init_by_db(zip_tid, hzip_enabled, hzip_b256);
		return true;
	}
	public void Insert(Xop_ctx ctx, Xoae_page wpg) {
		// clear
		tmp_hpg.Clear();			// NOTE: must clear tmp_hpg or else will leak memory during mass build; DATE:2016-01-09
		wpg.File_queue().Clear();	// need to reset uid to 0, else xowa_file_# will resume from last

		// write to html
		Xoa_ttl ttl = wpg.Ttl();
		boolean is_wikitext = Xow_page_tid.Identify(wpg.Wiki().Domain_tid(), ttl.Ns().Id(), ttl.Page_db()) == Xow_page_tid.Tid_wikitext;
		byte[] orig_bry = Bry_.Empty;
		if (is_wikitext) {
			wiki.Html_mgr().Page_wtr_mgr().Wkr(Xopg_page_.Tid_read).Write_hdump(tmp_bfr, ctx, Xoh_wtr_ctx.Hdump, wpg);
			orig_bry = tmp_bfr.To_bry_and_clear();
			wpg.Db().Html().Html_bry_(orig_bry);
		}
		else {	// not wikitext; EX: pages in MediaWiki: ns; DATE:2016-09-12
			wpg.Db().Html().Html_bry_(wpg.Db().Text().Text_bry());
		}

		// save to db
		Xowd_html_tbl html_tbl = html_tbl_retriever.Get_html_tbl(wpg.Ttl().Ns(), prv_row_len);	// get html_tbl
		this.prv_row_len = hdump_mgr.Save_mgr().Save(tmp_hpg.Ctor_by_hdiff(tmp_bfr, wpg, toc_label), html_tbl, true, is_wikitext);	// save to db
		stat_tbl.Insert(tmp_hpg, stat_itm, wpg.Root().Root_src().length, tmp_hpg.Db().Html().Html_bry().length, prv_row_len); // save stats

		// run hzip diff if enabled
		if (hzip_diff && is_wikitext) {
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

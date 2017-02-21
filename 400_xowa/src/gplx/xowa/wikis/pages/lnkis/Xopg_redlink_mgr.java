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
package gplx.xowa.wikis.pages.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.guis.cbks.js.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.langs.vnts.*;
public class Xopg_redlink_mgr implements Gfo_invk {
	private final    Xoa_page page; private final    Xog_js_wkr js_wkr;
	public Xopg_redlink_mgr(Xoa_page page, Xog_js_wkr js_wkr) {this.page = page; this.js_wkr = js_wkr;	}
	private void Redlink() {
		// init; exit if redlink disabled (on Module pages)
		Xopg_lnki_list lnki_list = page.Html_data().Redlink_list(); if (lnki_list.Disabled()) return;
		Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Instance;
		Xow_wiki wiki = page.Wiki();
		Ordered_hash lnki_hash = Ordered_hash_.New_bry();

		// create unique list of page_rows
		int len = lnki_list.Len();
		usr_dlg.Log_many("", "", "redlink.redlink_bgn: page=~{0} total_links=~{1}", page.Ttl().Raw(), len);
		for (int i = 0; i < len; ++i) {
			if (usr_dlg.Canceled()) return;
			Xoa_ttl ttl = lnki_list.Get_at(i).Ttl();
			Xowd_page_itm page_row = new Xowd_page_itm().Ttl_(ttl);
			byte[] full_db = ttl.Full_db();
			if (!lnki_hash.Has(full_db))	// only search page_table once for multiple identical redlinks; EX: "[[Missing]] [[Missing]]"
				lnki_hash.Add(full_db, page_row);
		}

		// load page_rows from page_tbl
		int page_len = lnki_hash.Len();
		for (int i = 0; i < page_len; i += Batch_size) {
			if (usr_dlg.Canceled()) return;
			int end = i + Batch_size; if (end > page_len) end = page_len;
			wiki.Data__core_mgr().Tbl__page().Select_in__ns_ttl(usr_dlg, lnki_hash, wiki.Ns_mgr(), Bool_.Y, i, end);
			// wiki.Db_mgr().Load_mgr().Load_by_ttls(usr_dlg, lnki_hash, Bool_.Y, i, end);
		}

		// cross-check page_rows against lnki_list; redlink if missing;
		boolean vnt_enabled = wiki.Lang().Vnt_mgr().Enabled();
		Xol_vnt_mgr vnt_mgr = wiki.Lang().Vnt_mgr();
		int redlink_count = 0;
		for (int i = 0; i < len; ++i) {
			Xopg_lnki_itm lnki = lnki_list.Get_at(i);
			byte[] full_db = lnki.Ttl().Full_db();
			Xowd_page_itm page_row = (Xowd_page_itm)lnki_hash.Get_by(full_db);
			if (page_row.Exists()) continue;	// page exists; nothing to do;

			// for vnt languages, convert missing ttl to vnt and check again; EX: [[zh_cn]] will check for page_ttl for [[zh_tw]]
			String html_uid = Xopg_lnki_list.Lnki_id_prefix + Int_.To_str(lnki.Html_uid());
			if (vnt_enabled) {
				Xowd_page_itm vnt_page = vnt_mgr.Convert_mgr().Convert_ttl(wiki, lnki.Ttl());	// check db
				if (vnt_page != null) {	// vnt found; update href to point to vnt
					Xoa_ttl vnt_ttl = wiki.Ttl_parse(lnki.Ttl().Ns().Id(), vnt_page.Ttl_page_db());
					js_wkr.Html_atr_set(html_uid, "href", "/wiki/" + String_.new_u8(vnt_ttl.Full_url()));
					if (!String_.Eq(vnt_mgr.Html__lnki_style(), "")) js_wkr.Html_atr_set(html_uid, "style", vnt_mgr.Html__lnki_style());	// colorize for debugging
					continue;
				}
			}

			// lnki is missing; redlink it
			if (usr_dlg.Canceled()) return;
			Js_img_mgr.Update_link_missing(js_wkr, html_uid);
			++redlink_count;
		}
		usr_dlg.Log_many("", "", "redlink.redlink_end: redlinks_run=~{0}", redlink_count);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_run)) {synchronized (this) {Redlink();}}	// NOTE: attempt to eliminate random IndexBounds errors; DATE:2014-09-02
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	public static final String Invk_run = "run";
	private static final int Batch_size = 32;

	public static void Run_async(Xoa_page pg, Xog_js_wkr js_wkr) {
		try {			
			Xopg_redlink_mgr mgr = new Xopg_redlink_mgr(pg, js_wkr);
			gplx.core.threads.Thread_adp_.Start_by_key(gplx.xowa.apps.Xoa_thread_.Key_page_redlink, mgr, gplx.xowa.wikis.pages.lnkis.Xopg_redlink_mgr.Invk_run);
		}	catch (Exception e) {Gfo_usr_dlg_.Instance.Warn_many("", "", "page.thread.redlinks: page=~{0} err=~{1}", pg.Ttl().Raw(), Err_.Message_gplx_full(e));}
	}
}

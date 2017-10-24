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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import gplx.core.primitives.*;
import gplx.xowa.files.*; import gplx.xowa.files.xfers.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.repos.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.*; 
import gplx.xowa.wikis.tdbs.metas.*;
import gplx.xowa.htmls.core.htmls.*;
public class Xoh_file_mgr {
	private final    Xowe_wiki wiki;
	private final    Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	public Xoh_file_mgr(Xowe_wiki wiki, Xow_html_mgr html_mgr, Xoh_html_wtr html_wtr) {
		this.wiki = wiki; this.file_wtr = new Xoh_file_wtr__basic(wiki, html_mgr, html_wtr);
	}
	public Xoh_file_wtr__basic File_wtr() {return file_wtr;} private final    Xoh_file_wtr__basic file_wtr;
	public void Init_by_wiki(Xowe_wiki wiki) {
		file_wtr.Init_by_wiki(wiki);
	}
	public void Init_by_page(Xoh_wtr_ctx hctx, Xoae_page page) {file_wtr.Init_by_page(hctx, page);}
	public void Write_or_queue(Bry_bfr bfr, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki) {Write_or_queue(bfr, page, ctx, hctx, src, lnki, file_wtr.Bld_alt(Bool_.N, ctx, Xoh_wtr_ctx.Alt, src, lnki));}
	public void Write_or_queue(Bry_bfr bfr, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, byte[] alt_text) {
		file_wtr.Write_file(bfr, ctx, hctx, src, lnki, this.Lnki_eval(Xof_exec_tid.Tid_wiki_page, ctx, page, lnki), alt_text);
	}
	public Xof_file_itm Lnki_eval(int exec_tid, Xop_ctx ctx, Xoae_page page, Xop_lnki_tkn lnki) {return Lnki_eval(exec_tid, ctx, page, page.File_queue(), lnki.Ttl().Page_url(), lnki.Lnki_type(), lnki.Upright(), lnki.W(), lnki.H(), lnki.Time(), lnki.Page(), lnki.Ns_id() == Xow_ns_.Tid__media);}
	public Xof_file_itm Lnki_eval(int exec_tid, Xop_ctx ctx, Xoae_page page, Xof_xfer_queue queue, byte[] lnki_ttl, byte lnki_type, double lnki_upright, int lnki_w, int lnki_h, double lnki_time, int lnki_page, boolean lnki_is_media_ns) {
		int uid = queue.Html_uid().Val_add();
		Xof_xfer_itm xfer = new Xof_xfer_itm();
		xfer.Init_at_lnki(exec_tid, page.Wiki().Domain_itm().Abrv_xo(), lnki_ttl, lnki_type, lnki_upright, lnki_w, lnki_h, lnki_time, lnki_page, Xof_patch_upright_tid_.Tid_all);
		xfer.Init_at_hdoc(uid, Xof_html_elem.Tid_img);
		Xowe_wiki source_wiki = (Xowe_wiki)page.Commons_mgr().Source_wiki_or(wiki);
		boolean found = Find_file(ctx, source_wiki, xfer);
		boolean file_queue_add = File_queue_add(source_wiki, xfer, lnki_is_media_ns, found);
		if (file_queue_add)
			queue.Add(xfer);
		else
			xfer.File_exists_(found);
		return xfer;
	}
	private boolean Find_file(Xop_ctx ctx, Xowe_wiki source_wiki, Xof_xfer_itm xfer) {
		if (source_wiki.File__fsdb_mode().Tid__bld()) {
			Xof_orig_itm orig = source_wiki.File__orig_mgr().Find_by_ttl_or_null(xfer.Lnki_ttl());
			if (orig == null)
				return false;
			else {
				byte repo_id = orig.Repo();
				Xof_repo_itm repo = source_wiki.File_mgr().Repo_mgr().Repos_get_at(repo_id).Trg();
				xfer.Init_at_orig(orig.Repo(), repo.Wiki_domain(), orig.Ttl(), orig.Ext(), orig.W(), orig.H(), orig.Redirect());
				xfer.Init_at_html(Xof_exec_tid.Tid_wiki_page, wiki.Parser_mgr().Img_size(), repo, url_bldr);
				return true;
			}
		}
		if (	source_wiki.File__fsdb_mode() == null				// ignore if null; occurs during some tests; also null-ref check for next
			||	source_wiki.File__fsdb_mode().Tid__v2__bld()		// ignore builds
			)
			return false;	
		if (source_wiki.File_mgr().Version() == Xow_file_mgr.Version_2)
			return ctx.App().User().User_db_mgr().File__xfer_itm_finder().Find(source_wiki, xfer.Lnki_exec_tid(), xfer, ctx.Page().Url_bry_safe());
		else
			return source_wiki.File_mgr().Find_meta(xfer);
	}
	private static boolean File_queue_add(Xowe_wiki wiki, Xof_xfer_itm xfer, boolean lnki_is_media_ns, boolean found) {
		if (!wiki.File_mgr().Cfg_download().Enabled()) return false;
		if (lnki_is_media_ns) return false;
		switch (wiki.File_mgr().Cfg_download().Redownload()) {
			case Xof_cfg_download.Redownload_none:
				if (found) return false;
				if (!found 
					&& xfer.Dbmeta_itm() != null		// null check; fsdb_call does not set meta
					&& xfer.Dbmeta_itm().Orig_exists() == Xof_meta_itm.Exists_n) 
					return false;	// not found, and orig_exists is n; do not download again (NOTE: even if current lnki is thumb, don't bother looking for thumb if orig is missing)
				break;
			case Xof_cfg_download.Redownload_missing:
				if (found) return false;
				break;
			case Xof_cfg_download.Redownload_all:
				break;
		}
		return true;
	}
}

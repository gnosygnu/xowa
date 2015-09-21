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
package gplx.xowa.html.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.core.primitives.*;
import gplx.xowa.files.*; import gplx.xowa.files.xfers.*; import gplx.xowa.parsers.lnkis.*; 
import gplx.xowa.nss.*;
import gplx.xowa.parsers.*; 
import gplx.xowa.tdbs.metas.*;
public class Xoh_file_mgr {
	private final Xowe_wiki wiki;
	public Xoh_file_mgr(Xowe_wiki wiki, Xow_html_mgr html_mgr, Xoh_html_wtr html_wtr) {
		this.wiki = wiki; this.file_wtr = new Xoh_file_wtr__basic(wiki, html_mgr, html_wtr);
	}
	public Xoh_file_wtr__basic File_wtr() {return file_wtr;} private final Xoh_file_wtr__basic file_wtr;
	public void Init_by_page(Xoh_wtr_ctx hctx, Xoae_page page) {file_wtr.Init_by_page(hctx, page);}
	public void Write_or_queue(Bry_bfr bfr, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki) {Write_or_queue(bfr, page, ctx, hctx, src, lnki, file_wtr.Arg_alt_text(ctx, src, lnki));}
	public void Write_or_queue(Bry_bfr bfr, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, byte[] alt_text) {
		file_wtr.Write_file(bfr, ctx, hctx, src, lnki, this.Lnki_eval(Xof_exec_tid.Tid_wiki_page, ctx, page, lnki), alt_text);
	}
	public Xof_file_itm Lnki_eval(int exec_tid, Xop_ctx ctx, Xoae_page page, Xop_lnki_tkn lnki) {return Lnki_eval(exec_tid, ctx, page, page.File_queue(), lnki.Ttl().Page_url(), lnki.Lnki_type(), lnki.Upright(), lnki.W(), lnki.H(), lnki.Time(), lnki.Page(), lnki.Ns_id() == Xow_ns_.Id_media);}
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
		if (	source_wiki.File__fsdb_mode() == null				// ignore if null; occurs during some tests; also null-ref check for next
			||	source_wiki.File__fsdb_mode().Tid_v2_bld()			// ignore builds
			)
			return false;	
		if (source_wiki.File_mgr().Version() == Xow_file_mgr.Version_2)
			return ctx.App().User().User_db_mgr().File__xfer_itm_finder().Find(source_wiki, xfer.Lnki_exec_tid(), xfer, ctx.Cur_page().Url_bry_safe());
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
					&& xfer.Meta_itm() != null		// null check; fsdb_call does not set meta
					&& xfer.Meta_itm().Orig_exists() == Xof_meta_itm.Exists_n) 
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

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
import gplx.xowa.files.*; import gplx.xowa.parsers.lnkis.*;
public class Xoh_file_mgr {
	private final Xowe_wiki wiki; private final Bool_obj_ref queue_add_ref = Bool_obj_ref.n_();
	private final Xof_xfer_itm tmp_xfer_itm = new Xof_xfer_itm();
	public Xoh_file_mgr(Xowe_wiki wiki, Xow_html_mgr html_mgr, Xoh_html_wtr html_wtr) {
		this.wiki = wiki;
		this.file_wtr = new Xoh_file_wtr__basic(wiki, html_mgr, html_wtr);
	}
	public Xoh_file_wtr__basic File_wtr() {return file_wtr;} private final Xoh_file_wtr__basic file_wtr;
	public void Init_by_page(Xoh_wtr_ctx hctx, Xoae_page page) {file_wtr.Init_by_page(hctx, page);}
	public void Write_or_queue(Bry_bfr bfr, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki) {
		Xof_xfer_itm xfer_itm = this.Lnki_eval(ctx, page, lnki, queue_add_ref);
		file_wtr.Write_file(bfr, ctx, hctx, src, lnki, xfer_itm, file_wtr.Arg_alt_text(ctx, src, lnki));
	}
	public void Write_or_queue(Bry_bfr bfr, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, byte[] alt_text) {
		Xof_xfer_itm xfer_itm = this.Lnki_eval(ctx, page, lnki, queue_add_ref);
		file_wtr.Write_file(bfr, ctx, hctx, src, lnki, xfer_itm, alt_text);
	}
	public Xof_xfer_itm Lnki_eval(Xop_ctx ctx, Xoae_page page, Xop_lnki_tkn lnki, Bool_obj_ref queue_add_ref) {return Lnki_eval(ctx, page.File_queue(), lnki.Ttl().Page_url(), lnki.Lnki_type(), lnki.W(), lnki.H(), lnki.Upright(), lnki.Time(), lnki.Page(), lnki.Ns_id() == Xow_ns_.Id_media, queue_add_ref);}
	public Xof_xfer_itm Lnki_eval(Xop_ctx ctx, Xof_xfer_queue queue, byte[] lnki_ttl, byte lnki_type, int lnki_w, int lnki_h, double lnki_upright, double lnki_thumbtime, int lnki_page, boolean lnki_is_media_ns, Bool_obj_ref queue_add_ref) {
		queue_add_ref.Val_n_();
		int uid = queue.Html_uid().Val_add();
		tmp_xfer_itm.Clear().Init_by_lnki(lnki_ttl, Bry_.Empty, lnki_type, lnki_w, lnki_h, lnki_upright, lnki_thumbtime, lnki_page).Set__html_uid_tid(uid, Xof_html_elem.Tid_img);
		boolean found = Find_file(ctx, tmp_xfer_itm);
		boolean file_queue_add = File_queue_add(wiki, tmp_xfer_itm, lnki_is_media_ns, found);
		Xof_xfer_itm rv = tmp_xfer_itm;
		if (file_queue_add) {
			queue_add_ref.Val_y_();
			rv = tmp_xfer_itm.Clone();
			queue.Add(rv);
			return rv;
		}
		rv.File_found_(found);
		return rv;
	}
	private boolean Find_file(Xop_ctx ctx, Xof_xfer_itm xfer_itm) {
		if (wiki.File_mgr().Version() == Xow_file_mgr.Version_2)
			return ctx.Cur_page().Lnki_file_mgr().Find(wiki, ctx.Cur_page(), Xof_exec_tid.Tid_wiki_page, xfer_itm);
		else
			return wiki.File_mgr().Find_meta(xfer_itm);
	}
	private static boolean File_queue_add(Xowe_wiki wiki, Xof_xfer_itm xfer_itm, boolean lnki_is_media_ns, boolean found) {
		if (!wiki.File_mgr().Cfg_download().Enabled()) return false;
		if (lnki_is_media_ns) return false;
		switch (wiki.File_mgr().Cfg_download().Redownload()) {
			case Xof_cfg_download.Redownload_none:
				if (found) return false;
				if (!found 
					&& xfer_itm.Meta_itm() != null		// null check; fsdb_call does not set meta
					&& xfer_itm.Meta_itm().Orig_exists() == Xof_meta_itm.Exists_n) 
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

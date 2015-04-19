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
package gplx.xowa; import gplx.*;
import gplx.core.primitives.*;
import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.origs.*;
import gplx.xowa.files.gui.*;
public class Xof_xfer_queue {
	private final ListAdp xfer_list = ListAdp_.new_(); private final OrderedHash dirty_meta_mgrs = OrderedHash_.new_bry_();
	public Int_obj_ref Html_uid() {return html_uid;} private Int_obj_ref html_uid = Int_obj_ref.neg1_();
	public int Count() {return xfer_list.Count();}
	public void Clear() {
		dirty_meta_mgrs.Clear();
		xfer_list.Clear();
		html_uid.Val_neg1_();
	}
	public void Add(Xof_xfer_itm xfer_itm) {xfer_list.Add(xfer_itm);}
	public void Exec(byte exec_tid, Gfo_usr_dlg wtr, Xowe_wiki wiki, Xoae_page page) {
		if (wiki.File_mgr().Version() == Xow_file_mgr.Version_2)
			Exec_v2(exec_tid, wtr, wiki, page);
		else
			Exec_v1(exec_tid, wtr, wiki, page);
	}
	private void Exec_v1(byte exec_tid, Gfo_usr_dlg wtr, Xowe_wiki wiki, Xoae_page page) {
		Xof_meta_mgr meta_mgr = null;
		int xfer_len = xfer_list.Count();
		for (int i = 0; i < xfer_len; i++) {
			if (wiki.Appe().Usr_dlg().Canceled()) break;
			Xof_xfer_itm xfer_itm = (Xof_xfer_itm)xfer_list.FetchAt(i);
			meta_mgr = xfer_itm.Meta_itm().Owner_fil().Owner_mgr();
			byte[] meta_mgr_key = meta_mgr.Wiki().Domain_bry();
			if (!dirty_meta_mgrs.Has(meta_mgr_key)) dirty_meta_mgrs.Add(meta_mgr_key, meta_mgr);	// only add if new
			String queue_msg = wtr.Prog_many(GRP_KEY, "download.bgn", "downloading ~{0} of ~{1}: ~{2};", i + ListAdp_.Base1, xfer_len, xfer_itm.Lnki_ttl());
			wiki.App().Wmf_mgr().Download_wkr().Download_xrg().Prog_fmt_hdr_(queue_msg);
			wiki.File_mgr().Repo_mgr().Xfer_by_meta(xfer_itm, this);
			xfer_itm.Set__meta(xfer_itm.Meta_itm(), xfer_itm.Meta_itm().Repo_itm(wiki), wiki.Html_mgr().Img_thumb_width());
			xfer_itm.Calc_by_meta();
			if (!xfer_itm.Html_pass()) continue;	// file not found; don't call Update_img, else invalid src will be passed and caption box will be incorrectly resized; EX:ar.d:جَبَّارَة; DATE:2014-04-13
			if (Bry_.Len_gt_0(xfer_itm.Html_view_url())				// only update images that have been found; otherwise "Undefined" shows up in image box
				&& xfer_itm.Html_elem_tid() != Xof_html_elem.Tid_none) {	// skip updates when downloading orig on File page (there won't be any frame to update)
				Js_img_mgr.Update_img(page, xfer_itm);
			}
		}
		for (int i = 0; i < dirty_meta_mgrs.Count(); i++) {
			meta_mgr = (Xof_meta_mgr)dirty_meta_mgrs.FetchAt(i);
			meta_mgr.Save(true);
		}
		this.Clear();
	}
	private void Exec_v2(byte exec_tid, Gfo_usr_dlg wtr, Xowe_wiki wiki, Xoae_page page) {
		wiki.File_mgr().Init_file_mgr_by_load(wiki);
		wiki.File_mgr().Fsdb_mgr().Fsdb_search_by_list(exec_tid, Xfer_itms_to_fsdb_itms(wiki, xfer_list, wiki.File_mgr().Patch_upright()), page, page.Tab_data().Tab().Html_itm());
	}
	private ListAdp Xfer_itms_to_fsdb_itms(Xowe_wiki wiki, ListAdp xfer_list, int upright_patch) {
		ListAdp rv = ListAdp_.new_();
		int list_len = xfer_list.Count();
		for (int i = 0; i < list_len; i++) {
			Xof_xfer_itm xfer = (Xof_xfer_itm)xfer_list.FetchAt(i);
			Xof_fsdb_itm fsdb = new Xof_fsdb_itm();
			fsdb.Ctor_by_lnki(xfer.Lnki_ttl(), xfer.Lnki_type(), xfer.Lnki_w(), xfer.Lnki_h(), upright_patch, xfer.Lnki_upright(), xfer.Lnki_time(), xfer.Lnki_page());
			fsdb.Lnki_ext_(xfer.Lnki_ext());
			if (xfer.Orig_ext() != null)
				fsdb.Ctor_by_orig(xfer.Orig_repo_id(), xfer.Orig_repo_name(), xfer.Orig_ttl(), xfer.Orig_ext(), xfer.Orig_w(), xfer.Orig_h(), xfer.Orig_redirect());
			else {	// NOTE: orig_ext doesn't exist; try to get again, b/c Xof_file_wkr won't get it; DATE:2015-04-05
				Xof_orig_itm orig = wiki.File_mgr().Orig_mgr().Find_by_ttl_or_null(xfer.Lnki_ttl());
				if (orig != null) {; // no orig;
					gplx.xowa.files.repos.Xof_repo_pair repo_pair = wiki.File_mgr__repo_mgr().Repos_get_by_id(orig.Repo());
					fsdb.Ctor_by_orig(orig.Repo(), repo_pair.Trg().Wiki_key(), orig.Page(), Xof_ext_.new_by_id_(orig.Ext()), orig.W(), orig.H(), orig.Redirect());
				}
			}
			fsdb.Html_uid_(xfer.Html_uid());
			fsdb.Html_elem_tid_(xfer.Html_elem_tid());
			fsdb.Gallery_mgr_h_(xfer.Gallery_mgr_h());
			fsdb.Html_img_wkr_(xfer.Html_img_wkr());
			fsdb.File_exists_(xfer.File_exists());
			rv.Add(fsdb);
		}
		this.Clear();
		return rv;
	}
	private static final String GRP_KEY = "xowa.xfer.queue";
}

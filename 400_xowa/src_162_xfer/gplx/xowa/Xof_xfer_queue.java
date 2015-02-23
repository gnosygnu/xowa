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
import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.bins.*;
import gplx.xowa.files.gui.*;
public class Xof_xfer_queue {
	private ListAdp list = ListAdp_.new_(); private OrderedHash dirty = OrderedHash_.new_(); private Bry_obj_ref dirty_key = Bry_obj_ref.null_();		
	public Int_obj_ref Elem_id() {return elem_id;} private Int_obj_ref elem_id = Int_obj_ref.neg1_();
	public int Count() {return list.Count();}
	public void Clear() {
		dirty.Clear();
		list.Clear();
		elem_id.Val_neg1_();
	}
	public void Add(Xof_xfer_itm xfer_itm) {list.Add(xfer_itm);}
	public void Add_dirty_if_new(Xof_meta_mgr meta_mgr) {
		byte[] meta_mgr_key = meta_mgr.Wiki().Domain_bry();
		if (!dirty.Has(dirty_key.Val_(meta_mgr_key)))
			dirty.AddReplace(meta_mgr_key, meta_mgr);	// only add if new
	}
	public void Exec(byte exec_tid, Gfo_usr_dlg wtr, Xowe_wiki wiki, Xoae_page page) {
		if (wiki.File_mgr().Version() == Xow_file_mgr.Version_2)
			Exec_v2(exec_tid, wtr, wiki, page);
		else
			Exec_v1(exec_tid, wtr, wiki, page);
	}
	private void Exec_v1(byte exec_tid, Gfo_usr_dlg wtr, Xowe_wiki wiki, Xoae_page page) {
		Xof_meta_mgr meta_mgr = null;
		int xfer_len = list.Count();
		for (int i = 0; i < xfer_len; i++) {
			if (wiki.Appe().Usr_dlg().Canceled()) break;
			Xof_xfer_itm xfer_itm = (Xof_xfer_itm)list.FetchAt(i);
			meta_mgr = xfer_itm.Meta_itm().Owner_fil().Owner_mgr();
			Add_dirty_if_new(meta_mgr); // only add if new
			String queue_msg = wtr.Prog_many(GRP_KEY, "download.bgn", "downloading ~{0} of ~{1}: ~{2};", i + ListAdp_.Base1, xfer_len, xfer_itm.Lnki_ttl());
			wiki.App().Wmf_mgr().Download_wkr().Download_xrg().Prog_fmt_hdr_(queue_msg);
			wiki.File_mgr().Repo_mgr().Xfer_by_meta(xfer_itm, this);
			xfer_itm.Set__meta(xfer_itm.Meta_itm(), xfer_itm.Meta_itm().Repo_itm(wiki), wiki.Html_mgr().Img_thumb_width());
			xfer_itm.Calc_by_meta();
			if (!xfer_itm.Html_pass()) continue;	// file not found; don't call Update_img, else invalid src will be passed and caption box will be incorrectly resized; EX:ar.d:جَبَّارَة; DATE:2014-04-13
			if (Bry_.Len_gt_0(xfer_itm.Html_view_src())				// only update images that have been found; otherwise "Undefined" shows up in image box
				&& xfer_itm.Html_elem_tid() != Xof_html_elem.Tid_none) {	// skip updates when downloading orig on File page (there won't be any frame to update)
				Js_img_mgr.Update_img(page, xfer_itm);
			}
		}
		for (int i = 0; i < dirty.Count(); i++) {
			meta_mgr = (Xof_meta_mgr)dirty.FetchAt(i);
			meta_mgr.Save(true);
		}
		this.Clear();
	}
	private void Exec_v2(byte exec_tid, Gfo_usr_dlg wtr, Xowe_wiki wiki, Xoae_page page) {
		wiki.File_mgr().Fsdb_mgr().Init_by_wiki(wiki);
		wiki.File_mgr().Fsdb_mgr().Fsdb_search_by_list(exec_tid, Xfer_itms_to_fsdb_itms(list, wiki.File_mgr().Patch_upright()), page);
	}
	private ListAdp Xfer_itms_to_fsdb_itms(ListAdp list, int upright_patch) {
		ListAdp rv = ListAdp_.new_();
		int list_len = list.Count();
		for (int i = 0; i < list_len; i++) {
			Xof_xfer_itm xfer_itm = (Xof_xfer_itm)list.FetchAt(i);
			Xof_fsdb_itm fsdb_itm = new Xof_fsdb_itm();
			fsdb_itm.Ctor_by_lnki(xfer_itm.Lnki_ttl(), xfer_itm.Lnki_ext(), xfer_itm.Lnki_md5(), xfer_itm.Lnki_type(), xfer_itm.Lnki_w(), xfer_itm.Lnki_h(), upright_patch, xfer_itm.Lnki_upright(), xfer_itm.Lnki_thumbtime(), xfer_itm.Lnki_page());
			fsdb_itm.Html_uid_(xfer_itm.Html_uid());
			fsdb_itm.Html_elem_tid_(xfer_itm.Html_elem_tid());
			fsdb_itm.Gallery_mgr_h_(xfer_itm.Gallery_mgr_h());
			fsdb_itm.Html_img_wkr_(xfer_itm.Html_img_wkr());
			rv.Add(fsdb_itm);
		}
		this.Clear();
		return rv;
	}
	private static final String GRP_KEY = "xowa.xfer.queue";
}

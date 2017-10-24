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
package gplx.xowa.files.xfers; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.primitives.*; import gplx.core.envs.*;
import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.origs.*;
import gplx.xowa.guis.cbks.js.*;
import gplx.xowa.wikis.tdbs.metas.*;
public class Xof_xfer_queue {
	private final    List_adp xfer_list = List_adp_.New(); private final    Ordered_hash dirty_meta_mgrs = Ordered_hash_.New_bry();
	public Int_obj_ref Html_uid() {return html_uid;} private Int_obj_ref html_uid = Int_obj_ref.New_neg1();
	public int Count() {return xfer_list.Count();}
	public void Clear() {
		dirty_meta_mgrs.Clear();
		xfer_list.Clear();
		html_uid.Val_neg1_();
	}
	public void Add(Xof_file_itm xfer_itm) {xfer_list.Add(xfer_itm);}
	public void Exec(Xowe_wiki wiki, Xoae_page page) {
		if (wiki.File_mgr().Version() == Xow_file_mgr.Version_2)
			Exec_v2(wiki, page);
		else
			Exec_v1(wiki, page);
	}
	private void Exec_v1(Xowe_wiki wiki, Xoae_page page) {
		Xof_meta_mgr meta_mgr = null;
		int xfer_len = xfer_list.Count();
		Gfo_usr_dlg usr_dlg = Xoa_app_.Usr_dlg();
		for (int i = 0; i < xfer_len; i++) {
			if (wiki.Appe().Usr_dlg().Canceled()) break;
			Xof_xfer_itm xfer_itm = (Xof_xfer_itm)xfer_list.Get_at(i);
			meta_mgr = xfer_itm.Dbmeta_itm().Owner_fil().Owner_mgr();
			byte[] meta_mgr_key = meta_mgr.Wiki().Domain_bry();
			if (!dirty_meta_mgrs.Has(meta_mgr_key)) dirty_meta_mgrs.Add(meta_mgr_key, meta_mgr);	// only add if new
			String queue_msg = usr_dlg.Prog_many("", "", "downloading ~{0} of ~{1}: ~{2};", i + List_adp_.Base1, xfer_len, xfer_itm.Lnki_ttl());
			wiki.App().Wmf_mgr().Download_wkr().Download_xrg().Prog_fmt_hdr_(queue_msg);
			wiki.File_mgr().Repo_mgr().Xfer_by_meta(xfer_itm, this);
			xfer_itm.Set__meta(xfer_itm.Dbmeta_itm(), xfer_itm.Dbmeta_itm().Repo_itm(wiki), wiki.Html_mgr().Img_thumb_width());
			xfer_itm.Calc_by_meta();
			if (!xfer_itm.File_exists()) continue;	// file not found; don't call Update_img, else invalid src will be passed and caption box will be incorrectly resized; EX:ar.d:جَبَّارَة; DATE:2014-04-13
			if (Bry_.Len_gt_0(xfer_itm.Html_view_url().To_http_file_bry())	// only update images that have been found; otherwise "Undefined" shows up in image box
				&& xfer_itm.Html_elem_tid() != Xof_html_elem.Tid_none) {	// skip updates when downloading orig on File page (there won't be any frame to update)
				Xog_js_wkr js_wkr = Env_.Mode_testing() ? Xog_js_wkr_.Noop : page.Tab_data().Tab().Html_itm(); 
				Js_img_mgr.Update_img(page, js_wkr, xfer_itm);
			}
		}
		for (int i = 0; i < dirty_meta_mgrs.Count(); i++) {
			meta_mgr = (Xof_meta_mgr)dirty_meta_mgrs.Get_at(i);
			meta_mgr.Save(true);
		}
		this.Clear();
	}
	private void Exec_v2(Xowe_wiki wiki, Xoae_page page) {
		wiki.File_mgr().Init_file_mgr_by_load(wiki);
		Xog_js_wkr js_wkr = wiki.App().Mode().Tid_supports_js() ? page.Tab_data().Tab().Html_itm() : Xog_js_wkr_.Noop;
		wiki.File_mgr().Fsdb_mgr().Fsdb_search_by_list(Xfer_itms_to_fsdb_itms(wiki, page, xfer_list, wiki.File_mgr().Patch_upright()), wiki, page, js_wkr);
	}
	private List_adp Xfer_itms_to_fsdb_itms(Xowe_wiki cur_wiki, Xoae_page page, List_adp xfer_list, int upright_patch) {
		List_adp rv = List_adp_.New();
		int list_len = xfer_list.Count();
		for (int i = 0; i < list_len; i++) {
			Xof_file_itm xfer = (Xof_file_itm)xfer_list.Get_at(i);
			if (xfer.Hdump_mode() == Xof_fsdb_itm.Hdump_mode__null) {
				Xof_fsdb_itm fsdb = new Xof_fsdb_itm();
				fsdb.Init_at_lnki(xfer.Lnki_exec_tid(), xfer.Lnki_wiki_abrv(), xfer.Lnki_ttl(), xfer.Lnki_type(), xfer.Lnki_upright(), xfer.Lnki_w(), xfer.Lnki_h(), xfer.Lnki_time(), xfer.Lnki_page(), upright_patch);
				fsdb.Init_at_hdoc(xfer.Html_uid(), xfer.Html_elem_tid());
				fsdb.Html_gallery_mgr_h_(xfer.Html_gallery_mgr_h());
				fsdb.Html_img_wkr_(xfer.Html_img_wkr());
				fsdb.File_exists_(xfer.File_exists());
				if (xfer.Lnki_type() == gplx.xowa.parsers.lnkis.Xop_lnki_type.Tid_orig_known)
					fsdb.Init_at_gallery_bgn(xfer.Html_w(), xfer.Html_h(), xfer.File_w());
				rv.Add(fsdb);
			}
			else
				rv.Add(xfer);
		}
		this.Clear();
		return rv;
	}
}

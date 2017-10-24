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
package gplx.xowa.htmls.ns_files; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.primitives.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.parsers.lnkis.*;
class Xoh_file_page__other_resolutions implements gplx.core.brys.Bfr_arg {
	private Xow_repo_mgr repo_mgr; private Xof_file_itm orig_itm; private Xoh_file_page_wtr file_page;
	private final Xof_img_size img_size = new Xof_img_size(); private final Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	public Xoh_file_page__other_resolutions Init_by_fmtr(Xow_repo_mgr repo_mgr, Xof_file_itm orig_itm, Xoh_file_page_wtr file_page) {this.repo_mgr = repo_mgr; this.orig_itm = orig_itm; this.file_page = file_page; return this;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		Int_2_ref[] ary = file_page.Size_alts();
		Xof_file_itm xfer_itm = new Xof_fsdb_itm();
		int len = ary.length;
		Xof_repo_itm repo = repo_mgr.Get_trg_by_id_or_null(orig_itm.Orig_repo_id(), orig_itm.Lnki_ttl(), Bry_.Empty);
		if (repo == null) return;
		for (int i = 0; i < len; ++i) {
			Int_2_ref itm = ary[i];
			xfer_itm.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, Bry_.Empty, orig_itm.Lnki_ttl(), Xop_lnki_type.Id_none, Xop_lnki_tkn.Upright_null, itm.Val_0(), itm.Val_1(), Xof_lnki_time.Null, Xof_lnki_page.Null, Xof_patch_upright_tid_.Tid_all);
			xfer_itm.Init_at_orig(orig_itm.Orig_repo_id(), orig_itm.Orig_repo_name(), orig_itm.Orig_ttl(), orig_itm.Orig_ext(), orig_itm.Orig_w(), orig_itm.Orig_h(), Bry_.Empty);
			xfer_itm.Init_at_html(Xof_exec_tid.Tid_wiki_page, img_size, repo, url_bldr);
			byte[] itm_separator = i == len - 1 ? file_page.Html_alt_dlm_last() : file_page.Html_alt_dlm_dflt();	// "|" separator between itms unless last
			file_page.Html_alts().Bld_bfr_many(bfr, xfer_itm.Html_w(), xfer_itm.Html_h(), xfer_itm.Html_view_url().To_http_file_bry(), itm_separator, orig_itm.Lnki_ttl());
		}
	}	
}

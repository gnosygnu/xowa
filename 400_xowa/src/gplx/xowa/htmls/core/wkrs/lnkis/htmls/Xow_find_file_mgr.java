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
import gplx.xowa.wikis.domains.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
public class Xow_find_file_mgr {
	private final    Xof_repo_itm repo_itm;
	private final    Xof_url_bldr url_bldr = new Xof_url_bldr();
	private final    byte[] wiki_domain;
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	public Xow_find_file_mgr(Xof_repo_itm repo_itm, String wiki_domain_str) {
		this.repo_itm = repo_itm;
		this.wiki_domain = Bry_.new_u8(wiki_domain_str);
	}
	public void Init_file(String orig_ttl_str, int orig_w, int orig_h) {this.Init_file(true, orig_ttl_str, orig_w, orig_h);}
	public void Init_file(boolean repo_is_remote, String orig_ttl_str, int orig_w, int orig_h) {
		byte orig_repo_id = repo_is_remote ? Xof_repo_tid_.Tid__remote : Xof_repo_tid_.Tid__local;
		byte[] orig_repo_name = repo_is_remote ? Xow_domain_itm_.Bry__commons : wiki_domain;
		byte[] orig_ttl_bry = Bry_.new_u8(orig_ttl_str);
		Xof_ext orig_ext = Xof_ext_.new_by_ttl_(orig_ttl_bry);
		byte[] orig_redirect = null;
		this.Init_file(orig_repo_id, orig_repo_name, orig_ttl_bry, orig_ext, orig_w, orig_h, orig_redirect);
	}
	public void Init_file(byte orig_repo_id, byte[] orig_repo_name, byte[] orig_ttl_bry, Xof_ext orig_ext, int orig_w, int orig_h, byte[] orig_redirect) {
		Xof_xfer_itm orig = new Xof_xfer_itm();
		orig.Init_at_orig(orig_repo_id, orig_repo_name, orig_ttl_bry, orig_ext, orig_w, orig_h, orig_redirect);
		hash.Add_bry_obj(orig_ttl_bry, orig);
	}
	public boolean Find_file(Xof_xfer_itm xfer) {
		// find orig
		Xof_xfer_itm orig = (Xof_xfer_itm)hash.Get_by_bry(xfer.Lnki_ttl());

		// exit if not found
		if (orig == null)
			return false;

		// orig found; calc xfer.html based on orig; note, this seems early to generate html, but need to generate a correct html_url as well as other props
		Xof_img_size img_size = new Xof_img_size();
		img_size.Html_size_calc(Xof_exec_tid.Tid_wiki_page, xfer.Lnki_w(), xfer.Lnki_h(), xfer.Lnki_type(), Xof_patch_upright_tid_.Tid_all, xfer.Lnki_upright(), orig.Orig_ext().Id(), orig.Orig_w(), orig.Orig_h(), Xof_img_size.Thumb_width_img);
		xfer.Init_at_orig(orig.Orig_repo_id(), orig.Orig_repo_name(), orig.Orig_ttl(), orig.Orig_ext(), orig.Orig_w(), orig.Orig_h(), orig.Orig_redirect());
		xfer.Init_at_html(Xof_exec_tid.Tid_wiki_page, img_size, repo_itm, url_bldr);
		return true;
	}
}

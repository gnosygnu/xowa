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
package gplx.xowa.htmls.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.wikis.nss.*;
public class Xoh_subpages_bldr implements gplx.core.brys.Bfr_arg {
	private Bry_bfr tmp_bfr = Bry_bfr_.Reset(255), ttl_bfr = Bry_bfr_.Reset(255);
	private byte[][] segs;
	public byte[] Bld(Xow_ns_mgr ns_mgr, Xoa_ttl ttl) {
		Xow_ns ns = ttl.Ns();
		if (!	(	ns.Subpages_enabled()					// ns has subpages
				&&	ttl.Leaf_bgn() != Bry_find_.Not_found	// ttl has leaf text; EX: Help:A/B
				&&	ns.Id() != ns_mgr.Ns_page_id()			// ns is not [[Page:]]; PAGE:en.s:Notes_on_Osteology_of_Baptanodon._With_a_Description_of_a_New_Species DATE:2014-09-06
				)
			)	return Bry_.Empty;							// doesn't match above; return empty;
		byte[] raw = ttl.Raw();
		this.segs = Bry_split_.Split(raw, Byte_ascii.Slash);
		fmtr_grp.Bld_bfr(tmp_bfr, this);
		return tmp_bfr.To_bry_and_clear();
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int segs_len = segs.length - 1;	// last seg is current page; do not print
		for (int i = 0; i < segs_len; ++i) {
			byte[] dlm = null;
			if (i == 0)
				dlm = Dlm_1st;
			else {
				dlm = Dlm_nth;
				ttl_bfr.Add_byte_slash();
			}
			byte[] seg = segs[i];
			ttl_bfr.Add(seg);
			byte[] seg_ttl = ttl_bfr.To_bry();															
			byte[] seg_ttl_enc = gplx.langs.htmls.encoders.Gfo_url_encoder_.Href.Encode(ttl_bfr.To_bry());
			byte[] href = Bry_.Add(Xoh_href_.Bry__wiki, seg_ttl_enc);		// EX: /wiki/Help:A
			fmtr_itm.Bld_bfr(bfr, dlm, href, seg_ttl, seg);
		}
		ttl_bfr.Clear();
	}
	private static final    byte[] Dlm_1st = Bry_.new_a7("&lt; "), Dlm_nth = Bry_.new_a7("&lrm; | ");
	private static final    Bry_fmtr
	  fmtr_grp = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<span class=\"subpages\">~{itms}"
	, "</span>"
	), "itms")
	, fmtr_itm = Bry_fmtr.new_
	( "\n  ~{dlm}<a href=\"~{href}\" title=\"~{title}\">~{caption}</a>"
	, "dlm", "href", "title", "caption")
	;
}
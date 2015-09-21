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
package gplx.xowa.html.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.xowa.html.hrefs.*;
import gplx.xowa.nss.*;
public class Xoh_subpages_bldr implements Bry_fmtr_arg {
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(255), ttl_bfr = Bry_bfr.reset_(255);
	private byte[][] segs;
	public byte[] Bld(Xow_ns_mgr ns_mgr, Xoa_ttl ttl) {
		Xow_ns ns = ttl.Ns();
		if (!	(	ns.Subpages_enabled()				// ns has subpages
				&&	ttl.Leaf_bgn() != Bry_.NotFound		// ttl has leaf text; EX: Help:A/B
				&&	ns.Id() != ns_mgr.Ns_page_id()		// ns is not [[Page:]]; PAGE:en.s:Notes_on_Osteology_of_Baptanodon._With_a_Description_of_a_New_Species DATE:2014-09-06
				)
			)	return Bry_.Empty;						// doesn't match above; return empty;
		byte[] raw = ttl.Raw();
		this.segs = Bry_split_.Split(raw, Byte_ascii.Slash);
		fmtr_grp.Bld_bfr(tmp_bfr, this);
		return tmp_bfr.Xto_bry_and_clear();
	}
	public void XferAry(Bry_bfr bfr, int idx) {
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
			byte[] seg_ttl = ttl_bfr.Xto_bry();															
			byte[] seg_ttl_enc = Xoa_app_.Utl__encoder_mgr().Href().Encode(ttl_bfr.Xto_bry());
			byte[] href = Bry_.Add(Xoh_href_.Bry__wiki, seg_ttl_enc);		// EX: /wiki/Help:A
			fmtr_itm.Bld_bfr(bfr, dlm, href, seg_ttl, seg);
		}
		ttl_bfr.Clear();
	}
	private static final byte[] Dlm_1st = Bry_.new_a7("&lt; "), Dlm_nth = Bry_.new_a7("&lrm; | ");
	private static final Bry_fmtr
	  fmtr_grp = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<span class=\"subpages\">~{itms}"
	, "</span>"
	), "itms")
	, fmtr_itm = Bry_fmtr.new_
	( "\n  ~{dlm}<a href=\"~{href}\" title=\"~{title}\">~{caption}</a>"
	, "dlm", "href", "title", "caption")
	;
}
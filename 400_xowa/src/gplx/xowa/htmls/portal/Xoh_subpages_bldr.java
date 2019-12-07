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
	private final    Bry_bfr html_bfr = Bry_bfr_.Reset(255), path_bfr = Bry_bfr_.Reset(255), subpage_caption_bfr = Bry_bfr_.Reset(255);
	private Xowe_wiki wiki;
	private byte[][] segs;
	public byte[] Bld(Xowe_wiki wiki, Xoa_ttl ttl) {
		// check if subpages is valid
		Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
		Xow_ns ns = ttl.Ns();
		if (!	(	ns.Subpages_enabled()					// ns has subpages
				&&	ns.Id() != ns_mgr.Ns_page_id()			// ns is not [[Page:]]; PAGE:en.s:Notes_on_Osteology_of_Baptanodon._With_a_Description_of_a_New_Species DATE:2014-09-06
				&&	ttl.Leaf_bgn() != Bry_find_.Not_found	// ttl has leaf text; EX: Help:A/B
				)
			)
			return Bry_.Empty; // doesn't match above; return empty;

		// split ttl by slashes; EX: "Help:A/B/C" -> "Help:A", "B", "C"
		byte[] raw = ttl.Raw();
		this.segs = Bry_split_.Split(raw, Byte_ascii.Slash);

		// build html
		this.wiki = wiki;
		fmtr_grp.Bld_bfr(html_bfr, this);
		return html_bfr.To_bry_and_clear();
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int segs_len = segs.length - 1;	// skip last seg which is current page and should not be printed

		byte[] delimiter = Delimiter__1st;
		for (int i = 0; i < segs_len; i++) {
			// if not first, change delimiter and add "/" to path bfr
			if (i != 0) {
				delimiter = Delimiter__nth;
				path_bfr.Add_byte_slash();
			}

			// get seg and add to path
			byte[] seg = segs[i]; // EX: "B"
			path_bfr.Add(seg);    // EX: "Help:A/" + "B" -> "Help:A/B"

			// build subpage_ttl
			byte[] subpage_ttl_bry = path_bfr.To_bry();
			Xoa_ttl subpage_ttl = wiki.Ttl_parse(subpage_ttl_bry);

			// re-define subpage_ttl_bry to properly-case title elements; EX: "help:a/b" -> "Help:A/b"
			subpage_ttl_bry = subpage_ttl.Full_txt();

			// add subpage_caption_bfr; needed for cases like "Help:A/B/C/D/E" where "B/C/D" does not exist which should show as "Help:A" | "B/C/D"  not "D" DATE:2019-12-07
			if (subpage_caption_bfr.Len_gt_0()) subpage_caption_bfr.Add_byte_slash();
			subpage_caption_bfr.Add(subpage_ttl.Leaf_txt());

			// page is missing; move on to next seg; ISSUE#:626; DATE:2019-12-01
			if (!wiki.Parser_mgr().Ifexist_mgr().Exists(wiki, subpage_ttl_bry)) continue;

			// get subpage_caption
			byte[] subpage_caption
				= i == 0
				? subpage_ttl.Full_txt()        // 1st seg is Full_txt; EX: "Help:A"
				: subpage_caption_bfr.To_bry(); // nth seg is caption_bfr ("b", not "Help:A/b")
			subpage_caption_bfr.Clear(); // always clear subpage_caption_bfr; note that 1st seg will add to bfr, but never use it

			// add to subpage trail
			// NOTE: convert underscore to space; ISSUE#:308 PAGE:en.v:Computer-aided_design/Software DATE:2018-12-23
			fmtr_itm.Bld_bfr(bfr, delimiter
				, Bry_.Add(Xoh_href_.Bry__wiki, subpage_ttl.Full_url()) // EX: /wiki/Help:A
				, Xoa_ttl.Replace_unders(subpage_ttl_bry)
				, Xoa_ttl.Replace_unders(subpage_caption));
		}
		path_bfr.Clear();
	}
	private static final    byte[] Delimiter__1st = Bry_.new_a7("&lt; "), Delimiter__nth = Bry_.new_a7("&lrm; | ");
	private static final    Bry_fmtr
	  fmtr_grp = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<span class=\"subpages\">~{itms}"
	, "</span>"
	), "itms")
	, fmtr_itm = Bry_fmtr.new_
	( "~{delimiter}<a href=\"~{href}\" title=\"~{title}\">~{caption}</a>"
	, "delimiter", "href", "title", "caption")
	;
}
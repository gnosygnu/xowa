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
package gplx.xowa.parsers.hdrs.sections;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.xowa.*;
import gplx.xowa.mediawiki.includes.parsers.headingsOld.*;
import gplx.xowa.addons.htmls.tocs.*; import gplx.xowa.htmls.core.htmls.tidy.*;
class Xop_section_list implements Xomw_heading_cbk {
	private final Xomw_heading_wkr hdr_wkr = new Xomw_heading_wkr();
	private final Ordered_hash hash = Ordered_hash_.New_bry();
	private final Xoh_toc_mgr toc_mgr = new Xoh_toc_mgr();
	private byte[] src;
	private Xowe_wiki wiki;

	public Xop_section_list Parse(Xowe_wiki wiki, Xow_tidy_mgr_interface tidy_mgr, Xoa_url page_url, byte[] src) {
		// clear
		this.wiki = wiki;
		this.src = src;
		hash.Clear();
		toc_mgr.Clear();
		toc_mgr.Init(tidy_mgr, page_url, BryUtl.Empty);

		// parse
		hdr_wkr.Parse(src, 0, src.length, this);
		return this;
	}
	public byte[] Slice_bry_or_null(byte[] key) {
		int[] bounds = Get_section_bounds(key);
		if (bounds == null) return null;	// handle missing key

		// return slice
		return BryLni.Mid(src, bounds[0], bounds[1]);
	}
	public byte[] Merge_bry_or_null(byte[] key, byte[] edit) {
		int[] bounds = Get_section_bounds(key);
		if (bounds == null) return null;	// handle missing key

		// merge edit into orig
		BryWtr bfr = BryWtr.New();
		bfr.AddMid(src, 0, bounds[0]);
		bfr.Add(edit);
		bfr.AddMid(src, bounds[1], src.length);

		return bfr.ToBryAndClear();
	}
	private int[] Get_section_bounds(byte[] key) {
		int src_bgn = -1, src_end = -1;
		int hash_len = hash.Len();

		// if key == "", get lead section
		if (BryLni.Eq(key, BryUtl.Empty)) {
			src_bgn = 0;
			src_end = src.length;
			if (hash_len > 0) {
				Xop_section_itm itm = (Xop_section_itm)hash.GetAt(0);
				src_end = itm.Src_bgn();	// -1 to skip "\n" in "\n=="
			}
		}
		// else, get section matching key
		else {
			Xop_section_itm itm = (Xop_section_itm)hash.GetByOrNull(key);
			if (itm == null) return null;

			// get bgn
			src_bgn = itm.Src_bgn();
			if (src[src_bgn] == AsciiByte.Nl) src_bgn++;	// skip "\n" in "\n=="

			// get end
			for (int i = itm.Idx() + 1; i < hash_len; i++) {
				Xop_section_itm nxt = (Xop_section_itm)hash.GetAt(i);
				if (nxt.Num() > itm.Num()) continue;	// skip headers that are at lower level; EX: == H2 == should skip === H3 ===
				src_end = nxt.Src_bgn();
				break;
			}
			if (src_end == -1) src_end = src.length;	// no headers found; default to EOS
			src_end = BryFind.FindBwdSkipWs(src, src_end, src_bgn);	// always remove ws at end
		}

		return new int[] {src_bgn, src_end};
	}
	public void On_hdr_seen(Xomw_heading_wkr wkr) {
		// get key by taking everything between ==; EX: "== abc ==" -> " abc "
		byte[] src = wkr.Src();
		int hdr_txt_bgn = wkr.Hdr_lhs_end();
		int hdr_txt_end = wkr.Hdr_rhs_bgn();

		// trim ws
		hdr_txt_bgn = BryFind.FindFwdWhileWs(src, hdr_txt_bgn, hdr_txt_end);
		hdr_txt_end = BryFind.FindBwdSkipWs(src, hdr_txt_end, hdr_txt_bgn);
		byte[] key = BryLni.Mid(wkr.Src(), hdr_txt_bgn, hdr_txt_end);

		// handle nested templates; EX: "== {{A}} ==" note that calling Parse_text_to_html is expensive (called per header) but should be as long as its not nested
		key = wiki.Parser_mgr().Main().Parse_text_to_html(wiki.Parser_mgr().Ctx(), key);

		// handle math; EX: "== <math>\delta</math> =="
		key = wiki.Parser_mgr().Uniq_mgr().Parse(key);

		// convert key to toc_text to handle (a) XML ("<i>a</i>" -> "a"); (b) dupes ("text" -> "text_2")
		int num = wkr.Hdr_num();
		Xoh_toc_itm toc_itm = toc_mgr.Add(num, key);
		key = toc_itm.Anch();

		Xop_section_itm itm = new Xop_section_itm(hash.Len(), num, key, wkr.Hdr_bgn(), wkr.Hdr_end());
		hash.Add(key, itm);
	}
	public void On_src_done(Xomw_heading_wkr wkr) {}
}

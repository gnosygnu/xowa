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
package gplx.xowa.bldrs.setups.maints; import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
public class Wmf_dump_list_parser {
	public Wmf_dump_itm[] Parse(byte[] src) {
		Ordered_hash itms = Ordered_hash_.New_bry();
		int pos = 0;
		while (true) {
			int a_pos = BryFind.FindFwd(src, Find_anchor, pos); if (a_pos == BryFind.NotFound) break;	// no more anchors found
			pos = a_pos + Find_anchor.length;
			try {
				Wmf_dump_itm itm = new Wmf_dump_itm();
				if (!Parse_href(itm, src, a_pos)) continue;			// anchor not parseable; not a link to a wmf dump
				if (itms.Has(itm.Wiki_abrv())) continue;			// ignore dupes
				itms.Add(itm.Wiki_abrv(), itm);
				itm.Status_time_(Parse_status_time(src, a_pos));	
				itm.Status_msg_(Parse_status_msg(src, a_pos));
			} catch (Exception e) {}
		}
		return (Wmf_dump_itm[])itms.ToAry(Wmf_dump_itm.class);
	}
	private boolean Parse_href(Wmf_dump_itm itm, byte[] src, int a_pos) {	// EX: http://dumps.wikimedia.org/enwiki/20130807
		int href_pos = BryFind.FindFwd(src, Find_href, a_pos); if (href_pos == BryFind.NotFound) return false;	// no <li>; something bad happened
		int href_bgn_pos = BryFind.FindFwd(src, AsciiByte.Quote, href_pos + Find_href.length);
		int href_end_pos = BryFind.FindFwd(src, AsciiByte.Quote, href_bgn_pos + 1); if (href_end_pos == BryFind.NotFound) return false;
		byte[] href_bry = BryLni.Mid(src, href_bgn_pos + 1, href_end_pos);
		int date_end = href_bry.length;
		int date_bgn = BryFind.FindBwd(href_bry, AsciiByte.Slash); if (date_bgn == BryFind.NotFound) return false;
		byte[] date_bry = BryLni.Mid(href_bry, date_bgn + 1, date_end); if (date_bry.length == 0) return false;	// anchors like "/other_static_dumps" should be skipped
		if (BryUtl.Has(date_bry, BryUtl.NewU8("legal.html"))) return false;
		if (BryUtl.Has(date_bry, BryUtl.NewU8("Privacy_policy"))) return false;
		GfoDate date = GfoDateUtl.ParseFmt(StringUtl.NewA7(date_bry), "yyyyMMdd");
		itm.Dump_date_(date);
		int abrv_end = date_bgn;
		int abrv_bgn = BryFind.FindBwd(href_bry, AsciiByte.Slash, abrv_end); if (abrv_bgn == BryFind.NotFound) abrv_bgn = -1;	// "enwiki/20130708"
		byte[] abrv_bry = BryLni.Mid(href_bry, abrv_bgn + 1, abrv_end);
		itm.Wiki_abrv_(BryUtl.Replace(abrv_bry, AsciiByte.Underline, AsciiByte.Dash));
		return true;
	}
	private GfoDate Parse_status_time(byte[] src, int a_pos) {
		int li_pos = BryFind.FindBwd(src, Find_li, a_pos); if (li_pos == BryFind.NotFound) return null;
		int bgn = BryFind.FindFwd(src, AsciiByte.Gt, li_pos + Find_li.length); if (bgn == BryFind.NotFound) return null;
		byte[] rv_bry = BryLni.Mid(src, bgn + 1, a_pos);
		return GfoDateUtl.ParseFmt(StringUtl.Trim(StringUtl.NewA7(rv_bry)), "yyyy-MM-dd HH:mm:ss");
	}
	private byte[] Parse_status_msg(byte[] src, int a_pos) {
		int span_pos = BryFind.FindFwd(src, Find_span_bgn, a_pos); if (span_pos == BryFind.NotFound) return null;
		int bgn = BryFind.FindFwd(src, AsciiByte.Gt, span_pos + Find_span_bgn.length); if (bgn == BryFind.NotFound) return null;
		int end = BryFind.FindFwd(src, Find_span_end, bgn); if (end == BryFind.NotFound) return null;
		return BryLni.Mid(src, bgn + 1, end);
	}
	private static byte[]
		Find_anchor = BryUtl.NewA7("<a")
	,	Find_href = BryUtl.NewA7(" href=")
	, 	Find_li = BryUtl.NewA7("<li")
	, 	Find_span_bgn = BryUtl.NewA7("<span")
	, 	Find_span_end = BryUtl.NewA7("</span>")
	;
}

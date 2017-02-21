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
package gplx.xowa.bldrs.setups.maints; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.setups.*;
public class Wmf_dump_list_parser {
	public Wmf_dump_itm[] Parse(byte[] src) {
		Ordered_hash itms = Ordered_hash_.New_bry();
		int pos = 0;
		while (true) {
			int a_pos = Bry_find_.Find_fwd(src, Find_anchor, pos); if (a_pos == Bry_find_.Not_found) break;	// no more anchors found
			pos = a_pos + Find_anchor.length;
			try {
				Wmf_dump_itm itm = new Wmf_dump_itm();
				if (!Parse_href(itm, src, a_pos)) continue;			// anchor not parseable; not a link to a wmf dump
				if (itms.Has(itm.Wiki_abrv())) continue;			// ignore dupes
				itms.Add(itm.Wiki_abrv(), itm);
				itm.Status_time_(Parse_status_time(src, a_pos));	
				itm.Status_msg_(Parse_status_msg(src, a_pos));
			} catch (Exception e) {Err_.Noop(e);}
		}
		return (Wmf_dump_itm[])itms.To_ary(Wmf_dump_itm.class);
	}
	private boolean Parse_href(Wmf_dump_itm itm, byte[] src, int a_pos) {	// EX: http://dumps.wikimedia.org/enwiki/20130807
		int href_pos = Bry_find_.Find_fwd(src, Find_href, a_pos); if (href_pos == Bry_find_.Not_found) return false;	// no <li>; something bad happened
		int href_bgn_pos = Bry_find_.Find_fwd(src, Byte_ascii.Quote, href_pos + Find_href.length);
		int href_end_pos = Bry_find_.Find_fwd(src, Byte_ascii.Quote, href_bgn_pos + 1); if (href_end_pos == Bry_find_.Not_found) return false;
		byte[] href_bry = Bry_.Mid(src, href_bgn_pos + 1, href_end_pos);
		int date_end = href_bry.length;
		int date_bgn = Bry_find_.Find_bwd(href_bry, Byte_ascii.Slash); if (date_bgn == Bry_find_.Not_found) return false;
		byte[] date_bry = Bry_.Mid(href_bry, date_bgn + 1, date_end); if (date_bry.length == 0) return false;	// anchors like "/other_static_dumps" should be skipped
		if (Bry_.Has(date_bry, Bry_.new_u8("legal.html"))) return false;
		if (Bry_.Has(date_bry, Bry_.new_u8("Privacy_policy"))) return false;
		DateAdp date = DateAdp_.parse_fmt(String_.new_a7(date_bry), "yyyyMMdd");
		itm.Dump_date_(date);
		int abrv_end = date_bgn;
		int abrv_bgn = Bry_find_.Find_bwd(href_bry, Byte_ascii.Slash, abrv_end); if (abrv_bgn == Bry_find_.Not_found) abrv_bgn = -1;	// "enwiki/20130708"
		byte[] abrv_bry = Bry_.Mid(href_bry, abrv_bgn + 1, abrv_end);
		itm.Wiki_abrv_(Bry_.Replace(abrv_bry, Byte_ascii.Underline, Byte_ascii.Dash));
		return true;
	}
	private DateAdp Parse_status_time(byte[] src, int a_pos) {
		int li_pos = Bry_find_.Find_bwd(src, Find_li, a_pos); if (li_pos == Bry_find_.Not_found) return null;
		int bgn = Bry_find_.Find_fwd(src, Byte_ascii.Gt, li_pos + Find_li.length); if (bgn == Bry_find_.Not_found) return null;
		byte[] rv_bry = Bry_.Mid(src, bgn + 1, a_pos);
		return DateAdp_.parse_fmt(String_.Trim(String_.new_a7(rv_bry)), "yyyy-MM-dd HH:mm:ss");
	}
	private byte[] Parse_status_msg(byte[] src, int a_pos) {
		int span_pos = Bry_find_.Find_fwd(src, Find_span_bgn, a_pos); if (span_pos == Bry_find_.Not_found) return null;
		int bgn = Bry_find_.Find_fwd(src, Byte_ascii.Gt, span_pos + Find_span_bgn.length); if (bgn == Bry_find_.Not_found) return null;
		int end = Bry_find_.Find_fwd(src, Find_span_end, bgn); if (end == Bry_find_.Not_found) return null;
		return Bry_.Mid(src, bgn + 1, end);
	}
	private static byte[]
		Find_anchor = Bry_.new_a7("<a")
	,	Find_href = Bry_.new_a7(" href=")
	, 	Find_li = Bry_.new_a7("<li")
	, 	Find_span_bgn = Bry_.new_a7("<span")
	, 	Find_span_end = Bry_.new_a7("</span>")
	;
}

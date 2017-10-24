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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
class Xob_css_parser__url {
	private final byte[] site;
	public Xob_css_parser__url(byte[] site) {this.site = site;}
	public Xob_css_tkn__base Parse(byte[] src, int src_len, int tkn_bgn, int tkn_end) {	// " url"
		int bgn_pos = Bry_find_.Find_fwd_while_ws(src, tkn_end, src_len);	// skip any ws after " url("
		if (bgn_pos == src_len) return Xob_css_tkn__warn.new_(tkn_bgn, tkn_end, "mirror.parser.url:EOS; bgn=~{0}", tkn_bgn);			
		byte end_byte = src[bgn_pos];	// note that first non-ws byte should determine end_byte
		byte quote_byte = end_byte;
		switch (end_byte) {
			case Byte_ascii.Quote: case Byte_ascii.Apos:	// quoted; increment position; EX: ' url("a.png")' 
				++bgn_pos;
				break;
			default:										// not quoted; end byte is ")"; EX: ' url(a.png)'
				end_byte = Byte_ascii.Paren_end;
				quote_byte = Byte_ascii.Null;
				break;
		}
		int end_pos = Bry_find_.Find_fwd(src, end_byte, bgn_pos, src_len);
		if (end_pos == Bry_find_.Not_found)	// unclosed "url("; exit since nothing else will be found
			return Xob_css_tkn__warn.new_(tkn_bgn, tkn_end, "mirror.parser.url:dangling; bgn=~{0} excerpt=~{1}", bgn_pos, String_.new_u8__by_len(src, tkn_bgn, tkn_bgn + 128));
		if (end_pos - bgn_pos == 0)		// empty; "url()"; ignore
			return Xob_css_tkn__warn.new_(tkn_bgn, tkn_end, "mirror.parser.url:empty; bgn=~{0} excerpt=~{1}", bgn_pos, String_.new_u8__by_len(src, tkn_bgn, tkn_bgn + 128));
		byte[] url_orig = Bry_.Mid(src, bgn_pos, end_pos); int url_orig_len = url_orig.length;
		++end_pos;	// increment end_pos so rv will be after it;
		if (	end_byte != Byte_ascii.Paren_end) {	// end_byte is apos / quote
			if	(	end_pos < src_len 
				&&	src[end_pos] == Byte_ascii.Paren_end)
				++end_pos;
			else
				return Xob_css_tkn__warn.new_(tkn_bgn, end_pos, "mirror.parser.url:base64 dangling; bgn=~{0} excerpt=~{1}", bgn_pos, String_.new_u8(url_orig));
		}
		if (Bry_.Has_at_bgn(url_orig, Bry_data_image))	// base64
			return Xob_css_tkn__base64.new_(tkn_bgn, end_pos);
		byte[] src_url = Xob_url_fixer.Fix(site, url_orig, url_orig_len);
		if (src_url == null)	// could not convert
			return Xob_css_tkn__warn.new_(tkn_bgn, end_pos, "mirror.parser.url:invalid url; bgn=~{0} excerpt=~{1}", tkn_bgn, String_.new_u8(url_orig));
		return Xob_css_tkn__url.new_(tkn_bgn, end_pos, src_url, quote_byte);
	}
	private static final byte[] Bry_data_image = Bry_.new_a7("data:image/");
}

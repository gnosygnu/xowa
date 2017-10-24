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
package gplx.xowa.apps.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public class Xoav_url_parser {
	private static final byte[] Bry_site = Bry_.new_a7("/site/"), Bry_wiki = Bry_.new_a7("/wiki/"), Bry_http = Bry_.new_a7("http:");
	public void Parse_xo_href(Xoav_url rv, byte[] src, byte[] cur_wiki_bry) {
		rv.Clear();
		int pos = Bry_.Has_at_bgn(src, Bry_http) ? Bry_http.length  : 0;	// DRD: DRD:2.2 adds "http:" to all links
		int src_len = src.length;
		if (Bry_.Has_at_bgn(src, Bry_site, pos, src_len))
			pos = Parse_wiki(rv, src, src_len, pos);
		else
			rv.Wiki_bry_(cur_wiki_bry);
		if (Bry_.Has_at_bgn(src, Bry_wiki, pos, src_len)) pos = Parse_page(rv, src, src_len, pos);
	}
	private int Parse_wiki(Xoav_url rv, byte[] src, int src_len, int pos) {
		int wiki_bgn = pos + Bry_site.length;
		int wiki_end = Bry_find_.Find_fwd(src, Byte_ascii.Slash, wiki_bgn, src_len); if (wiki_end == Bry_find_.Not_found) throw Err_.new_wo_type("could not find wiki_end", "src", String_.new_u8(src));
		rv.Wiki_bry_(Bry_.Mid(src, wiki_bgn, wiki_end));
		return wiki_end;
	}
	private int Parse_page(Xoav_url rv, byte[] src, int src_len, int pos) {
		int page_bgn = pos + Bry_wiki.length;
		int page_end = src_len;
		rv.Page_bry_(Bry_.Mid(src, page_bgn, page_end));
		return page_end;
	}
}

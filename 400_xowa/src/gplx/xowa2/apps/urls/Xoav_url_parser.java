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
package gplx.xowa2.apps.urls; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.apps.*;
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
		int wiki_end = Bry_finder.Find_fwd(src, Byte_ascii.Slash, wiki_bgn, src_len); if (wiki_end == Bry_.NotFound) throw Err_.new_wo_type("could not find wiki_end", "src", String_.new_u8(src));
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

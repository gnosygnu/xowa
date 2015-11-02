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
package gplx.xowa.htmls.core.wkrs.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*; import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.wikis.ttls.*; import gplx.xowa.htmls.core.parsers.*;
public class Xoh_lnki_parse {
	public int Parse(Xoh_wkr wkr, Html_tag_rdr rdr, byte[] src, Html_tag lnki, Xow_ttl_parser ttl_parser) {// <a href="/wiki/A" title="A">b</a>
		int tag_bgn = lnki.Src_bgn(), tag_end = lnki.Src_end();
		Html_atr href_atr = lnki.Atrs__get_by(Html_atr_.Bry__href);
		int href_pos = href_atr.Val_bgn();
		int site_bgn = href_pos + Xoh_href_.Len__site, site_end = -1;
		if (href_hash.Get_as_int_or(src, href_pos, site_bgn, -1) == Href_tid__site)	// site; EX:"/site/en.wiktionary.org/"
			site_end = href_pos = Bry_find_.Find_fwd(src, Byte_ascii.Slash, site_bgn);
		else
			site_bgn = -1;
		int page_bgn = href_pos + Xoh_href_.Len__wiki, page_end = href_atr.Val_end();
		if (href_hash.Get_as_int_or(src, href_pos, page_bgn, -1) != Href_tid__wiki)		// site; EX:"/site/en.wiktionary.org/"
			throw Err_.new_("Xoh_parser", "invalid url", "url", String_.new_u8(src, href_pos, page_bgn));
//			Html_atr title_atr = lnki.Atrs__get_by(Html_atr_.Bry__title);
//			int title_bgn = title_atr.Val_bgn(), title_end = title_atr.Val_end();
		Html_tag lnki_tail = rdr.Tag__move_fwd_tail(Html_tag_.Id__a);
		int capt_bgn = tag_end; int capt_end = lnki_tail.Src_bgn();
		tag_end = lnki_tail.Src_end();
		byte lnki_type = ttl_matcher.Match(ttl_parser, src, page_bgn, page_end, src, capt_bgn, capt_end);
		byte[] page_bry = null, capt_bry = null, trail_bry = null;
		switch (lnki_type) {
			case Xoh_ttl_matcher.Tid__diff:
				page_bry = Bry_.Mid(src, page_bgn, page_end);
				capt_bry = Bry_.Mid(src, capt_bgn, capt_end);
				break;
			case Xoh_ttl_matcher.Tid__same:					
				capt_bry = Bry_.Mid(src, capt_bgn, capt_end);
				break;
			case Xoh_ttl_matcher.Tid__trail:
				capt_bry = Bry_.Mid(src, capt_bgn, ttl_matcher.Trail_bgn());
				trail_bry = Bry_.Mid(src, ttl_matcher.Trail_bgn(), capt_end);
				break;
		}
		wkr.On_lnki(tag_bgn, tag_end, lnki_type, site_bgn, site_end, page_bry, capt_bry, trail_bry);
		return tag_end;
	}
	private static final int Href_tid__wiki = 1, Href_tid__site = 2;
	private static final Hash_adp_bry href_hash = Hash_adp_bry.ci_a7().Add_bry_int(Xoh_href_.Bry__wiki, Href_tid__wiki).Add_bry_int(Xoh_href_.Bry__site, Href_tid__site);
	private final Xoh_ttl_matcher ttl_matcher = new Xoh_ttl_matcher();
}

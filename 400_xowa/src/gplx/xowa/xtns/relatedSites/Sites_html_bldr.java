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
package gplx.xowa.xtns.relatedSites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.pages.skins.*;
class Sites_xtn_skin_itm implements Xopg_xtn_skin_itm {
	private List_adp itms = List_adp_.new_();
	private Sites_html_bldr html_bldr;
	public Sites_xtn_skin_itm(Sites_html_bldr html_bldr) {this.html_bldr = html_bldr;}
	public byte Tid() {return Xopg_xtn_skin_itm_tid.Tid_sidebar;}
	public byte[] Key() {return KEY;} public static final byte[] KEY = Bry_.new_u8("RelatedSites");
	public void Add(Sites_regy_itm itm) {itms.Add(itm);}
	public void Write(Bry_bfr bfr, Xoae_page page) {
		html_bldr.Bld_all(bfr, page, itms);
	}
}
public class Sites_html_bldr implements Bry_fmtr_arg {
	private Sites_xtn_mgr xtn_mgr;
	private Bry_bfr tmp_ttl = Bry_bfr.reset_(255);
	private List_adp list; private int list_len;
	private Hash_adp_bry hash = Hash_adp_bry.cs_();
	public Sites_html_bldr(Sites_xtn_mgr xtn_mgr) {this.xtn_mgr = xtn_mgr;}
	private Bry_fmtr url_fmtr = Bry_fmtr.keys_("title");
	public void Bld_all(Bry_bfr bfr, Xoae_page page, List_adp list) {
		this.list = list; this.list_len = list.Count();
		hash.Clear();
		fmtr_grp.Bld_bfr_many(bfr, xtn_mgr.Msg_related_sites(), this);
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		Xowe_wiki wiki = xtn_mgr.Wiki();
		Xoh_href_parser href_parser = wiki.Appe().Href_parser();
		for (int i = 0; i < list_len; ++i) {
			Sites_regy_itm itm = (Sites_regy_itm)list.Get_at(i);
			byte[] xwiki_itm_name = itm.Xwiki_itm().Domain_name();
			if (hash.Has(xwiki_itm_name)) continue;
			hash.Add(xwiki_itm_name, xwiki_itm_name);
			byte[] href = Xto_href(tmp_ttl, url_fmtr, href_parser, wiki, itm.Xwiki_itm(), itm.Ttl().Page_db());
			fmtr_itm.Bld_bfr(bfr, itm.Cls(), href, xwiki_itm_name);
		}
	}
	private static byte[] Xto_href(Bry_bfr tmp_bfr, Bry_fmtr url_fmtr, Xoh_href_parser href_parser, Xowe_wiki wiki, Xow_xwiki_itm xwiki_itm, byte[] ttl_page_db) {
		href_parser.Encoder().Encode(tmp_bfr, ttl_page_db);
		byte[] rv = url_fmtr.Fmt_(xwiki_itm.Url_fmt()).Bld_bry_many(tmp_bfr, tmp_bfr.Xto_bry_and_clear());			
		if (xwiki_itm.Domain_tid() != Xow_domain_type_.Tid_other)
			rv = Bry_.Add(Xoh_href_parser.Href_site_bry, rv);
		return rv;
	}
	private static final Bry_fmtr
	  fmtr_grp = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<div id='p-relatedsites' class='portal'>"
	, "  <h3>~{related_sites_hdr}</h3>"
	, "  <div class='body'>"
	, "    <ul>~{itms}"
	, "    </ul>"
	, "  </div>"
	, "</div>"
	), "related_sites_hdr", "itms")
	, fmtr_itm = Bry_fmtr.new_
	( "\n      <li class='interwiki-~{key}'><a class='xowa-hover-off' href='~{href}'>~{name}</a></li>"
	, "key", "href", "name")
	;
}
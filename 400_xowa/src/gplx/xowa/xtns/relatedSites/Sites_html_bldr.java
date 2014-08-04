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
public class Sites_html_bldr implements Bry_fmtr_arg {
	private Sites_xtn_mgr xtn_mgr;
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(255), tmp_ttl = Bry_bfr.reset_(255);
	private ListAdp list; private int list_len;
	public Sites_html_bldr(Sites_xtn_mgr xtn_mgr) {this.xtn_mgr = xtn_mgr;}
	private Bry_fmtr url_fmtr = Bry_fmtr.keys_("title");
	public byte[] Bld_all(ListAdp list) {
		list_len = list.Count(); if (list_len == 0) return Bry_.Empty;
		this.list = list;
		fmtr_grp.Bld_bfr_many(tmp_bfr, xtn_mgr.Msg_related_sites(), this);
		return tmp_bfr.XtoAryAndClear();
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		for (int i = 0; i < list_len; ++i) {
			Sites_regy_itm itm = (Sites_regy_itm)list.FetchAt(i);
			byte[] href = url_fmtr.Fmt_(itm.Xwiki_itm().Fmt()).Bld_bry_many(tmp_ttl, itm.Ttl().Page_db());
			fmtr_itm.Bld_bfr(bfr, itm.Cls(), href, itm.Xwiki_itm().Name());
		}
	}
	private static final Bry_fmtr
	  fmtr_grp = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<div id=\"p-relatedsites\" class=\"portal\">"
	, "  <h3>~{related_sites_hdr}</h3>"
	, "  <div class=\"body\">"
	, "    <ul>~{itms}"
	, "    </ul>"
	, "  </div>"
	, "</div>"
	), "related_sites_hdr", "itms")
	, fmtr_itm = Bry_fmtr.new_
	( "\n      <li class=\"interwiki-~{key}\"><a href=\"~{href}\">~{name}</a></li>"
	, "key", "href", "name")
	;
}
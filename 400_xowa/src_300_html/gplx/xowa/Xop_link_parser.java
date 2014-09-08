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
package gplx.xowa; import gplx.*;
import gplx.xowa.wikis.xwikis.*; import gplx.xowa.html.*; import gplx.xowa.net.*;
public class Xop_link_parser {
	public byte[] Html_xowa_ttl()	{return html_xowa_ttl;} private byte[] html_xowa_ttl;
	public byte[] Html_anchor_cls()	{return html_anchor_cls;} private byte[] html_anchor_cls;
	public byte[] Html_anchor_rel()	{return html_anchor_rel;} private byte[] html_anchor_rel;
	public byte[] Parse(Bry_bfr tmp_bfr, Xoa_url tmp_url, Xow_wiki wiki, byte[] raw, byte[] or) {
		html_xowa_ttl = null; html_anchor_cls = Xow_html_mgr.Bry_anchor_class_image; html_anchor_rel = Xow_html_mgr.Bry_anchor_rel_blank;	// default member variables for html
		Xoa_app app = wiki.App(); int raw_len = raw.length;
		app.Url_parser().Parse(tmp_url, raw);							
		switch (tmp_url.Protocol_tid()) {
			case Xoo_protocol_itm.Tid_http: case Xoo_protocol_itm.Tid_https:	// "http:" or "https:"; check if to offline wiki and redirect 
				byte[] wiki_bry = tmp_url.Wiki_bry(), page_bry = tmp_url.Page_bry();
				if (Bry_.Eq(wiki_bry, wiki.Domain_bry())				// link is to this wiki; check if alias
					|| app.Xwiki_exists(wiki_bry)) {					// link is to an xwiki
					page_bry = tmp_url.Page_for_lnki();
					Parse__ttl(tmp_bfr, wiki, wiki_bry, page_bry);
				}
				else {	// http is to an unknown site
					if (tmp_url.Protocol_is_relative())					// relative protocol; EX:"//www.a.org";
						tmp_bfr.Add(tmp_url.Protocol_bry()).Add(raw);	//	 prepend protocol b/c mozilla cannot launch "//www.a.org", but can launch "http://www.a.org"
					else												// regular url
						tmp_bfr.Add(raw);								//   dump everything									
				}
				raw = tmp_bfr.XtoAryAndClear();
				html_anchor_cls = Xow_html_mgr.Bry_anchor_class_blank;
				html_anchor_rel = Xow_html_mgr.Bry_anchor_rel_nofollow;
				break;
			case Xoo_protocol_itm.Tid_file:		// "file:///" or "File:A.png"
				int proto_len = tmp_url.Protocol_bry().length;
				if (proto_len + 1 < raw_len && raw[proto_len + 1] == Byte_ascii.Slash) {	// next char is slash, assume xfer_itm refers to protocol; EX: file:///C/A.png
					int slash_pos = Bry_finder.Find_bwd(raw, Byte_ascii.Slash);
					if (slash_pos != Bry_.NotFound)	// set xowa_title to file_name; TODO: call Xoa_url.build; note that this will fail sometimes when (a) xfer_itm is very long (File:ReallyLongName will be shortened to 128 chars) or (b) xfer_itm has invalid windows characters (EX:File:a"b"c.jpg)
						html_xowa_ttl = Bry_.Mid(raw, slash_pos + Int_.Const_dlm_len, raw.length);
				}
				else // next char is not slash; assume xfer_itm refers to ns; EX:File:A.png
					raw = tmp_bfr.Add(Xoh_href_parser.Href_wiki_bry).Add(raw).XtoAryAndClear();
				break;
			default:	// is page only; EX: Abc
				if (Bry_.Len_eq_0(raw))		// NOTE: handle blank link; EX: [[File:Loudspeaker.svg|11px|link=|alt=play]]
					raw = or;
				else {
					if (raw[0] == Byte_ascii.Colon) raw = Bry_.Mid(raw, 1, raw.length);	// ignore initial colon; EX: [[:commons:A.png]]
					if (!Parse__ttl(tmp_bfr, wiki, wiki.Domain_bry(), raw)) {
						tmp_bfr.Clear();
						return null;
					}
					raw = tmp_bfr.XtoAryAndClear();
				}
				break;
		}
		return raw;
	}
	private static boolean Parse__ttl(Bry_bfr tmp_bfr, Xow_wiki wiki, byte[] wiki_bry, byte[] page_bry) {
		Xoa_ttl page_ttl = Xoa_ttl.parse_(wiki, page_bry);
		boolean page_ttl_is_valid = page_ttl != null;
		if (page_ttl_is_valid) {
			Xow_xwiki_itm xwiki_itm = page_ttl.Wik_itm();
			if (xwiki_itm != null) {				// is alias; set wiki, page
				wiki_bry = xwiki_itm.Domain();
				page_bry = Bry_.Mid(page_bry, xwiki_itm.Key_bry().length + 1, page_bry.length);	// +1 for ":"
			}
			else									// is regular page; use ttl.Full_db() to normalize; EX: &nbsp; -> _
				page_bry = page_ttl.Full_db();
		}
		if (Bry_.Eq(wiki_bry, wiki.Domain_bry()))	// NOTE: check against wiki.Key_bry() again; EX: in en_wiki, and http://commons.wikimedia.org/wiki/w:A
			tmp_bfr.Add(Xoh_href_parser.Href_wiki_bry).Add(page_bry);
		else
			tmp_bfr.Add(Xoh_href_parser.Href_site_bry).Add(wiki_bry).Add(Xoh_href_parser.Href_wiki_bry).Add(page_bry);
		return page_ttl_is_valid;
	}
}

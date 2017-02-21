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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.net.*; import gplx.xowa.wikis.xwikis.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*; import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.wikis.domains.*;
public class Xop_link_parser {
	public byte[] Html_xowa_ttl()	{return html_xowa_ttl;} private byte[] html_xowa_ttl;
	public byte Html_anchor_cls()	{return html_anchor_cls;} private byte html_anchor_cls;
	public byte Html_anchor_rel()	{return html_anchor_rel;} private byte html_anchor_rel;
	public byte[] Parse(Bry_bfr tmp_bfr, Xoa_url tmp_url, Xowe_wiki wiki, byte[] raw, byte[] or) {
		html_xowa_ttl = null; html_anchor_cls = Xoh_lnki_consts.Tid_a_cls_image; html_anchor_rel = Xoh_lnki_consts.Tid_a_rel_none;	// default member variables for html
		Xoae_app app = wiki.Appe(); int raw_len = raw.length;
		wiki.Utl__url_parser().Parse(tmp_url, raw);							
		switch (tmp_url.Protocol_tid()) {
			case Gfo_protocol_itm.Tid_http: case Gfo_protocol_itm.Tid_https:	// "http:" or "https:"; check if to offline wiki and redirect 
				byte[] wiki_bry = tmp_url.Wiki_bry(), page_bry = tmp_url.Page_bry();
				if (	!tmp_url.Wiki_is_missing()							// https://www.a.org and others will be marked "missing" by Xow_url_parser
						&&(	Bry_.Eq(wiki_bry, wiki.Domain_bry())			// link is to this wiki; check if alias
						||	app.Xwiki_mgr__exists(wiki_bry)					// link is to an xwiki
						)
					) {					
					page_bry = tmp_url.Page_for_lnki();
					Parse__ttl(tmp_bfr, wiki, wiki_bry, page_bry);
				}
				else {	// http is to an unknown site
					if (tmp_url.Protocol_is_relative()) {				// relative protocol; EX:"//www.a.org";
						Gfo_protocol_itm protocol_itm = Gfo_protocol_itm.Get_or(wiki.Props().Protocol_tid(), Gfo_protocol_itm.Itm_https);
						tmp_bfr.Add(protocol_itm.Key_w_colon_bry());	// prepend protocol b/c mozilla cannot launch "//www.a.org", but can launch "https://www.a.org"; DATE:2015-07-27
					}
					tmp_bfr.Add(raw);									// dump everything									
				}
				raw = tmp_bfr.To_bry_and_clear();
				html_anchor_cls = Xoh_lnki_consts.Tid_a_cls_none;
				Xow_domain_itm domain_itm = Xow_domain_itm_.parse(wiki_bry);
				html_anchor_rel = domain_itm.Domain_type().Tid() == Xow_domain_tid_.Tid__other ? Xoh_lnki_consts.Tid_a_rel_nofollow : Xoh_lnki_consts.Tid_a_rel_none;	// rel=nofollow if not WM wiki; DATE:2015-11-19
				break;
			case Gfo_protocol_itm.Tid_file:		// "file:///" or "File:A.png"
				int proto_len = Gfo_protocol_itm.Bry_file.length;							// "file:"
				if (proto_len + 1 < raw_len && raw[proto_len + 1] == Byte_ascii.Slash) {	// next char is slash, assume xfer_itm refers to protocol; EX: file:///C/A.png
					int slash_pos = Bry_find_.Find_bwd(raw, Byte_ascii.Slash);
					if (slash_pos != Bry_find_.Not_found)	// set xowa_title to file_name; TODO_OLD: call Xoa_url.build; note that this will fail sometimes when (a) xfer_itm is very long (File:ReallyLongName will be shortened to 128 chars) or (b) xfer_itm has invalid windows characters (EX:File:a"b"c.jpg)
						html_xowa_ttl = Bry_.Mid(raw, slash_pos + Int_.Const_dlm_len, raw.length);
				}
				else // next char is not slash; assume xfer_itm refers to ns; EX:File:A.png
					raw = tmp_bfr.Add(Xoh_href_.Bry__wiki).Add(raw).To_bry_and_clear();
				break;
			default:	// is page only; EX: Abc
				if (Bry_.Len_eq_0(raw))		// empty link should not create anchor; EX:[[File:A.png|link=|abc]]; [[File:Loudspeaker.svg|11px|link=|alt=play]]; PAGE:en.w:List_of_counties_in_New_York; DATE:2016-01-10;
					raw = Bry_.Empty;
				else {
					if (raw[0] == Byte_ascii.Colon) raw = Bry_.Mid(raw, 1, raw.length);	// ignore initial colon; EX: [[:commons:A.png]]
					if (!Parse__ttl(tmp_bfr, wiki, wiki.Domain_bry(), raw)) {
						tmp_bfr.Clear();
						return null;
					}
					raw = tmp_bfr.To_bry_and_clear();
				}
				break;
		}
		return raw;
	}
	private static boolean Parse__ttl(Bry_bfr tmp_bfr, Xowe_wiki wiki, byte[] wiki_bry, byte[] page_bry) {
		// handle colon-only aliases; EX:"link:" PAGE:en.w:Wikipedia:Main_Page_alternative_(CSS_Update) DATE:2016-08-18
		Xoa_ttl page_ttl = wiki.Ttl_parse(page_bry);
		Xow_xwiki_itm xwiki_itm = page_ttl == null ? null : page_ttl.Wik_itm();
		if (	xwiki_itm != null					// ttl is xwiki; EX:[[File:A.png|link=wikt:A]]
			&&	page_ttl.Page_db().length == 0) {	// ttl is empty; EX:[[File:A.png|link=wikt:]]
			Xow_wiki xwiki_wiki = wiki.App().Wiki_mgri().Get_by_or_make_init_n(page_ttl.Wik_itm().Domain_bry());
			page_bry = Bry_.Add(page_bry, xwiki_wiki.Props().Main_page());	// append Main_Page to ttl; EX:"wikt:" + "Wikipedia:Main_Page" -> "wikt:Wikipedia:Main_Page"
			page_ttl = wiki.Ttl_parse(page_bry);
			xwiki_itm = page_ttl.Wik_itm();	// should still be the same, but re-set it for good form
		}

		// identify wiki / page
		boolean page_ttl_is_valid = page_ttl != null;
		if (page_ttl_is_valid) {						// xwiki; need to define wiki / page
			if (xwiki_itm != null) {					// is alias; set wiki, page
				wiki_bry = xwiki_itm.Domain_bry();
				page_bry = Bry_.Mid(page_bry, xwiki_itm.Key_bry().length + 1, page_bry.length);	// +1 to skip ":"
			}
			else										// basic; just define page; use ttl.Full_db() to normalize; EX: &nbsp; -> _
				page_bry = page_ttl.Full_db_w_anch();	// add anch; PAGE:en.w:History_of_Nauru; DATE:2015-12-27
		}

		// build either "/wiki/Page" or "/site/domain/wiki/Page"
		if (Bry_.Eq(wiki_bry, wiki.Domain_bry())) {		// NOTE: check against wiki.Key_bry() again; EX: in en_wiki, and http://commons.wikimedia.org/wiki/w:A
			// title-case by ns; needed to handle "link=w:Help:a" which needs to generate "w:Help:A"
			if (page_ttl_is_valid) {					// valid_ttl; parse in same ns to title-case; EX:link=w:Help:a -> Help:A; DATE:2016-01-11
				page_ttl = wiki.Ttl_parse(page_ttl.Full_db_wo_xwiki());
				page_bry = page_ttl.Full_db_w_anch();
			}
			tmp_bfr.Add(Xoh_href_.Bry__wiki).Add(page_bry);
		}
		else
			tmp_bfr.Add(Xoh_href_.Bry__site).Add(wiki_bry).Add(Xoh_href_.Bry__wiki).Add(page_bry);
		return page_ttl_is_valid;
	}
}

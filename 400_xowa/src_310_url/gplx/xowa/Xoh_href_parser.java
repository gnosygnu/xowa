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
import gplx.core.btries.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.net.*; import gplx.xowa.parsers.lnkes.*;
import gplx.xowa.wikis.*;
public class Xoh_href_parser {
	private Gfo_url_parser url_parser; private Gfo_url tmp_url = new Gfo_url(); 
	private Btrie_slim_mgr segs = Btrie_slim_mgr.ci_ascii_(); // NOTE:ci.ascii:XO_const.en; /wiki/, /site/ etc.
	private Bry_bfr bfr_encoder = Bry_bfr.reset_(255), tmp_bfr = Bry_bfr.reset_(255);
	public Xoh_href_parser(Url_encoder encoder, Gfo_url_parser url_parser) {
		this.encoder = encoder;
		this.url_parser = url_parser;
		url_parser.Init_protocol(Protocol_xowa_tid, Xop_lnke_wkr.Str_xowa_protocol);
		segs.Add_stubs(Seg__ary);
	}
	public Url_encoder Encoder() {return encoder;} private Url_encoder encoder; 
	public void Parse(Xoh_href rv, String raw, Xowe_wiki wiki, byte[] cur_page) {Parse(rv, Bry_.new_utf8_(raw), wiki, cur_page);}
	public void Parse(Xoh_href rv, byte[] raw, Xowe_wiki wiki, byte[] cur_page) {
		int bgn = 0, raw_len = raw.length; int file_slash_end = 0;
		url_parser.Parse(tmp_url, raw, 0, raw_len);		// parse as regular tmp_url to get protocol
		rv.Init(raw, tmp_url.Protocol_tid());
		switch (tmp_url.Protocol_tid()) {
			default:									// tmp_url is known protocol ("http:", "ftp:", etc); use it and exit; do not do any substitutions EX: http://en.wikipedia.org
				rv.Tid_(Xoh_href.Tid_http);
				return;
			case Xoo_protocol_itm.Tid_null:		// unknown protocol ("unknown:A")or protocol-less ("A"); could be wiki-title or file-name; fall through to below
				break;
			case Xoo_protocol_itm.Tid_file:		// tmp_url is "file:"; remove it; NOTE: swt/mozilla automatically prepends "file://" to any protocol-less links; see NOTE_1 below
				int file_proto_len = tmp_url.Protocol_bry().length;
				bgn = file_slash_end = Bry_.While_fwd(raw, Byte_ascii.Slash, file_proto_len, raw_len);
				if (file_slash_end - file_proto_len > 0) --bgn;	// if at least 1 slash, include slash; this ensures that all strings which have "file://" stripped will start with a "/"; EX: file:///wiki -> "/wiki"; file://C -> "/C"
				break;
			case Xoo_protocol_itm.Tid_xowa:
				bgn = file_slash_end = Bry_.While_fwd(raw, Byte_ascii.Slash, tmp_url.Protocol_bry().length, raw_len);
				rv.Tid_(Xoh_href.Tid_xowa);
				rv.Wiki_(wiki.Domain_bry());										// wiki is always the current wiki
				byte[] page = Xoa_app_.Utl__encoder_mgr().Gfs().Decode(Bry_.Mid(raw, bgn, raw_len));
				rv.Page_(page);														// page is everything after "/xcmd/"; individual cmds will do further parsing; note that it should be decoded; EX: %20 -> " "; also note that anchor (#) or query params (?) are not parsed; the entire String will be reparsed later
				return;
		}
		if (file_slash_end < raw_len && raw[file_slash_end] == Byte_ascii.Hash) {	// 1st character is anchor; extract and return
			rv.Tid_(Xoh_href.Tid_anchor);
			rv.Wiki_(wiki.Domain_bry());											// wiki is always current
			rv.Page_(cur_page);														// page is always current
			rv.Anchor_(Bry_.Mid(raw, file_slash_end + 1, raw_len));					// +1 to skip #; i.e. Anchor should be "A" not "#A"
			return;
		}
		Object seg_obj = segs.Match_bgn(raw, bgn, raw_len);							// match /wiki/ or /site/ or /xcmd/
		if (seg_obj == null)														// nothing matched; assume file; EX: file:///C/dir/fil.txt -> /C/dir/fil.txt
			rv.Tid_(Xoh_href.Tid_file);
		else {																		// something matched;
			Btrie_itm_stub seg = (Btrie_itm_stub)seg_obj;
			bgn += seg.Val().length;
			switch (seg.Tid()) {
				case Seg_wiki_tid:		Parse_wiki(rv, encoder, wiki, raw, bgn, raw_len); break;
				case Seg_site_tid:		Parse_site(rv, encoder, wiki, raw, bgn, raw_len); break;
				case Seg_xcmd_tid:		Parse_xcmd(rv, encoder, wiki, raw, bgn, raw_len); break;
			}
		}
	}
	public byte[] Build_to_bry(Xow_wiki wiki, Xoa_ttl ttl) {
		synchronized (tmp_bfr) {
			Build_to_bfr(tmp_bfr, wiki.App(), wiki.Domain_bry(), ttl, Bool_.N);
			return tmp_bfr.Xto_bry_and_clear();
		}
	}
	public void Build_to_bfr(Bry_bfr bfr, Xoa_app app, byte[] domain_bry, Xoa_ttl ttl)	{Build_to_bfr(bfr, app, domain_bry, ttl, Bool_.N);}
	public void Build_to_bfr(Bry_bfr bfr, Xoa_app app, byte[] domain_bry, Xoa_ttl ttl, boolean force_site) {
		byte[] page = ttl.Full_txt_raw();
		Xow_xwiki_itm xwiki = ttl.Wik_itm();
		if (xwiki == null)																		// not an xwiki; EX: [[wikt:Word]]
			Build_to_bfr_page(ttl, page, 0);													// write page only; NOTE: changed to remove leaf logic DATE:2014-09-07
		else {																					// xwiki; skip wiki and encode page only;
			byte[] wik_txt = ttl.Wik_txt();
			Build_to_bfr_page(ttl, page, wik_txt.length + 1);
		}
		if (xwiki == null) {																	// not an xwiki
			if (ttl.Anch_bgn() != 1) {															// not an anchor-only;	EX: "#A"
				if (force_site) {																// popup parser always writes as "/site/"
					bfr.Add(Href_site_bry);														// add "/site/";	EX: /site/
					bfr.Add(domain_bry);														// add xwiki;		EX: en_dict	 
					bfr.Add(Href_wiki_bry);														// add "/wiki/";	EX: /wiki/
				}
				else
					bfr.Add(Href_wiki_bry);														// add "/wiki/";	EX: /wiki/Page
			}
			else {}																				// anchor: noop
		}
		else {																					// xwiki
			if (app.Xwiki_mgr__missing(xwiki.Domain_bry())) {									// xwiki is not offline; use http:
				Bry_fmtr url_fmtr = xwiki.Url_fmtr();
				if (url_fmtr == null) {
					bfr.Add(Href_http_bry);														// add "http://";	EX: http://
					bfr.Add(xwiki.Domain_bry());												// add xwiki;		EX: en_dict	 
					bfr.Add(Href_wiki_bry);														// add "/wiki/";	EX: /wiki/
				}
				else {																			// url_fmtr exists; DATE:2015-04-22
					url_fmtr.Bld_bfr(bfr, bfr_encoder.Xto_bry_and_clear());						// use it and pass bfr_encoder for page_name;
					return;
				}
			}
			else {																				// xwiki is avaiable; use /site/
				bfr.Add(Href_site_bry);															// add "/site/";	EX: /site/
				bfr.Add(xwiki.Domain_bry());													// add xwiki;		EX: en_dict	 
				bfr.Add(Href_wiki_bry);															// add "/wiki/";	EX: /wiki/
			}
		}
		bfr.Add_bfr_and_clear(bfr_encoder);
	}
	private void Build_to_bfr_page(Xoa_ttl ttl, byte[] ttl_full, int page_bgn) {
		int anch_bgn = Bry_finder.Find_fwd(ttl_full, Byte_ascii.Hash);	// NOTE: cannot use Anch_bgn b/c Anch_bgn has bug with whitespace
		if (anch_bgn == Bry_.NotFound)	// no anchor; just add page
			encoder.Encode(bfr_encoder, ttl_full, page_bgn, ttl_full.length);
		else {							// anchor exists; check if anchor is preceded by ws; EX: [[A #b]] -> "/wiki/A#b"
			int page_end = Bry_finder.Find_bwd_last_ws(ttl_full, anch_bgn);		// first 1st ws before #; handles multiple ws
			page_end = page_end == Bry_.NotFound ? anch_bgn : page_end;			// if ws not found, use # pos; else use 1st ws pos
			encoder.Encode(bfr_encoder, ttl_full, page_bgn, page_end);			// add page
			encoder.Encode(bfr_encoder, ttl_full, anch_bgn, ttl_full.length);	// add anchor
		}
	}
	public static final String Href_http_str = "http://", Href_file_str = "file:///", Href_wiki_str = "/wiki/", Href_site_str = "/site/", Href_xcmd_str = "/xcmd/";
	public static final byte[] Href_http_bry = Bry_.new_utf8_(Href_http_str), Href_file_bry = Bry_.new_ascii_(Href_file_str), Href_site_bry = Bry_.new_ascii_(Href_site_str), Href_wiki_bry = Bry_.new_ascii_(Href_wiki_str);

	private static final int Href_wiki_len = Href_wiki_bry.length;
	static final byte Seg_null_tid = 0, Seg_wiki_tid = 1, Seg_site_tid = 2, Seg_xcmd_tid = 3;
	private static final byte[] Seg_null_bry = Bry_.new_ascii_("/null/"), Seg_wiki_bry = Bry_.new_ascii_(Href_wiki_str), Seg_site_bry = Bry_.new_ascii_(Href_site_str), Seg_xcmd_bry = Bry_.new_ascii_(Href_xcmd_str);
	private static final byte[][] Seg__ary = new byte[][] {Seg_null_bry, Seg_wiki_bry, Seg_site_bry, Seg_xcmd_bry};
	private static void Parse_wiki(Xoh_href rv, Url_encoder encoder, Xowe_wiki wiki, byte[] raw, int bgn, int len) {
		byte[] ttl_raw = Bry_.Mid(raw, bgn, len);
		Xoa_ttl ttl = wiki.Ttl_parse(ttl_raw);
		if (ttl == null) {
			Xoa_app_.Usr_dlg().Warn_many("xowa.href.parser", "invalid_wiki", "wiki href does not have valid title: ~{0}", String_.new_utf8_(raw, bgn, len));
			return;
		}
		if (ttl.Wik_itm() == null) {										// standard href; EX: "/wiki/A"
			rv.Tid_(Xoh_href.Tid_wiki);
			rv.Wiki_(wiki.Domain_bry());									// wiki is always the current wiki
		}
		else {																// embedded xwiki prefix; EX: "/wiki/fr:A"
			rv.Tid_(Xoh_href.Tid_site);
			rv.Wiki_(ttl.Wik_itm().Domain_bry());							// wiki is the xwiki prefix; EX: "en.wikpedia.org//wiki/fr:A" -> "fr.wikpedia.org/wiki/A"
		}
		byte[] page_bry = encoder.Decode(ttl.Full_txt());					// note that Full is everything except for ns, so it handles "fr:A" ("fr:" being treated as ns, so only "A" will be Full_txt)
		if (Bry_.Len_eq_0(page_bry))										// handle xwiki hrefs like "fr:"; EX: "/wiki/wikipedia:" on en.wikisource.org/Main Page
			page_bry = Xoa_page_.Main_page_bry_empty;
//			if (ttl.Qarg_bgn() != Bry_.NotFound)
//				rv.Qarg_(ttl.Qarg_txt());
		rv.Page_(page_bry);							// add page; note that it should be decoded; EX: %20 -> " "; also note that anchor (#) or query params (?) are not parsed; the entire String will be reparsed later
		if (ttl.Anch_bgn() != Bry_.NotFound) rv.Anchor_(ttl.Anch_txt());
	}
	private static void Parse_site(Xoh_href rv, Url_encoder encoder, Xowe_wiki wiki, byte[] raw, int bgn, int len) {	// /site/; EX: /site/fr.wikipedia.org/wiki/A
		int slash = Bry_finder.Find_fwd(raw, Byte_ascii.Slash, bgn, len); if (slash == Bry_.NotFound) throw Err_mgr._.fmt_("xowa.href.parser", "invalid_site", "site href is missing slash: ~{0}", String_.new_utf8_(raw, bgn, len));
		rv.Tid_(Xoh_href.Tid_site);
		byte[] wiki_bry = Bry_.Mid(raw, bgn, slash);					// wiki is text between "/site/" and next "/"
		Xow_xwiki_itm xwiki = wiki.Appe().User().Wiki().Xwiki_mgr().Get_by_key(wiki_bry);	// NOTE: site may refer to alias in user_wiki; ex: /site/wikisource.org which points to en.wikisource.org; this occurs during lnke substitution; EX: [//wikisource.org Wikisource]
		if (xwiki != null) {
			wiki_bry = xwiki.Domain_bry();
			wiki = wiki.Appe().Wiki_mgr().Get_by_key_or_make(wiki_bry);		// NOTE: xwiki links should use case_match of xwiki (en.wiktionary.org) not cur_wiki (en.wikipedia.org); EX:w:alphabet
		}
		rv.Wiki_(wiki_bry);
		int page_pos = slash + Href_wiki_len;
		byte[] page_bry = page_pos < len 
			? Bry_.Mid(raw, page_pos, len)									// page is text after next "/" + "/wiki/";
			: Bry_.Empty; 
		if (Bry_.Len_eq_0(page_bry))										// handle "/site/fr.wikipedia.org/wiki/"; note that these are generated by [[fr:]]
			page_bry = wiki.Props().Main_page();							// default to Main Page
//			int qarg_pos = Bry_finder.Find_bwd(page_bry, Byte_ascii.Question);
//			byte[] qarg_bry = null;
//			if (qarg_pos != Bry_.NotFound) {
//				qarg_bry = Bry_.Mid(page_bry, qarg_pos + 1, page_bry.length);
//				rv.Qarg_(qarg_bry);
//				page_bry = Bry_.Mid(page_bry, 0, qarg_pos);
//			}
		Parse_ttl_and_resolve_xwiki(rv, wiki, encoder, page_bry, raw, bgn, len);
	}
	private static void Parse_ttl_and_resolve_xwiki(Xoh_href rv, Xowe_wiki wiki, Url_encoder encoder, byte[] page_bry, byte[] raw, int bgn, int len) {
		Xoa_ttl ttl = wiki.Ttl_parse(page_bry);
		if (ttl == null) {
			Xoa_app_.Usr_dlg().Warn_many("xowa.href.parser", "invalid_wiki", "wiki href does not have valid title: ~{0}", String_.new_utf8_(raw, bgn, len));
			rv.Page_(Bry_.Empty);
			return;
		}
		if (ttl.Wik_itm() != null) {				// page_bry has xwiki; EX: "wikt:A"; note that since this is called by "/site/", there may be two xwikis; EX: "w:wikt:"; Note that more than 2 is not being handled 
			wiki = wiki.Appe().Wiki_mgr().Get_by_key_or_make(ttl.Wik_itm().Domain_bry());
			rv.Wiki_(wiki.Domain_bry());
			if (Bry_.Len_eq_0(ttl.Page_txt()))	// page_bry is just alias; EX: "wikt:"
				page_bry = wiki.Props().Main_page();
			else									
				page_bry = ttl.Page_txt();
			ttl = Xoa_ttl.parse_(wiki, page_bry); if (ttl == null) throw Err_mgr._.fmt_("xowa.href.parser", "invalid_wiki", "wiki href does not have valid title: ~{0}", String_.new_utf8_(raw, bgn, len));
		}
		rv.Page_(encoder.Decode(ttl.Full_txt()));	// add page; note that it should be decoded; EX: %20 -> " "; also note that anchor (#) or query params (?) are not parsed; the entire String will be reparsed later
		if (ttl.Anch_bgn() != Bry_.NotFound)	// add anchor if it exists
			rv.Anchor_(ttl.Anch_txt());
	}
	private static void Parse_xcmd(Xoh_href rv, Url_encoder encoder, Xowe_wiki wiki, byte[] raw, int bgn, int len) {	// /xcmd/; note encoder is passed, but don't decode for now; most invk commands have an _ which will get changed to a " ";
		rv.Tid_(Xoh_href.Tid_xcmd);
		rv.Wiki_(wiki.Domain_bry());									// wiki is always the current wiki
		rv.Page_(Bry_.Mid(raw, bgn, len));								// page is everything after "/xcmd/"; individual cmds will do further parsing; note that it should be decoded; EX: %20 -> " "; also note that anchor (#) or query params (?) are not parsed; the entire String will be reparsed later
	}
	private static final byte Protocol_xowa_tid = Xoo_protocol_itm.Tid_xowa;
}
/*
NOTE_1:
. swt/mozilla treats text differently in href="{text}" when content_editable=n; occurs in LocationListener.changing
http://a.org						-> http://a.org								does nothing
A									-> file:///A								adds "file:///"
/wiki/A								-> file:///wiki/A							adds "file://"
Category:A							-> Category:A								noops; Category is assumed to be protocol?
//en.wiktionary.org/wiki/a			-> file:///wiki/a							strips out site name and prepends "file://"; no idea why

. so, to handle the above, the code does the following
http://a.org						-> http://a.org								does nothing; nothing needed
A									-> /wiki/A									always prepend /wiki/
Category:A							-> /wiki/Category:A							always prepend /wiki/
//en.wiktionary.org/wiki/A			-> /site/en.wiktionary.org/wiki/A			always transform relative url to /site/

. the href will still come here as file:///wiki/A or file:///site/en.wiktionary.org/wiki/A.
. however, the file:// can be lopped off and discarded and the rest of the href will fall into one of the following cases
.. /wiki/
.. /site/
.. /xcmd/
.. #
.. anything else -> assume to be really a file:// url; EX: file://C/dir/fil.txt -> C/dir/fil.txt
. the other advantage of this approach is that this proc can be reused outside of swt calls; i.e.: it can parse both "file:///wiki/A" and "/wiki/A"
*/

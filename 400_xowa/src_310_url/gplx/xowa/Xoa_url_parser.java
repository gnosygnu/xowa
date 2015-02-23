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
import gplx.core.primitives.*;
import gplx.xowa.langs.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.net.*; import gplx.xowa.files.*;
public class Xoa_url_parser {
	private Url_encoder encoder = Url_encoder.new_html_href_mw_().Itms_raw_same_many(Byte_ascii.Underline); private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public Gfo_url_parser Url_parser() {return url_parser;} private Gfo_url_parser url_parser = new Gfo_url_parser(); private Gfo_url gfo_url = new Gfo_url();
	public String Build_str(Xoa_url url) {									// transform to "canonical" form that fits url box for both XOWA and Mozilla Firefox
		tmp_bfr.Add(url.Wiki_bry());										// add wiki;		EX: "en.wikipedia.org"
		tmp_bfr.Add(Xoh_href_parser.Href_wiki_bry);							// add "/wiki/"		EX: "/wiki/"
		tmp_bfr.Add(encoder.Decode(url.Page_bry()));						// add page;		EX: "A"
		int args_len = url.Args().length;
		if (args_len > 0) {
			for (int i = 0; i < args_len; i++) {
				byte dlm = i == 0 ? Byte_ascii.Question : Byte_ascii.Amp;
				tmp_bfr.Add_byte(dlm);
				Gfo_url_arg arg = url.Args()[i];
				tmp_bfr.Add(arg.Key_bry()).Add_byte(Byte_ascii.Eq).Add(arg.Val_bry());
			}
		}
		if (url.Anchor_bry() != null)
			tmp_bfr.Add_byte(Byte_ascii.Hash).Add(url.Anchor_bry());		// add anchor;		EX: "#B"
		return tmp_bfr.Xto_str_and_clear();
	}
	public Xoa_url Parse(byte[] src) {
		Xoa_url rv = Xoa_url.blank_();
		Parse(rv, src);
		return rv;
	}
	public boolean Parse(Xoa_url url, byte[] src, int bgn, int end) {return Parse(url, Bry_.Mid(src, bgn, end));}
	public boolean Parse(Xoa_url url, byte[] src) {
		url.Init(src);	// NOTE: need to call init to clear state; Xoa_url is often reused
		src = encoder.Decode(src);	// decode any url-encoded parameters
		int src_len = src.length;
		url_parser.Parse(gfo_url, src, 0, src_len);	// parse protocol
		byte protocol_tid = gfo_url.Protocol_tid();
		url.Protocol_tid_(gfo_url.Protocol_tid());	// NOTE: set protocol early b/c file may exit below; DATE:2014-04-25
		url.Protocol_bry_(gfo_url.Protocol_bry());
		if (protocol_tid == Xoo_protocol_itm.Tid_file && src_len > 5 && src[5] != Byte_ascii.Slash) { // file ns; EX: "File:A.png"; NOTE: for file:A.png, assume "file" refers to wiki_ns (File:), not protocol; hackish as it relies on looking for / after "file:" to distinguish between MW "File:A.png" and file system "file:///C/A.png"
			url.Raw_(src);
			url.Wiki_bry_(gfo_url.Raw());
			return false;
		}
		url.Protocol_is_relative_(gfo_url.Protocol_is_relative());
		url.Err_(gfo_url.Err());
		url.Raw_(src);
		if (gfo_url.Site() != null && Bry_.Eq(gfo_url.Site(), Bry_upload_wikimedia_org)) {	// handle urls like "http://upload.wikimedia.org/wikipedia/commons/a/ab/C.svg"
			byte[][] segs_ary = gfo_url.Segs();
			byte[] domain_bry = segs_ary[0];						// type seems to be the 1st seg	; EX: "/wikipedia/"
			byte[] sub_bry = segs_ary[1];							// lang/type seems to be 2nd seg; EX: "en", "fr"; "commons"
			byte[] lang_bry = sub_bry;
			if (upload_segs_hash.Has(sub_bry)) {					// wikimedia links will have fmt of "/wikipedia/commons"; must change to wikimedia
				domain_bry = Xow_domain_.Seg_bry_wikimedia;
				lang_bry = Xol_lang_itm_.Key__unknown;
			}
			tmp_bfr.Clear().Add(sub_bry).Add_byte(Byte_ascii.Dot)	// add lang/type + .;	EX: "en."; "fr."; "commons."
				.Add(domain_bry).Add(Bry_dot_org);					// add type + .org;		EX: "wikipedia.org"; "wikimedia.org";
			url.Segs_ary_(Xoa_url_parser.Bry_wiki_name_bry);		// NOTE: add "wiki" as seg else will have "/site/commons.wikimedia.org/File:A" which will be invalid (needs to be "/site/commons.wikimedia.org/wiki/File:A")
			url.Lang_bry_(lang_bry);
			url.Wiki_bry_(tmp_bfr.Xto_bry_and_clear());
			byte[][] segs = gfo_url.Segs();
			byte[] page_bry = segs.length > 5 && Bry_.Eq(segs[2], Xof_url_bldr.Bry_thumb) ? segs[5] : gfo_url.Page();
			url.Page_bry_(tmp_bfr.Add(Bry_file).Add(page_bry).Xto_bry_and_clear());
			url.Anchor_bry_(Bry_.Empty);
		}
		else {
			url.Segs_ary_(gfo_url.Segs());
			url.Lang_bry_(gfo_url.Site_sub());
			url.Wiki_bry_(gfo_url.Site());
			url.Page_bry_(gfo_url.Page());
			url.Anchor_bry_(gfo_url.Anchor());
		}
		Gfo_url_arg[] args = gfo_url.Args();	// parse args
		int args_len = args.length;
		boolean args_is_invalid = false;
		for (int i = 0; i < args_len; i++) {
			Gfo_url_arg arg = args[i];
			byte[] key = arg.Key_bry();
			if (Bry_.Len_eq_0(key)) {
				args_is_invalid = true;
				break;
			}
			Object o = qry_args_hash.Get_by_bry(key);
			if (o != null) {
				Byte_obj_val id = (Byte_obj_val)o;
				switch (id.Val()) {
					case Id_arg_redirect: 	url.Redirect_force_(true); break;
					case Id_arg_uselang: 	url.Use_lang_(arg.Val_bry()); break;
					case Id_arg_action: 	if (Bry_.Eq(arg.Val_bry(), Bry_arg_action_edit)) url.Action_is_edit_(true); break;
					case Id_arg_title: 		url.Page_bry_(arg.Val_bry()); url.Segs_ary_(Segs_ary_remove_w(url.Segs_ary())); break;	// handle /w/index.php?title=Earth
					case Id_arg_fulltext: 	url.Search_fulltext_(true); break;
				}
			} 
		}
		if (args_is_invalid) {
			byte[] raw_bry = gfo_url.Raw();
			byte[] args_bry = Bry_.Mid(raw_bry, gfo_url.Args_bgn(), raw_bry.length);
			byte[] anchor_bry = url.Anchor_bry();
			if (anchor_bry == null)	// no anchor; set page to rest of url
				url.Page_bry_(Bry_.Add(url.Page_bry(), args_bry));
			else
				url.Anchor_bry_(Bry_.Add(url.Anchor_bry(), args_bry));
		}
		else
			url.Args_(args);
		return url.Err() == Gfo_url.Err_none;
	}
	private static byte[][] Segs_ary_remove_w(byte[][] ary) {
		int len = ary.length;
		if (len != 1) return ary;	// expecting only "w"
		byte[] last = ary[0];
		return last.length == 1  && last[0] == Byte_ascii.Ltr_w	// last is not "w"
			? Bry_.Ary_empty
			: ary
			;
	}
	public static Xoa_url Parse_url(Xoae_app app, Xowe_wiki cur_wiki, String raw) {
		byte[] raw_bry = Bry_.new_utf8_(raw);
		return Parse_url(Xoa_url.blank_(), app, cur_wiki, raw_bry, 0, raw_bry.length, false);
	}
	public static Xoa_url Parse_url(Xoae_app app, Xowe_wiki cur_wiki, byte[] raw, int bgn, int end, boolean from_url_bar) {return Parse_url(Xoa_url.blank_(), app, cur_wiki, raw, bgn, end, from_url_bar);}
	public static Xoa_url Parse_url(Xoa_url rv, Xoae_app app, Xowe_wiki cur_wiki, byte[] raw, int bgn, int end, boolean from_url_bar) {
		Xowe_wiki wiki = null; Bry_bfr_mkr bfr_mkr = app.Utl_bry_bfr_mkr();
		byte[] cur_wiki_key = cur_wiki.Domain_bry();
		byte[] page_bry = Bry_.Empty;
		boolean page_is_main_page = false;
		if (app.Url_parser().Parse(rv, raw, bgn, end)) {	// parse passed; url has protocol; take Page; EX: "http://en.wikipedia.org/wiki/Earth"
			wiki = Parse_url__wiki(app, rv.Wiki_bry());
			if (rv.Segs_ary().length == 0 && rv.Page_bry() != null && Bry_.Eq(rv.Page_bry(), Xoa_url_parser.Bry_wiki_name))	// wiki, but directly after site; EX:en.wikipedia.org/wiki
				page_is_main_page = true;
			else
				page_bry = Parse_url__combine(bfr_mkr, null, rv.Segs_ary(), rv.Page_bry());	// NOTE: pass null in for wiki b/c wiki has value, but should not be used for Page
		}
		else {												// parse failed; url doesn't have protocol
			byte[] wiki_bry = rv.Wiki_bry();
			if (Bry_.Len_gt_0(wiki_bry)) {					// NOTE: wiki_bry is null when passing in Category:A from home_wiki
				Xow_xwiki_itm xwiki_itm = app.User().Wiki().Xwiki_mgr().Get_by_key(wiki_bry);	// check if url.Wiki_bry is actually wiki; note that checking User().Wiki().Xwiki_mgr() to find "offline" wikis
				if (	xwiki_itm != null						// null-check
					&&	Bry_.Eq(xwiki_itm.Domain_bry(), wiki_bry)// check that xwiki.domain == wiki; avoids false lang matches like "so/page" or "C/page"; EX: "fr.wikipedia.org" vs "fr"; ca.s:So/Natura_del_so; DATE:2014-04-26; PAGE:no.b:C/Variabler; DATE:2014-10-14
					)
					wiki =  app.Wiki_mgr().Get_by_key_or_make(xwiki_itm.Domain_bry());
			}
			if (rv.Page_bry() == null) {					// 1 seg; EX: "Earth"; "fr.wikipedia.org"
				if (wiki != null) {							// wiki_bry is known wiki; EX: "fr.wikipedia.org"
					wiki = app.Wiki_mgr().Get_by_key_or_make(wiki_bry);	// call get again, but this time "make" it
					page_is_main_page = true;
				}
				else {										// otherwise, assume page name
					wiki = Parse_url__wiki(app, cur_wiki_key);
					page_bry = wiki_bry;
				}
			}
			else {											// 2+ segs
				if (wiki != null) {							// valid wiki; handle en.wikisource.org/Hamlet and en.wikisource.org/Hamlet/Act I
					if (rv.Segs_ary().length == 0 && Bry_.Eq(rv.Page_bry(), Xoa_url_parser.Bry_wiki_name))
						page_is_main_page = true;
					else
						page_bry = Parse_url__combine(bfr_mkr, Xoa_page_.Main_page_bry_empty, rv.Segs_ary(), rv.Page_bry());
				}
				else {										// invalid wiki; assume cur_wiki; EX: Hamlet/Act I
					page_bry = rv.Page_bry();
					byte[][] segs_ary = rv.Segs_ary();
					if (segs_ary.length > 0)
						page_bry = segs_ary[0];
					int colon_pos = Bry_finder.Find_fwd(page_bry, Byte_ascii.Colon);	// check for alias; EX: w:Earth
					boolean xwiki_set = false;
					if (colon_pos != Bry_.NotFound) {							// alias found
						Xow_xwiki_itm xwiki = cur_wiki.Xwiki_mgr().Get_by_mid(page_bry, 0, colon_pos);
						if (xwiki != null) {
							wiki = app.Wiki_mgr().Get_by_key_or_make(xwiki.Domain_bry());
							page_bry = Bry_.Mid(page_bry, colon_pos + 1, page_bry.length); 
							if (rv.Segs_ary().length == 0)		// handle xwiki without segs; EX: commons:Commons:Media_of_the_day; DATE:2014-02-19
								rv.Segs_ary_(new byte[][] {Bry_wiki_name, page_bry});	// create segs of "/wiki/Page"
							else {
								rv.Segs_ary()[0] = page_bry;
								page_bry = Parse_url__combine(bfr_mkr, rv.Wiki_bry(), rv.Segs_ary(), rv.Page_bry());
							}
							xwiki_set = true;
						}
					}
					if (!xwiki_set) {
						wiki = Parse_url__wiki(app, cur_wiki_key);
						page_bry = Parse_url__combine(bfr_mkr, rv.Wiki_bry(), rv.Segs_ary(), rv.Page_bry());
					}
				}
			}
		}
		if (page_is_main_page) {	// Main_Page requested; EX: "zh.wikipedia.org"; "zh.wikipedia.org/wiki/"; DATE:2014-02-16
			if (from_url_bar) {
				wiki.Init_assert();	// NOTE: must call Init_assert to load Main_Page; only call if from url_bar, else all sister wikis will be loaded when parsing Sister_wikis panel
				page_bry = wiki.Props().Main_page();
			}
			else
				page_bry = Xoa_page_.Main_page_bry_empty;
		}
		if (rv.Anchor_bry() != null) {
			byte[] anchor_bry = Xoa_app_.Utl_encoder_mgr().Id().Encode(rv.Anchor_bry());	// reencode for anchors (which use . encoding, not % encoding); PAGE:en.w:Enlightenment_Spain#Enlightened_despotism_.281759%E2%80%931788.29
			rv.Anchor_bry_(anchor_bry);
		}
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, page_bry);
		if (ttl != null) {	// can still be empty; EX: "en.wikipedia.org"
			Xow_xwiki_itm lang_xwiki = ttl.Wik_itm();
			if (lang_xwiki != null && lang_xwiki.Type_is_xwiki_lang(wiki.Lang().Lang_id())) {	// format of http://en.wikipedia.org/wiki/fr:A
				wiki = app.Wiki_mgr().Get_by_key_or_make(lang_xwiki.Domain_bry());
				page_bry = ttl.Page_txt();
			}
		}
		rv.Wiki_bry_(wiki.Domain_bry());
		rv.Page_bry_(page_bry);
		return rv;
	}
	private static Xowe_wiki Parse_url__wiki(Xoae_app app, byte[] key) {
		Xowe_wiki rv = null;
		Xow_xwiki_itm xwiki = app.User().Wiki().Xwiki_mgr().Get_by_key(key);
 			if (xwiki == null)
			rv = app.User().Wiki();
		else
			rv = app.Wiki_mgr().Get_by_key_or_make(xwiki.Domain_bry());
		return rv;			
	}
	private static byte[] Parse_url__combine(Bry_bfr_mkr bry_bfr_mkr, byte[] wiki, byte[][] segs, byte[] page) {
		Bry_bfr bfr = bry_bfr_mkr.Get_b512();
		if (wiki != null) bfr.Add(wiki);
		if (segs != null) {
			int segs_len = segs.length;
			for (int i = 0; i < segs_len; i++) {
				byte[] seg = segs[i];
				if (i == 0 && Bry_.Eq(seg, Xoa_url_parser.Bry_wiki_name)) continue; 
				if (bfr.Len() > 0) bfr.Add_byte(Byte_ascii.Slash);
				bfr.Add(seg);
			}
		}
		if (page != null) {
			if (bfr.Len() > 0) bfr.Add_byte(Byte_ascii.Slash);
			bfr.Add(page);
		}
		return bfr.Mkr_rls().Xto_bry_and_clear();
	}
	public static Xoa_url Parse_from_url_bar(Xoae_app app, Xowe_wiki wiki, String s) {
		byte[] bry = Bry_.new_utf8_(s);
		bry = Parse_from_url_bar__strip_mobile(bry);
		byte[] fmt = app.Gui_mgr().Url_macro_mgr().Fmt_or_null(bry);
		if (fmt != null) bry = fmt;
		Xoa_url rv = Xoa_url_parser.Parse_url(app, wiki, bry, 0, bry.length, true);
		if (app.Wiki_mgr().Wiki_regy().Url_is_invalid_domain(rv)) {	// handle lang_code entered; EX: "war" should redirect to "war" article in current wiki, not war.wikipedia.org; DATE:2014-02-07
			rv.Page_bry_(rv.Wiki_bry());
			rv.Wiki_bry_(wiki.Domain_bry());
		}
		return rv;
	}
	private static byte[] Parse_from_url_bar__strip_mobile(byte[] v) {// DATE:2014-05-03
		int pos = Bry_finder.Find_fwd(v, Byte_ascii.Dot);
		if (	pos == Bry_finder.Not_found	// no dot; EX: "A"
			||	pos + 2 >= v.length				// not enough space for .m.; EX: "A.b"
			)	
			return v;
		switch (v[pos + 1]) {	// check for m
			case Byte_ascii.Ltr_M:
			case Byte_ascii.Ltr_m:
				break;
			default:
				return v;
		}
		if (v[pos + 2] != Byte_ascii.Dot) return v;
		return Bry_.Add(Bry_.Mid(v, 0, pos), Bry_.Mid(v, pos + 2));	// skip ".m"
	}
	// private static final byte Tid_xowa = (byte)Gfo_url_parser.Protocol_file_tid + 1;
	private static final byte Id_arg_redirect = 0, Id_arg_uselang = 1, Id_arg_title = 2, Id_arg_action = 3, Id_arg_fulltext = 4;
	private static final byte[] Bry_arg_redirect = Bry_.new_ascii_("redirect"), Bry_arg_uselang = Bry_.new_ascii_("uselang"), Bry_arg_title = Bry_.new_ascii_("title"), Bry_arg_fulltext = Bry_.new_ascii_("fulltext");
	private static final byte[] Bry_upload_wikimedia_org = Bry_.new_ascii_("upload.wikimedia.org"), Bry_dot_org = Bry_.new_ascii_(".org")
		, Bry_file = Bry_.new_ascii_("File:");	// NOTE: File does not need i18n; is a canonical namespace 
	public static final byte[] Bry_wiki_name = Bry_.new_ascii_("wiki");
	private static final byte[][] Bry_wiki_name_bry = new byte[][] {Bry_wiki_name};
	public static final byte[] Bry_arg_action_eq_edit = Bry_.new_ascii_("action=edit")
	, Bry_arg_action = Bry_.new_ascii_("action")
	, Bry_arg_action_edit = Bry_.new_ascii_("edit")
	;
	private static final Hash_adp_bry qry_args_hash = Hash_adp_bry.ci_ascii_()
	.Add_bry_byte(Bry_arg_redirect, Id_arg_redirect)
	.Add_bry_byte(Bry_arg_uselang, Id_arg_uselang)
	.Add_bry_byte(Bry_arg_title, Id_arg_title)
	.Add_bry_byte(Bry_arg_action, Id_arg_action)
	.Add_bry_byte(Bry_arg_fulltext, Id_arg_fulltext)
	;
	private static final Hash_adp_bry upload_segs_hash = Hash_adp_bry.ci_ascii_()
	.Add_bry_bry(Xow_domain_.Tid_bry_commons);//.Add_bry_bry(Xow_domain_.Tid_bry_species_bry).Add_bry_bry(Xow_domain_.Tid_bry_meta_bry);
}

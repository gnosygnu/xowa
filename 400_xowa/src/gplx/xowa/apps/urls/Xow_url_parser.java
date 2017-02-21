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
import gplx.core.primitives.*; import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.langs.htmls.encoders.*;
import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.vnts.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.files.*;
public class Xow_url_parser {
	private final    Object thread_lock = new Object();
	private final    Gfo_url_encoder encoder;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	private final    Gfo_url_parser url_parser = new Gfo_url_parser();
	private final    Gfo_url_encoder gfs_encoder = Gfo_url_encoder_.New__gfs().Make();
	private final    Xoa_app app; private final    Xow_wiki wiki; private final    byte[] domain_bry;
	private byte tmp_protocol_tid;
	private int tmp_tid;
	private byte[] tmp_raw, tmp_orig, tmp_wiki, tmp_page, tmp_anch, tmp_protocol_bry; private Gfo_qarg_itm[] tmp_qargs;
	private byte[][] tmp_segs; private int tmp_segs_len;
	private boolean tmp_protocol_is_relative, tmp_page_is_main, tmp_wiki_is_missing;
	private byte[] tmp_vnt;
	private final    Xol_vnt_mgr vnt_mgr;
	public Xow_url_parser(Xow_wiki wiki) {
		this.app = wiki.App();
		this.wiki = wiki; this.domain_bry = wiki.Domain_bry();
		this.encoder = gplx.langs.htmls.encoders.Gfo_url_encoder_.Xourl;
		this.vnt_mgr = wiki.Type_is_edit() ? wiki.Lang().Vnt_mgr() : null;
	}
	public Xoa_url Parse_by_urlbar_or_null(String str) {
		Xoae_app app = (Xoae_app)wiki.App();
		byte[] bry = Strip_mobile_segment(Bry_.new_u8(str));
		byte[] fmt = app.Gui_mgr().Url_macro_mgr().Fmt_or_null(bry);
		if (fmt != null) bry = fmt;
		Xoa_url rv = Xoa_url.blank(); 
		this.Parse(rv, bry, 0, bry.length); if (rv.Page_bry() == null) {Xoa_url_.Invalid_warn(str); return null;}
		byte[] wiki_bry = rv.Wiki_bry();
		Xow_xwiki_itm xwiki_itm = app.User().Wikii().Xwiki_mgr().Get_by_key(wiki_bry); 
		if (xwiki_itm == null) {Xoa_url_.Invalid_warn(str); return null;}	// if wiki doesn't exist, warn and return nothing; DATE:2015-08-25
		if (rv.Page_is_main()) {		// Main_Page requested; EX: "zh.wikipedia.org"; "zh.wikipedia.org/wiki/"; DATE:2014-02-16
			Xow_wiki wiki_itm = app.Wiki_mgri().Get_by_or_make_init_y(wiki_bry); // NOTE: must call Init to load Main_Page; only call if from url_bar, else all sister wikis will be loaded when parsing Sister_wikis panel
			rv.Page_bry_(wiki_itm.Props().Main_page());
		}
		Xow_wiki parse_wiki = wiki;
		if (!Bry_.Eq(wiki_bry, wiki.Domain_bry()))							// NOTE: url's wiki is different than current wiki
			parse_wiki = app.Wiki_mgr().Get_by_or_make_init_y(wiki_bry);	// NOTE: change parse_wiki to url's wiki; needed to handle transition from home to en.d or other case-sensitivity wiki; EX: "d:earth" -> "earth" x> "Earth"; DATE:2016-04-28
		Xoa_ttl ttl = parse_wiki .Ttl_parse(rv.Page_bry());					// NOTE: parse to ttl to get proper casing; EX: "earth" -> "Earth" x> "earth"; DATE:2016-03-25
		rv.Page_bry_(ttl.Full_db());
		return rv;
	}
	public Gfo_url_parser Url_parser() {return url_parser;}
	public Xoa_url Parse(byte[] src) {Xoa_url rv = Xoa_url.blank(); Parse(rv, src); return rv;}
	public Xoa_url Parse(byte[] src, int bgn, int end) {Xoa_url rv = Xoa_url.blank(); Parse(rv, src, bgn, end); return rv;}
	public boolean Parse(Xoa_url rv, byte[] src) {return Parse(rv, src, 0, src.length);}
	public boolean Parse(Xoa_url rv, byte[] src, int bgn, int end) {
		synchronized (thread_lock) {
			if (end - bgn == 0) {Init_tmp_vars(Gfo_url.Empty); Make(rv); return false;}
			tmp_orig = (bgn == 0 && end == src.length) ? src : Bry_.Mid(src, bgn, end);
			// src = encoder.Decode(src, bgn, end);		// NOTE: must decode any url-encoded parameters; TOMBSTONE:do not auto-decode DATE:2016-10-10
			int src_len = end - bgn;
			Gfo_url gfo_url = url_parser.Parse(src, bgn, end);	// parse to plain gfo_url
			Init_tmp_vars(gfo_url);
			if (src[0] == Byte_ascii.Hash)				// src is anch; EX: #A
				Bld_anch();
			else {
				switch (tmp_protocol_tid) {
					case Gfo_protocol_itm.Tid_file:
						if (src_len > 5 && src[5] != Byte_ascii.Slash)			// src is ttl in [[File]] ns; EX: "File:A.png"
							Bld_page_by_file_ns();
						else													// src is file:///; EX: EX: "file:///C:/A.png"
							tmp_tid = Xoa_url_.Tid_file;
						break;
					case Gfo_protocol_itm.Tid_xowa:
						Bld_xowa();
						break;
					case Gfo_protocol_itm.Tid_http:
					case Gfo_protocol_itm.Tid_https:
					case Gfo_protocol_itm.Tid_relative_1:
						if (tmp_protocol_tid == Gfo_protocol_itm.Tid_relative_1)	// interpret relative protocol links to match wiki's protocol; EX: [//a.org] -> https://a.org for all WMF wikis; DATE:2015-07-27
							tmp_protocol_tid = wiki.Props().Protocol_tid();
						if (app.User().Wikii().Xwiki_mgr().Get_by_key(tmp_wiki) != null)// src is offline wiki; EX: http://fr.wikipedia.org/wiki/A
							Bld_page(0);
						else if (Bry_.Eq(tmp_wiki, Bry_upload_wikimedia_org))			// src is upload.wikimedia.org; EX: "http://upload.wikimedia.org/wikipedia/commons/a/ab/C.svg"
							Bld_page_from_upload_wikimedia_org();
						else															// src is unknown site: EX: "http://a.org"
							tmp_tid = Xoa_url_.Tid_inet;
						break;
					case Gfo_protocol_itm.Tid_unknown:
						Bld_page(0);
						break;
					default:
						tmp_tid = Xoa_url_.Tid_inet;
						break;
				} 
			}
			Bld_qargs();
			if (tmp_page_is_main) tmp_page = Xoa_page_.Main_page_bry_empty;
			if (tmp_anch != null) {
				byte[] anchor_bry = gplx.langs.htmls.encoders.Gfo_url_encoder_.Id.Encode(tmp_anch);	// reencode for anchors (which use . encoding, not % encoding); PAGE:en.w:Enlightenment_Spain#Enlightened_despotism_.281759%E2%80%931788.29
				tmp_anch = anchor_bry;
			}
			Make(rv);
			return true;
		}
	}
	private void Init_tmp_vars(Gfo_url gfo_url) {
		tmp_tid = Xoa_url_.Tid_unknown;
		tmp_raw = gfo_url.Raw();
		tmp_wiki = gfo_url.Segs__get_at_1st();
		tmp_page = gfo_url.Segs__get_at_nth();
		tmp_anch = gfo_url.Anch(); tmp_qargs = gfo_url.Qargs();
		tmp_wiki_is_missing = false;
		tmp_page_is_main = false;
		tmp_protocol_tid = gfo_url.Protocol_tid();
		tmp_protocol_bry = gfo_url.Protocol_bry();
		tmp_protocol_is_relative = gfo_url.Protocol_tid() == Gfo_protocol_itm.Tid_relative_1; // gfo_url.Protocol_is_relative();
		if (tmp_protocol_is_relative) tmp_protocol_tid = Gfo_protocol_itm.Tid_https;
		tmp_vnt = null;
		tmp_segs = gfo_url.Segs();
		tmp_segs_len = tmp_segs.length;
	}
	private Xoa_url Make(Xoa_url rv) {
		rv.Ctor
		( tmp_tid, tmp_orig, tmp_raw, tmp_protocol_tid, tmp_protocol_bry, tmp_protocol_is_relative
		, tmp_wiki, tmp_page, tmp_qargs, tmp_anch
		, tmp_segs, tmp_vnt, tmp_wiki_is_missing, Bry_.Eq(tmp_wiki, wiki.Domain_bry()), tmp_page_is_main);
		return rv;
	}
	private void Bld_anch() {
		tmp_tid = Xoa_url_.Tid_anch;
		tmp_wiki = domain_bry; tmp_wiki_is_missing = true;
		tmp_page = Bry_.Empty;
	}
	private void Bld_xowa() {
		tmp_tid = Xoa_url_.Tid_xcmd;
		tmp_page = Bry_.Mid(tmp_raw, Gfo_protocol_itm.Len_xcmd);		// NOTE: just get String after protocol; anchor (#) or query params (?) must not be parsed
		tmp_page = gfs_encoder.Decode(tmp_page);	// NOTE: should be decoded; EX: %20 -> " "
	}
	private void Bld_page_by_file_ns() {
		tmp_tid = Xoa_url_.Tid_page;
		tmp_segs[0] = Bry_.Add(Bry_file, tmp_wiki);			
		tmp_page = Make_page_from_segs(0);
		tmp_wiki = domain_bry; tmp_wiki_is_missing = true;
	}
	private void Bld_page_from_upload_wikimedia_org() {
		//	orig: https://upload.wikimedia.org/wikipedia/commons/a/ab/A.jpg
		//	thum: https://upload.wikimedia.org/wikipedia/commons/thumb/a/ab/A.jpg/220px-A.jpg
		byte[] domain_type = tmp_segs[1];						// seg[0] = type; EX: "/wikipedia/"
		byte[] lang = tmp_segs[2];								// seg[1] = lang; EX: "en", "fr"; "commons"
		if (Bry_.Eq(lang, Xow_domain_tid_.Bry__commons))	// commons links will have fmt of "/wikipedia/commons"; must change to wikimedia
			domain_type = Xow_domain_tid_.Bry__wikimedia;
		tmp_wiki = tmp_bfr.Clear()
			.Add(lang).Add_byte(Byte_ascii.Dot)					// add lang/type + .;	EX: "en."; "fr."; "commons."
			.Add(domain_type).Add(Bry_dot_org)					// add type + .org;		EX: "wikipedia.org"; "wikimedia.org";
			.To_bry_and_clear();
		if (tmp_segs_len > 6 && Bry_.Eq(tmp_segs[3], Xof_url_bldr.Bry_thumb)) tmp_page = tmp_segs[6];	// if "/thumb/", set page from seg[n-1] to seg[6]; EX: using thumb example above, "A.jpg", not "220px-A.jpg"
		tmp_page = Bry_.Add(Bry_file, tmp_page);				// always add "File:" ns
	}
	private boolean Find_wiki(byte[] domain) {
		Xow_xwiki_itm xwiki = app.User().Wikii().Xwiki_mgr().Get_by_key(domain);	// check if tmp_wiki is known wiki
		if (xwiki != null) {
			if (!Bry_.Eq(xwiki.Domain_bry(), domain))	// ignore aliases by checking that xwiki.domain == wiki; avoids false lang matches like "so/page" or "C/page"; PAGE: ca.s:So/Natura_del_so; DATE:2014-04-26; PAGE:no.b:C/Variabler; DATE:2014-10-14
				xwiki = null;
		}
		if (xwiki != null) return true;
		if (app.Wiki_mgri().Has(domain)) return true;
		return Byte_.In(tmp_protocol_tid, Gfo_protocol_itm.Tid_http, Gfo_protocol_itm.Tid_https);
	}
	private void Bld_page(int bgn_seg) {
		tmp_tid = Xoa_url_.Tid_page;
		tmp_wiki = tmp_segs[bgn_seg];	// try seg[0] as wiki
		int seg_idx = bgn_seg + 1;
		boolean wiki_exists = Find_wiki(tmp_wiki);
		if (wiki_exists) {
			int tmp_seg_idx = Bld_main_page_or_vnt(bgn_seg);
			seg_idx = tmp_seg_idx;
			int tmp_vnt_seg = bgn_seg + 1;
			if (vnt_mgr.Enabled() && tmp_vnt_seg < tmp_segs_len && vnt_mgr.Regy().Has(tmp_segs[tmp_vnt_seg])) {	// check if "/zh-hans/"
				tmp_vnt = tmp_segs[tmp_vnt_seg];
			}
			if (tmp_seg_idx == -1) return;	// main_page or vnt; exit
		}
		else {
			tmp_wiki = domain_bry;		// tmp_wiki is current
			tmp_wiki_is_missing = true;
			--seg_idx;					// move seg_idx back to wiki
		}
		tmp_page = tmp_segs[seg_idx];
		byte[] frag = Bld_page_by_alias(tmp_page);	// handle en.wikipedia.org/wiki/fr:A
		if (frag != null) {
			tmp_segs[seg_idx] = frag;	// alias found;	set page to rhs; EX: "fr:A" -> "A"
			if (frag.length == 0)		// handle main_page; EX: "fr:"
				tmp_page_is_main = true;
		}
		tmp_page = Make_page_from_segs(seg_idx);	// join segs together; needed for nesting; EX: A/B/C
	}
	private int Bld_main_page_or_vnt(int bgn_seg) {
		int rv = -1;
		switch (tmp_segs_len - bgn_seg) {
			case 1:	 // "en.wikipedia.org"
				if (Bry_.Eq(tmp_segs[0 + bgn_seg], Xow_domain_itm_.Bry__home)) {	// ignore "home" which should always go to "home" of current wiki, not "home" wiki
					tmp_wiki = domain_bry;
					return 0;
				}
				else {
					tmp_page_is_main = true;
					return -1;
				}
			case 2:	// "en.wikipedia.org/"; "en.wikipedia.org/wiki"; "en.wikipedia.org/A"
				if (Bry_.Len_eq_0(tmp_segs[1 + bgn_seg])) {	// check for "en.wikipedia.org/"
					tmp_page_is_main = true;
					return -1;
				}
				else
					rv = 1;
				break;
			case 3: 
				if (Bry_.Len_gt_0(tmp_segs[2 + bgn_seg]))	// check only for "en.wikipedia.org/wiki/" where seg[2] is blank
					return 2;
				else
					rv = 2;
				break;
			default:
				return 2;
		}
		byte[] mid = tmp_segs[1 + bgn_seg];
		if (Bry_.Eq(mid, Bry_wiki)) {	// check if "/wiki/"
			tmp_page_is_main = true;
			return -1;
		}
		else
			return rv;
	}
	private byte[] Bld_page_by_alias(byte[] bry) {
		if (bry == null) return null;
		int colon_pos = Bry_find_.Find_fwd(bry, Byte_ascii.Colon);						// check for colon; EX: commons:Earth
		if (colon_pos == Bry_find_.Not_found) return null;									// no colon
		Xow_wiki alias_wiki = wiki;														// default alias_wiki to cur_wiki
		if (!tmp_wiki_is_missing)														// tmp_wiki exists; use it for alias wikis; DATE:2015-09-17
			alias_wiki = wiki.App().Wiki_mgri().Get_by_or_make_init_n(tmp_wiki);
		Xow_xwiki_itm alias_itm = alias_wiki.Xwiki_mgr().Get_by_mid(bry, 0, colon_pos);	// check for alias;
		if (alias_itm == null) return null;												// colon-word is not alias; EX:A:B
		Xow_ns_mgr ns_mgr = tmp_wiki_is_missing ? wiki.Ns_mgr() : wiki.App().Dbmeta_mgr().Ns__get_or_load(tmp_wiki);
		if (ns_mgr.Names_get_or_null(alias_itm.Key_bry()) != null)						// special case to handle collision between "wikipedia" alias and "Wikipedia" namespace; if alias exists as ns, ignore it; EX:sv.wikipedia.org/wiki/Wikipedia:Main_Page DATE:2015-07-31
			return null;
		byte[] rv = Bry_.Mid(bry, colon_pos + 1); 
		tmp_wiki = alias_itm.Domain_bry();
		return rv;
	}
	private void Bld_qargs() {
		int qargs_len = tmp_qargs.length;
		for (int i = 0; i < qargs_len; ++i) {
			Gfo_qarg_itm qarg = tmp_qargs[i];
			if (Bry_.Eq(qarg.Key_bry(), Qarg__title))
				tmp_page = qarg.Val_bry();					// handle /w/index.php?title=Earth
		}
	}
	private byte[] Make_page_from_segs(int bgn) {
		if (tmp_segs_len - bgn == 1) return tmp_segs[tmp_segs_len - 1];	// only 1 item; just return it; don't build bry
		for (int i = bgn; i < tmp_segs_len; i++) {
			if (i != bgn) tmp_bfr.Add_byte(Byte_ascii.Slash);
			tmp_bfr.Add(tmp_segs[i]);
		}
		return tmp_bfr.To_bry_and_clear();
	}
	public String Build_str(Xoa_url url) {									// transform to "canonical" form that fits url box for both XOWA and Mozilla Firefox
		tmp_bfr.Add(url.Wiki_bry());										// add wiki;		EX: "en.wikipedia.org"
		tmp_bfr.Add(Xoh_href_.Bry__wiki);									// add "/wiki/"		EX: "/wiki/"
		tmp_bfr.Add(encoder.Decode(url.Page_bry()));						// add page;		EX: "A"
		int args_len = url.Qargs_ary().length;
		if (args_len > 0) {
			for (int i = 0; i < args_len; i++) {
				byte dlm = i == 0 ? Byte_ascii.Question : Byte_ascii.Amp;
				tmp_bfr.Add_byte(dlm);
				Gfo_qarg_itm arg = url.Qargs_ary()[i];
				tmp_bfr.Add(arg.Key_bry()).Add_byte(Byte_ascii.Eq).Add(arg.Val_bry());
			}
		}
		if (url.Anch_bry() != null)
			tmp_bfr.Add_byte(Byte_ascii.Hash).Add(url.Anch_bry());		// add anchor;		EX: "#B"
		return tmp_bfr.To_str_and_clear();
	}
	private static byte[] Strip_mobile_segment(byte[] v) {// DATE:2014-05-03
		int pos = Bry_find_.Find_fwd(v, Byte_ascii.Dot);
		if (	pos == Bry_find_.Not_found		// no dot; EX: "A"
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
	private static final    byte[] Qarg__title = Bry_.new_a7("title");
	private static final    byte[]
	  Bry_upload_wikimedia_org = Bry_.new_a7("upload.wikimedia.org")
	, Bry_file = Bry_.new_a7("File:")	// NOTE: File does not need i18n; is a canonical namespace 
	, Bry_wiki = Bry_.new_a7("wiki")
	;
	public static final    byte[]
	  Bry_dot_org = Bry_.new_a7(".org")
	;
}

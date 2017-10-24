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
package gplx.xowa.htmls.core.wkrs.lnkis.anchs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import gplx.core.brys.*; import gplx.core.btries.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.langs.htmls.encoders.*;
import gplx.xowa.wikis.ttls.*; import gplx.xowa.wikis.nss.*;
public class Xoh_anch_href_data implements Xoh_itm_parser {
	private final    Bry_rdr rdr = new Bry_rdr().Dflt_dlm_(Byte_ascii.Slash);
	public void Fail_throws_err_(boolean v) {rdr.Fail_throws_err_(v);}// TEST
	public Gfh_atr Atr() {return atr;} private Gfh_atr atr;
	public byte Tid() {return tid;} private byte tid;
	public byte[] Rng_src() {return rng_src;} private byte[] rng_src;
	public int Rng_bgn() {return rng_bgn;} private int rng_bgn;
	public int Rng_end() {return rng_end;} private int rng_end;
	public int Site_bgn() {return site_bgn;} private int site_bgn;
	public int Site_end() {return site_end;} private int site_end;
	public boolean Site_exists() {return site_end > site_bgn;}
	public byte[] Ttl_full_txt() {return ttl_full_txt;} private byte[] ttl_full_txt;
	public byte[] Ttl_page_db() {return ttl_page_db;} private byte[] ttl_page_db;
	public boolean Ttl_is_main_page() {return ttl_page_db.length == 0;}
	public int Ttl_ns_id() {return ttl_ns_id;} private int ttl_ns_id;
	public byte[] Ttl_ns_custom() {return ttl_ns_custom;} private byte[] ttl_ns_custom;
	public int Ttl_bgn() {return ttl_bgn;} private int ttl_bgn;
	public int Ttl_end() {return ttl_end;} private int ttl_end;
	public void Clear() {
		tid = Tid__wiki;
		rng_bgn = rng_end = site_bgn = site_end = ttl_bgn = ttl_end = -1;
		ttl_full_txt = ttl_page_db = ttl_ns_custom = null;
		ttl_ns_id = Xow_ns_.Tid__main;
	}
	public boolean Parse(Bry_err_wkr err_wkr, Xoh_hdoc_ctx hctx, byte[] src, Gfh_tag tag) {
		this.atr = tag.Atrs__get_by_or_empty(Gfh_atr_.Bry__href);
		return Parse(err_wkr, hctx, src, atr.Val_bgn(), atr.Val_end());
	}
	public boolean Parse(Bry_err_wkr err_wkr, Xoh_hdoc_ctx hctx, byte[] src, int rng_bgn, int rng_end) {
		if (rng_bgn == -1) return false;	// no href; return; EX: <a/> vs <a href='a.org'/>
		this.rng_src = src;
		rdr.Init_by_wkr(err_wkr, "href", rng_bgn, rng_end);
		this.rng_bgn = rng_bgn; this.rng_end = rng_end;
		if (rng_end == rng_bgn) {	// handle empty String separately; EX: href=""
			tid = Tid__inet;
			ttl_bgn = ttl_end = 0;
		}
		else {
			ttl_end = rng_end;
			switch (src[rng_bgn]) {
				case Byte_ascii.Hash:
					tid = Tid__anch;
					ttl_bgn = rng_bgn + 1;	// position ttl_bgn after #
					break;
				default:
					Parse_inet(hctx, src);
					break;
				case Byte_ascii.Slash:
					rdr.Move_by_one();		// skip "/"
					if (rdr.Chk(trie) == Tid__site) {	// EX: "/site/wiki/A"
						tid = Tid__site;
						site_bgn = rdr.Pos();
						site_end = rdr.Find_fwd_lr();
						rdr.Chk(Bry__wiki);
					}
					else
						tid = Tid__wiki;
					ttl_bgn = rdr.Pos();
					break;
			}
		}
		Parse_ttl(hctx.Wiki__ttl_parser(), src);
		return true;
	}
	private void Parse_inet(Xoh_hdoc_ctx hctx, byte[] src) {
		tid = Tid__inet;
		ttl_bgn = rng_bgn;
	}
	private void Parse_ttl(Xow_ttl_parser ttl_parser, byte[] src) {
		boolean ttl_is_empty = ttl_end - ttl_bgn == 0; // NOTE: ttl can be empty; EX: "href='/site/en.wikipedia.org/wiki/'" "href='/wiki/'"
		if (ttl_is_empty) {
			ttl_full_txt = ttl_page_db = Bry_.Empty;
		}
		else {
			ttl_full_txt = Bry_.Mid(src, ttl_bgn, ttl_end);
			int ttl_full_len = ttl_full_txt.length;
			int question_pos = Bry_find_.Find_fwd(ttl_full_txt, Byte_ascii.Question, 0, ttl_full_len); if (question_pos == -1) question_pos = ttl_full_len;
			ttl_full_txt = Xoa_ttl.Replace_unders(ttl_full_txt, 0, question_pos);	// NOTE: only replace unders up to question mark to handle category and sortkey; EX:Category:A?pageuntil=A B; PAGE:en.w:Category:Public_transit_articles_with_unsupported_infobox_fields; DATE:2016-01-14
			switch (tid) {
				case Xoh_anch_href_data.Tid__anch:
				case Xoh_anch_href_data.Tid__inet:
					ttl_ns_id = Xow_ns_.Tid__main;
					ttl_page_db = ttl_full_txt;
					break;
				case Xoh_anch_href_data.Tid__wiki:
				case Xoh_anch_href_data.Tid__site:
					int colon_pos = Bry_find_.Find_fwd(ttl_full_txt, Byte_ascii.Colon, 0, ttl_full_len);
					ttl_page_db = ttl_full_txt;
					if (colon_pos != Bry_find_.Not_found) {
						Xow_ns_mgr ns_mgr = ttl_parser.Ns_mgr();
						Object ns_obj = ns_mgr.Names_get_or_null(ttl_full_txt, 0, colon_pos);
						if (ns_obj != null) {
							Xow_ns ns = (Xow_ns)ns_obj;
							if (ns.Id() != Xow_ns_.Tid__main) {
								ttl_ns_id = ns.Id();
								ttl_page_db = Bry_.Mid(ttl_full_txt, colon_pos + 1, ttl_full_len);
								if (!Bry_.Match(ttl_full_txt, 0, colon_pos, ns.Name_ui()))			// ns does not match expd name
									ttl_ns_custom = Bry_.Mid(ttl_full_txt, 0, colon_pos);			// mark as custom; NOTE: not using ttl_full_txt, b/c need to preserve underscores, else href="User_talk" -> "User talk"; PAGE:de.b:Wikibooks:Benutzersperrung/_InselFahrer DATE:2016-06-24
							}
						}
					}
					ttl_page_db = Xoa_ttl.Replace_spaces(ttl_page_db);
					break;
				default: throw Err_.new_unhandled(tid);
			}
		}
	}
	public void Init_by_decode(byte[] anch_href_bry) {
		this.rng_src = anch_href_bry; this.rng_bgn = 0; this.rng_end = rng_src.length;
	}
	public static final byte 
	  Tid__wiki = 0		// EX: href="/wiki/A"
	, Tid__site = 1		// EX: href="/site/en.wikipedia.org/wiki/A"
	, Tid__anch = 2		// EX: href="#A"
	, Tid__inet = 3		// EX: href="https://a.org/A"
	;
	private static final    byte[] Bry__site = Bry_.new_a7("site/"), Bry__wiki = Bry_.new_a7("wiki/");
	private static final    Btrie_slim_mgr trie = Btrie_slim_mgr.ci_a7()
	.Add_bry_byte(Bry__wiki, Tid__wiki)
	.Add_bry_byte(Bry__site, Tid__site)
	;
	public static boolean Ns_exists(byte tid) {
		switch (tid) {
			case Tid__wiki: case Tid__site: return true;
			case Tid__anch: case Tid__inet: return false;
			default:						throw Err_.new_unhandled(tid);
		}
	}
}

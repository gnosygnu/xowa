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
package gplx.xowa.htmls.core.wkrs.lnkis.anchs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import gplx.core.brys.*; import gplx.core.btries.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
import gplx.xowa.wikis.ttls.*; import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.domains.*; import gplx.xowa.apps.metas.*;
public class Xoh_anch_href_parser implements Xoh_itm_parser {
	private byte[] page_bry; private Xoa_ttl page_ttl; private Xoa_app app; private Xow_ttl_parser ttl_parser;
	private final Bry_rdr rdr = new Bry_rdr().Dflt_dlm_(Byte_ascii.Slash);
	public void Fail_throws_err_(boolean v) {rdr.Fail_throws_err_(v);}// TEST
	public Html_atr Atr() {return atr;} private Html_atr atr;
	public byte Tid() {return tid;} private byte tid;
	public boolean Tid_has_ns() {return tid_has_ns;} private boolean tid_has_ns;
	public byte[] Src() {return src;} private byte[] src; 
	public int Val_bgn() {return val_bgn;} private int val_bgn;
	public int Val_end() {return val_end;} private int val_end;
	public int Site_bgn() {return site_bgn;} private int site_bgn;
	public int Site_end() {return site_end;} private int site_end;
	public boolean Site_exists() {return site_end > site_bgn;}
	public boolean Rel_nofollow_exists() {
		if (Site_exists()) {
			if (rel_nofollow_exists == Bool_.__byte) {
				Xow_domain_itm itm = Xow_domain_itm_.parse(Bry_.Mid(src, site_bgn, site_end));
				rel_nofollow_exists = itm.Domain_type_id() == Xow_domain_tid_.Int__other ? Bool_.Y_byte : Bool_.N_byte;
			}
			return rel_nofollow_exists == Bool_.Y_byte;
		}
		else
			return false;
	} private byte rel_nofollow_exists;
	public int Page_bgn() {return page_bgn;} private int page_bgn;
	public int Page_end() {return page_end;} private int page_end;
	public byte[] Page_bry() {
		if (page_bry == null) {
			if (page_end - page_bgn == 0)	// NOTE: href="/site/en.wikipedia.org/wiki/" can be null
				page_bry = Xoa_page_.Main_page_bry;
			else
				page_bry = gplx.langs.htmls.encoders.Gfo_url_encoder_.Href.Decode(src, page_bgn, page_end);
		}
		return page_bry;
	}
	public Xoa_ttl Page_ttl() {
		if (page_ttl == null) {
			page_bry = this.Page_bry();
			if (site_bgn != -1)
				ttl_parser = app.Wiki_mgri().Get_by_key_or_make_init_n(Bry_.Mid(src, site_bgn, site_end));
			page_ttl = ttl_parser.Ttl_parse(page_bry);
			page_bry = page_ttl.Full_db_w_anch();
		}
		return page_ttl;
	}
	public int Page_ns_id() {
		switch (tid) {
			case Xoh_anch_href_parser.Tid__anch:
			case Xoh_anch_href_parser.Tid__inet:	return Xow_ns_.Tid__main;	// for purposes of hzip/make, assume main_ns
			case Xoh_anch_href_parser.Tid__wiki:
			case Xoh_anch_href_parser.Tid__site:	return this.Page_ttl().Ns().Id();
			default:								throw Err_.new_unhandled(tid);
		}
	}
	public boolean Page_ns_id_is_image()		{return this.Page_ns_id() == Xow_ns_.Tid__file && Bry_.Has_at_bgn(page_bry, Xow_ns_.Alias__image__bry);}
	public boolean Parse(Bry_rdr owner_rdr, Xoa_app app, Xow_ttl_parser ttl_parser, Html_tag tag) {
		this.atr = tag.Atrs__get_by_or_empty(Html_atr_.Bry__href);
		return Parse(owner_rdr, app, ttl_parser, atr.Val_bgn(), atr.Val_end());
	}
	public boolean Parse(Bry_rdr owner_rdr, Xoa_app app, Xow_ttl_parser ttl_parser, int href_bgn, int href_end) {
		if (href_bgn == -1) return false;
		rdr.Init_by_sub(owner_rdr, "anch.href", href_bgn, href_end);
		rel_nofollow_exists = Bool_.__byte;
		site_bgn = site_end = page_bgn = page_end = -1; this.src = owner_rdr.Src();
		tid = Tid__wiki;
		page_bry = null; page_ttl = null;
		this.val_bgn = href_bgn; this.val_end = href_end;
		this.src = owner_rdr.Src(); this.ttl_parser = ttl_parser; this.app = app;
		if (val_end == val_bgn) {
			tid = Tid__inet;
			page_bgn = page_end = 0;
			return true;		// handle empty String separately; EX: href=""
		}
		int pos = href_bgn;
		switch (src[pos]) {
			case Byte_ascii.Hash:
				tid = Tid__anch; tid_has_ns = Bool_.N;
				page_bgn = pos + 1;		// position page_bgn after #
				page_end = val_end;		// anch ends at EOS
				break;
			default:
				tid = Tid__inet; tid_has_ns = Bool_.N;
				page_bgn = pos;			// position page_bgn after #
				page_end = val_end;		// anch ends at EOS
				break;
			case Byte_ascii.Slash:
				rdr.Move_by_one();		// skip "/"
				if (rdr.Chk(trie) == Tid__site) {	// EX: "/site/wiki/A"
					tid = Tid__site; tid_has_ns = Bool_.Y;
					site_bgn = rdr.Pos();
					site_end = rdr.Find_fwd_lr();
					rdr.Chk(Bry__wiki);
				}
				else {
					tid = Tid__wiki; tid_has_ns = Bool_.Y;
				}
				page_bgn = rdr.Pos();
				page_end = rdr.Src_end();
				break;
		}
		return true;
	}
	public static final byte 
	  Tid__wiki = 0		// EX: href="/wiki/A"
	, Tid__site = 1		// EX: href="/site/en.wikipedia.org/wiki/A"
	, Tid__anch = 2		// EX: href="#A"
	, Tid__inet = 3		// EX: href="https://a.org/A"
	;
	private static final byte[] Bry__site = Bry_.new_a7("site/"), Bry__wiki = Bry_.new_a7("wiki/");
	private static final Btrie_slim_mgr trie = Btrie_slim_mgr.ci_a7()
	.Add_bry_byte(Bry__wiki, Tid__wiki)
	.Add_bry_byte(Bry__site, Tid__site)
	;
}

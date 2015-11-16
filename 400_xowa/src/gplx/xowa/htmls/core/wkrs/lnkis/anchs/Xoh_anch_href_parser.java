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
import gplx.xowa.wikis.ttls.*;
public class Xoh_anch_href_parser implements Xoh_itm_parser {
	private Xoa_ttl page_ttl; Xow_ttl_parser ttl_parser;
	private final Bry_rdr rdr = new Bry_rdr().Dflt_dlm_(Byte_ascii.Slash);
	public void Fail_throws_err_(boolean v) {rdr.Fail_throws_err_(v);}// TEST
	public Html_atr Atr() {return atr;} private Html_atr atr;
	public byte Tid() {return tid;} private byte tid;
	public byte[] Src() {return src;} private byte[] src; 
	public int Val_bgn() {return val_bgn;} private int val_bgn;
	public int Val_end() {return val_end;} private int val_end;
	public int Site_bgn() {return site_bgn;} private int site_bgn;
	public int Site_end() {return site_end;} private int site_end;
	public boolean Site_exists() {return site_end > site_bgn;}
	public int Page_bgn() {return page_bgn;} private int page_bgn;
	public int Page_end() {return page_end;} private int page_end;
	public byte[] Page_bry() {if (page_bry == null) page_bry = Bry_.Mid(src, page_bgn, page_end); return page_bry;} private byte[] page_bry;
	public Xoa_ttl Page_ttl() {
		if (page_ttl == null) {
			page_bry = this.Page_bry();
			page_ttl = ttl_parser.Ttl_parse(page_bry);
		}
		return page_ttl;
	}
	public void Parse(Bry_rdr owner_rdr, Xow_ttl_parser ttl_parser, Html_tag tag) {
		this.atr = tag.Atrs__get_by_or_fail(Html_atr_.Bry__href);
		Parse(owner_rdr, ttl_parser, atr.Val_bgn(), atr.Val_end());
	}
	public void Parse(Bry_rdr owner_rdr, Xow_ttl_parser ttl_parser, int href_bgn, int href_end) {
		rdr.Init_by_sub(owner_rdr, "lnki.href", href_bgn, href_end);
		site_bgn = site_end = page_bgn = page_end = -1;
		tid = Tid__null;
		page_bry = null; page_ttl = null;
		this.val_bgn = href_bgn; this.val_end = href_end;
		this.src = owner_rdr.Src(); this.ttl_parser = ttl_parser;
		if (val_end == val_bgn) {
			page_bgn = page_end = 0;
			return;		// handle empty String separately; EX: href=""
		}
		int pos = href_bgn;
		switch (src[pos]) {
			case Byte_ascii.Hash:
				tid = Tid__anch;
				page_bgn = pos + 1;		// position page_bgn after #
				page_end = val_end;		// anch ends at EOS
				break;
			default:
				tid = Tid__inet;
				page_bgn = pos;			// position page_bgn after #
				page_end = val_end;		// anch ends at EOS
				break;
			case Byte_ascii.Slash:
				rdr.Move_by_one();		// skip "/"
				if (rdr.Chk(trie) == Tid__site) {	// EX: "/site/wiki/A"
					tid = Tid__site;
					site_bgn = rdr.Pos();
					site_end = rdr.Find_fwd_lr();
					rdr.Chk(Bry__wiki);
				}
				else {
					tid = Tid__wiki;
				}
				page_bgn = rdr.Pos();
				page_end = rdr.Src_end();
				break;
		}
	}
	public static final byte 
	  Tid__null = 0		// EX: href=""
	, Tid__wiki = 1		// EX: href="/wiki/A"
	, Tid__site = 2		// EX: href="/site/en.wikipedia.org/wiki/A"
	, Tid__anch = 3		// EX: href="#A"
	, Tid__inet = 4		// EX: href="https://a.org/A"
	;
	private static final byte[] Bry__site = Bry_.new_a7("site/"), Bry__wiki = Bry_.new_a7("wiki/");
	private static final Btrie_slim_mgr trie = Btrie_slim_mgr.ci_a7()
	.Add_bry_byte(Bry__wiki, Tid__wiki)
	.Add_bry_byte(Bry__site, Tid__site)
	;
}

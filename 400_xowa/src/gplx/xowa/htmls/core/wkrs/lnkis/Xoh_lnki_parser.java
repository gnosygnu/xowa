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
package gplx.xowa.htmls.core.wkrs.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*; import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.htmls.core.wkrs.lnkis.anchs.*;
import gplx.xowa.wikis.ttls.*; import gplx.xowa.wikis.nss.*;
public class Xoh_lnki_parser {
	private final Xoh_anch_capt_parser capt_parser = new Xoh_anch_capt_parser();
	private final Bry_rdr rdr = new Bry_rdr();
	public int		Rng_bgn() {return rng_bgn;} private int rng_bgn;
	public int		Rng_end() {return rng_end;} private int rng_end;
	public byte		Text_type() {return text_type;} private byte text_type;
	public byte[]	Href_bry() {return href_bry;} private byte[] href_bry;
	public int		Href_bgn() {return href_bgn;} private int href_bgn;
	public int		Href_end() {return href_end;} private int href_end;
	public byte[]	Capt_bry() {return capt_bry;} private byte[] capt_bry;
	public int		Capt_bgn() {return capt_bgn;} private int capt_bgn;
	public int		Capt_end() {return capt_end;} private int capt_end;
	public Xoh_anch_href_parser Href_parser() {return href_parser;} private final Xoh_anch_href_parser href_parser = new Xoh_anch_href_parser();
	public int Parse(Xoh_hdoc_wkr wkr, Xoh_hdoc_ctx hctx, byte[] src, Html_tag_rdr tag_rdr, Html_tag anch_head, Xow_ttl_parser ttl_parser) {// <a href="/wiki/A" title="A">b</a>
		this.rng_bgn = anch_head.Src_bgn();
		rdr.Init_by_sub(tag_rdr.Rdr(), "lnki", rng_bgn, src.length);
		href_parser.Parse(rdr, hctx.App(), hctx.Wiki__ttl_parser(), anch_head);					// href='/wiki/A'	
		// get href
		this.href_bry = src;
		this.href_bgn = href_parser.Page_bgn(); this.href_end = href_parser.Page_end();
		Xoa_ttl href_ttl = null; Xow_ns href_ns = null;
		int href_ns_id = Xow_ns_.Tid__main; boolean href_cs_tid_1st = true;			
		switch (href_parser.Tid()) {
			case Xoh_anch_href_parser.Tid__anch:
			case Xoh_anch_href_parser.Tid__inet:
				break;
			default:
				href_ttl = href_parser.Page_ttl();
				href_ns = href_ttl.Ns();
				href_ns_id = href_ns.Id();
				href_cs_tid_1st = href_ttl.Ns().Case_match() == Xow_ns_case_.Tid__1st;
				this.href_bry = href_parser.Page_bry();
				this.href_bgn = 0;
				this.href_end = href_bry.length;
				break;
		}
		// get capt
		this.capt_bry = src;
		this.capt_bgn = anch_head.Src_end();										// capt starts after <a>
		Html_tag anch_tail = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__a);			// </a>
		this.capt_end = anch_tail.Src_bgn();										// get capt between "<a>" and "</a>
		this.rng_end = anch_tail.Src_end();
		boolean capt_bgn_has_ns = true;
		if (href_ns_id != Xow_ns_.Tid__main) {										// not main; try to remove template name;				
			int colon_pos = Bry_find_.Find_fwd(href_bry, Byte_ascii.Colon, href_bgn, href_end);
			byte[] ns_name = Xoa_ttl.Replace_unders(Bry_.Mid(href_bry, href_bgn, colon_pos + 1));			// EX: 11="Template talk:"
			int ns_name_len = ns_name.length;
			int ns_name_end = capt_bgn + ns_name_len;
			href_bgn += ns_name_len;												// skip ns_name for href; EX: "Help:A" -> "A"; "Help" will be saved as encoded num
			if (Bry_.Match(src, capt_bgn, ns_name_end, ns_name, 0, ns_name_len)) 	// href matches capt; EX: [[Help:A]] -> <a href='/wiki/Help:A'>Help:A</a>
				capt_bgn = ns_name_end;
			else
				capt_bgn_has_ns = false;
		}
		if (href_parser.Tid() == Xoh_anch_href_parser.Tid__anch)
			this.text_type = Xoh_anch_capt_parser.Tid__capt;
		else 
			this.text_type = capt_parser.Parse(rdr, capt_bgn_has_ns, href_cs_tid_1st, href_bry, href_bgn, href_end, src, capt_bgn, capt_end);
		int split_pos = capt_parser.Split_pos();
		switch (text_type) {
			case Xoh_anch_capt_parser.Tid__capt:	// nothing to do; href / capt already set above
				break;
			case Xoh_anch_capt_parser.Tid__href:	// redefine href to capt since both href and capt are same except for case-sensitivity / underscores; EX: [[a]], [[A b]]
			case Xoh_anch_capt_parser.Tid__href_pipe:
				this.href_bry = src;
				this.href_bgn = capt_bgn;
				this.href_end = capt_end;
				break;
			case Xoh_anch_capt_parser.Tid__href_trail:
				this.href_bry = src;
				this.href_bgn = capt_bgn;
				this.href_end = split_pos;
				this.capt_bgn = split_pos;
				break;
			case Xoh_anch_capt_parser.Tid__capt_short:
				int tmp_capt_bgn = capt_bgn, tmp_capt_end = capt_end;
				this.capt_bry = href_bry;
				this.capt_bgn = split_pos;
				this.capt_end = href_end;
				this.href_bry = src;
				this.href_bgn = tmp_capt_bgn;
				this.href_end = tmp_capt_end;
				break;
		}
		wkr.On_lnki(this);
		return rng_end;
	}
}

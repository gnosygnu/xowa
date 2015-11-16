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
import gplx.xowa.wikis.ttls.*;
public class Xoh_lnki_parser {
	private final Xoh_ttl_matcher ttl_matcher = new Xoh_ttl_matcher();
	private final Bry_rdr rdr = new Bry_rdr();
	public int Rng_bgn() {return rng_bgn;} private int rng_bgn;
	public int Rng_end() {return rng_end;} private int rng_end;
	public byte Capt_type() {return capt_type;} private byte capt_type;
	public byte[] Capt_bry() {return capt_bry;} private byte[] capt_bry;
	public byte[] Trail_bry() {return trail_bry;} private byte[] trail_bry;
	public Xoh_anch_href_parser Anch_href_parser() {return anch_href_parser;} private final Xoh_anch_href_parser anch_href_parser = new Xoh_anch_href_parser();
	public int Parse(Xoh_hdoc_wkr wkr, Xoh_hdoc_ctx hctx, byte[] src, Html_tag_rdr tag_rdr, Html_tag anch_tag, Xow_ttl_parser ttl_parser) {// <a href="/wiki/A" title="A">b</a>
		this.rng_bgn = anch_tag.Src_bgn(); this.rng_end = anch_tag.Src_end();
		rdr.Init_by_page(Bry_.Empty, src, src.length);
		anch_href_parser.Parse(rdr, hctx.Wiki__ttl_parser(), anch_tag);			// href='/wiki/A'
		Html_tag lnki_tail = tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__a);		// </a>
		int capt_bgn = rng_end; int capt_end = lnki_tail.Src_bgn();				// get capt between "<a>" and "</a>
		if (	anch_href_parser.Tid() == Xoh_anch_href_parser.Tid__anch
			&&	src[capt_bgn] == Byte_ascii.Hash)
			++capt_bgn;
		this.rng_end = lnki_tail.Src_end();
		this.capt_type = ttl_matcher.Match(rdr, ttl_parser, src, anch_href_parser.Page_bgn(), anch_href_parser.Page_end(), src, capt_bgn, capt_end);
		this.capt_bry = trail_bry = null;
		switch (capt_type) {
			case Xoh_ttl_matcher.Tid__same:
				this.capt_bry = Bry_.Mid(src, capt_bgn, capt_end);
				break;
			case Xoh_ttl_matcher.Tid__diff:
				this.capt_bry = Bry_.Mid(src, capt_bgn, capt_end);
				break;
			case Xoh_ttl_matcher.Tid__tail:
				this.capt_bry = Bry_.Mid(src, capt_bgn, ttl_matcher.Trail_bgn());
				this.trail_bry = Bry_.Mid(src, ttl_matcher.Trail_bgn(), capt_end);
				break;
			case Xoh_ttl_matcher.Tid__head:
				this.capt_bry = Bry_.Mid(src, capt_bgn, capt_end);
				this.trail_bry = Bry_.Mid(src, ttl_matcher.Trail_bgn(), anch_href_parser.Page_end());
				break;
		}
		wkr.On_lnki(this);
		return rng_end;
	}
}

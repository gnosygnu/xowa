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
package gplx.xowa.htmls.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.btries.*; import gplx.core.primitives.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.wkrs.lnkes.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.hdrs.*;
import gplx.xowa.wikis.ttls.*;
class Xoh_doc_wkr__tag implements Html_doc_wkr {
	private final Html_tag_rdr rdr = new Html_tag_rdr();
	private byte[] src; private Xoh_wkr wkr;
	private final Xoh_lnki_parse wkr__lnki = new Xoh_lnki_parse();
	private final Xoh_lnke_parse wkr__lnke = new Xoh_lnke_parse();
	private final Xoh_hdr_parse  wkr__hdr  = new Xoh_hdr_parse();
	private Xow_ttl_parser ttl_parser;
	public byte[] Hook() {return Byte_ascii.Angle_bgn_bry;}
	public Xoh_doc_wkr__tag(Xoh_wkr wkr, Xow_ttl_parser ttl_parser) {
		this.wkr = wkr; this.ttl_parser = ttl_parser;
	}
	public void Init(byte[] src, int src_bgn, int src_end) {
		this.src = src;
		rdr.Init(src, src_bgn, src_end);
	}
	public int Parse(int pos) {
		rdr.Pos_(pos);
		Html_tag cur = rdr.Tag__move_fwd_head();
		int cur_name_id = cur.Name_id();
		switch (cur_name_id) {
			case Html_tag_.Id__h2:
			case Html_tag_.Id__h3:
			case Html_tag_.Id__h4:
			case Html_tag_.Id__h5:
			case Html_tag_.Id__h6:
				int hdr_tag_bgn = cur.Src_bgn();
				Html_tag nxt = rdr.Tag__peek_fwd_head();
				if (	nxt.Name_id() == Html_tag_.Id__span
					&&	nxt.Atrs__match_pair(Html_atr_.Bry__class		, Atr__class__mw_headline)) {
					return wkr__hdr.Parse(wkr, rdr, src, cur_name_id, hdr_tag_bgn, nxt);
				}
				break;
			case Html_tag_.Id__a:
				if		(cur.Atrs__match_pair(Html_atr_.Bry__rel		, Atr__rel__nofollow))
					return wkr__lnke.Parse(wkr, rdr, src, cur);
				else if (cur.Atrs__match_pair(Html_atr_.Bry__class		, Atr__class__image)) {
					// <a href="/wiki/File:A.png" class="image"><img alt="A" src="//upload.wikimedia.org/wikipedia/commons/thumb/7/70/A.png/220px-A.svg.png" width="220" height="149" /></a>
				}
				else
					return wkr__lnki.Parse(wkr, rdr, src, cur, ttl_parser);
				break;
		}
		return cur.Src_end();
	}
	private static final byte[] 
	  Atr__class__mw_headline	= Bry_.new_a7("mw-headline")
	, Atr__class__image			= Bry_.new_a7("image")
	, Atr__rel__nofollow		= Bry_.new_a7("nofollow")
	;
}

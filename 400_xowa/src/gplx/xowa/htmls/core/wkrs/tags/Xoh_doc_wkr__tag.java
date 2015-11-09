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
package gplx.xowa.htmls.core.wkrs.tags; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.btries.*; import gplx.core.primitives.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.parsers.*;
import gplx.xowa.htmls.core.wkrs.lnkes.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.hdrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.thms.*;
import gplx.xowa.wikis.ttls.*;
public class Xoh_doc_wkr__tag implements Html_doc_wkr {
	private Xoh_hdoc_wkr hdoc_wkr; private Xow_ttl_parser ttl_parser;
	private final Html_tag_rdr tag_rdr = new Html_tag_rdr(); private byte[] src; private int src_end;
	private final Xoh_lnki_parser wkr__lnki = new Xoh_lnki_parser();
	private final Xoh_lnke_parser wkr__lnke = new Xoh_lnke_parser();
	private final Xoh_hdr_parser  wkr__hdr  = new Xoh_hdr_parser();
	private final Xoh_thm_parse wkr__img_thm = new Xoh_thm_parse();		
	private final Xoh_img_parser wkr__img_box = new Xoh_img_parser();
	public byte[] Hook() {return Byte_ascii.Angle_bgn_bry;}
	public Xoh_doc_wkr__tag Ctor(Xoh_hdoc_wkr hdoc_wkr, Xow_ttl_parser ttl_parser) {
		this.hdoc_wkr = hdoc_wkr; this.ttl_parser = ttl_parser;
		return this;
	}
	public void Init(byte[] src, int src_bgn, int src_end) {
		this.src = src; this.src_end = src_end;
		tag_rdr.Init(src, src_bgn, src_end);
	}
	public int Parse(int pos) {
		tag_rdr.Pos_(pos);
		int nxt_pos = tag_rdr.Pos() + 1; if (nxt_pos == src_end) return src_end;
		Html_tag cur = src[tag_rdr.Pos() + 1] == Byte_ascii.Slash ? tag_rdr.Tag__move_fwd_tail(Html_tag_.Id__any) : tag_rdr.Tag__move_fwd_head();
		Html_tag nxt = null;
		if (cur.Tag_is_tail()) {
			hdoc_wkr.On_txt(pos, cur.Src_end());
		}
		else {
			int cur_name_id = cur.Name_id();
			switch (cur_name_id) {
				case Html_tag_.Id__h2:
				case Html_tag_.Id__h3:
				case Html_tag_.Id__h4:
				case Html_tag_.Id__h5:
				case Html_tag_.Id__h6:
					int hdr_tag_bgn = cur.Src_bgn();
					nxt = tag_rdr.Tag__peek_fwd_head();
					if (	nxt.Name_id() == Html_tag_.Id__span
						&&	nxt.Atrs__match_pair(Html_atr_.Bry__class		, Atr__class__mw_headline)) {
						return wkr__hdr.Parse(hdoc_wkr, tag_rdr, src, cur_name_id, hdr_tag_bgn, nxt);
					}
					break;
				case Html_tag_.Id__a:
					nxt = tag_rdr.Tag__peek_fwd_head();
					if		(nxt.Name_id() == Html_tag_.Id__img)
						return wkr__img_box.Parse(hdoc_wkr, src, tag_rdr, cur);					
					else if	(cur.Atrs__match_pair(Html_atr_.Bry__rel		, Atr__rel__nofollow))
						return wkr__lnke.Parse(hdoc_wkr, tag_rdr, cur);
					else
						return wkr__lnki.Parse(hdoc_wkr, tag_rdr, src, cur, ttl_parser);
				case Html_tag_.Id__div:
					if		(cur.Atrs__cls_has(Atr__class__thumb))
						return wkr__img_thm.Parse(hdoc_wkr, tag_rdr, src, cur);
					break;
			}
			hdoc_wkr.On_txt(pos, cur.Src_end());
		}
		return cur.Src_end();
	}
	private static final byte[] 
	  Atr__class__mw_headline	= Bry_.new_a7("mw-headline")
	, Atr__class__thumb			= Bry_.new_a7("thumb")
	, Atr__rel__nofollow		= Bry_.new_a7("nofollow")
	;
}

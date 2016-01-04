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
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.htmls.core.wkrs.lnkes.*; import gplx.xowa.htmls.core.wkrs.lnkis.*; import gplx.xowa.htmls.core.wkrs.hdrs.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.thms.*; import gplx.xowa.htmls.core.wkrs.glys.*;
import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.wikis.ttls.*;
public class Xoh_tag_parser implements Gfh_doc_wkr {
	private final Xoh_hdoc_wkr hdoc_wkr;
	private final Gfh_tag_rdr tag_rdr = new Gfh_tag_rdr();
	private Xoh_hdoc_ctx hctx;
	private final Xoh_lnki_data		wkr__lnki = new Xoh_lnki_data();
	private final Xoh_thm_data		wkr__thm = new Xoh_thm_data();
	private final Xoh_gly_grp_data	wkr__gly = new Xoh_gly_grp_data();
	public byte[] Hook() {return Byte_ascii.Angle_bgn_bry;}
	public Xoh_tag_parser(Xoh_hdoc_wkr hdoc_wkr) {this.hdoc_wkr = hdoc_wkr;}
	public void Init(Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end) {
		this.hctx = hctx;
		tag_rdr.Init(hctx.Page__url(), src, src_bgn, src_end);
	}
	public int Parse(byte[] src, int src_bgn, int src_end, int pos) {
		tag_rdr.Pos_(pos);
		int nxt_pos = tag_rdr.Pos() + 1; if (nxt_pos == src_end) return src_end;
		Gfh_tag cur = src[tag_rdr.Pos() + 1] == Byte_ascii.Slash ? tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__any) : tag_rdr.Tag__move_fwd_head();
		int cur_end = cur.Src_end();
		if (cur.Tag_is_tail())
			hdoc_wkr.On_txt(pos, cur_end);
		else {
			Gfh_tag nxt = null;
			int cur_name_id = cur.Name_id();
			int rv = -1;
			switch (cur_name_id) {
				case Gfh_tag_.Id__h2: case Gfh_tag_.Id__h3: case Gfh_tag_.Id__h4: case Gfh_tag_.Id__h5: case Gfh_tag_.Id__h6:
					nxt = tag_rdr.Tag__peek_fwd_head();
					if (	nxt.Name_id() == Gfh_tag_.Id__span
						&&	nxt.Atrs__match_pair(Gfh_atr_.Bry__class		, Xoh_hdr_data.Bry__class__mw_headline)) {
						rv = Parse_by_data(hdoc_wkr, hctx, tag_rdr, src, cur, nxt, Xoh_hzip_dict_.Tid__hdr);
					}
					break;
				case Gfh_tag_.Id__a:
					nxt = tag_rdr.Tag__peek_fwd_head();
					if		(nxt.Name_id() == Gfh_tag_.Id__img)													// lnki.img; EX: [[File:A.png]]
						rv = Parse_by_data(hdoc_wkr, hctx, tag_rdr, src, cur, null, Xoh_hzip_dict_.Tid__img);
					else if	(cur.Atrs__match_pair(Gfh_atr_.Bry__rel, Xoh_lnke_dict_.Html__rel__nofollow))		// lnke; EX: [http://a.org]
						rv = Parse_by_data(hdoc_wkr, hctx, tag_rdr, src, cur, null, Xoh_hzip_dict_.Tid__lnke);
					else if	(cur.Atrs__get_by_or_empty(Xoh_img_data.Bry__atr__xowa_title).Val_dat_exists()) {}	// lnki.aud; EX: [[File:A.oga]]; ignore for now
					else {																						// lnki; EX: [[A]]
						if (wkr__lnki.Parse1(hdoc_wkr, hctx, tag_rdr, src, cur)) return wkr__lnki.Src_end();
					}
					break;
				case Gfh_tag_.Id__img:
					if		(cur.Name_id() == Gfh_tag_.Id__img)													// img; EX: <hiero>
						rv = Parse_by_data(hdoc_wkr, hctx, tag_rdr, src, cur, null, Xoh_hzip_dict_.Tid__img_bare);
					break;
				case Gfh_tag_.Id__div:
					if		(cur.Atrs__cls_has(Xoh_thm_data.Atr__class__thumb)) {
						if (wkr__thm.Parse1(hdoc_wkr, hctx, src, tag_rdr, cur)) return wkr__thm.Src_end();
					}
					else if (cur.Atrs__cls_has(Xoh_thm_data.Atr__id__xowa_media_div))
						rv = Parse_by_data(hdoc_wkr, hctx, tag_rdr, src, cur, null, Xoh_hzip_dict_.Tid__img);
					break;
				case Gfh_tag_.Id__ul:
					if		(cur.Atrs__cls_has(Xoh_gly_grp_data.Atr__cls__gallery)) {
//							rv = Parse_by_data(hdoc_wkr, hctx, tag_rdr, src, cur, null, Xoh_hzip_dict_.Tid__gly);
						if (wkr__gly.Parse1(hdoc_wkr, hctx, src, tag_rdr, cur)) return wkr__gly.Src_end();
					}
					break;
//					case Gfh_tag_.Id__td:
//						if		(wkr__xnde.Parse(hdoc_wkr, hctx, tag_rdr, src, cur)) return wkr__xnde.Src_end();
//						break;
			}
			if (rv == -1) {
				rv = cur_end;
				hdoc_wkr.On_txt(pos, rv);
			}
			return rv;
		}
		return cur_end;
	}
	private int Parse_by_data(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag cur, Gfh_tag nxt, int tid) {
		Xoh_data_itm data = hctx.Pool_mgr__data().Get_by_tid(tid);
		data.Clear();
		if (!data.Init_by_parse(hdoc_wkr, hctx, tag_rdr, src, cur, nxt)) return -1;
		if (!hdoc_wkr.Process_parse(data)) return -1;
		int rv = data.Src_end();
		data.Pool__rls();
		return rv;
	}
}

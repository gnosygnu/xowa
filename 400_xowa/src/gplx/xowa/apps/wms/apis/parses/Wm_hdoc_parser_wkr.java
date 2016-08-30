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
package gplx.xowa.apps.wms.apis.parses; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.wms.*; import gplx.xowa.apps.wms.apis.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.xowa.htmls.core.wkrs.*;
class Wm_hdoc_parser_wkr implements Gfh_doc_wkr {
	private final    Wm_hdoc_bldr hdoc_wkr;
	private final    Gfh_tag_rdr tag_rdr = Gfh_tag_rdr.New__html();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    Wm_img_src_parser img_src_data = new Wm_img_src_parser();

	public Wm_hdoc_parser_wkr(Wm_hdoc_bldr hdoc_wkr) {this.hdoc_wkr = hdoc_wkr;}
	public byte[] Hook() {return Byte_ascii.Angle_bgn_bry;}
	public void Init_by_page(Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end) {
		tag_rdr.Init(hctx.Page__url(), src, src_bgn, src_end);
		img_src_data.Init_by_page(hctx);
	}
	public int Parse(byte[] src, int src_bgn, int src_end, int pos) {
		tag_rdr.Pos_(pos);
		int nxt_pos = tag_rdr.Pos() + 1; if (nxt_pos == src_end) return src_end;
		Gfh_tag cur = src[tag_rdr.Pos() + 1] == Byte_ascii.Slash ? tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__any) : tag_rdr.Tag__move_fwd_head();
		if (cur.Tag_is_tail()) {}
		else {
			int cur_name_id = cur.Name_id();
			switch (cur_name_id) {
				case Gfh_tag_.Id__span:
                        if (cur.Atrs__cls_has(Bry__span__edit_section)) {	// remove edit-section
                            tag_rdr.Tag__move_fwd_tail(cur_name_id);
						return tag_rdr.Pos();
					}
					else {
					}
					break;
				case Gfh_tag_.Id__img: // rewrite src for XOWA, particularly to handle relative protocol; EX: "//upload.wikimedia.org"; note do not use <super> tag b/c of issues with anchors like "href=#section"
					return Parse_img_src(cur);
				default:
					break;
			}
		}
		hdoc_wkr.On_txt(cur.Src_bgn(), cur.Src_end());
		return cur.Src_end();
	}
	private int Parse_img_src(Gfh_tag img_tag) {
		// get @src and parse it
		Gfh_atr src_atr = img_tag.Atrs__get_by_or_empty(Gfh_atr_.Bry__src);
		img_src_data.Parse(src_atr.Val());

		// if error, write comment; EX: <!--error--><img ...>
		String err_msg = img_src_data.Err_msg();
		if (err_msg != null) {
			hdoc_wkr.Add_bry(Gfh_tag_.Comm_bgn);
			hdoc_wkr.Add_str(img_src_data.Err_msg());
			hdoc_wkr.Add_bry(Gfh_tag_.Comm_end);
		}

		// get img_src; use img_src_data if no error, else use original value
		byte[] img_src_val = err_msg == null ? img_src_data.To_bry() : src_atr.Val();

		// write html
		Write_img_tag(tmp_bfr, img_tag, img_src_val);
		hdoc_wkr.Add_bfr(tmp_bfr);

		return img_tag.Src_end();
	}
	public static void Write_img_tag(Bry_bfr bfr, Gfh_tag img_tag, byte[] img_src_val) {
		// now, rewrite <img> tag
		int atrs_len = img_tag.Atrs__len();
		bfr.Add(Byte_ascii.Angle_bgn_bry);
		bfr.Add(Gfh_tag_.Bry__img);
		for (int i = 0; i < atrs_len; ++i) {
			Gfh_atr atr = img_tag.Atrs__get_at(i);
			// rewrite if atr is src; EX: ' src="//upload.wikimedia.org/..."' -> ' src="xowa:/file/..."
			Gfh_atr_.Add(bfr, atr.Key(), Bry_.Eq(atr.Key(), Gfh_atr_.Bry__src) ? img_src_val : atr.Val());
		}
		bfr.Add(Byte_ascii.Angle_end_bry);
	}
	private static final    byte[] Bry__span__edit_section = Bry_.new_a7("mw-editsection");
}

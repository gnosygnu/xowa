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
package gplx.xowa.addons.wikis.pages.syncs.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*; import gplx.xowa.addons.wikis.pages.syncs.core.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.xowa.htmls.core.wkrs.*;
public class Xosync_hdoc_parser implements Gfh_doc_wkr {
	private final    Xosync_hdoc_wtr hdoc_wtr;
	private final    Gfh_tag_rdr tag_rdr = Gfh_tag_rdr.New__html();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    Xosync_img_src_parser img_src_parser = new Xosync_img_src_parser();

	public Xosync_hdoc_parser(Xosync_hdoc_wtr hdoc_wtr) {this.hdoc_wtr = hdoc_wtr;}
	public byte[] Hook() {return Byte_ascii.Angle_bgn_bry;}
	public void Init_by_page(Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end) {
		tag_rdr.Init(hctx.Page__url(), src, src_bgn, src_end);
		img_src_parser.Init_by_page(hctx);
	}
	public int Parse(byte[] src, int src_bgn, int src_end, int pos) {
		// note that entry point is at "<"
		tag_rdr.Pos_(pos);
		int nxt_pos = tag_rdr.Pos() + 1; if (nxt_pos == src_end) return src_end;

		// check if head or tail; EX: "<a>" vs "</a>"
		byte nxt_byte = src[nxt_pos];
		// skip comment; needed else comment may gobble up rest of text; see test; DATE:2016-09-10
		if (nxt_byte == Byte_ascii.Bang) {	// assume comment; EX:"<!--"
			int end_comm = Bry_find_.Move_fwd(src, Gfh_tag_.Comm_end, nxt_pos);
			if (end_comm == Bry_find_.Not_found) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "end comment not found; src=~{0}", String_.new_u8(src));
				end_comm = src_end;
			}
			return end_comm;
		}
		Gfh_tag cur = nxt_byte == Byte_ascii.Slash ? tag_rdr.Tag__move_fwd_tail(Gfh_tag_.Id__any) : tag_rdr.Tag__move_fwd_head();
		if (cur.Tag_is_tail()) {}
		else {
			int cur_name_id = cur.Name_id();
			switch (cur_name_id) {
				case Gfh_tag_.Id__span:
                        if (cur.Atrs__cls_has(Bry__span__edit_section)) {	// remove edit-section
                            tag_rdr.Tag__move_fwd_tail(cur_name_id);
						return tag_rdr.Pos();
					}
					break;
				case Gfh_tag_.Id__img: // rewrite src for XOWA; especially necessary for relative protocol; EX: "//upload.wikimedia.org"; note do not use <super> tag b/c of issues with anchors like "href=#section"
					return Parse_img_src(cur);
				default:
					break;
			}
		}
		hdoc_wtr.On_txt(cur.Src_bgn(), cur.Src_end());
		return cur.Src_end();
	}
	private int Parse_img_src(Gfh_tag img_tag) {
		// get @src and parse it
		Gfh_atr src_atr = img_tag.Atrs__get_by_or_empty(Gfh_atr_.Bry__src);
		img_src_parser.Parse(src_atr.Val());

		// if error, write comment; EX: <!--error--><img ...>
		String err_msg = img_src_parser.Err_msg();
		if (err_msg != null) {
			hdoc_wtr.Add_bry(Gfh_tag_.Comm_bgn);
			hdoc_wtr.Add_str(img_src_parser.Err_msg());
			hdoc_wtr.Add_bry(Gfh_tag_.Comm_end);
		}

		// get img_src; use img_src_parser if no error, else use original value
		byte[] img_src_val = err_msg == null ? img_src_parser.To_bry() : src_atr.Val();

		// write html
		Write_img_tag(tmp_bfr, img_tag, img_src_val, -1);
		hdoc_wtr.Add_bfr(tmp_bfr);

		return img_tag.Src_end();
	}
	public static void Write_img_tag(Bry_bfr bfr, Gfh_tag img_tag, byte[] img_src_val, int uid) {
		// rewrite <img> tag with custom img_src_val
		int atrs_len = img_tag.Atrs__len();
		bfr.Add(Byte_ascii.Angle_bgn_bry);
		bfr.Add(Gfh_tag_.Bry__img);
		if (uid != -1) {
			Gfh_atr_.Add(bfr, Gfh_atr_.Bry__id, Bry_.new_a7("xoimg_" + Int_.To_str(uid)));
		}
		for (int i = 0; i < atrs_len; ++i) {
			Gfh_atr atr = img_tag.Atrs__get_at(i);
			// if atr is src use img_src_val; EX: ' src="//upload.wikimedia.org/..."' -> ' src="xowa:/file/..."
			Gfh_atr_.Add(bfr, atr.Key(), Bry_.Eq(atr.Key(), Gfh_atr_.Bry__src) ? img_src_val : atr.Val());
		}
		bfr.Add(Byte_ascii.Angle_end_bry);
	}
	private static final    byte[] Bry__span__edit_section = Bry_.new_a7("mw-editsection");
}

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
package gplx.xowa.xtns.imageMap; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.btries.*; import gplx.xowa.html.*;
import gplx.xowa.parsers.lnkis.redlinks.*; import gplx.xowa.parsers.logs.*;
public class Xop_imageMap_xnde implements Xox_xnde {
	private boolean first = true;
	public byte[] Xtn_src() {return lnki_src;} private byte[] lnki_src;
	private Xop_root_tkn xtn_root;
	public ListAdp Shape_list() {return shape_list;} ListAdp shape_list = ListAdp_.new_(); 
	public void Xtn_parse(Xow_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);
		int content_bgn = xnde.Tag_open_end(), content_end = xnde.Tag_close_bgn();
		int nl_0_pos = Bry_finder.Find_fwd_while_not_ws(src, content_bgn, content_end);
		int cur_pos = nl_0_pos, nl_1_pos = -1;//, ws_pos_bgn = -1;
		int src_len = src.length;
		Xop_ctx imageMap_ctx = Xop_ctx.new_sub_(wiki).Tid_is_image_map_(true);
		imageMap_ctx.Para().Enabled_n_();
		Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
		while (true) {
			boolean last = cur_pos == content_end;
			if (last) nl_1_pos = cur_pos;
			if (nl_1_pos != -1 || last) {
				Object typeId_obj = TypeTrie.Match_bgn(src, nl_0_pos, nl_1_pos);
				if (typeId_obj == null) {	// flag itm					
					if (!first && nl_1_pos - nl_0_pos > 0)
						ctx.Msg_log().Add_itm_none(Xtn_imageMap_msg.Line_type_unknown, src, nl_0_pos, nl_1_pos);
				}
				else {
					byte typeId = ((Byte_obj_val)typeId_obj).Val();
					switch (typeId) {
						case TypeId_comment: break;	// NOOP
						case TypeId_desc: break;	// FUTURE: flag; TODO: desc show info icon; top-right, bottom-right, bottom-left, top-left, none
						case TypeId_default:
						case TypeId_rect:
						case TypeId_circle:
						case TypeId_poly:
//								GfoLogWkr logWkr = new GfoLogWkr();
//								logWkr.Add_args(Xtn_imageMap_msg.Coords_count_invalid, TypeName[typeId], TypeCoords[typeId], coords.length);
							Xtn_imageMap_shape shape = Xtn_imageMap_shape.parse_(typeId, src, nl_0_pos + TypeTrie.Match_pos(), nl_1_pos);
							shape_list.Add(shape);
							break;
					}
				}
				//int bgn = ws_pos_bgn = -1 ? nl_0_pos : ws
				ParseLine(ctx, imageMap_ctx, wiki, tkn_mkr, root, src, src_len, xnde, nl_0_pos, nl_1_pos);
				nl_0_pos = nl_1_pos + 1;
				nl_1_pos = -1;
			}
			if (last) break;
			byte b = src[cur_pos];
			switch (b) {
				case Byte_ascii.Space:
				case Byte_ascii.Tab:
					break;
				case Byte_ascii.NewLine:
					nl_1_pos = cur_pos;
					break;
				default:
//						ws_pos_bgn = cur_pos;;
					break;
			}
			++cur_pos;
		}
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);
		ctx.Para().Process_nl(ctx, root, src, xnde.Tag_close_end(), xnde.Tag_close_end());	// NOTE: this should create an extra stub "p" so that remaining text gets enclosed in <p>; DATE:2014-05-08
		boolean log_wkr_enabled = Log_wkr != Xop_log_basic_wkr.Null; if (log_wkr_enabled) Log_wkr.Log_end_xnde(ctx.Cur_page(), Xop_log_basic_wkr.Tid_imageMap, src, xnde);
	}	public static Xop_log_basic_wkr Log_wkr = Xop_log_basic_wkr.Null;
	public void Xtn_write(Xoa_app app, Xoh_html_wtr html_wtr, Xoh_html_wtr_ctx opts, Xop_ctx ctx, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
		html_wtr.Write_tkn(bfr, ctx, opts, xtn_root.Root_src(), xnde, Xoh_html_wtr.Sub_idx_null, xtn_root);
	}
	private void ParseLine(Xop_ctx orig_ctx, Xop_ctx image_map_ctx, Xow_wiki wiki, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, Xop_xnde_tkn xnde, int nl_0_pos, int nl_1_pos) {
		int line_len = nl_1_pos - nl_0_pos; 
		if (line_len == 0 || src[nl_0_pos + 1] == Byte_ascii.Hash) return;
		if (first) {
			byte[] lnki_raw = Bry_.Add(Xop_tkn_.Lnki_bgn, Bry_.Mid(src, nl_0_pos, nl_1_pos), Xop_tkn_.Lnki_end);
			xtn_root = tkn_mkr.Root(lnki_src);
			image_map_ctx.Wiki().Parser().Parse_text_to_wdom(xtn_root, image_map_ctx, tkn_mkr, lnki_raw, 0);
			lnki_src = xtn_root.Root_src();	// NOTE: html_wtr will write based on parsed mid (not raw)
			xtn_root.Root_src_(lnki_src);		// HACK: Xoh_html_wtr uses raw (instead of mid); put data in raw in order to conform to other xtns
			Xop_lnki_logger file_wkr = orig_ctx.Lnki().File_wkr();	// NOTE: do not do image_map_ctx.Lnki(); image_map_ctx is brand new
			Xop_lnki_tkn lnki_tkn = null;
			int subs_len = xtn_root.Subs_len();
			for (int i = 0; i < subs_len; i++) {
				Xop_tkn_itm sub_tkn = xtn_root.Subs_get(i);
				if (sub_tkn.Tkn_tid() == Xop_tkn_itm_.Tid_lnki) {
					lnki_tkn = (Xop_lnki_tkn)sub_tkn;
					break;
				}
			}
			if (lnki_tkn == null) {
				image_map_ctx.Wiki().App().Usr_dlg().Warn_many("", "", "image_map failed to find lnki; page=~{0} imageMap=~{1}", String_.new_utf8_(image_map_ctx.Cur_page().Ttl().Full_txt()), String_.new_utf8_(lnki_raw));
			}
			else {
				orig_ctx.Cur_page().Lnki_list().Add(lnki_tkn);
				if (file_wkr != null) file_wkr.Wkr_exec(orig_ctx, src, lnki_tkn, gplx.xowa.bldrs.files.Xob_lnki_src_tid.Tid_imageMap);
			}
			first = false;
		}
		else {
		}
	}
	public static final byte TypeId_default = 0, TypeId_rect = 4, TypeId_circle = 3, TypeId_poly = 5, TypeId_desc = 6, TypeId_comment = 7;
	public static Btrie_mgr TypeTrie = Btrie_slim_mgr.ci_ascii_()	// NOTE: names are not i18n'd; // NOTE:ci.ascii:MW_const.en
	.Add_obj("default"	, Byte_obj_val.new_(TypeId_default))
	.Add_obj("rect"		, Byte_obj_val.new_(TypeId_rect))
	.Add_obj("circle"	, Byte_obj_val.new_(TypeId_circle))
	.Add_obj("poly"		, Byte_obj_val.new_(TypeId_poly))
	.Add_obj("desc"		, Byte_obj_val.new_(TypeId_desc))
	.Add_obj("#"		, Byte_obj_val.new_(TypeId_comment))
	; 
}
class Xtn_imageMap_shape {
	public byte TypeId() {return typeId;} private byte typeId;
	public int[] Coord_ary() {return coord_ary;} private int[] coord_ary = null;
	public Xop_lnki_tkn Lnki_tkn() {return lnki_tkn;} private Xop_lnki_tkn lnki_tkn = null;
	public static Xtn_imageMap_shape parse_(byte typeId, byte[] src, int bgn, int end) {
		Xtn_imageMap_shape rv = new Xtn_imageMap_shape();
		rv.typeId = typeId;
		return rv;
	}
}
class Xtn_imageMap_msg {
	public static final Gfo_msg_grp Nde = Gfo_msg_grp_.new_(Xoa_app_.Nde, "image_map");
	public static final Gfo_msg_itm
		  Line_type_unknown		= Gfo_msg_itm_.new_warn_(Nde, "line_type_unknown", "Line type is unknown")
		, Coords_count_invalid	= Gfo_msg_itm_.new_warn_(Nde, "coords_count_invalid", "Coordinate counts are invalid for shape: shape=~{0} expd=~{1} actl=~{2}")
		;
}

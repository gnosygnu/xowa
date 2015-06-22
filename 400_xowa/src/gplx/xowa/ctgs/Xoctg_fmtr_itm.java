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
package gplx.xowa.ctgs; import gplx.*; import gplx.xowa.*;
import gplx.xowa.html.*; import gplx.xowa.html.lnkis.*;
import gplx.xowa.users.history.*;
abstract class Xoctg_fmtr_itm_base implements Xoctg_fmtr_itm {
	public void Init_from_all(Xowe_wiki wiki, Xol_lang lang, Xoctg_view_ctg ctg, Xoctg_fmtr_all mgr, Xoctg_view_grp itms_list, int itms_list_len) {
		this.wiki = wiki; this.lang = lang; this.ctg = ctg; this.list = itms_list; this.len = itms_list_len; this.msg_mgr = wiki.Msg_mgr();
		href_parser = wiki.Appe().Href_parser();
		html_itm = mgr.Html_itm();
		html_itm_missing = mgr.Html_itm_missing();
		history_mgr = wiki.Appe().Usere().History_mgr();
	}	protected Xowe_wiki wiki; Xol_lang lang; Xoctg_view_ctg ctg; protected int len; protected Xoh_href_parser href_parser; protected Bry_fmtr html_itm, html_itm_missing; protected Xoctg_view_grp list; protected Xow_msg_mgr msg_mgr;
	Xou_history_mgr history_mgr;
	public void Init_from_grp(byte[] ttl_char_0, int col_bgn) {this.ttl_char_0 = ttl_char_0; this.col_bgn = col_bgn;} private byte[] ttl_char_0; int col_bgn; int col_end;
	public int Col_idx() {return col_idx;}
	public void Col_idx_(int col_idx, int col_bgn) {
		this.col_idx = col_idx;
		this.col_bgn = col_bgn;
		col_end = col_bgn + Xoctg_fmtr_grp.Calc_col_max(Xoctg_html_mgr.Cols_max, len, col_idx);
	} int col_idx;
	public int Grp_end_idx() {return grp_end_idx;} private int grp_end_idx;
	public boolean Grp_end_at_col() {return grp_end_at_col;} private boolean grp_end_at_col;
	@gplx.Virtual public void XferAry(Bry_bfr bfr, int idx) {
		for (int i = col_bgn; i < len; i++) {
			if (i == col_end) {
				grp_end_idx = i;
				grp_end_at_col = true;
				return;				
			}
			Xoctg_view_itm itm = list.Itms()[i];
			Xoa_ttl ttl = itm.Ttl();
			byte[] itm_sortkey = itm.Sortkey();
			byte[] ttl_bry = ttl.Page_txt();
			if (!Bry_.Has_at_bgn(itm_sortkey, ttl_char_0, 0, itm_sortkey.length)) {
				grp_end_idx = i;
				grp_end_at_col = i == col_end;
				return;
			}
			Bld_html(bfr, wiki, itm, ttl, ttl_bry, href_parser, html_itm);
		}
		grp_end_idx = len;
		grp_end_at_col = true;
	}
	@gplx.Virtual public void Bld_html(Bry_bfr bfr, Xowe_wiki wiki, Xoctg_view_itm itm, Xoa_ttl ttl, byte[] ttl_page, Xoh_href_parser href_parser, Bry_fmtr html_itm) {
		byte[] itm_href = href_parser.Build_to_bry(wiki, ttl);
		byte[] itm_full_ttl = ttl.Full_txt();// NOTE: ttl.Full_txt() to get full ns; EX: Template:A instead of just "A"
		byte[] itm_atr_cls = Xoh_lnki_wtr.Lnki_cls_visited(history_mgr, wiki.Domain_bry(), ttl.Page_txt());	// NOTE: must be ttl.Page_txt() in order to match Xou_history_mgr.Add
		Bry_fmtr fmtr = itm.Id_missing() ? html_itm_missing : html_itm;
		fmtr.Bld_bfr_many(bfr, itm_href, itm_full_ttl, itm_full_ttl, itm.Id(), itm_atr_cls);
	}
}
class Xoctg_fmtr_itm_page extends Xoctg_fmtr_itm_base {
	public static final Xoctg_fmtr_itm_page _ = new Xoctg_fmtr_itm_page(); Xoctg_fmtr_itm_page() {}
}
class Xoctg_fmtr_itm_file extends Xoctg_fmtr_itm_base {
//		public override void XferAry(Bry_bfr bfr, int idx) {
//			html_itm = wiki.Html_mgr().Gallery_xtn_mgr().Html_gallery_itm_img();
//			for (int i = list.Bgn(); i < len; i++) {
//				Xoctg_view_itm itm = list.Itms()[i];
//				Xoa_ttl ttl = itm.Ttl();
//				byte[] ttl_page = ttl.Page_txt();
//				byte[] itm_href = href_parser.Build_to_bry(ttl, wiki);
//				html_itm.Bld_bfr_many(bfr
//					, 155	// "itm_box_width"
//					, 155	// "itm_div_width"
//					, 15	// "itm_margin"
//					, -1	// "img_id"
//					, ttl_page // "img_ttl"
//					, itm_href // "img_href"
//					, Bry_.Empty // "html_src"
//					, -1 // "img_width"
//					, -1 // "img_height"
//					, ttl_page // "itm_caption"
//					);
//			}
//		}
	public static final Xoctg_fmtr_itm_file _ = new Xoctg_fmtr_itm_file(); Xoctg_fmtr_itm_file() {}
}
class Xoctg_fmtr_itm_subc extends Xoctg_fmtr_itm_base {
	@Override public void Bld_html(Bry_bfr bfr, Xowe_wiki wiki, Xoctg_view_itm itm, Xoa_ttl ttl, byte[] ttl_page, Xoh_href_parser href_parser, Bry_fmtr html_itm) {
		byte[] itm_href = href_parser.Build_to_bry(wiki, ttl);
		int sub_ctgs = itm.Subs_ctgs();
		int sub_pages = itm.Subs_pages();
		int sub_files = itm.Subs_files();
		byte[] contains_title = msg_mgr.Val_by_id_args(Xol_msg_itm_.Id_ctgtree_subc_counts, sub_ctgs, sub_pages, sub_files);
		byte[] contains_text = Bld_contains_text(sub_ctgs, sub_pages, sub_files);
		html_itm.Bld_bfr_many(bfr, ttl.Page_db(), ttl_page, itm_href, ttl_page, contains_title, contains_text);
	}
	byte[] Bld_contains_text(int sub_ctgs, int sub_pages, int sub_files) {
		if (sub_ctgs == 0 && sub_pages == 0 && sub_files == 0) return Bry_.Empty;
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b128();
		bfr.Add_byte(Byte_ascii.Paren_bgn);
		Bld_contains_text_itm(bfr, Xol_msg_itm_.Id_ctgtree_subc_counts_ctg, sub_ctgs);
		Bld_contains_text_itm(bfr, Xol_msg_itm_.Id_ctgtree_subc_counts_page, sub_pages);
		Bld_contains_text_itm(bfr, Xol_msg_itm_.Id_ctgtree_subc_counts_file, sub_files);
		bfr.Add_byte(Byte_ascii.Paren_end);
		return bfr.To_bry_and_rls();
	}
	private void Bld_contains_text_itm(Bry_bfr bfr, int msg_id, int val) {
		if (val == 0) return;
		if (bfr.Len() > 1) bfr.Add(Bld_contains_text_itm_dlm);	// NOTE: 1 b/c Paren_bgn is always added
		bfr.Add(msg_mgr.Val_by_id_args(msg_id, val));
	}	static final byte[] Bld_contains_text_itm_dlm = Bry_.new_a7(", "); 
	public static final Xoctg_fmtr_itm_subc _ = new Xoctg_fmtr_itm_subc(); Xoctg_fmtr_itm_subc() {}
}

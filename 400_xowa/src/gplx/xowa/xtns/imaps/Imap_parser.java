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
package gplx.xowa.xtns.imaps; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.btries.*; import gplx.xowa.parsers.lnkis.redlinks.*;
class Imap_parser {
	private Imap_xtn_mgr xtn_mgr; private Xoa_url page_url; private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_._;
	private byte[] imap_img_src;
	private Imap_itm_img imap_img;
	private Imap_itm_dflt imap_dflt;
	private Imap_itm_desc imap_desc;
	private ListAdp shapes = ListAdp_.new_(), pts = ListAdp_.new_(), errs = ListAdp_.new_();
	private byte[] src;
	private int itm_idx; private int itm_bgn, itm_end;
	private Xoa_app app; private Xow_wiki wiki; private Xop_ctx wiki_ctx, imap_ctx; private Xop_root_tkn imap_root;
	public Imap_parser(Imap_xtn_mgr xtn_mgr) {this.xtn_mgr = xtn_mgr;}
	public void Init(Xow_wiki wiki, Xoa_url page_url, Gfo_usr_dlg usr_dlg) {
		this.app = wiki.App(); this.wiki = wiki; this.page_url = page_url; this.usr_dlg = usr_dlg;
		this.wiki_ctx = wiki.Ctx();
		if (imap_ctx == null) {
			imap_ctx = Xop_ctx.new_(wiki);
			imap_root = app.Tkn_mkr().Root(Bry_.Empty);
		}
	}
	public void Clear() {
		this.itm_idx = 0;
		imap_img = null; imap_img_src = null; imap_desc = null; imap_dflt = null;
		shapes.Clear(); pts.Clear(); errs.Clear();
	}
	public Imap_map Parse(Xow_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		Imap_map rv = new Imap_map();
		Init(wiki, ctx.Cur_page().Url(), wiki.App().Usr_dlg());
		this.Parse(rv, ctx.Cur_page().Html_data().Imap_id_next(), src, xnde.Tag_open_end(), xnde.Tag_close_bgn());			
		return rv;
	}
	public void Parse(Imap_map rv, int imap_id, byte[] src, int src_bgn, int src_end) {
		this.Clear();
		this.src = src;
		itm_bgn = src_bgn; itm_end = src_bgn - 1;
		while (true) {
			if (itm_end == src_end) break;
			itm_bgn = Bry_finder.Trim_fwd_space_tab(src, itm_end + 1, src_end);					// trim ws at start, and look for first char
			if (itm_bgn == src_end) break;														// line is entirely ws and terminated by eos; EX: "\n  EOS"
			itm_end = Bry_finder.Find_fwd_until(src, itm_bgn, src_end, Byte_ascii.NewLine);		// look for \n
			if (itm_end == Bry_finder.Not_found) itm_end = src_end;								// no \n; make EOS = \n
			itm_end = Bry_finder.Trim_bwd_space_tab(src, itm_end, itm_bgn);						// trim any ws at end
			if (itm_end - itm_bgn == 0) continue;												// line is entirely ws; continue;
			byte b = src[itm_bgn];
			if (b == Byte_ascii.Hash) {
				Parse_comment(itm_bgn, itm_end);
				continue;
			}
			try {
				if (itm_idx == 0)
					Parse_img(rv, itm_bgn, itm_end);
				else {
					Object tid_obj = tid_trie.Match_bgn_w_byte(b, src, itm_bgn, itm_end);
					byte tid_val = tid_obj == null ? Imap_itm_.Tid_invalid : ((Byte_obj_val)tid_obj).Val();
					int tid_end_pos = tid_trie.Match_pos();
					switch (tid_val) {
						case Imap_itm_.Tid_desc:			Parse_desc(tid_end_pos, itm_end); break;
						case Imap_itm_.Tid_dflt:			Parse_dflt(tid_end_pos, itm_end); break;
						case Imap_itm_.Tid_shape_rect:		Parse_shape(tid_val, tid_end_pos, itm_bgn, itm_end, 4); break;
						case Imap_itm_.Tid_shape_poly:		Parse_shape(tid_val, tid_end_pos, itm_bgn, itm_end, Reqd_poly); break;
						case Imap_itm_.Tid_shape_circle:	Parse_shape(tid_val, tid_end_pos, itm_bgn, itm_end, 3); break;
						default:
						case Imap_itm_.Tid_invalid:			Parse_invalid(itm_bgn, itm_end); break;
					}
				}
			} catch (Exception e) {usr_dlg.Warn_many("", "", "imap.parse:skipping line; page=~{0} line=~{1} err=~{2}", page_url.Xto_full_str_safe(), Bry_.Mid_by_len_safe(src, itm_bgn, itm_end), Err_.Message_gplx(e));}
			++itm_idx;
		}
		rv.Init(xtn_mgr, imap_id, imap_img_src, imap_img, imap_dflt, imap_desc, (Imap_itm_shape[])shapes.XtoAryAndClear(Imap_itm_shape.class), (Imap_err[])errs.XtoAryAndClear(Imap_err.class));
	}
	private void Parse_comment(int itm_bgn, int itm_end) {}	// noop comments; EX: "# comment\n"
	private void Parse_invalid(int itm_bgn, int itm_end) {usr_dlg.Warn_many("", "", "imap has invalid line: page=~{0} line=~{1}", page_url.Xto_full_str_safe(), String_.new_utf8_(src, itm_bgn, itm_end));}
	private boolean Parse_desc(int itm_bgn, int itm_end) {
		xtn_mgr.Desc_assert();
		Btrie_slim_mgr trie = xtn_mgr.Desc_trie();
		byte tid_desc = Imap_desc_tid.parse_(trie, src, Bry_finder.Trim_fwd_space_tab(src, itm_bgn, itm_end), Bry_finder.Trim_bwd_space_tab(src, itm_bgn, itm_end));
		switch (tid_desc) {
			case Imap_desc_tid.Tid_null: return Add_err(Bool_.N, itm_bgn, itm_end, "imagemap_invalid_coord");
			case Imap_desc_tid.Tid_none: return true;
		}
		if (imap_img == null || imap_img.Img_link().Lnki_type() == Xop_lnki_type.Id_thumb) return true;	// thumbs don't get desc
		imap_desc = new Imap_itm_desc(tid_desc);
		return true;
	}
	private void Parse_dflt(int itm_bgn, int itm_end) {
		imap_dflt = new Imap_itm_dflt();
		Init_link_owner(imap_dflt, src, itm_bgn, itm_end);
	}
	private boolean Parse_shape(byte shape_tid, int tid_end_pos, int itm_bgn, int itm_end, int reqd_pts) {			
		int num_bgn = -1; // differs from MW parser which looks for link via regx, and then chops off rest; regx is difficult due to lnke; doing opposite approach which is eat numbers until something else
		int pos = Bry_finder.Trim_fwd_space_tab(src, tid_end_pos, itm_end);
		boolean reading_numbers = true;
		while (reading_numbers) {
			boolean last = pos == itm_end;
			byte b = last ? Byte_ascii.Space : src[pos];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
				case Byte_ascii.Dash: case Byte_ascii.Dot:
					if (num_bgn == -1)
						num_bgn = pos;
					++pos;
					break;
				default:
					int new_pos = Parse_shape_num(shape_tid, b, pos, num_bgn, pos, itm_end);
					if (new_pos == -1)	return Add_err(Bool_.Y, itm_bgn, itm_end, "imagemap_invalid_coord");
					if (new_pos == pos)
						reading_numbers = false;
					else
						pos = Bry_finder.Trim_fwd_space_tab(src, new_pos, itm_end);
					num_bgn = -1;
					break;
			}
			if (last) reading_numbers = false;
		}
		int pts_len = pts.Count();
		if (reqd_pts == Reqd_poly) {
			if		(pts_len == 0)			return Add_err(Bool_.Y, itm_bgn, itm_end, "imagemap_missing_coord");
			else if (pts_len % 2 != 0)		return Add_err(Bool_.Y, itm_bgn, itm_end, "imagemap_poly_odd");
		}
		else {
			if		(pts_len < reqd_pts)	return Add_err(Bool_.Y, itm_bgn, itm_end, "imagemap_missing_coord");
			else if (pts_len > reqd_pts)	pts.Del_range(reqd_pts, pts_len - 1);	// NOTE: MW allows more points, but doesn't show them; EX: rect 1 2 3 4 5 -> rect 1 2 3 4; PAGE:en.w:Kilauea DATE:2014-07-28
		}
		pos = Bry_finder.Trim_fwd_space_tab(src, pos, itm_end);
		Imap_itm_shape shape_itm = new Imap_itm_shape(shape_tid, (Double_obj_val[])pts.XtoAryAndClear(Double_obj_val.class));
		Init_link_owner(shape_itm, src, pos, itm_end);
		shapes.Add(shape_itm);
		return true;
	}
	private boolean Add_err(boolean clear_pts, int bgn, int end, String err_key) {
		usr_dlg.Warn_many("", "", err_key + ": page=~{0} line=~{1}", page_url.Xto_full_str_safe(), String_.new_utf8_(src, bgn, end));
		errs.Add(new Imap_err(itm_idx, err_key));
		if (clear_pts) pts.Clear();
		return false;
	}
	private void Init_link_owner(Imap_link_owner link_owner, byte[] src, int bgn, int end) {
		byte[] link_tkn_src = Bry_.Mid(src, bgn, end);
		Xop_tkn_itm link_tkn = Parse_link(link_tkn_src);
		Imap_link_owner_.Init(link_owner, app, wiki, link_tkn_src, link_tkn);
	}
	private Xop_tkn_itm Parse_link(byte[] raw) {
		imap_root.Clear();
		imap_ctx.Clear();			
		wiki.Parser().Parse_text_to_wdom(imap_root, imap_ctx, wiki.App().Tkn_mkr(), raw, 0);
		int subs_len = imap_root.Subs_len();
		for (int i = 0; i < subs_len; ++i) {
			Xop_tkn_itm sub = imap_root.Subs_get(i);
			switch (sub.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_lnki:
				case Xop_tkn_itm_.Tid_lnke:
					return sub;
			}
		}
		return null;
	}
	private int Parse_shape_num(byte shape_tid, byte b, int pos, int num_bgn, int num_end, int itm_end) {
		double num = 0;
		if (num_bgn == -1) {								// 1st char is non-numeric; EX: "poly a"
			if (	shape_tid == Imap_itm_.Tid_shape_poly	// poly code in ImageMap_body.php accepts invalid words and converts to 0; EX:"poly1"; PAGE:uk.w:Стратосфера; DATE:2014-07-26
				&&	b != Byte_ascii.Brack_bgn				// skip logic if "[" which may be beginning of lnki / lnke
				) {
				num_end = Bry_finder.Find_fwd_until_space_or_tab(src, pos, itm_end);	// gobble up rest of word and search forward for space / tab to 
				if (num_end == Bry_finder.Not_found) return -1;							// space / tab not found; return -1 (fail)
				num = 0;
			}
			else
				return num_end;
		}
		else
			num = Bry_.XtoDoubleByPosOr(src, num_bgn, num_end, Double_.NaN);
		if (Double_.IsNaN(num)) { 
			if (shape_tid == Imap_itm_.Tid_shape_poly)		// poly code in ImageMap_body.php accepts invalid words and converts to 0; EX:"poly 1a"
				num = 0;
			else
				return -1;	// invalid number; EX: "1.2.3"
		}
		pts.Add(Double_obj_val.new_(num));
		return Bry_finder.Trim_fwd_space_tab(src, num_end, itm_end);
	}
	private void Parse_img(Imap_map imap, int itm_bgn, int itm_end) {
		int pos = Bry_finder.Trim_fwd_space_tab(src, itm_bgn, itm_end);
		imap_img_src = Bry_.Add(Xop_tkn_.Lnki_bgn, Bry_.Mid(src, pos, itm_end), Xop_tkn_.Lnki_end);
		Xop_lnki_tkn lnki_tkn = (Xop_lnki_tkn)Parse_link(imap_img_src);
		imap_img_src = imap_root.Data_mid();	// NOTE: need to re-set src to pick up templates; EX: <imagemap>File:A.png|thumb|{{Test_template}}\n</imagemap>; PAGE:en.w:Kilauea; DATE:2014-07-27
		Xop_lnki_logger file_wkr = wiki_ctx.Lnki().File_wkr();	// NOTE: do not do imap_ctx.Lnki(); imap_ctx is brand new
		if (lnki_tkn == null)
			imap_ctx.Wiki().App().Usr_dlg().Warn_many("", "", "image_map failed to find lnki; page=~{0} imageMap=~{1}", String_.new_utf8_(imap_ctx.Cur_page().Ttl().Full_txt()), String_.new_utf8_(imap_img_src));
		else {
			imap_img = new Imap_itm_img(lnki_tkn);
			lnki_tkn.Lnki_file_wkr_(imap);
			wiki_ctx.Cur_page().Lnki_list().Add(lnki_tkn);
			if (file_wkr != null) file_wkr.Wkr_exec(wiki_ctx, src, lnki_tkn, gplx.xowa.bldrs.files.Xob_lnki_src_tid.Tid_imageMap);
		}
	}
	private static Btrie_slim_mgr tid_trie = Btrie_slim_mgr.ci_ascii_()	// NOTE: names are not i18n'd; // NOTE:ci.ascii:MW_const.en
	.Add_str_byte("desc"						, Imap_itm_.Tid_desc)
	.Add_str_byte("#"							, Imap_itm_.Tid_comment)
	.Add_bry_bval(Imap_itm_.Key_dflt			, Imap_itm_.Tid_dflt)
	.Add_bry_bval(Imap_itm_.Key_shape_rect		, Imap_itm_.Tid_shape_rect)
	.Add_bry_bval(Imap_itm_.Key_shape_circle	, Imap_itm_.Tid_shape_circle)
	.Add_bry_bval(Imap_itm_.Key_shape_poly		, Imap_itm_.Tid_shape_poly)
	; 
	private static final int Reqd_poly = -1;
}

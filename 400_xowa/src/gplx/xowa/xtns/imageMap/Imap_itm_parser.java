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
import gplx.core.btries.*;
class Imap_itm_parser {
	private Xoa_url page_url; private Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_._;
	private ListAdp itms = ListAdp_.new_();
	private ListAdp pts = ListAdp_.new_();
	private ListAdp errs = ListAdp_.new_();
	private byte[] src; // private int src_bgn, src_end;
	private int itm_idx; private int itm_bgn, itm_end;
	private Xoa_app app; private Xow_wiki wiki; private Xop_ctx imap_ctx; private Xop_root_tkn imap_root;
	public void Init(Xow_wiki wiki, Xoa_url page_url, Gfo_usr_dlg usr_dlg) {
		this.app = wiki.App(); this.wiki = wiki; this.page_url = page_url; this.usr_dlg = usr_dlg;
		if (imap_ctx == null) {
			imap_ctx = Xop_ctx.new_(wiki);
			imap_root = app.Tkn_mkr().Root(Bry_.Empty);
		}
	}
	public void Clear() {
		this.itm_idx = 0; itms.Clear();
		pts.Clear();
		errs.Clear();
	}
	public ListAdp Errs() {return errs;}
	public Imap_itm[] Parse(byte[] src, int src_bgn, int src_end) {
		this.Clear();
		this.src = src; // this.src_bgn = src_bgn; this.src_end = src_end;
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
			if (itm_idx == 0)
				Parse_img(itm_bgn, itm_end);
			else {
				Object tid_obj = tid_trie.Match_bgn_w_byte(b, src, itm_bgn, itm_end);
				byte tid_val = tid_obj == null ? Imap_itm_.Tid_invalid : ((Byte_obj_val)tid_obj).Val();
				int tid_end_pos = tid_trie.Match_pos();
				switch (tid_val) {
					case Imap_itm_.Tid_desc:			Parse_desc(itm_bgn, itm_end); break;
					case Imap_itm_.Tid_shape_dflt:		Parse_shape(tid_val, tid_end_pos, itm_end, Reqd_dflt); break;
					case Imap_itm_.Tid_shape_rect:		Parse_shape(tid_val, tid_end_pos, itm_end, 4); break;
					case Imap_itm_.Tid_shape_poly:		Parse_shape(tid_val, tid_end_pos, itm_end, Reqd_poly); break;
					case Imap_itm_.Tid_shape_circle:	Parse_shape(tid_val, tid_end_pos, itm_end, 3); break;
					default:
					case Imap_itm_.Tid_invalid:			Parse_invalid(itm_bgn, itm_end); break;
				}
			}
			++itm_idx;
		}
		return (Imap_itm[])itms.XtoAryAndClear(Imap_itm.class);
	}
	private void Parse_comment(int itm_bgn, int itm_end) {}	// noop comments; EX: "# comment\n"
	private void Parse_invalid(int itm_bgn, int itm_end) {usr_dlg.Warn_many("", "", "imap has invalid line: page=~{0} line=~{1}", page_url.Xto_full_str_safe(), String_.new_utf8_(src, itm_bgn, itm_end));}
	private void Parse_desc(int itm_bgn, int itm_end) {itms.Add(new Imap_itm_desc(itm_idx, itm_bgn, itm_end));}
	private void Parse_shape(byte shape_tid, int itm_bgn, int itm_end, int reqd_pts) {			
		int num_bgn = -1; // differs from MW parser which looks for link via regx, and then chops off rest; regx is difficult due to lnke; doing opposite approach which is eat numbers until something else
		int pos = Bry_finder.Trim_fwd_space_tab(src, itm_bgn, itm_end);
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
					int new_pos = Parse_shape_num(num_bgn, pos, itm_end);
					if (new_pos == -1) {Add_err("imagemap_invalid_coord", itm_bgn, itm_end); return;}
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
		switch (reqd_pts) {
			case Reqd_poly: {
				if		(pts_len == 0)		{Add_err("imagemap_missing_coord", itm_bgn, itm_end); return;}
				else if (pts_len % 2 != 0)	{Add_err("imagemap_poly_odd", itm_bgn, itm_end); return;}
				break;
			}
			case Reqd_dflt: {
				pts.Clear();	// dflt should have 0 points; if any defined, ignore them; clearing list for purpose of test
				break;
			}
			default: {
				if (reqd_pts != pts_len)	{Add_err("imagemap_missing_coord", itm_bgn, itm_end); return;}
				break;
			}
		}
		pos = Bry_finder.Trim_fwd_space_tab(src, pos, itm_end);
		Imap_itm_shape shape_itm = new Imap_itm_shape(itm_idx, itm_bgn, itm_end, shape_tid, (Double_obj_val[])pts.XtoAryAndClear(Double_obj_val.class));
		Xop_tkn_itm link_tkn = Parse_link(pos, itm_end);
		shape_itm.Shape_link_(app, wiki, src, link_tkn);
		itms.Add(shape_itm);
	}
	private void Add_err(String err_key, int bgn, int end) {
		usr_dlg.Warn_many("", "", err_key + ": page=~{0} line=~{1}", page_url.Xto_full_str_safe(), String_.new_utf8_(src, bgn, end));
		errs.Add(new Imap_itm_err(itm_idx, err_key));
	}
	private Xop_tkn_itm Parse_link(int link_bgn, int link_end) {
		imap_root.Clear();
		imap_ctx.Clear();
		wiki.Parser().Parse_to_src_end(imap_root, imap_ctx, wiki.App().Tkn_mkr(), src, wiki.Parser().Wtxt_trie(), link_bgn, link_end);
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
	private int Parse_shape_num(int num_bgn, int num_end, int itm_end) {
		if (num_bgn == -1) return num_end;
		double num = Bry_.XtoDoubleByPosOr(src, num_bgn, num_end, Double_.NaN);
		if (Double_.IsNaN(num)) return -1;	// invalid number; EX: "1.2.3"
		pts.Add(Double_obj_val.new_(num));
		return Bry_finder.Trim_fwd_space_tab(src, num_end, itm_end);
	}

	private void Parse_img(int itm_bgn, int itm_end) {
		int pos = Bry_finder.Trim_fwd_space_tab(src, itm_bgn, itm_end);
		Xop_tkn_itm link_tkn = Parse_link(pos, itm_end);
		Imap_itm_img itm = new Imap_itm_img(itm_idx, itm_bgn, itm_end, link_tkn);
		itms.Add(itm);
	}
	private static Btrie_slim_mgr tid_trie = Btrie_slim_mgr.ci_ascii_()	// NOTE: names are not i18n'd; // NOTE:ci.ascii:MW_const.en
	.Add_str_byte("desc"						, Imap_itm_.Tid_desc)
	.Add_str_byte("#"							, Imap_itm_.Tid_comment)
	.Add_bry_bval(Imap_itm_.Tid_name_default	, Imap_itm_.Tid_shape_dflt)
	.Add_bry_bval(Imap_itm_.Tid_name_rect		, Imap_itm_.Tid_shape_rect)
	.Add_bry_bval(Imap_itm_.Tid_name_circle		, Imap_itm_.Tid_shape_circle)
	.Add_bry_bval(Imap_itm_.Tid_name_poly		, Imap_itm_.Tid_shape_poly)
	; 
	private static final int Reqd_poly = -1, Reqd_dflt = -2;
}
class Imap_itm_err {
	public Imap_itm_err(int itm_idx, String err_key) {this.itm_idx = itm_idx; this.err_key = err_key;}
	public int Itm_idx() {return itm_idx;} private int itm_idx;
	public String Err_key() {return err_key;} private String err_key;
}

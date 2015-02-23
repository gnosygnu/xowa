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
package gplx.xowa.html.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.core.primitives.*; import gplx.core.brys.*; import gplx.html.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.hdumps.srls.*;
public class Xow_hzip_itm__anchor {
	private Xow_hzip_mgr hzip_mgr; private Xow_ttl_parser ttl_parser; private Byte_obj_ref xtid_ref = Byte_obj_ref.zero_();
	private Bry_rdr bry_rdr = new Bry_rdr();
	public Xow_hzip_itm__anchor(Xow_hzip_mgr hzip_mgr, Xow_ttl_parser ttl_parser) {this.hzip_mgr = hzip_mgr; this.ttl_parser = ttl_parser;}
	public int Save_a_rhs(Bry_bfr bfr, Xow_hzip_stats stats, byte[] src, int src_len, int bgn, int pos) {
		bfr.Add(Xow_hzip_dict.Bry_a_rhs);
		stats.A_rhs_add();
		return pos;
	}
	public int Save(Bry_bfr bfr, Xow_hzip_stats stats, byte[] src, int src_len, int bgn, int pos) {
		int xtid_end = Xow_hzip_xtid.Find_xtid(hzip_mgr, src, src_len, bgn, pos, xtid_ref); if (xtid_end == Xow_hzip_mgr.Unhandled) return Xow_hzip_mgr.Unhandled;
		byte xtid_val = xtid_ref.Val();
		switch (xtid_val) {
			case Xow_hzip_dict.Tid_lnki_text_n:
			case Xow_hzip_dict.Tid_lnki_text_y:			return Save_lnki(bfr, stats, src, src_len, bgn, xtid_end, xtid_val == Xow_hzip_dict.Tid_lnki_text_y);
			case Xow_hzip_dict.Tid_lnke_txt:
			case Xow_hzip_dict.Tid_lnke_brk_text_n:
			case Xow_hzip_dict.Tid_lnke_brk_text_y:		return Save_lnke(bfr, stats, src, src_len, bgn, xtid_end, xtid_val);
			case Xow_hzip_dict.Tid_img_full:			return Save_img_full(bfr, stats, src, src_len, bgn, xtid_end);
			default:									return hzip_mgr.Warn_by_pos("a.xtid_unknown", bgn, pos);
		}
	}
	private static int[] Save_img_full_pow = new int[] {0, 1, 2};
	private int Save_img_full(Bry_bfr bfr, Xow_hzip_stats stats, byte[] src, int src_len, int bgn, int pos) {
		bfr.Add(Xow_hzip_dict.Bry_img_full);
		int xatrs_bgn = Bry_finder.Move_fwd(src, Find_img_xatrs, pos, src_len);				if (xatrs_bgn == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.img_xatrs_missing", bgn, pos);
		bry_rdr.Src_(src).Pos_(xatrs_bgn);
		int a_cls		= bry_rdr.Read_int_to_pipe();
		int a_rel		= bry_rdr.Read_int_to_pipe();
		int img_rel		= bry_rdr.Read_int_to_pipe();
		byte meta		= (byte)Bit_.Shift_lhs_to_int(Save_img_full_pow, a_cls, a_rel, img_rel);
		bfr.Add_byte(meta);														// meta
		Hpg_srl_itm_.Save_bin_int_abrv(bfr, bry_rdr.Read_int_to_pipe());		// uid
		bfr.Add(bry_rdr.Read_bry_to_pipe()).Add_byte_pipe();					// img_cls_other
		bfr.Add(bry_rdr.Read_bry_to_apos());									// alt
		bfr.Add_byte(Xow_hzip_dict.Escape);
		return bry_rdr.Pos() + 2;												// +2=/>
	}
	public int Save_lnki(Bry_bfr bfr, Xow_hzip_stats stats, byte[] src, int src_len, int bgn, int pos, boolean caption) {
		int ttl_bgn = Bry_finder.Find_fwd(src, Find_href_wiki_bry, pos, src_len);			if (ttl_bgn == Bry_finder.Not_found) return Xow_hzip_mgr.Unhandled;//hzip_mgr.Warn_by_pos_add_dflt("a.ttl_bgn_missing", bgn, pos);
		ttl_bgn += Find_href_wiki_len;
		int ttl_end = Bry_finder.Find_fwd(src, Byte_ascii.Quote, ttl_bgn , src_len);		if (ttl_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.ttl_end_missing", bgn, ttl_bgn);
		Xoa_ttl ttl = ttl_parser.Ttl_parse(Bry_.Mid(src, ttl_bgn, ttl_end));				if (ttl == null) return hzip_mgr.Warn_by_pos("a.ttl_invalid", ttl_bgn, ttl_end);
		int a_lhs_end = Bry_finder.Find_fwd(src, Byte_ascii.Gt, ttl_end, src_len);			if (a_lhs_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.a_lhs_end_missing", bgn, ttl_end);
		++a_lhs_end;	// skip >
		int a_rhs_bgn = Bry_finder.Find_fwd(src, Find_a_rhs_bgn_bry, a_lhs_end, src_len);	if (a_rhs_bgn == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.a_rhs_bgn_missing", bgn, ttl_end);
		if (caption)
			bfr.Add(Xow_hzip_dict.Bry_lnki_text_y);
		else
			bfr.Add(Xow_hzip_dict.Bry_lnki_text_n);
		bfr.Add_byte((byte)ttl.Ns().Ord());	// ASSUME:NS_MAX_255
		int ttl_len = ttl_end - ttl_bgn;
		if (caption) {
			bfr.Add(ttl.Page_db());
			bfr.Add_byte(Xow_hzip_dict.Escape);
			stats.Lnki_text_y_add();
			return a_lhs_end;
		}
		else {
			int capt_len = a_rhs_bgn - a_lhs_end;
			if (capt_len == ttl_len && Bry_.Match(src, ttl_bgn, ttl_end, src, a_lhs_end, a_rhs_bgn))
				bfr.Add(ttl.Page_db());
			else
				bfr.Add_mid(src, a_lhs_end, a_rhs_bgn);
			bfr.Add_byte(Xow_hzip_dict.Escape);
			stats.Lnki_text_n_add();
			return a_rhs_bgn + Find_a_rhs_bgn_len;
		}
	}
	public int Save_lnke(Bry_bfr bfr, Xow_hzip_stats stats, byte[] src, int src_len, int bgn, int pos, byte xtid) {// <a rel="nofollow" class="external free" href="http://a.org">http://a.org</a>
		int href_bgn = Bry_finder.Find_fwd(src, Find_href_bry, pos, src_len);				if (href_bgn == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.href_missing", bgn, pos);
		href_bgn += Find_href_len;
		int href_end = Bry_finder.Find_fwd(src, Byte_ascii.Quote, href_bgn, src_len);		if (href_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.href_missing", bgn, href_bgn);
		bfr.Add_byte(Xow_hzip_dict.Escape).Add_byte(xtid);
		bfr.Add_mid(src, href_bgn, href_end);
		bfr.Add_byte(Xow_hzip_dict.Escape);
		switch (xtid) {
			case Xow_hzip_dict.Tid_lnke_txt: {
				int a_rhs_bgn = Bry_finder.Find_fwd(src, Find_a_rhs_bgn_bry, href_end, src_len);	if (a_rhs_bgn == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.a_rhs_bgn_missing", bgn, href_end);
				stats.Lnke_txt_add();
				return a_rhs_bgn + Find_a_rhs_bgn_len;
			}
			case Xow_hzip_dict.Tid_lnke_brk_text_n: {
				int a_lhs_end = Bry_finder.Find_fwd(src, Byte_ascii.Gt, href_end, src_len);			if (a_lhs_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.a_lhs_end_missing", bgn, href_end);
				int num_bgn = a_lhs_end + 2; // skip >[
				int num_end = Bry_finder.Find_fwd(src, Byte_ascii.Brack_end, num_bgn, src_len);		if (num_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.num_end_missing", bgn, href_end);
				int num = Bry_.Xto_int_or(src, num_bgn, num_end, -1);								if (num == -1) return hzip_mgr.Warn_by_pos_add_dflt("a.num_invalid", num_bgn, num_end);
				Hpg_srl_itm_.Save_bin_int_abrv(bfr, num);
				int a_rhs_bgn = num_end + 1;
				int a_rhs_end = a_rhs_bgn + Find_a_rhs_bgn_len;
				if (!Bry_.Match(src, a_rhs_bgn, a_rhs_end, Find_a_rhs_bgn_bry)) return hzip_mgr.Warn_by_pos_add_dflt("a.rhs_missing", bgn, href_end);
				stats.Lnke_brk_text_n_add();
				return a_rhs_end;
			}
			case Xow_hzip_dict.Tid_lnke_brk_text_y: {
				int a_lhs_end = Bry_finder.Find_fwd(src, Byte_ascii.Gt, href_end, src_len);			if (a_lhs_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.a_lhs_end_missing", bgn, href_end);
				stats.Lnke_brk_text_y_add();
				return a_lhs_end + 1;
			}
			default:
				return hzip_mgr.Warn_by_pos("a.xtid_unknown", bgn, href_end);
		}
	}
	private Int_obj_ref count_ref = Int_obj_ref.zero_();
	public int Load_lnke(Bry_bfr bfr, byte[] src, int src_len, int href_bgn, byte xtid) {
		int href_end = Bry_finder.Find_fwd(src, Xow_hzip_dict.Escape, href_bgn, src_len);	if (href_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.href_missing", href_bgn, href_bgn);
		switch (xtid) {
			case Xow_hzip_dict.Tid_lnke_txt:
				bfr.Add_str("<a rel=\"nofollow\" class=\"external free\" href=\"").Add_mid(src, href_bgn, href_end).Add_str("\">").Add_mid(src, href_bgn, href_end).Add_str("</a>");
				return href_end + 1; // +1 to skip escape
			case Xow_hzip_dict.Tid_lnke_brk_text_n:
				int num = Hpg_srl_itm_.Load_bin_int_abrv(src, src_len, href_end + 1, count_ref);
				bfr.Add_str("<a rel=\"nofollow\" class=\"external autonumber\" href=\"").Add_mid(src, href_bgn, href_end).Add_str("\">[").Add_int_variable(num).Add_str("]</a>");
				return href_end + 1 + count_ref.Val(); // +1 to skip escape
			case Xow_hzip_dict.Tid_lnke_brk_text_y:
				bfr.Add_str("<a rel=\"nofollow\" class=\"external text\" href=\"").Add_mid(src, href_bgn, href_end).Add_str("\">");
				return href_end + 1; // +1 to skip escape
			default:
				return hzip_mgr.Warn_by_pos("a.xtid_unknown", href_bgn, href_end);
		}
	}
	public int Load_lnki(Bry_bfr bfr, byte[] src, int src_len, int bgn, byte tid) {
		byte ns_ord = src[bgn];
		Xow_ns ns = ttl_parser.Ns_mgr().Ords_get_at(ns_ord);
		int ttl_bgn = bgn + 1;
		int ttl_end = Bry_finder.Find_fwd(src, Xow_hzip_dict.Escape, ttl_bgn, src_len);		if (ttl_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.ttl_end_missing", bgn, ttl_bgn);
		byte[] ttl_bry = Bry_.Mid(src, ttl_bgn, ttl_end);
		Xoa_ttl ttl = ttl_parser.Ttl_parse(ns.Id(), ttl_bry);
		bfr.Add_str("<a href='/wiki/").Add(ttl.Full_db()).Add_str("' title='");
		int rv = ttl_end + 1;
		if (tid == Xow_hzip_dict.Tid_lnki_text_n) {
			if (ns.Id() != 0) ttl_bry = ttl.Full_db();
			bfr.Add(Html_utl.Escape_html_as_bry(ttl_bry)).Add_str("'>").Add(ttl_bry);
			bfr.Add_str("</a>");
		}
		else {
			bfr.Add(ttl.Page_txt()).Add_str("'>");
		}
		return rv;
	}
	public void Html_plain(Bry_bfr bfr, Xop_lnki_tkn lnki) {
		bfr.Add_str
			(  lnki.Caption_exists()				// caption exists; EX: [[A|b]]
			|| lnki.Tail_bgn() != -1				// trailing chars; EX: [[A]]b
			? "<a xtid='a_lnki_text_y' href=\""		// embed caption
			: "<a xtid='a_lnki_text_n' href=\""		// use link only
			);
	}
	private static final byte[]
	  Find_href_wiki_bry	= Bry_.new_ascii_("href=\"/wiki/")
	, Find_href_bry			= Bry_.new_ascii_("href=\"")
	, Find_a_rhs_bgn_bry	= Bry_.new_ascii_("</a>")
	, Find_img_xatrs		= Bry_.new_ascii_("xatrs='")
	;
	private static final int 
	  Find_href_wiki_len	= Find_href_wiki_bry.length
	, Find_href_len			= Find_href_bry.length
	, Find_a_rhs_bgn_len	= Find_a_rhs_bgn_bry.length
	;
}	

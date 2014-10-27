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
package gplx.xowa.hdumps.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import gplx.html.*;
public class Xoa_hzip_itm__lnki {
	private Xoa_hzip_mgr hzip_mgr; private Xow_wiki wiki;
	public Xoa_hzip_itm__lnki(Xoa_hzip_mgr hzip_mgr, Xow_wiki wiki) {this.hzip_mgr = hzip_mgr; this.wiki = wiki;}
	public int Save(Bry_bfr bfr, byte[] src, int src_len, int bgn, int pos) {
		int xtid_bgn = pos + Find_xtid_len;										if (!Bry_.Match(src, pos, xtid_bgn, Find_xtid_bry)) return Xoa_hzip_mgr.Unhandled; // next atr should be "xtid='"
		int xtid_end = Bry_finder.Find_fwd(src, Byte_ascii.Apos, xtid_bgn);		if (xtid_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.xtid_end_missing", bgn, xtid_bgn);
		Object xtid_obj = Xtids.Get_by_mid(src, xtid_bgn, xtid_end);			if (xtid_obj == null) return hzip_mgr.Warn_by_pos("a.xtid_invalid", xtid_bgn, xtid_end);
		byte xtid = ((Byte_obj_val)xtid_obj).Val();
		switch (xtid) {
			case Xtid_ttl_int:		return Save_basic(bfr, src, src_len, bgn, xtid_end, Bool_.N);
			case Xtid_capt_int:		return Save_basic(bfr, src, src_len, bgn, xtid_end, Bool_.Y);
			default:				return hzip_mgr.Warn_by_pos("a.xtid_unknown", xtid_bgn, xtid_end);
		}
	}
	private int Save_basic(Bry_bfr bfr, byte[] src, int src_len, int bgn, int pos, boolean caption) {
		int ttl_bgn = Bry_finder.Find_fwd(src, Find_href_bry, pos, src_len);				if (ttl_bgn == Bry_finder.Not_found) return Xoa_hzip_mgr.Unhandled;//hzip_mgr.Warn_by_pos_add_dflt("a.ttl_bgn_missing", bgn, pos);
		ttl_bgn += Find_href_len;
		int ttl_end = Bry_finder.Find_fwd(src, Byte_ascii.Quote, ttl_bgn , src_len);		if (ttl_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.ttl_end_missing", bgn, ttl_bgn);
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, Bry_.Mid(src, ttl_bgn, ttl_end));				if (ttl == null) return hzip_mgr.Warn_by_pos("a.ttl_invalid", ttl_bgn, ttl_end);
		int a_lhs_end = Bry_finder.Find_fwd(src, Byte_ascii.Gt, ttl_end, src_len);			if (a_lhs_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.a_lhs_end_missing", bgn, ttl_end);
		++a_lhs_end;	// skip >
		int a_rhs_bgn = Bry_finder.Find_fwd(src, Find_a_rhs_bgn_bry, a_lhs_end, src_len);	if (a_rhs_bgn == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.a_rhs_bgn_missing", bgn, ttl_end);
		if (caption)
			bfr.Add(Xoa_hzip_dict.Bry_lnki_capt);
		else
			bfr.Add(Xoa_hzip_dict.Bry_lnki_ttl);
		bfr.Add_byte((byte)ttl.Ns().Ord());	// ASSUME:NS_MAX_255
		if (caption)
			bfr.Add(ttl.Page_db());
		else {
			int capt_len = a_rhs_bgn - a_lhs_end;
			int ttl_len = ttl_end - ttl_bgn;
			if (capt_len == ttl_len && Bry_.Match(src, ttl_bgn, ttl_end, src, a_lhs_end, a_rhs_bgn))
				bfr.Add(ttl.Page_db());
			else
				bfr.Add_mid(src, a_lhs_end, a_rhs_bgn);
		}
		bfr.Add_byte(Xoa_hzip_dict.Escape);
		if (caption) {
			bfr.Add_mid(src, a_lhs_end, a_rhs_bgn);
			bfr.Add(Xoa_hzip_dict.Bry_a_end);
		}
		return a_rhs_bgn + Find_a_rhs_bgn_len;
	}
	public int Load_ttl(Bry_bfr bfr, byte[] src, int src_len, int bgn, byte tid) {
		byte ns_ord = src[bgn];
		Xow_ns ns = wiki.Ns_mgr().Ords_get_at(ns_ord);
		int ttl_bgn = bgn + 1;
		int ttl_end = Bry_finder.Find_fwd(src, Xoa_hzip_dict.Escape, ttl_bgn, src_len);		if (ttl_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.ttl_end_missing", bgn, ttl_bgn);
		byte[] ttl_bry = Bry_.Mid(src, ttl_bgn, ttl_end);
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, ns.Id(), ttl_bry);
		bfr.Add_str("<a href='/wiki/").Add(ttl.Full_db()).Add_str("' title='");
		int rv = ttl_end + 1;
		if (tid == Xoa_hzip_dict.Tid_lnki_ttl) {
			if (ns.Id() != 0) ttl_bry = ttl.Full_db();
			bfr.Add(Html_utl.Escape_html_as_bry(ttl_bry)).Add_str("'>").Add(ttl_bry);
		}
		else {
			int capt_end = Bry_finder.Find_fwd(src, Xoa_hzip_dict.Bry_a_end, rv, src_len);		if (capt_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("a.capt_end_missing", bgn, rv);
			ttl_bry = Bry_.Mid(src, rv, capt_end);
			bfr.Add(Html_utl.Escape_html_as_bry(ttl_bry)).Add_str("'>").Add(ttl_bry);
			rv = capt_end + Xoa_hzip_dict.Bry_a_end.length;
		}
		bfr.Add_str("</a>");
		return rv;
	}
	public void Html(Bry_bfr bfr, boolean caption) {bfr.Add(caption ? Html_capt : Html_ttl);}
	private static final byte[] Html_ttl = Bry_.new_ascii_("<a xtid='a_ttl' href=\""), Html_capt = Bry_.new_ascii_("<a xtid='a_capt' href=\"");
	private static final byte[]
	  Find_xtid_bry			= Bry_.new_ascii_("xtid='")
	, Find_href_bry			= Bry_.new_ascii_("href=\"/wiki/")
	, Find_a_rhs_bgn_bry	= Bry_.new_ascii_("</a>")
	;
	private static final int 
	  Find_xtid_len			= Find_xtid_bry.length
	, Find_href_len			= Find_href_bry.length
	, Find_a_rhs_bgn_len	= Find_a_rhs_bgn_bry.length
	;
	private static final byte[]
	  Xtid_ttl_bry		= Bry_.new_ascii_("a_ttl")
	, Xtid_capt_bry		= Bry_.new_ascii_("a_capt")
	;
	private static final byte
	  Xtid_ttl_int		= 0
	, Xtid_capt_int		= 1
	;
	private static final Hash_adp_bry Xtids = Hash_adp_bry.cs_()
	.Add_bry_byte(Xtid_ttl_bry		, Xtid_ttl_int)
	.Add_bry_byte(Xtid_capt_bry		, Xtid_capt_int)
	;
}

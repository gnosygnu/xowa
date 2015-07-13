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
import gplx.core.primitives.*;
class Xow_hzip_xtid {
	public static int Find_xtid(Xow_hzip_mgr hzip_mgr, byte[] src, int src_len, int bgn, int pos, Byte_obj_ref xtid_ref) {
		int xtid_bgn = pos + Len_xtid;											if (!Bry_.Match(src, pos, xtid_bgn, Bry_xtid)) return Xow_hzip_mgr.Unhandled; // next atr should be "xtid='"
		int xtid_end = Bry_finder.Find_fwd(src, Byte_ascii.Apos, xtid_bgn);		if (xtid_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("xtid_end_missing", bgn, xtid_bgn);
		Object xtid_obj = Xtids.Get_by_mid(src, xtid_bgn, xtid_end);			if (xtid_obj == null) return hzip_mgr.Warn_by_pos("a.xtid_invalid", xtid_bgn, xtid_end);
		xtid_ref.Val_(xtid_obj == null ? Byte_.Min_value : ((Byte_obj_val)xtid_obj).Val());
		return xtid_end;
	}
	private static final byte[]
	  Bry_xtid				= Bry_.new_a7("xtid='")
	;
	private static final int 
	  Len_xtid				= Bry_xtid.length
	;
	public static final byte[]
	  Bry_lnki_text_n		= Bry_.new_a7("a_lnki_text_n")
	, Bry_lnki_text_y		= Bry_.new_a7("a_lnki_text_y")
	, Bry_lnke_txt			= Bry_.new_a7("a_lnke_txt")
	, Bry_lnke_brk_n		= Bry_.new_a7("a_lnke_brk_n")
	, Bry_lnke_brk_y		= Bry_.new_a7("a_lnke_brk_y")
	, Bry_img_full			= Bry_.new_a7("a_img_full")
	, Bry_hdr				= Bry_.new_a7("hdr")
	;
	private static final Hash_adp_bry Xtids = Hash_adp_bry.cs_()
	.Add_bry_byte(Bry_lnki_text_n		, Xow_hzip_dict.Tid_lnki_text_n)
	.Add_bry_byte(Bry_lnki_text_y		, Xow_hzip_dict.Tid_lnki_text_y)
	.Add_bry_byte(Bry_lnke_txt			, Xow_hzip_dict.Tid_lnke_txt)
	.Add_bry_byte(Bry_lnke_brk_n		, Xow_hzip_dict.Tid_lnke_brk_text_n)
	.Add_bry_byte(Bry_lnke_brk_y		, Xow_hzip_dict.Tid_lnke_brk_text_y)
	.Add_bry_byte(Bry_img_full			, Xow_hzip_dict.Tid_img_full)
	.Add_bry_byte(Bry_hdr				, Xow_hzip_dict.Tid_hdr_lhs)
	;
}

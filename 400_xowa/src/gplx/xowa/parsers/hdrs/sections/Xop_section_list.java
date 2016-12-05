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
package gplx.xowa.parsers.hdrs.sections; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.hdrs.*;
import gplx.xowa.parsers.mws.*; import gplx.xowa.parsers.mws.wkrs.*;
class Xop_section_list implements Xomw_hdr_cbk {
	private final    Xomw_hdr_wkr hdr_wkr = new Xomw_hdr_wkr();
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	private byte[] src;

	public Xop_section_list Parse(byte[] ttl_full_db, byte[] src) {
		this.src = src;
		Xomw_parser_ctx pctx = new Xomw_parser_ctx();
		hdr_wkr.Parse(pctx, src, 0, src.length, this);
		return this;
	}
	public byte[] Extract_bry_or_null(byte[] key) {
		// find section matching key
		Xop_section_itm itm = (Xop_section_itm)hash.Get_by(key);
		if (itm == null) return null;

		int src_bgn = itm.Src_bgn();
		if (src[src_bgn] == Byte_ascii.Nl) src_bgn++;

		int src_end = src.length;
		if (itm.Idx() != hash.Len() - 1) {	// if not last, get next
			Xop_section_itm nxt = (Xop_section_itm)hash.Get_at(itm.Idx() + 1);
			src_end = nxt.Src_bgn();
		}
		src_end = Bry_find_.Find_bwd__skip_ws(src, src_end, src_bgn);

		return Bry_.Mid(src, src_bgn, src_end);
	}
	public void On_hdr_seen(Xomw_parser_ctx pctx, Xomw_hdr_wkr wkr) {
		byte[] src = wkr.Src();
		int hdr_txt_bgn = wkr.Hdr_lhs_end();
		int hdr_txt_end = wkr.Hdr_rhs_bgn();
		hdr_txt_bgn = Bry_find_.Find_fwd_while_ws(src, hdr_txt_bgn, hdr_txt_end);
		hdr_txt_end = Bry_find_.Find_bwd__skip_ws(src, hdr_txt_end, hdr_txt_bgn);

		byte[] key = Bry_.Mid(wkr.Src(), hdr_txt_bgn, hdr_txt_end);			
		Xop_section_itm itm = new Xop_section_itm(hash.Count(), key, wkr.Hdr_bgn(), wkr.Hdr_end());
		hash.Add(key, itm);
	}
	public void On_src_done(Xomw_parser_ctx pctx, Xomw_hdr_wkr wkr) {}
}

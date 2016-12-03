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
import gplx.langs.htmls.*;
import gplx.xowa.parsers.mws.*; import gplx.xowa.parsers.mws.wkrs.*;
public class Xop_section_mgr implements Xomw_hdr_cbk {
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private final    Xomw_hdr_wkr hdr_wkr = new Xomw_hdr_wkr();
	private int section_idx;
	public byte[] Insert(Xoa_ttl ttl, byte[] src) {
		section_idx = 0;
		Xomw_parser_ctx pctx = new Xomw_parser_ctx(ttl);
		hdr_wkr.Parse(bfr, pctx, src, 0, src.length, this);
		return bfr.To_bry_and_clear();
	}
	public void Parse(Xop_hdr_tkn hdr, byte[] page_ttl, byte[] src, int cur_pos, int src_len) {
		// get page ttl
		int page_ttl_bgn = cur_pos + Bry__meta.length;
		int page_ttl_end = Bry_find_.Find_fwd(src, Byte_ascii.Pipe, page_ttl_bgn, src_len);
		if (page_ttl_end == Bry_find_.Not_found) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "invalid section ttl; page=~{0} excerpt=~{1}", page_ttl, Bry_.Mid(src, cur_pos, cur_pos + 100));
			return;
		}
		byte[] section_page = Bry_.Mid(src, page_ttl_bgn, page_ttl_end);

		// get section idx
		int section_idx_bgn = page_ttl_end + 1;
		int section_idx_end = Bry_find_.Find_fwd(src, Gfh_tag_.Comm_end, section_idx_bgn, src_len);
		int section_idx = Bry_.To_int_or(src, section_idx_bgn, section_idx_end, -1);
		if (page_ttl_end == -1) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "invalid section idx; page=~{0} excerpt=~{1}", page_ttl, Bry_.Mid(src, cur_pos, cur_pos + 100));
			return;
		}

		hdr.Section_editable_(section_page, section_idx);
	}
	public void Write(Bry_bfr bfr, Xomw_parser_ctx pctx, Xomw_hdr_wkr wkr) {
		bfr.Add_mid(wkr.Src(), wkr.Hdr_lhs_bgn(), wkr.Hdr_rhs_end());
		bfr.Add(Bry__meta);						// <!--xo_meta|section_edit|
		bfr.Add(pctx.Page_ttl().Full_db());		// Page_1
		bfr.Add_byte_pipe();					// |
		bfr.Add_int_variable(++section_idx);	// 123
		bfr.Add(Gfh_tag_.Comm_end);				// -->
	}
	public static final    byte[] Bry__meta = Bry_.new_a7("<!--xo_meta|section_edit|");
	public static final    int Len__meta = Bry__meta.length;
}

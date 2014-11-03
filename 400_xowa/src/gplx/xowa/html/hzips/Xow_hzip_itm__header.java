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
import gplx.html.*; import gplx.xowa.apps.ttls.*; import gplx.xowa.hdumps.srls.*;
public class Xow_hzip_itm__header {
	private Xow_hzip_mgr hzip_mgr;
	public Xow_hzip_itm__header(Xow_hzip_mgr hzip_mgr) {this.hzip_mgr = hzip_mgr;}
	public int Save(Bry_bfr bfr, Xow_hzip_stats stats, byte[] src, int src_len, int bgn, int pos) {
		if (pos >= src_len) return Xow_hzip_mgr.Unhandled;
		byte hdr_num_byte = src[pos];
		switch (hdr_num_byte) {
			case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3:
			case Byte_ascii.Num_4: case Byte_ascii.Num_5: case Byte_ascii.Num_6:
				break;
			default:
				return Xow_hzip_mgr.Unhandled;
		}
		int span_lhs_bgn = pos + 2;		// +2 to skip # and >; EX: "<h2>"
		int span_lhs_end = Bry_finder.Find_fwd(src, Byte_ascii.Gt, span_lhs_bgn, src_len);		if (span_lhs_end == Bry_finder.Not_found) return hzip_mgr.Warn_by_pos_add_dflt("h.span_end_missing", bgn, pos);
		bfr.Add(Xow_hzip_dict.Bry_hdr_lhs);
		bfr.Add_byte((byte)(hdr_num_byte - Byte_ascii.Num_0));
		stats.Hdr_add();
		return span_lhs_end;
	}
	public void Html(Bry_bfr bfr, boolean caption) {}
}

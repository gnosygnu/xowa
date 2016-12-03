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
package gplx.xowa.parsers.mws.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.mws.*;
public class Xomw_hdr_cbk__html implements Xomw_hdr_cbk {
	public void Write(Bry_bfr bfr, Xomw_parser_ctx pctx, Xomw_hdr_wkr wkr) {
		int hdr_len = wkr.Hdr_len();
		bfr.Add(Tag__lhs).Add_int_digits(1, hdr_len).Add(Byte_ascii.Angle_end_bry);	// <h2>
		bfr.Add_mid(wkr.Src(), wkr.Hdr_lhs_end(), wkr.Hdr_rhs_bgn());
		bfr.Add(Tag__rhs).Add_int_digits(1, hdr_len).Add(Byte_ascii.Angle_end_bry);	// </h2>
	}
	private static final    byte[] 
	  Tag__lhs = Bry_.new_a7("<h")
	, Tag__rhs = Bry_.new_a7("</h")
	;
}

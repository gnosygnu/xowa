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
package gplx.xowa.htmls.core.wkrs.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*; import gplx.xowa.wikis.ttls.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.sections.*; import gplx.xowa.htmls.core.hzips.stats.*;
public abstract class Xoh_hdr_parse_base {
	public void Parse(Bry_bfr bfr, Bry_parser parser, byte[] src, int hook_bgn) {// EX: <h2 data-xotype='rng_bgn'>
		int rng_bgn = parser.Pos_(hook_bgn - 3);	// move back to "<h#" b/c hook starts at " data-xotype"; 
		parser.Chk(Bry__hdr_lhs_bgn);				// check for "<h"

		// get level; EX: '<h2' -> '2'
		byte level = parser.Read_byte_as_a7_int();

		// get anchor between "id='" and "'>"
		parser.Fwd_end(Html_tag_.Span_lhs);
		int anchor_bgn = parser.Fwd_end(Bry__atr_id);
		int anchor_end = parser.Fwd_bgn(Bry__tag_end);

		// get display between "'>" and "</h#>"
		int display_bgn = parser.Pos();
		int display_end = parser.Fwd_bgn(Xoh_html_dict_.Hdr__end);

		// get content_bgn after "</h2>"
		int rng_end = parser.Fwd_end(Bry__hdr_rhs_bgn);
		if (level != parser.Read_byte_as_a7_int()) throw parser.Fail("levels doesn't match", Byte_.To_str(level));
		rng_end = parser.Chk(Byte_ascii.Angle_end);

		Parse_exec(bfr, src, hook_bgn, rng_bgn, rng_end, level, anchor_bgn, anchor_end, display_bgn, display_end);
	}
	public abstract void Parse_exec(Bry_bfr bfr, byte[] src, int hook_bgn, int rng_bgn, int rng_end, byte level, int anchor_bgn, int anchor_end, int display_bgn, int display_end);
	private static final byte[] Bry__hdr_lhs_bgn = Bry_.new_a7("<h"), Bry__atr_id = Bry_.new_a7(" id='"), Bry__tag_end = Bry_.new_a7("'>"), Bry__hdr_rhs_bgn = Bry_.new_a7("</h");
}

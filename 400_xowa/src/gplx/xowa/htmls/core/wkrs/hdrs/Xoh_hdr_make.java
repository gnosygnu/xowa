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
import gplx.core.brys.*;
import gplx.xowa.htmls.sections.*;
public class Xoh_hdr_make extends Xoh_hdr_parse_base {
	private Xoh_page hpg;
	public int Make(Bry_bfr bfr, Xoh_page hpg, Bry_parser parser, byte[] src, int hook_bgn) {
		this.hpg = hpg;
		this.Parse(bfr, parser, src, hook_bgn);
		return parser.Pos();
	}
	@Override public void Parse_exec(Bry_bfr bfr, byte[] src, int hook_bgn, int rng_bgn, int rng_end, byte level, int anchor_bgn, int anchor_end, int display_bgn, int display_end) {
		// register section
		Xoh_section_mgr section_mgr = hpg.Section_mgr();
		int section_len = section_mgr.Len();
		if (section_len != 0)	// guard against -1 index; should not happen
			section_mgr.Set_content(section_len - 1, src, rng_bgn - 2);	// -2 to skip "\n\n"
		hpg.Section_mgr().Add(section_len, level, Bry_.Mid(src, anchor_bgn, anchor_end), Bry_.Mid(src, display_bgn, display_end)).Content_bgn_(rng_end + 1); // +1 to skip "\n"
		bfr.Add_mid(src, hook_bgn, rng_end);
	}
}

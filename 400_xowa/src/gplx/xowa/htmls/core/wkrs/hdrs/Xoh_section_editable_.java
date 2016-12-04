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
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*; import gplx.xowa.parsers.hdrs.*;
public class Xoh_section_editable_ {
	public static void Write_imt(Bry_bfr bfr, Xop_ctx ctx, Xop_tkn_itm tkn) {
		Xop_hdr_tkn hdr_tkn = (Xop_hdr_tkn)tkn;
		ctx.Wiki().Parser_mgr().Hdr__section_editable__imt_fmt().Bld_many_to_bry(bfr, hdr_tkn.Section_editable_page(), hdr_tkn.Section_editable_idx());
	}
	public static final String Cfg__section_editing__enabled = "section_editing.enabled";
}

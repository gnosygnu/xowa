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
package gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.singles; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.*;
import gplx.core.brys.*; import gplx.core.brys.fmts.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.htmls.core.htmls.*;
public class Xoctg_single_box {
	private final    Xoctg_single_itm itms_fmtr = new Xoctg_single_itm();
	private byte[] lbl_categories;
	public void Init_by_wiki(Xow_wiki wiki) {
		this.lbl_categories = wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_ctg_tbl_hdr);
		itms_fmtr.Init_by_wiki(wiki);
	}
	public void Write_pagebox(Bry_bfr bfr, Xoctg_pagebox_itm[] itms) {
		itms_fmtr.Init_by_page(itms);
		Fmt__grp.Bld_many(bfr, lbl_categories, itms_fmtr);
	}
	private static final    Bry_fmt Fmt__grp = Bry_fmt.Auto_nl_skip_last
	( "<div id=\"catlinks\" class=\"catlinks\">"
	,   "<div id=\"mw-normal-catlinks\" class=\"mw-normal-catlinks\">"
	,     "~{grp_lbl}"
	,     "<ul>~{grp_itms}"
	,     "</ul>"
	,   "</div>"
	, "</div>"
	);
}

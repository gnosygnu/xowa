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
package gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.doubles; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.*;
import gplx.core.brys.*; import gplx.core.brys.fmts.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.users.history.*;
import gplx.xowa.addons.wikis.ctgs.htmls.*;
public class Xoctg_double_box implements Bfr_arg {
	public Xoctg_double_grp Grp_normal() {return grp_normal;} private final    Xoctg_double_grp grp_normal = new Xoctg_double_grp();
	public Xoctg_double_grp Grp_hidden() {return grp_hidden;} private final    Xoctg_double_grp grp_hidden = new Xoctg_double_grp();
	public void Init_by_wiki(Xow_wiki wiki) {
		Xou_history_mgr history_mgr = wiki.App().User().History_mgr();
		grp_normal.Init_by_wiki(wiki, history_mgr, Bool_.Y);
		grp_hidden.Init_by_wiki(wiki, history_mgr, Bool_.N);
	}
	public void Write_pagebox(Bry_bfr bfr, Xoctg_pagebox_itm[] ary) {
		grp_normal.Itms().Itms__clear();
		grp_hidden.Itms().Itms__clear();

		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xoctg_pagebox_itm itm = ary[i];
			Xoctg_double_grp list = itm.Hidden() ? grp_hidden : grp_normal; 
			list.Itms().Itms__add(itm);
		}
		this.Bfr_arg__add(bfr);
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		Fmt__all.Bld_many(bfr, grp_normal, grp_hidden);
	}
	private static final    Bry_fmt 
	  Fmt__all = Bry_fmt.Auto_nl_skip_last
	( "<div id=\"catlinks\" class=\"catlinks\">~{grp_normal}~{grp_hidden}"
	, "</div>"
	);
}

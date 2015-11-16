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
package gplx.xowa.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.brys.fmtrs.*;
public class Xoctg_pagelist_mgr extends gplx.core.brys.Bfr_arg_base {
	public Xoctg_pagelist_mgr Init_by_app(Xoae_app app, Xoctg_pagelist_wtr hidden_wtr) {
		this.fmtr_all = hidden_wtr.Fmtr_all();
		grp_normal.Init_app(app, Bool_.Y, hidden_wtr.Fmtr_grp_normal(), hidden_wtr.Fmtr_itm());
		grp_hidden.Init_app(app, Bool_.N, hidden_wtr.Fmtr_grp_hidden(), hidden_wtr.Fmtr_itm());
		return this;
	}	private Bry_fmtr fmtr_all;
	public void Init_by_wiki(Xowe_wiki wiki) {
		grp_normal.Init_by_wiki(wiki);
		grp_hidden.Init_by_wiki(wiki);
	}
	public Xoctg_pagelist_grp Grp_normal() {return grp_normal;} private Xoctg_pagelist_grp grp_normal = new Xoctg_pagelist_grp();
	public Xoctg_pagelist_grp Grp_hidden() {return grp_hidden;} private Xoctg_pagelist_grp grp_hidden = new Xoctg_pagelist_grp();
	@Override public void Bfr_arg__add(Bry_bfr bfr) {
		fmtr_all.Bld_bfr_many(bfr, grp_normal, grp_hidden);
	}
}

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
package gplx.xowa.ctgs; import gplx.*; import gplx.xowa.*;
import gplx.xowa.langs.msgs.*;
public class Xoctg_pagelist_grp implements Bry_fmtr_arg {
	public void Init_app(Xoa_app app, boolean type_is_normal, Bry_fmtr fmtr_grp, Bry_fmtr fmtr_itm) {
		this.type_is_normal = type_is_normal;
		this.fmtr_grp = fmtr_grp;
		itms.Init_app(app, fmtr_itm);
	}	private Bry_fmtr fmtr_grp;
	public void Init_by_wiki(Xow_wiki wiki) {
		lbl_ctg_text	= wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_ctg_tbl_hdr);
		lbl_ctg_help	= Xol_msg_mgr_.Get_msg_val(wiki, wiki.Lang(), Key_pagecategorieslink, Bry_.Ary_empty);
		lbl_hidden		= wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_ctg_tbl_hidden);
		itms.Init_wiki(wiki);
	}	private byte[] lbl_ctg_help, lbl_ctg_text, lbl_hidden; private static final byte[] Key_pagecategorieslink = Bry_.new_ascii_("pagecategorieslink");
	public boolean Type_is_normal() {return type_is_normal;} private boolean type_is_normal;
	public Xoctg_pagelist_itms Itms() {return itms;} private Xoctg_pagelist_itms itms = new Xoctg_pagelist_itms();
	public void XferAry(Bry_bfr bfr, int idx) {
		if (type_is_normal)
			fmtr_grp.Bld_bfr_many(bfr, lbl_ctg_help, lbl_ctg_text, itms);
		else
			fmtr_grp.Bld_bfr_many(bfr, lbl_hidden, itms);
	}
}

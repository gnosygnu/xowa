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
package gplx.xowa.guis.menus; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.xowa.guis.cmds.*;
class Xog_menu_bldr {
	private int indent = 0;
	private Bry_bfr bfr = Bry_bfr_.Reset(0);
	public String Gen_str() {return bfr.To_str_and_clear();}
	private Xog_menu_bldr Indent_add() {indent += 2; return this;}
	private Xog_menu_bldr Indent_del() {indent -= 2; return this;}
	private void Indent() {
		if (indent > 0)
			bfr.Add_byte_repeat(Byte_ascii.Space, indent);
	}
	public Xog_menu_bldr Add_spr() {
		Indent();
		bfr.Add(Const_spr);
		return this;
	}
	public Xog_menu_bldr Add_grp_bgn(String key) {
		Indent();
		bfr.Add(Const_itm_grp_bgn_lhs);
		bfr.Add_str_u8(key);
		bfr.Add(Const_itm_grp_bgn_rhs);
		Indent_add();
		return this;
	}
	public Xog_menu_bldr Add_grp_end() {
		Indent_del();
		Indent();
		bfr.Add(Const_itm_grp_end);
		return this;
	}
	public Xog_menu_bldr Add_btn(String key) {
		Indent();
		bfr.Add(Const_itm_btn_bgn_lhs);
		bfr.Add_str_u8(key);
		bfr.Add(Const_itm_btn_bgn_rhs);
		return this;
	}
	private static final    byte[]
	  Const_spr				= Bry_.new_a7("add_spr;\n")
	, Const_itm_btn_bgn_lhs	= Bry_.new_a7("add_btn_default('")
	, Const_itm_btn_bgn_rhs	= Bry_.new_a7("');\n")
	, Const_itm_grp_bgn_lhs	= Bry_.new_a7("add_grp_default('")
	, Const_itm_grp_bgn_rhs	= Bry_.new_a7("') {\n")
	, Const_itm_grp_end		= Bry_.new_a7("}\n")
	;
	public static final    Xog_menu_bldr Instance = new Xog_menu_bldr(); Xog_menu_bldr() {}
}

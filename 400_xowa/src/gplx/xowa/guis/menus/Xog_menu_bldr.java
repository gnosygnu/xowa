/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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

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
package gplx.xowa.xtns.wikias; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.langs.htmls.*;
class Tabber_tab_itm {
	public Tabber_tab_itm(boolean active, byte[] name, byte[] text) {
		this.Active = active;
		this.Name = name;
		this.Text = text;
	}
	public final    boolean Active;
	public final    byte[] Name;
	public final    byte[] Text;
	public static void Write(Bry_bfr bfr, byte[] id, Tabber_tab_itm[] ary) {
		bfr.Add_str_a7("<div id=\"tabber-");
		bfr.Add(id);
		bfr.Add_str_a7("\" class=\"tabber\">\n");

		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Tabber_tab_itm itm = ary[i];
			bfr.Add_str_a7("<div class=\"tabbertab\" title=\"");
			bfr.Add(Gfh_utl.Escape_html_as_bry(itm.Name));
			bfr.Add_str_a7("\"");
			if (itm.Active) {
				bfr.Add_str_a7(" data-active=\"true\"");
			}
			bfr.Add_str_a7(">\n");
			bfr.Add(Gfh_tag_.P_lhs);
			bfr.Add(itm.Text);
			bfr.Add(Gfh_tag_.P_rhs).Add_byte_nl();
			bfr.Add(Gfh_tag_.Div_rhs).Add_byte_nl();
		}
		bfr.Add(Gfh_tag_.Div_rhs);
	}
}

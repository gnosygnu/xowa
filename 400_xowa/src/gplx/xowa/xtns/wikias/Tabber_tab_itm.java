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

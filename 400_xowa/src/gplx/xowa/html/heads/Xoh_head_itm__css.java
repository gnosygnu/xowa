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
package gplx.xowa.html.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.xowa.gui.*;
public class Xoh_head_itm__css extends Xoh_head_itm__base {
	@Override public byte[] Key() {return Xoh_head_itm_.Key__css;}
	@Override public int Flags() {return Flag__css_text;}
	@Override public void Write_css_text(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_css_style_itm(app.Ctg_mgr().Missing_ctg_cls_css());
		if (app.Html_mgr().Page_mgr().Font_enabled())
			wtr.Write_css_style_itm(app.Html_mgr().Page_mgr().Font_css_bry());
		byte[] css_xtn = app.Gui_mgr().Html_mgr().Css_xtn();
		if (Bry_.Len_gt_0(css_xtn))
			wtr.Write_css_style_itm(css_xtn);
	}
}

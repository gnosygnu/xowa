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
package gplx.xowa.hdumps.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
 import gplx.xowa.hdumps.core.*;
class Hdump_html_fmtr__sidebars implements Bry_fmtr_arg {
	private Hdump_page_itm page;
	public void Init_by_page(Hdump_page_itm page)						{this.page = page;}
	public void XferAry(Bry_bfr bfr, int idx) {
		byte[][] sidebar_divs = page.Sidebar_divs();
		int sidebar_divs_len = sidebar_divs.length;
		for (int i = 0; i < sidebar_divs_len; ++i) {
			byte[] sidebar_div = sidebar_divs[i];
			bfr.Add(sidebar_div);
		}
	}
}

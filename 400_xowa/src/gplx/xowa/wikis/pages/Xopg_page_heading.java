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
package gplx.xowa.wikis.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.brys.*; import gplx.core.brys.fmtrs.*;
public class Xopg_page_heading implements Bfr_arg {
	private Xopg_html_data html_data;
	private byte[] display_title;
	public Xopg_page_heading Init(Xopg_html_data html_data, byte[] display_title) {
		this.html_data = html_data;
		this.display_title = display_title;
		return this;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (html_data.Xtn_pgbnr() != null) return;	// pgbnr exists; don't add title
		fmtr.Bld_many(bfr, display_title);
	}
	private final    Bry_fmt fmtr = Bry_fmt.New(Bry_.New_u8_nl_apos("<h1 id='firstHeading' class='firstHeading'><span>~{page_title}</span></h1>"), "page_title");
}

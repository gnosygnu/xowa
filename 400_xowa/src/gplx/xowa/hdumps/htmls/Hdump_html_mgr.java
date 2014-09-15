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
public class Hdump_html_mgr {
	private Hdump_html_body body_fmtr = new Hdump_html_body();
	public Hdump_html_mgr Init_by_app(Xoa_app app) {body_fmtr.Init_by_app(app); return this;}
	public void Write(Bry_bfr bfr, Xow_wiki wiki, Hdump_page page) {
		body_fmtr.Init_by_page(wiki, page);
		body_fmtr.XferAry(bfr, 0);
	}
}

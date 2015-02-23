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
package gplx.xowa2.wikis.specials; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.wikis.*;
import gplx.xowa.*; import gplx.xowa2.gui.*;
import gplx.xowa.specials.xowa.file_browsers.*;
public class Xosp_special_mgr {
	private final Xowv_wiki wiki;
	public Xosp_special_mgr(Xowv_wiki wiki) {this.wiki = wiki;}
	public void Get_by_ttl(Xog_page rv, Gfo_url url, Xoa_ttl ttl) {
		Xosp_fbrow_rslt rslt = Xosp_fbrow_special.Gen(url.Args(), wiki.Appv().Wiki_mgr());
		rv.Init(-1, null, ttl);
		rv.Page_body_(rslt.Html_body());
		rv.Html_head_xtn_(rslt.Html_head());
	}
}

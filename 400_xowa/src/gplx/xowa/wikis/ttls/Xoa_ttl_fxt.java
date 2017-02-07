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
package gplx.xowa.wikis.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.tests.*;
public class Xoa_ttl_fxt {
	private Xowe_wiki wiki;
	private String raw;
	private Xoa_ttl ttl;
	public void Clear() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
	}
	public Xoa_ttl_fxt Parse(String raw) {
		this.raw = raw;
		this.ttl = Xoa_ttl.Parse(wiki, Bry_.new_u8(raw));
		return this;
	}
	public void Test__null()					{Gftest.Eq__bool_y(ttl == null, "ttl is not null", "raw", raw);}
	public void Test__page_txt(String expd)		{Gftest.Eq__str(expd, String_.new_u8(ttl.Page_txt()), "page_txt", "raw", raw);}
}

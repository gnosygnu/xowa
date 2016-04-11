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
package gplx.xowa.addons.apps.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*;
import gplx.xowa.specials.*;
public class Xoa_special_regy {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();	// NOTE: case-sensitive; case-insensitive requires lang
	public void Add(Xows_page page)					{hash.Add(page.Special__meta().Key_bry(), page);}
	public void Add_many(Xows_page... ary)	{for (Xows_page itm : ary) Add(itm);}
	public Xows_page Get_by_or_null(byte[] key)		{return (Xows_page)hash.Get_by(key);}
}

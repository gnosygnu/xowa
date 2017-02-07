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
package gplx.xowa.specials.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.xowa.specials.*;
public class Xoa_special_regy {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();	// NOTE: case-sensitive; case-insensitive requires lang, but regy is at app level
	public int Len() {return hash.Len();}
	public Xow_special_page Get_at(int i)					{return (Xow_special_page)hash.Get_at(i);}
	public Xow_special_page Get_by_or_null(byte[] key)		{return (Xow_special_page)hash.Get_by(key);}
	public void Add(Xow_special_page page)	{
		hash.Add(page.Special__meta().Key_bry(), page);
		byte[][] aliases = page.Special__meta().Aliases();
		for (byte[] alias : aliases)
			hash.Add(alias, page);
	}
	public void Add_many(Xow_special_page... ary)	{for (Xow_special_page itm : ary) Add(itm);}
}

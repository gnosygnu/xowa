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
package gplx.xowa.addons.wikis.searchs.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
public class Srch_word_hash {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	public void Clear()					{hash.Clear();}
	public int Len()					{return hash.Count();}
	public boolean Has(byte[] word)		{return hash.Has(word);}
	public Srch_word_itm Get_at(int i)	{return (Srch_word_itm)hash.Get_at(i);}
	public void Add(byte[] word) {
		Srch_word_itm itm = (Srch_word_itm)hash.Get_by(word);
		if (itm == null) {
			itm = new Srch_word_itm(word);
			hash.Add(word, itm);
		}
		itm.Count_add_1_();
	}
}

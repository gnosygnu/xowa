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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
public class Xows_db_word {
	public Xows_db_word(int id, byte[] text, int page_count) {this.id = id; this.text = text; this.page_count = page_count;}
	public int				Id()			{return id;}			private final int id;
	public byte[]			Text()			{return text;}			private final byte[] text;
	public int				Page_count()	{return page_count;}	private final int page_count;
	public int				Rslts_offset()	{return rslts_offset;}	private int rslts_offset;
	public boolean				Rslts_done()	{return rslts_done;}	private boolean rslts_done;
	public void Rslts_offset_add_1() {++rslts_offset;}
	public void Rslts_done_y_() {rslts_done = true;}
}
class Xows_db_word_sorter implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xows_db_word lhs = (Xows_db_word)lhsObj;
		Xows_db_word rhs = (Xows_db_word)rhsObj;
		return -Int_.Compare(lhs.Page_count(), rhs.Page_count());
	}
        public static final Xows_db_word_sorter Page_count_dsc = new Xows_db_word_sorter(); Xows_db_word_sorter() {}
}

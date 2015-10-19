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
import gplx.xowa.specials.search.parsers_old.*;
class Xows_db_cache {	// one cache per search term; EX: "Earth* AND (History OR Future) AND -"middle earth"" is one cache
	private final Ordered_hash hash = Ordered_hash_.New_bry();
	public Xows_db_word[]	Words()		{return words;}		private Xows_db_word[] words;		// words in cache; EX: earth, history, future but not "middle earth" (since not'ed)
	public Xows_db_matcher	Matcher()	{return matcher;}	private Xows_db_matcher matcher;	// criteria
	public boolean Done() {return done;} public void Done_y_() {done = true;} private boolean done;	// marks if db search is done
	public int Count() {return hash.Count();}
	public int Itms_end() {return itms_end;} public void Itms_end_(int v) {itms_end = v;} private int itms_end;
	public void Init_by_db(Cancelable cxl, byte[] raw, gplx.xowa.wikis.data.tbls.Xowd_search_word_tbl word_tbl) {
		this.matcher	= new Xow_search_parser().Parse(raw);
		this.words		= new Xows_db_matcher_bldr().Gather_words_for_db(cxl, matcher, word_tbl);
	}
	public boolean Has(byte[] key) {return hash.Has(key);}
	public Xows_db_row Get_at(int i) {return (Xows_db_row)hash.Get_at(i);}
	public void Add(Xows_db_row row) {hash.Add(row.Page_ttl().Full_db(), row);}
	public void Get_between(Xows_ui_rslt search_ui, int bgn, int end) {
		if (bgn >= hash.Count()) return;	// requested start not in cache; exit
		for (int i = bgn; i < end; ++i) {
			if (i >= hash.Count()) break;
			search_ui.Add((Xows_db_row)hash.Get_at(i));
		}
	}
	public void Sort() {hash.Sort_by(Xows_db_row_sorter.Page_len_dsc);}
}

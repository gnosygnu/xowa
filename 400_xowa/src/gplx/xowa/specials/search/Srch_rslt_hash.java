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
import gplx.xowa.specials.search.crts.*;
class Srch_rslt_hash {	// per search request; EX: 'Earth* AND (History OR Future) AND -"middle earth"'
	private final Ordered_hash hash = Ordered_hash_.New_bry();
	public Srch_crt_node	Ttl_matcher()	{return ttl_matcher;}	private Srch_crt_node ttl_matcher;
	public Srch_word_row[]	Words()			{return words;}			private Srch_word_row[] words;		// words to search db; EX: earth, history, future but not "middle earth" (since not'ed)
	public boolean Done() {return done;} public void Done_y_() {done = true;} private boolean done;			// marks if db search is done
	public int Count() {return hash.Count();}
	public int Itms_end() {return itms_end;} public void Itms_end_(int v) {itms_end = v;} private int itms_end;
	public void Init_by_db(Cancelable cxl, byte[] raw, gplx.xowa.wikis.data.tbls.Xowd_search_word_tbl word_tbl) {
		this.ttl_matcher	= new Srch_crt_parser().Parse(raw);
		this.words			= new Xows_db_matcher_bldr().Gather_words_for_db(cxl, ttl_matcher, word_tbl);
	}
	public boolean Has(byte[] key)			{return hash.Has(key);}
	public Srch_rslt_itm Get_at(int i)	{return (Srch_rslt_itm)hash.Get_at(i);}
	public void Add(Srch_rslt_itm itm)	{hash.Add(itm.page_ttl.Full_db(), itm);}
	public void Get_between(Srch_rslt_list search_ui, int bgn, int end) {
		if (bgn >= hash.Count()) return;	// requested start not in cache; exit
		for (int i = bgn; i < end; ++i) {
			if (i >= hash.Count()) break;
			search_ui.Add((Srch_rslt_itm)hash.Get_at(i));
		}
	}
	public void Sort() {hash.Sort_by(Srch_rslt_itm_sorter.Page_len_dsc);}
}

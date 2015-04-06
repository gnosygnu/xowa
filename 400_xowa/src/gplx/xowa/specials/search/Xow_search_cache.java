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
class Xow_search_qry {
	public Xow_search_qry(byte[] raw, int rslts_bgn, int rslts_len, Xow_search_word[] word_ary, Xow_search_matcher search_matcher) {
		this.raw = raw; this.rslts_bgn = rslts_bgn; this.rslts_len = rslts_len;
		this.word_ary = word_ary; this.search_matcher = search_matcher;			
	}
	public byte[] Raw() {return raw;} private final byte[] raw;
	public Xow_search_matcher Search_matcher() {return search_matcher;} private final Xow_search_matcher search_matcher;
	public Xow_search_word[] Word_ary() {return word_ary;} private final Xow_search_word[] word_ary;
	public int Rslts_bgn() {return rslts_bgn;} private final int rslts_bgn;
	public int Rslts_len() {return rslts_len;} private final int rslts_len;
	public int Rslts_end() {return rslts_bgn + rslts_len;}
}
class Xow_search_rslt {
	public Xow_search_rslt(byte[] page_key, int page_id, int page_ns, byte[] page_ttl, int page_len) {
		this.page_key = page_key;
		this.page_id = page_id;
		this.page_ns = page_ns;
		this.page_ttl = page_ttl;
		this.page_len = page_len;
	}
	public byte[] Page_key() {return page_key;} private final byte[] page_key;
	public int Page_id() {return page_id;} private final int page_id;
	public int Page_ns() {return page_ns;} private final int page_ns;
	public byte[] Page_ttl() {return page_ttl;} private final byte[] page_ttl;
	public int Page_len() {return page_len;} private final int page_len;
}
class Xow_search_cache {
	private final OrderedHash hash = OrderedHash_.new_bry_();		
	public boolean Done() {return done;} public void Done_y_() {done = true;} private boolean done;
	public int Count() {return hash.Count();}
	public boolean Has(byte[] key) {return hash.Has(key);}
	public void Add(Xow_search_rslt itm) {hash.Add(itm.Page_key(), itm);}
	public void Get_between(ListAdp rv, int bgn, int end) {
		rv.Clear();
		if (bgn >= hash.Count()) return;
		for (int i = bgn; i < end; ++i) {
			if (i >= hash.Count()) break;
			Xow_search_rslt itm = (Xow_search_rslt)hash.FetchAt(i);
			rv.Add(itm);
		}
	}
}
class Xow_search_word {
	public Xow_search_word(byte[] text) {this.text = text;}
	public byte[] Text() {return text;} private final byte[] text;
	public int Id() {return id;} public void Id_(int v) {id = v;} private int id = -1;
	public int Offset() {return offset;} public void Offset_add(int v) {offset += v;} private int offset;
	public boolean Done() {return done;} public void Done_y_() {done = true;} private boolean done;
}

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
import gplx.core.regxs.*;
public class Xows_db_matcher {
	private final Gfo_pattern raw_pattern;
	public Xows_db_matcher(int tid, byte[] raw, Xows_db_matcher lhs, Xows_db_matcher rhs) {
		this.tid = tid; this.raw = raw; this.lhs = lhs; this.rhs = rhs;
		this.raw_pattern = Bry_.Has(raw, Byte_ascii.Star) ? new Gfo_pattern(raw) : null;
	}
	public int Tid() {return tid;} private final int tid;
	public int Page_count() {return page_count;} public void Page_count_(int v) {page_count = v;} private int page_count;
	public byte[] Raw() {return raw;} private final byte[] raw;
	public Xows_db_matcher Lhs() {return lhs;} private final Xows_db_matcher lhs;
	public Xows_db_matcher Rhs() {return rhs;} private final Xows_db_matcher rhs;
	public boolean Matches(byte[] page_ttl_lc, byte[][] page_ttl_words) {
		switch (tid) {
			case Xows_db_matcher.Tid_word: {
				int len = page_ttl_words.length;
				for (int i = 0; i < len; ++i) {
					byte[] word = page_ttl_words[i];
					if (raw_pattern == null) {
						if (Bry_.Eq(word, raw)) return true;
					}
					else {
						if (raw_pattern.Match(word)) return true;
					}
				}
				return false;
			}
			case Xows_db_matcher.Tid_word_quote:	// note that raw does not have quotes; EX: "B*" -> B*
				return Bry_finder.Find_fwd(page_ttl_lc, raw) != Bry_finder.Not_found;
			case Xows_db_matcher.Tid_not:
				return !rhs.Matches(page_ttl_lc, page_ttl_words);
			case Xows_db_matcher.Tid_or:
				return lhs.Matches(page_ttl_lc, page_ttl_words) || rhs.Matches(page_ttl_lc, page_ttl_words);
			case Xows_db_matcher.Tid_and:
				return lhs.Matches(page_ttl_lc, page_ttl_words) && rhs.Matches(page_ttl_lc, page_ttl_words);
			case Xows_db_matcher.Tid_null: return false;
			default: throw Err_.new_unhandled(tid);
		}
	}
	public static final Xows_db_matcher Null = new Xows_db_matcher(Xows_db_matcher.Tid_null, null, null, null);
	public static Xows_db_matcher new_join(byte tid, Xows_db_matcher lhs, Xows_db_matcher rhs) {return new Xows_db_matcher(tid, null, lhs, rhs);}
	public static final int 
	  Tid_word			= 0
	, Tid_and			= 1
	, Tid_or			= 2
	, Tid_not			= 3
	, Tid_word_quote	= 4
	, Tid_null			= 5
	;
}

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
public class Xow_search_matcher {
	public Xow_search_matcher(int tid, byte[] raw, Xow_search_matcher lhs, Xow_search_matcher rhs) {
		this.tid = tid; this.raw = raw; this.lhs = lhs; this.rhs = rhs;
	}
	public int Tid() {return tid;} private final int tid;
	public byte[] Raw() {return raw;} private final byte[] raw;
	public Xow_search_matcher Lhs() {return lhs;} private final Xow_search_matcher lhs;
	public Xow_search_matcher Rhs() {return rhs;} private final Xow_search_matcher rhs;
	public boolean Matches(byte[] src) {
		switch (tid) {
			case Xow_search_matcher.Tid_word:
			case Xow_search_matcher.Tid_word_quote:
				return Bry_finder.Find_fwd(src, raw) != Bry_finder.Not_found;
			case Xow_search_matcher.Tid_not:
				return !lhs.Matches(src);
			case Xow_search_matcher.Tid_or:
				return lhs.Matches(src) || rhs.Matches(src);
			case Xow_search_matcher.Tid_and:
				return lhs.Matches(src) && rhs.Matches(src);
			case Xow_search_matcher.Tid_null: return false;
			default: throw Err_.unhandled(tid);
		}
	}
	public static final Xow_search_matcher Null = new Xow_search_matcher(Xow_search_matcher.Tid_null, null, null, null);
	public static Xow_search_matcher new_join(byte tid, Xow_search_matcher lhs, Xow_search_matcher rhs) {return new Xow_search_matcher(tid, null, lhs, rhs);}
	public static final int 
	  Tid_word			= 0
	, Tid_and			= 1
	, Tid_or			= 2
	, Tid_not			= 3
	, Tid_word_quote	= 4
	, Tid_null			= 5
	;
}

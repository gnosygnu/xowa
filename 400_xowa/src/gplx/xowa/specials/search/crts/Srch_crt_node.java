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
package gplx.xowa.specials.search.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.search.*;
import gplx.langs.regxs.*;
public class Srch_crt_node {
	private final Gfo_pattern raw_pattern;
	public Srch_crt_node(int tid, byte[] raw, Srch_crt_node lhs, Srch_crt_node rhs) {
		this.tid = tid; this.raw = raw; this.lhs = lhs; this.rhs = rhs;
		this.raw_pattern = Bry_.Has(raw, Byte_ascii.Star) ? new Gfo_pattern(raw) : null;
	}
	public final int tid;
	public final byte[] raw;
	public final Srch_crt_node lhs;
	public final Srch_crt_node rhs;

	public boolean Matches(gplx.xowa.langs.cases.Xol_case_mgr case_mgr, byte[] ttl) {
		byte[] ttl_lower = case_mgr.Case_build_lower(Xoa_ttl.Replace_unders(ttl));
		byte[][] ttl_words = Bry_split_.Split(ttl_lower, Byte_ascii.Space, Bool_.Y);
		return Matches(ttl_lower, ttl_words);
	}
	private boolean Matches(byte[] ttl_lower, byte[][] ttl_words) {
		switch (tid) {
			case Srch_crt_node.Tid_word: {
				int len = ttl_words.length;
				for (int i = 0; i < len; ++i) {
					byte[] word = ttl_words[i];
					if (raw_pattern == null) {
						if (Bry_.Eq(word, raw)) return true;
					}
					else {
						if (raw_pattern.Match(word)) return true;
					}
				}
				return false;
			}
			case Srch_crt_node.Tid_word_quote:	// note that raw does not have quotes; EX: "B*" -> B*
				return Bry_find_.Find_fwd(ttl_lower, raw) != Bry_find_.Not_found;
			case Srch_crt_node.Tid_not:
				return !rhs.Matches(ttl_lower, ttl_words);
			case Srch_crt_node.Tid_or:
				return lhs.Matches(ttl_lower, ttl_words) || rhs.Matches(ttl_lower, ttl_words);
			case Srch_crt_node.Tid_and:
				return lhs.Matches(ttl_lower, ttl_words) && rhs.Matches(ttl_lower, ttl_words);
			case Srch_crt_node.Tid_null: return false;
			default: throw Err_.new_unhandled(tid);
		}
	}

	public static final Srch_crt_node Null = new Srch_crt_node(Srch_crt_node.Tid_null, null, null, null);
	public static final int 
	  Tid_word			= 0		// EX: 'A'
	, Tid_and			= 1		// EX: 'A B'
	, Tid_or			= 2		// EX: 'A OR B'
	, Tid_not			= 3		// EX: '-A'
	, Tid_word_quote	= 4		// EX: '"A B"'
	, Tid_null			= 5
	;
}

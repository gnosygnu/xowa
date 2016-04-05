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
package gplx.xowa.addons.apps.searchs.searchers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.searchs.*;
import gplx.core.btries.*;
import gplx.xowa.addons.apps.searchs.searchers.crts.*;
public class Srch_search_phrase {
	public Srch_search_phrase(boolean wildcard, byte[] orig, byte[] compiled) {
		this.Orig = orig;
		this.Compiled = compiled;
		this.Wildcard = wildcard;
	}
	public final    boolean   Wildcard;
	public final    byte[] Orig;			// EX: "Earth"
	public final    byte[] Compiled;		// EX: "earth*"

	public static Srch_search_phrase New(gplx.xowa.langs.cases.Xol_case_mgr case_mgr, byte[] orig, boolean wildcard) {
		int orig_len = orig.length;
		if (	orig_len > 0	// if "*" at end, remove and change to wildcard; needed for Special:Search which will send in "earth*" but "earth" needed for highlighting
			&&	orig[orig_len - 1] == Srch_crt_scanner_syms.Dflt.Wild()) {
			orig = Bry_.Mid(orig, 0, orig_len - 1);
			wildcard = true;
		}
		byte[] lcase = case_mgr.Case_build_lower(orig);
		lcase = Auto_wildcard(lcase, Srch_crt_scanner_syms.Dflt);
		return new Srch_search_phrase(wildcard, orig, lcase);
	}
	public static byte[] Auto_wildcard(byte[] raw, Srch_crt_scanner_syms syms) {
		Btrie_slim_mgr trie = syms.Trie();
		int raw_len = raw.length;
		int insert_pos = -1;
		int fail_pos = -1;
		for (int i = raw_len - 1; i > -1; --i) {
			byte b = raw[i];
			byte tid = trie.Match_byte_or(b, raw, i, i + 1, Byte_.Max_value_127);
			if (tid == Byte_.Max_value_127) {	// unknown sym
				if		(b == syms.Wild()) {	// wildcard is not tokenized
					fail_pos = i;
					break;
				}
				else {							// alphanum-char
					insert_pos = i;
					break;
				}
			}
			else {
				switch (tid) {
					case Srch_crt_tkn.Tid__quote:
					case Srch_crt_tkn.Tid__space:
					case Srch_crt_tkn.Tid__not:
					case Srch_crt_tkn.Tid__and:
					case Srch_crt_tkn.Tid__or:
					case Srch_crt_tkn.Tid__paren_bgn:
						fail_pos = i;								// these symbols will not auto-wildcard, unless they are escaped
						i = -1;
						break;
					case Srch_crt_tkn.Tid__escape:
						if (i > 0) {
							int prv_pos = i -1;
							if (raw[prv_pos] == syms.Escape()) {	// an escaped escape can be wildcarded; EX: "\\" -> "\\*"
								insert_pos = i;
								i = -1;
							}
							else
								fail_pos = i;
						}
						else
							fail_pos = i;
						i = -1;
						break;
					case Srch_crt_tkn.Tid__paren_end:
						break;
				}
			}
		}

		// check if preceded by escape
		if (insert_pos == -1) {
			if (	fail_pos > 0
				&&	raw[fail_pos - 1] == syms.Escape()) {
					insert_pos = fail_pos;
				}
			else
				return raw;
		}

		// check if word already has wildcard; EX: "a*b" x> "a*b*"
		for (int i = insert_pos - 1; i > -1; --i) {
			byte b = raw[i];
			if (b == syms.Wild()) {
				int prv_pos = i - 1;
				if (prv_pos > -1) {
					if (raw[prv_pos] == syms.Escape()) {	// ignore escaped wildcard
						i = prv_pos;
						continue;
					}
				}
				return raw;	// existing wildcard cancels auto-wildcard
			}
			else if (b == syms.Space()) {	// stop looking when word ends
				break;
			}
			else {}	// alphanum; keep going
		}

		// add wildcard
		if (insert_pos == raw_len - 1) return Bry_.Add(raw, syms.Wild());
		else {
			byte[] rv = new byte[raw_len + 1];
			int wildcard_pos = insert_pos + 1;
			for (int i = 0; i < wildcard_pos; ++i)
				rv[i] = raw[i];
			rv[wildcard_pos] = syms.Wild();
			for (int i = wildcard_pos; i < raw_len; ++i)
				rv[i + 1] = raw[i];
			return rv;
		}
	}
}

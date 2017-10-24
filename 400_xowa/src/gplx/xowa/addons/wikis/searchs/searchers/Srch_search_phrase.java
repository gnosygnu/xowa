/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.addons.wikis.searchs.searchers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.core.btries.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
public class Srch_search_phrase {
	public Srch_search_phrase(boolean wildcard, byte[] orig, byte[] compiled, Srch_crt_scanner_syms syms) {
		this.Orig = orig;
		this.Compiled = compiled;
		this.Wildcard = wildcard;
		this.Syms = syms;
	}
	public final    boolean   Wildcard;
	public final    byte[] Orig;			// EX: "Earth"
	public final    byte[] Compiled;		// EX: "earth*"
	public final    Srch_crt_scanner_syms Syms;

	public static Srch_search_phrase New(gplx.xowa.langs.cases.Xol_case_mgr case_mgr, Srch_crt_scanner_syms syms, boolean auto_wildcard, byte[] orig) {
		int orig_len = orig.length;
		if (	orig_len > 0	// if "*" at end, remove and change to wildcard; needed for Special:Search which will send in "earth*" but "earth" needed for highlighting
			&&	orig[orig_len - 1] == syms.Wild()) {
			orig = Bry_.Mid(orig, 0, orig_len - 1);
			auto_wildcard = true;
		}
		byte[] lcase = case_mgr.Case_build_lower(orig);
		lcase = Auto_wildcard(lcase, auto_wildcard, syms);
		return new Srch_search_phrase(auto_wildcard, orig, lcase, syms);
	}
	public static byte[] Auto_wildcard(byte[] raw, boolean auto_wildcard, Srch_crt_scanner_syms syms) {
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
		if (insert_pos == raw_len - 1) 
			return auto_wildcard ? Bry_.Add(raw, syms.Wild()) : raw;
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

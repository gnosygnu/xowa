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
package gplx.xowa.addons.wikis.searchs.searchers.crts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
import gplx.core.btries.*;
public class Srch_crt_scanner_syms {
	public Srch_crt_scanner_syms(byte escape, byte space, byte quote, byte not, byte and, byte or, byte paren_bgn, byte paren_end, byte wild) {
		this.escape = escape; this.space = space; this.quote = quote;
		this.not = not; this.and = and; this.and_bry = Bry_.New_by_byte(and); this.or = or;
		this.paren_bgn = paren_bgn; this.paren_end = paren_end;
		this.wild = wild;
		this.trie = Btrie_slim_mgr.cs();
		Make_trie(trie, this);
	}
	public byte Escape() {return escape;} private byte escape;
	public byte Space() {return space;} private byte space;
	public byte Quote() {return quote;} private byte quote;
	public byte Not() {return not;} private byte not;
	public byte And() {return and;} private byte and;
	public byte[] And_bry() {return and_bry;} private byte[] and_bry;
	public byte Or() {return or;} private byte or;
	public byte Paren_bgn() {return paren_bgn;} private byte paren_bgn;
	public byte Paren_end() {return paren_end;} private byte paren_end;
	public byte Wild() {return wild;} private byte wild;
	public Btrie_slim_mgr Trie() {return trie;} private final    Btrie_slim_mgr trie;
	public byte[] To_bry() {
		Bry_bfr bfr = Bry_bfr_.New();
		To_bry__add(bfr, "wild"			, wild);
		To_bry__add(bfr, "not"			, not);
		To_bry__add(bfr, "or"			, or);
		To_bry__add(bfr, "and"			, and);
		To_bry__add(bfr, "quote"		, quote);
		To_bry__add(bfr, "paren_bgn"	, paren_bgn);
		To_bry__add(bfr, "paren_end"	, paren_end);
		To_bry__add(bfr, "escape"		, escape);
		To_bry__add(bfr, "space"		, space);
		return bfr.To_bry_and_clear();
	}
	public void Parse(byte[] src) {
		byte[][] lines = Bry_split_.Split_lines(src);
		escape = space = quote = not = and = or = paren_bgn = paren_end = wild = Byte_.Zero;
		for (byte[] line : lines) {
			int line_len = line.length;
			int eq_pos = Bry_find_.Find_fwd(line, Byte_ascii.Eq, 0, line_len); if (eq_pos == Bry_find_.Not_found) continue;
			String key = String_.new_u8(Bry_.Mid(line, 0, eq_pos));
			byte val = Parse__val(line, eq_pos + 1, line_len);
			if		(String_.Eq(key, "wild"			)) wild = val;
			else if	(String_.Eq(key, "not"			)) not = val;
			else if	(String_.Eq(key, "or"			)) or = val;
			else if	(String_.Eq(key, "and"			)) and = val;
			else if	(String_.Eq(key, "quote"		)) quote = val;
			else if	(String_.Eq(key, "paren_bgn"	)) paren_bgn = val;
			else if	(String_.Eq(key, "paren_end"	)) paren_end = val;
			else if	(String_.Eq(key, "escape"		)) escape = val;
			else if	(String_.Eq(key, "space"		)) space = val;
		}
	}
	private static void To_bry__add(Bry_bfr bfr, String key, byte val) {
		bfr.Add_str_u8(key).Add_byte_eq();
		switch (val) {
			case Byte_ascii.Null	: bfr.Add_str_a7("\\0"); break;
			case Byte_ascii.Space	: bfr.Add_str_a7("\\s"); break;
			default					: bfr.Add_byte(val); break;
		}			
		bfr.Add_byte_nl();
	}
	private static void Make_trie(Btrie_slim_mgr trie, Srch_crt_scanner_syms bldr) {
		Make_trie__add(trie, bldr.Escape()		, Srch_crt_tkn.Tid__escape);
		Make_trie__add(trie, bldr.Space()		, Srch_crt_tkn.Tid__space);
		Make_trie__add(trie, bldr.Quote()		, Srch_crt_tkn.Tid__quote);
		Make_trie__add(trie, bldr.Not()			, Srch_crt_tkn.Tid__not);
		Make_trie__add(trie, bldr.And()			, Srch_crt_tkn.Tid__and);
		Make_trie__add(trie, bldr.Or()			, Srch_crt_tkn.Tid__or);
		Make_trie__add(trie, bldr.Paren_bgn()	, Srch_crt_tkn.Tid__paren_bgn);
		Make_trie__add(trie, bldr.Paren_end()	, Srch_crt_tkn.Tid__paren_end);
	}
	private static void Make_trie__add(Btrie_slim_mgr rv, byte b, byte tid) {
		if (b == Byte_ascii.Null) return;
		rv.Add_bry_byte(b, tid);
	}
	public static Srch_crt_scanner_syms New__dflt() {
		return new Srch_crt_scanner_syms
		( Byte_ascii.Backslash, Byte_ascii.Space, Byte_ascii.Quote, Byte_ascii.Dash, Byte_ascii.Plus, Byte_ascii.Comma
		, Byte_ascii.Paren_bgn, Byte_ascii.Paren_end, Byte_ascii.Star
		);
	}
	public static final    Srch_crt_scanner_syms Dflt = New__dflt();
	private static byte Parse__val(byte[] line, int val_bgn, int line_len) {
		if (line_len - val_bgn == 1) return line[val_bgn];
		if (	line_len - val_bgn == 2
			&&	line[val_bgn] == Byte_ascii.Backslash) {
			byte val_byte = line[val_bgn + 1];
			switch (val_byte) {
				case Byte_ascii.Num_0: return Byte_ascii.Null;
				case Byte_ascii.Ltr_s: return Byte_ascii.Space;
			}
		}
		return Byte_ascii.Null;
	}
}

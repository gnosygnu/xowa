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
package gplx.xowa.addons.wikis.searchs.searchers.crts;
import gplx.core.btries.*;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.custom.brys.BrySplit;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
public class Srch_crt_scanner_syms {
	public Srch_crt_scanner_syms(byte escape, byte space, byte quote, byte not, byte and, byte or, byte paren_bgn, byte paren_end, byte wild) {
		this.escape = escape; this.space = space; this.quote = quote;
		this.not = not; this.and = and; this.and_bry = BryUtl.NewByByte(and); this.or = or;
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
	public Btrie_slim_mgr Trie() {return trie;} private final Btrie_slim_mgr trie;
	public byte[] To_bry() {
		BryWtr bfr = BryWtr.New();
		To_bry__add(bfr, "wild"			, wild);
		To_bry__add(bfr, "not"			, not);
		To_bry__add(bfr, "or"			, or);
		To_bry__add(bfr, "and"			, and);
		To_bry__add(bfr, "quote"		, quote);
		To_bry__add(bfr, "paren_bgn"	, paren_bgn);
		To_bry__add(bfr, "paren_end"	, paren_end);
		To_bry__add(bfr, "escape"		, escape);
		To_bry__add(bfr, "space"		, space);
		return bfr.ToBryAndClear();
	}
	public void Parse(byte[] src) {
		byte[][] lines = BrySplit.SplitLines(src);
		escape = space = quote = not = and = or = paren_bgn = paren_end = wild = ByteUtl.Zero;
		for (byte[] line : lines) {
			int line_len = line.length;
			int eq_pos = BryFind.FindFwd(line, AsciiByte.Eq, 0, line_len); if (eq_pos == BryFind.NotFound) continue;
			String key = StringUtl.NewU8(BryLni.Mid(line, 0, eq_pos));
			byte val = Parse__val(line, eq_pos + 1, line_len);
			if		(StringUtl.Eq(key, "wild"			)) wild = val;
			else if	(StringUtl.Eq(key, "not"			)) not = val;
			else if	(StringUtl.Eq(key, "or"			)) or = val;
			else if	(StringUtl.Eq(key, "and"			)) and = val;
			else if	(StringUtl.Eq(key, "quote"		)) quote = val;
			else if	(StringUtl.Eq(key, "paren_bgn"	)) paren_bgn = val;
			else if	(StringUtl.Eq(key, "paren_end"	)) paren_end = val;
			else if	(StringUtl.Eq(key, "escape"		)) escape = val;
			else if	(StringUtl.Eq(key, "space"		)) space = val;
		}
	}
	private static void To_bry__add(BryWtr bfr, String key, byte val) {
		bfr.AddStrU8(key).AddByteEq();
		switch (val) {
			case AsciiByte.Null	: bfr.AddStrA7("\\0"); break;
			case AsciiByte.Space	: bfr.AddStrA7("\\s"); break;
			default					: bfr.AddByte(val); break;
		}			
		bfr.AddByteNl();
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
		if (b == AsciiByte.Null) return;
		rv.Add_bry_byte(b, tid);
	}
	public static Srch_crt_scanner_syms New__dflt() {
		return new Srch_crt_scanner_syms
		( AsciiByte.Backslash, AsciiByte.Space, AsciiByte.Quote, AsciiByte.Dash, AsciiByte.Plus, AsciiByte.Comma
		, AsciiByte.ParenBgn, AsciiByte.ParenEnd, AsciiByte.Star
		);
	}
	public static final Srch_crt_scanner_syms Dflt = New__dflt();
	private static byte Parse__val(byte[] line, int val_bgn, int line_len) {
		if (line_len - val_bgn == 1) return line[val_bgn];
		if (	line_len - val_bgn == 2
			&&	line[val_bgn] == AsciiByte.Backslash) {
			byte val_byte = line[val_bgn + 1];
			switch (val_byte) {
				case AsciiByte.Num0: return AsciiByte.Null;
				case AsciiByte.Ltr_s: return AsciiByte.Space;
			}
		}
		return AsciiByte.Null;
	}
}

package gplx.types.custom.brys.wtrs;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.IntRef;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.errs.ErrUtl;
public class BryUtlByWtr {
	public static String NewU8NlSwapAposAsStr(String... lines) {return StringUtl.NewU8(NewU8NlSwapApos(lines));}
	public static byte[] NewU8NlSwapApos(String... lines) {
		BryWtr bfr = BryBfrUtl.Get();
		try {
			NewU8NlSwapApos(bfr, lines);
			return bfr.ToBryAndClear();
		}
		finally {bfr.MkrRls();}
	}
	public static void NewU8NlSwapApos(BryWtr bfr, String... lines) {
		int lines_len = lines.length;
		for (int i = 0; i < lines_len; ++i) {
			if (i != 0) bfr.AddByteNl();
			byte[] line = BryUtl.NewU8(lines[i]);
			boolean dirty = false;
			int prv = 0;
			int line_len = line.length;
			for (int j = 0; j < line_len; ++j) {
				byte b = line[j];
				if (b == AsciiByte.Apos) {
					bfr.AddMid(line, prv, j);
					bfr.AddByte(AsciiByte.Quote);
					dirty = true;
					prv = j + 1;
				}
			}
			if (dirty)
				bfr.AddMid(line, prv, line_len);
			else
				bfr.Add(line);
		}
	}
	public static GfoDate ReadCsvDte(byte[] ary, IntRef posRef, byte lkp) {// ASSUME: fmt = yyyyMMdd HHmmss.fff
		int y = 0, M = 0, d = 0, H = 0, m = 0, s = 0, f = 0;
		int bgn = posRef.Val();
		y += (ary[bgn +  0] - BryUtl.AsciiZero) * 1000;
		y += (ary[bgn +  1] - BryUtl.AsciiZero) *  100;
		y += (ary[bgn +  2] - BryUtl.AsciiZero) *   10;
		y += (ary[bgn +  3] - BryUtl.AsciiZero);
		M += (ary[bgn +  4] - BryUtl.AsciiZero) *   10;
		M += (ary[bgn +  5] - BryUtl.AsciiZero);
		d += (ary[bgn +  6] - BryUtl.AsciiZero) *   10;
		d += (ary[bgn +  7] - BryUtl.AsciiZero);
		H += (ary[bgn +  9] - BryUtl.AsciiZero) *   10;
		H += (ary[bgn + 10] - BryUtl.AsciiZero);
		m += (ary[bgn + 11] - BryUtl.AsciiZero) *   10;
		m += (ary[bgn + 12] - BryUtl.AsciiZero);
		s += (ary[bgn + 13] - BryUtl.AsciiZero) *   10;
		s += (ary[bgn + 14] - BryUtl.AsciiZero);
		f += (ary[bgn + 16] - BryUtl.AsciiZero) *  100;
		f += (ary[bgn + 17] - BryUtl.AsciiZero) *   10;
		f += (ary[bgn + 18] - BryUtl.AsciiZero);
		if (ary[bgn + 19] != lkp) throw ErrUtl.NewArgs("csv date is invalid", "txt", StringUtl.NewU8ByLen(ary, bgn, 20));
		posRef.ValAdd(19 + 1); // +1=lkp.len
		return GfoDateUtl.New(y, M, d, H, m, s, f);
	}
	public static String ReadCsvStr(byte[] ary, IntRef posRef, byte lkp)                {return StringUtl.NewU8(ReadCsvBry(ary, posRef, lkp, true));}
	public static byte[] ReadCsvBry(byte[] ary, IntRef posRef, byte lkp)                {return ReadCsvBry(ary, posRef, lkp, true);}
	public static byte[] ReadCsvBry(byte[] ary, IntRef posRef, byte lkp, boolean make) {
		int bgn = posRef.Val(), aryLen = ary.length;
		BryWtr bb = null;
		if (aryLen > 0 && ary[0] == BryUtl.DlmQuote) {
			int pos = bgn + 1;    // +1 to skip quote
			if (make) bb = BryWtr.New();
			while (true) {
				if (pos == aryLen) throw ErrUtl.NewArgs("endOfAry reached, but no quote found", "txt", StringUtl.NewU8ByLen(ary, bgn, pos));
				byte b = ary[pos];
				if (b == BryUtl.DlmQuote) {
					if (pos == aryLen - 1) throw ErrUtl.NewArgs("endOfAry reached, quote found but lkp not", "txt", StringUtl.NewU8ByLen(ary, bgn, pos));
					byte next = ary[pos + 1];
					if        (next == BryUtl.DlmQuote) {    // byte followed by quote
						if (make) bb.AddByte(b);
						pos += 2;
					}
					else if (next == lkp) {
						posRef.ValSet(pos + 2);    // 1=endQuote;1=lkp;
						return make ? bb.ToBry() : BryUtl.Empty;
					}
					else throw ErrUtl.NewArgs("quote found, but not doubled", "txt", StringUtl.NewU8ByLen(ary, bgn, pos + 1));
				}
				else {
					if (make) bb.AddByte(b);
					pos++;
				}
			}
		}
		else {
			for (int i = bgn; i < aryLen; i++) {
				if (ary[i] == lkp) {
					posRef.ValSet(i + 1);    // +1 = lkp.Len
					return make ? BryLni.Mid(ary, bgn, i) : BryUtl.Empty;
				}
			}
			throw ErrUtl.NewArgs("lkp failed", "lkp", (char)lkp, "txt", StringUtl.NewU8ByLen(ary, bgn, aryLen));
		}
	}
	public static int ReadCsvInt(byte[] ary, IntRef posRef, byte lkp) {
		int bgn = posRef.Val();
		int pos = BryFind.FindFwd(ary, lkp, bgn, ary.length);
		if (pos == BryFind.NotFound) throw ErrUtl.NewArgs("lkp failed", "lkp", (char)lkp, "bgn", bgn);
		int rv = BryUtl.ToIntOr(ary, posRef.Val(), pos, -1);
		posRef.ValSet(pos + 1);    // +1 = lkp.Len
		return rv;
	}
	public static byte[] ReplaceSafe(BryWtr bfr, byte[] src, byte[] find, byte[] repl) {
		if (src == null || find == null || repl == null) return null;
		return Replace(bfr, src, find, repl, 0, src.length);
	}
	public static byte[] Replace(BryWtr bfr, byte[] src, byte[] find, byte[] repl) {return Replace(bfr, src, find, repl, 0, src.length);}
	public static byte[] Replace(BryWtr bfr, byte[] src, byte[] find, byte[] repl, int src_bgn, int src_end) {return Replace(bfr, src, find, repl, src_bgn, src_end, IntUtl.MaxValue);}
	public static byte[] Replace(BryWtr bfr, byte[] src, byte[] find, byte[] repl, int src_bgn, int src_end, int limit) {
		int pos = src_bgn;
		boolean dirty = false;
		int find_len = find.length;
		int bfr_bgn = pos;
		int replace_count = 0;
		while (pos < src_end) {
			int find_pos = BryFind.FindFwd(src, find, pos);
			if (find_pos == BryFind.NotFound) break;
			dirty = true;
			bfr.AddMid(src, bfr_bgn, find_pos);
			bfr.Add(repl);
			pos = find_pos + find_len;
			bfr_bgn = pos;
			++replace_count;
			if (replace_count == limit) break;
		}
		if (dirty)
			bfr.AddMid(src, bfr_bgn, src_end);
		return dirty ? bfr.ToBryAndClear() : src;
	}
	public static byte[] Replace(byte[] src, byte[] find, byte[] replace) {return ReplaceBetween(src, find, null, replace);}
	public static byte[] ReplaceBetween(byte[] src, byte[] bgn, byte[] end, byte[] replace) {
		BryWtr bfr = BryWtr.New();
		boolean replace_all = end == null;
		int src_len = src.length, bgn_len = bgn.length, end_len = replace_all ? 0 : end.length;
		int pos = 0;
		while (true) {
			if (pos >= src_len) break;
			int bgn_pos = BryFind.FindFwd(src, bgn, pos);
			if (bgn_pos == BryFind.NotFound) {
				bfr.AddMid(src, pos, src_len);
				break;
			}
			else {
				int bgn_rhs = bgn_pos + bgn_len;
				int end_pos = replace_all ? bgn_rhs : BryFind.FindFwd(src, end, bgn_rhs);
				if (end_pos == BryFind.NotFound) {
					bfr.AddMid(src, pos, src_len);
					break;
				}
				else {
					bfr.AddMid(src, pos, bgn_pos);
					bfr.Add(replace);
					pos = end_pos + end_len;
				}
			}
		}
		return bfr.ToBryAndClear();
	}
	public static byte[] ReplaceMany(byte[] src, byte[] find, byte[] repl) {
		BryWtr bfr = null;
		int src_len = src.length;
		int find_len = find.length;

		int pos = 0;
		while (true) {
			// find find_bgn
			int find_bgn = BryFind.FindFwd(src, find, pos, src_len);

			// exit if nothing found
			if (find_bgn == BryFind.NotFound)
				break;

			// lazy-instantiation
			if (bfr == null)
				bfr = BryWtr.New();

			// add everything up to find_bgn
			bfr.AddMid(src, pos, find_bgn);

			// add repl
			bfr.Add(repl);

			// move pos forward
			pos = find_bgn + find_len;
		}

		// nothing found; return src
		if (bfr == null)
			return src;
		else {
			// add rest
			bfr.AddMid(src, pos, src_len);
			return bfr.ToBryAndClear();
		}
	}
	public static byte[] ReplaceNlWithTab(byte[] src, int bgn, int end) {
		return BryUtl.Replace(BryLni.Mid(src, bgn, end), AsciiByte.Nl, AsciiByte.Tab);
	}
	public static byte[] XcaseBuildAll(BryWtr tmp, boolean upper, byte[] src) {
		if (src == null) return null;
		int src_bgn = 0;
		int src_end = src.length;
		int lbound = 96, ubound = 123;
		if (!upper) {
			lbound = 64; ubound =  91;
		}

		boolean dirty = false;
		for (int i = src_bgn; i < src_end; i++) {
			byte b = src[i];
			if (b > lbound && b < ubound) {
				if (!dirty) {
					dirty = true;
					tmp.AddMid(src, src_bgn, i);
				}
				if (upper)
					b -= 32;
				else
					b += 32;
			}
			if (dirty)
				tmp.AddByte(b);
		}
		return dirty ? tmp.ToBryAndClear() : src;
	}
	public static byte[] EscapeWs(byte[] bry) {BryWtr bfr = BryBfrUtl.Get(); byte[] rv = EscapeWs(bfr, bry); bfr.MkrRls(); return rv;}
	public static byte[] EscapeWs(BryWtr bfr, byte[] src) {
		boolean dirty = false;
		int len = src.length;
		for (int i = 0; i < len; ++i) {
			byte b = src[i];
			byte escape = ByteUtl.Zero;
			switch (b) {
				case AsciiByte.Tab:        escape = AsciiByte.Ltr_t; break;
				case AsciiByte.Nl:            escape = AsciiByte.Ltr_n; break;
				case AsciiByte.Cr:            escape = AsciiByte.Ltr_r; break;
				default:                    if (dirty) bfr.AddByte(b); break;
			}
			if (escape != ByteUtl.Zero) {
				if (!dirty) {
					dirty = true;
					bfr.AddMid(src, 0, i);
				}
				bfr.AddByteBackslash().AddByte(escape);
			}
		}
		return dirty ? bfr.ToBryAndClear() : src;
	}
	public static byte[] ResolveEscape(BryWtr bfr, byte escape, byte[] raw, int bgn, int end) {
		int pos = bgn;
		boolean dirty = false;
		while (pos < end) {
			byte b = raw[pos];
			if (b == escape) {
				if (!dirty) {
					dirty = true;
					bfr.AddMid(raw, bgn, pos);
				}
				++pos;
				if (pos < end) {    // check for eos; note that this ignores trailing "\"; EX: "a\" -> "a"
					bfr.AddByte(raw[pos]);
					++pos;
				}
			}
			else {
				if (dirty) bfr.AddByte(b);
				++pos;
			}
		}
		return dirty ? bfr.ToBryAndClear() : raw;
	}
}

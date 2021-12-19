/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.custom.brys.rdrs;
import gplx.core.btries.Btrie_rv;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.core.encoders.Gfo_hzip_int_;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.ByteVal;
import gplx.types.basics.wrappers.IntRef;
import gplx.types.custom.brys.BryFind;
public class BryRdr {
	private final IntRef posRef = IntRef.NewNeg1();
	private final Btrie_rv trv = new Btrie_rv();
	public byte[] Src() {return src;} protected byte[] src;
	public int SrcEnd() {return srcEnd;} protected int srcEnd;
	public int Pos() {return pos;} protected int pos;
	public boolean PosIsEos() {return pos == srcEnd;}
	public boolean PosIsReading() {return pos > -1 && pos < srcEnd;}
	public byte Cur() {return src[pos];}
	public byte Nxt() {return pos + 1 >= srcEnd ? NotFound : src[pos + 1];}
	public BryRdr DfltDlmSet(byte b) {this.dfltDlm = b; return this;} private byte dfltDlm;
	public BryRdr FailThrowsErrSet(boolean v) {errWkr.FailThrowsErrSet(v); return this;}
	public BryRdr InitBySrc(byte[] src)                                        {errWkr.InitByPage("", src);                        this.pos = 0; this.src = src; this.srcEnd = src.length; return this;}
	public BryRdr InitByPage(byte[] page, byte[] src, int srcLen)            {errWkr.InitByPage(StringUtl.NewU8(page), src);    this.pos = 0; this.src = src; this.srcEnd = srcLen; return this;}
	public BryRdr InitBySect(String sect, int sectBgn, int pos)                {errWkr.InitBySect(sect, sectBgn);                this.pos = pos; return this;}
	public BryRdr InitByWkr(BryRdrErrWkr wkr, String sect, int pos, int srcEnd)   {
		this.pos = pos; this.src = wkr.Src(); this.srcEnd = srcEnd;
		errWkr.InitByPage(wkr.Page(), src);
		errWkr.InitBySect(sect, pos);
		return this;
	}
	public BryRdrErrWkr ErrWkr()            {return errWkr;} private BryRdrErrWkr errWkr = new BryRdrErrWkr();
	public int MoveTo(int v)            {this.pos = v; return pos;}
	public int MoveToLast()           {this.pos = srcEnd - 1; return pos;}
	public int MoveToEnd()            {this.pos = srcEnd; return pos;}
	public int MoveByOne()            {return MoveBy(1);}
	public int MoveBy(int v)            {this.pos += v; return pos;}
	public int FindFwdLr()            {return FindFwd(dfltDlm, BoolUtl.Y, BoolUtl.N, FailIfMissing);}
	public int FindFwdLr(byte find)    {return FindFwd(find        , BoolUtl.Y, BoolUtl.N, FailIfMissing);}
	public int FindFwdLrOr(byte find, int or)
										{return FindFwd(find        , BoolUtl.Y, BoolUtl.N, or);}
	public int FindFwdLr(String find) {return FindFwd(BryUtl.NewU8(find), BoolUtl.Y, BoolUtl.N, FailIfMissing);}
	public int FindFwdLr(byte[] find) {return FindFwd(find        , BoolUtl.Y, BoolUtl.N, FailIfMissing);}
	public int FindFwdLrOr(String find, int or) {return FindFwd(BryUtl.NewU8(find), BoolUtl.Y, BoolUtl.N, or);}
	public int FindFwdLrOr(byte[] find, int or)
										{return FindFwd(find        , BoolUtl.Y, BoolUtl.N, or);}
	public int FindFwdRrOr(byte[] find, int or)
										{return FindFwd(find        , BoolUtl.N, BoolUtl.N, or);}
	private int FindFwd(byte find, boolean retLhs, boolean posLhs, int or) {
		int find_pos = BryFind.FindFwd(src, find, pos, srcEnd);
		if (find_pos == BryFind.NotFound) {
			if (or == FailIfMissing) {
				errWkr.Fail("find failed", "find", AsciiByte.ToStr(find));
				return BryFind.NotFound;
			}
			else
				return or;
		}
		pos = find_pos + (posLhs ? 0 : 1);
		return retLhs ? find_pos : pos;
	}
	private int FindFwd(byte[] find, boolean retLhs, boolean posLhs, int or) {
		int findPos = BryFind.FindFwd(src, find, pos, srcEnd);
		if (findPos == BryFind.NotFound) {
			if (or == FailIfMissing) {
				errWkr.Fail("find failed", "find", StringUtl.NewU8(find));
				return BryFind.NotFound;
			}
			else
				return or;
		}
		pos = findPos + (posLhs ? 0 : find.length);
		return retLhs ? findPos : pos;
	}
	public byte ReadByte() {
		byte rv = src[pos];
		++pos;
		return rv;
	}
	public byte ReadByteTo() {return ReadByteTo(dfltDlm);}
	public byte ReadByteTo(byte toChar) {
		byte rv = src[pos];
		++pos;
		if (pos < srcEnd) {
			if (src[pos] != toChar) {errWkr.Fail("read byte to failed", "to", AsciiByte.ToStr(toChar)); return ByteUtl.MaxValue127;}
			++pos;
		}
		return rv;
	}
	public double ReadDoubleTo()            {return ReadDoubleTo(dfltDlm);}
	public double ReadDoubleTo(byte toChar) {
		byte[] bry = ReadBryTo(toChar);
		return DoubleUtl.Parse(StringUtl.NewA7(bry));
	}
	public int ReadIntTo()            {return ReadIntTo(dfltDlm);}
	public int ReadIntToNonNum()    {return ReadIntTo(AsciiByte.Null);}
	public int ReadIntTo(byte toChar) {
		int bgn = pos;
		int rv = 0;
		int negative = 1;
		while (pos < srcEnd) {
			byte b = src[pos++];
			switch (b) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					rv = (rv * 10) + (b - AsciiByte.Num0);
					break;
				case AsciiByte.Dash:
					if (negative == -1) {    // 2nd negative
						errWkr.Fail("invalid int", "mid", StringUtl.NewU8(src, bgn, pos));
						return IntUtl.MinValue;
					}
					else                    // 1st negative
						negative = -1;        // flag negative
					break;
				default: {
					boolean match = b == toChar;
					if (toChar == AsciiByte.Null) {// hack for ReadIntToNonNum
						--pos;
						match = true;
					}
					if (!match) {
						errWkr.Fail("invalid int", "mid", StringUtl.NewU8(src, bgn, pos));
						return IntUtl.MinValue;
					}
					return rv * negative;
				}
			}
		}
		if (bgn == pos) {errWkr.Fail("int is empty"); return IntUtl.MinValue;}
		return rv * negative;
	}
	public int ReadHzipInt(int reqd) {
		int rv = Gfo_hzip_int_.Decode(reqd, src, srcEnd, pos, posRef);
		pos = posRef.Val();
		return rv;
	}
	public byte[] ReadBryTo() {return ReadBryTo(dfltDlm);}
	public byte[] ReadBryTo(byte b) {
		int bgn = pos;
		return BryLni.Mid(src, bgn, FindFwdLr(b));
	}
	public int MoveFwdWhile(byte b) {
		while (pos < srcEnd) {
			if (src[pos] != b) {
				break;
			}
			pos++;
		}
		return pos;
	}
	public int MoveBwdWhile(byte b) {
		while (pos > -1) {
			if (src[pos] != b) {
				return pos + 1; // return position which is start of b sequence
			}
			pos--;
		}
		return -1;
	}
	public boolean Match(byte[] find) { // same as Is but no auto-move
		int findLen = find.length;
		int findEnd = pos + findLen;
		return BryLni.Eq(src, pos, findEnd, find, 0, findLen);
	}
	public boolean Is(byte find) {
		boolean rv = src[pos] == find;
		if (rv) ++pos;    // only advance if match;
		return rv;
	}
	public boolean Is(byte[] find) {
		int findLen = find.length;
		int findEnd = pos + findLen;
		boolean rv = BryLni.Eq(src, pos, findEnd, find, 0, findLen);
		if (rv) pos = findEnd;    // only advance if match;
		return rv;
	}
	public int Chk(byte find) {
		if (src[pos] != find) {errWkr.Fail("failed check", "chk", ByteUtl.ToStr(find)); return BryFind.NotFound;}
		++pos;
		return pos;
	}
	public int Chk(byte[] find) {
		int findEnd = pos + find.length;
		if (!BryLni.Eq(src, pos, findEnd, find)) {errWkr.Fail("failed check", "chk", StringUtl.NewU8(find)); return BryFind.NotFound;}
		pos = findEnd;
		return pos;
	}
	public byte Chk(Btrie_slim_mgr trie)                {return Chk(trie, pos, srcEnd);}
	public void ChkTrieVal(Btrie_slim_mgr trie, byte val) {
		byte rv = ChkOr(trie, ByteUtl.MaxValue127);
		if (rv == ByteUtl.MaxValue127) errWkr.Fail("failed trie check", "mid", StringUtl.NewU8(BryUtl.MidByLenSafe(src, pos, 16)));
	}
	public Object ChkTrieAsObj(Btrie_rv trv, Btrie_slim_mgr trie) {
		Object rv = trie.MatchAt(trv, src, pos, srcEnd); if (rv == null) errWkr.Fail("failed trie check", "mid", StringUtl.NewU8(BryUtl.MidByLenSafe(src, pos, 16)));
		return rv;
	}
	public byte ChkOr(Btrie_slim_mgr trie, byte or)    {return ChkOr(trie, pos, srcEnd, or);}
	public byte Chk(Btrie_slim_mgr trie, int itmBgn, int itmEnd) {
		byte rv = ChkOr(trie, itmBgn, itmEnd, ByteUtl.MaxValue127);
		if (rv == ByteUtl.MaxValue127) {errWkr.Fail("failed trie check", "mid", StringUtl.NewU8(BryUtl.MidByLenSafe(src, pos, 16))); return ByteUtl.MaxValue127;}
		return rv;
	}
	public byte ChkOr(Btrie_slim_mgr trie, int itmBgn, int itmEnd, byte or) {
		Object rvObj = trie.MatchAt(trv, src, itmBgn, itmEnd);
		if (rvObj == null) return or;
		pos = trv.Pos();
		return ((ByteVal)rvObj).Val();
	}
	public BryRdr SkipWs() {
		while (pos < srcEnd) {
			switch (src[pos]) {
				case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr: case AsciiByte.Space:
					++pos;
					break;
				default:
					return this;
			}
		}
		return this;
	}
	public BryRdr SkipAlphaNumUnder() {
		while (pos < srcEnd) {
			switch (src[pos]) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
				case AsciiByte.Ltr_A: case AsciiByte.Ltr_B: case AsciiByte.Ltr_C: case AsciiByte.Ltr_D: case AsciiByte.Ltr_E:
				case AsciiByte.Ltr_F: case AsciiByte.Ltr_G: case AsciiByte.Ltr_H: case AsciiByte.Ltr_I: case AsciiByte.Ltr_J:
				case AsciiByte.Ltr_K: case AsciiByte.Ltr_L: case AsciiByte.Ltr_M: case AsciiByte.Ltr_N: case AsciiByte.Ltr_O:
				case AsciiByte.Ltr_P: case AsciiByte.Ltr_Q: case AsciiByte.Ltr_R: case AsciiByte.Ltr_S: case AsciiByte.Ltr_T:
				case AsciiByte.Ltr_U: case AsciiByte.Ltr_V: case AsciiByte.Ltr_W: case AsciiByte.Ltr_X: case AsciiByte.Ltr_Y: case AsciiByte.Ltr_Z:
				case AsciiByte.Ltr_a: case AsciiByte.Ltr_b: case AsciiByte.Ltr_c: case AsciiByte.Ltr_d: case AsciiByte.Ltr_e:
				case AsciiByte.Ltr_f: case AsciiByte.Ltr_g: case AsciiByte.Ltr_h: case AsciiByte.Ltr_i: case AsciiByte.Ltr_j:
				case AsciiByte.Ltr_k: case AsciiByte.Ltr_l: case AsciiByte.Ltr_m: case AsciiByte.Ltr_n: case AsciiByte.Ltr_o:
				case AsciiByte.Ltr_p: case AsciiByte.Ltr_q: case AsciiByte.Ltr_r: case AsciiByte.Ltr_s: case AsciiByte.Ltr_t:
				case AsciiByte.Ltr_u: case AsciiByte.Ltr_v: case AsciiByte.Ltr_w: case AsciiByte.Ltr_x: case AsciiByte.Ltr_y: case AsciiByte.Ltr_z:
				case AsciiByte.Underline:
					++pos;
					break;
				default:
					return this;
			}
		}
		return this;
	}
	private static final int FailIfMissing = IntUtl.MinValue;
	public static final int NotFound = BryFind.NotFound;
}

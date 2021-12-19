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
package gplx.types.custom.brys.fmts.fmtrs;
import gplx.types.basics.utls.BryLni;
import gplx.types.custom.brys.wtrs.BryRef;
import gplx.types.custom.brys.fmts.BryFmtrEvalMgrGfs;
import gplx.types.basics.wrappers.IntVal;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.lists.GfoHashBase;
import gplx.types.commons.lists.GfoListBase;
import gplx.types.basics.utls.CharUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.strings.bfrs.GfoStringBldr;
public class BryFmtr {
	private boolean dirty = true;
	private int baseInt = 0;
	private Object[] bldBfrOneAry = new Object[1];
	private GfoHashBase<BryRef, IntVal> keys = null;
	private BryFmtrItm[] itms; private int itms_len;
	public boolean FmtArgsExist() {return fmt_args_exist;} private boolean fmt_args_exist;
	public byte[] Fmt() {return fmt;} private byte[] fmt = BryUtl.Empty;
	public BryFmtr FmtSet(byte[] v) {fmt = v; dirty = true; return this;} public BryFmtr FmtSet(String v) {return FmtSet(BryUtl.NewU8(v));}
	public boolean FmtNull() {return fmt.length == 0;}
	public BryFmtrEvalMgr EvalMgr() {return evalMgr;} public BryFmtr EvalMgrSet(BryFmtrEvalMgr v) {evalMgr = v; return this;} private BryFmtrEvalMgr evalMgr = BryFmtrEvalMgrGfs.Instance;
	public BryFmtr KeysSet(String... ary) {
		if (keys == null)
			keys = new GfoHashBase<>();
		else
			keys.Clear();
		int aryLen = ary.length;
		for (int i = 0; i < aryLen; i++)
			keys.Add(BryRef.New(BryUtl.NewU8(ary[i])), new IntVal(i));
		dirty = true;
		return this;
	}
	public void BldToBfrNone(BryWtr bfr) {
		if (dirty) Compile(); 
		for (int i = 0; i < itms_len; i++) {
			BryFmtrItm itm = itms[i];
			if (itm.IsIdx())
				bfr.AddByte(CharEscape).AddByte(CharArgBgn).AddIntVariable(itm.Idx()).AddByte(CharArgEnd);
			else
				bfr.Add(itm.Data());
		}
	}
	public void BldToBfr(BryWtr bfr, byte[]... args) {
		if (dirty) Compile();
		int args_len = args.length;
		for (int i = 0; i < itms_len; i++) {
			BryFmtrItm itm = itms[i];
			if (itm.IsIdx()) {
				int arg_idx = itm.Idx();
				if (arg_idx < args_len)
					bfr.Add(args[arg_idx]);
				else
					bfr.Add(missingBgn).AddIntVariable(arg_idx + missingAdj).Add(missingEnd);
			}
			else
				bfr.Add(itm.Data());
		}
	}
	public void BldToBfrArgs(BryWtr bfr, BryBfrArg... args) {
		if (dirty) Compile(); 
		for (int i = 0; i < itms_len; i++) {
			BryFmtrItm itm = itms[i];
			if (itm.IsIdx())
				args[itm.Idx()].AddToBfr(bfr);
			else
				bfr.Add(itm.Data());
		}
	}
	public void BldToBfrObj(BryWtr bfr, Object val) {
		bldBfrOneAry[0] = val;
		BldToBfrAry(bfr, bldBfrOneAry);
	}
	public void BldToBfrMany(BryWtr bfr, Object... args) {BldToBfrAry(bfr, args);}
	public void BldToBfrAry(BryWtr bfr, Object[] args) {
		if (dirty) Compile(); 
		int args_len = args.length;
		for (int i = 0; i < itms_len; i++) {
			BryFmtrItm itm = itms[i];
			if (itm.IsIdx()) {
				int arg_idx = itm.Idx();
				if (arg_idx > -1 && arg_idx < args_len)
					bfr.AddObj(args[itm.Idx()]);
				else
					bfr.AddByte(CharEscape).AddByte(CharArgBgn).AddIntVariable(arg_idx).AddByte(CharArgEnd);
			}
			else
				bfr.Add(itm.Data());
		}
	}
	public byte[] BldToBryMany(BryWtr bfr, Object... args) {
		BldToBfrAry(bfr, args);
		return bfr.ToBryAndClear();
	}
	public String BldToStrMany(BryWtr bfr, String fmt, Object... args) {
		this.FmtSet(fmt).BldToBfrMany(bfr, args);
		return bfr.ToStrAndClear();
	}
	public String BldToStrMany(String... args) {
		if (dirty) Compile(); 
		GfoStringBldr rv = new GfoStringBldr();
		int args_len = args.length;
		for (int i = 0; i < itms_len; i++) {
			BryFmtrItm itm = itms[i];
			if (itm.IsIdx()) {
				int arg_idx = itm.Idx();
				if (arg_idx < args_len)
					rv.Add(args[arg_idx]);
				else
					rv.Add(missingBgn).Add(arg_idx + missingAdj).Add(missingEnd);
			}
			else
				rv.Add(StringUtl.NewU8(itm.Data()));
		}
		return rv.ToStr();
	}
	public void BldBfrManyAndSetFmt(Object... args) {
		BryWtr bfr = BryWtr.New();
		this.BldToBfrMany(bfr, args);
		byte[] bry = bfr.ToBryAndClear();
		this.FmtSet(bry).Compile();
	}
	private static byte[] missing_bgn_static = BryUtl.NewU8("~{"), missing_end_static = BryUtl.NewU8("}");
	public byte[] MissingBgn() {return missingBgn;} public BryFmtr MissingBgnSet(byte[] v) {missingBgn = v; return this;} private byte[] missingBgn = missing_bgn_static;
	public byte[] MissingEnd() {return missingEnd;} public BryFmtr MissingEndSet(byte[] v) {missingEnd = v; return this;} private byte[] missingEnd = missing_end_static;
	public int MissingAdj() {return missingAdj;} public BryFmtr MissingAdjSet(int v) {missingAdj = v; return this;} private int missingAdj;
	public boolean FailWhenInvalidEscapes() {return failWhenInvalidEscapes;} public BryFmtr FailWhenInvalidEscapesSet(boolean v) {failWhenInvalidEscapes = v; return this;} private boolean failWhenInvalidEscapes = true;
	public BryFmtr Compile() {
		synchronized (this) {    // THREAD: DATE:2015-04-29
			BryWtr lkp_bfr = BryWtr.NewWithSize(16);
			int fmt_len = fmt.length; int fmt_end = fmt_len - 1; int fmt_pos = 0;
			byte[] trg_bry = new byte[fmt_len]; int trg_pos = 0;
			boolean lkp_is_active = false, lkp_is_numeric = true;
			byte nxt_byte, tmp_byte;
			boolean dirty_disable = true;
			GfoListBase<BryFmtrItm> list = new GfoListBase<>();
			fmt_args_exist = false;
			while (true) {
				if (fmt_pos > fmt_end) break;
				byte cur_byte = fmt[fmt_pos];
				if        (lkp_is_active) {
					if (cur_byte == CharArgEnd) {
						if (lkp_is_numeric)
							list.Add(BryFmtrItm.NewIdx(lkp_bfr.ToInt(0) - baseInt));
						else {
							byte[] key_fmt = lkp_bfr.ToBry();
							Object idx_ref = keys.GetByOrNull(BryRef.New(key_fmt));
							if (idx_ref == null) {
								int lkp_bfr_len = lkp_bfr.Len();
								byte[] lkp_bry = lkp_bfr.Bry();
								trg_bry[trg_pos++] = CharEscape;
								trg_bry[trg_pos++] = CharArgBgn;
								for (int i = 0; i < lkp_bfr_len; i++)
									trg_bry[trg_pos++] = lkp_bry[i];
								trg_bry[trg_pos++] = CharArgEnd;
							}
							else {
								list.Add(BryFmtrItm.NewIdx(((IntVal)idx_ref).Val() - baseInt));
							}
						}
						lkp_is_active = false;
						lkp_bfr.Clear();
						fmt_args_exist = true;
					}
					else {
						lkp_bfr.AddByte(cur_byte);
						switch (cur_byte) {
							case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
							case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
								break;
							default:
								lkp_is_numeric = false;
								break;
						}
					}
					fmt_pos += 1;
				}
				else if    (cur_byte == CharEscape) {
					if (fmt_pos == fmt_end) {
						if (failWhenInvalidEscapes)
							throw ErrUtl.NewMsg("escape char encountered but no more chars left");
						else {
							trg_bry[trg_pos] = cur_byte;
							break;
						}
					}
					nxt_byte = fmt[fmt_pos + 1];
					if (nxt_byte == CharArgBgn) {
						if (trg_pos > 0) {list.Add(BryFmtrItm.NewDataByMid(trg_bry, trg_pos)); trg_pos = 0;}    // something pending; add it to list
						int eval_lhs_bgn = fmt_pos + 2;
						if (eval_lhs_bgn < fmt_len && fmt[eval_lhs_bgn] == CharEvalBgn) {    // eval found
							dirty_disable = false; // eval allows args to retrigger compiles; this is probably not used, but just in case, do not disable dirty; TEST: Tfds.Eq("012~{<>3<>}4", fmtr.Bld_str_many("1"));
							fmt_pos = CompileEvalCmd(fmt, fmt_len, eval_lhs_bgn, list);
							continue;
						}
						else {
							lkp_is_active = true;
							lkp_is_numeric = true;
						}
					}
					else {    // ~{0}; ~~ -> ~; ~n -> newLine; ~t -> tab
						if        (nxt_byte == CharEscape)        tmp_byte = CharEscape;
						else if    (nxt_byte == CharEscapeNl)    tmp_byte = AsciiByte.Nl;
						else if (nxt_byte == CharEscapeTab)    tmp_byte = AsciiByte.Tab;
						else {
							if (failWhenInvalidEscapes) throw ErrUtl.NewArgs("unknown escape code", "code", CharUtl.ByInt(nxt_byte), "fmt_pos", fmt_pos + 1);
							else
								tmp_byte = cur_byte;
						}
						trg_bry[trg_pos++] = tmp_byte;
					}
					fmt_pos += 2;
				}
				else {
					trg_bry[trg_pos++] = cur_byte;
					fmt_pos += 1;
				}
			}
			if (lkp_is_active) throw ErrUtl.NewMsg("idx mode not closed");
			if (trg_pos > 0) {list.Add(BryFmtrItm.NewDataByMid(trg_bry, trg_pos)); trg_pos = 0;}
			itms = (BryFmtrItm[])list.ToAry(BryFmtrItm.class);
			itms_len = itms.length;
			if (dirty_disable)
				dirty = false; // ISSUE#:575; DATE:2019-09-16
			return this;
		}
	}
	private int CompileEvalCmd(byte[] fmt, int fmt_len, int eval_lhs_bgn, GfoListBase<BryFmtrItm> list) {
		int eval_lhs_end = BryFind.FindFwd(fmt, CharEvalEnd, eval_lhs_bgn + AsciiByte.Len1, fmt_len); if (eval_lhs_end == BryFind.NotFound) throw ErrUtl.NewArgs("eval_lhs_end_invalid: could not find eval_lhs_end", "snip", StringUtl.NewU8(fmt, eval_lhs_bgn, fmt_len));
		byte[] eval_dlm = BryLni.Mid(fmt, eval_lhs_bgn        , eval_lhs_end + AsciiByte.Len1);
		int eval_rhs_bgn = BryFind.FindFwd(fmt, eval_dlm        , eval_lhs_end + AsciiByte.Len1, fmt_len); if (eval_rhs_bgn == BryFind.NotFound) throw ErrUtl.NewArgs("eval_rhs_bgn_invalid: could not find eval_rhs_bgn", "snip", StringUtl.NewU8(fmt, eval_lhs_end, fmt_len));
		byte[] eval_cmd = BryLni.Mid(fmt, eval_lhs_end + AsciiByte.Len1, eval_rhs_bgn);
		byte[] eval_rslt = evalMgr.Eval(eval_cmd);
		int eval_rhs_end = eval_rhs_bgn + AsciiByte.Len1 + eval_dlm.length;
		if (eval_rslt == null) eval_rslt = BryLni.Mid(fmt, eval_lhs_bgn - 2, eval_rhs_end);    // not found; return original argument
		list.Add(BryFmtrItm.NewData(eval_rslt));
		return eval_rhs_end;
	}

	public static final byte CharEscape = AsciiByte.Tilde, CharArgBgn = AsciiByte.CurlyBgn, CharArgEnd = AsciiByte.CurlyEnd, CharEscapeNl = AsciiByte.Ltr_n, CharEscapeTab = AsciiByte.Ltr_t, CharEvalBgn = AsciiByte.Lt, CharEvalEnd = AsciiByte.Gt;
	public static final BryFmtr Null = new BryFmtr().FmtSet("");
	public static BryFmtr NewTmp() {return new BryFmtr().FmtSet("").KeysSet();}
	public static BryFmtr New(String fmt, String... keys) {return new BryFmtr().FmtSet(fmt).KeysSet(keys);}    // NOTE: keys may seem redundant, but are needed to align ordinals with proc; EX: fmt may be "~{A} ~{B}" or "~{B} ~{A}"; call will always be Bld(a, b); passing in "A", "B" guarantees A is 0 and B is 1;
	public static BryFmtr New(byte[] fmt, String... keys) {return new BryFmtr().FmtSet(fmt).KeysSet(keys);}    // NOTE: keys may seem redundant, but are needed to align ordinals with proc; EX: fmt may be "~{A} ~{B}" or "~{B} ~{A}"; call will always be Bld(a, b); passing in "A", "B" guarantees A is 0 and B is 1;
	public static BryFmtr New() {return new BryFmtr();}
	public static BryFmtr NewKeys(String... keys) {return new BryFmtr().KeysSet(keys);}
	public static BryFmtr NewBry(byte[] fmt, String... keys) {return new BryFmtr().FmtSet(fmt).KeysSet(keys);}
	public static String EscapeTilde(String v) {return StringUtl.Replace(v, "~", "~~");}
}

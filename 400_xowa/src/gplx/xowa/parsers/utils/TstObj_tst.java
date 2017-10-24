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
package gplx.xowa.parsers.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.core.strings.*; import gplx.core.type_xtns.*; import gplx.core.stores.*; import gplx.core.envs.*;
interface TstRuleMgr {
	boolean SkipChkVal(String expdTypeKey, TstAtr expd);
	boolean SkipChkObj(String expdTypeKey, String atrKey, TstObj expd);
}
class Xop_rule_mgr implements TstRuleMgr {
	public boolean SkipChkVal(String expdTypeKey, TstAtr expd) {
		String key = expdTypeKey + "." + expd.Key();
		Xop_rule_dat ruleDat = (Xop_rule_dat)hash.Get_by(key); if (ruleDat == null) return false;
		if (expd.ValType().Eq(expd.Val(), ruleDat.SkipVal())) return true;
		return false;
	}
	public boolean SkipChkObj(String expdTypeKey, String atrKey, TstObj expd) {
		String key = expdTypeKey + "." + atrKey;
		Xop_rule_dat ruleDat = (Xop_rule_dat)hash.Get_by(key); if (ruleDat == null) return false;
		TstAtr expdAtr = (TstAtr)expd.Atrs().Get_by(ruleDat.SubKey());
		if (expdAtr == null) return false;
		if (expdAtr.ValType().Eq(expdAtr.Val(), ruleDat.SkipVal())) return true;
		return false;
	}
	public Xop_rule_mgr TypeKey_(String v) {typeKey = v; return this;} private String typeKey;
	public Xop_rule_mgr SkipIf_double(String atrKey, double v) {return SkipIfObj(atrKey, null, v);}
	public Xop_rule_mgr SkipIf(String atrKey, String v) {return SkipIfObj(atrKey, null, v);}
	public Xop_rule_mgr SkipIf(String atrKey, byte v) {return SkipIfObj(atrKey, null, v);}
	public Xop_rule_mgr SkipIf(String atrKey, int v) {return SkipIfObj(atrKey, null, v);}
	public Xop_rule_mgr SkipIf(String atrKey, boolean v) {return SkipIfObj(atrKey, null, v);}
	public Xop_rule_mgr SkipIf(String atrKey, String subKey, Object o) {return SkipIfObj(atrKey, subKey, o);}
	public Xop_rule_mgr SkipIfObj_many(String[] atrKeys, String subKey, Object o) {for (String atrKey : atrKeys) SkipIfObj(atrKey, subKey, o); return this;}
	Xop_rule_mgr SkipIfObj(String atrKey, String subKey, Object skipVal) {
		String key = typeKey + "." + atrKey;
		Xop_rule_dat ruleDat = new Xop_rule_dat(key, subKey, skipVal);
		hash.Add(key, ruleDat);
		return this;
	}
	public String Reg() {return typeKey;}
	Ordered_hash hash = Ordered_hash_.New();
	public static final    Xop_rule_mgr Instance = new Xop_rule_mgr();
}
class Xop_rule_dat {
	public String AtrKey() {return atrKey;} private String atrKey;
	public String SubKey() {return subKey;} private String subKey;
	public Object SkipVal() {return skipVal;} Object skipVal;
	public Xop_rule_dat(String atrKey, String subKey, Object skipVal) {this.atrKey = atrKey; this.subKey = subKey; this.skipVal = skipVal;}
}
public class TstObj_tst {
	@Test  public void Basic() {
		tst_(mock_().Val1_(1).Val2_("a"), mock_().Val1_(1).Val2_("a"));
//			tst_(mock_().Val1_(3).Val2_("a"), mock_().Val1_(1).Val2_("b"));
	}
	MockObj mock_() {return new MockObj();}
	private void tst_(MockObj expd, MockObj actl) {
		TstObj expdChk = TstObj.new_(), actlChk = TstObj.new_();
		expd.SrlObj_Srl(expdChk);
		actl.SrlObj_Srl(actlChk);
		Eval("", expdChk, actlChk, new Xop_rule_mgr());
	}
	private static void Max(int[] ary, int idx, String val) {
		int len = String_.Len(val);
		if (len > ary[idx]) ary[idx] = len;
	}
	private static int Add(int[] ary) {int rv = 0; for (int i = 0; i < ary.length; i++) rv += ary[i]; return rv;}
	@gplx.Internal protected static void Eval(String raw, TstObj expdChk, TstObj actlChk, TstRuleMgr ruleMgr) {
		List_adp rslts = List_adp_.New();
		Eval(rslts, ruleMgr, Ordered_hash_.New(), "", expdChk, actlChk);

		String_bldr sb = String_bldr_.new_();
		sb.Add(raw).Add(Op_sys.Lnx.Nl_str());
		boolean pass = true;
		int[] cols = new int[3];
		for (int i = 0; i < rslts.Count(); i++) {
			TstRslt rslt = (TstRslt)rslts.Get_at(i);
			Max(cols, 0, rslt.EvalStr());
			Max(cols, 1, rslt.Id());
			Max(cols, 2, rslt.Key());
		}
		String hdr =  String_.Repeat(" ", Add(cols) + 3);
		for (int i = 0; i < rslts.Count(); i++) {
			TstRslt rslt = (TstRslt)rslts.Get_at(i);
//				if (rslt.EvalPass()) continue;
			sb	.Add(String_.PadEnd(rslt.EvalStr(), cols[0] + 1, " "))
				.Add(String_.PadEnd(rslt.Id(), cols[1] + 1, " "))
				.Add(String_.PadEnd(rslt.Key(), cols[2] + 1, " "))
				.Add(rslt.ExpdStr()).Add(Op_sys.Lnx.Nl_str());
			if (!rslt.EvalPass()) {
				sb.Add(hdr).Add(rslt.ActlStr()).Add(Op_sys.Lnx.Nl_str());
				pass = false;
			}
		}
		if (pass) return;
		throw Err_.new_wo_type(Op_sys.Lnx.Nl_str() + sb.To_str());
	}
	private static void Eval(List_adp rslts, TstRuleMgr ruleMgr, Ordered_hash props, String idx, TstObj expd, TstObj actl) {
		int expdLen = expd.Atrs().Count();
		props.Clear();
		for (int i = 0; i < expdLen; i++) {
			TstAtr expdAtr = (TstAtr)expd.Atrs().Get_at(i);
			String key = expdAtr.Key();
			TstAtr actlAtr = (TstAtr)actl.Atrs().Get_by(key);
			if (expdAtr.ValType() == ObjectClassXtn.Instance) {
				SrlObj expdSrl = (SrlObj)expdAtr.Val();
				TstObj expdTst = TstObj.new_();
				expdSrl.SrlObj_Srl(expdTst);
				TstObj actlTst = TstObj.new_();
				if (actlAtr != null) ((SrlObj)actlAtr.Val()).SrlObj_Srl(actlTst);
				if (ruleMgr.SkipChkObj(expdAtr.TypeKey(), key, expdTst)) continue;
				Eval(rslts, ruleMgr, Ordered_hash_.New(), idx + "." + key, expdTst, actlTst);
			}
			else {
				if (actlAtr == null) actlAtr = new TstAtr();
				if (ruleMgr.SkipChkVal(expdAtr.TypeKey(), expdAtr)) continue;
				Eval(rslts, idx, key, expdAtr, actlAtr, true);
			}
			props.Add(key, key);
		}
		int expdSubsLen = expd.Subs().Count(), actlSubsLen = actl.Subs().Count();
		int maxLen = expdSubsLen > actlSubsLen ? expdSubsLen : actlSubsLen;
		for (int i = 0; i < maxLen; i++) {
			TstObj expdSub = i < expdSubsLen ? (TstObj)expd.Subs().Get_at(i) : TstObj.Null;
			TstObj actlSub = i < actlSubsLen ? (TstObj)actl.Subs().Get_at(i) : TstObj.Null;
//				if (ruleMgr != null) ruleMgr.Eval(expd.TypeKey(), expdSub.PropName(), expdAtr, actlAtr, skip);
			String iAsStr = Int_.To_str(i);
			String subId = String_.Eq(idx, "") ? iAsStr : idx + "." + iAsStr;
			if (expdSub == TstObj.Null && actlSub != TstObj.Null) {
				TstAtr mis = new TstAtr().Key_("idx").Val_(i).ValType_(IntClassXtn.Instance);
				rslts.Add(new TstRslt().Expd_(mis).Actl_(mis).EvalPass_(false).EvalStr_("!=")
					.Id_(subId).Key_("sub_ref")
					.ExpdStr_("null").ActlStr_("not null"));
				continue;
			}
			Eval(rslts, ruleMgr, props, subId, expdSub, actlSub);
		}
	}
	private static void Eval(List_adp rslts, String id, String key, TstAtr expd, TstAtr actl, boolean srcIsExpd) {
		int evalType = 0;
		boolean evalPass = false; String evalStr = "";
		switch (evalType) {
			case 0:
				if (expd.ValType().Eq(expd.Val(), actl.Val())) {
					evalPass = true;
					evalStr = "==";
				}
				else {
					evalPass = false;
					evalStr = "!=";
				}
				break;
		}
		TstRslt rslt = new TstRslt().Expd_(expd).Actl_(actl)
			.Id_(id).Key_(key)
			.EvalType_(evalType).EvalPass_(evalPass).EvalStr_(evalStr)
			.ExpdStr_(Object_.Xto_str_strict_or_null_mark(expd.Val())).ActlStr_(Object_.Xto_str_strict_or_null_mark(actl.Val()))
			;
		rslts.Add(rslt);
	}
}
class MockObj {
	public int Val1() {return val1;} public MockObj Val1_(int v) {val1 = v; return this;} private int val1;
	public String Val2() {return val2;} public MockObj Val2_(String v) {val2 = v; return this;} private String val2;
	public void SrlObj_Srl(SrlMgr mgr) {
		mgr.TypeKey_("MockObj");
		val1 = mgr.SrlIntOr("val1", val1);
		val2 = mgr.SrlStrOr("val2", val2);
	}
}
class TstObj implements SrlMgr {
	public boolean	Type_rdr() {return false;}
	public Ordered_hash Atrs() {return atrs;} private Ordered_hash atrs = Ordered_hash_.New();
	public Object StoreRoot(SrlObj root, String key) {return null;}
	public boolean	SrlBoolOr(String key, boolean v)					{Atrs_add(key, v, BoolClassXtn.Instance); return v;}
	public byte	SrlByteOr(String key, byte v)					{Atrs_add(key, v, ByteClassXtn.Instance); return v;}
	public int SrlIntOr(String key, int v)						{Atrs_add(key, v, IntClassXtn.Instance); return v;}
	public long SrlLongOr(String key, long v)					{Atrs_add(key, v, LongClassXtn.Instance); return v;}
	public String SrlStrOr(String key, String v)				{Atrs_add(key, v, StringClassXtn.Instance); return v;}
	public Decimal_adp SrlDecimalOr(String key, Decimal_adp v)	{Atrs_add(key, v, DecimalAdpClassXtn.Instance); return v;}
	public DateAdp SrlDateOr(String key, DateAdp v)				{Atrs_add(key, v, DateAdpClassXtn.Instance); return v;}
	public double SrlDoubleOr(String key, double v)				{Atrs_add(key, v, DoubleClassXtn.Instance); return v;}
	public Object SrlObjOr(String key, Object v)				{
		Atrs_add(key, v, ObjectClassXtn.Instance);
		return v;
	}
	public void	SrlList(String key, List_adp list, SrlObj proto, String itmKey) {}
	public String TypeKey() {return typeKey;} public void TypeKey_(String v) {typeKey = v;} private String typeKey;
	private void Atrs_add(String key, Object val, ClassXtn valType) {
		atrs.Add(key, new TstAtr().TypeKey_(typeKey).Key_(key).Val_(val).ValType_(valType));
	}
	public List_adp Subs() {return subs;} List_adp subs = List_adp_.Noop;
	public SrlMgr SrlMgr_new(Object o) {return Subs_new();}
	public TstObj Subs_new() {
		if (subs == List_adp_.Noop) subs = List_adp_.New();
		TstObj rv = TstObj.new_();
		subs.Add(rv);
		return rv;
	}
	public static TstObj new_() {return new TstObj();} TstObj() {}
	public static final    TstObj Null = new TstObj();
}
class TstAtr {
	public String TypeKey() {return typeKey;} public TstAtr TypeKey_(String v) {typeKey = v; return this;} private String typeKey;
	public String Key() {return key;} public TstAtr Key_(String v) {key = v; return this;} private String key;
	public Object Val() {return val;} public TstAtr Val_(Object v) {val = v; return this;} Object val;
	public ClassXtn ValType() {return valType;} public TstAtr ValType_(ClassXtn v) {valType = v; return this;} ClassXtn valType;
}
class TstRslt {
	public TstAtr Expd() {return expd;} public TstRslt Expd_(TstAtr v) {expd = v; return this;} TstAtr expd;
	public TstAtr Actl() {return actl;} public TstRslt Actl_(TstAtr v) {actl = v; return this;} TstAtr actl;
	public boolean Ignore() {return ignore;} public TstRslt Ignore_y_(boolean v) {ignore = v; return this;} private boolean ignore;
	public int EvalType() {return evalType;} public TstRslt EvalType_(int v) {evalType = v; return this;} private int evalType;
	public boolean EvalPass() {return evalPass;} public TstRslt EvalPass_(boolean v) {evalPass = v; return this;} private boolean evalPass;
	public String EvalStr() {return evalStr;} public TstRslt EvalStr_(String v) {evalStr = v; return this;} private String evalStr;
	public String Id() {return id;} public TstRslt Id_(String v) {id = v; return this;} private String id;
	public String Key() {return key;} public TstRslt Key_(String v) {key = v; return this;} private String key;
	public String ActlStr() {return actlStr;} public TstRslt ActlStr_(String v) {actlStr = v; return this;} private String actlStr;
	public String ExpdStr() {return expdStr;} public TstRslt ExpdStr_(String v) {expdStr = v; return this;} private String expdStr;
	public static final    Object Ignore_null = new Object();
}
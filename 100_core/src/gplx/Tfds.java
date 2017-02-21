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
package gplx;
import gplx.core.strings.*; import gplx.core.consoles.*; import gplx.core.tests.*;
public class Tfds {		// URL:doc/gplx.tfds/Tfds.txt
	public static boolean SkipDb = false;
	public static void Eq_bool	(boolean expd	, boolean   actl)										{Eq_exec_y(expd, actl, "", Object_.Ary_empty);}
	public static void Eq_bool	(boolean expd	, boolean   actl, String fmt, Object... args)	{Eq_exec_y(expd, actl, fmt, args);}
	public static void Eq_byte	(byte expd	, byte   actl, String fmt, Object... args)	{Eq_exec_y(expd, actl, fmt, args);}
	public static void Eq_int	(int  expd	, int    actl)										{Eq_exec_y(expd, actl, "", Object_.Ary_empty);}
	public static void Eq_int	(int  expd	, int    actl, String fmt, Object... args)	{Eq_exec_y(expd, actl, fmt, args);}
	public static void Eq_double(double expd, double actl)										{Eq_exec_y(expd, actl, "", Object_.Ary_empty);}
	public static void Eq_str	(byte[] expd, byte[] actl, String fmt, Object... args)	{Eq_exec_y(String_.new_u8(expd), String_.new_u8(actl), fmt, args);}
	public static void Eq_str	(byte[] expd, String actl, String fmt, Object... args)	{Eq_exec_y(String_.new_u8(expd), actl, fmt, args);}
	public static void Eq_str	(String expd, byte[] actl, String fmt, Object... args)	{Eq_exec_y(expd, String_.new_u8(actl), fmt, args);}
	public static void Eq_str	(String expd, String actl)										{Eq_exec_y(expd, actl, "", Object_.Ary_empty);}
	public static void Eq_str	(String expd, String actl, String fmt, Object... args)	{Eq_exec_y(expd, actl, fmt, args);}

	public static void Eq(Object expd, Object actl)											{Eq_wkr(expd, actl, true, EmptyStr);}
	public static void Eq_byte(byte expd, byte actl)										{Eq_wkr(expd, actl, true, EmptyStr);}
	public static void Eq_long(long expd, long actl)										{Eq_wkr(expd, actl, true, EmptyStr);}
	public static void Eq_float(float expd, float actl)										{Eq_wkr(expd, actl, true, EmptyStr);}
	public static void Eq_decimal(Decimal_adp expd, Decimal_adp actl)						{Eq_wkr(expd.To_double(), actl.To_double(), true, EmptyStr);}
	public static void Eq_date(DateAdp expd, DateAdp actl)									{Eq_wkr(expd.XtoStr_gplx(), actl.XtoStr_gplx(), true, EmptyStr);}
	public static void Eq_date(DateAdp expd, DateAdp actl, String fmt, Object... args){Eq_wkr(expd.XtoStr_gplx(), actl.XtoStr_gplx(), true, String_.Format(fmt, args));}
	public static void Eq_url(Io_url expd, Io_url actl)										{Eq_wkr(expd.Raw(), actl.Raw(), true, EmptyStr);}
	public static void Eq_str(String expd, byte[] actl)										{Eq_wkr(expd, String_.new_u8(actl), true, EmptyStr);}
	public static void Eq_bry(String expd, byte[] actl)										{Eq_wkr(expd, String_.new_u8(actl), true, EmptyStr);}
	public static void Eq_bry(byte[] expd, byte[] actl)										{Eq_wkr(String_.new_u8(expd), String_.new_u8(actl), true, EmptyStr);}
	public static void Eq_str_intf(To_str_able expd, To_str_able actl, String msg)			{Eq_wkr(expd.To_str(), actl.To_str(), true, msg);}
	public static void Eq_str_intf(To_str_able expd, To_str_able actl)						{Eq_wkr(expd.To_str(), actl.To_str(), true, String_.Empty);}
	public static void Eq_str_lines(String lhs, String rhs)									{Eq_str_lines(lhs, rhs, EmptyStr);}
	public static void Eq_str_lines(String lhs, String rhs, String note)					{
		if (lhs == null) lhs = "";
		if (rhs == null) rhs = "";
		Eq_ary_wkr(String_.Split(lhs, Char_.NewLine), String_.Split(rhs, Char_.NewLine), false, note);
	}
	public static void Eq(Object expd, Object actl, String fmt, Object... args)		{Eq_wkr(expd, actl, true, String_.Format(fmt, args));}
	public static void Eq_rev(Object actl, Object expd)										{Eq_wkr(expd, actl, true, EmptyStr);}
	public static void Eq_rev(Object actl, Object expd, String fmt, Object... args)	{Eq_wkr(expd, actl, true, String_.Format(fmt, args));}
	public static void Eq_true(Object actl)													{Eq_wkr(true, actl, true, EmptyStr);}
	public static void Eq_true(Object actl, String fmt, Object... args)				{Eq_wkr(true, actl, true, String_.Format(fmt, args));}
	public static void Eq_false(Object actl)												{Eq_wkr(false, actl, true, EmptyStr);}
	public static void Eq_false(Object actl, String fmt, Object... args)				{Eq_wkr(false, actl, true, String_.Format(fmt, args));}
	public static void Eq_null(Object actl)													{Eq_wkr(null, actl, true, EmptyStr);}
	public static void Eq_null(Object actl, String fmt, Object... args)				{Eq_wkr(null, actl, true, String_.Format(fmt, args));}
	public static void Eq_nullNot(Object actl)												{Eq_wkr(null, actl, false, EmptyStr);}
	public static void Eq_nullNot(Object actl, String fmt, Object... args)			{Eq_wkr(null, actl, false, String_.Format(fmt, args));}
	public static void Fail_expdError()														{Eq_wkr(true, false, true, "fail expd error");}
	public static void Fail(String fmt, Object... args)								{Eq_wkr(true, false, true, String_.Format(fmt, args));}
	public static void Eq_ary(Object lhs, Object rhs)							{Eq_ary_wkr(lhs, rhs, true, EmptyStr);}						
	public static void Eq_ary(Object lhs, Object rhs, String fmt, Object... args){Eq_ary_wkr(lhs, rhs, true, String_.Format(fmt, args));}	
	public static void Eq_ary_str(Object lhs, Object rhs, String note)			{Eq_ary_wkr(lhs, rhs, false, note);}					
	public static void Eq_ary_str(Object lhs, Object rhs)						{Eq_ary_wkr(lhs, rhs, false, EmptyStr);}					
	public static void Eq_list(List_adp lhs, List_adp rhs)									{Eq_list_wkr(lhs, rhs, TfdsEqListItmStr_cls_default.Instance, EmptyStr);}
	public static void Eq_list(List_adp lhs, List_adp rhs, TfdsEqListItmStr xtoStr)			{Eq_list_wkr(lhs, rhs, xtoStr, EmptyStr);}
	private static void Eq_exec_y(Object lhs, Object rhs, String fmt, Object[] args) {
		if (Object_.Eq(lhs, rhs)) return;
		String msg = msgBldr.Eq_xtoStr(lhs, rhs, String_.Format(fmt, args));
		throw Err_.new_wo_type(msg);
	}
	static void Eq_wkr(Object lhs, Object rhs, boolean expd, String customMsg) {
		boolean actl = Object_.Eq(lhs, rhs);
		if (expd == actl) return;
		String msg = msgBldr.Eq_xtoStr(lhs, rhs, customMsg);
		throw Err_.new_wo_type(msg);
	}
	static void Eq_ary_wkr(Object lhsAry, Object rhsAry, boolean compareUsingEquals, String customMsg) {
		List_adp list = List_adp_.New(); boolean pass = true;
		int lhsLen = Array_.Len(lhsAry), rhsLen = Array_.Len(rhsAry);
		for (int i = 0; i < lhsLen; i++) {
			Object lhs = Array_.Get_at(lhsAry, i);
			Object rhs = i >= rhsLen ? "<<N/A>>" : Array_.Get_at(rhsAry, i);
			String lhsString = msgBldr.Obj_xtoStr(lhs); String rhsString = msgBldr.Obj_xtoStr(rhs);	// even if compareUsingEquals, method does ToStr on each itm for failMsg
			boolean isEq = compareUsingEquals
				? Object_.Eq(lhs, rhs)
				: Object_.Eq(lhsString, rhsString);
			Eq_ary_wkr_addItm(list, i, isEq, lhsString, rhsString);
			if (!isEq) pass = false;
		}
		for (int i = lhsLen; i < rhsLen; i++) {
			String lhsString = "<<N/A>>";
			String rhsString = msgBldr.Obj_xtoStr(Array_.Get_at(rhsAry, i));
			Eq_ary_wkr_addItm(list, i, false, lhsString, rhsString);
			pass = false;
		}
		if (pass) return;
		String msg = msgBldr.Eq_ary_xtoStr(list, lhsLen, rhsLen, customMsg);
		throw Err_.new_wo_type(msg);
	}
	static void Eq_list_wkr(List_adp lhsList, List_adp rhsList, TfdsEqListItmStr xtoStr, String customMsg) {
		List_adp list = List_adp_.New(); boolean pass = true;
		int lhsLen = lhsList.Count(), rhsLen = rhsList.Count();
		for (int i = 0; i < lhsLen; i++) {
			Object lhs = lhsList.Get_at(i);
			Object rhs = i >= rhsLen ? null : rhsList.Get_at(i);
			String lhsStr = xtoStr.To_str(lhs, lhs);
			String rhsStr = rhs == null ? "<<N/A>>" : xtoStr.To_str(rhs, lhs);
			boolean isEq = Object_.Eq(lhsStr, rhsStr); if (!isEq) pass = false;
			Eq_ary_wkr_addItm(list, i, isEq, lhsStr, rhsStr);
		}
		for (int i = lhsLen; i < rhsLen; i++) {
			String lhsStr = "<<N/A>>";
			Object rhs = rhsList.Get_at(i);
			String rhsStr = xtoStr.To_str(rhs, null);
			Eq_ary_wkr_addItm(list, i, false, lhsStr, rhsStr);
			pass = false;
		}
		if (pass) return;
		String msg = msgBldr.Eq_ary_xtoStr(list, lhsLen, rhsLen, customMsg);
		throw Err_.new_wo_type(msg);
	}
	static void Eq_ary_wkr_addItm(List_adp list, int i, boolean isEq, String lhsString, String rhsString) {
		TfdsEqAryItm itm = new TfdsEqAryItm().Idx_(i).Eq_(isEq).Lhs_(lhsString).Rhs_(rhsString);
		list.Add(itm);
	}
	public static void Err_classMatch(Exception exc, Class<?> type) {
		boolean match = Type_adp_.Eq_typeSafe(exc, type);
		if (!match) throw Err_.new_("Tfds", "error types do not match", "expdType", Type_adp_.FullNameOf_type(type), "actlType", Type_adp_.NameOf_obj(exc), "actlMsg", Err_.Message_lang(exc));
	}
	public static void Eq_err(Err expd, Exception actlExc) {
		Tfds.Eq(Err_.Message_gplx_full(expd), Err_.Message_gplx_full(actlExc));
	}
	public static void Err_has(Exception e, String hdr) {
		Tfds.Eq_true(String_.Has(Err_.Message_gplx_full(e), hdr), "could not find '{0}' in '{1}'", hdr, Err_.Message_gplx_full(e));
	}
	static final    String EmptyStr = TfdsMsgBldr.EmptyStr;
	static TfdsMsgBldr msgBldr = TfdsMsgBldr.new_();
	public static final    Io_url RscDir		= Io_url_.Usr().GenSubDir_nest("000", "200_dev", "190_tst");
	public static void WriteText(String text) {Console_adp__sys.Instance.Write_str(text);}
	public static void Write(byte[] s, int b, int e) {Write(Bry_.Mid(s, b, e));}
	public static void Write() {Write("tmp");}
	public static void Dbg(Object... ary) {Write(ary);}
	public static void Write(Object... ary) {
		String_bldr sb = String_bldr_.new_();
		int aryLen = Array_.Len(ary);
		for (int i = 0; i < aryLen; i++)
			sb.Add_many("'", Object_.Xto_str_strict_or_null_mark(ary[i]), "'", " ");
		WriteText(sb.To_str() + String_.Lf);
	}
}
class TfdsEqListItmStr_cls_default implements TfdsEqListItmStr {
	public String To_str(Object cur, Object actl) {
		return Object_.Xto_str_strict_or_null_mark(cur);
	}
	public static final    TfdsEqListItmStr_cls_default Instance = new TfdsEqListItmStr_cls_default(); TfdsEqListItmStr_cls_default() {}
}
class TfdsEqAryItm {
	public int Idx() {return idx;} public TfdsEqAryItm Idx_(int v) {idx = v; return this;} int idx;
	public String Lhs() {return lhs;} public TfdsEqAryItm Lhs_(String v) {lhs = v; return this;} private String lhs;
	public String Rhs() {return rhs;} public TfdsEqAryItm Rhs_(String v) {rhs = v; return this;} private String rhs;
	public boolean Eq() {return eq;} public TfdsEqAryItm Eq_(boolean v) {eq = v; return this;} private boolean eq;
}
class TfdsMsgBldr {
	public String Eq_xtoStr(Object expd, Object actl, String customMsg) {
		String expdString = Obj_xtoStr(expd); String actlString = Obj_xtoStr(actl);
		String detail = String_.Concat
			( CustomMsg_xtoStr(customMsg)
			, "\t\t", "expd: ", expdString, String_.CrLf
			, "\t\t", "actl: ", actlString, String_.CrLf
			);
		return WrapMsg(detail);
	}
	public String Eq_ary_xtoStr(List_adp list, int lhsAryLen, int rhsAryLen, String customMsg) {
		String_bldr sb = String_bldr_.new_();
		sb.Add(CustomMsg_xtoStr(customMsg));
		if (lhsAryLen != rhsAryLen) 
			sb.Add_fmt_line("{0}element counts differ: {1} {2}", "\t\t", lhsAryLen, rhsAryLen);
		int lhsLenMax = 0, rhsLenMax = 0;
		for (int i = 0; i < list.Count(); i++) {
			TfdsEqAryItm itm = (TfdsEqAryItm)list.Get_at(i);
			int lhsLen = String_.Len(itm.Lhs()), rhsLen = String_.Len(itm.Rhs());
			if (lhsLen > lhsLenMax) lhsLenMax = lhsLen;
			if (rhsLen > rhsLenMax) rhsLenMax = rhsLen;
		}
		for (int i = 0; i < list.Count(); i++) {
			TfdsEqAryItm itm = (TfdsEqAryItm)list.Get_at(i);
			String eq_str = itm.Eq() ? "==" : "";
			if (!itm.Eq()) {
//					if (lhsLenMax < 8 )
//						eq_str = "!=";
//					else
				eq_str = "\n!=   ";
			}
			sb.Add_fmt_line("{0}: {1} {2} {3}"
				, Int_.To_str_pad_bgn_zero(itm.Idx(), 4)
				, itm.Lhs() // String_.PadBgn(itm.Lhs(), lhsLenMax, " ")
				, eq_str
				, itm.Rhs() // String_.PadBgn(itm.Rhs(), rhsLenMax, " ")
				);
		}
//			String compSym = isEq ? "  " : "!=";
//			String result = String_.Format("{0}: {1}{2}  {3}  {4}", Int_.To_str_pad_bgn_zero(i, 4), lhsString, String_.CrLf + "\t\t", compSym, rhsString);
//			foreach (Object obj in list) {
//				String itmComparison = (String)obj;
//				sb.Add_fmt_line("{0}{1}", "\t\t", itmComparison);
//			}
		return WrapMsg(sb.To_str());
	}
	String CustomMsg_xtoStr(String customMsg) {
		return (customMsg == EmptyStr) 
			? ""
			: String_.Concat(customMsg, String_.CrLf);
	}
	public String Obj_xtoStr(Object obj) {
		String s = String_.as_(obj);
		if (s != null) return String_.Concat("'", s, "'"); // if Object is String, put quotes around it for legibility
		To_str_able xtoStrAble = To_str_able_.as_(obj);
		if (xtoStrAble != null) return xtoStrAble.To_str();
		return Object_.Xto_str_strict_or_null_mark(obj);
	}
	String WrapMsg(String text) {
		return String_.Concat(String_.CrLf
			, "************************************************************************************************", String_.CrLf
			, text
			, "________________________________________________________________________________________________"
			);
	}
	public static TfdsMsgBldr new_() {return new TfdsMsgBldr();} TfdsMsgBldr() {}
	public static final    String EmptyStr = "";
}

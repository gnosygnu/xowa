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
package gplx;
public class Tfds {		// URL:doc/gplx.tfds/Tfds.txt
	public static boolean SkipDb = false;
	public static void Eq(Object expd, Object actl)											{Eq_wkr(expd, actl, true, EmptyStr);}
	public static void Eq_able(EqAble expd, EqAble actl)									{Eq_able_wkr(expd, actl, true, EmptyStr);}
	public static void Eq_able(EqAble expd, EqAble actl, String fmt, Object... args)	{Eq_able_wkr(expd, actl, true, String_.Format(fmt, args));}
	public static void Eq_byte(byte expd, byte actl)										{Eq_wkr(expd, actl, true, EmptyStr);}
	public static void Eq_long(long expd, long actl)										{Eq_wkr(expd, actl, true, EmptyStr);}
	public static void Eq_float(float expd, float actl)										{Eq_wkr(expd, actl, true, EmptyStr);}
	public static void Eq_decimal(DecimalAdp expd, DecimalAdp actl)							{Eq_wkr(expd.Xto_double(), actl.Xto_double(), true, EmptyStr);}
	public static void Eq_date(DateAdp expd, DateAdp actl)									{Eq_wkr(expd.XtoStr_gplx(), actl.XtoStr_gplx(), true, EmptyStr);}
	public static void Eq_date(DateAdp expd, DateAdp actl, String fmt, Object... args){Eq_wkr(expd.XtoStr_gplx(), actl.XtoStr_gplx(), true, String_.Format(fmt, args));}
	public static void Eq_url(Io_url expd, Io_url actl)										{Eq_wkr(expd.Raw(), actl.Raw(), true, EmptyStr);}
	public static void Eq_bry(String expd, byte[] actl)										{Eq_wkr(expd, String_.new_utf8_(actl), true, EmptyStr);}
	public static void Eq_bry(byte[] expd, byte[] actl)										{Eq_wkr(String_.new_utf8_(expd), String_.new_utf8_(actl), true, EmptyStr);}
	public static void Eq_str(XtoStrAble expd, XtoStrAble actl, String msg)					{Eq_wkr(expd.XtoStr(), actl.XtoStr(), true, msg);}
	public static void Eq_str(XtoStrAble expd, XtoStrAble actl)								{Eq_wkr(expd.XtoStr(), actl.XtoStr(), true, String_.Empty);}
	public static void Eq_str_lines(String lhs, String rhs)									{Eq_str_lines(lhs, rhs, EmptyStr);}
	public static void Eq_str_lines(String lhs, String rhs, String note)					{
		if		(lhs == null && rhs == null)	return;	// true
		else if (lhs == null)					throw Err_.new_("lhs is null" + note);
		else if (rhs == null)					throw Err_.new_("rhs is null" + note);
		else									Eq_ary_wkr(String_.Split(lhs, Char_.NewLine), String_.Split(rhs, Char_.NewLine), false, note);
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
	public static void Eq_list(ListAdp lhs, ListAdp rhs)									{Eq_list_wkr(lhs, rhs, TfdsEqListItmStr_cls_default._, EmptyStr);}
	public static void Eq_list(ListAdp lhs, ListAdp rhs, TfdsEqListItmStr xtoStr)			{Eq_list_wkr(lhs, rhs, xtoStr, EmptyStr);}
	static void Eq_able_wkr(EqAble lhs, EqAble rhs, boolean expd, String customMsg) {
		boolean actl = false;
		if		(lhs == null && rhs != null) actl = false;
		else if (lhs != null && rhs == null) actl = false;
		else	actl = lhs.Eq(rhs);
		if (expd == actl) return;
		String msg = msgBldr.Eq_xtoStr(lhs, rhs, customMsg);
		throw Err_.new_(msg);
	}
	static void Eq_wkr(Object lhs, Object rhs, boolean expd, String customMsg) {
		boolean actl = Object_.Eq(lhs, rhs);
		if (expd == actl) return;
		String msg = msgBldr.Eq_xtoStr(lhs, rhs, customMsg);
		throw Err_.new_(msg);
	}
	static void Eq_ary_wkr(Object lhsAry, Object rhsAry, boolean compareUsingEquals, String customMsg) {
		ListAdp list = ListAdp_.new_(); boolean pass = true;
		int lhsLen = Array_.Len(lhsAry), rhsLen = Array_.Len(rhsAry);
		for (int i = 0; i < lhsLen; i++) {
			Object lhs = Array_.FetchAt(lhsAry, i);
			Object rhs = i >= rhsLen ? "<<N/A>>" : Array_.FetchAt(rhsAry, i);
			String lhsString = msgBldr.Obj_xtoStr(lhs); String rhsString = msgBldr.Obj_xtoStr(rhs);	// even if compareUsingEquals, method does ToStr on each itm for failMsg
			boolean isEq = compareUsingEquals
				? Object_.Eq(lhs, rhs)
				: Object_.Eq(lhsString, rhsString);
			Eq_ary_wkr_addItm(list, i, isEq, lhsString, rhsString);
			if (!isEq) pass = false;
		}
		for (int i = lhsLen; i < rhsLen; i++) {
			String lhsString = "<<N/A>>";
			String rhsString = msgBldr.Obj_xtoStr(Array_.FetchAt(rhsAry, i));
			Eq_ary_wkr_addItm(list, i, false, lhsString, rhsString);
			pass = false;
		}
		if (pass) return;
		String msg = msgBldr.Eq_ary_xtoStr(list, lhsLen, rhsLen, customMsg);
		throw Err_.new_(msg);
	}
	static void Eq_list_wkr(ListAdp lhsList, ListAdp rhsList, TfdsEqListItmStr xtoStr, String customMsg) {
		ListAdp list = ListAdp_.new_(); boolean pass = true;
		int lhsLen = lhsList.Count(), rhsLen = rhsList.Count();
		for (int i = 0; i < lhsLen; i++) {
			Object lhs = lhsList.FetchAt(i);
			Object rhs = i >= rhsLen ? null : rhsList.FetchAt(i);
			String lhsStr = xtoStr.XtoStr(lhs, lhs);
			String rhsStr = rhs == null ? "<<N/A>>" : xtoStr.XtoStr(rhs, lhs);
			boolean isEq = Object_.Eq(lhsStr, rhsStr); if (!isEq) pass = false;
			Eq_ary_wkr_addItm(list, i, isEq, lhsStr, rhsStr);
		}
		for (int i = lhsLen; i < rhsLen; i++) {
			String lhsStr = "<<N/A>>";
			Object rhs = rhsList.FetchAt(i);
			String rhsStr = xtoStr.XtoStr(rhs, null);
			Eq_ary_wkr_addItm(list, i, false, lhsStr, rhsStr);
			pass = false;
		}
		if (pass) return;
		String msg = msgBldr.Eq_ary_xtoStr(list, lhsLen, rhsLen, customMsg);
		throw Err_.new_(msg);
	}
	static void Eq_ary_wkr_addItm(ListAdp list, int i, boolean isEq, String lhsString, String rhsString) {
		TfdsEqAryItm itm = new TfdsEqAryItm().Idx_(i).Eq_(isEq).Lhs_(lhsString).Rhs_(rhsString);
		list.Add(itm);
	}
	public static void Err_classMatch(Exception exc, Class<?> type) {
		boolean match = ClassAdp_.Eq_typeSafe(exc, type);
		if (!match) throw Err_.new_key_("Tfds", "error types do not match").Add("expdType", ClassAdp_.FullNameOf_type(type)).Add("actlType", ClassAdp_.NameOf_obj(exc)).Add("actlMsg", Err_.Message_lang(exc));
	}
	public static void Eq_err(Err expd, Exception actlExc) {
		Tfds.Eq(XtoStr_Err(expd), XtoStr_Err(actlExc));
	}
	public static void Err_has(Exception e, String hdr) {
		Tfds.Eq_true(String_.Has(Err_.Message_gplx_brief(e), hdr), "could not find '{0}' in '{1}'", hdr, Err_.Message_gplx_brief(e));
	}
	static String XtoStr_Err(Exception e) {
		Err err = Err_.as_(e); if (err == null) return Err_.Message_lang(e);
		String_bldr sb = String_bldr_.new_();
		sb.Add(err.Hdr()).Add(":");
		for (Object kvo : err.Args()) {
			KeyVal kv = (KeyVal)kvo;
			if (sb.Count() != 0) sb.Add(" ");
			sb.Add_fmt("{0}={1}", kv.Key(), kv.Val());
		}
		return sb.XtoStr();
	}
	static final String EmptyStr = TfdsMsgBldr.EmptyStr;
	static TfdsMsgBldr msgBldr = TfdsMsgBldr.new_();
	public static final Io_url RscDir		= Io_url_.Usr().GenSubDir_nest("xowa", "dev", "tst");
	public static DateAdp Now_time0_add_min(int minutes) {return time0.Add_minute(minutes);}
	@gplx.Internal protected static boolean Now_enabled() {return now_enabled;} static boolean now_enabled;
	public static void Now_enabled_n_() {now_enabled = false;}
	public static void Now_set(DateAdp date) {now_enabled = true; nowTime = date;}
	public static void Now_enabled_y_() {now_enabled = true; nowTime = time0;}
	@gplx.Internal protected static DateAdp Now() {
		DateAdp rv = nowTime;
		nowTime = rv.Add_minute(1);
		return rv;
	}
	private static final DateAdp time0 = DateAdp_.parse_gplx("2001-01-01 00:00:00.000");
	private static DateAdp nowTime; // NOTE: cannot set to time0 due to static initialization;
	public static void WriteText(String text) {ConsoleAdp._.WriteText(text);}
	public static void Write_bry(byte[] ary) {Write(String_.new_utf8_(ary));}
	public static void Write() {Write("tmp");}
	public static void Write(Object... ary) {
		String_bldr sb = String_bldr_.new_();
		int aryLen = Array_.Len(ary);
		for (int i = 0; i < aryLen; i++)
			sb.Add_many("'", Object_.Xto_str_strict_or_null_mark(ary[i]), "'", " ");
		WriteText(sb.XtoStr() + String_.CrLf);
	}
}
class TfdsEqListItmStr_cls_default implements TfdsEqListItmStr {
	public String XtoStr(Object cur, Object actl) {
		return Object_.Xto_str_strict_or_null_mark(cur);
	}
	public static final TfdsEqListItmStr_cls_default _ = new TfdsEqListItmStr_cls_default(); TfdsEqListItmStr_cls_default() {}
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
	public String Eq_ary_xtoStr(ListAdp list, int lhsAryLen, int rhsAryLen, String customMsg) {
		String_bldr sb = String_bldr_.new_();
		sb.Add(CustomMsg_xtoStr(customMsg));
		if (lhsAryLen != rhsAryLen) 
			sb.Add_fmt_line("{0}element counts differ: {1} {2}", "\t\t", lhsAryLen, rhsAryLen);
		int lhsLenMax = 0, rhsLenMax = 0;
		for (int i = 0; i < list.Count(); i++) {
			TfdsEqAryItm itm = (TfdsEqAryItm)list.FetchAt(i);
			int lhsLen = String_.Len(itm.Lhs()), rhsLen = String_.Len(itm.Rhs());
			if (lhsLen > lhsLenMax) lhsLenMax = lhsLen;
			if (rhsLen > rhsLenMax) rhsLenMax = rhsLen;
		}
		for (int i = 0; i < list.Count(); i++) {
			TfdsEqAryItm itm = (TfdsEqAryItm)list.FetchAt(i);
			sb.Add_fmt_line("{0}: {1} {2} {3}"
				, Int_.Xto_str_pad_bgn(itm.Idx(), 4)
				, String_.PadBgn(itm.Lhs(), lhsLenMax, " ")
				, itm.Eq() ? "==" : "!="
				, String_.PadBgn(itm.Rhs(), rhsLenMax, " ")
				);
		}
//			String compSym = isEq ? "  " : "!=";
//			String result = String_.Format("{0}: {1}{2}  {3}  {4}", Int_.Xto_str_pad_bgn(i, 4), lhsString, String_.CrLf + "\t\t", compSym, rhsString);
//			foreach (Object obj in list) {
//				String itmComparison = (String)obj;
//				sb.Add_fmt_line("{0}{1}", "\t\t", itmComparison);
//			}
		return WrapMsg(sb.XtoStr());
	}
	String CustomMsg_xtoStr(String customMsg) {
		return (customMsg == EmptyStr) 
			? ""
			: String_.Concat(customMsg, String_.CrLf);
	}
	public String Obj_xtoStr(Object obj) {
		String s = String_.as_(obj);
		if (s != null) return String_.Concat("'", s, "'"); // if Object is String, put quotes around it for legibility
		XtoStrAble xtoStrAble = XtoStrAble_.as_(obj);
		if (xtoStrAble != null) return xtoStrAble.XtoStr();
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
	public static final String EmptyStr = "";
}

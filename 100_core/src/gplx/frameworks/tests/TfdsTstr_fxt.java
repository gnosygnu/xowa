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
package gplx.frameworks.tests;
import gplx.core.lists.StackAdp;
import gplx.core.lists.StackAdp_;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.MathUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
public class TfdsTstr_fxt {
	public TfdsTstr_fxt Eq_str(Object expd, Object actl, String name) {
		int nameLen = StringUtl.Len(name); if (nameLen > nameLenMax) nameLenMax = nameLen;
		TfdsTstrItm itm = TfdsTstrItm.new_().Expd_(expd).Actl_(actl).Name_(name);
		list.Add(itm);
		return this;
	}
	public void SubName_push(String s) {
		stack.Push(s);
		TfdsTstrItm itm = TfdsTstrItm.new_();
		itm.SubName_make(stack);
		itm.TypeOf = 1;
		list.Add(itm);        
	} StackAdp stack = StackAdp_.new_();
	public void Fail() {
		manualFail = true;
	}boolean manualFail = false;
	public int List_Max(List_adp expd, List_adp actl) {return MathUtl.Max(expd.Len(), actl.Len());}
	public int List_Max(String[] expd, String[] actl) {return MathUtl.Max(expd.length, actl.length);}
	public Object List_FetchAtOrNull(List_adp l, int i) {return (i >= l.Len()) ? null : l.GetAt(i);}

	public void SubName_pop() {stack.Pop();}
	int nameLenMax = 0;
	public void tst_Equal(String hdr) {
		boolean pass = true;
		for (int i = 0; i < list.Len(); i++) {
			TfdsTstrItm itm = (TfdsTstrItm)list.GetAt(i);
			if (!itm.Compare()) pass = false;    // don't break early; Compare all vals
		}
		if (pass && !manualFail) return;
		String_bldr sb = String_bldr_.new_();
		sb.AddCharCrlf();
		sb.AddStrWithCrlf(hdr);
		for (int i = 0; i < list.Len(); i++) {
			TfdsTstrItm itm = (TfdsTstrItm)list.GetAt(i);
			if (itm.TypeOf == 1) {
				sb.AddFmtLine(" /{0}", itm.SubName());
				continue;
			}
			boolean hasError = itm.CompareResult() != TfdsTstrItm.CompareResult_eq;
			String errorKey = hasError ? "*" : " ";
			sb.AddFmtLine("{0}{1} {2}", errorKey, StringUtl.PadEnd(itm.Name(), nameLenMax, " "), itm.Expd());
			if (hasError)
				sb.AddFmtLine("{0}{1} {2}", errorKey, StringUtl.PadEnd("", nameLenMax, " "), itm.Actl());
		}
		sb.Add(StringUtl.Repeat("_", 80));
		throw ErrUtl.NewArgs(sb.ToStr());
	}
	List_adp list = List_adp_.New();
		public static TfdsTstr_fxt new_() {return new TfdsTstr_fxt();} TfdsTstr_fxt() {}
}
class TfdsTstrItm {
	public String Name() {return name;} public TfdsTstrItm Name_(String val) {name = val; return this;} private String name;
	public Object Expd() {return expd;} public TfdsTstrItm Expd_(Object val) {expd = val; return this;} Object expd;
	public Object Actl() {return actl;} public TfdsTstrItm Actl_(Object val) {actl = val; return this;} Object actl;
	public String SubName() {return subName;} private String subName = "";
	public int TypeOf;
	public void SubName_make(StackAdp stack) {
		if (stack.Count() == 0) return;
		List_adp list = stack.XtoList();
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < list.Len(); i++) {
			if (i != 0) sb.Add(".");
			sb.Add((String)list.GetAt(i));
		}
		subName = sb.ToStr();
	}
	public int CompareResult() {return compareResult;} public TfdsTstrItm CompareResult_(int val) {compareResult = val; return this;} int compareResult;
	public boolean Compare() {
		boolean eq = ObjectUtl.Eq(expd, actl);
		compareResult = eq ? 1 : 0;
		return eq;
	}
	public String CompareSym() {
		return compareResult == 1 ? "==" : "!=";
	}
		public static TfdsTstrItm new_() {return new TfdsTstrItm();} TfdsTstrItm() {}
	public static final int CompareResult_none = 0, CompareResult_eq = 1, CompareResult_eqn = 2;
}

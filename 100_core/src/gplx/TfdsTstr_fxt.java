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
import gplx.core.strings.*;
import gplx.core.lists.*;
public class TfdsTstr_fxt {
	public TfdsTstr_fxt Eq_str(Object expd, Object actl, String name) {
		int nameLen = String_.Len(name); if (nameLen > nameLenMax) nameLenMax = nameLen;
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
	public int List_Max(List_adp expd, List_adp actl) {return Math_.Max(expd.Count(), actl.Count());}
	public int List_Max(String[] expd, String[] actl) {return Math_.Max(expd.length, actl.length);}
	public Object List_FetchAtOrNull(List_adp l, int i) {return (i >= l.Count()) ? null : l.Get_at(i);}

	public void SubName_pop() {stack.Pop();}
	int nameLenMax = 0;
	public void tst_Equal(String hdr) {
		boolean pass = true;
		for (int i = 0; i < list.Count(); i++) {
			TfdsTstrItm itm = (TfdsTstrItm)list.Get_at(i);
			if (!itm.Compare()) pass = false;	// don't break early; Compare all vals
		}
		if (pass && !manualFail) return;
		String_bldr sb = String_bldr_.new_();
		sb.Add_char_crlf();
		sb.Add_str_w_crlf(hdr);
		for (int i = 0; i < list.Count(); i++) {
			TfdsTstrItm itm = (TfdsTstrItm)list.Get_at(i);
			if (itm.TypeOf == 1) {
				sb.Add_fmt_line(" /{0}", itm.SubName());
				continue;
			}
			boolean hasError = itm.CompareResult() != TfdsTstrItm.CompareResult_eq;
			String errorKey = hasError ? "*" : " ";
			sb.Add_fmt_line("{0}{1} {2}", errorKey, String_.PadEnd(itm.Name(), nameLenMax, " "), itm.Expd());
			if (hasError)
				sb.Add_fmt_line("{0}{1} {2}", errorKey, String_.PadEnd("", nameLenMax, " "), itm.Actl());
		}
		sb.Add(String_.Repeat("_", 80));
		throw Err_.new_wo_type(sb.To_str());
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
		for (int i = 0; i < list.Count(); i++) {
			if (i != 0) sb.Add(".");
			sb.Add((String)list.Get_at(i));
		}
		subName = sb.To_str();
	}
	public int CompareResult() {return compareResult;} public TfdsTstrItm CompareResult_(int val) {compareResult = val; return this;} int compareResult;
	public boolean Compare() {
		boolean eq = Object_.Eq(expd, actl);
		compareResult = eq ? 1 : 0;
		return eq;
	}
	public String CompareSym() {
		return compareResult == 1 ? "==" : "!=";
	}
        public static TfdsTstrItm new_() {return new TfdsTstrItm();} TfdsTstrItm() {}
	public static final    int CompareResult_none = 0, CompareResult_eq = 1, CompareResult_eqn = 2;
}

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
package gplx.langs.xmls;
import gplx.types.errs.Err;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.KeyValHash;
import gplx.types.basics.utls.CharUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.IntRef;
public class Xpath_ {
	public static XmlNdeList SelectAll(XmlNde owner, String xpath) {return Select(owner, xpath, Xpath_Args.all_());}
	public static XmlNde SelectFirst(XmlNde owner, String xpath) {
		XmlNdeList rv = Select(owner, xpath, Xpath_Args.first_());
		return rv.Count() == 0 ? null : rv.Get_at(0);    // selects first
	}
	public static XmlNdeList SelectElements(XmlNde owner) {
		XmlNdeList subNdes = owner.SubNdes(); int count = subNdes.Count();
		XmlNdeList_cls_list list = new XmlNdeList_cls_list(count);
		for (int i = 0; i < count; i++) {
			XmlNde sub = subNdes.Get_at(i);
			if (sub.NdeType_element()) 
				list.Add(sub);
		}
		return list;
	}
	static XmlNdeList Select(XmlNde owner, String xpath, Xpath_Args args) {
		XmlNdeList_cls_list rv = new XmlNdeList_cls_list(8);
		String[] parts = StringUtl.Split(xpath, "/");
		TraverseSubs(owner, parts, 0, rv, args);
		return rv;
	}
	static void TraverseSubs(XmlNde owner, String[] parts, int depth, XmlNdeList_cls_list results, Xpath_Args args) {
		int partsLen = ArrayUtl.Len(parts);
		if (depth == partsLen) return;
		String name = parts[depth];
		XmlNdeList subNdes = owner.SubNdes(); int count = subNdes.Count();
		for (int i = 0; i < count; i++) {
			XmlNde sub = subNdes.Get_at(i);
			if (args.Cancel) return;
			if (!StringUtl.Eq(name, sub.Name())) continue;
			if (depth == partsLen - 1) {
				results.Add(sub);
				if (args.SelectFirst) args.Cancel = true;
			}
			else
				TraverseSubs(sub, parts, depth + 1, results, args);
		}
	}
	public static final String InnetTextKey = "&innerText";
	public static KeyValHash ExtractKeyVals(String xml, IntRef posRef, String nodeName) {
		int pos = posRef.Val();
		Err xmlErr = ErrUtl.NewArgs("error parsing xml", "xml", xml, "pos", pos);
		String headBgnFind = "<" + nodeName + " "; int headBgnFindLen = StringUtl.Len(headBgnFind);
		int headBgn = StringUtl.FindFwd(xml, headBgnFind, pos);                        if (headBgn == StringUtl.FindNone) return null;
		int headEnd = StringUtl.FindFwd(xml, ">", headBgn + headBgnFindLen);            if (headEnd == StringUtl.FindNone) throw xmlErr;
		String atrXml = StringUtl.Mid(xml, headBgn, headEnd);
		KeyValHash rv = ExtractNodeVals(atrXml, xmlErr);
		boolean noInnerText = StringUtl.CharAt(xml, headEnd - 1) == '/';                // if />, then no inner text
		if (!noInnerText) {
			int tail = StringUtl.FindFwd(xml, "</" + nodeName + ">", headBgn);        if (tail == StringUtl.FindNone) throw ErrUtl.NewArgs("could not find tailPos", "headBgn", headBgn);
			String innerText = StringUtl.Mid(xml, headEnd + 1, tail);
			rv.Add(InnetTextKey, innerText);
		}
		posRef.ValSet(headEnd);
		return rv;
	}
	static KeyValHash ExtractNodeVals(String xml, Err xmlErr) {
		KeyValHash rv = new KeyValHash();
		int pos = 0;
		while (true) {
			int eqPos = StringUtl.FindFwd(xml, "=", pos);        if (eqPos == StringUtl.FindNone) break;
			int q0Pos = StringUtl.FindFwd(xml, "\"", eqPos + 1); if (q0Pos == StringUtl.FindNone) throw xmlErr.ArgsAdd("eqPos", eqPos);
			int q1Pos = StringUtl.FindFwd(xml, "\"", q0Pos + 1); if (q1Pos == StringUtl.FindNone) throw xmlErr.ArgsAdd("q1Pos", q1Pos);
			int spPos = eqPos - 1;
			while (spPos > -1) {
				char c = StringUtl.CharAt(xml, spPos);
				if (CharUtl.IsWhitespace(c)) break;
				spPos--;
			}
			if (spPos == StringUtl.FindNone) throw xmlErr.ArgsAdd("sub_msg", "could not find hdr").ArgsAdd("eqPos", eqPos);
			String key = StringUtl.Mid(xml, spPos + 1, eqPos);
			String val = StringUtl.Mid(xml, q0Pos + 1, q1Pos);
			rv.Add(key, val);
			pos = q1Pos;
		}
		return rv;
	}
}
class Xpath_Args {
	public boolean SelectFirst; // false=SelectAll
	public boolean Cancel;
	public static Xpath_Args all_() {return new Xpath_Args(false);}
	public static Xpath_Args first_() {return new Xpath_Args(true);}
	Xpath_Args(boolean selectFirst) {this.SelectFirst = selectFirst;}
}
enum Xpath_SelectMode {All, First}

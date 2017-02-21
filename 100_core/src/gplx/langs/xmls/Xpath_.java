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
package gplx.langs.xmls; import gplx.*; import gplx.langs.*;
import gplx.core.primitives.*;
public class Xpath_ {
	public static XmlNdeList SelectAll(XmlNde owner, String xpath) {return Select(owner, xpath, Xpath_Args.all_());}
	public static XmlNde SelectFirst(XmlNde owner, String xpath) {
		XmlNdeList rv = Select(owner, xpath, Xpath_Args.first_());
		return rv.Count() == 0 ? null : rv.Get_at(0);	// selects first
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
		String[] parts = String_.Split(xpath, "/");
		TraverseSubs(owner, parts, 0, rv, args);
		return rv;
	}
	static void TraverseSubs(XmlNde owner, String[] parts, int depth, XmlNdeList_cls_list results, Xpath_Args args) {
		int partsLen = Array_.Len(parts);
		if (depth == partsLen) return;
		String name = parts[depth];
		XmlNdeList subNdes = owner.SubNdes(); int count = subNdes.Count();
		for (int i = 0; i < count; i++) {
			XmlNde sub = subNdes.Get_at(i);
			if (args.Cancel) return;
			if (!String_.Eq(name, sub.Name())) continue;
			if (depth == partsLen - 1) {
				results.Add(sub);
				if (args.SelectFirst) args.Cancel = true;
			}
			else
				TraverseSubs(sub, parts, depth + 1, results, args);
		}
	}
	public static final    String InnetTextKey = "&innerText";
	public static Keyval_hash ExtractKeyVals(String xml, Int_obj_ref posRef, String nodeName) {
		int pos = posRef.Val();
		Err xmlErr = Err_.new_wo_type("error parsing xml", "xml", xml, "pos", pos);
		String headBgnFind = "<" + nodeName + " "; int headBgnFindLen = String_.Len(headBgnFind);
		int headBgn = String_.FindFwd(xml, headBgnFind, pos);						if (headBgn == String_.Find_none) return null;
		int headEnd = String_.FindFwd(xml, ">", headBgn + headBgnFindLen);			if (headEnd == String_.Find_none) throw xmlErr;
		String atrXml = String_.Mid(xml, headBgn, headEnd);
		Keyval_hash rv = ExtractNodeVals(atrXml, xmlErr);
		boolean noInnerText = String_.CharAt(xml, headEnd - 1) == '/';				// if />, then no inner text
		if (!noInnerText) {
			int tail = String_.FindFwd(xml, "</" + nodeName + ">", headBgn);		if (tail == String_.Find_none) throw Err_.new_wo_type("could not find tailPos", "headBgn", headBgn);
			String innerText = String_.Mid(xml, headEnd + 1, tail);
			rv.Add(InnetTextKey, innerText);
		}
		posRef.Val_(headEnd);
		return rv;
	}
	static Keyval_hash ExtractNodeVals(String xml, Err xmlErr) {
		Keyval_hash rv = new Keyval_hash();
		int pos = 0;
		while (true) {
			int eqPos = String_.FindFwd(xml, "=", pos);		if (eqPos == String_.Find_none) break;
			int q0Pos = String_.FindFwd(xml, "\"", eqPos + 1); if (q0Pos == String_.Find_none) throw xmlErr.Args_add("eqPos", eqPos);
			int q1Pos = String_.FindFwd(xml, "\"", q0Pos + 1); if (q1Pos == String_.Find_none) throw xmlErr.Args_add("q1Pos", q1Pos);
			int spPos = eqPos - 1;
			while (spPos > -1) {
				char c = String_.CharAt(xml, spPos);
				if (Char_.IsWhitespace(c)) break;
				spPos--;
			}
			if (spPos == String_.Find_none) throw xmlErr.Args_add("sub_msg", "could not find hdr", "eqPos", eqPos);
			String key = String_.Mid(xml, spPos + 1, eqPos);
			String val = String_.Mid(xml, q0Pos + 1, q1Pos);
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

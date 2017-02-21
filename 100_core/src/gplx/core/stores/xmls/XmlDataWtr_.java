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
package gplx.core.stores.xmls; import gplx.*; import gplx.core.*; import gplx.core.stores.*;
import gplx.core.strings.*; import gplx.core.gfo_ndes.*;
public class XmlDataWtr_ {
	public static DataWtr new_() {return XmlDataWtr.new_();}
}
class XmlDataWtr extends DataWtr_base implements DataWtr {
	public void InitWtr(String key, Object val) {}
	@Override public void WriteData(String name, Object val) {
//			if (val == null) return;
		String valString = Object_.Xto_str_strict_or_empty(val);
		int valStringLen = String_.Len(valString);
		sb.Add(" ").Add(name).Add("=\"");
		for (int i = 0; i < valStringLen; i++) {
			char c = String_.CharAt(valString, i);
			if		(c == '<')		sb.Add("&lt;");
			else if (c == '>')		sb.Add("&gt;");
			else if (c == '&')		sb.Add("&amp;");
			else if (c == '\"')		sb.Add("&quote;");
			else					sb.Add(c);
		}
		sb.Add("\"");
//			XmlAttribute atr = doc.CreateAttribute(name);
//			atr.Value = (val == null) ? String_.Empty : val.toString();
//			nde.Attributes.Append(atr);
	}
	public void WriteLeafBgn(String leafName) {this.WriteXmlNodeBegin(leafName);}
	public void WriteLeafEnd() {}
	public void WriteTableBgn(String name, GfoFldList fields) {this.WriteXmlNodeBegin(name);}
	@Override public void WriteNodeBgn(String nodeName) {this.WriteXmlNodeBegin(nodeName);}
	@Override public void WriteNodeEnd() {this.WriteXmlNodeEnd();}
	public String To_str() {
		while (names.Count() > 0) {
			WriteXmlNodeEnd();
		}
		return sb.To_str();
//			while (nde.ParentNode != null)
//				WriteXmlNodeEnd();			// close all open ndes automatically
//			return doc.OuterXml;
	}
	public void WriteComment(String comment) {
		sb.Add("<!--" + comment + "-->");
//			XmlComment xmlComment = doc.CreateComment(comment);
//			nde.AppendChild(xmlComment);
	}
	public void Clear() {
		sb.Clear();
//			doc = new XmlDocument();
	}
	void WriteXmlNodeBegin(String name) {
		if (ndeOpened) {
			sb.Add(">" + String_.CrLf);
		}
		ndeOpened = true;
		names.Add(name);
		sb.Add("<" + name);
//			XmlNode owner = nde;
//			nde = doc.CreateElement(name);
//			if (owner == null)		// first call to WriteXmlNodeBegin(); append child to doc
//				doc.AppendChild(nde);
//			else {
//				WriteLineFeedIfNeeded(doc, owner);
//				owner.AppendChild(nde);
//			}
	}
	void WriteXmlNodeEnd() {
		if (ndeOpened) {
			sb.Add(" />" + String_.CrLf);
			ndeOpened = false;
		}
		else {
			String name = (String)names.Get_at_last();
			sb.Add("</" + name + ">" + String_.CrLf);
		}
		names.Del_at(names.Count() - 1);
		//			if (nde.ParentNode == null) throw Err_.new_wo_type("WriteXmlNodeEnd() called on root node");
//			nde = nde.ParentNode;
//			WriteLineFeed(doc, nde);
	}
//		void WriteLineFeed(XmlDocument doc, XmlNode owner) {
//			XmlSignificantWhitespace crlf = doc.CreateSignificantWhitespace(String_.CrLf);
//			owner.AppendChild(crlf);
//		}
//		void WriteLineFeedIfNeeded(XmlDocument doc, XmlNode owner) {
//			XmlSignificantWhitespace lastSubNode = owner.ChildNodes[owner.ChildNodes.Count - 1] as XmlSignificantWhitespace;
//			if (lastSubNode == null)
//				WriteLineFeed(doc, owner); // write LineFeed for consecutive WriteXmlNodeBegin calls; ex: <node1><node11>				
//		}
	@Override public SrlMgr SrlMgr_new(Object o) {return new XmlDataWtr();}
	boolean ndeOpened = false;
//		int atrCount = 0;
//		int ndeState = -1; static final    int NdeState0_Opened = 0, NdeState0_H = 1;
//		XmlDocument doc = new XmlDocument(); XmlNode nde;
	List_adp names = List_adp_.New();
	String_bldr sb = String_bldr_.new_();
	public static XmlDataWtr new_() {return new XmlDataWtr();} XmlDataWtr() {}
}

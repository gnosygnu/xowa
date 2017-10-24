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
import org.w3c.dom.NodeList;
public interface XmlNdeList {
	int Count();
	XmlNde Get_at(int i);
}
class XmlNdeList_cls_xml implements XmlNdeList {
	public int Count() {return list.getLength();}
	public XmlNde Get_at(int i) {return new XmlNde(list.item(i));}		
	@gplx.Internal protected XmlNdeList_cls_xml(NodeList list) {this.list = list;} NodeList list;
}
class XmlNdeList_cls_list implements XmlNdeList {
	public int Count() {return list.Count();}
	public XmlNde Get_at(int i) {return (XmlNde)list.Get_at(i);}
	public void Add(XmlNde xnde) {list.Add(xnde);}
	@gplx.Internal protected XmlNdeList_cls_list(int count) {list = List_adp_.New(); list.Resize_bounds(count);} List_adp list;
}
//#}
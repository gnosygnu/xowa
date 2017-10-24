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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
public class XmlAtrList {
	public int Count() {return list == null ? 0 : list.getLength();}
	public String FetchValOr(String key, String or) {
		Node xatr = list.getNamedItem(key);
		return (xatr == null) ? or : xatr.getNodeValue();
	}
	public XmlAtr Fetch(String key) {
		Node xatr = list.getNamedItem(key); if (xatr == null) throw Err_.new_missing_key(key);
		return new XmlAtr(xatr);
	}
	public XmlAtr Fetch_or_null(String key) {
		Node xatr = list.getNamedItem(key); if (xatr == null) return null;
		return new XmlAtr(xatr);
	}
	public XmlAtr Get_at(int i) {return list == null ? null : new XmlAtr(list.item(i));}
	@gplx.Internal protected XmlAtrList(NamedNodeMap list) {this.list = list;} NamedNodeMap list;
}

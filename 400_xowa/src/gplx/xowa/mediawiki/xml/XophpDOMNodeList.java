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
package gplx.xowa.mediawiki.xml;
import gplx.langs.xmls.*;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
public class XophpDOMNodeList {
	private final List_adp list = List_adp_.New();
	public XophpDOMNodeList(XmlNdeList nde_list) {
		int len = nde_list.Count();
		for (int i = 0; i < len; i++) {
			XmlNde nde = nde_list.Get_at(i);
			list.Add(new XophpDOMNode(nde));
		}
	}
	public int count() {return list.Len();}
	public XophpDOMNode item(int i) {return (XophpDOMNode)list.GetAt(i);}
}

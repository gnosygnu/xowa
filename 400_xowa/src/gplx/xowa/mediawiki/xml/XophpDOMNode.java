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
package gplx.xowa.mediawiki.xml; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
import gplx.langs.xmls.*;
public class XophpDOMNode {
	private final    XmlNde xnde;
	public String nodeValue = null;
	public XophpDOMNode(XmlNde xnde) {
		this.xnde = xnde;
		// TODO.PHP:implement edge cases for nodeValue; https://stackoverflow.com/questions/12380919/php-dom-textcontent-vs-nodevalue
		this.nodeValue = xnde.Text_inner();
	}
	public String getAttribute(String attribName) {
		return xnde.Atrs().Get_by(attribName).Value();
	}
	public XophpDOMNodeList getElementsByTagName(String tagName) {
		return new XophpDOMNodeList(XmlDoc_.Select_tags(xnde, tagName));
	}
}

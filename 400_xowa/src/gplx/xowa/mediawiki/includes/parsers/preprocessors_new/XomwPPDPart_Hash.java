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
package gplx.xowa.mediawiki.includes.parsers.preprocessors_new; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
/**
* @ingroup Parser
*/
public class XomwPPDPart_Hash extends XomwPPDPart {
	public XomwPPDPart_Hash(String output) {
		XophpArray accum;
		if (XophpString_.eq_not(output, "")) {
			accum = XophpArray.New(output);
		} else {
			accum = XophpArray.New();
		}
		this.ctor_XomwPPDPart(accum);
	}

	@Override public XomwPPDPart New(String val) {return new XomwPPDPart_Hash(val);}

	public static final XomwPPDPart_Hash Instance = new XomwPPDPart_Hash("");
}

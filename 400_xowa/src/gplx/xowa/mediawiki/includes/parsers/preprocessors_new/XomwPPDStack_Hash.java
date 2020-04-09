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
// MW.SRC:1.33
/**
* Stack cl+ass to help Preprocessor::preprocessToObj()
* @ingroup Parser
*/
public class XomwPPDStack_Hash extends XomwPPDStack {
	public XomwPPDStack_Hash(XomwPPDPart partClass) {super(partClass);
		this.elementClass = XomwPPDStackElement_Hash.Prototype;
		// this.rootAccum = XophpArray.New(); // XO.NOTE: PHP does accum &= rootAccum, so doing "this.rootAccum = XophpArray.New()" will also do "accum = this.rootAccum" automatically
	}
}

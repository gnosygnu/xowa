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
package gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.src.Converter;
import gplx.types.basics.utls.IntUtl;
import gplx.xowa.mediawiki.*;
import gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.src.*;
// MW.SRC:1.33.1
/**
* Helper for Converter.
* The super class for operators and expressions, describing a region of the input String.
*/
public class XomwFragment {
	public XomwConverter parser;
	public int pos, length, end;

	public XomwFragment(XomwConverter parser, int pos, int length) {
		this.parser = parser;
		this.pos = pos;
		this.length = length;
		this.end = pos + length;
	}

	public void error(String message) {
		String text = this.getText();
		throw XomwError.New__fmt("$message at position " + IntUtl.ToStr(this.pos + 1) + ": \"$text\"", message, text);
	}

	public String getText() {
		return XophpString_.substr(this.parser.rule, this.pos, this.length);
	}
}
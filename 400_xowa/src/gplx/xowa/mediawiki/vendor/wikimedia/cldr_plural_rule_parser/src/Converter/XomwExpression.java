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
package gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.src.Converter; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.vendor.*; import gplx.xowa.mediawiki.vendor.wikimedia.*; import gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.*; import gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.src.*;
// MW.SRC:1.33.1
/**
* Helper for Converter.
* An expression Object, representing a region of the input String (for error
* messages), the RPN notation used to evaluate it, and the result type for
* validation.
*/
public class XomwExpression extends XomwFragment { 	/** @var String */
	public String type;

	/** @var String */
	public String rpn;

	public XomwExpression(XomwConverter parser, String type, String rpn, int pos, int length) {super(parser, pos, length);
		this.type = type;
		this.rpn = rpn;
	}

	public boolean isType(String type) {
		if (String_.Eq(type, "range") && (String_.Eq(this.type, "range") || String_.Eq(this.type, "number"))) {
			return true;
		}
		if (String_.Eq(type, this.type)) {
			return true;
		}

		return false;
	}
}
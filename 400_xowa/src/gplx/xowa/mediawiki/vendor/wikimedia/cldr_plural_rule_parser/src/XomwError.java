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
package gplx.xowa.mediawiki.vendor.wikimedia.cldr_plural_rule_parser.src;
import gplx.types.errs.Err;
import gplx.xowa.mediawiki.XophpString_;
// MW.SRC:1.33.1
/**
* The exception cl+ass for all the cl+asses in this file. This will be thrown
* back to the caller if there is any validation error.
*/
public class XomwError extends Err {
	public XomwError(String msg) {
		super(msg);
	}
	public static XomwError New(String msg) {return new XomwError("CLDR plural rule error: " + msg);}
	public static XomwError New__fmt(String msg, Object... args) {
		return new XomwError("CLDR plural rule error: " + XophpString_.Fmt(msg, args));
	}
}

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
package gplx.xowa.mediawiki.includes.parsers;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.StringUtl;
import org.junit.*;
public class Xomw_regex_parser__tst {
	private final Xomw_regex_parser__fxt fxt = new Xomw_regex_parser__fxt();
	@Test public void Ary__space() {
		fxt.Test__parse_ary(StringUtl.Ary("\\s"), StringUtl.Ary(" "));
	}
	@Test public void Ary__utf8() {
		fxt.Test__parse_ary(StringUtl.Ary("\\xc2\\xa7", "\\xe0\\xb9\\x90"), StringUtl.Ary("§", "๐"));
	}
	@Test public void Rng__ascii() {
		fxt.Test__parse_rng("a", "c", StringUtl.Ary("a", "b", "c"));
	}
}
class Xomw_regex_parser__fxt {
	private final Xomw_regex_parser parser = new Xomw_regex_parser();
	public void Test__parse_ary(String[] ary, String[] expd) {
		parser.Add_ary(ary);
		GfoTstr.EqLines(expd, StringUtl.Ary(parser.Rslt()));
	}
	public void Test__parse_rng(String bgn, String end, String[] expd) {
		parser.Add_rng("a", "c");
		GfoTstr.EqLines(expd, StringUtl.Ary(parser.Rslt()));
	}
}

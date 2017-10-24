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
package gplx.core.brys.fmts; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
import org.junit.*;
public class Bry_keys_parser_tst {
	private final    Bry_keys_parser_fxt fxt = new Bry_keys_parser_fxt();
	@Test  public void None()			{fxt.Test("a");}
	@Test  public void One()			{fxt.Test("~{a}"				, "a");}
	@Test  public void Many()			{fxt.Test("~{a}b~{c}d~{e}"		, "a", "c", "e");}
	@Test  public void Dupe()			{fxt.Test("~{a}b~{a}"			, "a");}
	@Test  public void Bug__space()		{fxt.Test("~{a}~{b} ~{c}"		, "a", "b", "c");}	// DATE:2016-08-02
}
class Bry_keys_parser_fxt {
	public void Test(String fmt, String... expd) {
		Tfds.Eq_ary(expd, String_.Ary(Bry_fmt_parser_.Parse_keys(Bry_.new_u8(fmt))));
	}
}

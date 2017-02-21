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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Vnt_rule_parser__undi_tst {
	private final Vnt_rule_parser_fxt fxt = new Vnt_rule_parser_fxt();
	@Test  public void One()					{fxt.Test_parse("k1=>x1:v1;"				, "x1:k1=v1");}
	@Test  public void Many()					{fxt.Test_parse("k1=>x1:v1;k2=>x2:v2;"		, "x1:k1=v1", "x2:k2=v2");}
}

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
package gplx.xowa.wikis.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*;
public class Xow_ttl__ws_tst {
	@Before public void init() {fxt.Reset();} private Xow_ttl_fxt fxt = new Xow_ttl_fxt();
	@Test   public void Space()					{fxt.Init_ttl("  a  b  ")			.Expd_page_url("A_b").Expd_page_txt("A b").Test();}
	@Test   public void Space_w_ns()			{fxt.Init_ttl("  Help  :  a  b  ")	.Expd_page_url("A_b").Expd_page_txt("A b").Test();}
	@Test   public void Nl()					{fxt.Init_ttl("\n\na\n\n")			.Expd_page_txt("A").Test();}
	@Test   public void Nl_end()				{fxt.Init_ttl("a\nb")				.Expd_page_txt("A b").Test();}
	@Test   public void Tab()					{fxt.Init_ttl("\ta\t")				.Expd_page_txt("A").Test();}
	@Test   public void Nbsp()					{fxt.Init_ttl("A&nbsp;bc")			.Expd_page_url("A_bc").Expd_page_txt("A bc").Test();}	// PURPOSE:convert "&nbsp;" to " "; DATE:2014-09-25
	@Test   public void Nbsp_mix()				{fxt.Init_ttl("A &nbsp; bc")		.Expd_page_url("A_bc").Expd_page_txt("A bc").Test();}	// PURPOSE:convert multiple "&nbsp;" to " "; PAGE:en.w:Greek_government-debt_crisis; DATE:2014-09-25
}

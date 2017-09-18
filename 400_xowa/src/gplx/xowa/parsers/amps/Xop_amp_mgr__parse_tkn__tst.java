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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.core.tests.*;
public class Xop_amp_mgr__parse_tkn__tst {
	@Before public void init() {} private final    Xop_amp_mgr_fxt fxt = new Xop_amp_mgr_fxt();
	@Test  public void Ent()					{fxt.Test__parse_tkn__ent("&amp;"		, "&amp;");}	// check for html_ref 
	@Test  public void Ent__fail()				{fxt.Test__parse_tkn__txt("&nil;"		, 1);}
	@Test  public void Num__nex()				{fxt.Test__parse_tkn__ncr("&#x3A3;"		, 931);}		// check for html_ncr; Σ: http://en.wikipedia.org/wiki/Numeric_character_reference
	@Test  public void Num__dec()				{fxt.Test__parse_tkn__ncr("&#931;"		, 931);}
	@Test  public void Num__fail()				{fxt.Test__parse_tkn__txt("&#"			, 1);}
}

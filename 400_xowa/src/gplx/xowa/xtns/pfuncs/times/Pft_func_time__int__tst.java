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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pft_func_time__int__tst {
	@Before	public void init()							{fxt.Reset(); Datetime_now.Manual_(DateAdp_.new_(2012, 1, 2, 3, 4, 5, 6));} private final    Xop_fxt fxt = new Xop_fxt();
	@Test   public void Time_before_date__dmy()			{fxt.Test_parse_tmpl_str("{{#time:Y-m-d H:i|01:02 3.4.2005}}"				, "2005-04-03 01:02");}	// PAGE:sk.w:Dr._House; DATE:2014-09-23
	@Test   public void Time_before_date__mdy()			{fxt.Test_parse_tmpl_str("{{#time:Y-m-d H:i|01:02 3.14.2005}}"				, "<strong class=\"error\">Invalid month: 14</strong>");}	// mdy is invalid; DATE:2014-09-23
}

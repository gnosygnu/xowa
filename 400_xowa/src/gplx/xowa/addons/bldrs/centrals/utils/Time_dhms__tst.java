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
package gplx.xowa.addons.bldrs.centrals.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import org.junit.*; import gplx.core.tests.*;
public class Time_dhms__tst {
	private final    Time_dhms__fxt fxt = new Time_dhms__fxt();
	@Test 	public void S__0()				{fxt.Test__to_str(       0, 4,           "0 s");}
	@Test 	public void S__1()				{fxt.Test__to_str(       1, 4,           "1 s");}
	@Test 	public void S__2()				{fxt.Test__to_str(      30, 4,          "30 s");}
	@Test 	public void M__1()				{fxt.Test__to_str(      60, 4,        "1:00 m");}
	@Test 	public void M__2()				{fxt.Test__to_str(     600, 4,       "10:00 m");}
	@Test 	public void H__1()				{fxt.Test__to_str(    3600, 4,     "1:00:00 h");}
	@Test 	public void H__2()				{fxt.Test__to_str(    5025, 4,     "1:23:45 h");}
	@Test 	public void D__1()				{fxt.Test__to_str(   86400, 4,  "1:00:00:00 d");}
	@Test 	public void Max_places()		{fxt.Test__to_str(   86400, 2,        "1:00 d");}
}
class Time_dhms__fxt {
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public Time_dhms__fxt Test__to_str(long v, int max_places, String expd) {
		Gftest.Eq__str(expd, Time_dhms_.To_str(bfr, v, Bool_.Y, max_places));
		return this;}
}
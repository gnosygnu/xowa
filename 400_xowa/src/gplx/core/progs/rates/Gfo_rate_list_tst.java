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
package gplx.core.progs.rates; import gplx.*; import gplx.core.*; import gplx.core.progs.*;
import org.junit.*; import gplx.core.tests.*;
public class Gfo_rate_list_tst {
	private final    Gfo_rate_list_fxt fxt = new Gfo_rate_list_fxt();
	@Before public void init() {fxt.Clear();}
	@Test 	public void Add__1()			{fxt.Add(100, 20).Test(5, 5);}
	@Test 	public void Add__2()			{fxt.Add(100, 20).Add(100, 30).Test(4, .20d);}
	@Test 	public void Add__3()			{fxt.Add(100, 20).Add(100, 30).Add(100, 50).Test(3, .25d);}
	@Test 	public void Add__4()			{fxt.Add(100, 20).Add(100, 30).Add(100, 50).Add(600, 0).Test(9, 2);}
}
class Gfo_rate_list_fxt {
	private final    Gfo_rate_list list = new Gfo_rate_list(6);
	public void Clear() {list.Clear();}
	public Gfo_rate_list_fxt Add(long data, long time) {list.Add(data, time); return this;}
	public void Test(double expd_rate, double expd_delta) {
		Gftest.Eq__double(expd_rate	, list.Cur_rate()	, "cur_rate");
		Gftest.Eq__double(expd_delta, list.Cur_delta()	, "cur_delta");
	}
}
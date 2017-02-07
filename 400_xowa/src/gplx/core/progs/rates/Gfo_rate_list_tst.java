/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Int_pool_tst {
	private final Int_pool_tstr tstr = new Int_pool_tstr();
	@Before public void init() {tstr.Clear();}
	@Test  public void Get__one() {
		tstr.Test_get(0);
	}
	@Test  public void Get__many() {
		tstr.Test_get(0);
		tstr.Test_get(1);
		tstr.Test_get(2);
	}
	@Test  public void Del__one() {
		tstr.Test_get(0);
		tstr.Exec_del(0);
		tstr.Test_get(0);
	}
	@Test  public void Del__sequential() {
		tstr.Test_get(0);
		tstr.Test_get(1);
		tstr.Test_get(2);
		tstr.Exec_del(2).Test_get(2);
		tstr.Exec_del(2);
		tstr.Exec_del(1);
		tstr.Exec_del(0).Test_get(0);
	}
	@Test  public void Del__out_of_order() {
		tstr.Test_get(0);
		tstr.Test_get(1);
		tstr.Test_get(2);
		tstr.Exec_del(0).Test_get(0);
		tstr.Exec_del(0);
		tstr.Exec_del(1);
		tstr.Exec_del(2);
		tstr.Test_get(0);
	}
}
class Int_pool_tstr {
	private final Int_pool pool = new Int_pool();
	public void Clear() {pool.Clear();}
	public Int_pool_tstr Test_get(int expd) {
		Tfds.Eq(expd, pool.Get_next());
		return this;
	}
	public Int_pool_tstr Exec_del(int val) {
		pool.Del(val);
		return this;
	}
}

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
	@Test  public void Del__out_of_order_2() {
		tstr.Test_get(0);
		tstr.Test_get(1);
		tstr.Test_get(2);
		tstr.Exec_del(1);
		tstr.Exec_del(2);
		tstr.Exec_del(0);
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

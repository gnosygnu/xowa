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
package gplx.core.threads.poolables; import gplx.*; import gplx.core.*; import gplx.core.threads.*;
import org.junit.*;
public class Gfo_poolable_mgr_tst {
	private final    Gfo_poolable_mgr_tstr tstr = new Gfo_poolable_mgr_tstr();
	@Before public void init() {tstr.Clear();}
	@Test  public void Get__one() {
		tstr.Test__get(0);
		tstr.Test__free__len(0);
		tstr.Test__pool__len(2);
	}
	@Test  public void Get__many__expand() {
		tstr.Test__get(0);
		tstr.Test__get(1);
		tstr.Test__get(2);
		tstr.Test__free__len(0);
		tstr.Test__pool__len(4);
	}
	@Test  public void Rls__lifo() {
		tstr.Test__get(0);
		tstr.Test__get(1);
		tstr.Test__get(2);
		tstr.Exec__rls(2);
		tstr.Exec__rls(1);
		tstr.Exec__rls(0);
		tstr.Test__pool__nxt(0);
		tstr.Test__free__len(0);
	}
	@Test  public void Rls__fifo() {
		tstr.Test__get(0);
		tstr.Test__get(1);
		tstr.Test__get(2);
		tstr.Exec__rls(0);
		tstr.Exec__rls(1);
		tstr.Test__pool__nxt(3);
		tstr.Test__free__len(2);			// 2 items in free_ary
		tstr.Test__free__ary(0, 1, 0, 0);

		tstr.Test__get(1);
		tstr.Exec__rls(1);

		tstr.Exec__rls(2);
		tstr.Test__free__len(0);			// 0 items in free_ary
		tstr.Test__free__ary(0, 0, 0, 0);
	}
}
class Gfo_poolable_mgr_tstr {
	private final    Gfo_poolable_mgr mgr = new Gfo_poolable_mgr(new Sample_poolable_itm(null, -1, Object_.Ary_empty), Object_.Ary("make"), 2, 8);
	public void Clear() {mgr.Clear_fast();}
	public void Test__get(int expd_idx) {
		Sample_poolable_itm actl_itm = (Sample_poolable_itm)mgr.Get_fast();
		Tfds.Eq(expd_idx, actl_itm.Pool__idx(), "pool_idx");
	}
	public void Test__free__ary(int... expd)	{Tfds.Eq_ary(expd, mgr.Free_ary(), "mgr.Free_ary()");}
	public void Test__free__len(int expd)			{Tfds.Eq(expd, mgr.Free_len(), "mgr.Free_len()");}
	public void Test__pool__len(int expd)			{Tfds.Eq(expd, mgr.Pool_len(), "mgr.Pool_len()");}
	public void Test__pool__nxt(int expd)			{Tfds.Eq(expd, mgr.Pool_nxt(), "mgr.Pool_nxt()");}
	public void Exec__rls(int idx) {mgr.Rls_fast(idx);}
}
class Sample_poolable_itm implements Gfo_poolable_itm {
	private Gfo_poolable_mgr pool_mgr;
	public Sample_poolable_itm(Gfo_poolable_mgr pool_mgr, int pool_idx, Object[] make_args) {this.pool_mgr = pool_mgr; this.pool_idx = pool_idx; this.pool__make_args = make_args;}
	public int				Pool__idx() {return pool_idx;} private final    int pool_idx;
	public Object[]			Pool__make_args() {return pool__make_args;} private final    Object[] pool__make_args;
	public void				Pool__rls() {pool_mgr.Rls_safe(pool_idx);}
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {return new Sample_poolable_itm(pool_mgr, idx, args);}
}

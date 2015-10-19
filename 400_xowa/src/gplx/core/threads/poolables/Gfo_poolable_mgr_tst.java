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
package gplx.core.threads.poolables; import gplx.*; import gplx.core.*; import gplx.core.threads.*;
import org.junit.*;
public class Gfo_poolable_mgr_tst {
	private final Gfo_poolable_mgr_tstr tstr = new Gfo_poolable_mgr_tstr();
	@Before public void init() {tstr.Clear();}
	@Test  public void Get__one() {
		tstr.Test__get(0);
		tstr.Test__mgr__free(0, 0);
		tstr.Test__pool__len(2);
	}
}
class Gfo_poolable_mgr_tstr {
	private final Gfo_poolable_mgr mgr = new Gfo_poolable_mgr(new Sample_poolable_itm(-1, Object_.Ary_empty), Object_.Ary("make"), Object_.Ary("clear"), 2, 8);
	public void Clear() {mgr.Clear_fast();}
	public void Test__get(int expd_idx) {
		Sample_poolable_itm actl_itm = (Sample_poolable_itm)mgr.Get_fast();
		Tfds.Eq(expd_idx, actl_itm.Pool__idx(), "pool_idx");
	}
	public void Test__mgr__free(int... expd) {
		Tfds.Eq_ary(expd, mgr.Free(), "mgr.Free()");
	}
	public void Test__pool__len(int expd) {
		Tfds.Eq(expd, mgr.Pool_len(), "mgr.Pool_len()");
	}
}
class Sample_poolable_itm implements Gfo_poolable_itm {
	public Sample_poolable_itm(int pool_idx, Object[] make_args) {this.pool_idx = pool_idx; this.pool__make_args = make_args;}
	public int				Pool__idx() {return pool_idx;} private final int pool_idx;
	public Object[]			Pool__make_args() {return pool__make_args;} private final Object[] pool__make_args;
	public Object[]			Pool__clear_args() {return pool__clear_args;} private Object[] pool__clear_args;
	public void				Pool__clear (Object[] args) {this.pool__clear_args = args;}
	public Gfo_poolable_itm	Pool__make	(int idx, Object[] args) {return new Sample_poolable_itm(idx, args);}
}
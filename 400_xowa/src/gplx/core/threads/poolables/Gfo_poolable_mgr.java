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
public class Gfo_poolable_mgr {
	private final Object thread_lock = new Object();
	private final Gfo_poolable_itm prototype; private final Object[] make_args, clear_args; 
	private Gfo_poolable_itm[] pool; private int pool_nxt, pool_len;
	public Gfo_poolable_mgr(Gfo_poolable_itm prototype, Object[] make_args, Object[] clear_args, int init_pool_len, int pool_max) {// NOTE: random IndexOutOfBounds errors in Get around free_ary[--free_len] with free_len being -1; put member variable initialization within thread_lock to try to avoid; DATE:2014-09-21
		this.prototype = prototype; this.make_args = make_args; this.clear_args = clear_args;
		this.pool_len = init_pool_len;
		this.Clear_fast();
	}
	public void Clear_safe() {synchronized (thread_lock) {Clear_fast();}}
	public void Clear_fast() {
		this.pool = new Gfo_poolable_itm[pool_len];
		for (int i = 0; i < pool_len; ++i)
			pool[i] = prototype.Pool__make(this, i, make_args);
		this.free_ary = new int[pool_len];
		pool_nxt = free_len = 0;
	}
	public Gfo_poolable_itm Get_safe() {synchronized (thread_lock) {return Get_fast();}}
	public Gfo_poolable_itm Get_fast() {
		Gfo_poolable_itm rv = null;
		int pool_idx = -1;
		if (free_len > 0) {	// free_itms in pool; use it
			pool_idx = free_ary[--free_len];
			rv = pool[pool_idx];
		}
		else {				// nothing in pool; take next
			if (pool_nxt == pool_len)
				Expand_pool();
			pool_idx = pool_nxt++;
			rv = pool[pool_idx];
			if (rv == null) {
				rv = prototype.Pool__make(this, pool_idx, make_args);
				pool[pool_idx] = rv;
			}
		}
		rv.Pool__clear(clear_args); // NOTE: ALWAYS call Clear when doing Get. caller may forget to call Clear, and reused bfr may have leftover bytes. unit tests will not catch, and difficult to spot in app
		return rv;
	}
	public void Rls_safe(int idx) {synchronized (thread_lock) {Rls_safe(idx);}}
	public void Rls_fast(int idx) {
		if (idx == -1) throw Err_.new_wo_type("rls called on poolable that was not created by pool_mgr");
		int pool_idx = pool_nxt - 1;
		if (idx == pool_idx)				// in-sequence; decrement count
			if (free_len == 0)
				this.pool_nxt = pool_idx;
			else {							// idx == pool_idx; assume entire pool released, but out of order
				for (int i = 0; i < free_len; ++i)
					free_ary[i] = 0;
				free_len = 0;
			}
		else {								// out-of-sequence
			free_ary[free_len] = idx;
			++free_len;
		}
	}
	private void Expand_pool() {
		// expand pool
		int new_pool_len = pool_len == 0 ? 2 : pool_len * 2;
		Gfo_poolable_itm[] new_pool = new Gfo_poolable_itm[new_pool_len];
		Array_.Copy_to(pool, 0, new_pool, 0, pool_len);
		this.pool = new_pool;
		this.pool_len = new_pool_len;

		// expand free_ary to same len
		int[] new_free = new int[pool_len];
		Array_.Copy_to(free_ary, 0, new_free, 0, free_len);
		this.free_ary = new_free;
	}
	@gplx.Internal protected int[] Free_ary() {return free_ary;} private int[] free_ary; private int free_len;
	@gplx.Internal protected int Free_len() {return free_len;}
	@gplx.Internal protected int Pool_len() {return pool_len;}
	@gplx.Internal protected int Pool_nxt() {return pool_nxt;}
}

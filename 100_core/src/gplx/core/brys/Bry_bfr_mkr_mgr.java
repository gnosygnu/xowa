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
package gplx.core.brys; import gplx.*; import gplx.core.*;
public class Bry_bfr_mkr_mgr {
	private final    Object thread_lock = new Object();
	private final    byte mgr_id; private final    int reset;
	private Bry_bfr[] used = Bry_bfr_.Ary_empty; private int used_len = 0, used_max = 0;
	private int[] free; private int free_len;
	public Bry_bfr_mkr_mgr(byte mgr_id, int reset) {// NOTE: random IndexOutOfBounds errors in Get around free[--free_len] with free_len being -1; put member variable initialization within thread_lock to try to avoid; DATE:2014-09-21
		this.mgr_id = mgr_id;
		this.reset = reset;
		this.free = Int_.Ary_empty;
		this.free_len = 0;
	}
	public Bry_bfr Get() {
		synchronized (thread_lock) {
			Bry_bfr rv = null; int rv_idx = -1;
			if (free_len > 0) {
				try {rv_idx = free[--free_len];}	catch (Exception e) {throw Err_.new_exc(e, "core", "failed to get free", "idx", free_len, "free_len", free.length);}
				try {rv = used[rv_idx];}			catch (Exception e) {throw Err_.new_exc(e, "core", "failed to get used", "idx", rv_idx, "used_len", used.length);}
			}
			else {
				if (used_len == used_max) Expand();
				rv_idx = used_len++;
				rv = used[rv_idx];
				if (rv == null) {
					rv = Bry_bfr_.Reset(reset);
					used[rv_idx] = rv;
				}
			}
			rv.Mkr_init(this, rv_idx);
			return rv.Clear();	// NOTE: ALWAYS call Clear when doing Get. caller may forget to call Clear, and reused bfr may have leftover bytes. unit tests will not catch, and difficult to spot in app
		}
	}
	public void Rls(int idx) {
		synchronized (thread_lock) {
			if (idx == -1) throw Err_.new_wo_type("rls called on bfr that was not created by factory");
			int new_used_len = used_len - 1;
			if (idx == new_used_len)
				used_len = new_used_len;
			else
				free[free_len++] = idx;
		}
	}
	public void Clear_fail_check() {
		synchronized (thread_lock) {
			for (int i = 0; i < used_max; i++) {
				Bry_bfr itm = used[i];
				if (itm != null) {
					if (!itm.Mkr_idx_is_null()) throw Err_.new_wo_type("failed to clear bfr", "mgr_id", mgr_id, "idx", Int_.To_str(i));
					itm.Clear();
				}
				used[i] = null;
			}
			used = Bry_bfr_.Ary_empty;
			free = Int_.Ary_empty;
			free_len = used_len = used_max = 0;
		}
	}
	public void Clear() {
		synchronized (thread_lock) {
			for (int i = 0; i < used_max; i++) {
				Bry_bfr itm = used[i];
				if (itm != null) itm.Clear();
				used[i] = null;
			}
			used = Bry_bfr_.Ary_empty;
			free = Int_.Ary_empty;
			free_len = 0;
			used_len = used_max = 0;
		}
	}
	@gplx.Internal protected Bry_bfr[] Used() {return used;}
	@gplx.Internal protected int Used_len() {return used_len;}
	private void Expand() {
		int new_max = used_max == 0 ? 2 : used_max * 2;
		Bry_bfr[] new_ary = new Bry_bfr[new_max];
		Array_.Copy_to(used, 0, new_ary, 0, used_max);
		used = new_ary;
		used_max = new_max;
		int[] new_free = new int[used_max];
		Array_.Copy_to(free, 0, new_free, 0, free_len);
		free = new_free;
	}
}

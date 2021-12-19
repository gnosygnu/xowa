/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.custom.brys.wtrs;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.arrays.IntAryUtl;
import gplx.types.basics.utls.IntUtl;
public class BryBfrMkrMgr {
	private final Object thread_lock = new Object();
	private final byte mgrId; private final int reset;
	private BryWtr[] used = BryWtr.AryEmpty; private int used_len = 0, used_max = 0;
	private int[] free; private int free_len;
	public BryBfrMkrMgr(byte mgrId, int reset) {// NOTE: random IndexOutOfBounds errors in Get around free[--free_len] with free_len being -1; put member variable initialization within thread_lock to try to avoid; DATE:2014-09-21
		this.mgrId = mgrId;
		this.reset = reset;
		this.free = IntAryUtl.Empty;
		this.free_len = 0;
	}
	public BryWtr Get() {
		synchronized (thread_lock) {
			BryWtr rv = null; int rv_idx = -1;
			if (free_len > 0) {
				try {rv_idx = free[--free_len];}    catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to get free", "idx", free_len, "free_len", free.length);}
				try {rv = used[rv_idx];}            catch (Exception e) {throw ErrUtl.NewArgs(e, "failed to get used", "idx", rv_idx, "used_len", used.length);}
			}
			else {
				if (used_len == used_max) Expand();
				rv_idx = used_len++;
				rv = used[rv_idx];
				if (rv == null) {
					rv = BryWtr.NewAndReset(reset);
					used[rv_idx] = rv;
				}
			}
			rv.MkrInit(this, rv_idx);
			return rv.Clear();    // NOTE: ALWAYS call Clear when doing Get. caller may forget to call Clear, and reused bfr may have leftover bytes. unit tests will not catch, and difficult to spot in app
		}
	}
	public void Rls(int idx) {
		synchronized (thread_lock) {
			if (idx == -1) throw ErrUtl.NewArgs("rls called on bfr that was not created by factory");
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
				BryWtr itm = used[i];
				if (itm != null) {
					if (!itm.MkrIdxIsNull()) throw ErrUtl.NewArgs("failed to clear bfr", "mgr_id", mgrId, "idx", IntUtl.ToStr(i));
					itm.Clear();
				}
				used[i] = null;
			}
			used = BryWtr.AryEmpty;
			free = IntAryUtl.Empty;
			free_len = used_len = used_max = 0;
		}
	}
	public void Clear() {
		synchronized (thread_lock) {
			for (int i = 0; i < used_max; i++) {
				BryWtr itm = used[i];
				if (itm != null) itm.Clear();
				used[i] = null;
			}
			used = BryWtr.AryEmpty;
			free = IntAryUtl.Empty;
			free_len = 0;
			used_len = used_max = 0;
		}
	}
	public BryWtr[] Used() {return used;}
	public int UsedLen() {return used_len;}
	private void Expand() {
		int new_max = used_max == 0 ? 2 : used_max * 2;
		BryWtr[] new_ary = new BryWtr[new_max];
		ArrayUtl.CopyTo(used, 0, new_ary, 0, used_max);
		used = new_ary;
		used_max = new_max;
		int[] new_free = new int[used_max];
		ArrayUtl.CopyTo(free, 0, new_free, 0, free_len);
		free = new_free;
	}
}

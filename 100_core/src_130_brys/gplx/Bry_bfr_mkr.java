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
package gplx;
public class Bry_bfr_mkr {
	public static final byte Tid_b128 = 0, Tid_b512 = 1, Tid_k004 = 2, Tid_m001 = 3;
	private Bry_bfr_mkr_mgr mkr_b128 = new Bry_bfr_mkr_mgr(Tid_b128, 128), mkr_b512 = new Bry_bfr_mkr_mgr(Tid_b512, 512), mkr_k004 = new Bry_bfr_mkr_mgr(Tid_k004, 4 * Io_mgr.Len_kb), mkr_m001 = new Bry_bfr_mkr_mgr(Tid_m001, 1 * Io_mgr.Len_mb);
	public Bry_bfr Get_b128() {return mkr_b128.Get();}
	public Bry_bfr Get_b512() {return mkr_b512.Get();}
	public Bry_bfr Get_k004() {return mkr_k004.Get();}
	public Bry_bfr Get_m001() {return mkr_m001.Get();}
	public void Rls(Bry_bfr v) {
		v.Mkr_rls();
//			v.Mkr_mgr().Rls(v);
	}
	public void Reset_if_gt(int v) {
		for (byte i = Tid_b128; i <= Tid_m001; i++)
			mkr(i).Reset_if_gt(v);
	} 
	public void Clear_fail_check() {
		for (byte i = Tid_b128; i <= Tid_m001; i++)
			mkr(i).Clear_fail_check();
	}

	public void Clear() {
		for (byte i = Tid_b128; i <= Tid_m001; i++)
			mkr(i).Clear();
	}
	Bry_bfr_mkr_mgr mkr(byte tid) {
		switch (tid) {
			case Tid_b128: 	return mkr_b128;
			case Tid_b512: 	return mkr_b512;
			case Tid_k004: 	return mkr_k004;
			case Tid_m001: 	return mkr_m001;
			default:		throw Err_.new_unhandled(tid);
		}
	}
}
class Bry_bfr_mkr_mgr {
	private final Object thread_lock;
	public Bry_bfr_mkr_mgr(byte mgr_id, int reset) {// NOTE: random IndexOutOfBounds errors in Get around free[--free_len] with free_len being -1; put member variable initialization within thread_lock to try to avoid; DATE:2014-09-21
		thread_lock = new Object();
		synchronized (thread_lock) {
			this.mgr_id = mgr_id;
			this.reset = reset;
			this.free = Int_.Ary_empty;
			this.free_len = 0;
		}
	}	private int reset;
	public byte Mgr_id() {return mgr_id;} private byte mgr_id; 
	private Bry_bfr[] ary = Ary_empty; private int nxt_idx = 0, ary_max = 0;
	private int[] free; private int free_len;
	public void Reset_if_gt(int v) {
		this.Clear();	// TODO: for now, just call clear
	}
	public void Clear_fail_check() {
		synchronized (thread_lock) {
			for (int i = 0; i < ary_max; i++) {
				Bry_bfr itm = ary[i];
				if (itm != null) {
					if (!itm.Mkr_idx_is_null()) throw Err_.new_wo_type("failed to clear bfr", "idx", Int_.Xto_str(i));
					itm.Clear();
				}
				ary[i] = null;
			}
			ary = Ary_empty;
			free = Int_.Ary_empty;
			free_len = 0;
			nxt_idx = ary_max = 0;
		}
	}
	public void Clear() {
		synchronized (thread_lock) {
			for (int i = 0; i < ary_max; i++) {
				Bry_bfr itm = ary[i];
				if (itm != null) itm.Clear();
				ary[i] = null;
			}
			ary = Ary_empty;
			free = Int_.Ary_empty;
			free_len = 0;
			nxt_idx = ary_max = 0;
		}
	}
	public Bry_bfr[] Ary() {return ary;}
	public int Nxt_idx() {return nxt_idx;}
	public Bry_bfr Get() {
		synchronized (thread_lock) {
			Bry_bfr rv = null;
			int rv_idx = -1;
			if (free_len > 0) {
				try {rv_idx = free[--free_len];}
				catch (Exception e) {throw Err_.new_exc(e, "core", "failed to get free index", "free_len", free_len, "free.length", free.length);}
				try {rv = ary[rv_idx];}
				catch (Exception e) {throw Err_.new_exc(e, "core", "failed to get bfr", "rv_idx", rv_idx, "ary.length", ary.length);}
			}
			else {
				if (nxt_idx == ary_max)
					Expand();
				rv_idx = nxt_idx++;
				rv = ary[rv_idx];
				if (rv == null) {
					rv = Bry_bfr.reset_(reset);
					ary[rv_idx] = rv;
				}
			}
			rv.Mkr_init(this, rv_idx);
			return rv.Clear();	// NOTE: ALWAYS call Clear when doing Get. caller may forget to call Clear, and reused bfr may have leftover bytes. unit tests will not catch, and difficult to spot in app
		}
	}
	private void Expand() {
		int new_max = ary_max == 0 ? 2 : ary_max * 2;
		Bry_bfr[] new_ary = new Bry_bfr[new_max];
		Array_.Copy_to(ary, 0, new_ary, 0, ary_max);
		ary = new_ary;
		ary_max = new_max;
		int[] new_free = new int[ary_max];
		Array_.Copy_to(free, 0, new_free, 0, free_len);
		free = new_free;
	}
//		public void Rls(Bry_bfr v) {
//			synchronized (thread_lock) {
//				int idx = v.Mkr_itm();
//				if (idx == -1) throw Err_mgr._.fmt_("gplx.Bry_bfr", "rls_failed", "rls called on bfr that was not created by factory");
//				int new_ary_len = nxt_idx - 1;
//				if (idx == new_ary_len)
//					nxt_idx = new_ary_len;
//				else
//					free[free_len++] = idx;
//				v.Mkr_(null, -1);
//			}
//		}
	public void Rls(int idx) {
		synchronized (thread_lock) {
			if (idx == -1) throw Err_.new_wo_type("rls called on bfr that was not created by factory");
			int new_ary_len = nxt_idx - 1;
			if (idx == new_ary_len)
				nxt_idx = new_ary_len;
			else
				free[free_len++] = idx;
		}
	}
	public static final Bry_bfr[] Ary_empty = new Bry_bfr[0];
}

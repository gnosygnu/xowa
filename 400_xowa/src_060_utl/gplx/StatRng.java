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
class StatRng_fxt {
	StatRng rng;
	public StatRng_fxt ini_(int lo_ary_len, int hi_ary_len, int... slot_hi_ary) {
		rng = new StatRng(lo_ary_len, hi_ary_len, slot_hi_ary);
		return this;
	}
	public StatRng_fxt tst_(int... vals) {
		for (int i = 0; i < vals.length; i++) {
			int val = vals[i];
			rng.Assign(val, val);
		}
		Tfds.Eq(expd_count, rng.Count, "Count");
		Tfds.Eq(expd_lo, rng.Lo, "Lo");
		Tfds.Eq(expd_hi, rng.Hi, "Hi");
		Tfds.Eq_float(expd_avg, rng.Avg());
		Tfds.Eq_ary(expd_lo_ary, XtoIntAry(rng.Lo_ary), "Lo_ary");
		Tfds.Eq_ary(expd_hi_ary, XtoIntAry(rng.Hi_ary), "Hi_ary");
		Tfds.Eq_ary(expd_slots, XtoIntAry(rng.Slot_ary), "Slots");
		return this;
	}
	int[] XtoIntAry(StatItm[] ary) {
		int[] rv = new int[ary.length];
		for (int i = 0; i < rv.length; i++)
			rv[i] = ary[i].Val;
		return rv;
	}
	int[] XtoIntAry(StatRng[] ary) {
		int[] rv = new int[ary.length];
		for (int i = 0; i < rv.length; i++)
			rv[i] = ary[i].Count;
		return rv;
	}
	public StatRng_fxt Count_(int v) {expd_count = v; return this;} private int expd_count; 
	public StatRng_fxt Lo_(int v) {expd_lo = v; return this;} private int expd_lo; 
	public StatRng_fxt Hi_(int v) {expd_hi = v; return this;} private int expd_hi; 
	public StatRng_fxt Avg_(float v) {expd_avg = v; return this;} float expd_avg;
	public StatRng_fxt Lo_ary_(int... v) {expd_lo_ary = v; return this;} private int[] expd_lo_ary; 
	public StatRng_fxt Hi_ary_(int... v) {expd_hi_ary = v; return this;} private int[] expd_hi_ary; 
	public StatRng_fxt Slots_(int... v) {expd_slots = v; return this;} private int[] expd_slots; 
}
class StatRng {
//		public String Key;
	public int Lo = Int_.MaxValue;
	public int Hi = Int_.MinValue;
	public long Sum = 0;
	public int Count = 0;
	public float Avg() {return Sum  / Count;}
	public final StatItm[] Lo_ary;
	public int Lo_ary_bound;
	public int Lo_ary_len;
	public final StatItm[] Hi_ary;
	public int Hi_ary_bound;
	public int Hi_ary_len;
	public StatRng[] Slot_ary;
	public int Slot_ary_len;
	public StatRng(int lo_ary_len, int hi_ary_len, int... slot_hi_ary) {
		this.Lo_ary_len = lo_ary_len;
		this.Lo_ary_bound = Int_.MaxValue;
		this.Lo_ary = NewBoundAry(lo_ary_len, Int_.MaxValue);		
		this.Hi_ary_len = hi_ary_len;
		this.Hi_ary_bound = Int_.MinValue;
		this.Hi_ary = NewBoundAry(hi_ary_len, Int_.MinValue);
		if (slot_hi_ary != null && slot_hi_ary.length > 0) {
			Slot_ary_len = slot_hi_ary.length + 1; // + 1 to hold max value
			Slot_ary = new StatRng[Slot_ary_len];
			int slot_lo = Int_.MinValue;
			for (int i = 0; i < Slot_ary_len - 1; i++) {
				int slot_hi = slot_hi_ary[i];
				Slot_ary[i] = NewSlot(slot_lo, slot_hi);
				slot_lo = slot_hi;
			}
			Slot_ary[Slot_ary_len - 1] = NewSlot(slot_lo, Int_.MaxValue);
		}
	}
	public void Assign(Object key, int val) {
		if (val < Lo) Lo = val;
		if (val > Hi) Hi = val;
		Sum += val;
		++Count;
		
		if (Slot_ary_len > 0) {
			for (int i = 0; i < Slot_ary_len; i++) {
				StatRng slot = Slot_ary[i];
				if (val >= slot.Lo && val < slot.Hi)
					slot.Assign(key, val);
			}
		}
		if (val < Lo_ary_bound) {
			Lo_ary_bound = CalcCutoff(Lo_ary, CompareAble_.More, Int_.MinValue, key, val);
		} 
		if (val > Hi_ary_bound) {
			Hi_ary_bound = CalcCutoff(Hi_ary, CompareAble_.Less, Int_.MaxValue, key, val);
		} 
	}
	int CalcCutoff(StatItm[] ary, int comp, int bgn_bound, Object key, int val) {
		int new_bound = bgn_bound;
		for (int i = 0; i < ary.length; i++) {
			StatItm itm = ary[i];
			if (Int_.Compare(itm.Val, val) == comp) {
				itm = new StatItm(key, val);
				ary[i] = itm;
			}
			if (Int_.Compare(itm.Val, new_bound) == comp) new_bound = itm.Val;
		}
		return new_bound;
	}
	StatRng NewSlot(int lo, int hi) {
		StatRng rv = new StatRng(0, 0);
		rv.Lo = lo;
		rv.Hi = hi;
		return rv;
	}
	StatItm[] NewBoundAry(int len, int dflt) {
		StatItm[] rv = new StatItm[len];
		for (int i = 0; i < len; i++)
			rv[i] = new StatItm(null, dflt);
		return rv;
	}
}
class StatItm {
	public Object Key;
	public int Val;
	public StatItm(Object key, int val) {this.Key = key; this.Val = val;}
}

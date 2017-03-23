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
package gplx.core.lists; import gplx.*; import gplx.core.*;
class StatRng_fxt {	// UNUSED:useful for stat processing
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
	public int Lo = Int_.Max_value;
	public int Hi = Int_.Min_value;
	public long Sum = 0;
	public int Count = 0;
	public float Avg() {return Sum  / Count;}
	public final    StatItm[] Lo_ary;
	public int Lo_ary_bound;
	public int Lo_ary_len;
	public final    StatItm[] Hi_ary;
	public int Hi_ary_bound;
	public int Hi_ary_len;
	public StatRng[] Slot_ary;
	public int Slot_ary_len;
	public StatRng(int lo_ary_len, int hi_ary_len, int... slot_hi_ary) {
		this.Lo_ary_len = lo_ary_len;
		this.Lo_ary_bound = Int_.Max_value;
		this.Lo_ary = NewBoundAry(lo_ary_len, Int_.Max_value);		
		this.Hi_ary_len = hi_ary_len;
		this.Hi_ary_bound = Int_.Min_value;
		this.Hi_ary = NewBoundAry(hi_ary_len, Int_.Min_value);
		if (slot_hi_ary != null && slot_hi_ary.length > 0) {
			Slot_ary_len = slot_hi_ary.length + 1; // + 1 to hold max value
			Slot_ary = new StatRng[Slot_ary_len];
			int slot_lo = Int_.Min_value;
			for (int i = 0; i < Slot_ary_len - 1; i++) {
				int slot_hi = slot_hi_ary[i];
				Slot_ary[i] = NewSlot(slot_lo, slot_hi);
				slot_lo = slot_hi;
			}
			Slot_ary[Slot_ary_len - 1] = NewSlot(slot_lo, Int_.Max_value);
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
			Lo_ary_bound = CalcCutoff(Lo_ary, CompareAble_.More, Int_.Min_value, key, val);
		} 
		if (val > Hi_ary_bound) {
			Hi_ary_bound = CalcCutoff(Hi_ary, CompareAble_.Less, Int_.Max_value, key, val);
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

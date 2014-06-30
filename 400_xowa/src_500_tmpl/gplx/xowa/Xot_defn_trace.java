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
package gplx.xowa; import gplx.*;
public interface Xot_defn_trace {
	void Clear();
	void Trace_bgn(Xop_ctx ctx, byte[] src, byte[] name, Xot_invk caller, Xot_invk self, Xot_defn defn);
	void Trace_end(int trg_bgn, Bry_bfr trg);
	void Print(byte[] src, Bry_bfr bb);
}
class Xot_defn_trace_null implements Xot_defn_trace {
	public void Clear() {}
	public void Trace_bgn(Xop_ctx ctx, byte[] src, byte[] name, Xot_invk caller, Xot_invk self, Xot_defn defn) {}
	public void Trace_end(int trg_bgn, Bry_bfr trg) {}
	public void Print(byte[] src, Bry_bfr bb) {}
	public static final Xot_defn_trace_null _ = new Xot_defn_trace_null(); Xot_defn_trace_null() {}
}
class Xot_defn_trace_brief implements Xot_defn_trace {
	public int Count() {return hash.Count();}
	public Xot_defn_trace_itm_brief GetAt(int i) {return (Xot_defn_trace_itm_brief)hash.FetchAt(i);}
	public void Trace_bgn(Xop_ctx ctx, byte[] src, byte[] name, Xot_invk caller, Xot_invk self, Xot_defn defn) {
		int hashKey = Bry_obj_ref.CalcHashCode(name, 0, name.length);
		Object o = hash.Fetch(hashKey);
		Xot_defn_trace_itm_brief itm = null;
		if (o == null) {
			itm = new Xot_defn_trace_itm_brief().Name_(name);
			hash.Add(hashKey, itm);
		}
		else
			itm = (Xot_defn_trace_itm_brief)o;
		itm.Count_add();
	}	private OrderedHash hash = OrderedHash_.new_();
	public void Trace_end(int trg_bgn, Bry_bfr trg) {}
	public void Print(byte[] src, Bry_bfr bb) {
		int count = hash.Count(); if (count == 0) return;
		if (bb.Len() != 0) bb.Add_byte_nl();	// only add newLine if something in bb; needed for tests
		for (int i = 0; i < count; i++) {
			Xot_defn_trace_itm_brief itm = (Xot_defn_trace_itm_brief)hash.FetchAt(i);
			bb.Add_int_fixed(itm.Count(), 4).Add_byte(Byte_ascii.Space);
			bb.Add(itm.Name()).Add_byte_nl();
		}
	}
	public void Clear() {hash.Clear();}
}
class Xot_defn_trace_itm_brief {// name,count,depth,args,
	public byte[] Name() {return name;} public Xot_defn_trace_itm_brief Name_(byte[] v) {name = v; return this;} private byte[] name = Bry_.Empty;
	public int Count() {return count;} public Xot_defn_trace_itm_brief Count_(int v) {count = v; return this;} public void Count_add() {++count;} private int count;
}

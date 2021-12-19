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
package gplx.xowa.parsers.tmpls;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.custom.brys.wtrs.BryRef;
import gplx.xowa.parsers.*;
public interface Xot_defn_trace {
	void Clear();
	void Trace_bgn(Xop_ctx ctx, byte[] src, byte[] name, Xot_invk caller, Xot_invk self, Xot_defn defn);
	void Trace_end(int trg_bgn, BryWtr trg);
	void Print(byte[] src, BryWtr bb);
}
class Xot_defn_trace_brief implements Xot_defn_trace {
	public int Count() {return hash.Len();}
	public Xot_defn_trace_itm_brief GetAt(int i) {return (Xot_defn_trace_itm_brief)hash.GetAt(i);}
	public void Trace_bgn(Xop_ctx ctx, byte[] src, byte[] name, Xot_invk caller, Xot_invk self, Xot_defn defn) {
		int hashKey = BryRef.CalcHashCode(name, 0, name.length);
		Object o = hash.GetByOrNull(hashKey);
		Xot_defn_trace_itm_brief itm = null;
		if (o == null) {
			itm = new Xot_defn_trace_itm_brief().Name_(name);
			hash.Add(hashKey, itm);
		}
		else
			itm = (Xot_defn_trace_itm_brief)o;
		itm.Count_add();
	}	private Ordered_hash hash = Ordered_hash_.New();
	public void Trace_end(int trg_bgn, BryWtr trg) {}
	public void Print(byte[] src, BryWtr bb) {
		int count = hash.Len(); if (count == 0) return;
		if (bb.Len() != 0) bb.AddByteNl();	// only add newLine if something in bb; needed for tests
		for (int i = 0; i < count; i++) {
			Xot_defn_trace_itm_brief itm = (Xot_defn_trace_itm_brief)hash.GetAt(i);
			bb.AddIntFixed(itm.Count(), 4).AddByte(AsciiByte.Space);
			bb.Add(itm.Name()).AddByteNl();
		}
	}
	public void Clear() {hash.Clear();}
}
class Xot_defn_trace_itm_brief {// name,count,depth,args,
	public byte[] Name() {return name;} public Xot_defn_trace_itm_brief Name_(byte[] v) {name = v; return this;} private byte[] name = BryUtl.Empty;
	public int Count() {return count;} public Xot_defn_trace_itm_brief Count_(int v) {count = v; return this;} public void Count_add() {++count;} private int count;
}

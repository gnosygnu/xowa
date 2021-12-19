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
package gplx.xowa.addons.wikis.searchs.searchers;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.arrays.IntAryUtl;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.wrappers.IntRef;
import gplx.xowa.addons.wikis.searchs.*;
import gplx.xowa.wikis.nss.*;
public class Srch_ns_mgr {
	private final Ordered_hash ns_hash = Ordered_hash_.New(); private final IntRef tmp_ns_id = IntRef.NewNeg1();
	private final BryWtr tmp_bfr = BryWtr.NewAndReset(32);
	private boolean ns_all, ns_main;
	public void Clear() {
		ns_hash.Clear();
		ns_all = ns_main = false;
	}
	public boolean Ns_main_only() {return ns_main && !ns_all;}	// Ns_main_only is used by Searcher to only search main srch_link db
	public boolean Has(int ns_id) {
		return ns_all									// ns_all always returns true
			|| ns_main && ns_id == Xow_ns_.Tid__main	// ns_main returns true if main_ns
			|| ns_hash.Has(tmp_ns_id.ValSet(ns_id));		// ns_hash returns true if has ns_id
	}
	public void Add_all()					{ns_all = true;}
	public Srch_ns_mgr Add_main_if_empty()	{if (ns_hash.Len() == 0) ns_main = true; return this;}
	public void Add_by_id(int ns_id)	{
		if (ns_hash.Has(tmp_ns_id.ValSet(ns_id))) ns_hash.Del(tmp_ns_id);
		ns_hash.AddAsKeyAndVal(IntRef.New(ns_id));
	}
	public void Add_by_name(byte[] ns_name) {
		int id = Xow_ns_canonical_.To_id(ns_name);
		if (id != Xow_ns_.Tid__null)
			Add_by_id(id);
	}
	public void Add_by_parse(byte[] key, byte[] val) {
		int ns_enabled = BryUtl.ToIntOrNeg1(val);
		if (ns_enabled == 1) {										// make sure set to 1; EX: ignore &ns0=0
			int key_len = key.length;
			if (key_len == 3 && key[2] == Srch_search_addon.Wildcard__star)	// key=ns* sets ns_all to true
				ns_all = true;
			else {
				int ns_id = BryUtl.ToIntOr(key, 2, key_len, IntUtl.MinValue);
				if (ns_id != IntUtl.MinValue) {						// ignore invalid ints; EX: &nsabc=1;
					Add_by_id(ns_id);
					ns_main = ns_all = false;
				}
			}
		}
	}
	public byte[] To_hash_key() {
		if		(ns_all)	return Hash_key_all;
		else if (ns_main)	return Hash_key_main;
		else {
			int ns_hash_len = ns_hash.Len();
			for (int i = 0; i < ns_hash_len; i++) {
				if (i != 0) tmp_bfr.AddByteSemic();
				IntRef ns_id_ref = (IntRef)ns_hash.GetAt(i);
				tmp_bfr.AddIntVariable(ns_id_ref.Val());
			}
			return tmp_bfr.ToBryAndClear();
		}
	}
	public void Add_by_int_ids(int[] ns_ids) {
		this.Clear();
		if (ns_ids.length == 0) {
			this.Add_all();
		} else if (ns_ids.length == 1 && ns_ids[0] == Xow_ns_.Tid__main) {
			this.Add_main_if_empty();
		} else {
			for (int ns_id : ns_ids)
				this.Add_by_id(ns_id);
		}
	}
	public int[] To_int_ary() {
		if		(ns_all)	return IntAryUtl.Empty;
		else if (ns_main)	return IntAryUtl.New(Xow_ns_.Tid__main);
		else {
			int len = ns_hash.Len();
			int[] rv = new int[len];
			for (int i = 0; i < len; i++) {
				IntRef ns_id_ref = (IntRef)ns_hash.GetAt(i);
				rv[i] = ns_id_ref.Val();
			}
			return rv;
		}
	}
	private static final byte[] Hash_key_all = new byte[] {Srch_search_addon.Wildcard__star}, Hash_key_main = new byte[] {AsciiByte.Num0};
}

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
package gplx.xowa.addons.searchs.searchers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.searchs.*;
import gplx.core.primitives.*;
import gplx.xowa.wikis.nss.*;
public class Srch_ns_mgr {
	private final    Ordered_hash ns_hash = Ordered_hash_.New(); private final    Int_obj_ref tmp_ns_id = Int_obj_ref.neg1_();
	private final    Bry_bfr tmp_bfr = Bry_bfr.reset_(32);
	private boolean ns_all, ns_main;
	public void Clear() {
		ns_hash.Clear();
		ns_all = ns_main = false;
	}
	public boolean Ns_main_only() {return ns_main;}
	public boolean Has(int ns_id) {
		return ns_all									// ns_all always returns true
			|| ns_main && ns_id == Xow_ns_.Tid__main	// ns_main returns true if main_ns
			|| ns_hash.Has(tmp_ns_id.Val_(ns_id));		// ns_hash returns true if has ns_id
	}
	public void Add_all()					{ns_all = true;}
	public Srch_ns_mgr Add_main_if_empty()	{if (ns_hash.Count() == 0) ns_main = true; return this;}
	public void Add_by_id(int ns_id)	{
		if (ns_hash.Has(tmp_ns_id.Val_(ns_id))) ns_hash.Del(tmp_ns_id);
		ns_hash.Add_as_key_and_val(Int_obj_ref.new_(ns_id));
	}
	public void Add_by_name(byte[] ns_name) {
		int id = Xow_ns_canonical_.To_id(ns_name);
		if (id != Xow_ns_.Tid__null)
			Add_by_id(id);
	}
	public void Add_by_parse(byte[] key, byte[] val) {
		int ns_enabled = Bry_.To_int_or_neg1(val);
		if (ns_enabled == 1) {										// make sure set to 1; EX: ignore &ns0=0
			int key_len = key.length;
			if (key_len == 3 && key[2] == Srch_search_addon.Wildcard__star)	// key=ns* sets ns_all to true
				ns_all = true;
			else {
				int ns_id = Bry_.To_int_or(key, 2, key_len, Int_.Min_value);
				if (ns_id != Int_.Min_value) {						// ignore invalid ints; EX: &nsabc=1;
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
			int ns_hash_len = ns_hash.Count();
			for (int i = 0; i < ns_hash_len; i++) {
				if (i != 0) tmp_bfr.Add_byte_semic();
				Int_obj_ref ns_id_ref = (Int_obj_ref)ns_hash.Get_at(i);
				tmp_bfr.Add_int_variable(ns_id_ref.Val());
			}
			return tmp_bfr.To_bry_and_clear();
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
		if		(ns_all)	return Int_.Ary_empty;
		else if (ns_main)	return Int_.Ary(Xow_ns_.Tid__main);
		else {
			int len = ns_hash.Count();
			int[] rv = new int[len];
			for (int i = 0; i < len; i++) {
				Int_obj_ref ns_id_ref = (Int_obj_ref)ns_hash.Get_at(i);
				rv[i] = ns_id_ref.Val();
			}
			return rv;
		}
	}
	private static final    byte[] Hash_key_all = new byte[] {Srch_search_addon.Wildcard__star}, Hash_key_main = new byte[] {Byte_ascii.Num_0};
}

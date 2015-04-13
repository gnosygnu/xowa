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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.core.primitives.*;
public class Xows_ns_mgr {
	private final OrderedHash ns_hash = OrderedHash_.new_(); private final Int_obj_ref tmp_ns_id = Int_obj_ref.neg1_(); private final Bry_bfr tmp_bfr = Bry_bfr.reset_(32);
	private boolean ns_all, ns_main;
	public void Clear() {
		ns_hash.Clear();
		ns_all = ns_main = false;
	}
	public boolean Has(int ns_id) {
		return ns_all								// all flag set
			|| ns_main && ns_id == Xow_ns_.Id_main	// main flag set
			|| ns_hash.Has(tmp_ns_id.Val_(ns_id))	// check against ns_hash
			;
	}
	public void Add_by_id(int ns_id) {
		if (ns_hash.Has(tmp_ns_id.Val_(ns_id)))
			ns_hash.Del(tmp_ns_id);
		ns_hash.AddKeyVal(Int_obj_ref.new_(ns_id));
	}
	public void Add_by_name(byte[] ns_name) {
		int id = Xow_ns_.Canonical_id(ns_name);
		if (id != Xow_ns_.Id_null)
			Add_by_id(id);
	}
	public void Add_all() {
		ns_all = true;
	}
	public void Add_by_parse(byte[] key, byte[] val) {
		int ns_enabled = Bry_.Xto_int(val);
		if (ns_enabled == 1) {										// make sure set to 1; EX: ignore &ns0=0
			int key_len = key.length;
			if (key_len == 3 && key[2] == Byte_ascii.Asterisk)		// translate ns* as ns_all
				ns_all = true;
			else {
				int ns_id = Bry_.Xto_int_or(key, 2, key_len, -1);
				if (ns_id != -1) {									// ignore invalid ints; EX: &nsabc=1;
					Add_by_id(ns_id);
					ns_main = ns_all = false;
				}
			}
		}
	}
	public void Add_main_if_empty() {
		if (ns_hash.Count() == 0)
			ns_main = true;
	}
	public byte[] To_hash_key() {
		if		(ns_all) 
			return Hash_key_all;
		else if (ns_main)
			return Hash_key_main;
		else {
			int ns_hash_len = ns_hash.Count();
			for (int i = 0; i < ns_hash_len; i++) {
				if (i != 0) tmp_bfr.Add_byte_semic();
				Int_obj_ref ns_id_ref = (Int_obj_ref)ns_hash.FetchAt(i);
				tmp_bfr.Add_int_variable(ns_id_ref.Val());
			}
			return tmp_bfr.Xto_bry_and_clear();
		}
	}
	private static final byte[] Hash_key_all = new byte[] {Byte_ascii.Asterisk}, Hash_key_main = new byte[] {Byte_ascii.Num_0};
}

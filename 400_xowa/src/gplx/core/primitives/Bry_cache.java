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
package gplx.core.primitives; import gplx.*; import gplx.core.*;
import gplx.core.brys.*;
public class Bry_cache {
	private final Hash_adp hash = Hash_adp_.new_(); private final Bry_obj_ref hash_ref = Bry_obj_ref.New_empty();
	public byte[] Get_or_new(String v) {return Get_or_new(Bry_.new_u8(v));}
	public byte[] Get_or_new(byte[] v) {
		if (v.length == 0) return Bry_.Empty;
		Object rv = hash.Get_by(hash_ref.Val_(v));
		if (rv == null) {
			Bry_obj_ref bry = Bry_obj_ref.New(v);
			hash.Add_as_key_and_val(bry);
			return v;
		}
		else
			return ((Bry_obj_ref)rv).Val();
	}
	public static final Bry_cache Instance = new Bry_cache(); Bry_cache() {}
}

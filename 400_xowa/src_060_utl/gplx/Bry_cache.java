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
import gplx.core.primitives.*;
public class Bry_cache {
	public byte[] Get_or_new(String v) {return Get_or_new(Bry_.new_utf8_(v));}
	public byte[] Get_or_new(byte[] v) {
		if (v.length == 0) return Bry_.Empty;
		Object rv = hash.Fetch(hash_ref.Val_(v));
		if (rv == null) {
			Bry_obj_ref bry = Bry_obj_ref.new_(v);
			hash.AddKeyVal(bry);
			return v;
		}
		else
			return ((Bry_obj_ref)rv).Val();
	}
	HashAdp hash = HashAdp_.new_(); Bry_obj_ref hash_ref = Bry_obj_ref.null_();
	public static final Bry_cache _ = new Bry_cache(); Bry_cache() {}
}

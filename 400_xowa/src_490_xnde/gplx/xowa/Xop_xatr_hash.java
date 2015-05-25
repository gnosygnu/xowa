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
public class Xop_xatr_hash {
	private final Ordered_hash hash = Ordered_hash_.new_bry_();
	private final byte[] src;
	Xop_xatr_hash(byte[] src) {this.src = src;}
	public int Len() {return hash.Count();}
	public Xop_xatr_itm Get_at(int idx) {
		return (Xop_xatr_itm)hash.Get_at(idx);
	}
	public Xop_xatr_itm Get_by(String key) {
		return (Xop_xatr_itm)hash.Get_by(Bry_.new_u8(key));
	}
	public byte[] Get_as_bry_or(String key, byte[] or) {
		Xop_xatr_itm itm = Get_by(key);
		return itm == null ? or : itm.Val_as_bry(src);
	}
	public boolean Match(String key, String val) {
		Xop_xatr_itm itm = Get_by(key); if (itm == null) return false;
		return String_.Eq(itm.Val_as_str(src), val);
	}
	private void Add(Xop_xatr_itm itm) {
		hash.Add_if_dupe_use_nth(itm.Key_bry(), itm);
	}
	public static Xop_xatr_hash new_ary(byte[] src, Xop_xatr_itm[] ary) {
		Xop_xatr_hash rv = new Xop_xatr_hash(src);
		for (Xop_xatr_itm itm : ary)
			rv.Add(itm);
		return rv;
	}
}

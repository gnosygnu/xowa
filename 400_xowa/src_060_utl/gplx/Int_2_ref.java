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
public class Int_2_ref {
	public Int_2_ref() {}
	public Int_2_ref(int v0, int v1) {Val_all_(v0, v1);}
	public int Val_0() {return val_0;} public Int_2_ref Val_0_(int v) {val_0 = v; return this;} private int val_0;
	public int Val_1() {return val_1;} public Int_2_ref Val_1_(int v) {val_1 = v; return this;} private int val_1;
	public Int_2_ref Val_all_(int v0, int v1) {val_0 = v0; val_1 = v1; return this;}
	@Override public int hashCode() {
		int hash = 23;
		hash = (hash * 31) + val_0;
		hash = (hash * 31) + val_1;
		return hash;
	}
	@Override public boolean equals(Object obj) {
		if (obj == null) return false;
		Int_2_ref comp = (Int_2_ref)obj;
		return val_0 == comp.val_0 && val_1 == comp.val_1;
	}
	public static Int_2_ref parse_(String raw) {
		try {
			String[] itms = String_.Split(raw, ",");
			int v0 = Int_.parse_(itms[0]);
			int v1 = Int_.parse_(itms[1]);
			return new Int_2_ref(v0, v1);
		} catch (Exception e) {Err_.Noop(e); throw Err_mgr._.parse_("Int_2_ref", raw);}
	}
	public static Int_2_ref[] parse_ary_(String raw) {
		try {
			String[] itms = String_.Split(raw, ";");
			int itms_len = itms.length;
			Int_2_ref[] rv = new Int_2_ref[itms_len];
			for (int i = 0; i < itms_len; i++) {
				String[] vals = String_.Split(itms[i], ",");
				int v0 = Int_.parse_(vals[0]);
				int v1 = Int_.parse_(vals[1]);
				rv[i] = new Int_2_ref(v0, v1);
			}
			return rv;
		} catch (Exception e) {Err_.Noop(e); throw Err_mgr._.parse_("Int_2_ref[]", raw);}
	}
}

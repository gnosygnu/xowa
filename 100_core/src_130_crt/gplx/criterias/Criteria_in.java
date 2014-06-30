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
package gplx.criterias; import gplx.*;
public class Criteria_in implements Criteria {
	public byte Crt_tid() {return Criteria_.Tid_in;}
	public boolean Negated() {return negated;} private boolean negated;
	public Object[] Values() {return ary;} Object[] ary; Class<?> aryType; int aryLen;
	public boolean Matches(Object comp) {
		if (aryLen == 0) return false;	// empty array never matches
		if (!ClassAdp_.Eq_typeSafe(comp, aryType)) throw Err_.type_mismatch_(aryType, comp);
		boolean rv = false;
		for (int i = 0; i < aryLen; i++) {
			Object val = ary[i];
			if (Object_.Eq(val, comp)) {
				rv = true;
				break;
			}
		}
		return negated ? !rv : rv;
	}
	public String XtoStr() {return String_.Concat_any("IN ", String_.Concat_any(ary));}
	public Criteria_in(boolean negated, Object[] vals) {
		this.negated = negated; this.ary = vals;
		aryLen = Array_.Len(ary);
		aryType = aryLen == 0 ? Object.class : ClassAdp_.ClassOf_obj(ary[0]);
	}
	public static Criteria_in as_(Object obj) {return obj instanceof Criteria_in ? (Criteria_in)obj : null;}
}

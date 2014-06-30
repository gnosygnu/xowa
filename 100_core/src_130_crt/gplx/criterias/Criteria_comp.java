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
public class Criteria_comp implements Criteria {
	public byte Crt_tid() {return Criteria_.Tid_comp;}
	public Comparable Value() {return val;} final Comparable val;
	public boolean Matches(Object compObj) {
		Comparable comp = CompareAble_.as_(compObj);
		return CompareAble_.Is(compMode, comp, val);
	}
	public String XtoStr() {return String_.Concat_any(XtoSymbol(), " ", val);}
	public String XtoSymbol() {
		String compSym = compMode < CompareAble_.Same ? "<" : ">";
		String eqSym = compMode % 2 == CompareAble_.Same ? "=" : "";
		return compSym + eqSym;
	}

	int compMode;
	@gplx.Internal protected Criteria_comp(int compMode, Comparable val) {this.compMode = compMode; this.val = val;}
	public static Criteria_comp as_(Object obj) {return obj instanceof Criteria_comp ? (Criteria_comp)obj : null;}
}

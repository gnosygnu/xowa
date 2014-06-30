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
public class Criteria_between implements Criteria {
	public byte Crt_tid() {return Criteria_.Tid_between;}
	public boolean Negated() {return negate;} private boolean negate;
	public Comparable Lhs() {return lhs;} Comparable lhs;
	public Comparable Rhs() {return rhs;} Comparable rhs;
	public String XtoStr() {return String_.Concat_any("BETWEEN ", lhs, " AND ", rhs);}
	public boolean Matches(Object compObj) {
		Comparable comp = CompareAble_.as_(compObj);
		int lhsResult = CompareAble_.CompareComparables(lhs, comp);
		int rhsResult = CompareAble_.CompareComparables(rhs, comp);
		boolean rv = (lhsResult * rhsResult) != 1;
		return negate ? !rv : rv;
	}
	public Criteria_between(boolean negate, Comparable lhs, Comparable rhs) {this.negate = negate; this.lhs = lhs; this.rhs = rhs;}
	public static Criteria_between as_(Object obj) {return obj instanceof Criteria_between ? (Criteria_between)obj : null;}
}

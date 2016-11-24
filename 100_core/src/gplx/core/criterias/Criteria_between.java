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
package gplx.core.criterias; import gplx.*; import gplx.core.*;
public class Criteria_between implements Criteria {
	public Criteria_between(boolean neg, Comparable lo, Comparable hi) {this.neg = neg; this.lo = lo; this.hi = hi;}
	public byte			Tid()	{return Criteria_.Tid_between;}
	public boolean			Neg()	{return neg;} private final boolean neg;
	public Comparable	Lo()	{return lo;} private Comparable lo; public void Lo_(Comparable v) {this.lo = v;}
	public Comparable	Hi()	{return hi;} private Comparable hi; public void Hi_(Comparable v) {this.hi = v;}
	public void			Val_from_args(Hash_adp args) {throw Err_.new_unimplemented();}
	public void			Val_as_obj_(Object v) {
		Object[] ary = (Object[])v;
		lo = (Comparable)ary[0];
		hi = (Comparable)ary[1];
	}
	public boolean Matches(Object comp_obj) {
		Comparable comp = CompareAble_.as_(comp_obj);
		int lo_rslt = CompareAble_.CompareComparables(lo, comp);
		int hi_rslt = CompareAble_.CompareComparables(hi, comp);
		boolean rv = (lo_rslt * hi_rslt) != 1;
		return neg ? !rv : rv;
	}

	public String To_str() {return String_.Concat_any("BETWEEN ", lo, " AND ", hi);}
}

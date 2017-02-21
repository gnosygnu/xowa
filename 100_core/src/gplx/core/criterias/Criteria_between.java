/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.criterias; import gplx.*; import gplx.core.*;
public class Criteria_between implements Criteria {
	public Criteria_between(boolean neg, Comparable lo, Comparable hi) {this.neg = neg; this.lo = lo; this.hi = hi;}
	public byte			Tid()	{return Criteria_.Tid_between;}
	public boolean			Neg()	{return neg;} private final    boolean neg;
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
		int lo_rslt = CompareAble_.Compare_comp(lo, comp);
		int hi_rslt = CompareAble_.Compare_comp(hi, comp);
		boolean rv = (lo_rslt * hi_rslt) != 1;
		return neg ? !rv : rv;
	}

	public String To_str() {return String_.Concat_any("BETWEEN ", lo, " AND ", hi);}
}

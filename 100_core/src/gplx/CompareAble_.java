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
package gplx;
public class CompareAble_ {
	public static Comparable as_(Object obj) {return obj instanceof Comparable ? (Comparable)obj : null;}
	public static int Compare_obj(Object lhs, Object rhs) {return Compare_comp(as_(lhs), as_(rhs));}
	public static int Compare_comp(Comparable lhs, Comparable rhs) {
		if		(lhs == null && rhs == null)	return CompareAble_.Same;
		else if (lhs == null)					return CompareAble_.More;
		else if (rhs == null)					return CompareAble_.Less;
		else									return Compare(lhs, rhs);
	}
	public static int Compare(Comparable lhs, Comparable rhs) {return lhs.compareTo(rhs);}	

	public static boolean Is(int expd, Comparable lhs, Comparable rhs) {
		int actl = Compare_comp(lhs, rhs);
		if (actl == Same && expd % 2 == Same)	// actl=Same and expd=(Same||MoreOrSame||LessOrSame)
			return true;
		else
			return (actl * expd) > 0;			// actl=More||Less; expd will match if on same side of 0 (ex: expd=Less; actl=Less; -1 * -1 = 1)
	}

	public static final int 
	  More = 1
	, Less = -1
	, Same = 0
	, More_or_same = 2
	, Less_or_same = -2
	;
}

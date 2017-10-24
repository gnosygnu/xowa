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
package gplx.core.lists.binary_searches; import gplx.*; import gplx.core.*; import gplx.core.lists.*;
import gplx.core.lists.*;
interface Binary_search_cmp {
	int Compare(Object comp);
}
class Binary_search_cmp__comparable implements Binary_search_cmp {
	private final    CompareAble val;
	public Binary_search_cmp__comparable(CompareAble val) {this.val = val;}
	public int Compare(Object comp) {
		return val.compareTo((CompareAble)comp);
	}
}
class Binary_search_cmp__comparer implements Binary_search_cmp {
	private final    Binary_comparer comparer; private final    Object val;
	public Binary_search_cmp__comparer(Binary_comparer comparer, Object val) {this.comparer = comparer; this.val = val;}
	public int Compare(Object comp) {
		return comparer.Compare_val_to_obj(val, comp);
	}
}

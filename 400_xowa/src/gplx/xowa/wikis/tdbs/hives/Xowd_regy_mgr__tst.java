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
package gplx.xowa.wikis.tdbs.hives; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import org.junit.*; import gplx.core.lists.*;
public class Xowd_regy_mgr__tst implements ComparerAble {
	@Test  public void Basic() {
		String[] slotAry = new String[] {"b", "e", "h"};  // 0=b 1=e 2=h
		tst_FindSlot(slotAry, "f", "h");	// f ->  1 2 -> 2
		tst_FindSlot(slotAry, "c", "e");	// c -> -1 1 -> 0 ->  0 1 -> 1
		tst_FindSlot(slotAry, "a", "b");	// a -> -1 1 -> 0 -> -1 0 -> 0
	}
	@Test  public void Null() {
		String[] slotAry = new String[] {"b", "g", "l", "q", "v", null}; 
		tst_FindSlot(slotAry, "a", "b");
		tst_FindSlot(slotAry, "b", "b");
		tst_FindSlot(slotAry, "c", "g");
		tst_FindSlot(slotAry, "v", "v");
		tst_FindSlot(slotAry, "w", null);
	}
	public int compare(Object lhsObj, Object rhsObj) {return CompareAble_.Compare_obj(lhsObj, rhsObj);}
	void tst_FindSlot(String[] slotAry, String s, String expd) {Tfds.Eq(expd, slotAry[gplx.xowa.wikis.tdbs.hives.Xowd_regy_mgr_.FindSlot(this, slotAry, s)]);}
}

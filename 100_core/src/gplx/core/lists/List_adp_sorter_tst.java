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
package gplx.core.lists; import gplx.*; import gplx.core.*;
import org.junit.*;
public class List_adp_sorter_tst {
	@Test  public void Basic() {
		Object[] src = new Object[] {0,8,1,7,2,6,3,5,4};
		List_adp_sorter.new_().Sort(src, src.length);
		Tfds.Eq_ary(src, Sequential(0, 8));
	}
	@Test  public void Basic2() {
		Object[] src = new Object[] {"0","8","1","7","2","6","3","5","4"};
		List_adp_sorter.new_().Sort(src, src.length);
		Tfds.Eq_ary(src, new Object[] {"0","1","2","3","4","5","6","7","8"});
	}
	Object[] Sequential(int bgn, int end) {
		Object[] rv = new Object[end - bgn + 1];
		for (int i = 0; i < Array_.Len(rv); i++)
			rv[i] = i + bgn;
		return rv;
	}
}

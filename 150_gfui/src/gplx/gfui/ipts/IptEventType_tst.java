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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
import org.junit.*; import gplx.gfui.ipts.*;
public class IptEventType_tst {
	@Test  public void Has() {
		tst_Has(IptEventType_.KeyDown, IptEventType_.KeyDown, true);
		tst_Has(IptEventType_.KeyUp, IptEventType_.KeyDown, false);
		tst_Has(IptEventType_.None, IptEventType_.KeyDown, false);
		tst_Has(IptEventType_.KeyDown, IptEventType_.None, false);
		tst_Has(IptEventType_.KeyDown.Add(IptEventType_.KeyUp), IptEventType_.KeyDown, true);
		tst_Has(IptEventType_.MouseDown.Add(IptEventType_.MouseUp), IptEventType_.KeyDown, false);
		tst_Has(IptEventType_.KeyDown.Add(IptEventType_.KeyUp), IptEventType_.None, false);
	}	void tst_Has(IptEventType val, IptEventType find, boolean expd) {Tfds.Eq(expd, IptEventType_.Has(val, find));}
	@Test  public void add_() {
		tst_add(IptEventType_.KeyDown, IptEventType_.KeyDown, IptEventType_.KeyDown.Val());
		tst_add(IptEventType_.KeyDown, IptEventType_.KeyUp, IptEventType_.KeyDown.Val() + IptEventType_.KeyUp.Val());
	}	void tst_add(IptEventType lhs, IptEventType rhs, int expd) {Tfds.Eq(expd, IptEventType_.add_(lhs, rhs).Val());}
}

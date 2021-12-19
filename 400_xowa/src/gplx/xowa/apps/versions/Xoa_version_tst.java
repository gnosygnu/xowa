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
package gplx.xowa.apps.versions;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.frameworks.tests.GfoTstr;
import org.junit.*;
public class Xoa_version_tst {
	@Before public void init() {fxt.Clear();} private Xoa_version_fxt fxt = new Xoa_version_fxt();
	@Test public void Compare() {
		fxt.Test_compare("1.8.1.1", "1.8.2.1"	, CompareAbleUtl.Less);	// rev:less
		fxt.Test_compare("1.8.2.1", "1.8.1.1"	, CompareAbleUtl.More);	// rev:more
		fxt.Test_compare("1.8.1.1", "1.8.1.1"	, CompareAbleUtl.Same);	// rev:same
		fxt.Test_compare("1.7.9.1", "1.8.1.1"	, CompareAbleUtl.Less);	// min:less
		fxt.Test_compare("", "1.8.1.1"			, CompareAbleUtl.Less);	// empty:less
		fxt.Test_compare("1.8.1.1", ""			, CompareAbleUtl.More);	// empty:more
		fxt.Test_compare("", ""					, CompareAbleUtl.Same);	// empty:more
	}
}
class Xoa_version_fxt {
	public void Clear() {}
	public void Test_compare(String lhs, String rhs, int expd) {
		GfoTstr.EqObj(expd, Xoa_version_.Compare(lhs, rhs), lhs + "|" + rhs);
	}
}

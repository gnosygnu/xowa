/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.type_xtns;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.commons.GfoDateUtl;
import org.junit.*;
public class DateAdpClassXtn_tst {
	@Test public void XtoDb() {
		tst_XtoDb("20091115 220102.999", "2009-11-15 22:01:02.999");
	}
	void tst_XtoDb(String val, String expdRaw) {
		String actlRaw = (String)DateAdpClassXtn.Instance.XtoDb(GfoDateUtl.ParseGplx(val));
		GfoTstr.EqObj(expdRaw, actlRaw);
	}
}

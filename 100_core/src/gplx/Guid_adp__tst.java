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
import org.junit.*;
public class Guid_adp__tst {
	@Test  public void parse() {
		tst_parse_("467ffb41-cdfe-402f-b22b-be855425784b");
	}
	void tst_parse_(String s) {
		Guid_adp uuid = Guid_adp_.Parse(s);
		Tfds.Eq(uuid.To_str(), s);
	}
}

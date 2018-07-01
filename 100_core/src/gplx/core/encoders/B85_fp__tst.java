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
package gplx.core.encoders; import gplx.*; import gplx.core.*;
import org.junit.*;
public class B85_fp__tst {
	private final B85_fp__fxt fxt = new B85_fp__fxt();
	@Test  public void Double_to_str() {
		fxt.Test__to_str(.1d, "/\"");
	}
}
class B85_fp__fxt {
	public void Test__to_str(double val, String expd) {
		byte[] actl = B85_fp_.To_bry(val);
		Tfds.Eq_str(expd, String_.new_a7(actl));
	}
}

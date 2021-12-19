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
package gplx.gfui.envs; import gplx.gfui.*;
import gplx.frameworks.tests.GfoTstr;
import org.junit.*;
public class ScreenAdp_tst {
	@Test public void parse() {
		ScreenAdp actl = ScreenAdp_.parse("{screen{0}");
		GfoTstr.EqObj(0, actl.Index());
	}
	@Test public void opposite_() {
		ScreenAdp actl = ScreenAdp_.from_point_(PointAdp_.new_(2000, 2000));
		GfoTstr.EqObj(0, actl.Index());
	}
}

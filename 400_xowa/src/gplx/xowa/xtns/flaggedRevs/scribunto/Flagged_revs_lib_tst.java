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
package gplx.xowa.xtns.flaggedRevs.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.flaggedRevs.*;
import org.junit.*;
import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.libs.*; import gplx.xowa.xtns.scribunto.engines.mocks.*;
public class Flagged_revs_lib_tst {
	private final Mock_scrib_fxt fxt = new Mock_scrib_fxt(); private Flagged_revs_lib lib;
	@Before public void init() {
		fxt.Clear();
		lib = new Flagged_revs_lib();
		lib.Init();
		lib.Core_(fxt.Core());
	}
	@Test   public void GetStabilitySettings() {
		fxt.Test__proc__objs__nest(lib, Flagged_revs_lib.Invk_getStabilitySettings, Object_.Ary("Page1"), String_.Concat_lines_nl_skip_last
		( "1="
		, "  over"+"ride=0"
		, "  autoreview="
		, "  expiry=infinity"
		));
	}
}	

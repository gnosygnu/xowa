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
import org.junit.*; import gplx.core.tests.*; import gplx.core.envs.*;
public class Io_url__tst {
	@Before public void init() {fxt.Clear();} private final    Io_url__fxt fxt = new Io_url__fxt();
	@Test   public void Basic__lnx()	{fxt.Test__New__http_or_null(Bool_.N, "file:///C:/a.txt", "C:/a.txt");}
	@Test   public void Basic__wnt()	{fxt.Test__New__http_or_null(Bool_.Y, "file:///C:/a.txt", "C:\\a.txt");}
	@Test   public void Null()			{fxt.Test__New__http_or_null(Bool_.N, "C:/a.txt", null);}
}
class Io_url__fxt {
	public void Clear() {Io_mgr.Instance.InitEngine_mem();}
	public void Test__New__http_or_null(boolean os_is_wnt, String raw, String expd) {
		Op_sys.Cur_(os_is_wnt ? Op_sys.Tid_wnt : Op_sys.Tid_lnx);
		Gftest.Eq__obj_or_null(expd, Io_url_.New__http_or_null(raw));
	}
}

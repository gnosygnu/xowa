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
package gplx.xowa.apps.servers.tcp; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
import org.junit.*;
import gplx.core.ios.*;
public class Xosrv_server_tst {
	@Before public void init() {fxt.Clear();} private Xosrv_server_fxt fxt = new Xosrv_server_fxt();
	@Test   public void Exec_js() {
		fxt.Test_exec_js("{\"args\":[\"xowa_exec_test\",\"a\",\"b\"]}", "{\"xowa_js_result\":\"xowa_exec_test|a|b\"}");
	}
	@Test   public void Exec_js_ary() {
		fxt.Test_exec_js("{\"args\":[\"xowa_exec_test_as_array\",\"a\",\"b\"]}", "{\"xowa_js_result\":[\"xowa_exec_test_as_array\",\"a\",\"b\"]}");
	}
}
class Xosrv_server_fxt {
	public Xosrv_server_fxt Clear() {
		app = Xoa_app_fxt.Make__app__edit();
		Xoa_app_fxt.Init_gui(app, null);	// NOTE: null wiki does not matter for test
		return this;
	} 	private Xoae_app app;
	public void Test_exec_js(String raw, String expd) {
		String actl = app.Tcp_server().Exec_js(null, Bry_.new_a7(raw));
		Tfds.Eq(expd, actl);
	}
}

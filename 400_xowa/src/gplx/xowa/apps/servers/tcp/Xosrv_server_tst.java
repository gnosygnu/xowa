/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
		app = Xoa_app_fxt.app_();
		Xoa_app_fxt.Init_gui(app, null);	// NOTE: null wiki does not matter for test
		return this;
	} 	private Xoae_app app;
	public void Test_exec_js(String raw, String expd) {
		String actl = app.Tcp_server().Exec_js(null, Bry_.new_a7(raw));
		Tfds.Eq(expd, actl);
	}
}

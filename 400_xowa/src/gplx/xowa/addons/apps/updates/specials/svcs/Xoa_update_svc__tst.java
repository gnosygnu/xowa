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
package gplx.xowa.addons.apps.updates.specials.svcs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*; import gplx.xowa.addons.apps.updates.specials.*;
import org.junit.*; import gplx.core.tests.*; import gplx.core.envs.*;
public class Xoa_update_svc__tst {
	private final    Xoa_update_svc__fxt fxt = new Xoa_update_svc__fxt();
	@Test 	public void Restart_cmd() {
		Io_url jar_fil = Io_url_.new_dir_("/home/gnosygnu/xowa/xowa.jar");
		fxt.Test__restart_cmd("manual"	, jar_fil, Op_sys.Tid_lnx, Op_sys.Bitness_64, "manual");
		fxt.Test__restart_cmd(""		, jar_fil, Op_sys.Tid_lnx, Op_sys.Bitness_64, "sh /home/gnosygnu/xowa/xowa_linux_64.sh");
		fxt.Test__restart_cmd(""		, jar_fil, Op_sys.Tid_lnx, Op_sys.Bitness_32, "sh /home/gnosygnu/xowa/xowa_linux.sh");
		fxt.Test__restart_cmd(""		, jar_fil, Op_sys.Tid_osx, Op_sys.Bitness_64, "sh /home/gnosygnu/xowa/xowa_macosx_64");
		fxt.Test__restart_cmd(""		, jar_fil, Op_sys.Tid_osx, Op_sys.Bitness_32, "sh /home/gnosygnu/xowa/xowa_macosx");
		fxt.Test__restart_cmd(""		, jar_fil, Op_sys.Tid_wnt, Op_sys.Bitness_64, "/home/gnosygnu/xowa/xowa_64.exe");
		fxt.Test__restart_cmd(""		, jar_fil, Op_sys.Tid_wnt, Op_sys.Bitness_32, "/home/gnosygnu/xowa/xowa.exe");
	}
}
class Xoa_update_svc__fxt {
	public void Test__restart_cmd(String current, Io_url app_url, byte op_sys_tid, byte bitness, String expd) {
		Gftest.Eq__str(expd, Xoa_update_svc.App__update__restart_cmd(current, app_url, op_sys_tid, bitness), "restart_cmd");
	}
}
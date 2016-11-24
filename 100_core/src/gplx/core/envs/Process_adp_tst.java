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
package gplx.core.envs; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Process_adp_tst {
	private Process_adp_fxt fxt = new Process_adp_fxt();
	@Test  public void Escape_ampersands_if_process_is_cmd() {
		fxt.Test_Escape_ampersands_if_process_is_cmd(Bool_.Y, "cmd"	, "/c \"http://a.org?b=c&d=e\"", "/c \"http://a.org?b=c^&d=e\"");
		fxt.Test_Escape_ampersands_if_process_is_cmd(Bool_.Y, "cmd1", "/c \"http://a.org?b=c&d=e\"", "/c \"http://a.org?b=c&d=e\"");
		fxt.Test_Escape_ampersands_if_process_is_cmd(Bool_.N, "cmd"	, "/c \"http://a.org?b=c&d=e\"", "/c \"http://a.org?b=c&d=e\"");
	}
}
class Process_adp_fxt {
	public void Test_Escape_ampersands_if_process_is_cmd(boolean os_is_wnt, String exe_url, String exe_args, String expd) {
		Tfds.Eq(expd, Process_adp.Escape_ampersands_if_process_is_cmd(os_is_wnt, exe_url, exe_args));
	}
}

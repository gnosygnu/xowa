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

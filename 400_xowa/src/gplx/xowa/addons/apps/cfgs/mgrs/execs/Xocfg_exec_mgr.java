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
package gplx.xowa.addons.apps.cfgs.mgrs.execs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.mgrs.*;
public class Xocfg_exec_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New();
	public void Add(Gfo_invk invk, String... cmds) {
		for (String cmd : cmds) {
			hash.Add(cmd, invk);
		}
	}
	public void Exec(String cmd, Object... args) {
		Gfo_invk invk = (Gfo_invk)hash.Get_by(cmd);

		// create msg and add args
		GfoMsg msg = GfoMsg_.new_parse_(cmd);
		int args_len = args.length;
		if (args_len > 0) {
			for (int i = 0; i < args_len; i += 2) {
				msg.Add((String)args[i], args[i + 1]);
			}
		}
		Gfo_invk_.Invk_by_msg(invk, cmd, msg);
	}
}

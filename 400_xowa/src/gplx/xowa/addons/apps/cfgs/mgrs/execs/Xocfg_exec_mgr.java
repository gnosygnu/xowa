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

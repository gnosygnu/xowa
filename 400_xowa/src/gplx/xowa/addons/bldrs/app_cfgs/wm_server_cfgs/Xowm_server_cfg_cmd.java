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
package gplx.xowa.addons.bldrs.app_cfgs.wm_server_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.app_cfgs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
public class Xowm_server_cfg_cmd extends Xob_cmd__base {
	public Xowm_server_cfg_cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		new Xowm_server_cfg_mgr().Exec(bldr.App());
	}

	public static final String BLDR_CMD_KEY = "cfg.wikis.wm_server_cfg";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;}
	public static final    Xob_cmd Prototype = new Xowm_server_cfg_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xowm_server_cfg_cmd(bldr, wiki);}
}

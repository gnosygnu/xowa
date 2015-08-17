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
package gplx.xowa.bldrs.cmds.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.dbs.*; import gplx.xowa.bldrs.*;
import gplx.xowa.wmfs.*; import gplx.xowa.wmfs.data.*;
import gplx.xowa.wikis.domains.*;
public class Xob_site_meta_cmd implements Xob_cmd {
	private final Xob_bldr bldr;
	public Xob_site_meta_cmd(Xob_bldr bldr, Xow_wiki wiki) {this.bldr = bldr;}
	public String Cmd_key() {return Xob_cmd_keys.Key_site_meta;}
	public void Cmd_run() {
		Site_meta_itm.Build_site_meta(bldr.App().Wmf_mgr(), bldr.App().Fsys_mgr().Cfg_site_meta_fil(), Xow_wmf_api_mgr.Wikis, DateAdp_.Now());
	}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return GfoInvkAble_.Rv_unhandled;}
}

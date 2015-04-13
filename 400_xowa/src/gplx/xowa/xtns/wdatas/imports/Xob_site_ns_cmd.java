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
package gplx.xowa.xtns.wdatas.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.dbs.*; import gplx.xowa.bldrs.*;
public class Xob_site_ns_cmd implements Xob_cmd {
	private final Xob_bldr bldr;
	public Xob_site_ns_cmd(Xob_bldr bldr, Xow_wiki wiki) {this.bldr = bldr;}
	public String Cmd_key() {return Xob_cmd_keys.Key_wbase_ns;}		
	public void Cmd_run() {
		Xow_wmf_api_mgr api_mgr = new Xow_wmf_api_mgr();
		Db_conn conn = Xowmf_site_tbl.Get_conn_or_new(bldr.App().Fsys_mgr().Root_dir());
		Xowmf_site_tbl tbl_site = new Xowmf_site_tbl(conn); Xowmf_ns_tbl tbl_itm = new Xowmf_ns_tbl(conn);
		Xow_wmf_api_wkr__ns api_wkr = new Xow_wmf_api_wkr__ns(tbl_site, tbl_itm);
		api_mgr.Api_exec(api_wkr);
	}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return GfoInvkAble_.Rv_unhandled;}
}

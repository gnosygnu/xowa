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
package gplx.xowa.xtns.xowa_cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
import gplx.xowa.apps.*;
public class Xop_xowa_func_tst {
	@Before public void init() {
		Xoa_gfs_mgr.Msg_parser_init();
	} private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Template() {
		GfsCore._.AddCmd(fxt.App(), Xoae_app.Invk_app);
		fxt.Wiki().Sys_cfg().Xowa_cmd_enabled_(true);
		fxt.Init_defn_add("A", "{{#xowa|{{{1}}}}}");
		fxt.Test_parse_page_all_str("{{A|app.users.get('anonymous').name;}}", "anonymous");
	}
}

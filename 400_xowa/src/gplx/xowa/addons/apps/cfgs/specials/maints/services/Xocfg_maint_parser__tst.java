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
package gplx.xowa.addons.apps.cfgs.specials.maints.services; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.maints.*;
import org.junit.*; import gplx.core.tests.*;
public class Xocfg_maint_parser__tst {
	private final    Xocfg_maint_parser__fxt fxt = new Xocfg_maint_parser__fxt();
	@Test   public void Parse_grp() {
		fxt.Exec__parse("root {grp {key='key_1'; owner='owner_1'; name='name_1'; help='help_1'}}"
		, fxt.Make__grp("key_1", "owner_1", "name_1", "help_1")
		);
	}
	@Test   public void Parse_itm() {
		fxt.Exec__parse("root {itm {key='key_1'; owner='owner_1'; name='name_1'; help='help_1'; scope='scope_1'; db_type='db_type_1'; dflt='dflt_1'; gui_type='gui_type_1'; gui_args='gui_args_1'}}"
		, fxt.Make__itm("key_1", "owner_1", "name_1", "help_1", "scope_1", "db_type_1", "dflt_1", "gui_type_1", "gui_args_1")
		);
	}
}
class Xocfg_maint_parser__fxt {
	private Xocfg_maint_parser parser = new Xocfg_maint_parser();
	public void Exec__parse(String raw, Xocfg_maint_nde... expd) {
		Xocfg_maint_nde[] actl = parser.Parse(raw);
		Gftest.Eq__ary(expd, actl);
	}
	public Xocfg_maint_grp Make__grp(String key, String owner, String name, String help) {
		return new Xocfg_maint_grp(key, owner, name, help);
	}
	public Xocfg_maint_itm Make__itm(String key, String owner, String name, String help, String scope, String db_type, String dflt, String gui_type, String gui_args) {
		return new Xocfg_maint_itm(key, owner, name, help, scope, db_type, dflt, gui_type, gui_args);
	}
}
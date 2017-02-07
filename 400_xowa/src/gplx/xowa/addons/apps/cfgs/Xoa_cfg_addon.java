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
package gplx.xowa.addons.apps.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*;
import gplx.xowa.specials.*; import gplx.xowa.htmls.bridges.*;
import gplx.xowa.addons.apps.cfgs.specials.edits.pages.*; import gplx.xowa.addons.apps.cfgs.specials.edits.services.*;
import gplx.xowa.addons.apps.cfgs.specials.maints.pages.*; import gplx.xowa.addons.apps.cfgs.specials.maints.services.*;
public class Xoa_cfg_addon implements Xoax_addon_itm, Xoax_addon_itm__special, Xoax_addon_itm__json {
	public Xow_special_page[] Special_pages() {
		return new Xow_special_page[]
		{ Xocfg_maint_special.Prototype
		, Xocfg_edit_special.Prototype
		};
	}
	public Bridge_cmd_itm[] Json_cmds() {
		return new Bridge_cmd_itm[]
		{ Xocfg_maint_bridge.Prototype
		, Xocfg_edit_bridge.Prototype
		};
	}

	public String Addon__key() {return ADDON__KEY;} private static final String ADDON__KEY = "xowa.app.cfg";
}

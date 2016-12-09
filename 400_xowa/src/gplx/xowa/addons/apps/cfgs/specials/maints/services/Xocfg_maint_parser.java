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
import gplx.langs.gfs.*;
class Xocfg_maint_parser {
	public Xocfg_maint_nde[] Parse(String raw) {
		GfoMsg root = Gfs_msg_bldr.Instance.ParseToMsg(raw);

		int len = root.Subs_count();
		Xocfg_maint_nde[] rv = new Xocfg_maint_nde[len];
		for (int i = 0; i < len; i++) {
			GfoMsg msg = root.Subs_getAt(i);
			rv[i] = Parse_nde(msg);
		}
		return rv;
	}
	private Xocfg_maint_nde Parse_nde(GfoMsg msg) {
		Ordered_hash hash = To_atr_hash(msg);
		// get common atrs
		String owner	= Get_atr_as_str_or_fail(hash, "owner_");
		String key		= Get_atr_as_str_or_fail(hash, "key_");
		String name		= Get_atr_as_str_or_fail(hash, "name_");
		String help		= Get_atr_as_str_or_fail(hash, "help_");

		// create
		String nde_type = msg.Key();
		if (String_.Eq(nde_type, "grp")) {
			return new Xocfg_maint_grp(key, owner, name, help);
		}
		else if (String_.Eq(nde_type, "itm")) {
			String scope		= Get_atr_as_str_or_fail(hash, "scope_");
			String db_type		= Get_atr_as_str_or_fail(hash, "db_type_");
			String dflt			= Get_atr_as_str_or_fail(hash, "dflt_");
			String gui_type		= Get_atr_as_str_or(hash, "gui_type_", null);
			String gui_args		= Get_atr_as_str_or(hash, "gui_args_", "");

			if (gui_type == null) {
				gui_type = gplx.xowa.addons.apps.cfgs.enums.Xoitm_gui_tid.Infer_gui_type(db_type);
			}
			return new Xocfg_maint_itm(key, owner, name, help, scope, db_type, dflt, gui_type, gui_args);
		}
		else throw Err_.new_wo_type("xo.cfg_maint:unknown type", "type", nde_type);
	}
	private static Ordered_hash To_atr_hash(GfoMsg msg) {
		Ordered_hash rv = Ordered_hash_.New();
		int len = msg.Subs_count();
		for (int i = 0; i < len; i++) {
			GfoMsg sub = msg.Subs_getAt(i);
			rv.Add(sub.Key(), sub.Args_getAt(0).Val());
		}
		return rv;
	}
	private static String Get_atr_as_str_or_fail(Ordered_hash hash, String key) {			
		String rv = Get_atr_as_str_or(hash, key, null);
		if (rv == null) throw Err_.new_wo_type("xo.cfg_maint:missing key", "key", key);
		return rv;
	}
	private static String Get_atr_as_str_or(Ordered_hash hash, String key, String or) {
		String val = (String)hash.Get_by(key);
		return val == null ? or : val;
	}
}

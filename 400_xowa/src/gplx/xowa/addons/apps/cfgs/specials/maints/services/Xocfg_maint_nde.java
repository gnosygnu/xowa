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
abstract class Xocfg_maint_nde implements gplx.core.brys.Bry_bfr_able {
	public Xocfg_maint_nde(int id, String key, String owner, String name, String help) {
		this.id = id;
		this.key = key;
		this.owner = owner;
		this.name = name;
		this.help = help;
	}
	public abstract boolean Type_is_grp();
	public int Id() {return id;} private final    int id;
	public String Key() {return key;} private final    String key;
	public String Owner() {return owner;} private final    String owner;
	public String Name() {return name;} private final    String name;
	public String Help() {return help;} private final    String help;
	public void To_bfr(Bry_bfr bfr) {
		bfr.Add_str_u8_fmt("{0}|{1}|{2}|{3}|{4}", this.Type_is_grp(), key, owner, name, help);
		To_bfr_hook(bfr);
	}
	protected abstract void To_bfr_hook(Bry_bfr bfr);
}
class Xocfg_maint_grp extends Xocfg_maint_nde {	public Xocfg_maint_grp(int id, String key, String owner, String name, String help) {super(id, key, owner, name, help);
	}
	@Override public boolean Type_is_grp() {return true;}
	@Override protected void To_bfr_hook(Bry_bfr bfr) {}
}
class Xocfg_maint_itm extends Xocfg_maint_nde {	public Xocfg_maint_itm(int id, String key, String owner, String name, String help, String scope, String db_type, String dflt, String gui_type, String gui_args, String gui_cls) {super(id, key, owner, name, help);
		this.scope = scope;
		this.db_type = db_type;
		this.dflt = dflt;
		this.gui_type = gui_type;
		this.gui_args = gui_args;
		this.gui_cls = gui_cls;
	}
	@Override public boolean Type_is_grp() {return false;}
	public String Scope() {return scope;} private final    String scope;
	public String Db_type() {return db_type;} private final    String db_type;
	public String Dflt() {return dflt;} private final    String dflt;
	public String Gui_type() {return gui_type;} private final    String gui_type;
	public String Gui_args() {return gui_args;} private final    String gui_args;
	public String Gui_cls() {return gui_cls;} private final    String gui_cls;
	@Override protected void To_bfr_hook(Bry_bfr bfr) {
		bfr.Add_str_u8_fmt("|{0}|{1}|{2}|{3}|{4}|{5}", scope, db_type, dflt, gui_type, gui_args, gui_cls);
	}
}

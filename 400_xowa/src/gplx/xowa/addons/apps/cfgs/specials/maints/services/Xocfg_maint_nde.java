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
	public Xocfg_maint_nde(String owner, int id, String key, String name, String help) {
		this.owner = owner;
		this.id = id;
		this.key = key;
		this.name = name;
		this.help = help;
	}
	public abstract boolean Type_is_grp();
	public String Owner() {return owner;} private final    String owner;
	public int Id() {return id;} private final    int id;
	public String Key() {return key;} private final    String key;
	public String Name() {return name;} private final    String name;
	public String Help() {return help;} private final    String help;
	public void To_bfr(Bry_bfr bfr) {
		bfr.Add_str_u8_fmt("{0}|{1}|{2}|{3}|{4}", owner, this.Type_is_grp(), key, name, help);
		To_bfr_hook(bfr);
	}
	protected abstract void To_bfr_hook(Bry_bfr bfr);
}
class Xocfg_maint_grp extends Xocfg_maint_nde {	public Xocfg_maint_grp(String owner, int id, String key, String name, String help) {super(owner, id, key, name, help);
	}
	@Override public boolean Type_is_grp() {return true;}
	@Override protected void To_bfr_hook(Bry_bfr bfr) {}
}
class Xocfg_maint_itm extends Xocfg_maint_nde {	public Xocfg_maint_itm(String owner, int id, String key, String name, String help, String scope, String type, String dflt, String html_atrs, String html_cls) {super(owner, id, key, name, help);
		this.scope = scope;
		this.type = type;
		this.dflt = dflt;
		this.html_atrs = html_atrs;
		this.html_cls = html_cls;
	}
	@Override public boolean Type_is_grp() {return false;}
	public String Scope() {return scope;} private final    String scope;
	public String Type() {return type;} private final    String type;
	public String Dflt() {return dflt;} private final    String dflt;
	public String Html_atrs() {return html_atrs;} private final    String html_atrs;
	public String Html_cls() {return html_cls;} private final    String html_cls;
	@Override protected void To_bfr_hook(Bry_bfr bfr) {
		bfr.Add_str_u8_fmt("|{0}|{1}|{2}|{3}|{4}", scope, type, dflt, html_atrs, html_cls);
	}
}

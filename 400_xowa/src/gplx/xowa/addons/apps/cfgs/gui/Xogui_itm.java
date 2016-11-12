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
package gplx.xowa.addons.apps.cfgs.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
public class Xogui_itm {
	public Xogui_itm(int id, int sort) {
		this.id = id;
		this.sort = sort;
	}
	public int Id() {return id;} private final    int id;
	public int Sort() {return sort;} private final    int sort;

	public int Scope_id() {return scope_id;} private int scope_id;
	public int Gui_type() {return gui_type;} private int gui_type;
	public String Gui_args() {return gui_args;} private String gui_args;
	public String Key() {return key;} private String key;
	public String Dflt() {return dflt;} private String dflt;

	public String Lang() {return lang;} private String lang;
	public String Name() {return name;} private String name;
	public String Help() {return help;} private String help;

	public String Ctx() {return ctx;} private String ctx;
	public String Val() {return val;} private String val;
	public String Date() {return date;} private String date;
	public void Load_by_meta(String key, int scope_id, int gui_type, String gui_args, String dflt) {
		this.scope_id = scope_id;
		this.gui_type = gui_type;
		this.gui_args = gui_args;
		this.key = key;
		this.dflt = dflt;
	}
	public void Load_by_i18n(String lang, String name, String help) {
		this.lang = lang;
		this.name = name;
		this.help = help;
	}
	public void Load_by_data(String ctx, String val, String date) {
		this.ctx = ctx;
		this.val = val;
		this.date = date;
	}
}

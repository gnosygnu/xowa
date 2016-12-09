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
package gplx.xowa.addons.apps.cfgs.specials.edits.objs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*; import gplx.xowa.addons.apps.cfgs.specials.edits.*;
import gplx.langs.mustaches.*;
import gplx.core.gfobjs.*; import gplx.langs.jsons.*;
public class Xoedit_itm implements Xoedit_nde, Mustache_doc_itm {
	public Xoedit_itm(int id, int sort, String key) {
		this.id = id;
		this.sort = sort;
		this.key = key;
	}
	public int Id() {return id;} private final    int id;
	public int Sort() {return sort;} private final    int sort;

	public int Scope_id() {return scope_id;} private int scope_id;
	public int Gui_type() {return gui_type;} private int gui_type;
	public String Gui_args() {return gui_args;} private String gui_args;
	public String Key() {return key;} private String key;
	public String Dflt() {return dflt;} private String dflt;
	public boolean Edited() {return edited;} private boolean edited;

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
		this.edited = true;
	}
	public void Set_data_by_dflt() {
		this.ctx = Ctx__app;
		this.val = dflt;
		this.date = String_.Empty;
		this.edited = false;
	}
	public Gfobj_nde To_nde() {
		Gfobj_nde rv = Gfobj_nde.New();
		rv.Add_int("id", id);
		rv.Add_str("key", key);
		rv.Add_str("dflt", dflt);
		rv.Add_str("lang", lang);
		rv.Add_str("name", name);
		rv.Add_str("help", help);
		rv.Add_str("ctx", ctx);
		rv.Add_str("val", val);
		rv.Add_str("date", date);
		Bry_bfr bfr = Bry_bfr_.New();
		To_html(bfr);
		rv.Add_str("html", bfr.To_str_and_clear());
		return rv;
	}
	private void To_html(Bry_bfr bfr) {
		new Xoedit_itm_html().Build_html(bfr, key, name, gui_type, gui_args, val);
	}
	public boolean Mustache__write(String k, Mustache_bfr bfr) {
		if		(String_.Eq(k, "id"))		bfr.Add_int(id);
		else if	(String_.Eq(k, "key"))		bfr.Add_str_u8(key);
		else if	(String_.Eq(k, "dflt"))		bfr.Add_str_u8(dflt);
		else if	(String_.Eq(k, "lang"))		bfr.Add_str_u8(lang);
		else if	(String_.Eq(k, "name"))		bfr.Add_str_u8(name);
		else if	(String_.Eq(k, "help"))		bfr.Add_str_u8(help);
		else if	(String_.Eq(k, "ctx"))		bfr.Add_str_u8(ctx);
		else if	(String_.Eq(k, "val"))		bfr.Add_str_u8(val);
		else if	(String_.Eq(k, "date"))		bfr.Add_str_u8(date);
		else if	(String_.Eq(k, "html"))		To_html(bfr.Bfr());
		return true;
	}
	public static String Ctx__app = "app";
	public Mustache_doc_itm[] Mustache__subs(String k) {
		if	(String_.Eq(k, "edited"))		return Mustache_doc_itm_.Ary__bool(edited);
		return Mustache_doc_itm_.Ary__empty;
	}
}

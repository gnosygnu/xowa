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
import gplx.xowa.addons.apps.cfgs.mgrs.types.*;
import gplx.langs.htmls.*;
public class Xoedit_itm implements Xoedit_nde, Mustache_doc_itm {
	private String gui_type;
	private boolean edited;
	private String data_type, gui_args, gui_cls, lang, name, ctx, date;
	private byte[] val, dflt;	// NOTE: data is always escaped b/c it is only consumed by mustache; EX: "&lt;&apos;" not "<'"
	private Xocfg_type_mgr type_mgr;
	public Xoedit_itm(Xocfg_type_mgr type_mgr, int id, String key, int sort) {
		this.type_mgr = type_mgr;
		this.id = id;
		this.key = key;
		this.sort = sort;
	}
	public int		Id()		{return id;}	private final    int id;
	public String	Key()		{return key;}	private final    String key;
	public String	Help()		{return help;}	private String help;

	public int		Sort()		{return sort;}	private final    int sort;
	public void Load_by_meta(Bry_bfr tmp_bfr, int scope_id, String data_type, String gui_type, String gui_args, String gui_cls, String dflt_str) {
		this.data_type = data_type;
		this.gui_type = gui_type;
		this.gui_args = gui_args;
		this.gui_cls = gui_cls;
		this.dflt = Gfh_utl.Escape_html_as_bry(tmp_bfr, Bry_.new_u8(dflt_str), Bool_.N, Bool_.N, Bool_.N, Bool_.Y, Bool_.N);
	}
	public void Load_by_i18n(String lang, String name, String help) {
		this.lang = lang;
		this.name = name;
		this.help = help;
	}
	public void Load_by_data(Bry_bfr tmp_bfr, String ctx, String val_str, String date) {
		this.ctx = ctx;
		this.val = Gfh_utl.Escape_html_as_bry(tmp_bfr, Bry_.new_u8(val_str), Bool_.N, Bool_.N, Bool_.N, Bool_.Y, Bool_.N);
		this.date = date;
		this.edited = true;
		if (	String_.Has(gui_cls, "read"+"only")
			||	String_.Eq(gui_type, gplx.xowa.addons.apps.cfgs.enums.Xoitm_gui_tid.Itm__btn.Key()))
			edited = false;
	}
	public void Set_data_by_dflt() {
		this.ctx = Xocfg_mgr.Ctx__app;
		this.val = dflt;
		this.date = String_.Empty;
		this.edited = false;
	}
	public Gfobj_nde To_nde(Bry_bfr tmp_bfr) {
		Gfobj_nde rv = Gfobj_nde.New();
		rv.Add_int("id", id);
		rv.Add_str("key", key);
		rv.Add_bry("dflt", dflt);
		rv.Add_str("lang", lang);
		rv.Add_str("name", name);
		rv.Add_str("help", help);
		rv.Add_str("ctx", ctx);
		rv.Add_bry("val", val);
		rv.Add_str("date", date);
		rv.Add_str("gui", gui_type);
		To_html(tmp_bfr, type_mgr);
		rv.Add_str("html", tmp_bfr.To_str_and_clear());
		rv.Add_bool("edited", edited);
		return rv;
	}
	private void To_html(Bry_bfr bfr, Xocfg_type_mgr type_mgr) {
		Xoedit_itm_html.Build_html(bfr, type_mgr, key, name, data_type, gui_type, gui_args, gui_cls, val);
	}
	public boolean Mustache__write(String k, Mustache_bfr bfr) {
		if		(String_.Eq(k, "id"))				bfr.Add_int(id);
		else if	(String_.Eq(k, "key"))				bfr.Add_str_u8(key);
		else if	(String_.Eq(k, "dflt"))				bfr.Add_bry(dflt);
		else if	(String_.Eq(k, "lang"))				bfr.Add_str_u8(lang);
		else if	(String_.Eq(k, "name"))				bfr.Add_str_u8(name);
		else if	(String_.Eq(k, "help"))				bfr.Add_str_u8(help);
		else if	(String_.Eq(k, "ctx"))				bfr.Add_str_u8(ctx);
		else if	(String_.Eq(k, "val"))				bfr.Add_bry(val);
		else if	(String_.Eq(k, "date"))				bfr.Add_str_u8(date);
		else if	(String_.Eq(k, "gui"))				bfr.Add_str_u8(gui_type);
		else if	(String_.Eq(k, "html"))				To_html(bfr.Bfr(), type_mgr);
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String k) {
		if	(String_.Eq(k, "edited"))		return Mustache_doc_itm_.Ary__bool(edited);
		return Mustache_doc_itm_.Ary__empty;
	}
}

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
package gplx.gfui; import gplx.*;
public class Gfui_html extends GfuiElemBase {		
	public void					Under_html_(Gxw_html v) {under = v;} private Gxw_html under;
	public void					Html_doc_html_load_by_mem(String html)				{under.Html_doc_html_load_by_mem(html);}
	public void					Html_doc_html_load_by_url(String path, String html)	{under.Html_doc_html_load_by_url(path, html);}
	public byte					Html_doc_html_load_tid()							{return under.Html_doc_html_load_tid();}
	public void					Html_doc_html_load_tid_(byte v)						{under.Html_doc_html_load_tid_(v);}
	public void					Html_js_enabled_(boolean v) {under.Html_js_enabled_(v);}
	@gplx.Virtual public String		Html_js_eval_proc_as_str(String name, Object... args) {return under.Html_js_eval_proc_as_str(name, args);}
	@gplx.Virtual public boolean			Html_js_eval_proc_as_bool(String name, Object... args) {return under.Html_js_eval_proc_as_bool(name, args);}
	public String				Html_js_eval_script(String script) {return under.Html_js_eval_script(script);}
	public void					Html_js_cbks_add(String js_func_name, GfoInvkAble invk) {under.Html_js_cbks_add(js_func_name, invk);}
	public void					Html_invk_src_(GfoEvObj v) {under.Html_invk_src_(v);}
	public void					Html_dispose() {under.Html_dispose();}
	@Override public GfuiElem Text_(String v) {
		this.Html_doc_html_load_by_mem(v);
		return this;
	}
	public static Gfui_html kit_(Gfui_kit kit, String key, Gxw_html under, KeyValHash ctorArgs) {
		Gfui_html rv = new Gfui_html();
		rv.ctor_kit_GfuiElemBase(kit, key, (GxwElem)under, ctorArgs);
		rv.under = under;
		return rv;
	}
	public static Gfui_html mem_(String key, Gxw_html under) {
		Gfui_html rv = new Gfui_html();
		rv.Key_of_GfuiElem_(key);
		rv.under = under;
		return rv;
	}
	public static Object Js_args_exec(GfoInvkAble invk, Object[] args) {
		GfoMsg m = Js_args_to_msg(args);
		return GfoInvkAble_.InvkCmd_msg(invk, m.Key(), m);
	}
	public static GfoMsg Js_args_to_msg(Object[] args) {
		String proc = (String)args[0];
		GfoMsg rv = GfoMsg_.new_parse_(proc);
		for (int i = 1; i < args.length; i++)
			rv.Add(Int_.Xto_str(i), args[i]);	// NOTE: args[i] can be either String or String[]
		return rv;
	}
	public static final String Atr_href = "href", Atr_title = "title", Atr_value = "value", Atr_innerHTML = "innerHTML", Atr_src = "src";
	public static final String Elem_id_body = "body";
	public static final String Evt_location_changed = "location_changed", Evt_location_changing = "location_changing", Evt_link_hover = "link_hover", Evt_win_resized = "win_resized";
}

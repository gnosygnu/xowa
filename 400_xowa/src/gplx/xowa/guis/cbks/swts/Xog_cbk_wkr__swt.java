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
package gplx.xowa.guis.cbks.swts; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.cbks.*;
import gplx.core.gfobjs.*;
import gplx.gfui.*; import gplx.gfui.kits.core.*; import gplx.xowa.guis.*; import gplx.xowa.guis.views.*;
public class Xog_cbk_wkr__swt implements Xog_cbk_wkr {
	private final    Xoa_gui_mgr gui_mgr;
	private final    Xog_browser_func browser_func;
	private final    Gfobj_wtr__json__browser json_wtr = new Gfobj_wtr__json__browser();
	public Xog_cbk_wkr__swt(Xoa_gui_mgr gui_mgr) {
		this.gui_mgr = gui_mgr;
		this.browser_func = new Xog_browser_func();
	}
	public Object Send_json(Xog_cbk_trg trg, String func, Gfobj_nde data) {
		if (gui_mgr.Kit().Tid() != Gfui_kit_.Swt_tid) return null;	// guard against calling when HTTP_server
		String script = json_wtr.Write_as_func__swt(func, data);
		GfuiInvkCmd swt_cmd = gui_mgr.Kit().New_cmd_sync(browser_func.Script_(script));
		Xog_tab_mgr tab_mgr = gui_mgr.Browser_win().Tab_mgr();
		int tabs_len = tab_mgr.Tabs_len();
		Object rv = null;
		for (int i = 0; i < tabs_len; ++i) {
			Xog_tab_itm tab = tab_mgr.Tabs_get_at(i);
			Xoa_page page = tab.Page();
			boolean match = false;
			switch (trg.Tid()) {
				case Xog_cbk_trg.Tid__cbk_enabled:
					match = page.Html_data().Cbk_enabled();
					break;
				case Xog_cbk_trg.Tid__specific_page:
					match = Bry_.Eq(trg.Page_ttl(), page.Ttl().Full_db());
					break;
			}
			if (match) {
				browser_func.Tab_(tab);
				rv = Gfo_invk_.Invk_no_key(swt_cmd);
				if (rv == null && !String_.Eq(func, "xo.log.add__recv")) throw Err_.new_("gplx.swt", "send_json was not acknowledged", "func", func, "script", script);
			}				
		}
		return rv;
	}
}
class Xog_browser_func implements Gfo_invk {
	private String script;
	public Xog_browser_func Script_(String v) {this.script = v; return this;}
	public void Tab_(Xog_tab_itm v) {this.tab = v;} private Xog_tab_itm tab;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return tab.Html_box().Html_js_eval_script_as_obj(script);
	}
}

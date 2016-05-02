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
import gplx.gfui.*; import gplx.xowa.guis.*;
public class Xog_cbk_wkr__swt implements Xog_cbk_wkr {
	private final    Xoa_gui_mgr gui_mgr;
	private final    Xog_browser_func browser_func;
	private final    Gfobj_wtr__json__swt json_wtr = new Gfobj_wtr__json__swt();
	public Xog_cbk_wkr__swt(Xoa_gui_mgr gui_mgr) {
		this.gui_mgr = gui_mgr;
		this.browser_func = new Xog_browser_func(gui_mgr.Browser_win().Active_tab().Html_box());
	}
	public Object Send_json(String func, Gfobj_nde data) {
		String script = json_wtr.Write_as_func(func, data);
		GfuiInvkCmd swt_cmd = gui_mgr.Kit().New_cmd_sync(browser_func.Init(script));
		return GfoInvkAble_.Invk(swt_cmd);
	}
}
class Xog_browser_func implements GfoInvkAble {
	private final    Gfui_html html_box;
	private String script;
	public Xog_browser_func(Gfui_html html_box) {
		this.html_box = html_box;
	}
	public Xog_browser_func Init(String script) {
		this.script = script;
		return this;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return html_box.Html_js_eval_script(script);
	}
}

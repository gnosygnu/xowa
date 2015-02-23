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
package gplx.xowa.gui.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
import gplx.gfui.*; import gplx.xowa.gui.bnds.*; import gplx.xowa.gui.cmds.*;
public class Xog_win_itm_ {
	public static void Show_win(Xog_win_itm win) {
		Xoae_app app = win.App(); GfuiWin win_box = win.Win_box();			

		win_box.Focus_able_(false);
		win_box.BackColor_(ColorAdp_.White);
		win.Tab_mgr().Tab_mgr().BackColor_(ColorAdp_.White);

		app.User().Cfg_mgr().Startup_mgr().Window_mgr().Init_window(win_box);
		win.Resizer().Exec_win_resize(app, win_box.Width(), win_box.Height());

		IconAdp.regy_loadDir_shallow(app.User().Fsys_mgr().Root_dir().GenSubDir_nest("app", "img", "win"));
		win_box.Icon_(IconAdp.regy_("xowa.app"));
	}
	public static GfuiBtn new_btn(Xoae_app app, Gfui_kit kit, GfuiWin win, Io_url img_dir, String id, String file) {
		GfuiBtn rv = kit.New_btn(id, win);
		rv.Btn_img_(kit.New_img_load(img_dir.GenSubFil(file)));
		return rv;
	}
	public static GfuiTextBox new_txt(Xoae_app app, Gfui_kit kit, GfuiWin win, FontAdp ui_font, String id, boolean border_on) {
		GfuiTextBox rv = kit.New_text_box(id, win, KeyVal_.new_(GfuiTextBox.CFG_border_on_, border_on));
		rv.TextMgr().Font_(ui_font);
		return rv;
	}
	public static void Update_tiptext(Xoae_app app, GfuiElem elem, int tiptext_id) {
		elem.TipText_(Xog_win_itm_.new_tiptext(app, tiptext_id));
	}
	public static void Font_update(Xog_win_itm win, Xol_font_info itm_font) {
		FontAdp gui_font = win.Url_box().TextMgr().Font();
		if (!itm_font.Eq(gui_font)) {
			FontAdp new_font = itm_font.XtoFontAdp();
			win.Url_box().TextMgr().Font_(new_font);
			win.Find_box().TextMgr().Font_(new_font);
			win.Prog_box().TextMgr().Font_(new_font);
			win.Tab_mgr().Tab_mgr().TextMgr().Font_(new_font);
		}
	}
	public static String new_tiptext(Xoae_app app, int id) {return String_.new_utf8_(app.User().Lang().Msg_mgr().Val_by_id(app.User().Wiki(), id));}
}
